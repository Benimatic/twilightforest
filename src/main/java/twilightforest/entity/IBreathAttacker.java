package twilightforest.entity;

import net.minecraft.entity.Entity;

public interface IBreathAttacker {

	boolean isBreathing();

	void setBreathing(boolean flag);

	/**
	 * Deal damage for our breath attack
	 *
	 * @param target
	 */
	void doBreathAttack(Entity target);
}