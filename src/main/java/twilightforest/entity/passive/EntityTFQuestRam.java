package twilightforest.entity.passive;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import twilightforest.TFAchievementPage;
import twilightforest.TFFeature;
import twilightforest.entity.ai.EntityAITFEatLoose;
import twilightforest.entity.ai.EntityAITFFindLoose;
import twilightforest.item.TFItems;


public class EntityTFQuestRam extends EntityAnimal {
	
    private int randomTickDivider;


	public EntityTFQuestRam(World par1World) {
		super(par1World);
		//this.texture = TwilightForestMod.MODEL_DIR + "questram.png";
		this.setSize(1.25F, 2.9F);
		this.randomTickDivider = 0;
		

        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.38F));
        this.tasks.addTask(2, new EntityAITempt(this, 1.0F, Item.getItemFromBlock(Blocks.wool), false));
        this.tasks.addTask(3, new EntityAITFEatLoose(this, Item.getItemFromBlock(Blocks.wool)));
        this.tasks.addTask(4, new EntityAITFFindLoose(this, 1.0F, Item.getItemFromBlock(Blocks.wool)));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0F));
//        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
	}

	@Override
	public EntityAnimal createChild(EntityAgeable entityanimal)
	{
		return null;
	}

	/**
	 * Set monster attributes
	 */
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(70.0D); // max health
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
    }
	
    @Override
	protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(16, (int)0);
        this.dataWatcher.addObject(17, (byte)0);
    }


    /**
     * Returns true if the newer Entity AI code should be run
     */
    @Override
	public boolean isAIEnabled()
    {
        return true;
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
     * main AI tick function, replaces updateEntityActionState
     */
    @Override
	protected void updateAITick()
    {
        if (--this.randomTickDivider <= 0)
        {
            this.randomTickDivider = 70 + this.rand.nextInt(50);
            
            // check if we're near a quest grove and if so, set that as home
            int chunkX = MathHelper.floor_double(this.posX) / 16;
            int chunkZ = MathHelper.floor_double(this.posZ) / 16;
            
            TFFeature nearFeature = TFFeature.getNearestFeature(chunkX, chunkZ, this.worldObj);

            if (nearFeature != TFFeature.questGrove)
            {
                this.detachHome();
            }
            else
            {
            	// set our home position to the center of the quest grove
                ChunkCoordinates cc = TFFeature.getNearestCenterXYZ(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posZ), worldObj);
                this.setHomeArea(cc.posX, cc.posY, cc.posZ, 13);
                
                //System.out.println("Set home area to " + cc.posX + ", " + cc.posY + ", " + cc.posZ);
            }
            
            // do we need to reward the player?
            if (countColorsSet() > 15 && !getRewarded()) {
            	rewardQuest();
            	setRewarded(true);
            }
            
        }

        super.updateAITick();
    }
    
    /**
     * Pay out!
     */
    private void rewardQuest() {
    	func_145778_a(Item.getItemFromBlock(Blocks.diamond_block), 1, 1.0F);
    	func_145778_a(Item.getItemFromBlock(Blocks.iron_block), 1, 1.0F);
    	func_145778_a(Item.getItemFromBlock(Blocks.emerald_block), 1, 1.0F);
    	func_145778_a(Item.getItemFromBlock(Blocks.gold_block), 1, 1.0F);
    	func_145778_a(Item.getItemFromBlock(Blocks.lapis_block), 1, 1.0F);
    	func_145778_a(TFItems.crumbleHorn, 1, 1.0F);
    	
    	rewardNearbyPlayers(this.worldObj, this.posX, this.posY, this.posZ);
	}

    /**
     * Give achievement to nearby players
     */
    @SuppressWarnings("unchecked")
	private void rewardNearbyPlayers(World world, double posX, double posY, double posZ) {
		// scan for players nearby to give the achievement
		List<EntityPlayer> nearbyPlayers = world.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(posX, posY, posZ, posX + 1, posY + 1, posZ + 1).expand(16.0D, 16.0D, 16.0D));
		
		for (EntityPlayer player : nearbyPlayers) {
			player.triggerAchievement(TFAchievementPage.twilightQuestRam);
		}
	}
    
	/**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    @Override
	public boolean interact(EntityPlayer par1EntityPlayer)
    {
        ItemStack currentItem = par1EntityPlayer.inventory.getCurrentItem();

        if (currentItem != null && currentItem.getItem() == Item.getItemFromBlock(Blocks.wool) && !isColorPresent(currentItem.getItemDamage()))
        {
//            par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, new ItemStack(Items.bucketMilk));
        	this.setColorPresent(currentItem.getItemDamage());
        	this.animateAddColor(currentItem.getItemDamage(), 50);
        	
            if (!par1EntityPlayer.capabilities.isCreativeMode)
            {
                --currentItem.stackSize;

                if (currentItem.stackSize <= 0)
                {
                    par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
                }
            }
        	
        	//par1EntityPlayer.addChatMessage("Successfully used color " + currentItem.getItemDamage());
        	//par1EntityPlayer.addChatMessage("Color flags are now " + Integer.toBinaryString(this.getColorFlags()));
            return true;
        }
        else
        {
            return super.interact(par1EntityPlayer);
        }
    }
    
	
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
	public void onLivingUpdate()
    {
    	super.onLivingUpdate();

//        for (int var1 = 0; var1 < 2; ++var1)
//        {
//            this.worldObj.spawnParticle("mobSpell", this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.44, 0.625, this.rand.nextDouble());
//        }
    	checkAndAnimateColors();
    }
    

    /**
     * Called every tick.  If we have got all the colors and have not paid out the reward yet, do a colorful animation.
     */
    public void checkAndAnimateColors() {
		if (countColorsSet() > 15 && !getRewarded()) {
			animateAddColor(this.rand.nextInt(16), 5);
		}
	}

	/**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    @Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("ColorFlags", this.getColorFlags());
        par1NBTTagCompound.setBoolean("Rewarded", this.getRewarded());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.setColorFlags(par1NBTTagCompound.getInteger("ColorFlags"));
        this.setRewarded(par1NBTTagCompound.getBoolean("Rewarded"));
    }
    
    /**
     * Returns an int representing which of the 16 colors this ram currently has
     */
    public int getColorFlags()
    {
        return this.dataWatcher.getWatchableObjectInt(16);
    }

    /**
     * Sets the color flags int
     */
    public void setColorFlags(int par1)
    {
    	this.dataWatcher.updateObject(16, par1);
    }

    /**
     * @param color
     * @return true if the specified color is marked present
     */
    public boolean isColorPresent(int color) {
    	int flags = this.getColorFlags();
    	
    	return (flags & (int)Math.pow(2, color)) > 0;
    }
    
    public void setColorPresent(int color) {
    	int flags = this.getColorFlags();
    	
//    	System.out.println("Setting color flag for color " + color);
//    	System.out.println("Color int is " + flags);
//    	System.out.println("ORing that with " + Math.pow(2, color) + " which is " + Integer.toBinaryString((int) Math.pow(2, color)));
    	
    	setColorFlags(flags | (int)Math.pow(2, color));
    }
    
    /**
     * Have we paid out the reward yet?
     */
    public boolean getRewarded()
    {
        return this.dataWatcher.getWatchableObjectByte(17) != (byte)0;
    }

    /**
     * Sets whether we have paid the quest reward yet
     */
    public void setRewarded(boolean par1)
    {
    	this.dataWatcher.updateObject(17, par1 ? ((byte)1) : (byte)0);
    }


    
    /**
     * Do a little animation for when we successfully add a new color
     * @param color
     */
    public void animateAddColor(int color, int iterations) {
    	//EntitySheep.fleeceColorTable[i][0]
    	
    	for (int i = 0; i < iterations; i++) {
          this.worldObj.spawnParticle("mobSpell", this.posX + (this.rand.nextDouble() - 0.5D) * this.width * 1.5, this.posY + this.rand.nextDouble() * this.height * 1.5, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width * 1.5, EntitySheep.fleeceColorTable[color][0], EntitySheep.fleeceColorTable[color][1], EntitySheep.fleeceColorTable[color][2]);
    	}
    	
    	//TODO: it would be nice to play a custom sound
    	playLivingSound();
    }
    
    /**
     * 
     * @return how many colors are present currently
     */
    public int countColorsSet() {
    	int count = 0;
    	
    	for (int i = 0; i < 16; i++) {
    		if (isColorPresent(i)) {
    			count++;
    		}
    	}
    	
    	return count;
    }
    


    
    /**
     * Plays living's sound at its position
     */
    @Override
	public void playLivingSound()
    {
        this.worldObj.playSoundAtEntity(this, "mob.sheep.say", this.getSoundVolume(), this.getSoundPitch());
    }
    
    /**
     * Returns the volume for the sounds this mob makes.
     */
    @Override
	protected float getSoundVolume()
    {
        return 5.0F;
    }
    
    /**
     * Gets the pitch of living sounds in living entities.
     */
    @Override
	protected float getSoundPitch()
    {
        return (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 0.7F;
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return "mob.sheep.say";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "mob.sheep.say";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "mob.sheep.say";
    }

    protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
    {
        this.playSound("mob.sheep.step", 0.15F, 1.0F);
    }

    public float getMaximumHomeDistance()
    {
        return this.func_110174_bM();
    }
}
