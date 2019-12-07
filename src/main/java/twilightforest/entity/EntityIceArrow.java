package twilightforest.entity;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import twilightforest.potions.TFPotions;

public class EntityIceArrow extends EntityTFArrow {

	public EntityIceArrow(World world) {
		super(world);
	}

	public EntityIceArrow(World world, LivingEntity shooter) {
		super(world, shooter);
	}

	@Override
	public void tick() {
		super.tick();
		if (world.isRemote && !inGround) {
			int stateId = Block.getStateId(Blocks.SNOW.getDefaultState());
			for (int i = 0; i < 4; ++i) {
				this.world.addParticle(ParticleTypes.FALLING_DUST, this.posX + this.getMotion().getX() * (double) i / 4.0D, this.posY + this.getMotion().getY() * (double) i / 4.0D, this.posZ + this.getMotion().getZ() * (double) i / 4.0D, -this.getMotion().getX(), -this.getMotion().getY() + 0.2D, -this.getMotion().getZ(), stateId);
			}
		}
	}

	@Override
	protected void onHit(RayTraceResult ray) {
		super.onHit(ray);
		if (!world.isRemote && ray.entityHit instanceof LivingEntity) {
			int chillLevel = 2;
			((LivingEntity) ray.entityHit).addPotionEffect(new EffectInstance(TFPotions.frosty, 20 * 10, chillLevel));
		}
	}
}
