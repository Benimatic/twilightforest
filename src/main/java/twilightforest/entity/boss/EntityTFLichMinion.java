package twilightforest.entity.boss;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.potion.Effects;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

public class EntityTFLichMinion extends ZombieEntity {

	private static final DataParameter<Boolean> STRONG = EntityDataManager.createKey(EntityTFLichMinion.class, DataSerializers.BOOLEAN);

	EntityTFLich master;

	public EntityTFLichMinion(EntityType<? extends EntityTFLichMinion> type, World world) {
		super(type, world);
		this.master = null;
	}

	public EntityTFLichMinion(World world, EntityTFLich entityTFLich) {
		super(world);
		this.master = entityTFLich;
	}

	@Override
	protected void registerData() {
		super.registerData();
		dataManager.register(STRONG, false);
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		LivingEntity prevTarget = getAttackTarget();

		if (super.attackEntityFrom(source, amount)) {
			if (source.getTrueSource() instanceof EntityTFLich) {
				// return to previous target but speed up
				setRevengeTarget(prevTarget);
				addPotionEffect(new EffectInstance(Effects.SPEED, 200, 4));
				addPotionEffect(new EffectInstance(Effects.STRENGTH, 200, 1));
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void livingTick() {
		if (master == null) {
			findNewMaster();
		}
		// if we still don't have a master, or ours is dead, die.
		if (master == null || master.isDead) {
			this.setHealth(0);
		}
		super.livingTick();
	}

	private void findNewMaster() {
		List<EntityTFLich> nearbyLiches = world.getEntitiesWithinAABB(EntityTFLich.class, new AxisAlignedBB(getX(), getY(), getZ(), getX() + 1, getY() + 1, getZ() + 1).grow(32.0D, 16.0D, 32.0D));

		for (EntityTFLich nearbyLich : nearbyLiches) {
			if (!nearbyLich.isShadowClone() && nearbyLich.wantsNewMinion(this)) {
				this.master = nearbyLich;

				// animate our new linkage!
				master.makeBlackMagicTrail(getX(), getY() + this.getEyeHeight(), getZ(), master.getX(), master.getY() + master.getEyeHeight(), master.getZ());

				// become angry at our masters target
				setAttackTarget(master.getAttackTarget());

				// quit looking
				break;
			}
		}
	}

	@Override
	protected void onNewPotionEffect(EffectInstance effect) {
		super.onNewPotionEffect(effect);
		if (!world.isRemote && effect.getPotion() == Effects.STRENGTH) {
			dataManager.set(STRONG, true);
		}
	}

	@Override
	protected void onFinishedPotionEffect(EffectInstance effect) {
		super.onFinishedPotionEffect(effect);
		if (!world.isRemote && effect.getPotion() == Effects.STRENGTH) {
			dataManager.set(STRONG, false);
		}
	}

	public boolean isStrong() {
		return dataManager.get(STRONG);
	}
}
