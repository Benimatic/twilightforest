package twilightforest.data;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.data.TagsProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import twilightforest.TwilightForestMod;
import twilightforest.block.TFBlocks;
import twilightforest.item.TFItems;

public class ItemTagGenerator extends ItemTagsProvider {
	public static final ITag.INamedTag<Item> PAPER = ItemTags.makeWrapperTag("forge:paper");

	public static final ITag.INamedTag<Item> FIERY_VIAL = ItemTags.makeWrapperTag(TwilightForestMod.prefix("fiery_vial").toString());

	public static final ITag.INamedTag<Item> ARCTIC_FUR = ItemTags.makeWrapperTag(TwilightForestMod.prefix("arctic_fur").toString());
	public static final ITag.INamedTag<Item> CARMINITE_GEMS = ItemTags.makeWrapperTag(TwilightForestMod.prefix("gems/carminite").toString());
	public static final ITag.INamedTag<Item> FIERY_INGOTS = ItemTags.makeWrapperTag(TwilightForestMod.prefix("ingots/fiery").toString());
	public static final ITag.INamedTag<Item> IRONWOOD_INGOTS = ItemTags.makeWrapperTag(TwilightForestMod.prefix("ingots/ironwood").toString());
	public static final ITag.INamedTag<Item> KNIGHTMETAL_INGOTS = ItemTags.makeWrapperTag(TwilightForestMod.prefix("ingots/knightmetal").toString());
	public static final ITag.INamedTag<Item> STEELEAF_INGOTS = ItemTags.makeWrapperTag(TwilightForestMod.prefix("ingots/steeleaf").toString());

	public static final ITag.INamedTag<Item> STORAGE_BLOCKS_ARCTIC_FUR = ItemTags.makeWrapperTag(TwilightForestMod.prefix("storage_blocks/arctic_fur").toString());
	public static final ITag.INamedTag<Item> STORAGE_BLOCKS_CARMINITE = ItemTags.makeWrapperTag(TwilightForestMod.prefix("storage_blocks/carminite").toString());
	public static final ITag.INamedTag<Item> STORAGE_BLOCKS_FIERY = ItemTags.makeWrapperTag(TwilightForestMod.prefix("storage_blocks/fiery").toString());
	public static final ITag.INamedTag<Item> STORAGE_BLOCKS_IRONWOOD = ItemTags.makeWrapperTag(TwilightForestMod.prefix("storage_blocks/ironwood").toString());
	public static final ITag.INamedTag<Item> STORAGE_BLOCKS_KNIGHTMETAL = ItemTags.makeWrapperTag(TwilightForestMod.prefix("storage_blocks/knightmetal").toString());
	public static final ITag.INamedTag<Item> STORAGE_BLOCKS_STEELEAF = ItemTags.makeWrapperTag(TwilightForestMod.prefix("storage_blocks/steeleaf").toString());

	public static final ITag.INamedTag<Item> ORES_IRONWOOD = ItemTags.makeWrapperTag(TwilightForestMod.prefix("ores/ironwood").toString());
	public static final ITag.INamedTag<Item> ORES_KNIGHTMETAL = ItemTags.makeWrapperTag(TwilightForestMod.prefix("ores/knightmetal").toString());

	public ItemTagGenerator(DataGenerator generator, BlockTagsProvider blockprovider) {
		super(generator, blockprovider);
	}

	@Override
	protected void registerTags() {
		this.copy(Tags.Blocks.FENCES_WOODEN, Tags.Items.FENCES_WOODEN);
		this.copy(Tags.Blocks.FENCE_GATES_WOODEN, Tags.Items.FENCE_GATES_WOODEN);
		this.copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
		this.copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
		this.copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
		this.copy(BlockTags.SLABS, ItemTags.SLABS);
		this.copy(BlockTags.STAIRS, ItemTags.STAIRS);
		this.copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
		this.copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
		this.copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
		this.copy(BlockTags.LEAVES, ItemTags.LEAVES);
		this.copy(BlockTags.LOGS, ItemTags.LOGS);
		this.copy(BlockTags.PLANKS, ItemTags.PLANKS);
		this.copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);

		getTag("forge:paper").add(Items.PAPER);
		getTag("forge:feather").add(TFItems.raven_feather.get());

		getTag(TwilightForestMod.prefix("fiery_vial").toString()).add(TFItems.fiery_blood.get()).add(TFItems.fiery_tears.get());

		getTag(TwilightForestMod.prefix("arctic_fur").toString()).add(TFItems.arctic_fur.get());
		getTag(TwilightForestMod.prefix("gems/carminite").toString()).add(TFItems.carminite.get());
		getTag(TwilightForestMod.prefix("ingots/fiery").toString()).add(TFItems.fiery_ingot.get());
		getTag(TwilightForestMod.prefix("ingots/ironwood").toString()).add(TFItems.ironwood_ingot.get());
		getTag(TwilightForestMod.prefix("ingots/knightmetal").toString()).add(TFItems.knightmetal_ingot.get());
		getTag(TwilightForestMod.prefix("ingots/steeleaf").toString()).add(TFItems.steeleaf_ingot.get());

		getTag(TwilightForestMod.prefix("storage_blocks/arctic_fur").toString()).add(TFBlocks.arctic_fur_block.get().asItem());
		getTag(TwilightForestMod.prefix("storage_blocks/carminite").toString()).add(TFBlocks.carminite_block.get().asItem());
		getTag(TwilightForestMod.prefix("storage_blocks/fiery").toString()).add(TFBlocks.fiery_block.get().asItem());
		getTag(TwilightForestMod.prefix("storage_blocks/ironwood").toString()).add(TFBlocks.ironwood_block.get().asItem());
		getTag(TwilightForestMod.prefix("storage_blocks/knightmetal").toString()).add(TFBlocks.knightmetal_block.get().asItem());
		getTag(TwilightForestMod.prefix("storage_blocks/steeleaf").toString()).add(TFBlocks.steeleaf_block.get().asItem());

		getTag(TwilightForestMod.prefix("ores/ironwood").toString()).add(TFItems.ironwood_raw.get());
		getTag(TwilightForestMod.prefix("ores/knightmetal").toString()).add(TFItems.armor_shard_cluster.get());
	}

	private TagsProvider.Builder<Item> getTag(String name) {
		return getOrCreateBuilder(ItemTags.makeWrapperTag(name));
	}
}
