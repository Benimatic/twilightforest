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
        this.tasks.addTask(2, new EntityAITempt(this, 0.85F, Items.WHEAT_SEEDS, true));
//        this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityPlayer.class, 2.0F, 0.23F, 0.4F));
//        this.tasks.addTask(4, new EntityAITFBirdFly(this, 0.25F));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0F));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
 	}
	
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.20000001192092896D);
    }
    
    @Override
	protected void updateAITasks() {
		super.updateAITasks();
		this.ravenLook.onUpdateLook();
	}

	@Override
	public EntityLookHelper getLookHelper()
    {
        return this.ravenLook;
    }
	
	@Override
    protected String getLivingSound()
    {
        return TwilightForestMod.ID + ":mob.raven.caw";
    }
 
	@Override
    protected String getHurtSound()
    {
        return TwilightForestMod.ID + ":mob.raven.squawk";
    }

	@Override
    protected String getDeathSound()
    {
        return TwilightForestMod.ID + ":mob.raven.squawk";
    }
	
    @Override
	protected Item getDropItem()
    {
        return TFItems.feather;
    }

	@Override
	public float getRenderSizeModifier() {
		 return 0.3F;
	}

    @Override
	public boolean isSpooked() {
		return this.hurtTime > 0;
	}
    
}
