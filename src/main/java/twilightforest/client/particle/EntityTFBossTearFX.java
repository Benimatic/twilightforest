package twilightforest.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EntityTFBossTearFX extends Particle
{
    public EntityTFBossTearFX(World par1World, double par2, double par4, double par6, Item par8Item)
    {
        super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
        this.particleTexture = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(par8Item);
        this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
        this.particleGravity = Blocks.SNOW.blockParticleGravity * 2F;
        this.particleScale = 16.0F;
        
        this.particleMaxAge = 20 + rand.nextInt(40);
    }

    public EntityTFBossTearFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12, Item par14Item)
    {
        this(par1World, par2, par4, par6, par14Item);
        this.motionX *= 0.10000000149011612D;
        this.motionY *= 0.10000000149011612D;
        this.motionZ *= 0.10000000149011612D;
        this.motionX += par8;
        this.motionY += par10;
        this.motionZ += par12;
    }

    @Override
    public int getFXLayer()
    {
        return 2;
    }

    @Override
    public void onUpdate()
    {
	    super.onUpdate();    
    	
        if (this.isCollided && rand.nextBoolean())
        {
        	//world.playSoundEffect(this.posX, this.posY + 1D, this.posZ, "random.fizz", 2.0F, 2.0F);
            //world.spawnParticle("lava", this.posX, this.posY, this.posZ, 0, 0, 0);
			//world.playAuxSFXAtEntity(null, 2001, (int)this.posX, (int)this.posY + 1, (int)this.posZ, 0);
    		//this.world.playAuxSFXAtEntity((EntityPlayer)null, 2002, (int)this.posX, (int)this.posY, (int)this.posZ, 4037);
        	
        	world.playSound(this.posX, this.posY + 1D, this.posZ, "random.glass", 0.5F, 1.0F, false);

    		for (int var1 = 0; var1 < 50; ++var1)
    		{
    		    double gaussX = rand.nextGaussian() * 0.1D;
    		    double gaussY = rand.nextGaussian() * 0.2D;
    		    double gaussZ = rand.nextGaussian() * 0.1D;
    		    Item popItem = Items.GHAST_TEAR;
    		    
    		    
    		    world.spawnParticle(EnumParticleTypes.ITEM_CRACK, this.posX + rand.nextFloat() - rand.nextFloat(), this.posY + 0.5F, this.posZ + rand.nextFloat(), gaussX, gaussY, gaussZ, Item.getIdFromItem(popItem));
    		}
            this.setExpired();
        }
    }

    // [VanillaCopy] of super, relevant edits noted.
    @Override
    public void renderParticle(VertexBuffer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ)
    {
        float f = (float)this.particleTextureIndexX / 16.0F;
        float f1 = f + 0.0624375F;
        float f2 = (float)this.particleTextureIndexY / 16.0F;
        float f3 = f2 + 0.0624375F;
        float f4 = 0.1F * this.particleScale;

        if (this.particleTexture != null)
        {
            f = this.particleTexture.getInterpolatedU(0); // TF - getMinU -> getInterpolatedU(0)
            f1 = this.particleTexture.getInterpolatedU(16); // TF - getMaxU -> getInterpolatedU(16)
            f2 = this.particleTexture.getInterpolatedV(0); // TF - getMinV -> getInterpolatedV(0)
            f3 = this.particleTexture.getInterpolatedV(16); // TF - getMaxV -> getInterpolatedV(16)
        }

        float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)partialTicks - interpPosX);
        float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)partialTicks - interpPosY);
        float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)partialTicks - interpPosZ);
        int i = this.getBrightnessForRender(partialTicks);
        int j = i >> 16 & 65535;
        int k = i & 65535;
        worldRendererIn.pos((double)(f5 - rotationX * f4 - rotationXY * f4), (double)(f6 - rotationZ * f4), (double)(f7 - rotationYZ * f4 - rotationXZ * f4)).tex((double)f1, (double)f3).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
        worldRendererIn.pos((double)(f5 - rotationX * f4 + rotationXY * f4), (double)(f6 + rotationZ * f4), (double)(f7 - rotationYZ * f4 + rotationXZ * f4)).tex((double)f1, (double)f2).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
        worldRendererIn.pos((double)(f5 + rotationX * f4 + rotationXY * f4), (double)(f6 + rotationZ * f4), (double)(f7 + rotationYZ * f4 + rotationXZ * f4)).tex((double)f, (double)f2).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
        worldRendererIn.pos((double)(f5 + rotationX * f4 - rotationXY * f4), (double)(f6 - rotationZ * f4), (double)(f7 + rotationYZ * f4 - rotationXZ * f4)).tex((double)f, (double)f3).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
    }

}
