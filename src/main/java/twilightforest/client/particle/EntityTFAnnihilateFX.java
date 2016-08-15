package twilightforest.client.particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;
import twilightforest.item.ItemTFCubeOfAnnihilation;
import twilightforest.item.ItemTFIceBomb;
import twilightforest.item.TFItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EntityTFAnnihilateFX extends EntityFX
{
    float initialParticleScale;

    public EntityTFAnnihilateFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
    {
        this(par1World, par2, par4, par6, par8, par10, par12, 1.0F);
    }

    public EntityTFAnnihilateFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12, float par14)
    {
        super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
        this.motionX *= 0.10000000149011612D;
        this.motionY *= 0.10000000149011612D;
        this.motionZ *= 0.10000000149011612D;
        this.motionX += par8 * 0.4D;
        this.motionY += par10 * 0.4D;
        this.motionZ += par12 * 0.4D;
        this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
        this.particleScale *= 0.75F;
        this.particleScale *= par14;
        this.initialParticleScale = this.particleScale;
        this.particleMaxAge = (int)(60.0D / ((new org.bogdang.modifications.random.XSTR()).nextFloat() * 0.8D + 0.6D));
        this.particleMaxAge = (int)((float)this.particleMaxAge * par14);
        this.noClip = false;
        
        this.setParticleIcon(((ItemTFCubeOfAnnihilation)TFItems.cubeOfAnnihilation).getAnnihilateIcon());
        
        this.onUpdate();
    }

    public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.setDead();
        }

        this.moveEntity(this.motionX, this.motionY, this.motionZ);

        this.motionX *= 0.9599999785423279D;
        this.motionY *= 0.9599999785423279D;
        this.motionZ *= 0.9599999785423279D;
        //this.motionY -= 0.019999999552965164D;

        if (this.onGround)
        {
            this.motionX *= 0.699999988079071D;
            this.motionZ *= 0.699999988079071D;
        }
        
        this.particleScale *= 0.97D;
        
        if (this.particleScale < 0.4D) {
        	this.setDead();
        }
        
        float blacken = 0.985F;
        
        this.particleRed *= blacken;
        this.particleGreen *= blacken;
        this.particleBlue *= blacken;

    }
    
    public int getBrightnessForRender(float par1)
    {
    	return 240 | 240 << 16;
    }
    
    public int getFXLayer()
    {
        return 2;
    }
}