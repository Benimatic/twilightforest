package twilightforest.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.MobEffects;
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

	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6);
    }

    @Override
    public boolean attackEntityAsMob(Entity par1Entity)
    {
        if (super.attackEntityAsMob(par1Entity))
        {
            float myBrightness = this.getBrightness(1.0F);
            
            //System.out.println("Biting and brightness is " + myBrightness);

            if (par1Entity instanceof EntityLivingBase && myBrightness < 0.10F)
            {
                byte effectDuration = 0;

                if (this.worldObj.getDifficulty() != EnumDifficulty.EASY)
                {
                    if (this.worldObj.getDifficulty() == EnumDifficulty.NORMAL)
                    {
                        effectDuration = 7;
                    }
                    else if (this.worldObj.getDifficulty() == EnumDifficulty.HARD)
                    {
                        effectDuration = 15;
                    }
                }

                if (effectDuration > 0)
                {
                    ((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, effectDuration * 20, 0));
                }
            }

            return true;
        }
        else
        {
            return false;
        }
    }
    
    @Override
    protected float getSoundPitch()
    {
        return (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 0.6F;
    }
}
