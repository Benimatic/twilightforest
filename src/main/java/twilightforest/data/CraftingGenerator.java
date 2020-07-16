package twilightforest.data;

import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import twilightforest.TwilightForestMod;
import twilightforest.block.TFBlocks;
import twilightforest.item.TFItems;
import twilightforest.item.recipe.UncraftingEnabledCondition;

import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CraftingGenerator extends RecipeProvider {

	public static final ITag.INamedTag<Item> ARCTIC_FUR = ItemTags.makeWrapperTag(TwilightForestMod.prefix("arctic_fur").toString());
	public static final ITag.INamedTag<Item> CARMINITE = ItemTags.makeWrapperTag(TwilightForestMod.prefix("carminite").toString());
	public static final ITag.INamedTag<Item> FIERY_INGOTS = ItemTags.makeWrapperTag(TwilightForestMod.prefix("fiery_ingots").toString());
	public static final ITag.INamedTag<Item> IRONWOOD_INGOTS = ItemTags.makeWrapperTag(TwilightForestMod.prefix("ironwood_ingots").toString());
	public static final ITag.INamedTag<Item> KNIGHTMETAL_INGOTS = ItemTags.makeWrapperTag(TwilightForestMod.prefix("knightmetal_ingots").toString());
	public static final ITag.INamedTag<Item> STEELEAF_INGOTS = ItemTags.makeWrapperTag(TwilightForestMod.prefix("steeleaf_ingots").toString());
	public static final ITag.INamedTag<Item> ARCTIC_FUR_BLOCKS = ItemTags.makeWrapperTag(TwilightForestMod.prefix("arctic_fur").toString());
	public static final ITag.INamedTag<Item> CARMINITE_BLOCKS = ItemTags.makeWrapperTag(TwilightForestMod.prefix("carminite").toString());
	public static final ITag.INamedTag<Item> FIERY_BLOCKS = ItemTags.makeWrapperTag(TwilightForestMod.prefix("fiery_ingots").toString());
	public static final ITag.INamedTag<Item> IRONWOOD_BLOCKS = ItemTags.makeWrapperTag(TwilightForestMod.prefix("ironwood_ingots").toString());
	public static final ITag.INamedTag<Item> KNIGHTMETAL_BLOCKS = ItemTags.makeWrapperTag(TwilightForestMod.prefix("knightmetal_ingots").toString());
	public static final ITag.INamedTag<Item> STEELEAF_BLOCKS = ItemTags.makeWrapperTag(TwilightForestMod.prefix("steeleaf_ingots").toString());

	public CraftingGenerator(DataGenerator generator) {
		super(generator);
	}

	@Override
	protected void saveRecipeAdvancement(DirectoryCache p_208310_1_, JsonObject p_208310_2_, Path p_208310_3_) {
		//Silence. This just makes it so that we don't gen advancements
	}

	@Override
	protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
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

		reverseCompressBlock(consumer, "arctic_block_to_item", TFItems.arctic_fur, ARCTIC_FUR_BLOCKS);
		reverseCompressBlock(consumer, "carminite_block_to_item", TFItems.carminite, CARMINITE_BLOCKS);
		reverseCompressBlock(consumer, "fiery_block_to_ingot", TFItems.fiery_ingot, FIERY_BLOCKS);
		reverseCompressBlock(consumer, "ironwood_block_ingot", TFItems.ironwood_ingot, IRONWOOD_BLOCKS);
		reverseCompressBlock(consumer, "knightmetal_block_ingot", TFItems.knightmetal_ingot, KNIGHTMETAL_BLOCKS);
		reverseCompressBlock(consumer, "steeleaf_block_ingot", TFItems.steeleaf_ingot, STEELEAF_BLOCKS);
		compressedBlock(consumer, "arctic_block", TFBlocks.arctic_fur_block, ARCTIC_FUR);
		compressedBlock(consumer, "carminite_block", TFBlocks.carminite_block, CARMINITE);
		compressedBlock(consumer, "fiery_block", TFBlocks.fiery_block, FIERY_INGOTS);
		compressedBlock(consumer, "ironwood_block", TFBlocks.ironwood_block, IRONWOOD_INGOTS);
		compressedBlock(consumer, "knightmetal_block", TFBlocks.knightmetal_block, KNIGHTMETAL_INGOTS);
		compressedBlock(consumer, "steeleaf_block", TFBlocks.steeleaf_block, STEELEAF_INGOTS);

		bootsItem(consumer, "arctic_boots", TFItems.arctic_boots, ARCTIC_FUR);
		chestplateItem(consumer, "arctic_chestplate", TFItems.arctic_chestplate, ARCTIC_FUR);
		helmetItem(consumer, "arctic_helmet", TFItems.arctic_helmet, ARCTIC_FUR);
		leggingsItem(consumer, "arctic_leggings", TFItems.arctic_leggings, ARCTIC_FUR);
		bootsItem(consumer, "fiery_boots", TFItems.fiery_boots, FIERY_INGOTS);
		chestplateItem(consumer, "fiery_chestplate", TFItems.fiery_chestplate, FIERY_INGOTS);
		helmetItem(consumer, "fiery_helmet", TFItems.fiery_helmet, FIERY_INGOTS);
		leggingsItem(consumer, "fiery_leggings", TFItems.fiery_leggings, FIERY_INGOTS);
		pickaxeItem(consumer, "fiery_pickaxe", TFItems.fiery_pickaxe, FIERY_INGOTS, Tags.Items.RODS_BLAZE);
		bootsItem(consumer, "knightmetal_boots", TFItems.knightmetal_boots, KNIGHTMETAL_INGOTS);
		chestplateItem(consumer, "knightmetal_chestplate", TFItems.knightmetal_chestplate, KNIGHTMETAL_INGOTS);
		helmetItem(consumer, "knightmetal_helmet", TFItems.knightmetal_helmet, KNIGHTMETAL_INGOTS);
		leggingsItem(consumer, "knightmetal_leggings", TFItems.knightmetal_leggings, KNIGHTMETAL_INGOTS);
		pickaxeItem(consumer, "knightmetal_pickaxe", TFItems.knightmetal_pickaxe, KNIGHTMETAL_INGOTS, Tags.Items.RODS_WOODEN);
		swordItem(consumer, "knightmetal_sword", TFItems.knightmetal_sword, KNIGHTMETAL_INGOTS, Tags.Items.RODS_WOODEN);

		stairsBlock(consumer, locNaga("nagastone_stairs_left"), TFBlocks.nagastone_stairs_left, TFBlocks.etched_nagastone.get());
		stairsRightBlock(consumer, locNaga("nagastone_stairs_right"), TFBlocks.nagastone_stairs_right, TFBlocks.etched_nagastone.get());
		stairsBlock(consumer, locNaga("nagastone_stairs_left_mossy"), TFBlocks.nagastone_stairs_mossy_left, TFBlocks.etched_nagastone_mossy.get());
		stairsRightBlock(consumer, locNaga("nagastone_stairs_right_mossy"), TFBlocks.nagastone_stairs_mossy_right, TFBlocks.etched_nagastone_mossy.get());
		stairsBlock(consumer, locNaga("nagastone_stairs_left_weathered"), TFBlocks.nagastone_stairs_weathered_left, TFBlocks.etched_nagastone_weathered.get());
		stairsRightBlock(consumer, locNaga("nagastone_stairs_right_weathered"), TFBlocks.nagastone_stairs_weathered_right, TFBlocks.etched_nagastone_weathered.get());

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
	}

	public void castleBlock(Consumer<IFinishedRecipe> consumer, String name, Supplier<? extends Block> result, IItemProvider... ingredients) {
		ShapedRecipeBuilder.shapedRecipe(result.get(), 4)
				.patternLine("##")
				.patternLine("##")
				.key('#', Ingredient.fromItems(ingredients))
				.addCriterion("has_" + result.get().getRegistryName().getPath(), hasItem(result.get()))
				.build(consumer, locCastle(name));
	}

	public void stairsBlock(Consumer<IFinishedRecipe> consumer, ResourceLocation loc, Supplier<? extends Block> result, IItemProvider... ingredients) {
		ShapedRecipeBuilder.shapedRecipe(result.get(),  8)
				.patternLine("#  ")
				.patternLine("## ")
				.patternLine("###")
				.key('#', Ingredient.fromItems(ingredients))
				.addCriterion("has_" + result.get().getRegistryName().getPath(), hasItem(result.get()))
				.build(consumer, loc);
	}

	public void stairsRightBlock(Consumer<IFinishedRecipe> consumer, ResourceLocation loc, Supplier<? extends Block> result, IItemProvider... ingredients) {
		ShapedRecipeBuilder.shapedRecipe(result.get(),  8)
				.patternLine("###")
				.patternLine(" ##")
				.patternLine("  #")
				.key('#', Ingredient.fromItems(ingredients))
				.addCriterion("has_" + result.get().getRegistryName().getPath(), hasItem(result.get()))
				.build(consumer, loc);
	}

	public void reverseStairsBlock(Consumer<IFinishedRecipe> consumer, ResourceLocation loc, Supplier<? extends Block> result, IItemProvider ingredient) {
		ShapelessRecipeBuilder.shapelessRecipe(result.get(),  3)
				.addIngredient(ingredient)
				.addIngredient(ingredient)
				.addIngredient(ingredient)
				.addIngredient(ingredient)
				.addCriterion("has_" + result.get().getRegistryName().getPath(), hasItem(result.get()))
				.build(consumer, loc);
	}

	public void compressedBlock(Consumer<IFinishedRecipe> consumer, String name, Supplier<? extends Block> result, ITag.INamedTag<Item> ingredient) {
		ShapedRecipeBuilder.shapedRecipe(result.get())
				.patternLine("###")
				.patternLine("###")
				.patternLine("###")
				.key('#', ingredient)
				.addCriterion("has_" + result.get().getRegistryName().getPath(), hasItem(result.get()))
				.build(consumer, TwilightForestMod.prefix("compressed_blocks/" + name));
	}

	public void reverseCompressBlock(Consumer<IFinishedRecipe> consumer, String name, Supplier<? extends Item> result, ITag.INamedTag<Item> ingredient) {
		ShapelessRecipeBuilder.shapelessRecipe(result.get(), 9)
				.addIngredient(ingredient)
				.addCriterion("has_" + result.get().getRegistryName().getPath(), hasItem(result.get()))
				.build(consumer, TwilightForestMod.prefix("compressed_blocks/reversed/" + name));
	}

	public void helmetItem(Consumer<IFinishedRecipe> consumer, String name, Supplier<? extends Item> result, ITag.INamedTag<Item> material) {
		ShapedRecipeBuilder.shapedRecipe(result.get())
				.patternLine("###")
				.patternLine("# #")
				.key('#', material)
				.addCriterion("has_" + result.get().getRegistryName().getPath(), hasItem(result.get()))
				.build(consumer, locEquip(name));
	}

	public void chestplateItem(Consumer<IFinishedRecipe> consumer, String name, Supplier<? extends Item> result, ITag.INamedTag<Item> material) {
		ShapedRecipeBuilder.shapedRecipe(result.get())
				.patternLine("# #")
				.patternLine("###")
				.patternLine("###")
				.key('#', material)
				.addCriterion("has_" + result.get().getRegistryName().getPath(), hasItem(result.get()))
				.build(consumer, locEquip(name));
	}

	public void leggingsItem(Consumer<IFinishedRecipe> consumer, String name, Supplier<? extends Item> result, ITag.INamedTag<Item> material) {
		ShapedRecipeBuilder.shapedRecipe(result.get())
				.patternLine("###")
				.patternLine("# #")
				.patternLine("# #")
				.key('#', material)
				.addCriterion("has_" + result.get().getRegistryName().getPath(), hasItem(result.get()))
				.build(consumer, locEquip(name));
	}

	public void bootsItem(Consumer<IFinishedRecipe> consumer, String name, Supplier<? extends Item> result, ITag.INamedTag<Item> material) {
		ShapedRecipeBuilder.shapedRecipe(result.get())
				.patternLine("# #")
				.patternLine("# #")
				.key('#', material)
				.addCriterion("has_" + result.get().getRegistryName().getPath(), hasItem(result.get()))
				.build(consumer, locEquip(name));
	}

	public void pickaxeItem(Consumer<IFinishedRecipe> consumer, String name, Supplier<? extends Item> result, ITag.INamedTag<Item> material, ITag.INamedTag<Item> handle) {
		ShapedRecipeBuilder.shapedRecipe(result.get())
				.patternLine("###")
				.patternLine(" X ")
				.patternLine(" X ")
				.key('#', material)
				.key('X', handle)
				.addCriterion("has_" + result.get().getRegistryName().getPath(), hasItem(result.get()))
				.build(consumer, locEquip(name));
	}

	public void swordItem(Consumer<IFinishedRecipe> consumer, String name, Supplier<? extends Item> result, ITag.INamedTag<Item> material, ITag.INamedTag<Item> handle) {
		ShapedRecipeBuilder.shapedRecipe(result.get())
				.patternLine("#")
				.patternLine("#")
				.patternLine("X")
				.key('#', material)
				.key('X', handle)
				.addCriterion("has_" + result.get().getRegistryName().getPath(), hasItem(result.get()))
				.build(consumer, locEquip(name));
	}

	public void buttonBlock(Consumer<IFinishedRecipe> consumer, String name, Supplier<? extends Block> result, Supplier<? extends Block> material) {
		ShapelessRecipeBuilder.shapelessRecipe(result.get())
				.addIngredient(material.get())
				.addCriterion("has_" + result.get().asItem().getRegistryName().getPath(), hasItem(result.get()))
				.build(consumer, locWood(name + "_button"));
	}

	public void doorBlock(Consumer<IFinishedRecipe> consumer, String name, Supplier<? extends Block> result, Supplier<? extends Block> material) {
		ShapedRecipeBuilder.shapedRecipe(result.get(), 3)
				.patternLine("##")
				.patternLine("##")
				.patternLine("##")
				.key('#', material.get())
				.addCriterion("has_" + result.get().asItem().getRegistryName().getPath(), hasItem(result.get()))
				.build(consumer, locWood(name + "_door"));
	}

	public void fenceBlock(Consumer<IFinishedRecipe> consumer, String name, Supplier<? extends Block> result, Supplier<? extends Block> material) {
		ShapedRecipeBuilder.shapedRecipe(result.get(), 3)
				.patternLine("#S#")
				.patternLine("#S#")
				.key('#', material.get())
				.key('S', Tags.Items.RODS_WOODEN)
				.addCriterion("has_" + result.get().asItem().getRegistryName().getPath(), hasItem(result.get()))
				.build(consumer, locWood(name + "_fence"));
	}

	public void gateBlock(Consumer<IFinishedRecipe> consumer, String name, Supplier<? extends Block> result, Supplier<? extends Block> material) {
		ShapedRecipeBuilder.shapedRecipe(result.get())
				.patternLine("S#S")
				.patternLine("S#S")
				.key('#', material.get())
				.key('S', Tags.Items.RODS_WOODEN)
				.addCriterion("has_" + result.get().asItem().getRegistryName().getPath(), hasItem(result.get()))
				.build(consumer, locWood(name + "_gate"));
	}

	public void planksBlock(Consumer<IFinishedRecipe> consumer, String name, Supplier<? extends Block> result, Supplier<? extends Block> material) {
		ShapelessRecipeBuilder.shapelessRecipe(result.get(), 4)
				.addIngredient(material.get())
				.addCriterion("has_" + result.get().asItem().getRegistryName().getPath(), hasItem(result.get()))
				.build(consumer, locWood(name + "_planks"));
	}

	public void plateBlock(Consumer<IFinishedRecipe> consumer, String name, Supplier<? extends Block> result, Supplier<? extends Block> material) {
		ShapedRecipeBuilder.shapedRecipe(result.get())
				.patternLine("##")
				.key('#', material.get())
				.addCriterion("has_" + result.get().asItem().getRegistryName().getPath(), hasItem(result.get()))
				.build(consumer, locWood(name + "_plate"));
	}

	public void slabBlock(Consumer<IFinishedRecipe> consumer, String name, Supplier<? extends Block> result, Supplier<? extends Block> material) {
		ShapedRecipeBuilder.shapedRecipe(result.get(), 6)
				.patternLine("###")
				.key('#', material.get())
				.addCriterion("has_" + result.get().asItem().getRegistryName().getPath(), hasItem(result.get()))
				.build(consumer, locWood(name + "_slab"));
	}

	public void trapdoorBlock(Consumer<IFinishedRecipe> consumer, String name, Supplier<? extends Block> result, Supplier<? extends Block> material) {
		ShapedRecipeBuilder.shapedRecipe(result.get(), 6)
				.patternLine("###")
				.patternLine("###")
				.key('#', material.get())
				.addCriterion("has_" + result.get().asItem().getRegistryName().getPath(), hasItem(result.get()))
				.build(consumer, locWood(name + "_trapdoor"));
	}

	private ResourceLocation locCastle(String name) {
		return TwilightForestMod.prefix("castleblock/" + name);
	}

	private ResourceLocation locEquip(String name) {
		return TwilightForestMod.prefix("equipment/" + name);
	}

	private ResourceLocation locNaga(String name) {
		return TwilightForestMod.prefix("nagastone/" + name);
	}

	private ResourceLocation locWood(String name) {
		return TwilightForestMod.prefix("wood/" + name);
	}
}
