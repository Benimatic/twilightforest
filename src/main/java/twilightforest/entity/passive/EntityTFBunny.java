package twilightforest.entity.passive;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import twilightforest.TFAchievementPage;


public class EntityTFBunny extends EntityCreature implements IAnimals {

	public EntityTFBunny(World par1World) {
		super(par1World);
		//texture = TwilightForestMod.MODEL_DIR + "bunnydutch.png";
		
        this.setSize(0.3F, 0.7F);
		
		// maybe this will help them move cuter?
		this.stepHeight = 1;
		
		// squirrel AI
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 2.0F));
        this.tasks.addTask(2, new EntityAITempt(this, 1.0F, Items.wheat_seeds, true));
        this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityPlayer.class, 2.0F, 0.8F, 1.33F));
        this.tasks.addTask(5, new EntityAIWander(this, 0.8F));
        this.tasks.addTask(6, new EntityAIWander(this, 1.0F));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6F));
        this.tasks.addTask(8, new EntityAILookIdle(this));

        // random color
        setBunnyType(rand.nextInt(4));

	}


	/**
	 * Set monster attributes
	 */
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(3.0D); // max health
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
    }
	
	@Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(16, (byte)0);
    }

	/**
	 * Returns true if the newer Entity AI code should be run
	 */
	@Override
	public boolean isAIEnabled() {
	    return true;
	}
	
//    /**
//     * Returns the texture's file path as a String.
//     */
//	@Override
//    public String getTexture()
//    {
//        switch (this.getBunnyType())
//        {
//            case 0:
//                return TwilightForestMod.MODEL_DIR + "bunnydutch.png";
//
//            case 1:
//                return TwilightForestMod.MODEL_DIR + "bunnydutch.png";
//
//            case 2:
//                return TwilightForestMod.MODEL_DIR + "bunnywhite.png";
//
//            case 3:
//                return TwilightForestMod.MODEL_DIR + "bunnybrown.png";
//
//            default:
//                return super.getTexture();
//        }
//    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("BunnyType", this.getBunnyType());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
	@Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.setBunnyType(par1NBTTagCompound.getInteger("BunnyType"));
    }

    public int getBunnyType()
    {
        return this.dataWatcher.getWatchableObjectByte(16);
    }

    public void setBunnyType(int par1)
    {
        this.dataWatcher.updateObject(16, ((byte)par1));
    }
	

	/**
	 * Actually only used for the shadow
	 */
	@Override
	public float getRenderSizeModifier() {
		 return 0.3F;
	}
	
	/**
     * Determines if an entity can be despawned, used on idle far away entities
     */
	@Override
    protected boolean canDespawn()
    {
        return false;
    }
	
	
    /**
     * Takes a coordinate in and returns a weight to determine how likely this creature will try to path to the block.
     * Args: x, y, z
     */
	@Override
    public float getBlockPathWeight(int par1, int par2, int par3)
    {
    	// avoid leaves & wood
		Material underMaterial = this.worldObj.getBlock(par1, par2 - 1, par3).getMaterial();
		if (underMaterial == Material.leaves) {
			return -1.0F;
		}
		if (underMaterial == Material.wood) {
			return -1.0F;
		}
		if (underMaterial == Material.grass) {
			return 10.0F;
		}
		// default to just prefering lighter areas
		return this.worldObj.getLightBrightness(par1, par2, par3) - 0.5F;
    }
	
    /**
     * Trigger achievement when killed
     */
	@Override
	public void onDeath(DamageSource par1DamageSource) {
		super.onDeath(par1DamageSource);
		if (par1DamageSource.getSourceOfDamage() instanceof EntityPlayer) {
			((EntityPlayer)par1DamageSource.getSourceOfDamage()).triggerAchievement(TFAchievementPage.twilightHunter);
		}
	}
}
