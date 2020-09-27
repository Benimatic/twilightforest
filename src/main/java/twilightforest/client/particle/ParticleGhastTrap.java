package twilightforest.client.particle;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleGhastTrap extends Particle {

	float reddustParticleScale;

	private double originX;
	private double originY;
	private double originZ;

	public ParticleGhastTrap(World world, double x, double y, double z, double vx, double vy, double vz) {
		this(world, x, y, z, 3.0F, vx, vy, vz);
	}

	public ParticleGhastTrap(World world, double x, double y, double z, float scale, double mx, double my, double mz) {
		super(world, x + mx, y + my, z + mz, mx, my, mz);
		this.motionX = mx;
		this.motionY = my;
		this.motionZ = mz;

		this.originX = x;
		this.originY = y;
		this.originZ = z;

		float brightness = (float) Math.random() * 0.4F;// + 0.6F;
		//this.particleRed = ((float) (Math.random() * 0.20000000298023224D) + 0.8F) * brightness;
		this.particleGreen = ((float) (Math.random() * 0.20000000298023224D) + 0.8F) * brightness;
		this.particleBlue  = ((float) (Math.random() * 0.20000000298023224D) + 0.8F) * brightness;

		this.particleRed = 1.0F;

		this.particleScale *= 0.75F;
		this.particleScale *= scale;
		this.reddustParticleScale = this.particleScale;
		this.particleMaxAge = (int) (10.0D / (Math.random() * 0.8D + 0.2D));
		//this.particleMaxAge = (int)((float)this.particleMaxAge * scale);

		this.canCollide = true;
	}

	@Override
	public void renderParticle(BufferBuilder buffer, Entity entity, float partialTicks,
	                           float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {

		float f6 = ((float) this.particleAge + partialTicks) / (float) this.particleMaxAge * 32.0F;
		f6 = MathHelper.clamp(f6, 0f, 1f);

		this.particleScale = this.reddustParticleScale * f6;
		super.renderParticle(buffer, entity, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
	}

//    /**
//     * Called to update the entity's position/logic.
//     */
//    public void onUpdate()
//    {
//        this.prevPosX = this.posX;
//        this.prevPosY = this.posY;
//        this.prevPosZ = this.posZ;
//
//        if (this.particleAge++ >= this.particleMaxAge)
//        {
//            this.setDead();
//        }
//
//        this.setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
//        this.moveEntity(this.motionX, this.motionY, this.motionZ);
//
//        if (this.posY == this.prevPosY)
//        {
//            this.motionX *= 1.1D;
//            this.motionZ *= 1.1D;
//        }
//
//        this.motionX *= 0.9599999785423279D;
//        this.motionY *= 0.9599999785423279D;
//        this.motionZ *= 0.9599999785423279D;
//
//        if (this.isCollided)
//        {
//            this.motionX *= 0.699999988079071D;
//            this.motionZ *= 0.699999988079071D;
//        }
//    }

	@Override
	public void onUpdate() {

		this.setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);

		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		float proportion = (float) this.particleAge / (float) this.particleMaxAge;
		proportion = 1.0F - proportion;
//        float antiProportion = 1.0F - proportion;
//        antiProportion *= antiProportion;
//        antiProportion *= antiProportion;
		this.posX = this.originX + this.motionX * (double) proportion;
		this.posY = this.originY + this.motionY * (double) proportion;// - (double)(antiProportion * 1.2F);
		this.posZ = this.originZ + this.motionZ * (double) proportion;

		if (this.particleAge++ >= this.particleMaxAge) {
			this.setExpired();
		}
	}
}
