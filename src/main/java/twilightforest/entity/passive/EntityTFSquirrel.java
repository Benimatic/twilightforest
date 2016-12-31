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
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import twilightforest.TFAchievementPage;


public class EntityTFSquirrel extends EntityCreature implements IAnimals {

	public EntityTFSquirrel(World par1World) {
		super(par1World);
		
        this.setSize(0.3F, 0.7F);
		
		// maybe this will help them move cuter?
		this.stepHeight = 1;
		
		// squirrel AI
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.38F));
        this.tasks.addTask(2, new EntityAITempt(this, 1.0F, Items.wheat_seeds, true));
        this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityPlayer.class, 2.0F, 0.8F, 1.4F));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0F));
        this.tasks.addTask(6, new EntityAIWander(this, 1.25F));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6F));
        this.tasks.addTask(8, new EntityAILookIdle(this));

	}

	/**
	 * Set monster attributes
	 */
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1.0D+twilightforest.TwilightForestMod.Scatter.nextInt(5)); // max health
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
    }
	
	/**
	 * Called when the mob is falling. Calculates and applies fall damage.
	 * TODO: maybe they should just take less damage?
	 */
	@Override
	protected void fall(float par1) {}

	/**
	 * Returns true if the newer Entity AI code should be run
	 */
	@Override
	public boolean isAIEnabled() {
	    return true;
	}

	/**
	 * Actually only used for the shadow
	 */
	@Override
	public float getRenderSizeModifier() {
		 return 0.3F;
	}
	
    /**
     * Takes a coordinate in and returns a weight to determine how likely this creature will try to path to the block.
     * Args: x, y, z
     */
	@Override
    public float getBlockPathWeight(int par1, int par2, int par3)
    {
    	// prefer standing on leaves
		Material underMaterial = this.worldObj.getBlock(par1, par2 - 1, par3).getMaterial();
		if (underMaterial == Material.leaves) {
			return 12.0F;
		}
		if (underMaterial == Material.wood) {
			return 15.0F;
		}
		if (underMaterial == Material.grass) {
			return 10.0F;
		}
		// default to just prefering lighter areas
		return this.worldObj.getLightBrightness(par1, par2, par3) - 0.5F;
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
