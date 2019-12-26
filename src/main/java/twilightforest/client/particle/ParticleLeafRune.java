package twilightforest.client.particle;

import net.minecraft.client.particle.EnchantmentTableParticle;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ParticleLeafRune extends EnchantmentTableParticle {

	public ParticleLeafRune(World world, double x, double y, double z, double velX, double velY, double velZ) {
		//TODO: Private constructor
		super(world, x, y, z, velX, velY, velZ);

		this.particleScale = this.rand.nextFloat() + 1F;
		this.maxAge += 10;
		this.particleGravity = 0.003F + rand.nextFloat() * 0.006F;


		this.canCollide = true;
	}

	@Override
	public void tick() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		this.move(this.motionX, this.motionY, this.motionZ);
		this.motionY -= (double) this.particleGravity;


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
			ParticleLeafRune particle = new ParticleLeafRune(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
			particle.selectSpriteRandomly(this.spriteSet);
			return particle;
		}
	}
}
