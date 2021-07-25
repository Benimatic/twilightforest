package twilightforest.client.particle;

import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import twilightforest.client.particle.data.PinnedFireflyData;

import javax.annotation.Nullable;

/**
 * Version of {@link FireflyParticle} where location is pinned to an entity
 */
public class PinnedFireflyParticle extends FireflyParticle {
	private final Entity follow;

	public PinnedFireflyParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, Entity e) {
		super(world, x, y, z, vx, vy, vz);
		this.follow = e;
	}

	@Override
	public void tick() {
		super.tick();
		xo = x;
		yo = y;
		zo = z;
		setPos(follow.getX(), follow.getY(), follow.getZ());
	}

	public static class Factory implements ParticleProvider<PinnedFireflyData> {
		private final SpriteSet sprite;
		public Factory(SpriteSet sprite) {
			this.sprite = sprite;
		}

		@Nullable
		@Override
		public Particle createParticle(PinnedFireflyData data, ClientLevel world, double x, double y, double z, double vx, double vy, double vz) {
			Entity e = world.getEntity(data.follow);
			if (e == null) {
				return null;
			} else {
				PinnedFireflyParticle ret = new PinnedFireflyParticle(world, x, y, z, vx, vy, vz, e);
				ret.pickSprite(sprite);
				return ret;
			}
		}
	}
}
