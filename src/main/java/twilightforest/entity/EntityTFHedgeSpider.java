package twilightforest.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import twilightforest.TFAchievementPage;
import twilightforest.TFFeature;


/**
 * The hedge spider is just like a normal spider, but it can spawn in the daytime.
 * 
 * @author Ben
 *
 */
public class EntityTFHedgeSpider extends EntitySpider {

	public EntityTFHedgeSpider(World world) {
		super(world);
		//texture = TwilightForestMod.MODEL_DIR + "hedgespider.png";
	}

    public EntityTFHedgeSpider(World world, double x, double y, double z)
    {
        this(world);
        this.setPosition(x, y, z);
    }
    
    /**
     * Finds the closest player within 16 blocks to attack, or null if this Entity isn't interested in attacking
     * (Animals, Spiders at day, peaceful PigZombies).
     */
    @Override
	protected Entity findPlayerToAttack()
    {
    	// kill at all times!
    	double var2 = 16.0D;
    	return this.worldObj.getClosestVulnerablePlayerToEntity(this, var2);
    }
	
	/**
     * Checks to make sure the light is not too bright where the mob is spawning
	 */
	@Override
    protected boolean isValidLightLevel()
    {
		int chunkX = MathHelper.floor_double(posX) >> 4;
		int chunkZ = MathHelper.floor_double(posZ) >> 4;
		// We're allowed to spawn in bright light only in hedge mazes.
		if (TFFeature.getNearestFeature(chunkX, chunkZ, worldObj) == TFFeature.hedgeMaze) 
		{
			return true;
		}
		else
		{
			return super.isValidLightLevel();
		}
    }
	
    /**
     * Trigger achievement when killed
     */
	@Override
	public void onDeath(DamageSource par1DamageSource) {
		super.onDeath(par1DamageSource);
		if (par1DamageSource.getSourceOfDamage() instanceof EntityPlayer) {
			((EntityPlayer)par1DamageSource.getSourceOfDamage()).addStat(TFAchievementPage.twilightHunter);
			// are in a hedge maze?
			int chunkX = MathHelper.floor_double(posX) >> 4;
			int chunkZ = MathHelper.floor_double(posZ) >> 4;
			if (TFFeature.getNearestFeature(chunkX, chunkZ, worldObj) == TFFeature.hedgeMaze) {
				// award hedge maze cheevo
				((EntityPlayer)par1DamageSource.getSourceOfDamage()).addStat(TFAchievementPage.twilightHedge);
			}
		}
	}
}
