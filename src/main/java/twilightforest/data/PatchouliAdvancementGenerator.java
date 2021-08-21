package twilightforest.data;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import twilightforest.TwilightForestMod;
import twilightforest.advancements.HasAdvancementTrigger;
import twilightforest.entity.TFEntities;
import twilightforest.world.registration.TFStructures;
import twilightforest.world.registration.biomes.BiomeKeys;

import java.util.Locale;
import java.util.function.Consumer;

public class PatchouliAdvancementGenerator implements Consumer<Consumer<Advancement>> {
	@Override
	public void accept(Consumer<Advancement> consumer) {
		Advancement root = Advancement.Builder.advancement()
				.addCriterion("hidden", new ImpossibleTrigger.TriggerInstance())
				.save(consumer, "twilightforest:alt/root");

		//biomes
		Advancement.Builder.advancement().parent(root)
				.addCriterion("has_forest", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/biomes/twilight_forest")))
				.addCriterion("has_firefly", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/biomes/firefly_forest")))
				.addCriterion("has_clearing", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/biomes/twilight_clearing")))
				.addCriterion("has_savannah", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/biomes/oak_savannah")))
				.addCriterion("has_dense", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/biomes/dense_twilight_forest")))
				.addCriterion("has_mush", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/biomes/mushroom_forest")))
				.addCriterion("has_dense_mush", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/biomes/dense_mushroom_forest")))
				.addCriterion("has_lake", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/biomes/twilight_lake")))
				.requirements(RequirementsStrategy.OR)
				.save(consumer, "twilightforest:alt/biomes/general_biomes");

		minorKeyBiomes(consumer, root);
		makeBiomeAdvancement("dark_forest_center", BiomeKeys.DARK_FOREST_CENTER, consumer, root);
		makeBiomeAdvancement("dense_mushroom_forest", BiomeKeys.DENSE_MUSHROOM_FOREST, consumer, root);
		makeBiomeAdvancement("dense_twilight_forest", BiomeKeys.DENSE_FOREST, consumer, root);
		makeBiomeAdvancement("enchanted_forest", BiomeKeys.ENCHANTED_FOREST, consumer, root);
		makeBiomeAdvancement("final_plateau", BiomeKeys.FINAL_PLATEAU, consumer, root);
		makeBiomeAdvancement("fire_swamp", BiomeKeys.FIRE_SWAMP, consumer, root);
		makeBiomeAdvancement("firefly_forest", BiomeKeys.FIREFLY_FOREST, consumer, root);
		makeBiomeAdvancement("mushroom_forest", BiomeKeys.MUSHROOM_FOREST, consumer, root);
		makeBiomeAdvancement("oak_savannah", BiomeKeys.OAK_SAVANNAH, consumer, root);
		makeBiomeAdvancement("spooky_forest", BiomeKeys.SPOOKY_FOREST, consumer, root);
		makeBiomeAdvancement("thornlands", BiomeKeys.THORNLANDS, consumer, root);
		makeBiomeAdvancement("twilight_clearing", BiomeKeys.CLEARING, consumer, root);
		makeBiomeAdvancement("twilight_forest", BiomeKeys.FOREST, consumer, root);
		makeBiomeAdvancement("twilight_glacier", BiomeKeys.GLACIER, consumer, root);
		makeBiomeAdvancement("twilight_highlands", BiomeKeys.HIGHLANDS, consumer, root);
		makeBiomeAdvancement("twilight_lake", BiomeKeys.LAKE, consumer, root);

		//entities
		entityAdvancement(TFEntities.adherent, consumer, root);
		entityAdvancement(TFEntities.armored_giant, consumer, root);
		entityAdvancement(TFEntities.bighorn_sheep, consumer, root);
		entityAdvancement(TFEntities.blockchain_goblin, consumer, root);
		entityAdvancement(TFEntities.bunny, consumer, root);
		entityAdvancement(TFEntities.death_tome, consumer, root);
		entityAdvancement(TFEntities.deer, consumer, root);
		entityAdvancement(TFEntities.fire_beetle, consumer, root);
		entityAdvancement(TFEntities.giant_miner, consumer, root);
		entityAdvancement(TFEntities.harbinger_cube, consumer, root);
		entityAdvancement(TFEntities.hedge_spider, consumer, root);
		entityAdvancement(TFEntities.helmet_crab, consumer, root);
		entityAdvancement(TFEntities.hostile_wolf, consumer, root);
		entityAdvancement(TFEntities.hydra, consumer, root);
		entityAdvancement(TFEntities.ice_crystal, consumer, root);
		entityAdvancement(TFEntities.king_spider, consumer, root);
		entityAdvancement(TFEntities.knight_phantom, consumer, root);
		entityAdvancement(TFEntities.kobold, consumer, root);
		entityAdvancement(TFEntities.lich, consumer, root);
		entityAdvancement(TFEntities.lich_minion, consumer, root);
		entityAdvancement(TFEntities.maze_slime, consumer, root);
		entityAdvancement(TFEntities.mini_ghast, consumer, root);
		entityAdvancement(TFEntities.minoshroom, consumer, root);
		entityAdvancement(TFEntities.minotaur, consumer, root);
		entityAdvancement(TFEntities.mist_wolf, consumer, root);
		entityAdvancement(TFEntities.mosquito_swarm, consumer, root);
		entityAdvancement(TFEntities.naga, consumer, root);
		entityAdvancement(TFEntities.penguin, consumer, root);
		entityAdvancement(TFEntities.pinch_beetle, consumer, root);
		entityAdvancement(TFEntities.raven, consumer, root);
		entityAdvancement(TFEntities.redcap, consumer, root);
		entityAdvancement(TFEntities.redcap_sapper, consumer, root);
		entityAdvancement(TFEntities.skeleton_druid, consumer, root);
		entityAdvancement(TFEntities.slime_beetle, consumer, root);
		entityAdvancement(TFEntities.snow_guardian, consumer, root);
		entityAdvancement(TFEntities.snow_queen, consumer, root);
		entityAdvancement(TFEntities.squirrel, consumer, root);
		entityAdvancement(TFEntities.stable_ice_core, consumer, root);
		entityAdvancement(TFEntities.swarm_spider, consumer, root);
		entityAdvancement(TFEntities.tiny_bird, consumer, root);
		entityAdvancement(TFEntities.tower_broodling, consumer, root);
		entityAdvancement(TFEntities.tower_ghast, consumer, root);
		entityAdvancement(TFEntities.tower_golem, consumer, root);
		entityAdvancement(TFEntities.tower_termite, consumer, root);
		entityAdvancement(TFEntities.troll, consumer, root);
		entityAdvancement(TFEntities.unstable_ice_core, consumer, root);
		entityAdvancement(TFEntities.ur_ghast, consumer, root);
		entityAdvancement(TFEntities.wild_boar, consumer, root);
		entityAdvancement(TFEntities.winter_wolf, consumer, root);
		entityAdvancement(TFEntities.wraith, consumer, root);
		entityAdvancement(TFEntities.yeti, consumer, root);
		entityAdvancement(TFEntities.yeti_alpha, consumer, root);

		Advancement.Builder.advancement().parent(root)
				.addCriterion("hurt_by_indirect", EntityHurtPlayerTrigger.TriggerInstance.entityHurtPlayer(DamagePredicate.Builder.damageInstance().sourceEntity(EntityPredicate.Builder.entity().of(TFEntities.goblin_knight_lower).build())))
				.addCriterion("slain_by", KilledTrigger.TriggerInstance.entityKilledPlayer(EntityPredicate.Builder.entity().of(TFEntities.goblin_knight_lower)))
				.addCriterion("hurt", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(EntityPredicate.Builder.entity().of(TFEntities.goblin_knight_lower).build()))
				.addCriterion("slay", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(TFEntities.goblin_knight_lower)))
				.addCriterion("summon", SummonedEntityTrigger.TriggerInstance.summonedEntity(EntityPredicate.Builder.entity().of(TFEntities.goblin_knight_lower)))
				.addCriterion("tame", TameAnimalTrigger.TriggerInstance.tamedAnimal(EntityPredicate.Builder.entity().of(TFEntities.goblin_knight_lower).build()))
				.addCriterion("hurt_by_indirect_alt", EntityHurtPlayerTrigger.TriggerInstance.entityHurtPlayer(DamagePredicate.Builder.damageInstance().sourceEntity(EntityPredicate.Builder.entity().of(TFEntities.goblin_knight_upper).build())))
				.addCriterion("slain_by_alt", KilledTrigger.TriggerInstance.entityKilledPlayer(EntityPredicate.Builder.entity().of(TFEntities.goblin_knight_upper)))
				.addCriterion("hurt_alt", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(EntityPredicate.Builder.entity().of(TFEntities.goblin_knight_upper).build()))
				.addCriterion("slay_alt", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(TFEntities.goblin_knight_upper)))
				.addCriterion("summon_alt", SummonedEntityTrigger.TriggerInstance.summonedEntity(EntityPredicate.Builder.entity().of(TFEntities.goblin_knight_upper)))
				.addCriterion("tame_alt", TameAnimalTrigger.TriggerInstance.tamedAnimal(EntityPredicate.Builder.entity().of(TFEntities.goblin_knight_upper).build()))
				.requirements(RequirementsStrategy.OR)
				.save(consumer, "twilightforest:alt/entities/goblin_knight");

		Advancement.Builder.advancement().parent(root)
				.addCriterion("has_bighorn", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/entities/bighorn_sheep")))
				.addCriterion("has_bunny", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/entities/bunny")))
				.addCriterion("has_deer", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/entities/deer")))
				.addCriterion("has_raven", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/entities/raven")))
				.addCriterion("has_squirrel", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/entities/squirrel")))
				.addCriterion("has_boar", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/entities/wild_boar")))
				.addCriterion("has_penguin", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/entities/penguin")))
				.requirements(RequirementsStrategy.OR)
				.save(consumer, "twilightforest:alt/entities/animals");

		Advancement.Builder.advancement().parent(root)
				.addCriterion("has_hedge", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/entities/hedge_spider")))
				.addCriterion("has_swarm", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/entities/swarm_spider")))
				.addCriterion("has_slime", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/entities/slime_beetle")))
				.addCriterion("has_fire", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/entities/fire_beetle")))
				.addCriterion("has_pinch", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/entities/pinch_beetle")))
				.addCriterion("has_mosquito", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/entities/mosquito_swarm")))
				.addCriterion("has_king", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/entities/king_spider")))
				.addCriterion("has_termite", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/entities/tower_termite")))
				.addCriterion("has_broodling", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/entities/tower_broodling")))
				.requirements(RequirementsStrategy.OR)
				.save(consumer, "twilightforest:alt/entities/arthopods");

		Advancement.Builder.advancement().parent(root)
				.addCriterion("has_ghastling", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/entities/mini_ghast")))
				.addCriterion("has_ghastguard", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/entities/tower_ghast")))
				.requirements(RequirementsStrategy.OR)
				.save(consumer, "twilightforest:alt/entities/ghasts");

		Advancement.Builder.advancement().parent(root)
				.addCriterion("has_armored", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/entities/armored_giant")))
				.addCriterion("has_miner", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/entities/giant_miner")))
				.requirements(RequirementsStrategy.OR)
				.save(consumer, "twilightforest:alt/entities/giants");

		Advancement.Builder.advancement().parent(root)
				.addCriterion("has_redcap", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/entities/redcap")))
				.addCriterion("has_sapper", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/entities/redcap_sapper")))
				.addCriterion("has_blockchain", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/entities/blockchain_goblin")))
				.addCriterion("has_knight", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/entities/goblin_knight")))
				.requirements(RequirementsStrategy.OR)
				.save(consumer, "twilightforest:alt/entities/goblins");

		Advancement.Builder.advancement().parent(root)
				.addCriterion("has_stable", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/entities/stable_ice_core")))
				.addCriterion("has_unstable", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/entities/unstable_ice_core")))
				.requirements(RequirementsStrategy.OR)
				.save(consumer, "twilightforest:alt/entities/ice_cores");

		Advancement.Builder.advancement().parent(root)
				.addCriterion("has_druid", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/entities/skeleton_druid")))
				.addCriterion("has_wraith", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/entities/wraith")))
				.addCriterion("has_guardian", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/entities/snow_gurdian")))
				.requirements(RequirementsStrategy.OR)
				.save(consumer, "twilightforest:alt/entities/undead");

		Advancement.Builder.advancement().parent(root)
				.addCriterion("has_hostile", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/entities/hostile_wolf")))
				.addCriterion("has_mist", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/entities/mist_wolf")))
				.addCriterion("has_winter", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/entities/winter_wolf")))
				.requirements(RequirementsStrategy.OR)
				.save(consumer, "twilightforest:alt/entities/wolves");

		//landmarks
		landmarkAdvancement(TFStructures.DARK_TOWER, consumer, root);
		landmarkAdvancement(TFStructures.FINAL_CASTLE, consumer, root);
		landmarkAdvancement(TFStructures.HEDGE_MAZE, consumer, root);
		landmarkAdvancement(TFStructures.HYDRA_LAIR, consumer, root);
		landmarkAdvancement(TFStructures.AURORA_PALACE, consumer, root);
		landmarkAdvancement(TFStructures.KNIGHT_STRONGHOLD, consumer, root);
		landmarkAdvancement(TFStructures.LABYRINTH, consumer, root);
		landmarkAdvancement(TFStructures.HOLLOW_HILL_LARGE, consumer, root);
		landmarkAdvancement(TFStructures.LICH_TOWER, consumer, root);
		landmarkAdvancement(TFStructures.HOLLOW_HILL_MEDIUM, consumer, root);
		landmarkAdvancement(TFStructures.MUSHROOM_TOWER, consumer, root);
		landmarkAdvancement(TFStructures.NAGA_COURTYARD, consumer, root);
		landmarkAdvancement(TFStructures.QUEST_GROVE, consumer, root);
		landmarkAdvancement(TFStructures.HOLLOW_HILL_SMALL, consumer, root);
		landmarkAdvancement(TFStructures.YETI_CAVE, consumer, root);

		Advancement.Builder.advancement().parent(root)
				.addCriterion("found_structure", LocationTrigger.TriggerInstance.located(LocationPredicate.Builder.location().setFeature(TFStructures.TROLL_CAVE).setY(MinMaxBounds.Doubles.atLeast(150)).build()))
				.save(consumer, "twilightforest:alt/major_landmarks/giant_cloud");

		Advancement.Builder.advancement().parent(root)
				.addCriterion("found_structure", LocationTrigger.TriggerInstance.located(LocationPredicate.Builder.location().setFeature(TFStructures.TROLL_CAVE).setY(MinMaxBounds.Doubles.atMost(50)).build()))
				.save(consumer, "twilightforest:alt/major_landmarks/troll_cave");

		Advancement.Builder.advancement().parent(root)
				.addCriterion("has_smol", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/major_landmarks/hollow_hill_small")))
				.addCriterion("has_med", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/major_landmarks/hollow_hill_medium")))
				.addCriterion("has_large", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/major_landmarks/hollow_hill_large")))
				.requirements(RequirementsStrategy.OR)
				.save(consumer, "twilightforest:alt/major_landmarks/hollow_hills");

	}

	private void makeBiomeAdvancement(String name, ResourceKey<Biome> key, Consumer<Advancement> consumer, Advancement root) {
		Advancement.Builder.advancement().parent(root)
				.addCriterion("in_biome", LocationTrigger.TriggerInstance.located(LocationPredicate.inBiome(key)))
				.requirements(RequirementsStrategy.OR)
				.save(consumer, "twilightforest:alt/biomes/" + name);
	}

	private void entityAdvancement(EntityType<?> entity, Consumer<Advancement> consumer, Advancement root) {
		Advancement.Builder.advancement().parent(root)
				.addCriterion("hurt_by_indirect", EntityHurtPlayerTrigger.TriggerInstance.entityHurtPlayer(DamagePredicate.Builder.damageInstance().sourceEntity(EntityPredicate.Builder.entity().of(entity).build())))
				.addCriterion("slain_by", KilledTrigger.TriggerInstance.entityKilledPlayer(EntityPredicate.Builder.entity().of(entity)))
				.addCriterion("hurt", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(EntityPredicate.Builder.entity().of(entity).build()))
				.addCriterion("slay", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(entity)))
				.addCriterion("summon", SummonedEntityTrigger.TriggerInstance.summonedEntity(EntityPredicate.Builder.entity().of(entity)))
				.addCriterion("tame", TameAnimalTrigger.TriggerInstance.tamedAnimal(EntityPredicate.Builder.entity().of(entity).build()))
				.requirements(RequirementsStrategy.OR)
				.save(consumer, "twilightforest:alt/entities/" + entity.getRegistryName().getPath());
	}

	private void landmarkAdvancement(StructureFeature<?> structure, Consumer<Advancement> consumer, Advancement root) {
		Advancement.Builder.advancement().parent(root)
				.addCriterion("found_structure", LocationTrigger.TriggerInstance.located(LocationPredicate.inFeature(structure)))
				.save(consumer, "twilightforest:alt/major_landmarks/" + structure.getRegistryName().getPath());
	}

	private void minorKeyBiomes(Consumer<Advancement> consumer, Advancement root) {
		Advancement.Builder.advancement().parent(root)
				.addCriterion("in_biome", LocationTrigger.TriggerInstance.located(LocationPredicate.inBiome(BiomeKeys.DARK_FOREST)))
				.addCriterion("has_other", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/biomes/dark_forest_center")))
				.requirements(RequirementsStrategy.OR)
				.save(consumer, "twilightforest:alt/biomes/dark_forest");

		Advancement.Builder.advancement().parent(root)
				.addCriterion("in_biome", LocationTrigger.TriggerInstance.located(LocationPredicate.inBiome(BiomeKeys.SWAMP)))
				.addCriterion("has_other", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/biomes/fire_swamp")))
				.requirements(RequirementsStrategy.OR)
				.save(consumer, "twilightforest:alt/biomes/swamp");

		Advancement.Builder.advancement().parent(root)
				.addCriterion("in_biome", LocationTrigger.TriggerInstance.located(LocationPredicate.inBiome(BiomeKeys.SNOWY_FOREST)))
				.addCriterion("has_other", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, TwilightForestMod.prefix("alt/biomes/twilight_glacier")))
				.requirements(RequirementsStrategy.OR)
				.save(consumer, "twilightforest:alt/biomes/snowy_forest");
	}
}
