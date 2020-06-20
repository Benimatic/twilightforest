package twilightforest.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;
import twilightforest.item.TFItems;
import twilightforest.util.WorldUtil;

public class EntityTFChainBlock extends ThrowableEntity implements IEntityMultiPart, IEntityAdditionalSpawnData {

	private static final int MAX_SMASH = 12;
	private static final int MAX_CHAIN = 16;

	private Hand hand = Hand.MAIN_HAND;
	private boolean isReturning = false;
	private int blocksSmashed = 0;
	private double velX;
	private double velY;
	private double velZ;

	public final EntityTFGoblinChain chain1 = new EntityTFGoblinChain(this);
	public final EntityTFGoblinChain chain2 = new EntityTFGoblinChain(this);
	public final EntityTFGoblinChain chain3 = new EntityTFGoblinChain(this);
	public final EntityTFGoblinChain chain4 = new EntityTFGoblinChain(this);
	public final EntityTFGoblinChain chain5 = new EntityTFGoblinChain(this);
	private final Entity[] partsArray = { chain1, chain2, chain3, chain4, chain5 };

	public EntityTFChainBlock(EntityType<? extends EntityTFChainBlock> type, World world) {
		super(type, world);
	}

	public EntityTFChainBlock(EntityType<? extends EntityTFChainBlock> type, World world, LivingEntity thrower, Hand hand) {
		super(type, thrower, world);
		this.isReturning = false;
		this.hand = hand;
		this.shoot(thrower, thrower.rotationPitch, thrower.rotationYaw, 0F, 1.5F, 1F);
	}

	@Override
	public void shoot(double x, double y, double z, float speed, float accuracy) {
		super.shoot(x, y, z, speed, accuracy);

		// save velocity
		this.velX = this.getMotion().getX();
		this.velY = this.getMotion().getY();
		this.velZ = this.getMotion().getZ();
	}

	@Override
	protected float getGravityVelocity() {
		return 0.05F;
	}

	@Override
	protected void onImpact(RayTraceResult ray) {
		if (world.isRemote) {
			return;
		}

		if (ray instanceof EntityRayTraceResult) {
			EntityRayTraceResult entityRay = (EntityRayTraceResult) ray;

			// only hit living things
			if (entityRay.getEntity() instanceof LivingEntity && entityRay.getEntity() != this.getThrower()) {
				if (entityRay.getEntity().attackEntityFrom(this.getDamageSource(), 10)) {
					// age when we hit a monster so that we go back to the player faster
					this.ticksExisted += 60;
				}
			}
		}

		if (ray instanceof BlockRayTraceResult) {
			BlockRayTraceResult blockRay = (BlockRayTraceResult) ray;

			if (blockRay.getPos() != null && !this.world.isAirBlock(blockRay.getPos())) {
				if (!this.isReturning) {
					playSound(SoundEvents.BLOCK_ANVIL_LAND, 0.125f, this.rand.nextFloat());
				}

				if (this.blocksSmashed < MAX_SMASH) {
					if (this.world.getBlockState(blockRay.getPos()).getBlockHardness(world, blockRay.getPos()) > 0.3F) {
						// riccochet
						double bounce = 0.6;
						this.velX *= bounce;
						this.velY *= bounce;
						this.velZ *= bounce;


						switch (blockRay.getFace()) {
							case DOWN:
								if (this.velY > 0) {
									this.velY *= -bounce;
								}
								break;
							case UP:
								if (this.velY < 0) {
									this.velY *= -bounce;
								}
								break;
							case NORTH:
								if (this.velZ > 0) {
									this.velZ *= -bounce;
								}
								break;
							case SOUTH:
								if (this.velZ < 0) {
									this.velZ *= -bounce;
								}
								break;
							case WEST:
								if (this.velX > 0) {
									this.velX *= -bounce;
								}
								break;
							case EAST:
								if (this.velX < 0) {
									this.velX *= -bounce;
								}
								break;
						}
					}

					// demolish some blocks
					this.affectBlocksInAABB(this.getBoundingBox());
				}

				this.isReturning = true;

				// if we have smashed enough, add to ticks so that we go back faster
				if (this.blocksSmashed > MAX_SMASH && this.ticksExisted < 60) {
					this.ticksExisted += 60;
				}
			}
		}
	}

	private DamageSource getDamageSource() {
		LivingEntity thrower = this.getThrower();
		if (thrower instanceof PlayerEntity) {
			return DamageSource.causePlayerDamage((PlayerEntity) thrower);
		} else if (thrower != null) {
			return DamageSource.causeMobDamage(thrower);
		} else {
			return DamageSource.causeThrownDamage(this, null);
		}
	}

	private void affectBlocksInAABB(AxisAlignedBB box) {
		for (BlockPos pos : WorldUtil.getAllInBB(box)) {

			BlockState state = world.getBlockState(pos);
			Block block = state.getBlock();

			// TODO: The "explosion" parameter can't actually be null
			if (!block.isAir(state, world, pos) && block.getExplosionResistance(state, world, pos, this, null) < 7F
					&& state.getBlockHardness(world, pos) >= 0 && block.canEntityDestroy(state, world, pos, this)) {

				if (getThrower() instanceof PlayerEntity) {
					PlayerEntity player = (PlayerEntity) getThrower();

					if (block.canHarvestBlock(state, world, pos, player)) {
						block.harvestBlock(world, player, pos, state, world.getTileEntity(pos), player.getHeldItem(hand));
					}
				}

				world.destroyBlock(pos, false);
				this.blocksSmashed++;
			}
		}
	}

	@Override
	public void tick() {
		super.tick();

		if (world.isRemote) {
			chain1.tick();
			chain2.tick();
			chain3.tick();
			chain4.tick();
			chain5.tick();

			// set chain positions
			if (this.getThrower() != null) {
				// interpolate chain position
				Vec3d handVec = this.getThrower().getLookVec().rotateYaw(hand == Hand.MAIN_HAND ? -0.4F : 0.4F);

				double sx = this.getThrower().getX() + handVec.x;
				double sy = this.getThrower().getY() + handVec.y - 0.4F + this.getThrower().getEyeHeight();
				double sz = this.getThrower().getZ() + handVec.z;

				double ox = sx - this.getX();
				double oy = sy - this.getY() - 0.25F;
				double oz = sz - this.getZ();

				this.chain1.setPosition(sx - ox * 0.05, sy - oy * 0.05, sz - oz * 0.05);
				this.chain2.setPosition(sx - ox * 0.25, sy - oy * 0.25, sz - oz * 0.25);
				this.chain3.setPosition(sx - ox * 0.45, sy - oy * 0.45, sz - oz * 0.45);
				this.chain4.setPosition(sx - ox * 0.65, sy - oy * 0.65, sz - oz * 0.65);
				this.chain5.setPosition(sx - ox * 0.85, sy - oy * 0.85, sz - oz * 0.85);
			}
		} else {
			if (getThrower() == null) {
				remove();
			} else {
				double distToPlayer = this.getDistance(this.getThrower());
				// return if far enough away
				if (!this.isReturning && distToPlayer > MAX_CHAIN) {
					this.isReturning = true;
				}

				if (this.isReturning) {
					// despawn if close enough
					if (distToPlayer < 2F) {
						this.remove();
					}

					LivingEntity returnTo = this.getThrower();

					Vec3d back = new Vec3d(returnTo.getX(), returnTo.getY() + returnTo.getEyeHeight(), returnTo.getZ()).subtract(this.getPositionVector()).normalize();
					float age = Math.min(this.ticksExisted * 0.03F, 1.0F);

					// separate the return velocity from the normal bouncy velocity
//					this.getMotion().getX() = this.velX * (1.0 - age) + (back.x * 2F * age);
//					this.getMotion().getY() = this.velY * (1.0 - age) + (back.y * 2F * age) - this.getGravityVelocity();
//					this.getMotion().getZ() = this.velZ * (1.0 - age) + (back.z * 2F * age);
					this.setMotion(new Vec3d(
							this.velX * (1.0 - age) + (back.x * 2F * age),
							this.velY * (1.0 - age) + (back.y * 2F * age) - this.getGravityVelocity(),
							this.velZ * (1.0 - age) + (back.z * 2F * age)
					));
				}
			}
		}
	}

	@Override
	protected void registerData() {
	}

	@Override
	public void remove() {
		super.remove();
		LivingEntity thrower = this.getThrower();
		if (thrower != null && thrower.getActiveItemStack().getItem() == TFItems.block_and_chain.get()) {
			thrower.resetActiveHand();
		}
	}

	@Override
	public World getWorld() {
		return this.world;
	}

	@Override
	public boolean attackEntityFromPart(MultiPartEntityPart part, DamageSource source, float damage) {
		return false;
	}

	@Override
	public Entity[] getParts() {
		return partsArray;
	}

	@Override
	public void writeSpawnData(PacketBuffer buffer) {
		buffer.writeInt(getThrower() != null ? getThrower().getEntityId() : -1);
		buffer.writeBoolean(hand == Hand.MAIN_HAND);
	}

	@Override
	public void readSpawnData(PacketBuffer additionalData) {
		Entity e = world.getEntityByID(additionalData.readInt());
		if (e instanceof LivingEntity) {
			owner = (LivingEntity) e;
		}
		hand = additionalData.readBoolean() ? Hand.MAIN_HAND : Hand.OFF_HAND;
	}


	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
