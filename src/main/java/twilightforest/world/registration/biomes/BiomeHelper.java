package twilightforest.world.registration.biomes;

import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.placement.AquaticPlacements;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import twilightforest.client.particle.TFParticleType;
import twilightforest.entity.TFEntities;
import twilightforest.world.registration.ConfiguredWorldCarvers;
import twilightforest.world.registration.features.TFConfiguredFeatures;
import twilightforest.world.registration.features.TFPlacedFeatures;

public abstract class BiomeHelper {

    public static BiomeGenerationSettings.Builder twilightForestGen() {
		BiomeGenerationSettings.Builder biome = defaultGenSettingBuilder();
		commonFeatures(biome);

		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_DENSE_CANOPY_TREES.get()));
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_DENSE_TREE.get()));
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_VANILLA_TF_TREES.get()));
		//biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_DEFAULT_FALLEN_LOGS.get()));
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TreePlacements.JUNGLE_BUSH);
		addForestVegetation(biome);
		addHollowOakTrees(biome);
		
		return biome;
	}
	
	public static BiomeGenerationSettings.Builder denseForestGen() {
		BiomeGenerationSettings.Builder biome = defaultGenSettingBuilder();
		commonFeatures(biome);

		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_VANILLA_TF_TREES.get()));
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_TWILIGHT_OAK_TREE.get()));
		addForestVegetation(biome);
		addCanopyTrees(biome);
		addHollowOakTrees(biome);
		
		return biome;
	}

	public static BiomeGenerationSettings.Builder fireflyForestGen() {
		BiomeGenerationSettings.Builder biome = defaultGenSettingBuilder();

		commonFeatures(biome);
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_FIREFLY_FOREST_TREES.get()));
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_VANILLA_TF_TREES.get()));
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_TWILIGHT_OAK_TREE.get()));
		//biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_DEFAULT_FALLEN_LOGS.get()));
		addHollowOakTrees(biome);

		//biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_LAMPPOST_PLACER.get()));
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_MUSHGLOOM_CLUSTER.get()));
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_PUMPKIN);
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.FLOWER_FOREST_FLOWERS);
		addForestVegetation(biome);

		return biome;
	}

	public static BiomeGenerationSettings.Builder clearingGen() {
		BiomeGenerationSettings.Builder biome = defaultGenSettingBuilder();
		commonFeatures(biome);

		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.FLOWER_FOREST_FLOWERS);
		//biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_DEFAULT_FALLEN_LOGS.get()));

		addForestVegetation(biome);

		return biome;
	}
	
	public static BiomeGenerationSettings.Builder oakSavannaGen() {
		BiomeGenerationSettings.Builder biome = defaultGenSettingBuilder();
		commonFeatures(biome);
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_SAVANNAH_OAK_TREE.get()));
		//biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_DEFAULT_FALLEN_LOGS.get()));
		addHollowOakTrees(biome);

		addForestVegetation(biome);

		return biome;
	}
	
	public static BiomeGenerationSettings.Builder enchantedForestGen() {
		BiomeGenerationSettings.Builder biome = defaultGenSettingBuilder();
		commonFeatures(biome);
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_ENCHANTED_FOREST_TREES.get()));
		addCanopyTrees(biome);
		addHollowOakTrees(biome);

		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_FIDDLEHEAD.get()));
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.VINES);
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_GRASS_PLACER.get()));
		addForestVegetation(biome);

		return biome;
	}
	
	public static BiomeGenerationSettings.Builder spookyForestGen() {
		BiomeGenerationSettings.Builder biome = defaultGenSettingBuilder();

		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_TWILIGHT_OAK_TREE.get()));
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_DEAD_CANOPY_TREE.get()));

		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_PUMPKIN_LAMPPOST.get()));
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_GRASS_PLACER.get()));
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_TF_OAK_FALLEN_LOG.get()));
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_CANOPY_FALLEN_LOG.get()));
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_WEBS.get()));
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_FALLEN_LEAVES.get()));
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_PUMPKIN);
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_DEAD_BUSH);
		
		biome.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, Holder.direct(TFPlacedFeatures.PLACED_GRAVEYARD.get()));
		
		return biome;
	}
	
	public static BiomeGenerationSettings.Builder mushroomForestGen() {
		BiomeGenerationSettings.Builder biome = defaultGenSettingBuilder();
		commonFeatures(biome);

		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_MYCELIUM_BLOB.get()));

		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_VANILLA_TF_TREES.get()));
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_TWILIGHT_OAK_TREE.get()));
		addHollowOakTrees(biome);
		addCanopyTrees(biome);
		addCanopyMushrooms(biome, false);
		addForestVegetation(biome);

		return biome;
	}
	
	public static BiomeGenerationSettings.Builder denseMushroomForestGen() {
		BiomeGenerationSettings.Builder biome = defaultGenSettingBuilder();
		commonFeatures(biome);

		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_MYCELIUM_BLOB.get()));

		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_VANILLA_TF_TREES.get()));
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_TWILIGHT_OAK_TREE.get()));
		addCanopyMushrooms(biome, true);
		addCanopyTrees(biome);
		addHollowOakTrees(biome);
		addForestVegetation(biome);

		return biome;
	}

	public static BiomeGenerationSettings.Builder plateauGen() {
		return new BiomeGenerationSettings.Builder();
	}
	
	public static BiomeGenerationSettings.Builder thornlandsGen() {
		BiomeGenerationSettings.Builder biome = new BiomeGenerationSettings.Builder();

		commonFeaturesWithoutBuildings(biome);
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_THORNS.get()));
		
		return biome;
	}
	
	public static BiomeGenerationSettings.Builder highlandsGen() {
		BiomeGenerationSettings.Builder biome = new BiomeGenerationSettings.Builder();

		commonFeatures(biome);

		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_HIGHLANDS_TREES.get()));
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, MiscOverworldPlacements.FOREST_ROCK);
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_TAIGA);
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_SPRUCE_FALLEN_LOG.get()));

		addSmallStoneClusters(biome);
		addHighlandCaves(biome);

		return biome;
	}

	public static BiomeGenerationSettings.Builder streamsAndLakes(boolean isLake) {
		BiomeGenerationSettings.Builder biome = new BiomeGenerationSettings.Builder();

		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, isLake ? AquaticPlacements.SEAGRASS_DEEP : AquaticPlacements.SEAGRASS_NORMAL);

		BiomeDefaultFeatures.addDefaultOres(biome);
		BiomeDefaultFeatures.addDefaultSeagrass(biome);

		addSmallStoneClusters(biome);

		return biome;
	}
	
	public static BiomeGenerationSettings.Builder swampGen() {
		BiomeGenerationSettings.Builder biome = defaultGenSettingBuilder();
		commonFeatures(biome);
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_MANGROVE_TREE.get()));
		addSwampTrees(biome);
		addHollowOakTrees(biome);

		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_SUGAR_CANE_SWAMP);
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.VINES);
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_DEAD_BUSH);
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_MANGROVE_FALLEN_LOG.get()));

		addForestVegetation(biome);
		lilypads(biome);
		
		return biome;
	}
	
	public static BiomeGenerationSettings.Builder fireSwampGen() {
		BiomeGenerationSettings.Builder biome = defaultGenSettingBuilder();

		commonFeaturesWithoutBuildings(biome);
		addSwampTrees(biome);
		addHollowOakTrees(biome);

		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_GRASS_PLACER.get()));
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_FIRE_JET.get()));
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_SMOKER.get()));
		biome.addFeature(GenerationStep.Decoration.LAKES, Holder.direct(TFPlacedFeatures.PLACED_LAKE_LAVA.get()));
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_SUGAR_CANE_SWAMP);
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.VINES);
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.BROWN_MUSHROOM_SWAMP);
		
		return biome;
	}
	
	public static BiomeGenerationSettings.Builder darkForestGen() {
		BiomeGenerationSettings.Builder biome = new BiomeGenerationSettings.Builder();

		addDarkForestVegetation(biome);
		addForestVegetation(biome);
		addCaves(biome);
		
		return biome;
	}
	
	public static BiomeGenerationSettings.Builder darkForestCenterGen() {
		BiomeGenerationSettings.Builder biome = new BiomeGenerationSettings.Builder();

		addDarkForestVegetation(biome);
		addCaves(biome);
		
		return biome;
	}
	
	public static BiomeGenerationSettings.Builder snowyForestGen() {
		BiomeGenerationSettings.Builder biome = new BiomeGenerationSettings.Builder();

		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_SNOWY_FOREST_TREES.get()));
		biome.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, Holder.direct(TFPlacedFeatures.PLACED_SNOW_UNDER_TREES.get()));

		biome.addFeature(GenerationStep.Decoration.LAKES, Holder.direct(TFPlacedFeatures.PLACED_LAKE_WATER.get()));
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_SPRUCE_FALLEN_LOG.get()));

		BiomeDefaultFeatures.addDefaultOres(biome);
		BiomeDefaultFeatures.addSurfaceFreezing(biome);

		addCaves(biome);

		return biome;
	}
	
	public static BiomeGenerationSettings.Builder glacierGen() {
		BiomeGenerationSettings.Builder biome = new BiomeGenerationSettings.Builder();
		addCaves(biome);
		return biome;
	}
	
	public static void withWoodRoots(BiomeGenerationSettings.Builder biome) {
        biome.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Holder.direct(TFPlacedFeatures.PLACED_WOOD_ROOTS_SPREAD.get()));
	}

	public static void commonFeatures(BiomeGenerationSettings.Builder biome) {
		biome.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, Holder.direct(TFPlacedFeatures.PLACED_DRUID_HUT.get()));
		//biome.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, Holder.direct(TFPlacedFeatures.PLACED_WELL_PLACER.get()));
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_GROVE_RUINS.get()));
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_FOUNDATION.get()));

		commonFeaturesWithoutBuildings(biome);
	}

	public static void commonFeaturesWithoutBuildings(BiomeGenerationSettings.Builder biome) {
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_STONE_CIRCLE.get()));
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_OUTSIDE_STALAGMITE.get()));
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_MONOLITH.get()));
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_HOLLOW_STUMP.get()));
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_HOLLOW_LOG.get()));
	}

	public static void lilypads(BiomeGenerationSettings.Builder biome) {
    	biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_HUGE_LILY_PAD.get()));
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_HUGE_WATER_LILY.get()));
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_WATERLILY);
	}

    public static void addForestVegetation(BiomeGenerationSettings.Builder biome) {
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_FOREST_GRASS_PLACER.get()));
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_FLOWER_PLACER.get()));
	}

	public static void addDarkForestVegetation(BiomeGenerationSettings.Builder biome) {
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_DARKWOOD_TREE.get()));
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_DARK_FOREST_TREES.get()));
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_DARK_GRASS.get()));
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_DARK_FERNS.get()));
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_DARK_MUSHGLOOMS.get()));
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_DARK_DEAD_BUSHES.get()));
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_DARK_PUMPKINS.get()));
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_DARK_MUSHROOMS.get()));
	}

    //Canopies, trees, and anything resembling a forest thing
    public static void addCanopyTrees(BiomeGenerationSettings.Builder biome) {
        biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_CANOPY_TREES.get()));
		//biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_DEFAULT_FALLEN_LOGS.get()));
	}

    public static void addCanopyMushrooms(BiomeGenerationSettings.Builder biome, boolean dense) {
        BiomeDefaultFeatures.addDefaultMushrooms(biome); // Add small mushrooms
		//Same config as DefaultBiomeFeatures.withMushroomBiomeVegetation, we just use our custom large mushrooms instead
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.BROWN_MUSHROOM_TAIGA);
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.RED_MUSHROOM_TAIGA);
        biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_VANILLA_TF_BIG_MUSH.get()));

        biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, dense ? Holder.direct(TFPlacedFeatures.PLACED_CANOPY_MUSHROOMS_DENSE.get()) : Holder.direct(TFPlacedFeatures.PLACED_CANOPY_MUSHROOMS_SPARSE.get()));
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, dense ? Holder.direct(TFPlacedFeatures.PLACED_BIG_MUSHGLOOM.get()) : Holder.direct(TFPlacedFeatures.PLACED_MUSHGLOOM_CLUSTER.get()));
	}

    public static void addHollowOakTrees(BiomeGenerationSettings.Builder biome) {
        //biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TFPlacedFeatures.PLACED_HOLLOW_TREE_PLACER);
	}

	public static void addSwampTrees(BiomeGenerationSettings.Builder biome) {
		biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_SWAMPY_OAK_TREE.get()));
	}

	public static void addSmallStoneClusters(BiomeGenerationSettings.Builder biome) {
		biome.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Holder.direct(TFPlacedFeatures.PLACED_SMALL_ANDESITE.get()));
		biome.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Holder.direct(TFPlacedFeatures.PLACED_SMALL_DIORITE.get()));
		biome.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Holder.direct(TFPlacedFeatures.PLACED_SMALL_GRANITE.get()));
	}

    public static BiomeSpecialEffects.Builder whiteAshParticles(BiomeSpecialEffects.Builder builder) {
        builder.ambientParticle(new AmbientParticleSettings(ParticleTypes.WHITE_ASH, 0.1f));
        return builder;
    }

	public static BiomeSpecialEffects.Builder fireflyForestParticles(BiomeSpecialEffects.Builder builder) {
		builder.ambientParticle(new AmbientParticleSettings(TFParticleType.WANDERING_FIREFLY.get(), 0.001f));
		return builder;
	}

	public static BiomeSpecialEffects.Builder fireflyParticles(BiomeSpecialEffects.Builder builder) {
		builder.ambientParticle(new AmbientParticleSettings(TFParticleType.WANDERING_FIREFLY.get(), 0.00025f));
		return builder;
	}

    //Caves!
	public static void addCaves(BiomeGenerationSettings.Builder biome) {
		biome.addCarver(GenerationStep.Carving.AIR, ConfiguredWorldCarvers.TFCAVES_CONFIGURED);
		biome.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_PLANT_ROOTS.get()));
		biome.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_TORCH_BERRIES.get()));
		biome.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_VANILLA_ROOTS.get()));
		BiomeDefaultFeatures.addDefaultOres(biome);
	}

	public static void addHighlandCaves(BiomeGenerationSettings.Builder biome) {
		biome.addCarver(GenerationStep.Carving.AIR, ConfiguredWorldCarvers.HIGHLANDCAVES_CONFIGURED);
		biome.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Holder.direct(TFPlacedFeatures.PLACED_TROLL_ROOTS.get()));
		BiomeDefaultFeatures.addDefaultOres(biome);
	}

	//Special mob spawns. EntityClassification.MONSTER is forced underground, so use CREATURE for above ground spawns.
	public static MobSpawnSettings.Builder penguinSpawning() {
		MobSpawnSettings.Builder spawnInfo = new MobSpawnSettings.Builder();

		spawnInfo.creatureGenerationProbability(0.2f);
		spawnInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(TFEntities.PENGUIN.get(), 10, 2, 4));

		return spawnInfo;
	}

	public static MobSpawnSettings.Builder darkForestSpawning() {
		MobSpawnSettings.Builder spawnInfo = new MobSpawnSettings.Builder();

		spawnInfo.creatureGenerationProbability(0.1f);
		spawnInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 1, 1, 4));
		spawnInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 5, 1, 4));
		spawnInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 5, 1, 4));
		spawnInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(TFEntities.MIST_WOLF.get(), 10, 1, 4));
		spawnInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(TFEntities.SKELETON_DRUID.get(), 10, 1, 4));
		spawnInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(TFEntities.KING_SPIDER.get(), 10, 1, 4));
		spawnInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(TFEntities.KOBOLD.get(), 10, 1, 4));
		spawnInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WITCH, 1, 1, 1));

		return spawnInfo;
	}

	public static MobSpawnSettings.Builder snowForestSpawning() {
		MobSpawnSettings.Builder spawnInfo = new MobSpawnSettings.Builder();

		spawnInfo.creatureGenerationProbability(0.1f);
		spawnInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(TFEntities.WINTER_WOLF.get(), 5, 1, 2));
		spawnInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(TFEntities.YETI.get(), 5, 1, 1));

		return spawnInfo;
	}

	public static MobSpawnSettings.Builder ravenSpawning() {
		MobSpawnSettings.Builder spawnInfo = new MobSpawnSettings.Builder();

		spawnInfo.creatureGenerationProbability(0.3f);
		spawnInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(TFEntities.RAVEN.get(), 10, 4, 4));

		return spawnInfo;
	}

	public static MobSpawnSettings.Builder swampSpawning() {
		MobSpawnSettings.Builder spawnInfo = new MobSpawnSettings.Builder();

		spawnInfo.creatureGenerationProbability(0.2f);
		spawnInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.CREEPER, 10, 4, 4));
		spawnInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 10, 4, 4));
		spawnInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(TFEntities.MOSQUITO_SWARM.get(), 10, 1, 1));

		return spawnInfo;
	}

	public static MobSpawnSettings.Builder spookSpawning() {
		MobSpawnSettings.Builder spawnInfo = new MobSpawnSettings.Builder();

		spawnInfo.creatureGenerationProbability(0.4f);
		spawnInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 50, 1, 4));
		spawnInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 20, 1, 4));
		spawnInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(TFEntities.SKELETON_DRUID.get(), 5, 1, 1));
		spawnInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.BAT, 20, 4, 4));

		return spawnInfo;
	}

	// Defaults
    public static BiomeSpecialEffects.Builder defaultAmbientBuilder() {
        return new BiomeSpecialEffects.Builder()
                .fogColor(0xC0FFD8) // TODO Change based on Biome. Not previously done before
                .waterColor(0x3F76E4)
                .waterFogColor(0x050533)
                .skyColor(0x20224A) //TODO Change based on Biome. Not previously done before
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS) // We should probably change it
                .backgroundMusic(TFConfiguredFeatures.TFMUSICTYPE);
        
    }

    public static BiomeGenerationSettings.Builder defaultGenSettingBuilder() {
        BiomeGenerationSettings.Builder biome = new BiomeGenerationSettings.Builder();

        BiomeDefaultFeatures.addSwampClayDisk(biome);
		BiomeDefaultFeatures.addDefaultSoftDisks(biome);
        BiomeDefaultFeatures.addForestGrass(biome);
        BiomeDefaultFeatures.addSavannaGrass(biome);
        biome.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_SUGAR_CANE);

        addSmallStoneClusters(biome);
        withWoodRoots(biome);
		addCaves(biome);
        return biome;
    }

    public static MobSpawnSettings.Builder defaultMobSpawning() {
        MobSpawnSettings.Builder spawnInfo = new MobSpawnSettings.Builder();

        spawnInfo.creatureGenerationProbability(0.1f);

        spawnInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(TFEntities.BIGHORN_SHEEP.get(), 12, 4, 4));
        spawnInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(TFEntities.BOAR.get(), 10, 4, 4));
        spawnInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.CHICKEN, 10, 4, 4));
        spawnInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(TFEntities.DEER.get(), 15, 4, 5));
        spawnInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 5, 4, 4));
        spawnInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(TFEntities.TINY_BIRD.get(), 15, 4, 8));
        spawnInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(TFEntities.SQUIRREL.get(), 10, 2, 4));
        spawnInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(TFEntities.DWARF_RABBIT.get(), 10, 4, 5));
        spawnInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(TFEntities.RAVEN.get(), 10, 1, 2));

        spawnInfo.addSpawn(MobCategory. MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 10, 4, 4));
        spawnInfo.addSpawn(MobCategory. MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 10, 4, 4));
        spawnInfo.addSpawn(MobCategory. MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 10, 4, 4));
        spawnInfo.addSpawn(MobCategory. MONSTER, new MobSpawnSettings.SpawnerData(EntityType.CREEPER, 1, 4, 4));
        spawnInfo.addSpawn(MobCategory. MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SLIME, 10, 4, 4));
        spawnInfo.addSpawn(MobCategory. MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 1, 1, 4));
        spawnInfo.addSpawn(MobCategory. MONSTER, new MobSpawnSettings.SpawnerData(TFEntities.KOBOLD.get(), 10, 2, 4));
		//not a monster, but we want them to go underground
        spawnInfo.addSpawn(MobCategory. MONSTER, new MobSpawnSettings.SpawnerData(EntityType.BAT, 10, 1, 2));

        return spawnInfo;
    }

	public static Biome.BiomeBuilder biomeWithDefaults(BiomeSpecialEffects.Builder biomeAmbience, MobSpawnSettings.Builder mobSpawnInfo, BiomeGenerationSettings.Builder biomeGenerationSettings) {
        return new Biome.BiomeBuilder()
                .precipitation(Biome.Precipitation.RAIN)
                .biomeCategory(Biome.BiomeCategory.FOREST)
                //.depth(0.025f)
                //.scale(0.05f)
                .temperature(0.5F)
                .downfall(0.5F)
                .specialEffects(biomeAmbience.build())
                .mobSpawnSettings(mobSpawnInfo.build())
                .generationSettings(biomeGenerationSettings.build())
                .temperatureAdjustment(Biome.TemperatureModifier.NONE);
    }
}
