package twilightforest;

import com.google.common.collect.Maps;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DataPackRegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import twilightforest.advancements.TFAdvancements;
import twilightforest.capabilities.CapabilityList;
import twilightforest.client.ClientInitiator;
import twilightforest.command.TFCommand;
import twilightforest.compat.curios.CuriosCompat;
import twilightforest.compat.top.TopCompat;
import twilightforest.data.custom.stalactites.entry.Stalactite;
import twilightforest.dispenser.TFDispenserBehaviors;
import twilightforest.init.*;
import twilightforest.init.custom.*;
import twilightforest.network.TFPacketHandler;
import twilightforest.util.WoodPalette;
import twilightforest.world.components.BiomeGrassColors;
import twilightforest.world.components.biomesources.LandmarkBiomeSource;
import twilightforest.world.components.biomesources.TFBiomeProvider;
import twilightforest.world.components.chunkgenerators.ChunkGeneratorTwilight;
import twilightforest.init.TFStructureProcessors;

import java.util.Locale;
import java.util.Objects;

@Mod(TwilightForestMod.ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TwilightForestMod {

	public static final String ID = "twilightforest";
	public static final String REGISTRY_NAMESPACE = "twilight";

	private static final String MODEL_DIR = "textures/model/";
	private static final String GUI_DIR = "textures/gui/";
	private static final String ENVIRO_DIR = "textures/environment/";
	// odd one out, as armor textures are a stringy mess at present
	public static final String ARMOR_DIR = ID + ":textures/armor/";

	public static final GameRules.Key<GameRules.BooleanValue> ENFORCED_PROGRESSION_RULE = GameRules.register("tfEnforcedProgression", GameRules.Category.UPDATES, GameRules.BooleanValue.create(true)); //Putting it in UPDATES since other world stuff is here

	public static final Logger LOGGER = LogManager.getLogger(ID);

	private static final Rarity rarity = Rarity.create("TWILIGHT", ChatFormatting.DARK_GREEN);

	public TwilightForestMod() {
		{
			final Pair<TFConfig.Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(TFConfig.Common::new);
			ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, specPair.getRight());
			TFConfig.COMMON_CONFIG = specPair.getLeft();
		}
		{
			final Pair<TFConfig.Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(TFConfig.Client::new);
			ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, specPair.getRight());
			TFConfig.CLIENT_CONFIG = specPair.getLeft();
		}

		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientInitiator::call);
		MinecraftForge.EVENT_BUS.addListener(this::registerCommands);
		MinecraftForge.EVENT_BUS.addGenericListener(Level.class, CapabilityList::attachLevelCapability);
		MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, CapabilityList::attachEntityCapability);
		MinecraftForge.EVENT_BUS.addListener(Stalactite::reloadStalactites);

		IEventBus modbus = FMLJavaModLoadingContext.get().getModEventBus();

		TFBannerPatterns.BANNER_PATTERNS.register(modbus);
		TFBlockEntities.BLOCK_ENTITIES.register(modbus);
		TFBlocks.BLOCKS.register(modbus);
		TFLoot.CONDITIONS.register(modbus);
		TFMenuTypes.CONTAINERS.register(modbus);
		TFEnchantments.ENCHANTMENTS.register(modbus);
		TFEntities.ENTITIES.register(modbus);
		BiomeLayerTypes.BIOME_LAYER_TYPES.register(modbus);
		BiomeLayerStack.BIOME_LAYER_STACKS.register(modbus);
		TFFeatures.FEATURES.register(modbus);
		TFFeatureModifiers.FOLIAGE_PLACERS.register(modbus);
		TFLoot.FUNCTIONS.register(modbus);
		TFItems.ITEMS.register(modbus);
		TFLootModifiers.LOOT_MODIFIERS.register(modbus);
		TFMobEffects.MOB_EFFECTS.register(modbus);
		TFParticleType.PARTICLE_TYPES.register(modbus);
		TFPOITypes.POIS.register(modbus);
		TFFeatureModifiers.PLACEMENT_MODIFIERS.register(modbus);
		TFRecipes.RECIPE_SERIALIZERS.register(modbus);
		TFRecipes.RECIPE_TYPES.register(modbus);
		TFSounds.SOUNDS.register(modbus);
		TFEntities.SPAWN_EGGS.register(modbus);
		TFStats.STATS.register(modbus);
		TFStructurePieceTypes.STRUCTURE_PIECE_TYPES.register(modbus);
		TFStructureProcessors.STRUCTURE_PROCESSORS.register(modbus);
		TFStructurePlacementTypes.STRUCTURE_PLACEMENT_TYPES.register(modbus);
		TFStructureTypes.STRUCTURE_TYPES.register(modbus);
		TFFeatureModifiers.TREE_DECORATORS.register(modbus);
		TFFeatureModifiers.TRUNK_PLACERS.register(modbus);

		DwarfRabbitVariant.DWARF_RABBITS.register(modbus);
		TinyBirdVariant.TINY_BIRDS.register(modbus);
		WoodPalettes.WOOD_PALETTES.register(modbus);

		modbus.addListener(this::sendIMCs);
		modbus.addListener(CapabilityList::registerCapabilities);

		if (ModList.get().isLoaded("curios")) {
			Bindings.getForgeBus().get().addListener(CuriosCompat::keepCurios);
		}

		BiomeGrassColors.init();
	}

	@SubscribeEvent
	public static void setRegistriesForDatapack(DataPackRegistryEvent.NewRegistry event) {
		event.dataPackRegistry(WoodPalettes.WOOD_PALETTE_TYPE_KEY, WoodPalette.CODEC);
		event.dataPackRegistry(BiomeLayerStack.BIOME_STACK_KEY, BiomeLayerStack.DISPATCH_CODEC);
	}

	@SubscribeEvent
	public static void addClassicPack(AddPackFindersEvent event) {
		if (event.getPackType() == PackType.CLIENT_RESOURCES) {
			var resourcePath = ModList.get().getModFileById(TwilightForestMod.ID).getFile().findResource("classic");
			var pack = Pack.readMetaAndCreate("builtin/twilight_forest_classic_resources", Component.literal("Twilight Classic"), false,
					path -> new PathPackResources(path, resourcePath, true), PackType.CLIENT_RESOURCES, Pack.Position.TOP, PackSource.BUILT_IN);
			event.addRepositorySource(consumer -> consumer.accept(pack));
		}
	}

	@SubscribeEvent
	public static void registerSerializers(RegisterEvent evt) {
		if (Objects.equals(evt.getForgeRegistry(), ForgeRegistries.RECIPE_SERIALIZERS)) {
			Registry.register(BuiltInRegistries.BIOME_SOURCE, TwilightForestMod.prefix("twilight_biomes"), TFBiomeProvider.TF_CODEC);
			Registry.register(BuiltInRegistries.BIOME_SOURCE, TwilightForestMod.prefix("landmarks"), LandmarkBiomeSource.CODEC);

			Registry.register(BuiltInRegistries.CHUNK_GENERATOR, TwilightForestMod.prefix("structure_locating_wrapper"), ChunkGeneratorTwilight.CODEC);
		}
	}

	public void sendIMCs(InterModEnqueueEvent evt) {
		if (ModList.get().isLoaded("curios")) {
			CuriosCompat.handleCuriosIMCs();
		}
		if (ModList.get().isLoaded("theoneprobe")) {
			InterModComms.sendTo("theoneprobe", "getTheOneProbe", TopCompat::new);
		}
	}

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent evt) {
		TFPacketHandler.init();
		TFAdvancements.init();

		evt.enqueueWork(() -> {
			TFBlocks.tfCompostables();
			TFBlocks.tfBurnables();
			TFBlocks.tfPots();
			TFSounds.registerParrotSounds();
			TFDispenserBehaviors.init();
			TFStats.init();

			CauldronInteraction.WATER.put(TFItems.ARCTIC_HELMET.get(), CauldronInteraction.DYED_ITEM);
			CauldronInteraction.WATER.put(TFItems.ARCTIC_CHESTPLATE.get(), CauldronInteraction.DYED_ITEM);
			CauldronInteraction.WATER.put(TFItems.ARCTIC_LEGGINGS.get(), CauldronInteraction.DYED_ITEM);
			CauldronInteraction.WATER.put(TFItems.ARCTIC_BOOTS.get(), CauldronInteraction.DYED_ITEM);

			AxeItem.STRIPPABLES = Maps.newHashMap(AxeItem.STRIPPABLES);
			AxeItem.STRIPPABLES.put(TFBlocks.TWILIGHT_OAK_LOG.get(), TFBlocks.STRIPPED_TWILIGHT_OAK_LOG.get());
			AxeItem.STRIPPABLES.put(TFBlocks.CANOPY_LOG.get(), TFBlocks.STRIPPED_CANOPY_LOG.get());
			AxeItem.STRIPPABLES.put(TFBlocks.MANGROVE_LOG.get(), TFBlocks.STRIPPED_MANGROVE_LOG.get());
			AxeItem.STRIPPABLES.put(TFBlocks.DARK_LOG.get(), TFBlocks.STRIPPED_DARK_LOG.get());
			AxeItem.STRIPPABLES.put(TFBlocks.TIME_LOG.get(), TFBlocks.STRIPPED_TIME_LOG.get());
			AxeItem.STRIPPABLES.put(TFBlocks.TRANSFORMATION_LOG.get(), TFBlocks.STRIPPED_TRANSFORMATION_LOG.get());
			AxeItem.STRIPPABLES.put(TFBlocks.MINING_LOG.get(), TFBlocks.STRIPPED_MINING_LOG.get());
			AxeItem.STRIPPABLES.put(TFBlocks.SORTING_LOG.get(), TFBlocks.STRIPPED_SORTING_LOG.get());

			AxeItem.STRIPPABLES.put(TFBlocks.TWILIGHT_OAK_WOOD.get(), TFBlocks.STRIPPED_TWILIGHT_OAK_WOOD.get());
			AxeItem.STRIPPABLES.put(TFBlocks.CANOPY_WOOD.get(), TFBlocks.STRIPPED_CANOPY_WOOD.get());
			AxeItem.STRIPPABLES.put(TFBlocks.MANGROVE_WOOD.get(), TFBlocks.STRIPPED_MANGROVE_WOOD.get());
			AxeItem.STRIPPABLES.put(TFBlocks.DARK_WOOD.get(), TFBlocks.STRIPPED_DARK_WOOD.get());
			AxeItem.STRIPPABLES.put(TFBlocks.TIME_WOOD.get(), TFBlocks.STRIPPED_TIME_WOOD.get());
			AxeItem.STRIPPABLES.put(TFBlocks.TRANSFORMATION_WOOD.get(), TFBlocks.STRIPPED_TRANSFORMATION_WOOD.get());
			AxeItem.STRIPPABLES.put(TFBlocks.MINING_WOOD.get(), TFBlocks.STRIPPED_MINING_WOOD.get());
			AxeItem.STRIPPABLES.put(TFBlocks.SORTING_WOOD.get(), TFBlocks.STRIPPED_SORTING_WOOD.get());
		});
	}

	public void registerCommands(RegisterCommandsEvent event) {
		TFCommand.register(event.getDispatcher());
	}

	public static ResourceLocation prefix(String name) {
		return new ResourceLocation(ID, name.toLowerCase(Locale.ROOT));
	}

	public static ResourceLocation namedRegistry(String name) {
		return new ResourceLocation(REGISTRY_NAMESPACE, name.toLowerCase(Locale.ROOT));
	}

	public static ResourceLocation getModelTexture(String name) {
		return new ResourceLocation(ID, MODEL_DIR + name);
	}

	public static ResourceLocation getGuiTexture(String name) {
		return new ResourceLocation(ID, GUI_DIR + name);
	}

	public static ResourceLocation getEnvTexture(String name) {
		return new ResourceLocation(ID, ENVIRO_DIR + name);
	}

	public static Rarity getRarity() {
		return rarity;
	}
}
