package twilightforest.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;

import javax.annotation.Nullable;

public class EntityTFKingSpider extends SpiderEntity {
	public static final ResourceLocation LOOT_TABLE = TwilightForestMod.prefix("entities/king_spider");

	public EntityTFKingSpider(EntityType<? extends EntityTFKingSpider> type, World world) {
		super(type, world);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		//this.goalSelector.addGoal(1, new EntityAITFChargeAttack(this, 0.4F));
	}

	@Override
	protected void registerAttributes() {
		super.registerAttributes();
		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
		this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
	}

	@Override
	public float getRenderSizeModifier() {
		return 2.0F;
	}

	@Override
	public boolean isOnLadder() {
		// let's not climb
		return false;
	}

	@Nullable
	@Override
	public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData livingData, @Nullable CompoundNBT dataTag) {
		livingData = super.onInitialSpawn(worldIn, difficultyIn, reason, livingData, dataTag);

		// will always have a dryad riding the spider or whatever is riding the spider
		EntityTFSkeletonDruid druid = new EntityTFSkeletonDruid(TFEntities.skeleton_druid.get(), this.world);
		druid.setLocationAndAngles(this.getX(), this.getY(), this.getZ(), this.rotationYaw, 0.0F);
		druid.onInitialSpawn(difficulty, null);
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

	@Override
	protected ResourceLocation getLootTable() {
		return LOOT_TABLE;
	}
}
