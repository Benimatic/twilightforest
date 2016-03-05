package twilightforest.entity;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class EntityTFMiniGhast extends EntityTFTowerGhast
{
	public boolean isMinion = false;
	
	public EntityTFMiniGhast(World par1World) {
		super(par1World);
        this.setSize(1.1F, 1.5F);
        //this.texture = TwilightForestMod.MODEL_DIR + "towerghast.png";

        this.aggroRange = 16.0F;
    	this.stareRange = 8.0F;
    	
    	this.wanderFactor = 4.0F;
    	
	}

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int getMaxSpawnedInChunk()
    {
        return 16;
    }
    
	/**
	 * Set monster attributes
	 */
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(this.isMinion ? 6 : 10); // max health
    }
    
    @Override
	public void onUpdate() 
    {
		super.onUpdate();
        //byte aggroStatus = this.dataWatcher.getWatchableObjectByte(16);

//        switch (aggroStatus)
//        {
//        case 0:
//        default:
//        	this.texture = TwilightForestMod.MODEL_DIR + "towerghast.png";
//        	break;
//        case 1:
//        	this.texture = TwilightForestMod.MODEL_DIR + "towerghast_openeyes.png";
//        	break;
//        case 2:
//        	this.texture = TwilightForestMod.MODEL_DIR + "towerghast_fire.png";
//        	break;
//        }
	}

    /**
     * Checks to see if this mini ghast should be attacking this player.
     * 
     * Very similar to endermen code, but we also attack at a certain range
     */
    protected boolean shouldAttackPlayer(EntityPlayer par1EntityPlayer)
    {
        ItemStack playerHeadArmor = par1EntityPlayer.inventory.armorInventory[3];

        if (playerHeadArmor != null && playerHeadArmor.getItem() == Item.getItemFromBlock(Blocks.pumpkin))
        {
            return false;
        }
        else if (par1EntityPlayer.getDistanceToEntity(this) <= 3.5F && par1EntityPlayer.canEntityBeSeen(this))
        {
            return true;
        }
        else
        {
            Vec3 var3 = par1EntityPlayer.getLook(1.0F).normalize();
            Vec3 var4 = Vec3.createVectorHelper(this.posX - par1EntityPlayer.posX, this.boundingBox.minY + (double)(this.height / 2.0F) - (par1EntityPlayer.posY + (double)par1EntityPlayer.getEyeHeight()), this.posZ - par1EntityPlayer.posZ);
            double var5 = var4.lengthVector();
            var4 = var4.normalize();
            double var7 = var3.dotProduct(var4);
            return var7 > 1.0D - 0.025D / var5 ? par1EntityPlayer.canEntityBeSeen(this) : false;
        }
    }
    
    
    /**
     * Checks to make sure the light is not too bright where the mob is spawning
     */
    protected boolean isValidLightLevel()
    {
    	if (isMinion)
    	{
    		return true;
    	}
    	
        int myX = MathHelper.floor_double(this.posX);
        int myY = MathHelper.floor_double(this.boundingBox.minY);
        int myZ = MathHelper.floor_double(this.posZ);

        if (this.worldObj.getSavedLightValue(EnumSkyBlock.Sky, myX, myY, myZ) > this.rand.nextInt(32))
        {
            return false;
        }
        else
        {
            int lightLevel = this.worldObj.getBlockLightValue(myX, myY, myZ);

            if (this.worldObj.isThundering())
            {
                int var5 = this.worldObj.skylightSubtracted;
                this.worldObj.skylightSubtracted = 10;
                lightLevel = this.worldObj.getBlockLightValue(myX, myY, myZ);
                this.worldObj.skylightSubtracted = var5;
            }

            return lightLevel <= this.rand.nextInt(8);
        }
    }

    /**
     * Turn this mini ghast into a boss minion
     */
	public void makeBossMinion() {
		this.wanderFactor = 0.005F;
		this.isMinion = true;
		
		this.setHealth(this.getMaxHealth());
	}
	
    /**
     * Drop 0-2 items of this living's type. @param par1 - Whether this entity has recently been hit by a player. @param
     * par2 - Level of Looting used to kill this mob.
     */
    protected void dropFewItems(boolean par1, int par2)
    {
    	// no loot from minions
        if (!this.isMinion)
        {
        	super.dropFewItems(par1, par2);
        }
    }
	
    @Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setBoolean("isMinion", this.isMinion);
        super.writeEntityToNBT(nbttagcompound);
    }

    @Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        if (nbttagcompound.getBoolean("isMinion"))
        {
        	makeBossMinion();
        }
    }
    
}
