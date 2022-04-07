package twilightforest.entity.ai;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import twilightforest.data.tags.EntityTagGenerator;
import twilightforest.entity.boss.Lich;
import twilightforest.util.EntityUtil;

public class LichPopMobsGoal extends Goal {

	private final Lich lich;

	public LichPopMobsGoal(Lich lich) {
		this.lich = lich;
	}

	@Override
	public boolean requiresUpdateEveryTick() {
		return true;
	}

	@Override
	public boolean canUse() {
		return !this.lich.isShadowClone() &&
				this.lich.getHealth() < this.lich.getMaxHealth() &&
				this.lich.getPopCooldown() == 0 &&
				!this.lich.level.getEntitiesOfClass(Mob.class,
						this.lich.getBoundingBox().inflate(32.0D, 16.0D, 32.0D),
						e -> e.getType().is(EntityTagGenerator.LICH_POPPABLES)).isEmpty();
	}

	@Override
	public void tick() {
		super.tick();
		for (Mob mob : lich.level.getEntitiesOfClass(Mob.class, this.lich.getBoundingBox().inflate(32.0D, 16.0D, 32.0D), e -> e.getType().is(EntityTagGenerator.LICH_POPPABLES))) {
			if (this.lich.getSensing().hasLineOfSight(mob)) {
				if(!lich.level.isClientSide) {
					mob.spawnAnim();
					mob.discard();
					// play death sound
					this.lich.level.playSound(null, mob.blockPosition(), EntityUtil.getDeathSound(mob), SoundSource.HOSTILE, 1.0F, mob.getVoicePitch());

					// make trail so it's clear that we did it
					//TODO goal tick methods are only fired server side.
					//changing this to use sendParticles instead causes them to be either rainbow or black. We need pink.
					this.lich.makeRedMagicTrail(mob.eyeBlockPosition(), this.lich.eyeBlockPosition());

					//heal a little health
					this.lich.heal(2.0F);

					this.lich.setPopCooldown(60);

					break;
				}
			}
		}
	}
}
