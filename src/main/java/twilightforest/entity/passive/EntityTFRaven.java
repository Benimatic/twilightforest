package twilightforest.entity.passive;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;
import twilightforest.entity.ai.EntityTFRavenLookHelper;
import twilightforest.item.TFItems;


public class EntityTFRaven extends EntityTFTinyBird {
	
	EntityTFRavenLookHelper ravenLook = new EntityTFRavenLookHelper(this);

    public EntityTFRaven(World par1World) {
		super(par1World);
		//texture = TwilightForestMod.MODEL_DIR + "raven.png";
		
        this.setSize(0.3F, 0.7F);
		
		// maybe this will help them move cuter?
		this.stepHeight = 1;
		
		// bird AI
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.5F));
        this.tasks.addTask(2, new EntityAITempt(this, 0.85F, Items.wheat_seeds, true));
//        this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityPlayer.class, 2.0F, 0.23F, 0.4F));
//        this.tasks.addTask(4, new EntityAITFBirdFly(this, 0.25F));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0F));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
 	}
	
	/**
	 * Set monster attributes
	 */
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D+twilightforest.TwilightForestMod.Scatter.nextInt(10)-twilightforest.TwilightForestMod.Scatter.nextInt(10)); // max health
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.20000001192092896D);
    }
    
    /**
     * We have our own getLookHelper
     */
    @Override
	protected void updateAITasks() {
		super.updateAITasks();
		this.ravenLook.onUpdateLook();
	}

    /**
     * We have our own getLookHelper
     */
	@Override
	public EntityLookHelper getLookHelper()
    {
        return this.ravenLook;
    }
	
    /**
     * Returns the sound this mob makes while it's alive.
     */
	@Override
    protected String getLivingSound()
    {
        return TwilightForestMod.ID + ":mob.raven.caw";
    }
 
    /**
     * Returns the sound this mob makes when it is hurt.
     */
	@Override
    protected String getHurtSound()
    {
        return TwilightForestMod.ID + ":mob.raven.squawk";
    }

    /**
     * Returns the sound this mob makes on death.
     */
	@Override
    protected String getDeathSound()
    {
        return TwilightForestMod.ID + ":mob.raven.squawk";
    }
	
	/**
	 * Returns the item ID for the item the mob drops on death.
	 */
    @Override
	protected Item getDropItem()
    {
        return TFItems.feather;
    }

	/**
	 * Actually only used for the shadow
	 */
	@Override
	public float getRenderSizeModifier() {
		 return 0.3F;
	}


    /**
     * Return true if the bird is spooked
     */
	public boolean isSpooked() {
		return this.hurtTime > 0;
	}
    
}
