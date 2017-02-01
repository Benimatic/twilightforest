package twilightforest.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityTFKingSpider extends EntitySpider {

	public EntityTFKingSpider(World world) {
		super(world);
		//texture = TwilightForestMod.MODEL_DIR + "kingspider.png";
        this.setSize(1.6F, 1.6F);
        //this.moveSpeed = 0.35F;

        this.setPathPriority(PathNodeType.WATER, -1.0F);
		//this.tasks.addTask(1, new EntityAITFChargeAttack(this, 0.4F));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.3F));
        this.tasks.addTask(6, new EntityAIWander(this, 0.2F));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true, false, null));

	}

	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
    }

    @Override
	protected Entity findPlayerToAttack()
    {
    	// kill at all times!
    	double var2 = 16.0D;
    	return this.world.getClosestVulnerablePlayerToEntity(this, var2);
    }

	//@Override
    public float spiderScaleAmount()
    {
        return 1.9F;
    }

	@Override
	public float getRenderSizeModifier() {
		 return 2.0F;
	}

    @Override
    public boolean isOnLadder()
    {
    	// let's not climb
        return false;
    }
    
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData par1EntityLivingData)
    {
        Object par1EntityLivingData1 = super.onInitialSpawn(difficulty, par1EntityLivingData);

    	// always a spider jockey
        EntityTFSkeletonDruid druid = new EntityTFSkeletonDruid(this.world);
        druid.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
        druid.onInitialSpawn(difficulty, null);
        this.world.spawnEntity(druid);
        druid.startRiding(this);
        
        return (IEntityLivingData)par1EntityLivingData1;
    }

    @Override
    public double getMountedYOffset()
    {
        return (double)this.height * 0.5D;
    }

}
