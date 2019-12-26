package twilightforest.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.util.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ParticleGhastTear extends SpriteTexturedParticle {

	public ParticleGhastTear(World world, double x, double y, double z, Item item) {
		super(world, x, y, z, 0.0D, 0.0D, 0.0D);
		this.sprite = Minecraft.getInstance().getItemRenderer().getItemModelMesher().getParticleIcon(item);
		this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
		//this.particleGravity = Blocks.SNOW.blockParticleGravity * 2F; TODO: Find what this is supposed to be
		this.particleScale = 16.0F;

		this.maxAge = 20 + rand.nextInt(40);
		this.canCollide = true;
	}

	@Override
	public IParticleRenderType getRenderType() {
		return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	public ParticleGhastTear(World world, double x, double y, double z, double vx, double vy, double vz, Item item) {
		this(world, x, y, z, item);
		this.motionX *= 0.10000000149011612D;
		this.motionY *= 0.10000000149011612D;
		this.motionZ *= 0.10000000149011612D;
		this.motionX += vx;
		this.motionY += vy;
		this.motionZ += vz;

		//TwilightForestMod.LOGGER.info("creating tear particle {}, isremote {}", this, world.isRemote);
	}

	@Override
	public void tick() {
		if (this.onGround && rand.nextBoolean()) {
			world.playSound(null, this.posX, this.posY + 1D, this.posZ, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.HOSTILE, 0.5F, 1.0F);

			ItemStack itemID = new ItemStack(Items.GHAST_TEAR);
			for (int i = 0; i < 20; ++i) {
				double gaussX = rand.nextGaussian() * 0.1D;
				double gaussY = rand.nextGaussian() * 0.2D;
				double gaussZ = rand.nextGaussian() * 0.1D;

				//TwilightForestMod.LOGGER.info("tear impact {}, isremote {}", this, world.isRemote);

				world.addParticle(new ItemParticleData(ParticleTypes.ITEM, itemID), this.posX + rand.nextFloat() - rand.nextFloat(), this.posY + 0.5F, this.posZ + rand.nextFloat(), gaussX, gaussY, gaussZ);
				world.addParticle(ParticleTypes.EXPLOSION, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);

			}
			this.setExpired();
		}
		super.tick();
	}

	@OnlyIn(Dist.CLIENT)
	public static class Factory implements IParticleFactory<BasicParticleType> {
		private final IAnimatedSprite spriteSet;

		public Factory(IAnimatedSprite sprite) {
			this.spriteSet = sprite;
		}

		@Override
		public Particle makeParticle(BasicParticleType typeIn, World worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			ParticleGhastTear particle = new ParticleGhastTear(worldIn, x, y, z, Items.GHAST_TEAR);
			particle.selectSpriteRandomly(this.spriteSet);
			return particle;
		}
	}
}
