package twilightforest.entity.projectile;

import net.minecraft.core.Position;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.world.BlockEvent;
import twilightforest.data.BlockTagGenerator;
import twilightforest.entity.TFEntities;
import twilightforest.entity.monster.Yeti;
import twilightforest.potions.TFMobEffects;
import twilightforest.util.TFDamageSources;

import java.util.List;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class IceBomb extends TFThrowable {

	private int zoneTimer = 80;
	private boolean hasHit;

	public IceBomb(EntityType<? extends IceBomb> type, Level world) {
		super(type, world);
	}

	public IceBomb(EntityType<? extends IceBomb> type, Level world, LivingEntity thrower) {
		super(type, world, thrower);
	}

	public IceBomb(Level world, Position pos) {
		super(TFEntities.THROWN_ICE, world, pos.x(), pos.y(), pos.z());
	}

	@Override
	protected void onHit(HitResult ray) {
		this.setDeltaMovement(0.0D, 0.0D, 0.0D);
		this.hasHit = true;

		this.doTerrainEffects();
	}

	private void doTerrainEffects() {

		final int range = 3;

		int ix = Mth.floor(this.xOld);
		int iy = Mth.floor(this.yOld);
		int iz = Mth.floor(this.zOld);

		for (int x = -range; x <= range; x++) {
			for (int y = -range; y <= range; y++) {
				for (int z = -range; z <= range; z++) {
					BlockPos pos = new BlockPos(ix + x, iy + y, iz + z);
					this.doTerrainEffect(pos);
				}
			}
		}
	}

	/**
	 * Freeze water, put snow on snowable surfaces
	 */
	private void doTerrainEffect(BlockPos pos) {
		BlockState state = level.getBlockState(pos);
		if(!level.isClientSide) {
			if (state.getMaterial() == Material.WATER) {
				this.level.setBlockAndUpdate(pos, Blocks.ICE.defaultBlockState());
			}
			if (state == Blocks.LAVA.defaultBlockState()) {
				this.level.setBlockAndUpdate(pos, Blocks.OBSIDIAN.defaultBlockState());
			}
			if (this.level.isEmptyBlock(pos) && Blocks.SNOW.defaultBlockState().canSurvive(this.level, pos)) {
				this.level.setBlockAndUpdate(pos, Blocks.SNOW.defaultBlockState());
			}
			if (BlockTagGenerator.ICE_BOMB_REPLACEABLES.contains(state.getBlock())) {
				this.level.setBlock(pos, Blocks.SNOW.defaultBlockState().canSurvive(this.level, pos) ? Blocks.SNOW.defaultBlockState() : Blocks.AIR.defaultBlockState(), 3);
			}
			if (state.is(Blocks.SNOW) && state.getValue(SnowLayerBlock.LAYERS) < 8) {
				this.level.setBlockAndUpdate(pos, state.setValue(SnowLayerBlock.LAYERS, state.getValue(SnowLayerBlock.LAYERS) + 1));
			}
		}
	}

	@Override
	public void tick() {
		super.tick();

		if (this.hasHit) {
			this.getDeltaMovement().multiply(0.1D, 0.1D, 0.1D);

			this.zoneTimer--;
			makeIceZone();

			if (!level.isClientSide && this.zoneTimer <= 0) {
				level.levelEvent(2001, new BlockPos(this.blockPosition()), Block.getId(Blocks.ICE.defaultBlockState()));
				discard();
			}
		} else {
			makeTrail();
		}
	}

	public void makeTrail() {
		BlockState stateId = Blocks.SNOW.defaultBlockState();
		for (int i = 0; i < 5; i++) {
			double dx = getX() + 1.5F * (random.nextFloat() - 0.5F);
			double dy = getY() + 1.5F * (random.nextFloat() - 0.5F);
			double dz = getZ() + 1.5F * (random.nextFloat() - 0.5F);

			level.addParticle(new BlockParticleOption(ParticleTypes.FALLING_DUST, stateId), dx, dy, dz, -getDeltaMovement().x(), -getDeltaMovement().y(), -getDeltaMovement().z());
		}
	}

	private void makeIceZone() {
		if (this.level.isClientSide) {
			// sparkles
			BlockState stateId = Blocks.SNOW.defaultBlockState();
			for (int i = 0; i < 15; i++) {
				double dx = this.getX() + (random.nextFloat() - random.nextFloat()) * 3.0F;
				double dy = this.getY() + (random.nextFloat() - random.nextFloat()) * 3.0F;
				double dz = this.getZ() + (random.nextFloat() - random.nextFloat()) * 3.0F;

				level.addParticle(new BlockParticleOption(ParticleTypes.FALLING_DUST, stateId), dx, dy, dz, 0, 0, 0);
			}
		} else {
			if (this.zoneTimer % 20 == 0) {
				hitNearbyEntities();
			}
		}
	}

	private void hitNearbyEntities() {
		List<LivingEntity> nearby = this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(3, 2, 3));

		for (LivingEntity entity : nearby) {
			if (entity != this.getOwner()) {
				if (entity instanceof Yeti) {
					// TODO: make "frozen yeti" entity?
					BlockPos pos = new BlockPos(entity.xOld, entity.yOld, entity.zOld);
					level.setBlockAndUpdate(pos, Blocks.ICE.defaultBlockState());
					level.setBlockAndUpdate(pos.above(), Blocks.ICE.defaultBlockState());

					entity.discard();
				} else {
					if(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES.contains(entity.getType())) {
						entity.hurt(TFDamageSources.frozen(this, (LivingEntity) this.getOwner()), EntityTypeTags.FREEZE_HURTS_EXTRA_TYPES.contains(entity.getType()) ? 5 : 1);
						entity.addEffect(new MobEffectInstance(TFMobEffects.FROSTY.get(), 20 * 5));
					}
				}
			}
		}
	}

	public BlockState getBlockState() {
		return Blocks.PACKED_ICE.defaultBlockState();
	}

	@Override
	protected float getGravity() {
		return this.hasHit ? 0F : 0.025F;
	}
}
