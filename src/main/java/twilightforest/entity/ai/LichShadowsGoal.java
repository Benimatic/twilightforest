package twilightforest.entity.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3d;
import twilightforest.entity.boss.LichEntity;
import twilightforest.item.TFItems;

import java.util.EnumSet;

public class LichShadowsGoal extends Goal {

	private final LichEntity lich;

	public LichShadowsGoal(LichEntity boss) {
		lich = boss;
		setMutexFlags(EnumSet.of(Flag.LOOK, Flag.MOVE));
	}

	@Override
	public boolean shouldExecute() {
		return lich.getPhase() == 1;
	}

	@Override
	public void startExecuting() {
		lich.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(TFItems.twilight_scepter.get()));
	}

	@Override
	public void resetTask() {
		despawnClones();
	}

	@Override
	public void tick() {
		if (lich.isShadowClone())
			checkForMaster();
		LivingEntity targetedEntity = lich.getAttackTarget();
		if (targetedEntity == null)
			return;
		float dist = lich.getDistance(targetedEntity);

		if (lich.getAttackCooldown() == 60) {
			lich.teleportToSightOfEntity(targetedEntity);

			if (!lich.isShadowClone()) {
				checkAndSpawnClones();
			}
		}

		if (lich.getEntitySenses().canSee(targetedEntity) && lich.getAttackCooldown() == 0 && dist < 20F) {
			if (lich.getNextAttackType() == 0) {
				lich.launchBoltAt();
			} else {
				lich.launchBombAt();
			}

			if (lich.getRNG().nextInt(3) > 0) {
				lich.setNextAttackType(0);
			} else {
				lich.setNextAttackType(1);
			}
			lich.setAttackCooldown(100);
		}
	}

	private void checkForMaster() {
		if (lich.getMasterLich() == null) {
			findNewMaster();
		}
		if (!lich.world.isRemote && (lich.getMasterLich() == null || !lich.getMasterLich().isAlive())) {
			lich.remove();
		}
	}

	private void checkAndSpawnClones() {
		// if not, spawn one!
		if (lich.countMyClones() < LichEntity.MAX_SHADOW_CLONES)
			spawnShadowClone();
	}

	private void spawnShadowClone() {
		LivingEntity targetedEntity = lich.getAttackTarget();

		// find a good spot
		Vector3d cloneSpot = lich.findVecInLOSOf(targetedEntity);

		if (cloneSpot != null) {
			// put a clone there
			LichEntity newClone = new LichEntity(lich.world, lich);
			newClone.setPosition(cloneSpot.x, cloneSpot.y, cloneSpot.z);
			lich.world.addEntity(newClone);

			newClone.setAttackTarget(targetedEntity);
			newClone.setAttackCooldown(60 + lich.getRNG().nextInt(3) - lich.getRNG().nextInt(3));

			// make sparkles leading to it
			lich.makeTeleportTrail(lich.getPosX(), lich.getPosY(), lich.getPosZ(), cloneSpot.x, cloneSpot.y, cloneSpot.z);
		}
	}

	private void despawnClones() {
		for (LichEntity nearbyLich : lich.getNearbyLiches()) {
			if (nearbyLich.isShadowClone()) {
				nearbyLich.remove(true);
			}
		}
	}

	private void findNewMaster() {

		for (LichEntity nearbyLich : lich.getNearbyLiches()) {
			if (!nearbyLich.isShadowClone() && nearbyLich.wantsNewClone(lich)) {
				lich.setMaster(nearbyLich);

				// animate our new linkage!
				lich.makeTeleportTrail(lich.getPosX(), lich.getPosY(), lich.getPosZ(), nearbyLich.getPosX(), nearbyLich.getPosY(), nearbyLich.getPosZ());

				lich.setAttackTarget(nearbyLich.getAttackTarget());
				break;
			}
		}
	}
}
