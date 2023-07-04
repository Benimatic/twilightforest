package twilightforest.events;

import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import twilightforest.TwilightForestMod;
import twilightforest.compat.curios.CuriosCompat;
import twilightforest.entity.passive.Bighorn;
import twilightforest.entity.passive.DwarfRabbit;
import twilightforest.entity.passive.Squirrel;
import twilightforest.entity.passive.TinyBird;
import twilightforest.init.TFBlocks;
import twilightforest.network.CreateMovingCicadaSoundPacket;
import twilightforest.network.TFPacketHandler;

@Mod.EventBusSubscriber(modid = TwilightForestMod.ID)
public class MiscEvents {

	@SubscribeEvent
	public static void addPrey(EntityJoinLevelEvent event) {
		if (event.getEntity() instanceof Mob mob) {
			EntityType<?> type = mob.getType();
			if (type == EntityType.CAT) {
				mob.targetSelector.addGoal(1, new NonTameRandomTargetGoal<>((TamableAnimal) mob, DwarfRabbit.class, false, null));
				mob.targetSelector.addGoal(1, new NonTameRandomTargetGoal<>((TamableAnimal) mob, Squirrel.class, false, null));
				mob.targetSelector.addGoal(1, new NonTameRandomTargetGoal<>((TamableAnimal) mob, TinyBird.class, false, null));
			} else if (type == EntityType.OCELOT) {
				mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, DwarfRabbit.class, false));
				mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, Squirrel.class, false));
				mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, TinyBird.class, false));
			} else if (type == EntityType.FOX) {
				mob.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(mob, DwarfRabbit.class, false));
				mob.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(mob, Squirrel.class, false));
			} else if (type == EntityType.WOLF) {
				mob.targetSelector.addGoal(7, new NonTameRandomTargetGoal<>((TamableAnimal) mob, DwarfRabbit.class, false, null));
				mob.targetSelector.addGoal(7, new NonTameRandomTargetGoal<>((TamableAnimal) mob, Squirrel.class, false, null));
				mob.targetSelector.addGoal(7, new NonTameRandomTargetGoal<>((TamableAnimal) mob, Bighorn.class, false, null));
			}
		}
	}

	@SubscribeEvent
	public static void armorChanged(LivingEquipmentChangeEvent event) {
		LivingEntity living = event.getEntity();

		// from what I can see, vanilla doesnt have a hook for this in the item class. So this will have to do.
		// we only have to check equipping, when its unequipped the sound instance handles the rest

		//if we have a cicada in our curios slot, dont try to run this
		if (ModList.get().isLoaded("curios")) {
			if (CuriosCompat.isCicadaEquipped(living)) {
				return;
			}
		}

		if (living != null && !living.level().isClientSide() && event.getSlot() == EquipmentSlot.HEAD && event.getTo().is(TFBlocks.CICADA.get().asItem())) {
			TFPacketHandler.CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> living), new CreateMovingCicadaSoundPacket(living.getId()));
		}
	}
}
