package twilightforest.data;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;

public class ItemTagGenerator extends ItemTagsProvider {
	public ItemTagGenerator(DataGenerator generator, BlockTagsProvider blockprovider) {
		super(generator, blockprovider);
	}

	@Override
	protected void registerTags() {
		this.func_240521_a_(Tags.Blocks.FENCES_WOODEN, Tags.Items.FENCES_WOODEN);
		this.func_240521_a_(Tags.Blocks.FENCE_GATES_WOODEN, Tags.Items.FENCE_GATES_WOODEN);
		this.func_240521_a_(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
		this.func_240521_a_(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
		this.func_240521_a_(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
		this.func_240521_a_(BlockTags.SLABS, ItemTags.SLABS);
		this.func_240521_a_(BlockTags.STAIRS, ItemTags.STAIRS);
		this.func_240521_a_(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
		this.func_240521_a_(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
		this.func_240521_a_(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
		this.func_240521_a_(BlockTags.LEAVES, ItemTags.LEAVES);
		this.func_240521_a_(BlockTags.LOGS, ItemTags.LOGS);
		this.func_240521_a_(BlockTags.PLANKS, ItemTags.PLANKS);
		this.func_240521_a_(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
	}
}
