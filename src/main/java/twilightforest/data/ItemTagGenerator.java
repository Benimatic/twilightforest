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
import twilightforest.item.TFItems;

public class ItemTagGenerator extends ItemTagsProvider {
	public static final ITag.INamedTag<Item> PAPER = wrapTag("forge:paper");

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
	}

	private TagsProvider.Builder<Item> getTag(String name) {
		return getOrCreateBuilder(ItemTags.makeWrapperTag(name));
	}

	private static ITag.INamedTag<Item> wrapTag(String resourceLocation) {
		return ItemTags.makeWrapperTag(resourceLocation);
	}
}
