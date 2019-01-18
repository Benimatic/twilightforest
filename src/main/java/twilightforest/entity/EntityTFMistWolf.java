package twilightforest.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityTFMistWolf extends EntityTFHostileWolf {

	public EntityTFMistWolf(World world) {
		super(world);
        this.setSize(1.4F, 1.9F);
        
        //this.texture = TwilightForestMod.MODEL_DIR + "mistwolf.png";
	}

	/**
	 * Set monster attributes
	 */
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D*2.5+twilightforest.TwilightForestMod.Scatter.nextInt(15)-twilightforest.TwilightForestMod.Scatter.nextInt(15)); // max health
    }

    /**
     * Returns the amount of damage a mob should deal.
     */
    public int getAttackStrength(Entity par1Entity)
    {
        return 6;
    }
    
    public boolean attackEntityAsMob(Entity par1Entity)
    {
    	int damage = this.getAttackStrength(par1Entity);
        if (par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), damage))
        {
            float myBrightness = this.getBrightness(1.0F);
            
            //System.out.println("Biting and brightness is " + myBrightness);

            if (par1Entity instanceof EntityLivingBase && myBrightness < 0.10F)
            {
                byte effectDuration = 0;

                if (this.worldObj.difficultySetting != EnumDifficulty.EASY)
                {
                    if (this.worldObj.difficultySetting == EnumDifficulty.NORMAL)
                    {
                        effectDuration = 7;
                    }
                    else if (this.worldObj.difficultySetting == EnumDifficulty.HARD)
                    {
                        effectDuration = 15;
                    }
                }

                if (effectDuration > 0)
                {
                    ((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(Potion.blindness.id, (int)(effectDuration * 20 * 1.5), 0));
                }
            }

            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Gets the pitch of living sounds in living entities.
     */
    protected float getSoundPitch()
    {
        return (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 0.6F;
    }
}
