package twilightforest.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;
import twilightforest.network.TFPacketHandler;
import twilightforest.block.BlockTFCastleMagic;
import twilightforest.block.TFBlocks;
import twilightforest.item.TFItems;
import twilightforest.network.PacketAnnihilateBlock;
import twilightforest.util.WorldUtil;

import java.util.List;

public class EntityTFCubeOfAnnihilation extends ThrowableEntity {

	private boolean hasHitObstacle = false;

	public EntityTFCubeOfAnnihilation(EntityType<? extends EntityTFCubeOfAnnihilation> type, World world) {
		super(type, world);
		this.isImmuneToFire();
	}

	public EntityTFCubeOfAnnihilation(EntityType<? extends EntityTFCubeOfAnnihilation> type, World world, LivingEntity thrower) {
		super(type, thrower, world);
		this.isImmuneToFire();
		this.shoot(thrower, thrower.rotationPitch, thrower.rotationYaw, 0F, 1.5F, 1F);
	}

	@Override
	protected float getGravityVelocity() {
		return 0F;
	}

	@Override
	protected void onImpact(RayTraceResult ray) {
		if (world.isRemote)
			return;

		// only hit living things
		if (ray instanceof EntityRayTraceResult) {
			if (((EntityRayTraceResult) ray).getEntity() instanceof LivingEntity && ((EntityRayTraceResult) ray).getEntity().attackEntityFrom(this.getDamageSource(), 10)) {
				this.ticksExisted += 60;
			}
		}

		if (ray instanceof BlockRayTraceResult) {
			if (((BlockRayTraceResult)ray).getPos() != null && !this.world.isAirBlock(((BlockRayTraceResult)ray).getPos())) {
				this.affectBlocksInAABB(this.getBoundingBox().grow(0.2F, 0.2F, 0.2F));
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
			if (!state.getBlock().isAir(state, world, pos)) {
				if (canAnnihilate(pos, state)) {
					this.world.removeBlock(pos, false);
					this.playSound(SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, 0.125f, this.rand.nextFloat() * 0.25F + 0.75F);
					this.sendAnnihilateBlockPacket(world, pos);
				} else {
					this.hasHitObstacle = true;
				}
			}
		}
	}

	//TODO: Should this be put into a Tag?
	private boolean canAnnihilate(BlockPos pos, BlockState state) {
		// whitelist many castle blocks
		Block block = state.getBlock();
		if (block == TFBlocks.deadrock || block == TFBlocks.castle_brick || (block == TFBlocks.castle_rune_brick && state.get(BlockTFCastleMagic.COLOR) != DyeColor.PURPLE) || block == TFBlocks.force_field || block == TFBlocks.thorns) {
			return true;
		}

		return block.getExplosionResistance(this) < 8F && state.getBlockHardness(world, pos) >= 0;
	}


	private void sendAnnihilateBlockPacket(World world, BlockPos pos) {
		PacketDistributor.TargetPoint targetPoint = new PacketDistributor.TargetPoint(pos.getX(), pos.getY(), pos.getZ(), 64, world.getDimension().getType());
		TFPacketHandler.CHANNEL.send(PacketDistributor.NEAR.with(() -> targetPoint), new PacketAnnihilateBlock(pos));
	}

	@Override
	public void tick() {
		super.tick();

		if (!this.world.isRemote) {
			if (this.getThrower() == null) {
				this.remove();
				return;
			}

			// always head towards either the point or towards the player
			Vec3d destPoint = new Vec3d(this.getThrower().getX(), this.getThrower().getY() + this.getThrower().getEyeHeight(), this.getThrower().getZ());

			if (this.isReturning()) {
				// if we are returning, and are near enough to the player, then we are done
				List<LivingEntity> list = this.world.getEntitiesWithinAABB(LivingEntity.class, this.getBoundingBox().offset(this.getMotion().getX(), this.getMotion().getY(), this.getMotion().getZ()).grow(1.0D, 1.0D, 1.0D));

				if (list.contains(this.getThrower())) {
					this.remove();
				}
			} else {
				destPoint = destPoint.add(getThrower().getLookVec().scale(16F));
			}

			// set motions
			Vec3d velocity = new Vec3d(this.getX() - destPoint.x, (this.getY() + this.getHeight() / 2F) - destPoint.y, this.getZ() - destPoint.z);

//			this.motionX -= velocity.x;
//			this.motionY -= velocity.y;
//			this.motionZ -= velocity.z;
			this.getMotion().subtract(velocity.x, velocity.y, velocity.z);

			// normalize speed
			float currentSpeed = MathHelper.sqrt(this.getMotion().getX() * this.getMotion().getX() + this.getMotion().getY() * this.getMotion().getY() + this.getMotion().getZ() * this.getMotion().getZ());

			float maxSpeed = 0.5F;


			if (currentSpeed > maxSpeed) {
				this.setMotion(new Vec3d(
						this.getMotion().getX() / currentSpeed / maxSpeed,
						this.getMotion().getY() / currentSpeed / maxSpeed,
						this.getMotion().getZ() / currentSpeed / maxSpeed));
			} else {
				float slow = 0.5F;
//				this.motionX *= slow;
//				this.motionY *= slow;
//				this.motionZ *= slow;
				this.getMotion().mul(slow, slow, slow);
			}

			// demolish some blocks
			this.affectBlocksInAABB(this.getBoundingBox().grow(0.2F, 0.2F, 0.2F));
		}
	}

	@Override
	public void remove() {
		super.remove();
		LivingEntity thrower = this.getThrower();
		if (thrower != null && thrower.getActiveItemStack().getItem() == TFItems.cube_of_annihilation.get()) {
			thrower.resetActiveHand();
		}
	}

	private boolean isReturning() {
		if (this.hasHitObstacle || this.getThrower() == null || !(this.getThrower() instanceof PlayerEntity)) {
			return true;
		} else {
			PlayerEntity player = (PlayerEntity) this.getThrower();
			return !player.isHandActive();
		}
	}
}
