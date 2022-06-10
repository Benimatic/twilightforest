package twilightforest.client.particle;

import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SnowWarningParticle extends SnowParticle {

	SnowWarningParticle(ClientLevel level, double x, double y, double z, double vx, double vy, double vz, float scale) {
		super(level, x, y, z, vx, vy, vz, scale);
		this.lifetime = 50;
	}

	@Override
	public void tick() {
		super.tick();
		this.yd -= 0.02D;
	}

	@OnlyIn(Dist.CLIENT)
	public record Factory(SpriteSet sprite) implements ParticleProvider<SimpleParticleType> {

		@Override
		public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			SnowWarningParticle particle = new SnowWarningParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, 1);
			particle.pickSprite(this.sprite);
			return particle;
		}
	}
}
