package twilightforest.entity.boss;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import twilightforest.client.particle.TFParticleType;

import java.util.List;

public class EntityTFFallingIce extends FallingBlockEntity {

	private static final int HANG_TIME = 100;

	public EntityTFFallingIce(EntityType<? extends EntityTFFallingIce> type, World world) {
		this(type, world, 0, 0, 0); // set falling tile on client to prevent crash
	}

	public EntityTFFallingIce(EntityType<? extends EntityTFFallingIce> type, World world, int x, int y, int z) {
		super(type, world);
		this.fallHurtAmount = 10.0F; // TODO: AT
		this.fallHurtMax = 30; // TODO: AT
		this.setHurtEntities(true);
		this.fallTile = Blocks.PACKED_ICE.getDefaultState(); // TODO: AT
		this.preventEntitySpawning = true;
		this.setPosition(x, y + (double)((1.0F - this.getHeight()) / 2.0F), z);
		this.setMotion(Vec3d.ZERO);
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
		this.setOrigin(new BlockPos(this));
	}

	@Override
	public void tick() {
		if (fallTime > HANG_TIME) {
			setNoGravity(true);
		}

		super.tick();

		// kill other nearby blocks if they are not as old as this one
		if (!this.world.isRemote) {
			List<EntityTFFallingIce> nearby = this.world.getEntitiesWithinAABB(EntityTFFallingIce.class, this.getBoundingBox());

			for (EntityTFFallingIce entity : nearby) {
				if (entity != this) {
					if (entity.fallTime < this.fallTime) {
						entity.remove();
					}
				}
			}

			destroyIceInAABB(this.getBoundingBox().grow(0.5, 0, 0.5));
		} else {
			makeTrail();
		}
	}

	private void makeTrail() {
		for (int i = 0; i < 2; i++) {
			double dx = this.getX() + 2F * (rand.nextFloat() - rand.nextFloat());
			double dy = this.getY() - 3F + 3F * (rand.nextFloat() - rand.nextFloat());
			double dz = this.getZ() + 2F * (rand.nextFloat() - rand.nextFloat());

			world.addParticle(TFParticleType.SNOW_WARNING.get(), dx, dy, dz, 0, -1, 0);
		}
	}

	// [VanillaCopy] Like super, but without anvil cases and with extra stuff
	@Override
	public void fall(float distance, float multiplier) {
		if (this.hurtEntities) {
			int i = MathHelper.ceil(distance - 1.0F);

			if (i > 0) {
				List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getBoundingBox());
				DamageSource damagesource = DamageSource.FALLING_BLOCK;

				for (Entity entity : list) {
					if (!(entity instanceof EntityTFYetiAlpha)) {
						entity.attackEntityFrom(damagesource, (float) Math.min(MathHelper.floor((float) i * this.fallHurtAmount), this.fallHurtMax));
					}
				}
			}
		}

		int stateId = Block.getStateId(Blocks.PACKED_ICE.getDefaultState());
		for (int i = 0; i < 200; i++) {
			double dx = this.getX() + 3F * (rand.nextFloat() - rand.nextFloat());
			double dy = this.getY() + 2 + 3F * (rand.nextFloat() - rand.nextFloat());
			double dz = this.getZ() + 3F * (rand.nextFloat() - rand.nextFloat());

			this.world.addParticle(ParticleTypes.BLOCK_CRACK, dx, dy, dz, 0, 0, 0, stateId);
		}

		this.playSound(Blocks.PACKED_ICE.getSoundType(Blocks.PACKED_ICE.getDefaultState(), world, getPosition(), null).getBreakSound(), 3F, 0.5F);
	}

	private void destroyIceInAABB(AxisAlignedBB aabb) {
		int minX = MathHelper.floor(aabb.minX);
		int minY = MathHelper.floor(aabb.minY);
		int minZ = MathHelper.floor(aabb.minZ);
		int maxX = MathHelper.floor(aabb.maxX);
		int maxY = MathHelper.floor(aabb.maxY);
		int maxZ = MathHelper.floor(aabb.maxZ);

		for (int dx = minX; dx <= maxX; ++dx) {
			for (int dy = minY; dy <= maxY; ++dy) {
				for (int dz = minZ; dz <= maxZ; ++dz) {
					BlockPos pos = new BlockPos(dx, dy, dz);
					Block block = this.world.getBlockState(pos).getBlock();

					if (block == Blocks.ICE || block == Blocks.PACKED_ICE || block == Blocks.STONE) {
						this.world.destroyBlock(pos, false);
					}
				}
			}
		}
	}
}
