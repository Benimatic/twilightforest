package twilightforest.entity.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.event.ForgeEventFactory;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFSounds;
import twilightforest.util.ColorUtil;

public class UnstableIceCore extends BaseIceMob {

	private static final float EXPLOSION_RADIUS = 1;

	public UnstableIceCore(EntityType<? extends UnstableIceCore> type, Level world) {
		super(type, world);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, false));
		this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0.23D)
				.add(Attributes.ATTACK_DAMAGE, 3.0D);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return TFSounds.ICE_CORE_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return TFSounds.ICE_CORE_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return TFSounds.ICE_CORE_DEATH.get();
	}

	@Override
	public float getEyeHeight(Pose pose) {
		return this.getBbHeight() * 0.6F;
	}

	@Override
	protected void tickDeath() {
		++this.deathTime;

		if (this.deathTime == 60) { // delay until 3 seconds
			if (!this.getLevel().isClientSide()) {
				boolean mobGriefing = ForgeEventFactory.getMobGriefingEvent(this.getLevel(), this);
				this.getLevel().explode(this, this.getX(), this.getY(), this.getZ(), UnstableIceCore.EXPLOSION_RADIUS, Level.ExplosionInteraction.MOB);

				if (mobGriefing) {
					this.transformBlocks();
				}
			}
			// Fake to trigger super's behaviour
			this.deathTime = 19;
			super.tickDeath();
			this.deathTime = 60;
		}
	}

	private void transformBlocks() {
		int range = 4;

		BlockPos pos = new BlockPos(this.blockPosition());

		for (int dx = -range; dx <= range; dx++) {
			for (int dy = -range; dy <= range; dy++) {
				for (int dz = -range; dz <= range; dz++) {
					double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

					float randRange = range + (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 2.0F;

					if (distance < randRange) {
						this.transformBlock(pos.offset(dx, dy, dz));
					}
				}
			}
		}
	}

	private void transformBlock(BlockPos pos) {
		BlockState state = this.getLevel().getBlockState(pos);
		Block block = state.getBlock();

		if (block.getExplosionResistance() < 8F && state.getDestroySpeed(this.getLevel(), pos) >= 0) {
			// todo improve for blocks where state is known? or perhaps if a propertycolor is present
			int blockColor = state.getMapColor(this.getLevel(), pos).col;

			// do appropriate transformation
			if (this.shouldTransformGlass(state, pos)) {
				this.getLevel().setBlockAndUpdate(pos, ColorUtil.STAINED_GLASS.getColor(getClosestDyeColor(blockColor)));
			} else if (this.shouldTransformClay(state, pos)) {
				this.getLevel().setBlockAndUpdate(pos, ColorUtil.TERRACOTTA.getColor(getClosestDyeColor(blockColor)));
			}
		}
	}

	private boolean shouldTransformClay(BlockState state, BlockPos pos) {
		return state.isRedstoneConductor(this.getLevel(), pos);
	}

	private boolean shouldTransformGlass(BlockState state, BlockPos pos) {
		return state.getBlock() != Blocks.AIR && isBlockNormalBounds(state, pos) && (!state.getMaterial().isSolidBlocking() || state.getMaterial() == Material.LEAVES || state.is(Blocks.ICE) || state.is(TFBlocks.AURORA_BLOCK.get()));
	}

	private boolean isBlockNormalBounds(BlockState state, BlockPos pos) {
		return Block.isShapeFullBlock(state.getShape(this.getLevel(), pos));
	}

	private static DyeColor getClosestDyeColor(int blockColor) {
		int red = (blockColor >> 16) & 255;
		int green = (blockColor >> 8) & 255;
		int blue = blockColor & 255;


		DyeColor bestColor = DyeColor.WHITE;
		int bestDifference = 1024;

		for (DyeColor color : DyeColor.values()) {
			float[] iColor = color.getTextureDiffuseColors();

			int iRed = (int) (iColor[0] * 255F);
			int iGreen = (int) (iColor[1] * 255F);
			int iBlue = (int) (iColor[2] * 255F);

			int difference = Math.abs(red - iRed) + Math.abs(green - iGreen) + Math.abs(blue - iBlue);

			if (difference < bestDifference) {
				bestColor = color;
				bestDifference = difference;
			}
		}

		return bestColor;
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 2;
	}
}
