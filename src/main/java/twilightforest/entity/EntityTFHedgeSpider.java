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

	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		// todo 1.9 need to replace player target task with the normal one that doesnt turn docile in light
	}

	@Override
    protected boolean isValidLightLevel() {
		int chunkX = MathHelper.floor(posX) >> 4;
		int chunkZ = MathHelper.floor(posZ) >> 4;
		// We're allowed to spawn in bright light only in hedge mazes.
		return TFFeature.getNearestFeature(chunkX, chunkZ, world) == TFFeature.hedgeMaze
				|| super.isValidLightLevel();
	}
	
	@Override
	public void onDeath(DamageSource par1DamageSource) {
		super.onDeath(par1DamageSource);
		if (par1DamageSource.getSourceOfDamage() instanceof EntityPlayer) {
			((EntityPlayer)par1DamageSource.getSourceOfDamage()).addStat(TFAchievementPage.twilightHunter);
			// are in a hedge maze?
			int chunkX = MathHelper.floor(posX) >> 4;
			int chunkZ = MathHelper.floor(posZ) >> 4;
			if (TFFeature.getNearestFeature(chunkX, chunkZ, world) == TFFeature.hedgeMaze) {
				// award hedge maze cheevo
				((EntityPlayer)par1DamageSource.getSourceOfDamage()).addStat(TFAchievementPage.twilightHedge);
			}
		}
	}
}
