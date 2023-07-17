package twilightforest.dispenser;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.AABB;
import twilightforest.init.TFSounds;

import java.util.List;

public class FeatherFanDispenseBehavior extends DefaultDispenseItemBehavior {

	boolean fired = false;

	@Override
	protected ItemStack execute(BlockSource source, ItemStack stack) {
		Level level = source.getLevel();
		BlockPos blockpos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
		int damage = stack.getMaxDamage() - stack.getDamageValue();
		if (!level.isClientSide()) {
			List<LivingEntity> thingsToPush = level.getEntitiesOfClass(LivingEntity.class, new AABB(blockpos).inflate(3), EntitySelector.NO_SPECTATORS);
			if (!(thingsToPush.size() >= damage)) {
				for (Entity entity : thingsToPush) {
					Vec3i lookVec = level.getBlockState(source.getPos()).getValue(DispenserBlock.FACING).getNormal();

					if (entity.isPushable() || entity instanceof ItemEntity) {
						entity.setDeltaMovement(lookVec.getX(), lookVec.getY(), lookVec.getZ());
						if (stack.hurt(1, level.getRandom(), null)) {
							stack.setCount(0);
						}
					}
				}
				this.fired = true;
			}
		}
		return stack;
	}

	@Override
	protected void playSound(BlockSource source) {
		if (this.fired) {
			RandomSource random = source.getLevel().getRandom();
			source.getLevel().playSound(null, source.getPos(), TFSounds.FAN_WHOOSH.get(), SoundSource.BLOCKS, 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F);
			this.fired = false;
		} else {
			source.getLevel().levelEvent(1001, source.getPos(), 0);
		}
	}

	//Particle woooosh
	//[VanillaCopy] of WorldRender.playEvent(case 2000), but with further range and a different particle
	@Override
	protected void playAnimation(BlockSource source, Direction direction) {
		BlockPos blockpos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
		Level world = source.getLevel();
		RandomSource random = world.getRandom();

		int j1 = direction.getStepX();
		int j2 = direction.getStepY();
		int k2 = direction.getStepZ();
		double d18 = (double) blockpos.getX() + (double) j1 * 0.6D + 0.5D;
		double d24 = (double) blockpos.getY() + (double) j2 * 0.6D + 0.5D;
		double d28 = (double) blockpos.getZ() + (double) k2 * 0.6D + 0.5D;

		for (int i = 0; i < 30; ++i) {
			double d4 = random.nextDouble() * 0.2D + 0.01D;
			double d6 = d18 + (double) j1 * 0.01D + (random.nextDouble() - 0.5D) * (double) k2 * 0.5D;
			double d8 = d24 + (double) j2 * 0.01D + (random.nextDouble() - 0.5D) * (double) j2 * 0.5D;
			double d30 = d28 + (double) k2 * 0.01D + (random.nextDouble() - 0.5D) * (double) j1 * 0.5D;
			double d9 = (double) j1 * d4 + random.nextGaussian() * 0.01D;
			double d10 = (double) j2 * d4 + random.nextGaussian() * 0.01D;
			double d11 = (double) k2 * d4 + random.nextGaussian() * 0.01D;
			world.addParticle(ParticleTypes.CLOUD, d6, d8, d30, d9, d10, d11);
		}
	}
}
