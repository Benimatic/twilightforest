package twilightforest.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.util.MathHelper;


public class EntityTFRavenLookHelper extends EntityLookHelper
{
    private EntityLiving entity;
    private float field_46149_b;
    private float field_46150_c;
    private boolean field_46147_d = false;
    private float posX;
    private float posY;
    private float posZ;

    public EntityTFRavenLookHelper(EntityLiving par1EntityLiving)
    {
    	super(par1EntityLiving);
        this.entity = par1EntityLiving;
    }

    /**
     * Sets position to look at using entity
     */
    public void setLookPositionWithEntity(Entity par1Entity, float par2, float par3)
    {
        this.posX = (float)par1Entity.posX;

        if (par1Entity instanceof EntityLiving)
        {
            this.posY = (float)par1Entity.posY + ((EntityLiving)par1Entity).getEyeHeight();
        }
        else
        {
            this.posY = (float)(par1Entity.boundingBox.minY + par1Entity.boundingBox.maxY) / 2.0F;
        }

        this.posZ = (float)par1Entity.posZ;
        this.field_46149_b = par2;
        this.field_46150_c = par3;
        this.field_46147_d = true;
    }

    /**
     * Sets position to look at
     */
    @Override
	public void setLookPosition(double par1, double par3, double par5, float par7, float par8)
    {
        this.posX = (float)par1;
        this.posY = (float)par3;
        this.posZ = (float)par5;
        this.field_46149_b = par7;
        this.field_46150_c = par8;
        this.field_46147_d = true;
    }

    /**
     * Updates look
     */
    @Override
	public void onUpdateLook()
    {
        this.entity.rotationPitch = 0.0F;

        if (this.field_46147_d)
        {
            this.field_46147_d = false;
            float var1 = this.posX - (float)this.entity.posX;
            float var3 = this.posY - (float)(this.entity.posY + this.entity.getEyeHeight());
            float var5 = this.posZ - (float)this.entity.posZ;
            float var7 = MathHelper.sqrt_float(var1 * var1 + var5 * var5);
            float var9 = (org.bogdang.modifications.math.TrigMath2.atan2(var5, var1) * 180.0F / (float)Math.PI) - 30.0F;
            float var10 = (-(org.bogdang.modifications.math.TrigMath2.atan2(var3, var7) * 180.0F / (float)Math.PI));
            this.entity.rotationPitch = this.updateRotation(this.entity.rotationPitch, var10, this.field_46150_c);
            this.entity.rotationYawHead = this.updateRotation(this.entity.rotationYawHead, var9, this.field_46149_b);
        }
        else
        {
            this.entity.rotationYawHead = this.updateRotation(this.entity.rotationYawHead, this.entity.renderYawOffset, 10.0F);
        }

        float var11;

        for (var11 = this.entity.rotationYawHead - this.entity.renderYawOffset; var11 < -180.0F; var11 += 360.0F)
        {
            ;
        }

        while (var11 >= 180.0F)
        {
            var11 -= 360.0F;
        }

        if (!this.entity.getNavigator().noPath())
        {
            if (var11 < -75.0F)
            {
                this.entity.rotationYawHead = this.entity.renderYawOffset - 75.0F;
            }

            if (var11 > 75.0F)
            {
                this.entity.rotationYawHead = this.entity.renderYawOffset + 75.0F;
            }
        }
    }

    private float updateRotation(float par1, float par2, float par3)
    {
        float var4;

        for (var4 = par2 - par1; var4 < -180.0F; var4 += 360.0F)
        {
            ;
        }

        while (var4 >= 180.0F)
        {
            var4 -= 360.0F;
        }

        if (var4 > par3)
        {
            var4 = par3;
        }

        if (var4 < -par3)
        {
            var4 = -par3;
        }

        return par1 + var4;
    }
}
