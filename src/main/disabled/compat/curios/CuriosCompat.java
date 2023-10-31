package twilightforest.compat.curios;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.common.capabilities.ICapabilityProvider;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.event.DropRulesEvent;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.common.capability.CurioItemCapability;
import twilightforest.client.model.TFModelLayers;
import twilightforest.compat.curios.model.CharmOfLifeNecklaceModel;
import twilightforest.compat.curios.renderer.CharmOfKeepingRenderer;
import twilightforest.compat.curios.renderer.CharmOfLifeNecklaceRenderer;
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
					if (stack.is(TFBlocks.CICADA.get().asItem()) && !context.entity().level().isClientSide()) {
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
			if (!player.level().isClientSide() && CharmEvents.charmUsed != null && playerData.contains(CharmEvents.CHARM_INV_TAG) && !playerData.getList(CharmEvents.CHARM_INV_TAG, 10).isEmpty()) {
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

	public static void registerCurioLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(TFModelLayers.CHARM_OF_LIFE, CharmOfLifeNecklaceModel::create);
	}

	public static void registerCurioRenderers(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			CuriosRendererRegistry.register(TFItems.CHARM_OF_LIFE_1.get(), () -> new CharmOfLifeNecklaceRenderer(new float[]{1.0F, 0.5F, 0.5F}));
			CuriosRendererRegistry.register(TFItems.CHARM_OF_LIFE_2.get(), () -> new CharmOfLifeNecklaceRenderer(new float[]{1.0F, 0.9F, 0.0F}));
			CuriosRendererRegistry.register(TFItems.CHARM_OF_KEEPING_1.get(), CharmOfKeepingRenderer::new);
			CuriosRendererRegistry.register(TFItems.CHARM_OF_KEEPING_2.get(), CharmOfKeepingRenderer::new);
			CuriosRendererRegistry.register(TFItems.CHARM_OF_KEEPING_3.get(), CharmOfKeepingRenderer::new);

			CuriosRendererRegistry.register(TFItems.NAGA_TROPHY.get(), CurioHeadRenderer::new);
			CuriosRendererRegistry.register(TFItems.LICH_TROPHY.get(), CurioHeadRenderer::new);
			CuriosRendererRegistry.register(TFItems.MINOSHROOM_TROPHY.get(), CurioHeadRenderer::new);
			CuriosRendererRegistry.register(TFItems.HYDRA_TROPHY.get(), CurioHeadRenderer::new);
			CuriosRendererRegistry.register(TFItems.KNIGHT_PHANTOM_TROPHY.get(), CurioHeadRenderer::new);
			CuriosRendererRegistry.register(TFItems.UR_GHAST_TROPHY.get(), CurioHeadRenderer::new);
			CuriosRendererRegistry.register(TFItems.ALPHA_YETI_TROPHY.get(), CurioHeadRenderer::new);
			CuriosRendererRegistry.register(TFItems.SNOW_QUEEN_TROPHY.get(), CurioHeadRenderer::new);
			CuriosRendererRegistry.register(TFItems.QUEST_RAM_TROPHY.get(), CurioHeadRenderer::new);

			CuriosRendererRegistry.register(TFItems.CICADA.get(), CurioHeadRenderer::new);
			CuriosRendererRegistry.register(TFItems.FIREFLY.get(), CurioHeadRenderer::new);
			CuriosRendererRegistry.register(TFItems.MOONWORM.get(), CurioHeadRenderer::new);

			CuriosRendererRegistry.register(TFItems.CREEPER_SKULL_CANDLE.get(), CurioHeadRenderer::new);
			CuriosRendererRegistry.register(TFItems.PIGLIN_SKULL_CANDLE.get(), CurioHeadRenderer::new);
			CuriosRendererRegistry.register(TFItems.PLAYER_SKULL_CANDLE.get(), CurioHeadRenderer::new);
			CuriosRendererRegistry.register(TFItems.SKELETON_SKULL_CANDLE.get(), CurioHeadRenderer::new);
			CuriosRendererRegistry.register(TFItems.WITHER_SKELETON_SKULL_CANDLE.get(), CurioHeadRenderer::new);
			CuriosRendererRegistry.register(TFItems.ZOMBIE_SKULL_CANDLE.get(), CurioHeadRenderer::new);});
	}

	public static boolean isCicadaEquipped(LivingEntity entity) {
		Optional<SlotResult> slot = CuriosApi.getCuriosHelper().findFirstCurio(entity, stack -> stack.is(TFItems.CICADA.get()));
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
