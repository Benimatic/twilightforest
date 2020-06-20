package twilightforest.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ParticleGhastTrap extends SpriteTexturedParticle {

	private final float reddustParticleScale;

	private final double originX;
	private final double originY;
	private final double originZ;

	ParticleGhastTrap(World world, double x, double y, double z, double vx, double vy, double vz) {
		this(world, x, y, z, 3.0F, vx, vy, vz);
	}

	ParticleGhastTrap(World world, double x, double y, double z, float scale, double mx, double my, double mz) {
		super(world, x + mx, y + my, z + mz, mx, my, mz);
		this.motionX = mx;
		this.motionY = my;
		this.motionZ = mz;

		this.originX = x;
		this.originY = y;
		this.originZ = z;
		float brightness = (float) Math.random() * 0.4F;// + 0.6F;
		this.particleRed = 1.0F;
		this.particleGreen = ((float) (Math.random() * 0.20000000298023224D) + 0.8F) * brightness;
		this.particleBlue  = ((float) (Math.random() * 0.20000000298023224D) + 0.8F) * brightness;
		this.particleScale *= 0.75F * scale;
		this.reddustParticleScale = this.particleScale;
		this.maxAge = (int) (10.0D / (Math.random() * 0.8D + 0.2D));
		this.canCollide = true;
	}

	@Override
	public IParticleRenderType getRenderType() {
		return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	@Override
	public float getScale(float partialTicks) {
		float f6 = ((float) this.age + partialTicks) / (float) this.maxAge * 32.0F;
		f6 = MathHelper.clamp(f6, 0f, 1f);

		return this.reddustParticleScale * f6;
	}

	@Override
	public void tick() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		float proportion = (float) this.age / (float) this.maxAge;
		proportion = 1.0F - proportion;
//        float antiProportion = 1.0F - proportion;
//        antiProportion *= antiProportion;
//        antiProportion *= antiProportion;
		this.posX = this.originX + this.motionX * (double) proportion;
		this.posY = this.originY + this.motionY * (double) proportion;// - (double)(antiProportion * 1.2F);
		this.posZ = this.originZ + this.motionZ * (double) proportion;

		if (this.age++ >= this.maxAge) {
			this.setExpired();
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static class Factory implements IParticleFactory<BasicParticleType> {
		private final IAnimatedSprite spriteSet;

		public Factory(IAnimatedSprite sprite) {
			this.spriteSet = sprite;
		}

		@Override
		public Particle makeParticle(BasicParticleType typeIn, World worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			ParticleGhastTrap particle = new ParticleGhastTrap(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
			particle.selectSpriteWithAge(this.spriteSet);
			return particle;
		}
	}
}
