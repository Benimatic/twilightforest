package twilightforest.data;

import com.google.gson.JsonObject;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.CookingRecipeSerializer;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import twilightforest.TwilightForestMod;
import twilightforest.block.TFBlocks;
import twilightforest.item.TFItems;
import twilightforest.item.recipe.UncraftingEnabledCondition;

import java.nio.file.Path;
import java.util.function.Consumer;

public class CraftingGenerator extends CraftingShapes {
	public CraftingGenerator(DataGenerator generator) {
		super(generator);
	}

	@Override
	protected void saveRecipeAdvancement(DirectoryCache p_208310_1_, JsonObject p_208310_2_, Path p_208310_3_) {
		//Silence. This just makes it so that we don't gen advancements
		//TODO Recipe advancements control the unlock of a recipe, so if we ever consider actually making them, recipes should unlock based on also possible prerequisite conditions, instead of ONLY obtaining the item itself
	}

	@Override
	protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
		// The Recipe Builder currently doesn't support enchantment-resulting recipes, those must be manually created.
		blockCompressionRecipes(consumer);
		equipmentRecipes(consumer);
		emptyMapRecipes(consumer);
		woodRecipes(consumer);
		fieryConversions(consumer);

		nagastoneRecipes(consumer);
		castleRecipes(consumer);

		slabBlock(consumer, "aurora_slab", TFBlocks.aurora_slab, TFBlocks.aurora_block);
		ShapedRecipeBuilder.shapedRecipe(TFBlocks.aurora_pillar.get(), 2)
				.patternLine("#")
				.patternLine("#")
				.key('#', Ingredient.fromItems(TFBlocks.aurora_block.get()))
				.addCriterion("has_" + TFBlocks.aurora_pillar.getId().getPath(), hasItem(TFBlocks.aurora_pillar.get()))
				.build(consumer);

		ShapedRecipeBuilder.shapedRecipe(TFBlocks.iron_ladder.get(), 3)
				.patternLine("-#-")
				.patternLine("-#-")
				.key('#', Ingredient.fromItems(Blocks.IRON_BARS))
				.key('-', Tags.Items.NUGGETS_IRON)
				.addCriterion("has_" + TFBlocks.iron_ladder.getId().getPath(), hasItem(TFBlocks.iron_ladder.get()))
				.build(consumer);

		ShapelessRecipeBuilder.shapelessRecipe(TFBlocks.firefly_jar.get())
				.addIngredient(Ingredient.fromItems(TFBlocks.firefly.get()))
				.addIngredient(Ingredient.fromItems(Items.GLASS_BOTTLE))
				.addCriterion("has_item", hasItem(TFBlocks.firefly.get()))
				.build(consumer);

		ShapelessRecipeBuilder.shapelessRecipe(Items.STICK)
				.addIngredient(Ingredient.fromItems(TFBlocks.root_strand.get()))
				.addCriterion("has_item", hasItem(TFBlocks.root_strand.get()))
				.build(consumer, TwilightForestMod.prefix("root_stick"));

		ShapedRecipeBuilder.shapedRecipe(Blocks.TORCH, 5)
				.patternLine("∴")
				.patternLine("|")
				.key('∴', Ingredient.fromItems(TFItems.torchberries.get()))
				.key('|', Tags.Items.RODS_WOODEN)
				.addCriterion("has_item", hasItem(TFItems.torchberries.get()))
				.build(consumer, TwilightForestMod.prefix("berry_torch"));

		ConditionalRecipe.builder()
				.addCondition(new UncraftingEnabledCondition())
				.addRecipe(ShapedRecipeBuilder.shapedRecipe(TFBlocks.uncrafting_table.get())
						.patternLine("###")
						.patternLine("#X#")
						.patternLine("###")
						.key('#', Blocks.CRAFTING_TABLE)
						.key('X', TFItems.maze_map_focus.get())
						.addCriterion("has_uncrafting_table", hasItem(TFBlocks.uncrafting_table.get()))
						::build)
				.build(consumer, TwilightForestMod.prefix("uncrafting_table"));

		// Patchouli books would also go here, except they also must craft-result with NBT data.

		cookingRecipes(consumer, "smelted", IRecipeSerializer.SMELTING, 200);
		cookingRecipes(consumer, "smoked", IRecipeSerializer.SMOKING, 100);
		cookingRecipes(consumer, "campfired", IRecipeSerializer.CAMPFIRE_COOKING, 600);

		ingotRecipes(consumer, "smelted", IRecipeSerializer.SMELTING, 200);
		ingotRecipes(consumer, "blasted", IRecipeSerializer.BLASTING, 100);

		ShapelessRecipeBuilder.shapelessRecipe(TFItems.armor_shard_cluster.get())
				.addIngredient(Ingredient.fromItems(TFItems.armor_shard.get()))
				.addIngredient(Ingredient.fromItems(TFItems.armor_shard.get()))
				.addIngredient(Ingredient.fromItems(TFItems.armor_shard.get()))
				.addIngredient(Ingredient.fromItems(TFItems.armor_shard.get()))
				.addIngredient(Ingredient.fromItems(TFItems.armor_shard.get()))
				.addIngredient(Ingredient.fromItems(TFItems.armor_shard.get()))
				.addIngredient(Ingredient.fromItems(TFItems.armor_shard.get()))
				.addIngredient(Ingredient.fromItems(TFItems.armor_shard.get()))
				.addIngredient(Ingredient.fromItems(TFItems.armor_shard.get()))
				.addCriterion("has_item", hasItem(TFItems.armor_shard.get()))
				.build(consumer, TwilightForestMod.prefix("material/" + TFItems.armor_shard_cluster.getId().getPath()));

		ShapelessRecipeBuilder.shapelessRecipe(TFItems.carminite.get())
				.addIngredient(Ingredient.fromItems(TFItems.borer_essence.get()))
				.addIngredient(Tags.Items.DUSTS_REDSTONE)
				.addIngredient(Tags.Items.DUSTS_REDSTONE)
				.addIngredient(Tags.Items.DUSTS_REDSTONE)
				.addIngredient(Tags.Items.DUSTS_REDSTONE)
				.addIngredient(Tags.Items.DUSTS_GLOWSTONE)
				.addIngredient(Tags.Items.DUSTS_GLOWSTONE)
				.addIngredient(Tags.Items.DUSTS_GLOWSTONE)
				.addIngredient(Tags.Items.DUSTS_GLOWSTONE)
				.addCriterion("has_item", hasItem(TFItems.borer_essence.get()))
				.build(consumer, TwilightForestMod.prefix("material/" + TFItems.carminite.getId().getPath()));

		ShapelessRecipeBuilder.shapelessRecipe(TFItems.ironwood_raw.get())
				.addIngredient(Ingredient.fromItems(TFItems.liveroot.get()))
				.addIngredient(Tags.Items.INGOTS_IRON)
				.addIngredient(Tags.Items.NUGGETS_GOLD)
				.addCriterion("has_item", hasItem(TFItems.liveroot.get()))
				.build(consumer, TwilightForestMod.prefix("material/" + TFItems.ironwood_raw.getId().getPath()));
	}

	private void equipmentRecipes(Consumer<IFinishedRecipe> consumer) {
		bootsItem(consumer, "arctic_boots", TFItems.arctic_boots, ItemTagGenerator.ARCTIC_FUR);
		chestplateItem(consumer, "arctic_chestplate", TFItems.arctic_chestplate, ItemTagGenerator.ARCTIC_FUR);
		helmetItem(consumer, "arctic_helmet", TFItems.arctic_helmet, ItemTagGenerator.ARCTIC_FUR);
		leggingsItem(consumer, "arctic_leggings", TFItems.arctic_leggings, ItemTagGenerator.ARCTIC_FUR);

		bootsItem(consumer, "fiery_boots", TFItems.fiery_boots, ItemTagGenerator.FIERY_INGOTS);
		chestplateItem(consumer, "fiery_chestplate", TFItems.fiery_chestplate, ItemTagGenerator.FIERY_INGOTS);
		helmetItem(consumer, "fiery_helmet", TFItems.fiery_helmet, ItemTagGenerator.FIERY_INGOTS);
		leggingsItem(consumer, "fiery_leggings", TFItems.fiery_leggings, ItemTagGenerator.FIERY_INGOTS);
		pickaxeItem(consumer, "fiery_pickaxe", TFItems.fiery_pickaxe, ItemTagGenerator.FIERY_INGOTS, Tags.Items.RODS_BLAZE);
		// Sword has Fire Aspect; manual

		bootsItem(consumer, "knightmetal_boots", TFItems.knightmetal_boots, ItemTagGenerator.KNIGHTMETAL_INGOTS);
		chestplateItem(consumer, "knightmetal_chestplate", TFItems.knightmetal_chestplate, ItemTagGenerator.KNIGHTMETAL_INGOTS);
		helmetItem(consumer, "knightmetal_helmet", TFItems.knightmetal_helmet, ItemTagGenerator.KNIGHTMETAL_INGOTS);
		leggingsItem(consumer, "knightmetal_leggings", TFItems.knightmetal_leggings, ItemTagGenerator.KNIGHTMETAL_INGOTS);
		pickaxeItem(consumer, "knightmetal_pickaxe", TFItems.knightmetal_pickaxe, ItemTagGenerator.KNIGHTMETAL_INGOTS, Tags.Items.RODS_WOODEN);
		swordItem(consumer, "knightmetal_sword", TFItems.knightmetal_sword, ItemTagGenerator.KNIGHTMETAL_INGOTS, Tags.Items.RODS_WOODEN);

		ShapelessRecipeBuilder.shapelessRecipe(TFItems.block_and_chain.get())
				.addIngredient(ItemTagGenerator.STORAGE_BLOCKS_KNIGHTMETAL)
				.addIngredient(ItemTagGenerator.KNIGHTMETAL_INGOTS)
				.addIngredient(ItemTagGenerator.KNIGHTMETAL_INGOTS)
				.addIngredient(ItemTagGenerator.KNIGHTMETAL_INGOTS)
				.addIngredient(Ingredient.fromItems(TFItems.knightmetal_ring.get()))
				.addCriterion("has_item", hasItem(TFBlocks.knightmetal_block.get()))
				.build(consumer, locEquip(TFItems.block_and_chain.getId().getPath()));
	}

	private void blockCompressionRecipes(Consumer<IFinishedRecipe> consumer) {
		reverseCompressBlock(consumer, "arctic_block_to_item", TFItems.arctic_fur, ItemTagGenerator.STORAGE_BLOCKS_ARCTIC_FUR);
		reverseCompressBlock(consumer, "carminite_block_to_item", TFItems.carminite, ItemTagGenerator.STORAGE_BLOCKS_CARMINITE);
		reverseCompressBlock(consumer, "fiery_block_to_ingot", TFItems.fiery_ingot, ItemTagGenerator.STORAGE_BLOCKS_FIERY);
		reverseCompressBlock(consumer, "ironwood_block_ingot", TFItems.ironwood_ingot, ItemTagGenerator.STORAGE_BLOCKS_IRONWOOD);
		reverseCompressBlock(consumer, "knightmetal_block_ingot", TFItems.knightmetal_ingot,ItemTagGenerator.STORAGE_BLOCKS_KNIGHTMETAL);
		reverseCompressBlock(consumer, "steeleaf_block_ingot", TFItems.steeleaf_ingot, ItemTagGenerator.STORAGE_BLOCKS_STEELEAF);

		compressedBlock(consumer, "arctic_block", TFBlocks.arctic_fur_block, ItemTagGenerator.ARCTIC_FUR);
		compressedBlock(consumer, "carminite_block", TFBlocks.carminite_block, ItemTagGenerator.CARMINITE_GEMS);
		compressedBlock(consumer, "fiery_block", TFBlocks.fiery_block, ItemTagGenerator.FIERY_INGOTS);
		compressedBlock(consumer, "ironwood_block", TFBlocks.ironwood_block, ItemTagGenerator.IRONWOOD_INGOTS);
		compressedBlock(consumer, "knightmetal_block", TFBlocks.knightmetal_block, ItemTagGenerator.KNIGHTMETAL_INGOTS);
		compressedBlock(consumer, "steeleaf_block", TFBlocks.steeleaf_block, ItemTagGenerator.STEELEAF_INGOTS);
	}

	private void emptyMapRecipes(Consumer<IFinishedRecipe> consumer) {
		ShapelessRecipeBuilder.shapelessRecipe(TFItems.magic_map_focus.get())
				.addIngredient(TFItems.raven_feather.get())
				.addIngredient(TFItems.torchberries.get())
				.addIngredient(Tags.Items.DUSTS_GLOWSTONE)
				.addCriterion("has_item", hasItem(TFItems.torchberries.get()))
				.build(consumer);

		ShapedRecipeBuilder.shapedRecipe(TFItems.magic_map_empty.get())
				.patternLine("###")
				.patternLine("#•#")
				.patternLine("###")
				.key('#', ItemTagGenerator.PAPER)
				.key('•', Ingredient.fromItems(TFItems.magic_map_focus.get()))
				.addCriterion("has_item", hasItem(TFItems.magic_map_focus.get()))
				.build(consumer);

		ShapedRecipeBuilder.shapedRecipe(TFItems.maze_map_empty.get())
				.patternLine("###")
				.patternLine("#•#")
				.patternLine("###")
				.key('#', ItemTagGenerator.PAPER)
				.key('•', Ingredient.fromItems(TFItems.maze_map_focus.get()))
				.addCriterion("has_item", hasItem(TFItems.maze_map_focus.get()))
				.build(consumer);

		ShapelessRecipeBuilder.shapelessRecipe(TFItems.ore_map_empty.get())
				.addIngredient(TFItems.ore_map_empty.get())
				.addIngredient(Tags.Items.STORAGE_BLOCKS_DIAMOND)
				.addIngredient(Tags.Items.STORAGE_BLOCKS_GOLD)
				.addIngredient(Tags.Items.STORAGE_BLOCKS_IRON)
				.addCriterion("has_item", hasItem(TFItems.ore_magnet.get()))
				.build(consumer);
	}

	private void woodRecipes(Consumer<IFinishedRecipe> consumer) {
		buttonBlock(consumer, "canopy", TFBlocks.canopy_button, TFBlocks.canopy_planks);
		buttonBlock(consumer, "darkwood", TFBlocks.dark_button, TFBlocks.dark_planks);
		buttonBlock(consumer, "mangrove", TFBlocks.mangrove_button, TFBlocks.mangrove_planks);
		buttonBlock(consumer, "mine", TFBlocks.mine_button, TFBlocks.mine_planks);
		buttonBlock(consumer, "sort", TFBlocks.sort_button, TFBlocks.sort_planks);
		buttonBlock(consumer, "time", TFBlocks.time_button, TFBlocks.time_planks);
		buttonBlock(consumer, "trans", TFBlocks.trans_button, TFBlocks.trans_planks);
		buttonBlock(consumer, "twilight_oak", TFBlocks.twilight_oak_button, TFBlocks.twilight_oak_planks);

		doorBlock(consumer, "canopy", TFBlocks.canopy_door, TFBlocks.canopy_planks);
		doorBlock(consumer, "darkwood", TFBlocks.dark_door, TFBlocks.dark_planks);
		doorBlock(consumer, "mangrove", TFBlocks.mangrove_door, TFBlocks.mangrove_planks);
		doorBlock(consumer, "mine", TFBlocks.mine_door, TFBlocks.mine_planks);
		doorBlock(consumer, "sort", TFBlocks.sort_door, TFBlocks.sort_planks);
		doorBlock(consumer, "time", TFBlocks.time_door, TFBlocks.time_planks);
		doorBlock(consumer, "trans", TFBlocks.trans_door, TFBlocks.trans_planks);
		doorBlock(consumer, "twilight_oak", TFBlocks.twilight_oak_door, TFBlocks.twilight_oak_planks);

		fenceBlock(consumer, "canopy", TFBlocks.canopy_fence, TFBlocks.canopy_planks);
		fenceBlock(consumer, "darkwood", TFBlocks.dark_fence, TFBlocks.dark_planks);
		fenceBlock(consumer, "mangrove", TFBlocks.mangrove_fence, TFBlocks.mangrove_planks);
		fenceBlock(consumer, "mine", TFBlocks.mine_fence, TFBlocks.mine_planks);
		fenceBlock(consumer, "sort", TFBlocks.sort_fence, TFBlocks.sort_planks);
		fenceBlock(consumer, "time", TFBlocks.time_fence, TFBlocks.time_planks);
		fenceBlock(consumer, "trans", TFBlocks.trans_fence, TFBlocks.trans_planks);
		fenceBlock(consumer, "twilight_oak", TFBlocks.twilight_oak_fence, TFBlocks.twilight_oak_planks);

		gateBlock(consumer, "canopy", TFBlocks.canopy_gate, TFBlocks.canopy_planks);
		gateBlock(consumer, "darkwood", TFBlocks.dark_gate, TFBlocks.dark_planks);
		gateBlock(consumer, "mangrove", TFBlocks.mangrove_gate, TFBlocks.mangrove_planks);
		gateBlock(consumer, "mine", TFBlocks.mine_gate, TFBlocks.mine_planks);
		gateBlock(consumer, "sort", TFBlocks.sort_gate, TFBlocks.sort_planks);
		gateBlock(consumer, "time", TFBlocks.time_gate, TFBlocks.time_planks);
		gateBlock(consumer, "trans", TFBlocks.trans_gate, TFBlocks.trans_planks);
		gateBlock(consumer, "twilight_oak", TFBlocks.twilight_oak_gate, TFBlocks.twilight_oak_planks);

		planksBlock(consumer, "canopy", TFBlocks.canopy_planks, TFBlocks.canopy_log);
		planksBlock(consumer, "darkwood", TFBlocks.dark_planks, TFBlocks.dark_log);
		planksBlock(consumer, "mangrove", TFBlocks.mangrove_planks, TFBlocks.mangrove_log);
		planksBlock(consumer, "mine", TFBlocks.mine_planks, TFBlocks.mining_log);
		planksBlock(consumer, "sort", TFBlocks.sort_planks, TFBlocks.sorting_log);
		planksBlock(consumer, "time", TFBlocks.time_planks, TFBlocks.time_log);
		planksBlock(consumer, "trans", TFBlocks.trans_planks, TFBlocks.transformation_log);
		planksBlock(consumer, "twilight_oak", TFBlocks.twilight_oak_planks, TFBlocks.oak_log);

		plateBlock(consumer, "canopy", TFBlocks.canopy_plate, TFBlocks.canopy_planks);
		plateBlock(consumer, "darkwood", TFBlocks.dark_plate, TFBlocks.dark_planks);
		plateBlock(consumer, "mangrove", TFBlocks.mangrove_plate, TFBlocks.mangrove_planks);
		plateBlock(consumer, "mine", TFBlocks.mine_plate, TFBlocks.mine_planks);
		plateBlock(consumer, "sort", TFBlocks.sort_plate, TFBlocks.sort_planks);
		plateBlock(consumer, "time", TFBlocks.time_plate, TFBlocks.time_planks);
		plateBlock(consumer, "trans", TFBlocks.trans_plate, TFBlocks.trans_planks);
		plateBlock(consumer, "twilight_oak", TFBlocks.twilight_oak_plate, TFBlocks.twilight_oak_planks);

		slabBlock(consumer, "canopy", TFBlocks.canopy_slab, TFBlocks.canopy_planks);
		slabBlock(consumer, "darkwood", TFBlocks.dark_slab, TFBlocks.dark_planks);
		slabBlock(consumer, "mangrove", TFBlocks.mangrove_slab, TFBlocks.mangrove_planks);
		slabBlock(consumer, "mine", TFBlocks.mine_slab, TFBlocks.mine_planks);
		slabBlock(consumer, "sort", TFBlocks.sort_slab, TFBlocks.sort_planks);
		slabBlock(consumer, "time", TFBlocks.time_slab, TFBlocks.time_planks);
		slabBlock(consumer, "trans", TFBlocks.trans_slab, TFBlocks.trans_planks);
		slabBlock(consumer, "twilight_oak", TFBlocks.twilight_oak_slab, TFBlocks.twilight_oak_planks);

		stairsBlock(consumer, locWood("canopy_stairs"), TFBlocks.canopy_stairs, TFBlocks.canopy_planks.get());
		stairsBlock(consumer, locWood("darkwood_stairs"), TFBlocks.dark_stairs, TFBlocks.dark_planks.get());
		stairsBlock(consumer, locWood("mangrove_stairs"), TFBlocks.mangrove_stairs, TFBlocks.mangrove_planks.get());
		stairsBlock(consumer, locWood("mine_stairs"), TFBlocks.mine_stairs, TFBlocks.mine_planks.get());
		stairsBlock(consumer, locWood("sort_stairs"), TFBlocks.sort_stairs, TFBlocks.sort_planks.get());
		stairsBlock(consumer, locWood("time_stairs"), TFBlocks.time_stairs, TFBlocks.time_planks.get());
		stairsBlock(consumer, locWood("trans_stairs"), TFBlocks.trans_stairs, TFBlocks.trans_planks.get());
		stairsBlock(consumer, locWood("twilight_oak_stairs"), TFBlocks.twilight_oak_stairs, TFBlocks.twilight_oak_planks.get());

		trapdoorBlock(consumer, "canopy", TFBlocks.canopy_trapdoor, TFBlocks.canopy_planks);
		trapdoorBlock(consumer, "darkwood", TFBlocks.dark_trapdoor, TFBlocks.dark_planks);
		trapdoorBlock(consumer, "mangrove", TFBlocks.mangrove_trapdoor, TFBlocks.mangrove_planks);
		trapdoorBlock(consumer, "mine", TFBlocks.mine_trapdoor, TFBlocks.mine_planks);
		trapdoorBlock(consumer, "sort", TFBlocks.sort_trapdoor, TFBlocks.sort_planks);
		trapdoorBlock(consumer, "time", TFBlocks.time_trapdoor, TFBlocks.time_planks);
		trapdoorBlock(consumer, "trans", TFBlocks.trans_trapdoor, TFBlocks.trans_planks);
		trapdoorBlock(consumer, "twilight_oak", TFBlocks.twilight_oak_trapdoor, TFBlocks.twilight_oak_planks);
	}

	private void nagastoneRecipes(Consumer<IFinishedRecipe> consumer) {
		ShapedRecipeBuilder.shapedRecipe(TFBlocks.spiral_bricks.get(), 8)
				.patternLine("BSS")
				.patternLine("BSS")
				.patternLine("BBB")
				.key('B', Ingredient.fromItems(Blocks.STONE_BRICKS, Blocks.MOSSY_STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS, Blocks.CHISELED_STONE_BRICKS))//Ingredient.merge(ImmutableList.of(Ingredient.fromTag(Tags.Items.STONE), Ingredient.fromItems(Blocks.STONE_BRICKS, Blocks.MOSSY_STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS, Blocks.CHISELED_STONE_BRICKS))))
				.key('S', Ingredient.fromItems(Blocks.STONE_SLAB, Blocks.STONE_BRICK_SLAB))
				.addCriterion("has_item", hasItem(TFBlocks.spiral_bricks.get()))
				.build(consumer, locNaga("nagastone_spiral"));

		stairsBlock(consumer, locNaga("nagastone_stairs_left"), TFBlocks.nagastone_stairs_left, TFBlocks.etched_nagastone.get());
		stairsRightBlock(consumer, locNaga("nagastone_stairs_right"), TFBlocks.nagastone_stairs_right, TFBlocks.etched_nagastone.get());

		ShapelessRecipeBuilder.shapelessRecipe(TFBlocks.etched_nagastone.get(), 3)
				.addIngredient(Ingredient.fromItems(TFBlocks.nagastone_stairs_left.get(), TFBlocks.nagastone_stairs_right.get()))
				.addIngredient(Ingredient.fromItems(TFBlocks.nagastone_stairs_left.get(), TFBlocks.nagastone_stairs_right.get()))
				.addIngredient(Ingredient.fromItems(TFBlocks.nagastone_stairs_left.get(), TFBlocks.nagastone_stairs_right.get()))
				.addIngredient(Ingredient.fromItems(TFBlocks.nagastone_stairs_left.get(), TFBlocks.nagastone_stairs_right.get()))
				.addCriterion("has_item", hasItem(TFBlocks.etched_nagastone.get()))
				.build(consumer, locNaga("nagastone_stairs_reverse"));

		stairsBlock(consumer, locNaga("nagastone_stairs_left_mossy"), TFBlocks.nagastone_stairs_mossy_left, TFBlocks.etched_nagastone_mossy.get());
		stairsRightBlock(consumer, locNaga("nagastone_stairs_right_mossy"), TFBlocks.nagastone_stairs_mossy_right, TFBlocks.etched_nagastone_mossy.get());

		ShapelessRecipeBuilder.shapelessRecipe(TFBlocks.etched_nagastone_mossy.get(), 3)
				.addIngredient(Ingredient.fromItems(TFBlocks.nagastone_stairs_mossy_left.get(), TFBlocks.nagastone_stairs_mossy_right.get()))
				.addIngredient(Ingredient.fromItems(TFBlocks.nagastone_stairs_mossy_left.get(), TFBlocks.nagastone_stairs_mossy_right.get()))
				.addIngredient(Ingredient.fromItems(TFBlocks.nagastone_stairs_mossy_left.get(), TFBlocks.nagastone_stairs_mossy_right.get()))
				.addIngredient(Ingredient.fromItems(TFBlocks.nagastone_stairs_mossy_left.get(), TFBlocks.nagastone_stairs_mossy_right.get()))
				.addCriterion("has_item", hasItem(TFBlocks.etched_nagastone_mossy.get()))
				.build(consumer, locNaga("nagastone_stairs_mossy_reverse"));

		stairsBlock(consumer, locNaga("nagastone_stairs_left_weathered"), TFBlocks.nagastone_stairs_weathered_left, TFBlocks.etched_nagastone_weathered.get());
		stairsRightBlock(consumer, locNaga("nagastone_stairs_right_weathered"), TFBlocks.nagastone_stairs_weathered_right, TFBlocks.etched_nagastone_weathered.get());

		ShapelessRecipeBuilder.shapelessRecipe(TFBlocks.etched_nagastone_weathered.get(), 3)
				.addIngredient(Ingredient.fromItems(TFBlocks.nagastone_stairs_weathered_left.get(), TFBlocks.nagastone_stairs_weathered_right.get()))
				.addIngredient(Ingredient.fromItems(TFBlocks.nagastone_stairs_weathered_left.get(), TFBlocks.nagastone_stairs_weathered_right.get()))
				.addIngredient(Ingredient.fromItems(TFBlocks.nagastone_stairs_weathered_left.get(), TFBlocks.nagastone_stairs_weathered_right.get()))
				.addIngredient(Ingredient.fromItems(TFBlocks.nagastone_stairs_weathered_left.get(), TFBlocks.nagastone_stairs_weathered_right.get()))
				.addCriterion("has_item", hasItem(TFBlocks.etched_nagastone_weathered.get()))
				.build(consumer, locNaga("nagastone_stairs_weathered_reverse"));
	}

	private void castleRecipes(Consumer<IFinishedRecipe> consumer) {
		castleBlock(consumer, "castle_paver", TFBlocks.castle_brick_frame, TFBlocks.castle_brick.get(), TFBlocks.castle_brick_worn.get(), TFBlocks.castle_brick_cracked.get(), TFBlocks.castle_brick_mossy.get());
		castleBlock(consumer, "castle_pillar_bold", TFBlocks.castle_pillar_bold, TFBlocks.castle_brick_frame.get());
		castleBlock(consumer, "castle_pillar_bold_none", TFBlocks.castle_pillar_bold_tile, TFBlocks.castle_pillar_bold.get(), TFBlocks.castle_pillar_bold_tile.get());
		ShapedRecipeBuilder.shapedRecipe(TFBlocks.castle_pillar_encased.get(), 6)
				.patternLine("#H#")
				.patternLine("#H#")
				.key('#', Ingredient.fromItems(TFBlocks.castle_brick.get(), TFBlocks.castle_brick_worn.get(), TFBlocks.castle_brick_cracked.get(), TFBlocks.castle_brick_mossy.get(), TFBlocks.castle_brick_frame.get()))
				.key('H', Ingredient.fromItems(TFBlocks.castle_pillar_encased.get(), TFBlocks.castle_pillar_encased_tile.get(), TFBlocks.castle_pillar_bold_tile.get()))
				.addCriterion("has_" + TFBlocks.castle_pillar_encased.get().getRegistryName().getPath(), hasItem(TFBlocks.castle_pillar_encased.get()))
				.build(consumer, locCastle("castle_pillar_encased"));
		castleBlock(consumer, "castle_pillar_encased_none", TFBlocks.castle_pillar_bold_tile, TFBlocks.castle_pillar_bold.get(), TFBlocks.castle_pillar_bold_tile.get());
		stairsBlock(consumer, locCastle("castleblock_stairs_bold"), TFBlocks.castle_stairs_bold, TFBlocks.castle_pillar_bold.get(), TFBlocks.castle_pillar_bold_tile.get());
		reverseStairsBlock(consumer, locCastle("castleblock_stairs_bold_reverse"), TFBlocks.castle_pillar_bold, TFBlocks.castle_stairs_bold.get());
		stairsBlock(consumer, locCastle("castleblock_stairs_brick"), TFBlocks.castle_stairs_brick, TFBlocks.castle_brick.get());
		reverseStairsBlock(consumer, locCastle("castleblock_stairs_brick_reverse"), TFBlocks.castle_brick, TFBlocks.castle_stairs_brick.get());
		stairsBlock(consumer, locCastle("castleblock_stairs_cracked"), TFBlocks.castle_stairs_cracked, TFBlocks.castle_brick_cracked.get());
		reverseStairsBlock(consumer, locCastle("castleblock_stairs_cracked_reverse"), TFBlocks.castle_brick_cracked, TFBlocks.castle_stairs_cracked.get());
		stairsBlock(consumer, locCastle("castleblock_stairs_encased"), TFBlocks.castle_stairs_encased, TFBlocks.castle_pillar_encased.get(), TFBlocks.castle_pillar_encased_tile.get());
		reverseStairsBlock(consumer, locCastle("castleblock_stairs_encased_reverse"), TFBlocks.castle_pillar_encased, TFBlocks.castle_stairs_encased.get());
		stairsBlock(consumer, locCastle("castleblock_stairs_mossy"), TFBlocks.castle_stairs_mossy, TFBlocks.castle_brick_mossy.get());
		reverseStairsBlock(consumer, locCastle("castleblock_stairs_mossy_reverse"), TFBlocks.castle_brick_mossy, TFBlocks.castle_stairs_mossy.get());
		stairsBlock(consumer, locCastle("castleblock_stairs_worn"), TFBlocks.castle_stairs_worn, TFBlocks.castle_brick_worn.get());
		reverseStairsBlock(consumer, locCastle("castleblock_stairs_worn_reverse"), TFBlocks.castle_brick_worn, TFBlocks.castle_stairs_worn.get());
	}

	private void fieryConversions(Consumer<IFinishedRecipe> consumer) {
		SmithingRecipeBuilder.func_240502_a_(Ingredient.fromItems(Items.IRON_INGOT), Ingredient.fromTag(ItemTagGenerator.FIERY_VIAL), TFItems.fiery_ingot.get()).func_240503_a_("has_item", hasItem(TFItems.fiery_ingot.get())).func_240505_a_(consumer, TwilightForestMod.prefix("material/fiery_iron_ingot"));

		ShapelessRecipeBuilder.shapelessRecipe(TFItems.fiery_helmet.get())
				.addIngredient(Ingredient.fromItems(Items.IRON_HELMET))
				.addIngredient(ItemTagGenerator.FIERY_VIAL)
				.addIngredient(ItemTagGenerator.FIERY_VIAL)
				.addIngredient(ItemTagGenerator.FIERY_VIAL)
				.addIngredient(ItemTagGenerator.FIERY_VIAL)
				.addIngredient(ItemTagGenerator.FIERY_VIAL)
				.addCriterion("has_item", hasItem(TFItems.fiery_helmet.get()))
				.build(consumer, TwilightForestMod.prefix("equipment/fiery_iron_helmet"));

		ShapelessRecipeBuilder.shapelessRecipe(TFItems.fiery_chestplate.get())
				.addIngredient(Ingredient.fromItems(Items.IRON_CHESTPLATE))
				.addIngredient(ItemTagGenerator.FIERY_VIAL)
				.addIngredient(ItemTagGenerator.FIERY_VIAL)
				.addIngredient(ItemTagGenerator.FIERY_VIAL)
				.addIngredient(ItemTagGenerator.FIERY_VIAL)
				.addIngredient(ItemTagGenerator.FIERY_VIAL)
				.addIngredient(ItemTagGenerator.FIERY_VIAL)
				.addIngredient(ItemTagGenerator.FIERY_VIAL)
				.addIngredient(ItemTagGenerator.FIERY_VIAL)
				.addCriterion("has_item", hasItem(TFItems.fiery_chestplate.get()))
				.build(consumer, TwilightForestMod.prefix("equipment/fiery_iron_chestplate"));

		ShapelessRecipeBuilder.shapelessRecipe(TFItems.fiery_leggings.get())
				.addIngredient(Ingredient.fromItems(Items.IRON_LEGGINGS))
				.addIngredient(ItemTagGenerator.FIERY_VIAL)
				.addIngredient(ItemTagGenerator.FIERY_VIAL)
				.addIngredient(ItemTagGenerator.FIERY_VIAL)
				.addIngredient(ItemTagGenerator.FIERY_VIAL)
				.addIngredient(ItemTagGenerator.FIERY_VIAL)
				.addIngredient(ItemTagGenerator.FIERY_VIAL)
				.addIngredient(ItemTagGenerator.FIERY_VIAL)
				.addCriterion("has_item", hasItem(TFItems.fiery_leggings.get()))
				.build(consumer, TwilightForestMod.prefix("equipment/fiery_iron_leggings"));

		ShapelessRecipeBuilder.shapelessRecipe(TFItems.fiery_boots.get())
				.addIngredient(Ingredient.fromItems(Items.IRON_BOOTS))
				.addIngredient(ItemTagGenerator.FIERY_VIAL)
				.addIngredient(ItemTagGenerator.FIERY_VIAL)
				.addIngredient(ItemTagGenerator.FIERY_VIAL)
				.addIngredient(ItemTagGenerator.FIERY_VIAL)
				.addCriterion("has_item", hasItem(TFItems.fiery_boots.get()))
				.build(consumer, TwilightForestMod.prefix("equipment/fiery_iron_boots"));

		ShapelessRecipeBuilder.shapelessRecipe(TFItems.fiery_pickaxe.get())
				.addIngredient(Ingredient.fromItems(Items.IRON_PICKAXE))
				.addIngredient(ItemTagGenerator.FIERY_VIAL)
				.addIngredient(ItemTagGenerator.FIERY_VIAL)
				.addIngredient(ItemTagGenerator.FIERY_VIAL)
				.addIngredient(Tags.Items.RODS_BLAZE)
				.addIngredient(Tags.Items.RODS_BLAZE)
				.addCriterion("has_item", hasItem(TFItems.fiery_pickaxe.get()))
				.build(consumer, TwilightForestMod.prefix("equipment/fiery_iron_pickaxe"));
	}

	private void cookingRecipes(Consumer<IFinishedRecipe> consumer, String processName, CookingRecipeSerializer<?> process, int smeltingTime) {
		CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(TFItems.raw_meef.get()), TFItems.cooked_meef.get(), 0.3f, smeltingTime, process).addCriterion("has_food", hasItem(TFItems.raw_meef.get())).build(consumer, TwilightForestMod.prefix("food/" + processName + "_meef").toString());
		CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(TFItems.raw_venison.get()), TFItems.cooked_venison.get(), 0.3f, smeltingTime, process).addCriterion("has_food", hasItem(TFItems.raw_venison.get())).build(consumer, TwilightForestMod.prefix("food/" + processName + "_venison").toString());
	}

	private void ingotRecipes(Consumer<IFinishedRecipe> consumer, String processName, CookingRecipeSerializer<?> process, int smeltingTime) {
		CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(TFItems.armor_shard_cluster.get()), TFItems.knightmetal_ingot.get(), 1.0f, smeltingTime, process).addCriterion("has_item", hasItem(TFItems.armor_shard_cluster.get())).build(consumer, TwilightForestMod.prefix( "material/" + processName + "_knightmetal_ingot").toString());
		CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(TFItems.ironwood_raw.get()), TFItems.ironwood_ingot.get(), 1.0f, smeltingTime, process).addCriterion("has_item", hasItem(TFItems.ironwood_raw.get())).build(consumer, TwilightForestMod.prefix("material/" + processName + "_ironwood_ingot").toString());
	}
}
