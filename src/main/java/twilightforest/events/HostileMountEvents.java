package twilightforest.events;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import twilightforest.TwilightForestMod;
import twilightforest.capabilities.CapabilityList;
import twilightforest.capabilities.thrown.YetiThrowCapability;
import twilightforest.entity.IHostileMount;
import twilightforest.init.TFDamageSources;

@Mod.EventBusSubscriber(modid = TwilightForestMod.ID)
public class HostileMountEvents {

	public static volatile boolean allowDismount = false;

	@SubscribeEvent
	public static void entityHurts(LivingAttackEvent event) {
		LivingEntity living = event.getEntity();
		DamageSource damageSource = event.getSource();
		// lets not make the player take suffocation damage if riding something
		if (living instanceof Player && isRidingUnfriendly(living) && damageSource == DamageSource.IN_WALL) {
			event.setCanceled(true);
		}

		if (damageSource == DamageSource.FALL && living.getCapability(CapabilityList.YETI_THROWN).map(YetiThrowCapability::getThrown).orElse(false)) {
			float amount = event.getAmount();
			event.setCanceled(true);
			living.hurt(TFDamageSources.yeeted(living.getCapability(CapabilityList.YETI_THROWN).resolve().get().getThrower()), amount);
		}
	}

	@SubscribeEvent
	public static void entityTeleports(EntityTeleportEvent event) {
		// if our grabbed target tries to teleport dont let them
		if (event.getEntity() instanceof LivingEntity living && isRidingUnfriendly(living)) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void preventMountDismount(EntityMountEvent event) {
		if (!event.getEntityBeingMounted().getLevel().isClientSide() &&
				!event.isMounting() && event.getEntityBeingMounted().isAlive() &&
				event.getEntityMounting() instanceof Player player && player.isAlive() &&
				isRidingUnfriendly(player) && !allowDismount && !player.getAbilities().invulnerable)
			event.setCanceled(true);
	}

	@SubscribeEvent
	public static void livingUpdate(LivingEvent.LivingTickEvent event) {
		if (event.getEntity() instanceof IHostileMount)
			event.getEntity().getPassengers().forEach(e -> e.setShiftKeyDown(false));
	}

	public static boolean isRidingUnfriendly(LivingEntity entity) {
		return entity.isPassenger() && entity.getVehicle() instanceof IHostileMount;
	}
}
