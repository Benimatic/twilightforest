package twilightforest.entity.passive;

import java.util.Random;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import twilightforest.TFAchievementPage;



public class EntityTFBighorn extends EntitySheep
{

    public EntityTFBighorn(World world)
    {
        super(world);
        //texture = TwilightForestMod.MODEL_DIR + "bighorn.png";
        setSize(0.9F, 1.3F);
    }
    
    public EntityTFBighorn(World world, double x, double y, double z)
    {
        this(world);
        this.setPosition(x, y, z);
    }

    /**
     * 50% brown, 50% any other color
     * 
     * @param random
     * @return
     */
    public static int getRandomFleeceColor(Random random)
    {
    	if (random.nextInt(2) == 0)
    	{
    		return 12;
    	}
    	else
    	{
        	return random.nextInt(15);
    	}
    }
    

    /**
     * Entity init, set our fleece color
     */
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData)
    {
        par1EntityLivingData = super.onSpawnWithEgg(par1EntityLivingData);
        this.setFleeceColor(getRandomFleeceColor(this.worldObj.rand));
        return par1EntityLivingData;
    }
    
    /**
     * What is our baby?!
     */
    @Override
	public EntitySheep createChild(EntityAgeable entityanimal)
    {
    	EntityTFBighorn otherParent = (EntityTFBighorn)entityanimal;
    	EntityTFBighorn babySheep = new EntityTFBighorn(worldObj);
        if(rand.nextBoolean())
        {
            babySheep.setFleeceColor(getFleeceColor());
        } else
        {
            babySheep.setFleeceColor(otherParent.getFleeceColor());
        }
        return babySheep;
    }

    /**
     * Trigger achievement when killed
     */
	@Override
	public void onDeath(DamageSource par1DamageSource) {
		super.onDeath(par1DamageSource);
		if (par1DamageSource.getSourceOfDamage() instanceof EntityPlayer) {
			((EntityPlayer)par1DamageSource.getSourceOfDamage()).addStat(TFAchievementPage.twilightHunter);
		}
	}

}
