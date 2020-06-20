package twilightforest.world.feature;

import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.SphereReplaceConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import twilightforest.TwilightForestMod;
import twilightforest.features.GenDruidHut;
import twilightforest.features.TFGenGraveyard;
import twilightforest.world.feature.config.CaveStalactiteConfig;
import twilightforest.world.feature.config.TFTreeFeatureConfig;

//I'd call this TFFeatures, but that'd be confused with TFFeature.
public class TFBiomeFeatures {

	public static final DeferredRegister<Feature<?>> FEATURES = new DeferredRegister<>(ForgeRegistries.FEATURES, TwilightForestMod.ID);

	public static final RegistryObject<Feature<NoFeatureConfig>> BIG_MUSHGLOOM = FEATURES.register("big_mushgloom", () ->
			new TFGenBigMushgloom(NoFeatureConfig::deserialize));
	public static final RegistryObject<Feature<NoFeatureConfig>> CANOPY_MUSHROOM = FEATURES.register("canopy_mushroom", () ->
			new TFGenCanopyMushroom(NoFeatureConfig::deserialize));
	public static final RegistryObject<Feature<TFTreeFeatureConfig>> CANOPY_OAK = FEATURES.register("canopy_oak", () ->
			new TFGenCanopyOak(TFTreeFeatureConfig::deserialize));
	public static final RegistryObject<Feature<TFTreeFeatureConfig>> CANOPY_TREE = FEATURES.register("canopy_tree", () ->
			new TFGenCanopyTree(TFTreeFeatureConfig::deserialize));
	public static final RegistryObject<Feature<CaveStalactiteConfig>> CAVE_STALACTITE = FEATURES.register("cave_stalactite", () ->
			new TFGenCaveStalactite(CaveStalactiteConfig::deserialize));
	public static final RegistryObject<Feature<TFTreeFeatureConfig>> DARK_CANOPY_TREE = FEATURES.register("dark_canopy_tree", () ->
			new TFGenDarkCanopyTree(TFTreeFeatureConfig::deserialize));
	public static final RegistryObject<Feature<NoFeatureConfig>> DRUID_HUT = FEATURES.register("druid_hut", () ->
			new GenDruidHut(NoFeatureConfig::deserialize));
	public static final RegistryObject<Feature<NoFeatureConfig>> FALLEN_HOLLOW_LOG = FEATURES.register("fallen_hollow_log", () ->
			new TFGenFallenHollowLog(NoFeatureConfig::deserialize));
	public static final RegistryObject<Feature<NoFeatureConfig>> FALLEN_LEAVES = FEATURES.register("fallen_leaves", () ->
			new TFGenFallenLeaves(NoFeatureConfig::deserialize));
	public static final RegistryObject<Feature<NoFeatureConfig>> FALLEN_SMALL_LOG = FEATURES.register("fallen_small_log", () ->
			new TFGenFallenSmallLog(NoFeatureConfig::deserialize));
	public static final RegistryObject<Feature<BlockStateFeatureConfig>> FIRE_JET = FEATURES.register("fire_jet", () ->
			new TFGenFireJet(BlockStateFeatureConfig::deserialize));
	public static final RegistryObject<Feature<NoFeatureConfig>> FOUNDATION = FEATURES.register("foundation", () ->
			new TFGenFoundation(NoFeatureConfig::deserialize));
	public static final RegistryObject<Feature<NoFeatureConfig>> GRAVEYARD = FEATURES.register("graveyard", () ->
			new TFGenGraveyard(NoFeatureConfig::deserialize));
	public static final RegistryObject<Feature<NoFeatureConfig>> GROVE_RUINS = FEATURES.register("grove_ruins", () ->
			new TFGenGroveRuins(NoFeatureConfig::deserialize));
	public static final RegistryObject<Feature<NoFeatureConfig>> HANGING_LAMPS = FEATURES.register("hanging_lamps", () ->
			new TFGenHangingLamps(NoFeatureConfig::deserialize));
	public static final RegistryObject<Feature<TFTreeFeatureConfig>> HOLLOW_STUMP = FEATURES.register("hollow_stump", () ->
			new TFGenHollowStump(TFTreeFeatureConfig::deserialize));
	public static final RegistryObject<Feature<TFTreeFeatureConfig>> HOLLOW_TREE = FEATURES.register("hollow_tree", () ->
			new TFGenHollowTree(TFTreeFeatureConfig::deserialize));
	public static final RegistryObject<Feature<NoFeatureConfig>> HUGE_LILY_PAD = FEATURES.register("huge_lily_pad", () ->
			new TFGenHugeLilyPad(NoFeatureConfig::deserialize));
	public static final RegistryObject<Feature<NoFeatureConfig>> HUGE_WATER_LILY = FEATURES.register("huge_water_lily", () ->
			new TFGenHugeWaterLily(NoFeatureConfig::deserialize));
	public static final RegistryObject<Feature<BlockStateFeatureConfig>> LAMPPOSTS = FEATURES.register("lampposts", () ->
			new TFGenLampposts(BlockStateFeatureConfig::deserialize));
	public static final RegistryObject<Feature<TFTreeFeatureConfig>> LARGE_WINTER_TREE = FEATURES.register("large_winter_tree", () ->
			new TFGenLargeWinter(TFTreeFeatureConfig::deserialize));
	public static final RegistryObject<Feature<TFTreeFeatureConfig>> MANGROVE_TREE = FEATURES.register("mangrove_tree", () ->
			new TFGenMangroveTree(TFTreeFeatureConfig::deserialize));
	public static final RegistryObject<Feature<TFTreeFeatureConfig>> MINERS_TREE = FEATURES.register("miners_tree", () ->
			new TFGenMinersTree(TFTreeFeatureConfig::deserialize));
	public static final RegistryObject<Feature<NoFeatureConfig>> MONOLITH = FEATURES.register("monolith", () ->
			new TFGenMonolith(NoFeatureConfig::deserialize));
	public static final RegistryObject<Feature<SphereReplaceConfig>> MYCELIUM_BLOB = FEATURES.register("mycelium_blob", () ->
			new TFGenMyceliumBlob(SphereReplaceConfig::deserialize));
	public static final RegistryObject<Feature<CaveStalactiteConfig>> OUTSIDE_STALAGMITE = FEATURES.register("outside_stalagmite", () ->
			new TFGenOutsideStalagmite(CaveStalactiteConfig::deserialize));
	public static final RegistryObject<Feature<NoFeatureConfig>> PENGUINS = FEATURES.register("penguins", () ->
			new TFGenPenguins(NoFeatureConfig::deserialize));
	public static final RegistryObject<Feature<NoFeatureConfig>> PLANT_ROOTS = FEATURES.register("plant_roots", () ->
			new TFGenPlantRoots(NoFeatureConfig::deserialize));
	public static final RegistryObject<Feature<TFTreeFeatureConfig>> SORTING_TREE = FEATURES.register("sorting_tree", () ->
			new TFGenSortingTree(TFTreeFeatureConfig::deserialize));
	public static final RegistryObject<Feature<NoFeatureConfig>> STONE_CIRCLE = FEATURES.register("stone_circle", () ->
			new TFGenStoneCircle(NoFeatureConfig::deserialize));
	public static final RegistryObject<Feature<NoFeatureConfig>> THORNS = FEATURES.register("thorns", () ->
			new TFGenThorns(NoFeatureConfig::deserialize));
	public static final RegistryObject<Feature<NoFeatureConfig>> TORCH_BERRIES = FEATURES.register("torch_berries", () ->
			new TFGenTorchBerries(NoFeatureConfig::deserialize));
	public static final RegistryObject<Feature<TFTreeFeatureConfig>> TREE_OF_TIME = FEATURES.register("tree_of_time", () ->
			new TFGenTreeOfTime(TFTreeFeatureConfig::deserialize));
	public static final RegistryObject<Feature<TFTreeFeatureConfig>> TREE_OF_TRANSFORMATION = FEATURES.register("tree_of_transformation", () ->
			new TFGenTreeOfTransformation(TFTreeFeatureConfig::deserialize));
	public static final RegistryObject<Feature<NoFeatureConfig>> TROLL_ROOTS = FEATURES.register("troll_roots", () ->
			new TFGenTrollRoots(NoFeatureConfig::deserialize));
	public static final RegistryObject<Feature<NoFeatureConfig>> WEBS = FEATURES.register("webs", () ->
			new TFGenWebs(NoFeatureConfig::deserialize));
	public static final RegistryObject<Feature<NoFeatureConfig>> WELL = FEATURES.register("well", () ->
			new TFGenWell(NoFeatureConfig::deserialize));
	public static final RegistryObject<Feature<NoFeatureConfig>> WOOD_ROOTS = FEATURES.register("wood_roots", () ->
			new TFGenWoodRoots(NoFeatureConfig::deserialize));
}
