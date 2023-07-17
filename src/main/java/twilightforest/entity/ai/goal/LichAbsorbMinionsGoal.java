package twilightforest.entity.ai.goal;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.gameevent.GameEvent;
import twilightforest.entity.boss.Lich;
import twilightforest.entity.monster.LichMinion;
import twilightforest.init.TFSounds;
import twilightforest.item.LifedrainScepterItem;
import twilightforest.util.EntityUtil;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

public class LichAbsorbMinionsGoal extends Goal {

	private final Lich lich;

	public LichAbsorbMinionsGoal(Lich lich) {
		this.lich = lich;
		this.setFlags(EnumSet.of(Flag.LOOK, Flag.MOVE));
	}

	@Override
	public boolean canUse() {
		return this.lich.getHealth() < this.lich.getMaxHealth() / 2 && this.lich.countMyMinions() > 0;
	}

	@Override
	public void start() {
		super.start();
		this.lich.setScepterTime();
	}

	@Override
	public void stop() {
		super.stop();
		this.lich.resetScepterTime();
	}

	@Override
	public void tick() {
		super.tick();
		if (this.lich.getScepterTimeLeft() > 0) return;

		List<LichMinion> minions = this.lich.getLevel().getEntitiesOfClass(LichMinion.class, this.lich.getBoundingBox().inflate(32.0D, 16.0D, 32.0D))
				.stream().filter(m -> m.master == this.lich).collect(Collectors.toList());

		if (!minions.isEmpty()) {
			LichMinion minion = minions.get(0);
			minion.discard();
			//rain particles
			LifedrainScepterItem.animateTargetShatter((ServerLevel) this.lich.getLevel(), minion);
			// play death sound
			SoundEvent deathSound = EntityUtil.getDeathSound(minion);
			if (deathSound != null) {
				this.lich.getLevel().playSound(null, minion.blockPosition(), deathSound, SoundSource.HOSTILE, 1.0F, minion.getVoicePitch());
			}
			//funny pop sound
			this.lich.playSound(TFSounds.LICH_POP_MOB.get(), 3.0F, 0.4F + this.lich.getRandom().nextFloat() * 0.2F);
			minion.playSound(TFSounds.LICH_POP_MOB.get(), 3.0F, 0.4F + this.lich.getRandom().nextFloat() * 0.2F);
			// make trail so it's clear that we did it
			this.lich.makeMagicTrail(minion.getEyePosition(), this.lich.getEyePosition(), 0.5F, 0.0F, 1.0F);
			//heal some health
			this.lich.heal(minion.getHealth());
			this.lich.swing(InteractionHand.MAIN_HAND);
			this.lich.setPopCooldown(40);
			this.lich.gameEvent(GameEvent.ENTITY_DIE);
		}
	}
}
