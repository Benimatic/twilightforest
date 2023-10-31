package twilightforest.events;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.eventbus.api.SubscribeEvent;
import net.neoforged.neoforge.fml.ModList;
import net.neoforged.neoforge.fml.common.Mod;
import net.neoforged.neoforge.network.PacketDistributor;
import twilightforest.TwilightForestMod;
import twilightforest.compat.curios.CuriosCompat;
import twilightforest.entity.monster.DeathTome;
import twilightforest.entity.passive.Bighorn;
import twilightforest.entity.passive.DwarfRabbit;
import twilightforest.entity.passive.Squirrel;
import twilightforest.entity.passive.TinyBird;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFEntities;
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

	@SubscribeEvent
	public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
		Player player = event.getEntity();
		ItemStack stack = player.getItemInHand(event.getHand());
		if (stack.getItem() instanceof SpawnEggItem spawnEggItem && spawnEggItem.getType(stack.getTag()) == TFEntities.DEATH_TOME.get()) {
			BlockPos pos = event.getPos();
			BlockState state = event.getLevel().getBlockState(pos);
			if (state.getBlock() instanceof LecternBlock && !state.getValue(BlockStateProperties.HAS_BOOK)) {
				event.setCanceled(true);
				event.getLevel().playSound(null, pos, SoundEvents.BOOK_PUT, SoundSource.BLOCKS, 1.0F, 1.0F);

				if (event.getLevel() instanceof ServerLevel serverLevel) {
					DeathTome tome = TFEntities.DEATH_TOME.get().spawn(serverLevel, stack, player, pos.below(), MobSpawnType.SPAWN_EGG, true, false);
					if (tome != null) {
						if (!player.getAbilities().instabuild) stack.shrink(1);
						serverLevel.gameEvent(player, GameEvent.ENTITY_PLACE, pos);
						tome.setOnLectern(true);
					}
				}

			}
		}
	}
}
