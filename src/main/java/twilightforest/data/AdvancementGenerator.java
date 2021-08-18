package twilightforest.data;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import twilightforest.TFConfig;
import twilightforest.TFStructures;
import twilightforest.TwilightForestMod;
import twilightforest.advancements.*;
import twilightforest.block.Experiment115Block;
import twilightforest.block.TFBlocks;
import twilightforest.entity.TFEntities;
import twilightforest.item.TFItems;

import java.util.function.Consumer;

public class AdvancementGenerator implements Consumer<Consumer<Advancement>> {

	private static final EntityType<?>[] TF_KILLABLE = new EntityType<?>[]{TFEntities.adherent, TFEntities.armored_giant, TFEntities.bighorn_sheep, TFEntities.blockchain_goblin, TFEntities.bunny, TFEntities.death_tome, TFEntities.deer, TFEntities.fire_beetle, TFEntities.giant_miner, TFEntities.goblin_knight_lower, TFEntities.goblin_knight_upper, TFEntities.harbinger_cube, TFEntities.hedge_spider, TFEntities.helmet_crab, TFEntities.hostile_wolf, TFEntities.hydra, TFEntities.king_spider, TFEntities.knight_phantom, TFEntities.kobold, TFEntities.lich, TFEntities.lich_minion, TFEntities.maze_slime, TFEntities.mini_ghast, TFEntities.minoshroom, TFEntities.minotaur, TFEntities.mist_wolf, TFEntities.mosquito_swarm, TFEntities.naga, TFEntities.penguin, TFEntities.pinch_beetle, TFEntities.plateau_boss, TFEntities.quest_ram, TFEntities.raven, TFEntities.redcap, TFEntities.redcap_sapper, TFEntities.skeleton_druid, TFEntities.slime_beetle, TFEntities.snow_guardian, TFEntities.snow_queen, TFEntities.squirrel, TFEntities.stable_ice_core, TFEntities.swarm_spider, TFEntities.tiny_bird, TFEntities.tower_broodling, TFEntities.tower_ghast, TFEntities.tower_golem, TFEntities.tower_termite, TFEntities.troll, TFEntities.unstable_ice_core, TFEntities.ur_ghast, TFEntities.wild_boar, TFEntities.winter_wolf, TFEntities.wraith, TFEntities.yeti, TFEntities.yeti_alpha};

	@Override
	public void accept(Consumer<Advancement> consumer) {
		Advancement root = Advancement.Builder.advancement().display(
				TFBlocks.twilight_portal_miniature_structure.get(),
				new TranslatableComponent("itemGroup.twilightforest"),
				new TranslatableComponent("advancement.twilightforest.root.desc"),
				new ResourceLocation(TwilightForestMod.ID, "textures/block/mazestone_large_brick.png"),
				FrameType.TASK,
				true, false, false)
				.requirements(RequirementsStrategy.OR)
				.addCriterion("in_tf",
						LocationTrigger.TriggerInstance.located(
								LocationPredicate.inDimension(
										ResourceKey.create(Registry.DIMENSION_REGISTRY,
												new ResourceLocation(TFConfig.COMMON_CONFIG.DIMENSION.twilightForestID.get())))))
				.addCriterion("make_portal",
						new MakePortalTrigger.Instance(EntityPredicate.Composite.ANY))
				.save(consumer, "twilightforest:root");

		Advancement silence = this.addTFKillable(Advancement.Builder.advancement().parent(root).display(
				TFItems.raven_feather.get(),
						new TranslatableComponent("advancement.twilightforest.twilight_hunter"),
						new TranslatableComponent("advancement.twilightforest.twilight_hunter.desc"),
				null, FrameType.TASK, true, true, false).requirements(RequirementsStrategy.OR))
				.save(consumer, "twilightforest:twilight_hunter");

		Advancement naga = Advancement.Builder.advancement().parent(silence).display(
				TFBlocks.naga_courtyard_miniature_structure.get(),
				new TranslatableComponent("advancement.twilightforest.kill_naga"),
				new TranslatableComponent("advancement.twilightforest.kill_naga.desc",
						new TranslatableComponent("entity.twilightforest.naga"),
						new TranslatableComponent("item.twilightforest.naga_scale")),
				null, FrameType.GOAL, true, true, false)
				.addCriterion("naga", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(TFEntities.naga)))
				.addCriterion("scale", InventoryChangeTrigger.TriggerInstance.hasItems(TFItems.naga_scale.get()))
				.addCriterion("kill_mob", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, silence.getId()))
				.requirements(new CountRequirementsStrategy(2, 1))
				.save(consumer, "twilightforest:progress_naga");

		Advancement lich = Advancement.Builder.advancement().parent(naga).display(
						TFBlocks.lich_tower_miniature_structure.get(),
						new TranslatableComponent("advancement.twilightforest.kill_lich"),
						new TranslatableComponent("advancement.twilightforest.kill_lich.desc",
								new TranslatableComponent("entity.twilightforest.lich")),
						null, FrameType.GOAL, true, true, false)
				.addCriterion("kill_lich", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(TFEntities.lich)))
				.addCriterion("trophy", InventoryChangeTrigger.TriggerInstance.hasItems(TFBlocks.lich_trophy.get()))
				.addCriterion("lifedrain_scepter", InventoryChangeTrigger.TriggerInstance.hasItems(TFItems.lifedrain_scepter.get()))
				.addCriterion("twilight_scepter", InventoryChangeTrigger.TriggerInstance.hasItems(TFItems.twilight_scepter.get()))
				.addCriterion("zombie_scepter", InventoryChangeTrigger.TriggerInstance.hasItems(TFItems.zombie_scepter.get()))
				.addCriterion("shield_scepter", InventoryChangeTrigger.TriggerInstance.hasItems(TFItems.shield_scepter.get()))
				.addCriterion("kill_naga", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, naga.getId()))
				.requirements(new CountRequirementsStrategy(6, 1))
				.save(consumer, "twilightforest:progress_lich");

		Advancement minoshroom = Advancement.Builder.advancement().parent(lich).display(
						TFItems.meef_stroganoff.get(),
						new TranslatableComponent("advancement.twilightforest.progress_labyrinth"),
						new TranslatableComponent("advancement.twilightforest.progress_labyrinth.desc"),
						null, FrameType.GOAL, true, true, false)
				.addCriterion("meef", ConsumeItemTrigger.TriggerInstance.usedItem(TFItems.meef_stroganoff.get()))
				.addCriterion("kill_lich", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, lich.getId()))
				.requirements(RequirementsStrategy.AND)
				.save(consumer, "twilightforest:progress_labyrinth");

		Advancement hydra = Advancement.Builder.advancement().parent(minoshroom).display(
						TFBlocks.hydra_trophy.get(),
						new TranslatableComponent("advancement.twilightforest.kill_hydra"),
						new TranslatableComponent("advancement.twilightforest.kill_hydra.desc",
								new TranslatableComponent("entity.twilightforest.hydra")),
						null, FrameType.GOAL, true, true, false)
				.addCriterion("kill_hydra", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(TFEntities.hydra)))
				.addCriterion("trophy", InventoryChangeTrigger.TriggerInstance.hasItems(TFBlocks.hydra_trophy.get()))
				.addCriterion("blood", InventoryChangeTrigger.TriggerInstance.hasItems(TFItems.fiery_blood.get()))
				.addCriterion("stroganoff", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, minoshroom.getId()))
				.requirements(new CountRequirementsStrategy(3, 1))
				.save(consumer, "twilightforest:progress_hydra");

		Advancement trophy_pedestal = Advancement.Builder.advancement().parent(lich).display(
						TFBlocks.trophy_pedestal.get(),
						new TranslatableComponent("advancement.twilightforest.progress_trophy_pedestal"),
						new TranslatableComponent("advancement.twilightforest.progress_trophy_pedestal.desc"),
						null, FrameType.GOAL, true, true, false)
				.addCriterion("trophy_pedestal", new TrophyPedestalTrigger.Instance(EntityPredicate.Composite.ANY))
				.addCriterion("kill_lich", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, lich.getId()))
				.requirements(RequirementsStrategy.AND)
				.save(consumer, "twilightforest:progress_trophy_pedestal");

		Advancement knights = Advancement.Builder.advancement().parent(trophy_pedestal).display(
						TFBlocks.knight_phantom_trophy.get(),
						new TranslatableComponent("advancement.twilightforest.progress_knights"),
						new TranslatableComponent("advancement.twilightforest.progress_knights.desc"),
						null, FrameType.GOAL, true, true, false)
				.addCriterion("knight", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(TFEntities.knight_phantom)))
				.addCriterion("trophy", InventoryChangeTrigger.TriggerInstance.hasItems(TFBlocks.knight_phantom_trophy.get()))
				.addCriterion("previous_progression", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, trophy_pedestal.getId()))
				.requirements(new CountRequirementsStrategy(2, 1))
				.save(consumer, "twilightforest:progress_knights");

		Advancement trap = Advancement.Builder.advancement().parent(knights).display(
				TFBlocks.ghast_trap.get(),
				new TranslatableComponent("advancement.twilightforest.ghast_trap"),
				new TranslatableComponent("advancement.twilightforest.ghast_trap.desc",
						new TranslatableComponent("entity.twilightforest.mini_ghast"),
						new TranslatableComponent("block.twilightforest.ghast_trap"),
						new TranslatableComponent("entity.twilightforest.ur_ghast")),
				null, FrameType.TASK, true, true, false)
				.addCriterion("activate_ghast_trap", new ActivateGhastTrapTrigger.Instance(EntityPredicate.Composite.ANY))
				.save(consumer, "twilightforest:ghast_trap");

		Advancement ur_ghast = Advancement.Builder.advancement().parent(trap).display(
						TFBlocks.ur_ghast_trophy.get(),
						new TranslatableComponent("advancement.twilightforest.progress_ur_ghast"),
						new TranslatableComponent("advancement.twilightforest.progress_ur_ghast.desc",
								new TranslatableComponent("entity.twilightforest.ur_ghast")),
						null, FrameType.GOAL, true, true, false)
				.addCriterion("ghast", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(TFEntities.ur_ghast)))
				.addCriterion("trophy", InventoryChangeTrigger.TriggerInstance.hasItems(TFBlocks.ur_ghast_trophy.get()))
				.addCriterion("tear", InventoryChangeTrigger.TriggerInstance.hasItems(TFItems.fiery_tears.get()))
				.addCriterion("previous_progression", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, trap.getId()))
				.requirements(new CountRequirementsStrategy(3, 1))
				.save(consumer, "twilightforest:progress_ur_ghast");

		Advancement yeti = Advancement.Builder.advancement().parent(lich).display(
						TFItems.alpha_fur.get(),
						new TranslatableComponent("advancement.twilightforest.progress_yeti"),
						new TranslatableComponent("advancement.twilightforest.progress_yeti.desc",
								new TranslatableComponent("entity.twilightforest.yeti_alpha")),
						null, FrameType.GOAL, true, true, false)
				.addCriterion("yeti", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(TFEntities.yeti_alpha)))
				.addCriterion("trophy", InventoryChangeTrigger.TriggerInstance.hasItems(TFBlocks.yeti_trophy.get()))
				.addCriterion("fur", InventoryChangeTrigger.TriggerInstance.hasItems(TFItems.alpha_fur.get()))
				.addCriterion("previous_progression", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, lich.getId()))
				.requirements(new CountRequirementsStrategy(3, 1))
				.save(consumer, "twilightforest:progress_yeti");

		Advancement snow_queen = Advancement.Builder.advancement().parent(yeti).display(
						TFBlocks.snow_queen_trophy.get(),
						new TranslatableComponent("advancement.twilightforest.progress_glacier"),
						new TranslatableComponent("advancement.twilightforest.progress_glacier.desc",
								new TranslatableComponent("entity.twilightforest.snow_queen"),
								new TranslatableComponent("structure.aurora_palace")),
						null, FrameType.GOAL, true, true, false)
				.addCriterion("queen", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(TFEntities.snow_queen)))
				.addCriterion("trophy", InventoryChangeTrigger.TriggerInstance.hasItems(TFBlocks.snow_queen_trophy.get()))
				.addCriterion("previous_progression", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, yeti.getId()))
				.requirements(new CountRequirementsStrategy(2, 1))
				.save(consumer, "twilightforest:progress_glacier");

		Advancement merge = Advancement.Builder.advancement().parent(lich).display(
						TFBlocks.uberous_soil.get(),
						new TranslatableComponent("advancement.twilightforest.progress_merge"),
						new TranslatableComponent("advancement.twilightforest.progress_merge.desc",
								new TranslatableComponent("entity.twilightforest.hydra"),
								new TranslatableComponent("entity.twilightforest.ur_ghast"),
								new TranslatableComponent("entity.twilightforest.snow_queen")),
						null, FrameType.GOAL, true, true, false)
				.addCriterion("hydra", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, hydra.getId()))
				.addCriterion("ur_ghast", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, ur_ghast.getId()))
				.addCriterion("snow_queen", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, snow_queen.getId()))
				.save(consumer, "twilightforest:progress_merge");

		Advancement troll = Advancement.Builder.advancement().parent(merge).display(
						TFItems.magic_beans.get(),
						new TranslatableComponent("advancement.twilightforest.troll"),
						new TranslatableComponent("advancement.twilightforest.troll.desc",
								new TranslatableComponent("entity.twilightforest.troll")),
						null, FrameType.TASK, true, true, false)
				.addCriterion("troll", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(TFEntities.troll).located(LocationPredicate.inFeature(TFStructures.TROLL_CAVE))))
				.save(consumer, "twilightforest:troll");

		Advancement beanstalk = Advancement.Builder.advancement().parent(troll).display(
						TFBlocks.huge_stalk.get(),
						new TranslatableComponent("advancement.twilightforest.beanstalk"),
						new TranslatableComponent("advancement.twilightforest.beanstalk.desc",
								new TranslatableComponent("item.twilightforest.magic_beans")),
						null, FrameType.GOAL, true, true, false)
				.addCriterion("beans", InventoryChangeTrigger.TriggerInstance.hasItems(TFItems.magic_beans.get()))
				.addCriterion("use_beans", ItemUsedOnBlockTrigger.TriggerInstance.itemUsedOnBlock(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(TFBlocks.uberous_soil.get()).build()), ItemPredicate.Builder.item().of(TFItems.magic_beans.get())))
				.save(consumer, "twilightforest:beanstalk");

		Advancement giants = Advancement.Builder.advancement().parent(beanstalk).display(
						TFItems.giant_pickaxe.get(),
						new TranslatableComponent("advancement.twilightforest.giants"),
						new TranslatableComponent("advancement.twilightforest.giants.desc",
								new TranslatableComponent("entity.twilightforest.giant_miner"),
								new TranslatableComponent("item.twilightforest.giant_pickaxe")),
						null, FrameType.GOAL, true, true, false)
				.addCriterion("giant", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(TFEntities.giant_miner)))
				.addCriterion("pickaxe", InventoryChangeTrigger.TriggerInstance.hasItems(TFItems.magic_beans.get()))
				.save(consumer, "twilightforest:giants");

		Advancement lamp = Advancement.Builder.advancement().parent(giants).display(
						TFItems.lamp_of_cinders.get(),
						new TranslatableComponent("advancement.twilightforest.progress_troll"),
						new TranslatableComponent("advancement.twilightforest.progress_troll.desc",
								new TranslatableComponent("item.twilightforest.lamp_of_cinders")),
						null, FrameType.GOAL, true, true, false)
				.addCriterion("lamp", InventoryChangeTrigger.TriggerInstance.hasItems(TFItems.lamp_of_cinders.get()))
				.addCriterion("previous_progression", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, merge.getId()))
				.save(consumer, "twilightforest:progress_troll");

		Advancement thornlands = Advancement.Builder.advancement().parent(lamp).display(
						TFBlocks.brown_thorns.get(),
						new TranslatableComponent("advancement.twilightforest.progress_thorns"),
						new TranslatableComponent("advancement.twilightforest.progress_thorns.desc"),
						null, FrameType.GOAL, true, true, false)
				.addCriterion("castle", LocationTrigger.TriggerInstance.located(LocationPredicate.inFeature(TFStructures.FINAL_CASTLE)))
				.addCriterion("previous_progression", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, lamp.getId()))
				.save(consumer, "twilightforest:progress_thorns");

		Advancement.Builder.advancement().parent(thornlands).display(
						TFBlocks.castle_rune_brick_purple.get(),
						new TranslatableComponent("advancement.twilightforest.progress_castle"),
						new TranslatableComponent("advancement.twilightforest.progress_castle.desc"),
						null, FrameType.GOAL, true, true, false)
				.addCriterion("castle", LocationTrigger.TriggerInstance.located(LocationPredicate.inFeature(TFStructures.FINAL_CASTLE)))
				.addCriterion("previous_progression", new HasAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, thornlands.getId()))
				.save(consumer, "twilightforest:progress_castle");

		Advancement focus = Advancement.Builder.advancement().parent(silence).display(
						TFItems.magic_map_focus.get(),
						new TranslatableComponent("advancement.twilightforest.magic_map_focus"),
						new TranslatableComponent("advancement.twilightforest.magic_map_focus.desc",
								new TranslatableComponent("item.twilightforest.magic_map_focus"),
								new TranslatableComponent("item.twilightforest.raven_feather"),
								new TranslatableComponent("item.minecraft.glowstone_dust"),
								new TranslatableComponent("item.twilightforest.torchberries")),
						null, FrameType.TASK, true, true, false)
				.addCriterion("focus", InventoryChangeTrigger.TriggerInstance.hasItems(TFItems.magic_map_focus.get()))
				.save(consumer, "twilightforest:magic_map_focus");

		Advancement magic_map = Advancement.Builder.advancement().parent(focus).display(
						TFItems.magic_map.get(),
						new TranslatableComponent("advancement.twilightforest.magic_map"),
						new TranslatableComponent("advancement.twilightforest.magic_map.desc",
								new TranslatableComponent("item.twilightforest.magic_map")),
						null, FrameType.TASK, true, true, false)
				.addCriterion("magic_map", InventoryChangeTrigger.TriggerInstance.hasItems(TFItems.magic_map.get()))
				.save(consumer, "twilightforest:magic_map");

		Advancement maze_map = Advancement.Builder.advancement().parent(magic_map).display(
						TFItems.maze_map.get(),
						new TranslatableComponent("advancement.twilightforest.maze_map"),
						new TranslatableComponent("advancement.twilightforest.maze_map.desc",
								new TranslatableComponent("item.twilightforest.maze_map")),
						null, FrameType.GOAL, true, true, false)
				.addCriterion("maze_map", InventoryChangeTrigger.TriggerInstance.hasItems(TFItems.maze_map.get()))
				.save(consumer, "twilightforest:maze_map");

		Advancement.Builder.advancement().parent(maze_map).display(
						TFItems.ore_map.get(),
						new TranslatableComponent("advancement.twilightforest.ore_map"),
						new TranslatableComponent("advancement.twilightforest.ore_map.desc",
								new TranslatableComponent("item.twilightforest.ore_map")),
						null, FrameType.CHALLENGE, true, true, true)
				.addCriterion("ore_map", InventoryChangeTrigger.TriggerInstance.hasItems(TFItems.ore_map.get()))
				.save(consumer, "twilightforest:ore_map");

		Advancement hill1 =  Advancement.Builder.advancement().parent(root).display(
						Items.IRON_BOOTS,
						new TranslatableComponent("advancement.twilightforest.hill1"),
						new TranslatableComponent("advancement.twilightforest.hill1.desc",
								new TranslatableComponent("entity.twilightforest.redcap"),
								new TranslatableComponent("structure.hollow_hill")),
						null, FrameType.TASK, true, true, false)
				.addCriterion("redcap", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(TFEntities.redcap).located(LocationPredicate.inFeature(TFStructures.HOLLOW_HILL_SMALL))))
				.save(consumer, "twilightforest:hill1");

		Advancement hill2 =  Advancement.Builder.advancement().parent(hill1).display(
						TFItems.ironwood_pickaxe.get(),
						new TranslatableComponent("advancement.twilightforest.hill2"),
						new TranslatableComponent("advancement.twilightforest.hill2.desc",
								new TranslatableComponent("entity.twilightforest.redcap_sapper"),
								new TranslatableComponent("structure.hollow_hill")),
						null, FrameType.TASK, true, true, false)
				.addCriterion("redcap", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(TFEntities.redcap_sapper).located(LocationPredicate.inFeature(TFStructures.HOLLOW_HILL_MEDIUM))))
				.save(consumer, "twilightforest:hill2");

		Advancement.Builder.advancement().parent(hill2).display(
						Items.GLOWSTONE_DUST,
						new TranslatableComponent("advancement.twilightforest.hill3"),
						new TranslatableComponent("advancement.twilightforest.hill3.desc",
								new TranslatableComponent("entity.twilightforest.wraith"),
								new TranslatableComponent("structure.hollow_hill")),
						null, FrameType.TASK, true, true, false)
				.addCriterion("redcap", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(TFEntities.wraith).located(LocationPredicate.inFeature(TFStructures.HOLLOW_HILL_LARGE))))
				.save(consumer, "twilightforest:hill3");

		Advancement.Builder.advancement().parent(root).display(
						TFBlocks.hedge.get(),
						new TranslatableComponent("advancement.twilightforest.hedge"),
						new TranslatableComponent("advancement.twilightforest.hedge.desc",
								new TranslatableComponent("structure.hedge_maze")),
						null, FrameType.TASK, true, true, false)
				.addCriterion("hedge_spider", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(TFEntities.hedge_spider).located(LocationPredicate.inFeature(TFStructures.HEDGE_MAZE))))
				.addCriterion("swarm_spider", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(TFEntities.swarm_spider).located(LocationPredicate.inFeature(TFStructures.HEDGE_MAZE))))
				.requirements(RequirementsStrategy.OR)
				.save(consumer, "twilightforest:hedge");

		Advancement.Builder.advancement().parent(root).display(
						Items.BOWL,
						new TranslatableComponent("advancement.twilightforest.twilight_dining"),
						new TranslatableComponent("advancement.twilightforest.twilight_dining.desc",
								new TranslatableComponent("structure.hedge_maze")),
						null, FrameType.CHALLENGE, true, true, false)
				.addCriterion("raw_venison", ConsumeItemTrigger.TriggerInstance.usedItem(TFItems.raw_venison.get()))
				.addCriterion("cooked_venison", ConsumeItemTrigger.TriggerInstance.usedItem(TFItems.cooked_venison.get()))
				.addCriterion("raw_meef", ConsumeItemTrigger.TriggerInstance.usedItem(TFItems.raw_meef.get()))
				.addCriterion("cooked_meef", ConsumeItemTrigger.TriggerInstance.usedItem(TFItems.cooked_meef.get()))
				.addCriterion("meef_stroganoff", ConsumeItemTrigger.TriggerInstance.usedItem(TFItems.meef_stroganoff.get()))
				.addCriterion("hydra_chop", ConsumeItemTrigger.TriggerInstance.usedItem(TFItems.hydra_chop.get()))
				.addCriterion("maze_wafer", ConsumeItemTrigger.TriggerInstance.usedItem(TFItems.maze_wafer.get()))
				.addCriterion("experiment_115", ConsumeItemTrigger.TriggerInstance.usedItem(TFItems.experiment_115.get()))
				.requirements(new CountRequirementsStrategy(2, 2, 1, 1, 1, 1))
				.rewards(AdvancementRewards.Builder.experience(75))
				.save(consumer, "twilightforest:twilight_dinner");

		Advancement.Builder.advancement().parent(naga).display(
						TFItems.naga_chestplate.get(),
						new TranslatableComponent("advancement.twilightforest.naga_armors"),
						new TranslatableComponent("advancement.twilightforest.naga_armors.desc"),
						null, FrameType.CHALLENGE, true, true, false)
				.addCriterion("armor", InventoryChangeTrigger.TriggerInstance.hasItems(
						TFItems.naga_chestplate.get(), TFItems.naga_leggings.get()))
				.rewards(AdvancementRewards.Builder.experience(25))
				.save(consumer, "twilightforest:naga_armors");

		Advancement.Builder.advancement().parent(lich).display(
						TFItems.zombie_scepter.get(),
						new TranslatableComponent("advancement.twilightforest.lich_scepters"),
						new TranslatableComponent("advancement.twilightforest.lich_scepters.desc"),
						null, FrameType.CHALLENGE, true, true, true)
				.addCriterion("scepters", InventoryChangeTrigger.TriggerInstance.hasItems(
						TFItems.lifedrain_scepter.get(), TFItems.twilight_scepter.get(),
						TFItems.zombie_scepter.get(), TFItems.shield_scepter.get()))
				.rewards(AdvancementRewards.Builder.experience(100))
				.save(consumer, "twilightforest:lich_scepters");

		Advancement.Builder.advancement().parent(minoshroom).display(
						TFItems.mazebreaker_pickaxe.get(),
						new TranslatableComponent("advancement.twilightforest.mazebreaker"),
						new TranslatableComponent("advancement.twilightforest.mazebreaker.desc",
								new TranslatableComponent("item.twilightforest.mazebreaker_pickaxe")),
						null, FrameType.CHALLENGE, true, true, false)
				.addCriterion("pick", InventoryChangeTrigger.TriggerInstance.hasItems(TFItems.mazebreaker_pickaxe.get()))
				.rewards(AdvancementRewards.Builder.experience(50))
				.save(consumer, "twilightforest:mazebreaker");

		Advancement.Builder.advancement().parent(hydra).display(
						TFItems.hydra_chop.get(),
						new TranslatableComponent("advancement.twilightforest.hydra_chop"),
						new TranslatableComponent("advancement.twilightforest.hydra_chop.desc",
								new TranslatableComponent("entity.twilightforest.hydra")),
						null, FrameType.TASK, true, true, false)
				.addCriterion("hydra_chop", new HydraChopTrigger.Instance(EntityPredicate.Composite.ANY))
				.save(consumer, "twilightforest:hydra_chop");

		Advancement.Builder.advancement().parent(hydra).display(
						TFItems.fiery_sword.get(),
						new TranslatableComponent("advancement.twilightforest.fiery_set"),
						new TranslatableComponent("advancement.twilightforest.fiery_set.desc"),
						null, FrameType.CHALLENGE, true, true, false)
				.addCriterion("fiery_pick", InventoryChangeTrigger.TriggerInstance.hasItems(TFItems.fiery_pickaxe.get()))
				.addCriterion("fiery_sword", InventoryChangeTrigger.TriggerInstance.hasItems(TFItems.fiery_sword.get()))
				.addCriterion("fiery_helmet", InventoryChangeTrigger.TriggerInstance.hasItems(TFItems.fiery_helmet.get()))
				.addCriterion("fiery_chestplate", InventoryChangeTrigger.TriggerInstance.hasItems(TFItems.fiery_chestplate.get()))
				.addCriterion("fiery_leggings", InventoryChangeTrigger.TriggerInstance.hasItems(TFItems.fiery_leggings.get()))
				.addCriterion("fiery_boots", InventoryChangeTrigger.TriggerInstance.hasItems(TFItems.fiery_boots.get()))
				.requirements(new CountRequirementsStrategy(2, 4))
				.rewards(AdvancementRewards.Builder.experience(75))
				.save(consumer, "twilightforest:fiery_set");

		Advancement e115 = Advancement.Builder.advancement().parent(knights).display(
						TFItems.experiment_115.get(),
						new TranslatableComponent("advancement.twilightforest.experiment_115"),
						new TranslatableComponent("advancement.twilightforest.experiment_115.desc"),
						null, FrameType.TASK, true, true, false)
				.addCriterion("eat_experiment_115", ConsumeItemTrigger.TriggerInstance.usedItem(TFItems.experiment_115.get()))
				.save(consumer, "twilightforest:experiment_115");

		Advancement.Builder.advancement().parent(e115).display(
						e115Tag("think"),
						new TranslatableComponent("advancement.twilightforest.experiment_115_3"),
						new TranslatableComponent("advancement.twilightforest.experiment_115_3.desc"),
						null, FrameType.CHALLENGE, true, true, true)
				.addCriterion("eat_115_e115", LocationTrigger.TriggerInstance.located(EntityPredicate.Builder.entity().player(PlayerPredicate.Builder.player().addStat(Stats.ITEM_USED.get(TFItems.experiment_115.get()), MinMaxBounds.Ints.atLeast(115)).build()).build()))
				.save(consumer, "twilightforest:experiment_115_115");

		Advancement.Builder.advancement().parent(e115).display(
						e115Tag("full"),
						new TranslatableComponent("advancement.twilightforest.experiment_115_2"),
						new TranslatableComponent("advancement.twilightforest.experiment_115_2.desc"),
						null, FrameType.CHALLENGE, true, true, true)
				.addCriterion("place_complete_e115", ItemUsedOnBlockTrigger.TriggerInstance.itemUsedOnBlock(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(TFBlocks.experiment_115.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(Experiment115Block.BITES_TAKEN, 0).hasProperty(Experiment115Block.REGENERATE, false).build()).build()), ItemPredicate.Builder.item().of(Items.REDSTONE)))
				.save(consumer, "twilightforest:experiment_115_self_replenishing");

		Advancement.Builder.advancement().parent(yeti).display(
						TFItems.arctic_chestplate.get(),
						new TranslatableComponent("advancement.twilightforest.arctic_dyed"),
						new TranslatableComponent("advancement.twilightforest.arctic_dyed.desc"),
						null, FrameType.TASK, true, true, false)
				.addCriterion("helmet", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(TFItems.arctic_helmet.get()).hasNbt(arcticDye(TFItems.arctic_helmet.get())).build()))
				.addCriterion("chestplate", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(TFItems.arctic_chestplate.get()).hasNbt(arcticDye(TFItems.arctic_chestplate.get())).build()))
				.addCriterion("leggings", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(TFItems.arctic_leggings.get()).hasNbt(arcticDye(TFItems.arctic_leggings.get())).build()))
				.addCriterion("boots", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(TFItems.arctic_boots.get()).hasNbt(arcticDye(TFItems.arctic_boots.get())).build()))
				.save(consumer, "twilightforest:arctic_armor_dyed");

		Advancement.Builder.advancement().parent(yeti).display(
						TFItems.glass_sword.get(),
						new TranslatableComponent("advancement.twilightforest.glass_sword"),
						new TranslatableComponent("advancement.twilightforest.glass_sword.desc"),
						null, FrameType.CHALLENGE, true, true, true)
				.addCriterion("broken_sword", ItemDurabilityTrigger.TriggerInstance.changedDurability(ItemPredicate.Builder.item().of(TFItems.glass_sword.get()).build(), MinMaxBounds.Ints.exactly(-1)))
				.rewards(AdvancementRewards.Builder.experience(42).addLootTable(TwilightForestMod.prefix("glass_sword")))
				.save(consumer, "twilightforest:break_glass_sword");

	}

	private ItemStack e115Tag(String nbt) {
		ItemStack itemstack = new ItemStack(TFItems.experiment_115.get());
		CompoundTag compoundtag = itemstack.getOrCreateTagElement(nbt);
		compoundtag.putInt(nbt, 1);
		return itemstack;
	}

	private CompoundTag arcticDye(Item item) {
		ItemStack itemstack = new ItemStack(item);
		CompoundTag compoundtag = itemstack.getOrCreateTagElement("display");
		CompoundTag color = itemstack.getOrCreateTagElement("hasColor");
		color.putBoolean("hasColor", true);
		compoundtag.put("display", color);
		return compoundtag;
	}

	private Advancement.Builder addTFKillable(Advancement.Builder builder) {
		for (EntityType<?> entity : TF_KILLABLE) {
			builder.addCriterion(EntityType.getKey(entity).getPath(),
					KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(entity)
							.located(
									LocationPredicate.inDimension(
											ResourceKey.create(Registry.DIMENSION_REGISTRY,
													new ResourceLocation(TFConfig.COMMON_CONFIG.DIMENSION.twilightForestID.get()))))));
		}
		return builder;
	}
}
