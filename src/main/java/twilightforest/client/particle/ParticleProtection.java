package twilightforest.client.particle;

import net.minecraft.client.particle.SuspendedTownParticle;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ParticleProtection extends SuspendedTownParticle {

	public ParticleProtection(World world, double x, double y, double z, double velX, double velY, double velZ) {
		super(world, x, y, z, velX, velY, velZ);
		//this.setParticleTextureIndex(82); TODO: probably not needed?
		this.setColor(1.0F, 1.0F, 1.0F);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public int getBrightnessForRender(float partialTicks) {
		return 0xF000F0;
	}
}
