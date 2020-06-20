package twilightforest.client.particle;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Version of {@link ParticleFirefly} where location is pinned to an entity
 */
public class ParticleFireflyPinned extends ParticleFirefly {
	private final Entity follow;

	public ParticleFireflyPinned(World world, double x, double y, double z, double vx, double vy, double vz, Entity e) {
		super(world, x, y, z, vx, vy, vz);
		this.follow = e;
	}

	@Override
	public void tick() {
		super.tick();
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		setPosition(follow.getX(), follow.getY(), follow.getZ());
	}

	public static class Factory implements IParticleFactory<PinnedFireflyData> {
		private final IAnimatedSprite sprite;
		public Factory(IAnimatedSprite sprite) {
			this.sprite = sprite;
		}

		@Nullable
		@Override
		public Particle makeParticle(PinnedFireflyData data, World world, double x, double y, double z, double vx, double vy, double vz) {
			Entity e = world.getEntityByID(data.follow);
			if (e == null) {
				return null;
			} else {
				ParticleFireflyPinned ret = new ParticleFireflyPinned(world, x, y, z, vx, vy, vz, e);
				ret.selectSpriteRandomly(sprite);
				return ret;
			}
		}
	}
}
