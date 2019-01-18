package twilightforest.entity.boss;

import twilightforest.TwilightForestMod;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class EntityTFIceCrystal extends EntityMob {

	private int crystalAge;
	public int maxCrystalAge;


	public EntityTFIceCrystal(World par1World) {
		super(par1World);
		
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
        this.tasks.addTask(2, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(3, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        this.setSize(0.6F, 1.8F);
        
        this.maxCrystalAge = -1;
        
        //this.setCurrentItemOrArmor(0, new ItemStack(TFItems.iceSword));

	}


    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D*1.5D+twilightforest.TwilightForestMod.Scatter.nextInt(15/3)-twilightforest.TwilightForestMod.Scatter.nextInt(15/3));
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D*1.5D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5.0D*1.5D);
    }
    

    /**
     * Returns true if the newer Entity AI code should be run
     */
    protected boolean isAIEnabled()
    {
        return true;
    }
    
    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected Item getDropItem()
    {
        return Items.snowball;
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int getMaxSpawnedInChunk()
    {
    	return 8;
    }

    @Override
    protected String getLivingSound()
    {
    	return TwilightForestMod.ID + ":mob.ice.noise";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
    	return TwilightForestMod.ID + ":mob.ice.hurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
    	return TwilightForestMod.ID + ":mob.ice.death";
    }


    /**
     * Used by the snow queen when she summons crystals
     */
    public void setToDieIn30Seconds() {
    	this.maxCrystalAge = 600;
    }
    
//
//    /**
//     * Get this Entity's EnumCreatureAttribute
//     */
//    public EnumCreatureAttribute getCreatureAttribute()
//    {
//        return EnumCreatureAttribute.UNDEAD;
//    }

    
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        
        this.crystalAge++;
        
        //System.out.println("I am a skeleton and my age is " + this.skeletonAge);
        
        // die after 30 seconds
        if (this.maxCrystalAge > 0 && this.crystalAge >= this.maxCrystalAge && !this.worldObj.isRemote) {
        	this.setDead();
        }
    }
}
