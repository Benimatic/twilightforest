package twilightforest.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ParticleLargeFlame extends SpriteTexturedParticle {

	private float flameScale;

	public ParticleLargeFlame(World world, double x, double y, double z, double vx, double vy, double vz) {
		super(world, x, y, z, vx, vy, vz);
		this.motionX = this.motionX * 0.009999999776482582D + vx;
		this.motionY = this.motionY * 0.009999999776482582D + vy;
		this.motionZ = this.motionZ * 0.009999999776482582D + vz;
		this.particleScale *= 5.0D;
		this.flameScale = this.particleScale;
		this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
		this.maxAge = (int) (8.0D / (Math.random() * 0.8D + 0.2D)) + 4;
		//this.noClip = true;
		//this.setParticleTextureIndex(48); TODO: basically copy the flame particle json?
	}

	@Override
	public IParticleRenderType getRenderType() {
		return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	@Override
	public void renderParticle(BufferBuilder buffer, ActiveRenderInfo entity, float partialTicks,
							   float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {

		float var8 = ((float) this.age + partialTicks) / (float) this.maxAge;
		this.particleScale = this.flameScale * (1.0F - var8 * var8 * 0.5F);
		super.renderParticle(buffer, entity, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
	}

	@Override
	public int getBrightnessForRender(float partialTicks) {
		float var2 = ((float) this.age + partialTicks) / (float) this.maxAge;

		if (var2 < 0.0F) {
			var2 = 0.0F;
		}

		if (var2 > 1.0F) {
			var2 = 1.0F;
		}

		int var3 = super.getBrightnessForRender(partialTicks);
		int var4 = var3 & 255;
		int var5 = var3 >> 16 & 255;
		var4 += (int) (var2 * 15.0F * 16.0F);

		if (var4 > 240) {
			var4 = 240;
		}

		return var4 | var5 << 16;
	}

	@Override
	public void tick() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		if (this.age++ >= this.maxAge) {
			this.setExpired();
		}

		this.motionY += 0.004D;

		this.move(this.motionX, this.motionY, this.motionZ);
		this.motionX *= 0.9599999785423279D;
		this.motionY *= 0.9599999785423279D;
		this.motionZ *= 0.9599999785423279D;

		if (this.onGround) {
			this.motionX *= 0.699999988079071D;
			this.motionZ *= 0.699999988079071D;
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
			ParticleLargeFlame particle = new ParticleLargeFlame(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
			particle.selectSpriteRandomly(this.spriteSet);
			return particle;
		}
	}
}
