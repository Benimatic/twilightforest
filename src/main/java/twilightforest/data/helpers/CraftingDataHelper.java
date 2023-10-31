package twilightforest.data.helpers;

import net.minecraft.Util;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.crafting.PartialNBTIngredient;
import net.neoforged.neoforge.registries.ForgeRegistries;
import twilightforest.TwilightForestMod;
import twilightforest.block.TFChestBlock;
import twilightforest.data.tags.ItemTagGenerator;
import twilightforest.init.TFBlocks;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public abstract class CraftingDataHelper extends RecipeProvider {
	public CraftingDataHelper(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
		super(output, provider);
	}

	public final PartialNBTIngredient scepter(Item scepter) {
		return PartialNBTIngredient.of(scepter, Util.make(() -> {
			CompoundTag nbt = new CompoundTag();
			nbt.putInt(ItemStack.TAG_DAMAGE, scepter.getMaxDamage());
			return nbt;
		}));
	}

	public final PartialNBTIngredient potion(Potion potion) {
		return PartialNBTIngredient.of(Items.POTION, Util.make(() -> {
			CompoundTag nbt = new CompoundTag();
			nbt.putString("Potion", ForgeRegistries.POTIONS.getKey(potion).toString());
			return nbt;
		}));
	}

	protected final void charmRecipe(RecipeOutput output, String name, Supplier<? extends Item> result, Supplier<? extends Item> item) {
		ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, result.get())
				.requires(item.get(), 4)
				.unlockedBy("has_item", has(item.get()))
				.save(output, TwilightForestMod.prefix(name));
	}

	protected final void castleBlock(RecipeOutput output, Supplier<? extends Block> result, ItemLike... ingredients) {
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result.get(), 4)
				.pattern("##")
				.pattern("##")
				.define('#', Ingredient.of(ingredients))
				.unlockedBy("has_castle_brick", has(TFBlocks.CASTLE_BRICK.get()))
				.save(output, locCastle(ForgeRegistries.BLOCKS.getKey(result.get()).getPath()));
	}

	protected final void stairsBlock(RecipeOutput output, ResourceLocation loc, Supplier<? extends Block> result, Supplier<? extends Block> criteria, ItemLike... ingredients) {
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result.get(), 8)
				.pattern("#  ")
				.pattern("## ")
				.pattern("###")
				.define('#', Ingredient.of(ingredients))
				.unlockedBy("has_item", has(criteria.get()))
				.save(output, loc);
	}

	protected final void stairsRightBlock(RecipeOutput output, ResourceLocation loc, Supplier<? extends Block> result, Supplier<? extends Block> criteria, ItemLike... ingredients) {
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result.get(), 8)
				.pattern("###")
				.pattern(" ##")
				.pattern("  #")
				.define('#', Ingredient.of(ingredients))
				.unlockedBy("has_item", has(criteria.get()))
				.save(output, loc);
	}

	protected final void compressedBlock(RecipeOutput output, String name, Supplier<? extends Block> result, TagKey<Item> ingredient) {
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result.get())
				.pattern("###")
				.pattern("###")
				.pattern("###")
				.define('#', ingredient)
				.unlockedBy("has_item", has(ingredient))
				.save(output, TwilightForestMod.prefix("compressed_blocks/" + name));
	}

	protected final void reverseCompressBlock(RecipeOutput output, String name, Supplier<? extends Item> result, TagKey<Item> ingredient) {
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result.get(), 9)
				.requires(ingredient)
				.unlockedBy("has_item", has(ingredient))
				.save(output, TwilightForestMod.prefix("compressed_blocks/reversed/" + name));
	}

	protected final void helmetItem(RecipeOutput output, String name, Supplier<? extends Item> result, TagKey<Item> material) {
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result.get())
				.pattern("###")
				.pattern("# #")
				.define('#', material)
				.unlockedBy("has_item", has(material))
				.save(output, locEquip(name));
	}

	protected final void chestplateItem(RecipeOutput output, String name, Supplier<? extends Item> result, TagKey<Item> material) {
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result.get())
				.pattern("# #")
				.pattern("###")
				.pattern("###")
				.define('#', material)
				.unlockedBy("has_item", has(material))
				.save(output, locEquip(name));
	}

	protected final void leggingsItem(RecipeOutput output, String name, Supplier<? extends Item> result, TagKey<Item> material) {
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result.get())
				.pattern("###")
				.pattern("# #")
				.pattern("# #")
				.define('#', material)
				.unlockedBy("has_item", has(material))
				.save(output, locEquip(name));
	}

	protected final void bootsItem(RecipeOutput output, String name, Supplier<? extends Item> result, TagKey<Item> material) {
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result.get())
				.pattern("# #")
				.pattern("# #")
				.define('#', material)
				.unlockedBy("has_item", has(material))
				.save(output, locEquip(name));
	}

	protected final void pickaxeItem(RecipeOutput output, String name, Supplier<? extends Item> result, TagKey<Item> material, TagKey<Item> handle) {
		ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result.get())
				.pattern("###")
				.pattern(" X ")
				.pattern(" X ")
				.define('#', material)
				.define('X', handle)
				.unlockedBy("has_item", has(material))
				.save(output, locEquip(name));
	}

	protected final void swordItem(RecipeOutput output, String name, Supplier<? extends Item> result, TagKey<Item> material, TagKey<Item> handle) {
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result.get())
				.pattern("#")
				.pattern("#")
				.pattern("X")
				.define('#', material)
				.define('X', handle)
				.unlockedBy("has_item", has(material))
				.save(output, locEquip(name));
	}

	protected final void axeItem(RecipeOutput output, String name, Supplier<? extends Item> result, TagKey<Item> material, TagKey<Item> handle) {
		ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result.get())
				.pattern("##")
				.pattern("#X")
				.pattern(" X")
				.define('#', material)
				.define('X', handle)
				.unlockedBy("has_item", has(material))
				.save(output, locEquip(name));
	}

	protected final void buttonBlock(RecipeOutput output, String name, Supplier<? extends Block> result, Supplier<? extends Block> material) {
		ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, result.get())
				.requires(material.get())
				.unlockedBy("has_item", has(material.get()))
				.save(output, locWood(name + "_button"));
	}

	protected final void doorBlock(RecipeOutput output, String name, Supplier<? extends Block> result, Supplier<? extends Block> material) {
		ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, result.get(), 3)
				.pattern("##")
				.pattern("##")
				.pattern("##")
				.define('#', material.get())
				.unlockedBy("has_item", has(material.get()))
				.save(output, locWood(name + "_door"));
	}

	protected final void fenceBlock(RecipeOutput output, String name, Supplier<? extends Block> result, Supplier<? extends Block> material) {
		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result.get(), 3)
				.pattern("#S#")
				.pattern("#S#")
				.define('#', material.get())
				.define('S', Tags.Items.RODS_WOODEN)
				.unlockedBy("has_item", has(material.get()))
				.save(output, locWood(name + "_fence"));
	}

	protected final void gateBlock(RecipeOutput output, String name, Supplier<? extends Block> result, Supplier<? extends Block> material) {
		ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, result.get())
				.pattern("S#S")
				.pattern("S#S")
				.define('#', material.get())
				.define('S', Tags.Items.RODS_WOODEN)
				.unlockedBy("has_item", has(material.get()))
				.save(output, locWood(name + "_gate"));
	}

	protected final void planksBlock(RecipeOutput output, String name, Supplier<? extends Block> result, TagKey<Item> material) {
		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, result.get(), 4)
				.requires(material)
				.unlockedBy("has_item", has(material))
				.save(output, locWood(name + "_planks"));
	}

	protected final void plateBlock(RecipeOutput output, String name, Supplier<? extends Block> result, Supplier<? extends Block> material) {
		ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, result.get())
				.pattern("##")
				.define('#', material.get())
				.unlockedBy("has_item", has(material.get()))
				.save(output, locWood(name + "_plate"));
	}

	protected final void slabBlock(RecipeOutput output, String name, Supplier<? extends Block> result, Supplier<? extends Block> material) {
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result.get(), 6)
				.pattern("###")
				.define('#', material.get())
				.unlockedBy("has_item", has(material.get()))
				.save(output, locWood(name + "_slab"));
	}

	protected final void bannerPattern(RecipeOutput output, String name, Supplier<? extends Block> trophy, Supplier<? extends Item> result) {
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result.get())
				.requires(Ingredient.of(ItemTagGenerator.PAPER))
				.requires(Ingredient.of(trophy.get().asItem()))
				.unlockedBy("has_trophy", has(trophy.get()))
				.save(output);
	}

	protected final void trapdoorBlock(RecipeOutput output, String name, Supplier<? extends Block> result, Supplier<? extends Block> material) {
		ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, result.get(), 2)
				.pattern("###")
				.pattern("###")
				.define('#', material.get())
				.unlockedBy("has_item", has(material.get()))
				.save(output, locWood(name + "_trapdoor"));
	}
	
	protected final void woodBlock(RecipeOutput output, String name, Supplier<? extends Block> result, Supplier<? extends Block> material) {
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result.get(), 3)
				.pattern("##")
				.pattern("##")
				.define('#', material.get())
				.unlockedBy("has_item", has(material.get()))
				.save(output, locWood(name + "_wood"));
	}

	protected final void strippedWoodBlock(RecipeOutput output, String name, Supplier<? extends Block> result, Supplier<? extends Block> material) {
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result.get(), 3)
				.pattern("##")
				.pattern("##")
				.define('#', material.get())
				.unlockedBy("has_item", has(material.get()))
				.save(output, locWood(name + "_stripped_wood"));
	}

	protected final void signBlock(RecipeOutput output, String name, Supplier<? extends Item> result, Supplier<? extends Block> material) {
		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result.get(), 3)
				.pattern("###")
				.pattern("###")
				.pattern(" - ")
				.define('#', material.get())
				.define('-', Tags.Items.RODS_WOODEN)
				.unlockedBy("has_item", has(material.get()))
				.save(output, locWood(name + "_sign"));
	}

	protected final void hangingSignBlock(RecipeOutput output, String name, Supplier<? extends Item> result, Supplier<? extends Block> material) {
		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result.get(), 6)
				.pattern("| |")
				.pattern("###")
				.pattern("###")
				.define('#', material.get())
				.define('|', Items.CHAIN)
				.unlockedBy("has_item", has(material.get()))
				.save(output, locWood(name + "_hanging_sign"));
	}

	protected final void banisterBlock(RecipeOutput output, String name, Supplier<? extends Block> result, Supplier<? extends Block> material) {
		this.banisterBlock(output, name, result, material.get());
	}

	protected final void banisterBlock(RecipeOutput output, String name, Supplier<? extends Block> result, Block material) {
		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result.get(), 3)
				.pattern("---")
				.pattern("| |")
				.define('-', material)
				.define('|', Tags.Items.RODS_WOODEN)
				.unlockedBy("has_item", has(material))
				.save(output, locWood(name + "_banister"));
	}

	protected final void chestBlock(RecipeOutput output, String name, Supplier<? extends TFChestBlock> result, Supplier<? extends Block> material) {
		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result.get(), 2)
				.pattern("###")
				.pattern("#C#")
				.pattern("###")
				.define('#', material.get())
				.define('C', Tags.Items.CHESTS_WOODEN)
				.unlockedBy("has_item", has(material.get()))
				.save(output, locWood(name + "_chest"));
	}

	protected final void fieryConversion(RecipeOutput output, Supplier<? extends Item> result, Item armor, int vials) {
		ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, result.get())
				.requires(armor)
				.requires(Ingredient.of(ItemTagGenerator.FIERY_VIAL), vials)
				.unlockedBy("has_item", has(ItemTagGenerator.FIERY_VIAL))
				.save(output, locEquip("fiery_" + ForgeRegistries.ITEMS.getKey(result.get()).getPath()));
	}

	protected final void buildBoats(RecipeOutput output, Supplier<? extends Item> boat, Supplier<? extends Item> chestBoat, Supplier<? extends Block> planks) {
		ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, boat.get())
				.pattern("P P")
				.pattern("PPP")
				.define('P', planks.get())
				.group("boat")
				.unlockedBy("in_water", insideOf(Blocks.WATER))
				.save(output);

		ShapelessRecipeBuilder.shapeless(RecipeCategory.TRANSPORTATION, chestBoat.get())
				.requires(boat.get())
				.requires(Tags.Items.CHESTS_WOODEN)
				.group("chest_boat")
				.unlockedBy("has_boat", has(ItemTags.BOATS))
				.save(output);
	}

	protected final ResourceLocation locCastle(String name) {
		return TwilightForestMod.prefix("castleblock/" + name);
	}

	protected final ResourceLocation locEquip(String name) {
		return TwilightForestMod.prefix("equipment/" + name);
	}

	protected final ResourceLocation locNaga(String name) {
		return TwilightForestMod.prefix("nagastone/" + name);
	}

	protected final ResourceLocation locWood(String name) {
		return TwilightForestMod.prefix("wood/" + name);
	}

	protected static Criterion<InventoryChangeTrigger.TriggerInstance> has(TagKey<Item> tag) {
		return inventoryTrigger(ItemPredicate.Builder.item().of(tag).build());
	}
}
