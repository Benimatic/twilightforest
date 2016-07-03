package twilightforest.client.particle;

import net.minecraft.client.particle.EntityAuraFX;
import net.minecraft.client.particle.ParticleSuspendedTown;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityTFProtectionFX extends ParticleSuspendedTown {

	public EntityTFProtectionFX(World world, double x, double y, double z, double velX, double velY, double velZ) {
		super(world, x, y, z, velX, velY, velZ);
        this.setParticleTextureIndex(82);
        this.setRBGColorF(1.0F, 1.0F, 1.0F);
	}
    
    /**
     * Gets how bright this entity is.
     */
    public float getBrightness(float par1)
    {
        return 1.0F;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getBrightnessForRender(float par1)
    {
        return 15728880;
    }
}
