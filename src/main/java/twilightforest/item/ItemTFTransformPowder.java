package twilightforest.item;

import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import twilightforest.TwilightForestMod;
import twilightforest.entity.TFEntities;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ItemTFTransformPowder extends Item {

	private final Map<EntityType<?>, EntityType<?>> transformMap = new HashMap<>();

	protected ItemTFTransformPowder(Properties props) {
		super(props);
	}

	public void initTransformations() {
		addTwoWayTransformation(TFEntities.minotaur,       EntityType.ZOMBIE_PIGMAN);
		addTwoWayTransformation(TFEntities.deer,           EntityType.COW);
		addTwoWayTransformation(TFEntities.bighorn_sheep,  EntityType.SHEEP);
		addTwoWayTransformation(TFEntities.wild_boar,      EntityType.PIG);
		addTwoWayTransformation(TFEntities.bunny,          EntityType.RABBIT);
		addTwoWayTransformation(TFEntities.tiny_bird,      EntityType.PARROT);
		addTwoWayTransformation(TFEntities.raven,          EntityType.BAT);
		addTwoWayTransformation(TFEntities.hostile_wolf,   EntityType.WOLF);
		addTwoWayTransformation(TFEntities.penguin,        EntityType.CHICKEN);
		addTwoWayTransformation(TFEntities.hedge_spider,   EntityType.SPIDER);
		addTwoWayTransformation(TFEntities.swarm_spider,   EntityType.CAVE_SPIDER);
		addTwoWayTransformation(TFEntities.wraith,         EntityType.BLAZE);
		addTwoWayTransformation(TFEntities.redcap,         EntityType.VILLAGER);
		addTwoWayTransformation(TFEntities.skeleton_druid, EntityType.WITCH);
	}

	private void addTwoWayTransformation(EntityType<?> from, EntityType<?> to) {
		transformMap.put(from, to);
		transformMap.put(to, from);
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, PlayerEntity player, LivingEntity target, Hand hand) {
		if (!target.isAlive()) {
			return false;
		}

		EntityType<?> type = transformMap.get(target.getType());
		if (type == null) {
			return false;
		}

		Entity newEntity = type.create(player.world);
		if (newEntity == null) {
			return false;
		}

		newEntity.setLocationAndAngles(target.getX(), target.getY(), target.getZ(), target.rotationYaw, target.rotationPitch);
		if (newEntity instanceof MobEntity) {
			((MobEntity) newEntity).onInitialSpawn(target.world, target.world.getDifficultyForLocation(new BlockPos(target)), SpawnReason.CONVERSION, null, null);
		}

		try { // try copying what can be copied
			UUID uuid = newEntity.getUniqueID();
			newEntity.read(target.writeWithoutTypeId(newEntity.writeWithoutTypeId(new CompoundNBT())));
			newEntity.setUniqueId(uuid);
		} catch (Exception e) {
			TwilightForestMod.LOGGER.warn("Couldn't transform entity NBT data", e);
		}

		target.world.addEntity(newEntity);
		target.remove();
		stack.shrink(1);

		if (target instanceof MobEntity) {
			((MobEntity) target).spawnExplosionParticle();
			((MobEntity) target).spawnExplosionParticle();
		}
		target.playSound(SoundEvents.ENTITY_ZOMBIE_VILLAGER_CURE, 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F);

		return true;
	}

	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, @Nonnull Hand hand) {
		if (world.isRemote) {
			AxisAlignedBB area = getEffectAABB(player);

			// particle effect
			for (int i = 0; i < 30; i++) {
				world.addParticle(ParticleTypes.CRIT, area.minX + world.rand.nextFloat() * (area.maxX - area.minX),
						area.minY + world.rand.nextFloat() * (area.maxY - area.minY),
						area.minZ + world.rand.nextFloat() * (area.maxZ - area.minZ),
						0, 0, 0);
			}

		}

		return new ActionResult<>(ActionResultType.SUCCESS, player.getHeldItem(hand));
	}

	private AxisAlignedBB getEffectAABB(PlayerEntity player) {
		double range = 2.0D;
		double radius = 1.0D;
		Vec3d srcVec = new Vec3d(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
		Vec3d lookVec = player.getLookVec();
		Vec3d destVec = srcVec.add(lookVec.x * range, lookVec.y * range, lookVec.z * range);

		return new AxisAlignedBB(destVec.x - radius, destVec.y - radius, destVec.z - radius, destVec.x + radius, destVec.y + radius, destVec.z + radius);
	}
}
