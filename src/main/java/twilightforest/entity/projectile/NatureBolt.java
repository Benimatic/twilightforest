package twilightforest.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.ForgeEventFactory;
import twilightforest.data.tags.BlockTagGenerator;
import twilightforest.init.TFDamageTypes;
import twilightforest.init.TFEntities;
import twilightforest.util.EntityUtil;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class NatureBolt extends TFThrowable implements ITFProjectile, ItemSupplier {

	public NatureBolt(EntityType<? extends NatureBolt> type, Level level) {
		super(type, level);
	}

	public NatureBolt(Level level, LivingEntity owner) {
		super(TFEntities.NATURE_BOLT.get(), level, owner);
	}

	@Override
	public void tick() {
		super.tick();
		this.makeTrail(ParticleTypes.HAPPY_VILLAGER, 5);
	}

	@Override
	protected float getGravity() {
		return 0.003F;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void handleEntityEvent(byte id) {
		if (id == 3) {
			for (int i = 0; i < 8; ++i) {
				this.getLevel().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.OAK_LEAVES.defaultBlockState()), false, this.getX(), this.getY(), this.getZ(), random.nextGaussian() * 0.05D, random.nextDouble() * 0.2D, random.nextGaussian() * 0.05D);
			}
		} else {
			super.handleEntityEvent(id);
		}
	}

	@Override
	protected void onHitBlock(BlockHitResult result) {
		super.onHitBlock(result);
		BlockPos blockPosHit = result.getBlockPos();
		Material materialHit = this.getLevel().getBlockState(blockPosHit).getMaterial();

		if (ForgeEventFactory.getMobGriefingEvent(this.getLevel(), this)) {
			if (materialHit == Material.GRASS) {
				ItemStack dummy = new ItemStack(Items.BONE_MEAL, 1);
				if (BoneMealItem.growCrop(dummy, this.getLevel(), blockPosHit)) {
					this.getLevel().levelEvent(2005, blockPosHit, 0);
				}
			} else if (materialHit.isSolid() && this.canReplaceBlock(this.getLevel(), blockPosHit)) {
				this.getLevel().setBlockAndUpdate(blockPosHit, Blocks.BIRCH_LEAVES.defaultBlockState().setValue(LeavesBlock.PERSISTENT, true));
			}
		}
	}

	@Override
	protected void onHitEntity(EntityHitResult result) {
		super.onHitEntity(result);
		Entity owner = this.getOwner();
		Entity entityHit = result.getEntity();
		if (entityHit instanceof LivingEntity living && (owner == null || (entityHit != owner && entityHit != owner.getVehicle()))) {
			if (entityHit.hurt(TFDamageTypes.getIndirectEntityDamageSource(this.getLevel(), TFDamageTypes.LEAF_BRAIN, this.getOwner(), this), 2)
					&& this.getLevel().getDifficulty() != Difficulty.PEACEFUL) {
				int poisonTime = this.getLevel().getDifficulty() == Difficulty.HARD ? 7 : 3;
				living.addEffect(new MobEffectInstance(MobEffects.POISON, poisonTime * 20, 0));
			}
		}
	}

	@Override
	protected void onHit(HitResult result) {
		super.onHit(result);
		if (!this.getLevel().isClientSide()) {
			this.getLevel().broadcastEntityEvent(this, (byte) 3);
			this.discard();
		}
	}

	private boolean canReplaceBlock(Level level, BlockPos pos) {
		return !level.getBlockState(pos).hasBlockEntity() && level.getBlockState(pos).isSolidRender(level, pos) && level.getBlockState(pos).is(BlockTagGenerator.DRUID_PROJECTILE_REPLACEABLE) && EntityUtil.canDestroyBlock(level, pos, this);
	}

	@Override
	public ItemStack getItem() {
		return new ItemStack(Items.WHEAT_SEEDS);
	}
}
