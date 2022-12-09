package twilightforest.compat.curios;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.network.PacketDistributor;
import top.theillusivec4.curios.api.*;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.event.DropRulesEvent;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.common.capability.CurioItemCapability;
import twilightforest.TFConfig;
import twilightforest.compat.curios.renderer.CharmOfKeepingRenderer;
import twilightforest.compat.curios.renderer.CharmOfLife1NecklaceRenderer;
import twilightforest.compat.curios.renderer.CharmOfLife2NecklaceRenderer;
import twilightforest.compat.curios.renderer.CurioHeadRenderer;
import twilightforest.events.CharmEvents;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFItems;
import twilightforest.item.SkullCandleItem;
import twilightforest.item.TrophyItem;
import twilightforest.network.CreateMovingCicadaSoundPacket;
import twilightforest.network.TFPacketHandler;

import javax.annotation.Nonnull;
import java.util.Optional;

public class CuriosCompat {

	public static void handleCuriosIMCs() {
		InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.CHARM.getMessageBuilder().build());
		InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.HEAD.getMessageBuilder().build());
	}

	public static ICapabilityProvider setupCuriosCapability(ItemStack stack) {
		return CurioItemCapability.createProvider(new ICurio() {
			@Override
			public ItemStack getStack() {
				return stack;
			}

			@Nonnull
			@Override
			public SoundInfo getEquipSound(SlotContext slotContext) {
				return new SoundInfo(SoundEvents.ARMOR_EQUIP_GENERIC, 1.0F, 1.0F);
			}

			@Override
			public void onEquip(SlotContext context, ItemStack prevStack) {
				//check that we dont have a cicada already on our head before trying to start the sound
				if (!context.entity().getItemBySlot(EquipmentSlot.HEAD).is(TFBlocks.CICADA.get().asItem())) {
					if (stack.is(TFBlocks.CICADA.get().asItem()) && !context.entity().getLevel().isClientSide()) {
						TFPacketHandler.CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(context::entity), new CreateMovingCicadaSoundPacket(context.entity().getId()));
					}
				}
			}

			@Override
			public boolean canEquipFromUse(SlotContext slotContext) {
				return true;
			}
		});
	}

	//if we have any curios and die with a charm of keeping on us, keep our curios instead of dropping them
	public static void keepCurios(DropRulesEvent event) {
		if (event.getEntity() instanceof Player player) {
			CompoundTag playerData = CharmEvents.getPlayerData(player);
			if (!player.getLevel().isClientSide() && CharmEvents.charmUsed != null && playerData.contains(CharmEvents.CHARM_INV_TAG) && !playerData.getList(CharmEvents.CHARM_INV_TAG, 10).isEmpty()) {
				//Keep all Curios items
				CuriosApi.getCuriosHelper().getEquippedCurios(player).ifPresent(modifiable -> {
					for (int i = 0; i < modifiable.getSlots(); ++i) {
						int finalI = i;
						event.addOverride(stack -> stack == modifiable.getStackInSlot(finalI), ICurio.DropRule.ALWAYS_KEEP);
					}
				});
			}
		}
	}

	public static void registerCurioRenderers() {
		CuriosRendererRegistry.register(TFItems.CHARM_OF_LIFE_1.get(), CharmOfLife1NecklaceRenderer::new);
		CuriosRendererRegistry.register(TFItems.CHARM_OF_LIFE_2.get(), CharmOfLife2NecklaceRenderer::new);
		CuriosRendererRegistry.register(TFItems.CHARM_OF_KEEPING_1.get(), CharmOfKeepingRenderer::new);
		CuriosRendererRegistry.register(TFItems.CHARM_OF_KEEPING_2.get(), CharmOfKeepingRenderer::new);
		CuriosRendererRegistry.register(TFItems.CHARM_OF_KEEPING_3.get(), CharmOfKeepingRenderer::new);

		CuriosRendererRegistry.register(TFBlocks.NAGA_TROPHY.get().asItem(), CurioHeadRenderer::new);
		CuriosRendererRegistry.register(TFBlocks.LICH_TROPHY.get().asItem(), CurioHeadRenderer::new);
		CuriosRendererRegistry.register(TFBlocks.MINOSHROOM_TROPHY.get().asItem(), CurioHeadRenderer::new);
		CuriosRendererRegistry.register(TFBlocks.HYDRA_TROPHY.get().asItem(), CurioHeadRenderer::new);
		CuriosRendererRegistry.register(TFBlocks.KNIGHT_PHANTOM_TROPHY.get().asItem(), CurioHeadRenderer::new);
		CuriosRendererRegistry.register(TFBlocks.UR_GHAST_TROPHY.get().asItem(), CurioHeadRenderer::new);
		CuriosRendererRegistry.register(TFBlocks.ALPHA_YETI_TROPHY.get().asItem(), CurioHeadRenderer::new);
		CuriosRendererRegistry.register(TFBlocks.SNOW_QUEEN_TROPHY.get().asItem(), CurioHeadRenderer::new);
		CuriosRendererRegistry.register(TFBlocks.QUEST_RAM_TROPHY.get().asItem(), CurioHeadRenderer::new);

		CuriosRendererRegistry.register(TFBlocks.CICADA.get().asItem(), CurioHeadRenderer::new);
		CuriosRendererRegistry.register(TFBlocks.FIREFLY.get().asItem(), CurioHeadRenderer::new);
		CuriosRendererRegistry.register(TFBlocks.MOONWORM.get().asItem(), CurioHeadRenderer::new);

		CuriosRendererRegistry.register(TFBlocks.CREEPER_SKULL_CANDLE.get().asItem(), CurioHeadRenderer::new);
		CuriosRendererRegistry.register(TFBlocks.PLAYER_SKULL_CANDLE.get().asItem(), CurioHeadRenderer::new);
		CuriosRendererRegistry.register(TFBlocks.SKELETON_SKULL_CANDLE.get().asItem(), CurioHeadRenderer::new);
		CuriosRendererRegistry.register(TFBlocks.WITHER_SKELE_SKULL_CANDLE.get().asItem(), CurioHeadRenderer::new);
		CuriosRendererRegistry.register(TFBlocks.ZOMBIE_SKULL_CANDLE.get().asItem(), CurioHeadRenderer::new);
	}

	public static boolean isCicadaEquipped(LivingEntity entity) {
		Optional<SlotResult> slot = CuriosApi.getCuriosHelper().findFirstCurio(entity, stack -> stack.is(TFBlocks.CICADA.get().asItem()));
		return slot.isPresent();
	}

	public static boolean isTrophyCurioEquipped(LivingEntity entity) {
		Optional<SlotResult> slot = CuriosApi.getCuriosHelper().findFirstCurio(entity, stack -> stack.getItem() instanceof TrophyItem);
		return slot.isPresent() && slot.get().slotContext() != null && slot.get().slotContext().visible();
	}

	public static boolean isSkullCurioEquipped(LivingEntity entity) {
		Optional<SlotResult> slot = CuriosApi.getCuriosHelper().findFirstCurio(entity, stack -> stack.getItem() instanceof SkullCandleItem);
		return slot.isPresent() && slot.get().slotContext() != null && slot.get().slotContext().visible();
	}
}
