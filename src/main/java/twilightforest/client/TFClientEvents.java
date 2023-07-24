package twilightforest.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.ChatFormatting;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.ParticleStatus;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.WrittenBookItem;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
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
import org.apache.commons.lang3.tuple.Pair;
import twilightforest.TFConfig;
import twilightforest.TwilightForestMod;
import twilightforest.block.CloudBlock;
import twilightforest.block.MiniatureStructureBlock;
import twilightforest.block.entity.GrowingBeanstalkBlockEntity;
import twilightforest.client.model.block.doors.CastleDoorModelLoader;
import twilightforest.client.model.block.giantblock.GiantBlockModelLoader;
import twilightforest.client.model.block.leaves.BakedLeavesModel;
import twilightforest.client.model.block.patch.PatchModelLoader;
import twilightforest.client.renderer.TFSkyRenderer;
import twilightforest.client.renderer.TFWeatherRenderer;
import twilightforest.client.renderer.entity.ShieldLayer;
import twilightforest.compat.curios.CuriosCompat;
import twilightforest.data.tags.ItemTagGenerator;
import twilightforest.events.HostileMountEvents;
import twilightforest.init.TFItems;
import twilightforest.item.*;
import twilightforest.world.registration.TFGenerationSettings;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = TwilightForestMod.ID, value = Dist.CLIENT)
public class TFClientEvents {

	@Mod.EventBusSubscriber(modid = TwilightForestMod.ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class ModBusEvents {
		@SubscribeEvent
		public static void registerLoaders(ModelEvent.RegisterGeometryLoaders event) {
			event.register("patch", PatchModelLoader.INSTANCE);
			event.register("giant_block", GiantBlockModelLoader.INSTANCE);
			event.register("castle_door", CastleDoorModelLoader.INSTANCE);
		}

		@SubscribeEvent
		public static void modelBake(ModelEvent.ModifyBakingResult event) {
			TFItems.addItemModelProperties();

			List<Map.Entry<ResourceLocation, BakedModel>> models =  event.getModels().entrySet().stream()
					.filter(entry -> entry.getKey().getNamespace().equals(TwilightForestMod.ID) && entry.getKey().getPath().contains("leaves") && !entry.getKey().getPath().contains("dark")).toList();

			models.forEach(entry -> event.getModels().put(entry.getKey(), new BakedLeavesModel(entry.getValue())));
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
	 * And cloud block rain / snow
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
		} else if (TFConfig.CLIENT_CONFIG.cloudBlockPrecipitationRender.get() && event.getStage() == RenderLevelStageEvent.Stage.AFTER_WEATHER) { // Semi vanilla copy of the weather renderer, but made so that it only renders the weather below each cloud block
			Minecraft minecraft = Minecraft.getInstance();
			if (minecraft.level == null) return;
			float partialTick = minecraft.getPartialTick();
			LightTexture lightTexture = minecraft.gameRenderer.lightTexture();
			int ticks = minecraft.levelRenderer.getTicks();
			lightTexture.turnOnLightLayer();

			Vec3 vec3 = event.getCamera().getPosition();
			double camX = vec3.x();
			double camY = vec3.y();
			double camZ = vec3.z();

			int floorX = Mth.floor(camX);
			int floorY = Mth.floor(camY);
			int floorZ = Mth.floor(camZ);

			Tesselator tesselator = Tesselator.getInstance();
			BufferBuilder bufferbuilder = tesselator.getBuilder();
			RenderSystem.disableCull();
			RenderSystem.enableBlend();
			RenderSystem.enableDepthTest();
			RenderSystem.depthMask(Minecraft.useShaderTransparency());

			int renderDistance = Minecraft.useFancyGraphics() ? 10 : 5;

			int tesselatorCheck = -1;
			float fullTick = (float)ticks + partialTick;
			RenderSystem.setShader(GameRenderer::getParticleShader);
			BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

			for(int roofZ = floorZ - renderDistance; roofZ <= floorZ + renderDistance; ++roofZ) {
				for(int roofX = floorX - renderDistance; roofX <= floorX + renderDistance; ++roofX) {
					int rainS = (roofZ - floorZ + 16) * 32 + roofX - floorX + 16;
					double rainX = (double) TFWeatherRenderer.rainxs[rainS] * 0.5D;
					double rainZ = (double) TFWeatherRenderer.rainzs[rainS] * 0.5D;
					mutableBlockPos.set(roofX, camY, roofZ);
					int lastBadYLevel = Integer.MIN_VALUE;

					for (int roofY = minecraft.level.getMinBuildHeight(); roofY < minecraft.level.getMaxBuildHeight(); roofY++) {
						boolean skipLoop = roofY == lastBadYLevel + 1; // Cloud can't rain if there is an invalid blockState right below it, so might as well skip the loop
						BlockPos pos = new BlockPos(roofX, roofY, roofZ);
						if (Heightmap.Types.MOTION_BLOCKING.isOpaque().test(minecraft.level.getBlockState(pos))) lastBadYLevel = roofY; // Check if we skip next loop
						if (skipLoop) continue;

						if (minecraft.level.getBlockState(pos).getBlock() instanceof CloudBlock cloudBlock) {
							Pair<Biome.Precipitation, Float> precipitationRainLevelPair = cloudBlock.getCurrentPrecipitation(pos, minecraft.level, minecraft.level.getRainLevel(partialTick));
							if (precipitationRainLevelPair.getLeft() == Biome.Precipitation.NONE) continue; // No rain no gain

							int highestRainyBlock = roofY;
							for (int y = roofY - 1; y > minecraft.level.getMinBuildHeight(); y--) {
								if (!Heightmap.Types.MOTION_BLOCKING.isOpaque().test(minecraft.level.getBlockState(pos.atY(y)))) highestRainyBlock = y;
								else break;
							}
							if (highestRainyBlock == roofY) continue;

							int botY = Math.max(highestRainyBlock, floorY - renderDistance);
							int topY = Math.min(roofY, floorY + renderDistance);
							if (topY - botY <= 0) continue;

							RandomSource random = RandomSource.create((long) roofX * roofX * 3121 + roofX * 45238971L ^ (long) roofZ * roofZ * 418711 + roofZ * 13761L);
							if (precipitationRainLevelPair.getLeft() == Biome.Precipitation.RAIN) {
								if (tesselatorCheck != 0) {
									if (tesselatorCheck >= 0) tesselator.end();

									tesselatorCheck = 0;
									RenderSystem.setShaderTexture(0, TFWeatherRenderer.RAIN_TEXTURES);
									bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
								}

								int offset = ticks + roofX * roofX * 3121 + roofX * 45238971 + roofZ * roofZ * 418711 + roofZ * 13761 & 31;
								float uvOffset = -((float) offset + partialTick) / 32.0F * (3.0F + random.nextFloat());
								double xDiff = (double) roofX + 0.5D - camX;
								double zDiff = (double) roofZ + 0.5D - camZ;
								float distance = (float) Math.sqrt(xDiff * xDiff + zDiff * zDiff) / (float) renderDistance;
								float alpha = ((1.0F - distance * distance) * 0.5F + 0.5F) * precipitationRainLevelPair.getRight();
								mutableBlockPos.set(roofX, Math.max(highestRainyBlock, floorY), roofZ);
								int lightColor = LevelRenderer.getLightColor(minecraft.level, mutableBlockPos);

								bufferbuilder.vertex((double) roofX - camX - rainX + 0.5D, (double) topY - camY, (double) roofZ - camZ - rainZ + 0.5D).uv(0.0F, (float) botY * 0.25F + uvOffset).color(1.0F, 1.0F, 1.0F, alpha).uv2(lightColor).endVertex();
								bufferbuilder.vertex((double) roofX - camX + rainX + 0.5D, (double) topY - camY, (double) roofZ - camZ + rainZ + 0.5D).uv(1.0F, (float) botY * 0.25F + uvOffset).color(1.0F, 1.0F, 1.0F, alpha).uv2(lightColor).endVertex();
								bufferbuilder.vertex((double) roofX - camX + rainX + 0.5D, (double) botY - camY, (double) roofZ - camZ + rainZ + 0.5D).uv(1.0F, (float) topY * 0.25F + uvOffset).color(1.0F, 1.0F, 1.0F, alpha).uv2(lightColor).endVertex();
								bufferbuilder.vertex((double) roofX - camX - rainX + 0.5D, (double) botY - camY, (double) roofZ - camZ - rainZ + 0.5D).uv(0.0F, (float) topY * 0.25F + uvOffset).color(1.0F, 1.0F, 1.0F, alpha).uv2(lightColor).endVertex();
							} else if (precipitationRainLevelPair.getLeft() == Biome.Precipitation.SNOW) {
								if (tesselatorCheck != 1) {
									if (tesselatorCheck >= 0) tesselator.end();

									tesselatorCheck = 1;
									RenderSystem.setShaderTexture(0, TFWeatherRenderer.SNOW_TEXTURES);
									bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
								}

								float offset = -((float) (ticks & 511) + partialTick) / 512.0F;
								float uOffset = (float) (random.nextDouble() + (double) fullTick * 0.01D * (double) ((float) random.nextGaussian()));
								float vOffset = (float) (random.nextDouble() + (double) (fullTick * (float) random.nextGaussian()) * 0.001D);
								double xDiff = (double) roofX + 0.5D - camX;
								double zDiff = (double) roofZ + 0.5D - camZ;
								float distance = (float) Math.sqrt(xDiff * xDiff + zDiff * zDiff) / (float) renderDistance;
								float alpha = ((1.0F - distance * distance) * 0.3F + 0.5F) * precipitationRainLevelPair.getRight();
								mutableBlockPos.set(roofX, Math.max(highestRainyBlock, floorY), roofZ);

								int lightColor = LevelRenderer.getLightColor(minecraft.level, mutableBlockPos);
								int v = lightColor >> 16 & '\uffff';
								int u = lightColor & '\uffff';
								v = (v * 3 + 240) / 4;
								u = (u * 3 + 240) / 4;

								bufferbuilder.vertex((double) roofX - camX - rainX + 0.5D, (double) topY - camY, (double) roofZ - camZ - rainZ + 0.5D).uv(0.0F + uOffset, (float) botY * 0.25F + offset + vOffset).color(1.0F, 1.0F, 1.0F, alpha).uv2(u, v).endVertex();
								bufferbuilder.vertex((double) roofX - camX + rainX + 0.5D, (double) topY - camY, (double) roofZ - camZ + rainZ + 0.5D).uv(1.0F + uOffset, (float) botY * 0.25F + offset + vOffset).color(1.0F, 1.0F, 1.0F, alpha).uv2(u, v).endVertex();
								bufferbuilder.vertex((double) roofX - camX + rainX + 0.5D, (double) botY - camY, (double) roofZ - camZ + rainZ + 0.5D).uv(1.0F + uOffset, (float) topY * 0.25F + offset + vOffset).color(1.0F, 1.0F, 1.0F, alpha).uv2(u, v).endVertex();
								bufferbuilder.vertex((double) roofX - camX - rainX + 0.5D, (double) botY - camY, (double) roofZ - camZ - rainZ + 0.5D).uv(0.0F + uOffset, (float) topY * 0.25F + offset + vOffset).color(1.0F, 1.0F, 1.0F, alpha).uv2(u, v).endVertex();
							}
						}
					}
				}
			}

			if (tesselatorCheck >= 0) tesselator.end();

			RenderSystem.enableCull();
			RenderSystem.disableBlend();
			lightTexture.turnOffLightLayer();
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
			}

			if (minecraft.player != null && HostileMountEvents.isRidingUnfriendly(minecraft.player)) {
				if (minecraft.gui != null) {
					minecraft.gui.setOverlayMessage(Component.empty(), false);
				}
			}
		}
	}

	public static int time = 0;
	private static int rotationTickerI = 0;
	private static int sineTickerI = 0;
	public static float rotationTicker = 0;
	public static float sineTicker = 0;
	public static final float PI = (float) Math.PI;
	private static final int SINE_TICKER_BOUND = (int) ((PI * 200.0F) - 1.0F);
	private static float intensity = 0.0F;

	@SubscribeEvent
	public static void clientTick(TickEvent.ClientTickEvent event) {
		if (event.phase != TickEvent.Phase.END) return;
		Minecraft mc = Minecraft.getInstance();
		float partial = mc.getFrameTime();

		if (!mc.isPaused()) {
			time++;

			rotationTickerI = (rotationTickerI >= 359 ? 0 : rotationTickerI + 1);
			sineTickerI = (sineTickerI >= SINE_TICKER_BOUND ? 0 : sineTickerI + 1);

			rotationTicker = rotationTickerI + partial;
			sineTicker = sineTicker + partial;
		}

		if (!mc.isPaused()) {
			BugModelAnimationHelper.animate();
			DimensionSpecialEffects info = DimensionSpecialEffectsManager.getForType(TwilightForestMod.prefix("renderer"));

			// add weather box if needed
			if (mc.level != null && info instanceof TwilightForestRenderInfo) {
				TFWeatherRenderer.tick();
			}

			if (TFConfig.CLIENT_CONFIG.firstPersonEffects.get() && mc.level != null && mc.player != null) {
				HashSet<ChunkPos> chunksInRange = new HashSet<>();
				for (int x = -16; x <= 16; x += 16) {
					for (int z = -16; z <= 16; z += 16) {
						chunksInRange.add(new ChunkPos((int) (mc.player.getX() + x) >> 4, (int) (mc.player.getZ() + z) >> 4));
					}
				}
				for (ChunkPos pos : chunksInRange) {
					if (mc.level.getChunk(pos.x, pos.z, ChunkStatus.FULL, false) != null) {
						List<BlockEntity> beanstalksInChunk = mc.level.getChunk(pos.x, pos.z).getBlockEntities().values().stream()
								.filter(blockEntity -> blockEntity instanceof GrowingBeanstalkBlockEntity beanstalkBlock && beanstalkBlock.isBeanstalkRumbling())
								.toList();
						if (!beanstalksInChunk.isEmpty()) {
							BlockEntity beanstalk = beanstalksInChunk.get(0);
							Player player = mc.player;
							intensity = (float) (1.0F - mc.player.distanceToSqr(Vec3.atCenterOf(beanstalk.getBlockPos())) / Math.pow(16, 2));
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
			if (mc.level != null && TFConfig.CLIENT_CONFIG.cloudBlockRainParticles.get()) { // Semi vanilla copy of the weather tick, but made to work with cloud blocks instead
				RandomSource randomsource = RandomSource.create((long) mc.levelRenderer.getTicks() * 312987231L);
				BlockPos camPos = BlockPos.containing(mc.gameRenderer.getMainCamera().getPosition());
				BlockPos particlePos = null;
				int particleCount = 100 / (mc.options.particles().get() == ParticleStatus.DECREASED ? 2 : 1);

				boolean yetToMakeASound = true;

				for (int i = 0; i < particleCount; ++i) {
					int x = randomsource.nextInt(21) - 10;
					int z = randomsource.nextInt(21) - 10;

					BlockPos randomPos = camPos.offset(x, 0, z);
					int lastBadYLevel = Integer.MIN_VALUE;
					for (int y = mc.level.getMinBuildHeight(); y < mc.level.getMaxBuildHeight(); y++) {

						boolean skipLoop = y == lastBadYLevel + 1; // Cloud can't rain if there is an invalid blockState right below it, so might as well skip the loop
						BlockPos cloudPos = randomPos.atY(y);
						if (Heightmap.Types.MOTION_BLOCKING.isOpaque().test(mc.level.getBlockState(cloudPos))) lastBadYLevel = y; // Check if we skip next loop
						if (skipLoop) continue;

						if (mc.level.getBlockState(cloudPos).getBlock() instanceof CloudBlock cloudBlock) {
							Pair<Biome.Precipitation, Float> pair = cloudBlock.getCurrentPrecipitation(cloudPos, mc.level, mc.level.getRainLevel(partial));
							if (pair.getLeft() != Biome.Precipitation.RAIN || pair.getRight() <= 0.0F || mc.level.getRandom().nextFloat() >= pair.getRight()) continue;
							int highestRainyBlock = y;
							for (int y1 = y - 1; y1 > mc.level.getMinBuildHeight(); y1--) {
								if (!Heightmap.Types.MOTION_BLOCKING.isOpaque().test(mc.level.getBlockState(cloudPos.atY(y1)))) highestRainyBlock = y1;
								else break;
							}
							if (highestRainyBlock == y) continue;

							BlockPos highestRainyPos = cloudPos.atY(highestRainyBlock);

							if (yetToMakeASound && particlePos != null && randomsource.nextInt(3) < mc.levelRenderer.rainSoundTime++) {
								mc.levelRenderer.rainSoundTime = 0;
								if (particlePos.getY() > camPos.getY() + 1 && mc.level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, camPos).getY() > Mth.floor((float) camPos.getY())) {
									mc.level.playLocalSound(particlePos, SoundEvents.WEATHER_RAIN_ABOVE, SoundSource.WEATHER, 0.1F, 0.5F, false);
								} else {
									mc.level.playLocalSound(particlePos, SoundEvents.WEATHER_RAIN, SoundSource.WEATHER, 0.2F, 1.0F, false);
								}
								yetToMakeASound = false;
							}

							if (highestRainyPos.getY() > mc.level.getMinBuildHeight() && highestRainyPos.getY() <= camPos.getY() + 10 && highestRainyPos.getY() >= camPos.getY() - 10) {
								particlePos = highestRainyPos.below();
								if (mc.options.particles().get() == ParticleStatus.MINIMAL) break;

								double particleX = randomsource.nextDouble();
								double particleZ = randomsource.nextDouble();
								BlockState blockstate = mc.level.getBlockState(particlePos);
								FluidState fluidstate = mc.level.getFluidState(particlePos);
								VoxelShape voxelshape = blockstate.getCollisionShape(mc.level, particlePos);
								double voxelMax = voxelshape.max(Direction.Axis.Y, particleX, particleZ);
								double fluidMax = fluidstate.getHeight(mc.level, particlePos);
								double particleY = Math.max(voxelMax, fluidMax);
								ParticleOptions particleoptions = !fluidstate.is(FluidTags.LAVA) && !blockstate.is(Blocks.MAGMA_BLOCK) && !CampfireBlock.isLitCampfire(blockstate) ? ParticleTypes.RAIN : ParticleTypes.SMOKE;
								mc.level.addParticle(particleoptions, (double) particlePos.getX() + particleX, (double) particlePos.getY() + particleY, (double) particlePos.getZ() + particleZ, 0.0D, 0.0D, 0.0D);
							}
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void camera(ViewportEvent.ComputeCameraAngles event) {
		if (TFConfig.CLIENT_CONFIG.firstPersonEffects.get() && !Minecraft.getInstance().isPaused() && intensity > 0 && Minecraft.getInstance().player != null) {
			event.setYaw((float) Mth.lerp(event.getPartialTick(), event.getYaw(), event.getYaw() + (Minecraft.getInstance().player.getRandom().nextFloat() * 2F - 1F) * intensity));
			event.setPitch((float) Mth.lerp(event.getPartialTick(), event.getPitch(), event.getPitch() + (Minecraft.getInstance().player.getRandom().nextFloat() * 2F - 1F) * intensity));
			event.setRoll((float) Mth.lerp(event.getPartialTick(), event.getRoll(), event.getRoll() + (Minecraft.getInstance().player.getRandom().nextFloat() * 2F - 1F) * intensity));
			intensity = 0F;
		}
	}

	private static final MutableComponent WIP_TEXT_0 = Component.translatable("misc.twilightforest.wip0").setStyle(Style.EMPTY.withColor(ChatFormatting.RED));
	private static final MutableComponent WIP_TEXT_1 = Component.translatable("misc.twilightforest.wip1").setStyle(Style.EMPTY.withColor(ChatFormatting.RED));
	private static final MutableComponent NYI_TEXT = Component.translatable("misc.twilightforest.nyi").setStyle(Style.EMPTY.withColor(ChatFormatting.RED));

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

		if (!visible && event.getRenderer().getModel() instanceof HeadedModel headedModel) {
			headedModel.getHead().visible = false;
			if (event.getRenderer().getModel() instanceof HumanoidModel<?> humanoidModel) {
				humanoidModel.hat.visible = false;
			}
		}
	}

	private static boolean areCuriosEquipped(LivingEntity entity) {
		if (ModList.get().isLoaded("curios")) {
			return CuriosCompat.isTrophyCurioEquipped(entity) || CuriosCompat.isSkullCurioEquipped(entity);
		}
		return false;
	}

	@SubscribeEvent
	public static void translateBookAuthor(ItemTooltipEvent event) {
		ItemStack stack = event.getItemStack();
		if (stack.getItem() instanceof WrittenBookItem && stack.hasTag()) {
			CompoundTag tag = stack.getOrCreateTag();
			if (tag.contains(TwilightForestMod.ID + ":book")) {
				List<Component> components = event.getToolTip();
				for (Component component : components) {
					if (component.toString().contains("book.byAuthor")) {
						components.set(components.indexOf(component), (Component.translatable("book.byAuthor", Component.translatable(TwilightForestMod.ID + ".book.author"))).withStyle(component.getStyle()));
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void unrenderMiniStructureHitbox(RenderHighlightEvent.Block event) {
		BlockState state = event.getCamera().getEntity().level().getBlockState(event.getTarget().getBlockPos());
		if (state.getBlock() instanceof MiniatureStructureBlock) {
			event.setCanceled(true);
		}
	}
}
