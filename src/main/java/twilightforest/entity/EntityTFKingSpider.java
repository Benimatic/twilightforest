package twilightforest.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;

public class EntityTFKingSpider extends EntitySpider {
	public static final ResourceLocation LOOT_TABLE = TwilightForestMod.prefix("entities/king_spider");

	public EntityTFKingSpider(World world) {
		super(world);
		this.setSize(1.6F, 1.6F);
	}

	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		//this.tasks.addTask(1, new EntityAITFChargeAttack(this, 0.4F));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
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

	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingData) {
		livingData = super.onInitialSpawn(difficulty, livingData);

		// will always have a dryad riding the spider or whatever is riding the spider
		EntityTFSkeletonDruid druid = new EntityTFSkeletonDruid(this.world);
		druid.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
		druid.onInitialSpawn(difficulty, null);
		this.world.spawnEntity(druid);
		Entity lastRider = this;
		while (!lastRider.getPassengers().isEmpty())
			lastRider = lastRider.getPassengers().get(0);
		druid.startRiding(lastRider);

		return livingData;
	}

	@Override
	public double getMountedYOffset() {
		return (double) this.height * 0.75D;
	}

	@Override
	protected ResourceLocation getLootTable() {
		return LOOT_TABLE;
	}
}
