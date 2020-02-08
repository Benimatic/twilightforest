package twilightforest.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import twilightforest.entity.TFEntities;
import twilightforest.entity.boss.EntityTFKnightPhantom;
import twilightforest.entity.boss.EntityTFThrownWep;
import twilightforest.item.TFItems;

import java.util.EnumSet;

public class EntityAIPhantomThrowWeapon extends Goal {

	private final EntityTFKnightPhantom boss;

	public EntityAIPhantomThrowWeapon(EntityTFKnightPhantom entity) {
		boss = entity;
		setMutexFlags(EnumSet.of(Flag.LOOK));
	}

	@Override
	public boolean shouldExecute() {
		return boss.getAttackTarget() != null && boss.getCurrentFormation() == EntityTFKnightPhantom.Formation.ATTACK_PLAYER_ATTACK;
	}

	@Override
	public void tick() {
		if (boss.getAttackTarget() != null && boss.getTicksProgress() % 4 == 0) {
			if (boss.isAxeKnight())
				launchAxeAt(boss.getAttackTarget());
			else if (boss.isPickKnight())
				launchPicks();
		}
	}

	private void launchAxeAt(Entity targetedEntity) {
		float bodyFacingAngle = ((boss.renderYawOffset * 3.141593F) / 180F);
		double sx = boss.getX() + (MathHelper.cos(bodyFacingAngle) * 1);
		double sy = boss.getY() + (boss.getHeight() * 0.82);
		double sz = boss.getZ() + (MathHelper.sin(bodyFacingAngle) * 1);

		double tx = targetedEntity.getX() - sx;
		double ty = (targetedEntity.getBoundingBox().minY + (double) (targetedEntity.getHeight() / 2.0F)) - (boss.getY() + boss.getHeight() / 2.0F);
		double tz = targetedEntity.getZ() - sz;

		boss.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1.0F, (boss.getRNG().nextFloat() - boss.getRNG().nextFloat()) * 0.2F + 0.4F);
		EntityTFThrownWep projectile = new EntityTFThrownWep(TFEntities.thrown_wep.get(), boss.world, boss).setItem(new ItemStack(TFItems.knightmetal_axe.get()));

		float speed = 0.75F;

		projectile.shoot(tx, ty, tz, speed, 1.0F);

		projectile.setLocationAndAngles(sx, sy, sz, boss.rotationYaw, boss.rotationPitch);

		boss.world.addEntity(projectile);
	}

	private void launchPicks() {
		boss.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1.0F, (boss.getRNG().nextFloat() - boss.getRNG().nextFloat()) * 0.2F + 0.4F);

		for (int i = 0; i < 8; i++) {
			float throwAngle = i * 3.14159165F / 4F;

			double sx = boss.getX() + (MathHelper.cos(throwAngle) * 1);
			double sy = boss.getY() + (boss.getHeight() * 0.82);
			double sz = boss.getZ() + (MathHelper.sin(throwAngle) * 1);

			double vx = MathHelper.cos(throwAngle);
			double vy = 0;
			double vz = MathHelper.sin(throwAngle);


			EntityTFThrownWep projectile = new EntityTFThrownWep(TFEntities.thrown_wep.get(), boss.world, boss).setDamage(3).setVelocity(0.015F).setItem(new ItemStack(TFItems.knightmetal_pickaxe.get()));


			projectile.setLocationAndAngles(sx, sy, sz, i * 45F, boss.rotationPitch);

			float speed = 0.5F;

			projectile.shoot(vx, vy, vz, speed, 1.0F);

			boss.world.addEntity(projectile);
		}
	}
}