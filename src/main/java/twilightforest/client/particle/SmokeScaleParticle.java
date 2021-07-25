package twilightforest.client.particle;

import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SmokeParticle;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SmokeScaleParticle extends SmokeParticle {

	SmokeScaleParticle(ClientLevel world, double x, double y, double z, double velX, double velY, double velZ, float scale, SpriteSet sprite) {
		super(world, x, y, z, velX, velY, velZ, scale, sprite);
	}

	@OnlyIn(Dist.CLIENT)
	public static class Factory implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet spriteSet;

		public Factory(SpriteSet sprite) {
			this.spriteSet = sprite;
		}

		@Override
		public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			return new SmokeScaleParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, 4.0F, this.spriteSet);
		}
	}
}
