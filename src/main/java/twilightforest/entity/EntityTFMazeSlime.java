package twilightforest.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import twilightforest.block.TFBlocks;
import twilightforest.item.TFItems;

public class EntityTFMazeSlime extends EntitySlime
{

	private String slimeParticleString;

	public EntityTFMazeSlime(World par1World) {
		super(par1World);
        //texture = TwilightForestMod.MODEL_DIR + "mazeslime.png";
        this.setSlimeSize(1 << (1 + this.rand.nextInt(2)));
	}

	/**
	 * Make more of meeee
	 */
    @Override
	protected EntitySlime createInstance()
    {
        return new EntityTFMazeSlime(this.worldObj);
    }
    
    @Override
	public void setSlimeSize(int par1)
    {
        super.setSlimeSize(par1);
        this.experienceValue = par1 + 3;
    }
    
    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    @Override
	public boolean getCanSpawnHere()
    {
    	return this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL && this.worldObj.checkNoEntityCollision(this.boundingBox) 
        		&& this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() 
        		&& !this.worldObj.isAnyLiquid(this.boundingBox) && this.isValidLightLevel();
    }
    
	/**
	 * Set monster attributes
	 */
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        int size = this.getSlimeSize();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(2.0D * size * size); // max health
    }
    
    /**
     * Indicates weather the slime is able to damage the player (based upon the slime's size)
     */
    @Override
	protected boolean canDamagePlayer()
    {
        return true;
    }

    /**
     * Gets the amount of damage dealt to the player when "attacked" by the slime.
     */
    @Override
	protected int getAttackStrength()
    {
        return super.getAttackStrength() + 3;
    }


    /**
     * Returns the name of a particle effect that may be randomly created by EntitySlime.onUpdate()
     */
    @Override
	protected String getSlimeParticle()
    {
    	if (slimeParticleString == null)
    	{
    		slimeParticleString = "blockcrack_" + Block.getIdFromBlock(TFBlocks.mazestone) + "_1";
    	}
    	
        return slimeParticleString;
    }

    /**
     * Checks to make sure the light is not too bright where the mob is spawning
     */
    protected boolean isValidLightLevel()
    {
        int var1 = MathHelper.floor_double(this.posX);
        int var2 = MathHelper.floor_double(this.boundingBox.minY);
        int var3 = MathHelper.floor_double(this.posZ);

        if (this.worldObj.getSavedLightValue(EnumSkyBlock.Sky, var1, var2, var3) > this.rand.nextInt(32))
        {
            return false;
        }
        else
        {
            int var4 = this.worldObj.getBlockLightValue(var1, var2, var3);

            if (this.worldObj.isThundering())
            {
                int var5 = this.worldObj.skylightSubtracted;
                this.worldObj.skylightSubtracted = 10;
                var4 = this.worldObj.getBlockLightValue(var1, var2, var3);
                this.worldObj.skylightSubtracted = var5;
            }

            return var4 <= this.rand.nextInt(8);
        }
    }
    

    /**
     * Returns the volume for the sounds this mob makes.
     */
    @Override
	protected float getSoundVolume()
    {
    	// OH MY GOD, SHUT UP
        return 0.1F * this.getSlimeSize();
    }
    
    
    @Override
	protected void dropRareDrop(int par1)
    {
        this.dropItem(TFItems.charmOfKeeping1, 1);
    }

}
