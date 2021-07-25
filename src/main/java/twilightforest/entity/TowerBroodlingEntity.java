package twilightforest.entity;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import twilightforest.TFSounds;

public class TowerBroodlingEntity extends SwarmSpiderEntity {

	public TowerBroodlingEntity(EntityType<? extends TowerBroodlingEntity> type, Level world) {
		this(type, world, true);
	}

	public TowerBroodlingEntity(EntityType<? extends TowerBroodlingEntity> type, Level world, boolean spawnMore) {
		super(type, world, spawnMore);
		xpReward = 3;
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return SwarmSpiderEntity.registerAttributes()
				.add(Attributes.MAX_HEALTH, 7.0D)
				.add(Attributes.ATTACK_DAMAGE, 4.0D);
	}
	
	@Override
	protected SoundEvent getAmbientSound() {
	      return TFSounds.BROODLING_AMBIENT;
	   }

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
	      return TFSounds.BROODLING_HURT;
	   }

	@Override
	protected SoundEvent getDeathSound() {
	      return TFSounds.BROODLING_DEATH;
	   }

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
	      this.playSound(TFSounds.BROODLING_STEP, 0.15F, 1.0F);
	   }

	@Override
	protected boolean spawnAnother() {
		SwarmSpiderEntity another = new TowerBroodlingEntity(TFEntities.tower_broodling, level, false);

		double sx = getX() + (random.nextBoolean() ? 0.9 : -0.9);
		double sy = getY();
		double sz = getZ() + (random.nextBoolean() ? 0.9 : -0.9);
		another.moveTo(sx, sy, sz, random.nextFloat() * 360F, 0.0F);
		if (!another.checkSpawnRules(level, MobSpawnType.MOB_SUMMONED)) {
			another.remove();
			return false;
		}
		level.addFreshEntity(another);
		another.spawnAnim();

		return true;
	}
}
