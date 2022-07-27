package twilightforest.client;

import net.minecraft.ChatFormatting;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.DimensionSpecialEffectsManager;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import twilightforest.TFConfig;
import twilightforest.TwilightForestMod;
import twilightforest.block.entity.GrowingBeanstalkBlockEntity;
import twilightforest.client.model.item.FullbrightBakedModel;
import twilightforest.client.model.item.TintIndexAwareFullbrightBakedModel;
import twilightforest.client.renderer.TFSkyRenderer;
import twilightforest.client.renderer.TFWeatherRenderer;
import twilightforest.client.renderer.entity.ShieldLayer;
import twilightforest.client.renderer.tileentity.TwilightChestRenderer;
import twilightforest.compat.curios.CuriosCompat;
import twilightforest.data.tags.ItemTagGenerator;
import twilightforest.events.HostileMountEvents;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFItems;
import twilightforest.item.*;
import twilightforest.util.WorldUtil;
import twilightforest.world.registration.TFGenerationSettings;

import java.util.Objects;
import java.util.function.UnaryOperator;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = TwilightForestMod.ID, value = Dist.CLIENT)
public class TFClientEvents {

	@Mod.EventBusSubscriber(modid = TwilightForestMod.ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class ModBusEvents {
		@SubscribeEvent
		public static void registerLoaders(ModelEvent.RegisterGeometryLoaders event) {
			event.register("patch", PatchModelLoader.INSTANCE);
		}

		@SubscribeEvent
		public static void modelBake(ModelEvent.BakingCompleted event) {
			TFItems.addItemModelProperties();

			// TODO Unhardcode, into using Model Deserializers and load from JSON instead
			fullbrightItem(event, TFItems.FIERY_INGOT);
			fullbrightItem(event, TFItems.FIERY_BOOTS);
			fullbrightItem(event, TFItems.FIERY_CHESTPLATE);
			fullbrightItem(event, TFItems.FIERY_HELMET);
			fullbrightItem(event, TFItems.FIERY_LEGGINGS);
			fullbrightItem(event, TFItems.FIERY_PICKAXE);
			fullbrightItem(event, TFItems.FIERY_SWORD);

			fullbrightItem(event, TFItems.RED_THREAD);

			fullbrightBlock(event, TFBlocks.FIERY_BLOCK);

			if (!ModList.get().isLoaded("ctm")) {
				tintedFullbrightBlock(event, TFBlocks.PINK_CASTLE_RUNE_BRICK, FullbrightBakedModel::disableCache);
				tintedFullbrightBlock(event, TFBlocks.BLUE_CASTLE_RUNE_BRICK, FullbrightBakedModel::disableCache);
				tintedFullbrightBlock(event, TFBlocks.YELLOW_CASTLE_RUNE_BRICK, FullbrightBakedModel::disableCache);
				tintedFullbrightBlock(event, TFBlocks.VIOLET_CASTLE_RUNE_BRICK, FullbrightBakedModel::disableCache);
			}
		}

		private static void fullbrightItem(ModelEvent.BakingCompleted event, RegistryObject<Item> item) {
			fullbrightItem(event, item, f -> f);
		}

		private static void fullbrightItem(ModelEvent.BakingCompleted event, RegistryObject<Item> item, UnaryOperator<FullbrightBakedModel> process) {
			fullbright(event, Objects.requireNonNull(item.getId()), "inventory", process);
		}

		private static void fullbrightBlock(ModelEvent.BakingCompleted event, RegistryObject<Block> block) {
			fullbrightBlock(event, block, f -> f);
		}

		private static void fullbrightBlock(ModelEvent.BakingCompleted event, RegistryObject<Block> block, UnaryOperator<FullbrightBakedModel> process) {
			fullbright(event, Objects.requireNonNull(block.getId()), "inventory", process);
			fullbright(event, Objects.requireNonNull(block.getId()), "", process);
		}

		private static void fullbright(ModelEvent.BakingCompleted event, ResourceLocation rl, String state, UnaryOperator<FullbrightBakedModel> process) {
			ModelResourceLocation mrl = new ModelResourceLocation(rl, state);
			event.getModels().put(mrl, process.apply(new FullbrightBakedModel(event.getModels().get(mrl))));
		}

		private static void tintedFullbrightItem(ModelEvent.BakingCompleted event, RegistryObject<Item> item) {
			tintedFullbrightItem(event, item, f -> f);
		}

		private static void tintedFullbrightItem(ModelEvent.BakingCompleted event, RegistryObject<Item> item, UnaryOperator<FullbrightBakedModel> process) {
			tintedFullbright(event, Objects.requireNonNull(item.getId()), "inventory", process);
		}

		private static void tintedFullbrightBlock(ModelEvent.BakingCompleted event, RegistryObject<Block> block) {
			tintedFullbrightBlock(event, block, f -> f);
		}

		private static void tintedFullbrightBlock(ModelEvent.BakingCompleted event, RegistryObject<Block> block, UnaryOperator<FullbrightBakedModel> process) {
			tintedFullbright(event, Objects.requireNonNull(block.getId()), "inventory", process);
			tintedFullbright(event, Objects.requireNonNull(block.getId()), "", process);
		}

		private static void tintedFullbright(ModelEvent.BakingCompleted event, ResourceLocation rl, String state, UnaryOperator<FullbrightBakedModel> process) {
			ModelResourceLocation mrl = new ModelResourceLocation(rl, state);
			event.getModels().put(mrl, process.apply(new TintIndexAwareFullbrightBakedModel(event.getModels().get(mrl))));
		}

		@SubscribeEvent
		public static void texStitch(TextureStitchEvent.Pre evt) {
			TextureAtlas map = evt.getAtlas();

			if (Sheets.CHEST_SHEET.equals(map.location()))
				TwilightChestRenderer.MATERIALS.values().stream()
						.flatMap(e -> e.values().stream())
						.map(Material::texture)
						.forEach(evt::addSprite);

			evt.addSprite(TwilightForestMod.prefix("block/mosspatch"));
		}

		@SubscribeEvent
		public static void registerModels(ModelEvent.RegisterAdditional event) {
			event.register(ShieldLayer.LOC);
			event.register(new ModelResourceLocation(TwilightForestMod.prefix("trophy"), "inventory"));
			event.register(new ModelResourceLocation(TwilightForestMod.prefix("trophy_minor"), "inventory"));
			event.register(new ModelResourceLocation(TwilightForestMod.prefix("trophy_quest"), "inventory"));

			event.register(TwilightForestMod.prefix("block/casket_obsidian"));
			event.register(TwilightForestMod.prefix("block/casket_stone"));
			event.register(TwilightForestMod.prefix("block/casket_basalt"));
		}

		@SubscribeEvent
		public static void registerDimEffects(RegisterDimensionSpecialEffectsEvent event) {
			new TFSkyRenderer();
			new TFWeatherRenderer();
			event.register(TwilightForestMod.prefix("renderer"), new TwilightForestRenderInfo(128.0F, false, DimensionSpecialEffects.SkyType.NONE, false, false));
		}
	}

	/**
	 * Stop the game from rendering the mount health for unfriendly creatures
	 */
	@SubscribeEvent
	public static void preOverlay(RenderGuiOverlayEvent.Pre event) {
		if (event.getOverlay().id() == VanillaGuiOverlay.MOUNT_HEALTH.id()) {
			if (HostileMountEvents.isRidingUnfriendly(Minecraft.getInstance().player)) {
				event.setCanceled(true);
			}
		}
	}

	/**
	 * Render effects in first-person perspective
	 */
	@SubscribeEvent
	public static void renderWorldLast(RenderLevelStageEvent event) {
		if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_PARTICLES) { // after particles says its best for special rendering effects, and thats what I consider this
			if (!TFConfig.CLIENT_CONFIG.firstPersonEffects.get()) return;

			Options settings = Minecraft.getInstance().options;
			if (settings.getCameraType() != CameraType.FIRST_PERSON || settings.hideGui) return;

			Entity entity = Minecraft.getInstance().getCameraEntity();
			if (entity instanceof LivingEntity) {
				EntityRenderer<? extends Entity> renderer = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(entity);
				if (renderer instanceof LivingEntityRenderer<?, ?>) {
					for (EffectRenders effect : EffectRenders.VALUES) {
						if (effect.shouldRender((LivingEntity) entity, true)) {
							effect.render((LivingEntity) entity, ((LivingEntityRenderer<?, ?>) renderer).getModel(), 0.0, 0.0, 0.0, event.getPartialTick(), true);
						}
					}
				}
			}
		}
	}

	/**
	 * On the tick, we kill the vignette
	 */
	@SubscribeEvent
	public static void renderTick(TickEvent.RenderTickEvent event) {
		if (event.phase == TickEvent.Phase.START) {
			Minecraft minecraft = Minecraft.getInstance();

			// only fire if we're in the twilight forest
			if (minecraft.level != null && TFGenerationSettings.DIMENSION_KEY.equals(minecraft.level.dimension())) {
				// vignette
				if (minecraft.gui != null) {
					minecraft.gui.vignetteBrightness = 0.0F;
				}
			}//*/

			if (minecraft.player != null && HostileMountEvents.isRidingUnfriendly(minecraft.player)) {
				if (minecraft.gui != null) {
					minecraft.gui.setOverlayMessage(Component.empty(), false);
				}
			}
		}
	}

	private static float intensity = 0.0F;

	@SubscribeEvent
	public static void clientTick(TickEvent.ClientTickEvent event) {
		if (event.phase != TickEvent.Phase.END || Minecraft.getInstance().isPaused()) return;
		time++;

		Minecraft mc = Minecraft.getInstance();
		float partial = mc.getFrameTime();

		rotationTickerI = (rotationTickerI >= 359 ? 0 : rotationTickerI + 1);
		sineTickerI = (sineTickerI >= SINE_TICKER_BOUND ? 0 : sineTickerI + 1);

		rotationTicker = rotationTickerI + partial;
		sineTicker = sineTicker + partial;

		BugModelAnimationHelper.animate();
		DimensionSpecialEffects info = DimensionSpecialEffectsManager.getForType(TwilightForestMod.prefix("renderer"));

		// add weather box if needed
		if (mc.level != null && info instanceof TwilightForestRenderInfo) {
			TFWeatherRenderer.tick();
		}

		if (mc.level != null && mc.player != null) {
			for (BlockPos pos : WorldUtil.getAllAround(mc.player.blockPosition(), 16)) {
				if (mc.level.getBlockEntity(pos) instanceof GrowingBeanstalkBlockEntity bean && bean.isBeanstalkRumbling()) {
					Player player = mc.player;
					intensity = (float) (1.0F - mc.player.distanceToSqr(Vec3.atCenterOf(pos)) / Math.pow(16, 2));
					if (intensity > 0) {
						player.moveTo(player.getX(), player.getY(), player.getZ(),
								player.getYRot() + (player.getRandom().nextFloat() * 2.0F - 1.0F) * intensity,
								player.getXRot() + (player.getRandom().nextFloat() * 2.0F - 1.0F) * intensity);
						intensity = 0.0F;
						break;
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void camera(ViewportEvent.ComputeCameraAngles event) {
		if (!Minecraft.getInstance().isPaused() && intensity > 0 && Minecraft.getInstance().player != null) {
			event.setYaw((float) Mth.lerp(event.getPartialTick(), event.getYaw(), event.getYaw() + (Minecraft.getInstance().player.getRandom().nextFloat() * 2F - 1F) * intensity));
			event.setPitch((float) Mth.lerp(event.getPartialTick(), event.getPitch(), event.getPitch() + (Minecraft.getInstance().player.getRandom().nextFloat() * 2F - 1F) * intensity));
			event.setRoll((float) Mth.lerp(event.getPartialTick(), event.getRoll(), event.getRoll() + (Minecraft.getInstance().player.getRandom().nextFloat() * 2F - 1F) * intensity));
			intensity = 0F;
		}
	}

	private static final MutableComponent WIP_TEXT_0 = Component.translatable("twilightforest.misc.wip0").setStyle(Style.EMPTY.withColor(ChatFormatting.RED));
	private static final MutableComponent WIP_TEXT_1 = Component.translatable("twilightforest.misc.wip1").setStyle(Style.EMPTY.withColor(ChatFormatting.RED));
	private static final MutableComponent NYI_TEXT = Component.translatable("twilightforest.misc.nyi").setStyle(Style.EMPTY.withColor(ChatFormatting.RED));

	@SubscribeEvent
	public static void tooltipEvent(ItemTooltipEvent event) {
		ItemStack item = event.getItemStack();

		if (!item.is(ItemTagGenerator.WIP) && !item.is(ItemTagGenerator.NYI)) return;

		if (item.is(ItemTagGenerator.WIP)) {
			event.getToolTip().add(WIP_TEXT_0);
			event.getToolTip().add(WIP_TEXT_1);
		} else {
			event.getToolTip().add(NYI_TEXT);
		}
	}

	public static int time = 0;
	private static int rotationTickerI = 0;
	private static int sineTickerI = 0;
	public static float rotationTicker = 0;
	public static float sineTicker = 0;
	public static final float PI = (float) Math.PI;
	private static final int SINE_TICKER_BOUND = (int) ((PI * 200.0F) - 1.0F);

	/**
	 * Zooms in the FOV while using a bow, just like vanilla does in the AbstractClientPlayer's getFieldOfViewModifier() method (1.18.2)
	 */
	@SubscribeEvent
	public static void FOVUpdate(ComputeFovModifierEvent event) {
		Player player = event.getPlayer();
		if (player.isUsingItem()) {
			Item useItem = player.getUseItem().getItem();
			if (useItem instanceof TripleBowItem || useItem instanceof EnderBowItem || useItem instanceof IceBowItem || useItem instanceof SeekerBowItem) {
				float f = player.getTicksUsingItem() / 20.0F;
				f = f > 1.0F ? 1.0F : f * f;
				event.setNewFovModifier(event.getFovModifier() * (1.0F - f * 0.15F));
			}
		}
	}

	@SubscribeEvent
	public static void unrenderHeadWithTrophies(RenderLivingEvent<?, ?> event) {
		ItemStack stack = event.getEntity().getItemBySlot(EquipmentSlot.HEAD);
		boolean visible = !(stack.getItem() instanceof TrophyItem) && !(stack.getItem() instanceof SkullCandleItem) && !areCuriosEquipped(event.getEntity());

		if (event.getRenderer().getModel() instanceof HeadedModel headedModel) {
			headedModel.getHead().visible = visible;
			if (event.getRenderer().getModel() instanceof HumanoidModel<?> humanoidModel) {
				humanoidModel.hat.visible = visible && partShown(event.getEntity());
			}
		}
	}

	private static boolean partShown(Entity entity) {
		return !(entity instanceof AbstractClientPlayer player) || player.isModelPartShown(PlayerModelPart.HAT);
	}

	private static boolean areCuriosEquipped(LivingEntity entity) {
		if (ModList.get().isLoaded("curios")) {
			return CuriosCompat.isTrophyCurioEquipped(entity) || CuriosCompat.isSkullCurioEquipped(entity);
		}
		return false;
	}
}
