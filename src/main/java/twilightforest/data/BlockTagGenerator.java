package twilightforest.data;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import twilightforest.block.TFBlocks;

public class BlockTagGenerator extends BlockTagsProvider {
	public BlockTagGenerator(DataGenerator generator) {
		super(generator);
	}

	@Override
	protected void registerTags() {
		func_240522_a_(Tags.Blocks.FENCES_WOODEN)
						.func_240534_a_(TFBlocks.twilight_oak_fence.get(), TFBlocks.canopy_fence.get(), TFBlocks.mangrove_fence.get(), TFBlocks.dark_fence.get())
						.func_240534_a_(TFBlocks.time_fence.get(), TFBlocks.trans_fence.get(), TFBlocks.mine_fence.get(), TFBlocks.sort_fence.get());
		func_240522_a_(Tags.Blocks.FENCE_GATES_WOODEN)
						.func_240534_a_(TFBlocks.twilight_oak_gate.get(), TFBlocks.canopy_gate.get(), TFBlocks.mangrove_gate.get(), TFBlocks.dark_gate.get())
						.func_240534_a_(TFBlocks.time_gate.get(), TFBlocks.trans_gate.get(), TFBlocks.mine_gate.get(), TFBlocks.sort_gate.get());
		func_240522_a_(BlockTags.WOODEN_DOORS)
						.func_240534_a_(TFBlocks.twilight_oak_door.get(), TFBlocks.canopy_door.get(), TFBlocks.mangrove_door.get(), TFBlocks.dark_door.get())
						.func_240534_a_(TFBlocks.time_door.get(), TFBlocks.trans_door.get(), TFBlocks.mine_door.get(), TFBlocks.sort_door.get());
		func_240522_a_(BlockTags.WOODEN_SLABS)
						.func_240534_a_(TFBlocks.twilight_oak_slab.get(), TFBlocks.canopy_slab.get(), TFBlocks.mangrove_slab.get(), TFBlocks.dark_slab.get())
						.func_240534_a_(TFBlocks.time_slab.get(), TFBlocks.trans_slab.get(), TFBlocks.mine_slab.get(), TFBlocks.sort_slab.get());
		func_240522_a_(BlockTags.WOODEN_STAIRS)
						.func_240534_a_(TFBlocks.twilight_oak_stairs.get(), TFBlocks.canopy_stairs.get(), TFBlocks.mangrove_stairs.get(), TFBlocks.dark_stairs.get())
						.func_240534_a_(TFBlocks.time_stairs.get(), TFBlocks.trans_stairs.get(), TFBlocks.mine_stairs.get(), TFBlocks.sort_stairs.get());
		// vanilla slab/stair tags don't include the wooden subtags by default, so we have to add them again >.>
		func_240522_a_(BlockTags.SLABS)
						.func_240534_a_(TFBlocks.aurora_slab.get())
						.func_240534_a_(TFBlocks.twilight_oak_slab.get(), TFBlocks.canopy_slab.get(), TFBlocks.mangrove_slab.get(), TFBlocks.dark_slab.get())
						.func_240534_a_(TFBlocks.time_slab.get(), TFBlocks.trans_slab.get(), TFBlocks.mine_slab.get(), TFBlocks.sort_slab.get());
		func_240522_a_(BlockTags.STAIRS)
						.func_240534_a_(TFBlocks.castle_stairs_brick.get(), TFBlocks.castle_stairs_worn.get(), TFBlocks.castle_stairs_cracked.get(), TFBlocks.castle_stairs_mossy.get(), TFBlocks.castle_stairs_encased.get(), TFBlocks.castle_stairs_bold.get())
						.func_240534_a_(TFBlocks.nagastone_stairs_left.get(), TFBlocks.nagastone_stairs_right.get(), TFBlocks.nagastone_stairs_mossy_left.get(), TFBlocks.nagastone_stairs_mossy_right.get(), TFBlocks.nagastone_stairs_weathered_left.get(), TFBlocks.nagastone_stairs_weathered_right.get())
						.func_240534_a_(TFBlocks.twilight_oak_stairs.get(), TFBlocks.canopy_stairs.get(), TFBlocks.mangrove_stairs.get(), TFBlocks.dark_stairs.get())
						.func_240534_a_(TFBlocks.time_stairs.get(), TFBlocks.trans_stairs.get(), TFBlocks.mine_stairs.get(), TFBlocks.sort_stairs.get());
		func_240522_a_(BlockTags.WOODEN_BUTTONS)
						.func_240534_a_(TFBlocks.twilight_oak_button.get(), TFBlocks.canopy_button.get(), TFBlocks.mangrove_button.get(), TFBlocks.dark_button.get())
						.func_240534_a_(TFBlocks.time_button.get(), TFBlocks.trans_button.get(), TFBlocks.mine_button.get(), TFBlocks.sort_button.get());
		func_240522_a_(BlockTags.WOODEN_PRESSURE_PLATES)
						.func_240534_a_(TFBlocks.twilight_oak_plate.get(), TFBlocks.canopy_plate.get(), TFBlocks.mangrove_plate.get(), TFBlocks.dark_plate.get())
						.func_240534_a_(TFBlocks.time_plate.get(), TFBlocks.trans_plate.get(), TFBlocks.mine_plate.get(), TFBlocks.sort_plate.get());
		func_240522_a_(BlockTags.WOODEN_TRAPDOORS)
						.func_240534_a_(TFBlocks.twilight_oak_trapdoor.get(), TFBlocks.canopy_trapdoor.get(), TFBlocks.mangrove_trapdoor.get(), TFBlocks.dark_trapdoor.get())
						.func_240534_a_(TFBlocks.time_trapdoor.get(), TFBlocks.trans_trapdoor.get(), TFBlocks.mine_trapdoor.get(), TFBlocks.sort_trapdoor.get());
		func_240522_a_(BlockTags.LEAVES)
						.func_240534_a_(TFBlocks.rainboak_leaves.get(), TFBlocks.oak_leaves.get(), TFBlocks.canopy_leaves.get(), TFBlocks.mangrove_leaves.get(), TFBlocks.dark_leaves.get())
						.func_240534_a_(TFBlocks.time_leaves.get(), TFBlocks.transformation_leaves.get(), TFBlocks.mining_leaves.get(), TFBlocks.sorting_leaves.get())
						.func_240534_a_(TFBlocks.thorn_leaves.get(), TFBlocks.beanstalk_leaves.get(), TFBlocks.giant_leaves.get());
	}
}
