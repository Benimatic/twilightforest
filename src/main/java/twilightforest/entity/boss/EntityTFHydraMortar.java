package twilightforest.entity.boss;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.block.Blocks;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import twilightforest.TwilightForestMod;

public class EntityTFHydraMortar extends ThrowableEntity {

	private static final int BURN_FACTOR = 5;
	private static final int DIRECT_DAMAGE = 18;

	public int fuse = 80;
	private boolean megaBlast = false;

	public EntityTFHydraMortar(EntityType<? extends EntityTFHydraMortar> type, World world) {
		super(type, world);
	}

	public EntityTFHydraMortar(EntityType<? extends EntityTFHydraMortar> type, World world, EntityTFHydraHead head) {
		super(type, head, world);

		Vec3d vector = head.getLookVec();

		double dist = 3.5;
		double px = head.posX + vector.x * dist;
		double py = head.posY + 1 + vector.y * dist;
		double pz = head.posZ + vector.z * dist;

		setLocationAndAngles(px, py, pz, 0, 0);
		// these are being set to extreme numbers when we get here, why?
		head.motionX = 0;
		head.motionY = 0;
		head.motionZ = 0;
		shoot(head, head.rotationPitch, head.rotationYaw, -20.0F, 0.5F, 1F);

		TwilightForestMod.LOGGER.debug("Launching mortar! Current head motion is {}, {}", head.motionX, head.motionZ);
	}

	@Override
	public void tick() {
		super.tick();

		this.pushOutOfBlocks(this.posX, (this.getBoundingBox().minY + this.getBoundingBox().maxY) / 2.0D, this.posZ);

		if (this.onGround) {
			this.motionX *= 0.9D;
			this.motionY *= 0.9D;
			this.motionZ *= 0.9D;

			if (!world.isRemote && this.fuse-- <= 0) {
				detonate();
			}
		}
	}

	public void setToBlasting() {
		this.megaBlast = true;
	}

	@Override
	protected void onImpact(RayTraceResult ray) {
		if (ray.entityHit == null && !megaBlast) {
			// we hit the ground
			this.motionY = 0;
			this.onGround = true;
		} else if (!world.isRemote && ray.entityHit != owner && !isPartOfHydra(ray.entityHit)) {
			detonate();
		}
	}

	private boolean isPartOfHydra(Entity entity) {
		if (owner instanceof EntityTFHydraPart) {
			EntityTFHydra hydra = ((EntityTFHydraPart) owner).hydra;
			if (hydra == null || hydra.getParts() == null)
				return false;
			if (entity == hydra)
				return true;
			for (Entity e : hydra.getParts())
				if (entity == e)
					return true;
			for (HydraHeadContainer container : hydra.hc)
				if (entity == container.headEntity)
					return true;
		}
		return false;
	}

	@Override
	public float getExplosionResistance(Explosion explosion, World world, BlockPos pos, BlockState state) {
		float resistance = super.getExplosionResistance(explosion, world, pos, state);

		if (this.megaBlast && state.getBlock() != Blocks.BEDROCK && state.getBlock() != Blocks.END_PORTAL && state.getBlock() != Blocks.END_PORTAL_FRAME) {
			resistance = Math.min(0.8F, resistance);
		}

		return resistance;
	}

	private void detonate() {
		float explosionPower = megaBlast ? 4.0F : 0.1F;
		boolean flag = ForgeEventFactory.getMobGriefingEvent(world, this);
		this.world.newExplosion(this, this.posX, this.posY, this.posZ, explosionPower, flag, flag);

		DamageSource src = new IndirectEntityDamageSource("onFire", this, getThrower()).setFireDamage().setProjectile();

		for (Entity nearby : this.world.getEntitiesWithinAABBExcludingEntity(this, this.getBoundingBox().grow(1.0D, 1.0D, 1.0D))) {
			if (nearby.attackEntityFrom(src, DIRECT_DAMAGE) && !nearby.isImmuneToFire()) {
				nearby.setFire(BURN_FACTOR);
			}
		}

		this.setDead();
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		super.attackEntityFrom(source, amount);

		if (source.getTrueSource() != null && !this.world.isRemote) {
			Vec3d vec3d = source.getTrueSource().getLookVec();
			if (vec3d != null) {
				// reflect faster and more accurately
				this.shoot(vec3d.x, vec3d.y, vec3d.z, 1.5F, 0.1F);  // reflect faster and more accurately
				this.onGround = false;
				this.fuse += 20;
			}

			if (source.getTrueSource() instanceof LivingEntity) {
				this.owner = (LivingEntity) source.getTrueSource();
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isBurning() {
		return true;
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	/**
	 * We need to set this so that the player can attack and reflect the bolt
	 */
	@Override
	public float getCollisionBorderSize() {
		return 1.5F;
	}

	@Override
	protected float getGravityVelocity() {
		return 0.05F;
	}
}
