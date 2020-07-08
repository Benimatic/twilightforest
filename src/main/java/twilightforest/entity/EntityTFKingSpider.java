package twilightforest.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityTFKingSpider extends SpiderEntity {

	public EntityTFKingSpider(EntityType<? extends EntityTFKingSpider> type, World world) {
		super(type, world);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		//this.goalSelector.addGoal(1, new EntityAITFChargeAttack(this, 0.4F));
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return SpiderEntity.func_234305_eI_()
				.func_233815_a_(Attributes.MAX_HEALTH, 30.0D)
				.func_233815_a_(Attributes.MOVEMENT_SPEED, 0.35D)
				.func_233815_a_(Attributes.ATTACK_DAMAGE, 6.0D);
	}

	//TODO: Moved to renderer?
//	@Override
//	public float getRenderSizeModifier() {
//		return 2.0F;
//	}

	@Override
	public boolean isOnLadder() {
		// let's not climb
		return false;
	}

	@Nullable
	@Override
	public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficulty, SpawnReason reason, @Nullable ILivingEntityData livingData, @Nullable CompoundNBT dataTag) {
		livingData = super.onInitialSpawn(worldIn, difficulty, reason, livingData, dataTag);

		// will always have a dryad riding the spider or whatever is riding the spider
		EntityTFSkeletonDruid druid = TFEntities.skeleton_druid.create(world);
		druid.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, 0.0F);
		druid.onInitialSpawn(worldIn, difficulty, SpawnReason.JOCKEY, null, null);
		this.world.addEntity(druid);
		Entity lastRider = this;
		while (!lastRider.getPassengers().isEmpty())
			lastRider = lastRider.getPassengers().get(0);
		druid.startRiding(lastRider);

		return livingData;
	}

	@Override
	public double getMountedYOffset() {
		return (double) this.getHeight() * 0.75D;
	}
}
