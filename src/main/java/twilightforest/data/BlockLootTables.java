package twilightforest.data;

import net.minecraft.block.Block;
import net.minecraft.block.DoorBlock;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.world.storage.loot.LootTable;
import twilightforest.block.TFBlocks;

import java.util.HashSet;
import java.util.Set;

public class BlockLootTables extends net.minecraft.data.loot.BlockLootTables {
	private final Set<Block> knownBlocks = new HashSet<>();
	// [VanillaCopy] super
	private static final float[] DEFAULT_SAPLING_DROP_RATES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};
	private static final float[] RARE_SAPLING_DROP_RATES = new float[]{0.025F, 0.027777778F, 0.03125F, 0.041666668F, 0.1F};

	@Override
	protected void registerLootTable(Block block, LootTable.Builder builder) {
		super.registerLootTable(block, builder);
		knownBlocks.add(block);
	}

	@Override
	protected void addTables() {
		registerLootTable(TFBlocks.experiment_115.get(), LootTable.builder());
		registerDropSelfLootTable(TFBlocks.castle_pillar_encased.get());
		registerDropSelfLootTable(TFBlocks.castle_pillar_encased_tile.get());
		registerDropSelfLootTable(TFBlocks.castle_pillar_bold.get());
		registerDropSelfLootTable(TFBlocks.castle_pillar_bold_tile.get());
		registerDropSelfLootTable(TFBlocks.castle_stairs_brick.get());
		registerDropSelfLootTable(TFBlocks.castle_stairs_worn.get());
		registerDropSelfLootTable(TFBlocks.castle_stairs_cracked.get());
		registerDropSelfLootTable(TFBlocks.castle_stairs_mossy.get());
		registerDropSelfLootTable(TFBlocks.castle_stairs_encased.get());
		registerDropSelfLootTable(TFBlocks.castle_stairs_bold.get());
		registerDropSelfLootTable(TFBlocks.castle_rune_brick_purple.get());
		registerDropSelfLootTable(TFBlocks.castle_rune_brick_yellow.get());
		registerDropSelfLootTable(TFBlocks.castle_rune_brick_pink.get());
		registerDropSelfLootTable(TFBlocks.castle_rune_brick_blue.get());
		registerDropSelfLootTable(TFBlocks.castle_door_purple.get());
		registerDropSelfLootTable(TFBlocks.castle_door_yellow.get());
		registerDropSelfLootTable(TFBlocks.castle_door_pink.get());
		registerDropSelfLootTable(TFBlocks.castle_door_blue.get());
		registerDropSelfLootTable(TFBlocks.twilight_portal_miniature_structure.get());
		registerDropSelfLootTable(TFBlocks.naga_courtyard_miniature_structure.get());
		registerDropSelfLootTable(TFBlocks.lich_tower_miniature_structure.get());
		registerDropSelfLootTable(TFBlocks.knightmetal_block.get());
		registerDropSelfLootTable(TFBlocks.ironwood_block.get());
		registerDropSelfLootTable(TFBlocks.fiery_block.get());
		registerDropSelfLootTable(TFBlocks.steeleaf_block.get());
		registerDropSelfLootTable(TFBlocks.arctic_fur_block.get());
		registerDropSelfLootTable(TFBlocks.carminite_block.get());
		registerDropSelfLootTable(TFBlocks.firefly_jar.get());
		registerDropSelfLootTable(TFBlocks.iron_ladder.get());
		registerDropSelfLootTable(TFBlocks.oak_log.get());
		registerDropSelfLootTable(TFBlocks.oak_wood.get());
		registerDropSelfLootTable(TFBlocks.oak_sapling.get());
		registerLootTable(TFBlocks.oak_leaves.get(), droppingWithChancesAndSticks(TFBlocks.oak_leaves.get(), TFBlocks.oak_sapling.get(), DEFAULT_SAPLING_DROP_RATES));
		registerDropSelfLootTable(TFBlocks.rainboak_sapling.get());
		registerLootTable(TFBlocks.rainboak_leaves.get(), droppingWithChancesAndSticks(TFBlocks.rainboak_leaves.get(), TFBlocks.rainboak_sapling.get(), RARE_SAPLING_DROP_RATES));
		registerDropSelfLootTable(TFBlocks.twilight_oak_planks.get());
		registerDropSelfLootTable(TFBlocks.twilight_oak_stairs.get());
		registerLootTable(TFBlocks.twilight_oak_slab.get(), droppingSlab(TFBlocks.twilight_oak_slab.get()));
		registerDropSelfLootTable(TFBlocks.twilight_oak_button.get());
		registerDropSelfLootTable(TFBlocks.twilight_oak_fence.get());
		registerDropSelfLootTable(TFBlocks.twilight_oak_gate.get());
		registerDropSelfLootTable(TFBlocks.twilight_oak_plate.get());
		registerLootTable(TFBlocks.twilight_oak_door.get(), (b) -> droppingWhen(b, DoorBlock.HALF, DoubleBlockHalf.LOWER));
		registerDropSelfLootTable(TFBlocks.twilight_oak_trapdoor.get());

		registerDropSelfLootTable(TFBlocks.canopy_log.get());
		registerDropSelfLootTable(TFBlocks.canopy_wood.get());
		registerDropSelfLootTable(TFBlocks.canopy_sapling.get());
		registerLootTable(TFBlocks.canopy_leaves.get(), droppingWithChancesAndSticks(TFBlocks.canopy_leaves.get(), TFBlocks.canopy_sapling.get(), DEFAULT_SAPLING_DROP_RATES));
		registerDropSelfLootTable(TFBlocks.canopy_planks.get());
		registerDropSelfLootTable(TFBlocks.canopy_stairs.get());
		registerLootTable(TFBlocks.canopy_slab.get(), droppingSlab(TFBlocks.canopy_slab.get()));
		registerDropSelfLootTable(TFBlocks.canopy_button.get());
		registerDropSelfLootTable(TFBlocks.canopy_fence.get());
		registerDropSelfLootTable(TFBlocks.canopy_gate.get());
		registerDropSelfLootTable(TFBlocks.canopy_plate.get());
		registerLootTable(TFBlocks.canopy_door.get(), (b) -> droppingWhen(b, DoorBlock.HALF, DoubleBlockHalf.LOWER));
		registerDropSelfLootTable(TFBlocks.canopy_trapdoor.get());

		registerDropSelfLootTable(TFBlocks.mangrove_log.get());
		registerDropSelfLootTable(TFBlocks.mangrove_wood.get());
		registerDropSelfLootTable(TFBlocks.mangrove_sapling.get());
		registerLootTable(TFBlocks.mangrove_leaves.get(), droppingWithChancesAndSticks(TFBlocks.mangrove_leaves.get(), TFBlocks.mangrove_sapling.get(), DEFAULT_SAPLING_DROP_RATES));
		registerDropSelfLootTable(TFBlocks.mangrove_planks.get());
		registerDropSelfLootTable(TFBlocks.mangrove_stairs.get());
		registerLootTable(TFBlocks.mangrove_slab.get(), droppingSlab(TFBlocks.mangrove_slab.get()));
		registerDropSelfLootTable(TFBlocks.mangrove_button.get());
		registerDropSelfLootTable(TFBlocks.mangrove_fence.get());
		registerDropSelfLootTable(TFBlocks.mangrove_gate.get());
		registerDropSelfLootTable(TFBlocks.mangrove_plate.get());
		registerLootTable(TFBlocks.mangrove_door.get(), (b) -> droppingWhen(b, DoorBlock.HALF, DoubleBlockHalf.LOWER));
		registerDropSelfLootTable(TFBlocks.mangrove_trapdoor.get());

		registerDropSelfLootTable(TFBlocks.dark_log.get());
		registerDropSelfLootTable(TFBlocks.dark_wood.get());
		registerDropSelfLootTable(TFBlocks.darkwood_sapling.get());
		registerLootTable(TFBlocks.dark_leaves.get(), droppingWithChancesAndSticks(TFBlocks.dark_leaves.get(), TFBlocks.darkwood_sapling.get(), RARE_SAPLING_DROP_RATES));
		registerDropSelfLootTable(TFBlocks.dark_planks.get());
		registerDropSelfLootTable(TFBlocks.dark_stairs.get());
		registerLootTable(TFBlocks.dark_slab.get(), droppingSlab(TFBlocks.dark_slab.get()));
		registerDropSelfLootTable(TFBlocks.dark_button.get());
		registerDropSelfLootTable(TFBlocks.dark_fence.get());
		registerDropSelfLootTable(TFBlocks.dark_gate.get());
		registerDropSelfLootTable(TFBlocks.dark_plate.get());
		registerLootTable(TFBlocks.dark_door.get(), (b) -> droppingWhen(b, DoorBlock.HALF, DoubleBlockHalf.LOWER));
		registerDropSelfLootTable(TFBlocks.dark_trapdoor.get());

		registerDropSelfLootTable(TFBlocks.time_log.get());
		registerDropSelfLootTable(TFBlocks.time_wood.get());
		registerDropSelfLootTable(TFBlocks.time_sapling.get());
		registerLootTable(TFBlocks.time_leaves.get(), droppingWithChancesAndSticks(TFBlocks.time_leaves.get(), TFBlocks.time_sapling.get(), RARE_SAPLING_DROP_RATES));
		registerDropSelfLootTable(TFBlocks.time_planks.get());
		registerDropSelfLootTable(TFBlocks.time_stairs.get());
		registerLootTable(TFBlocks.time_slab.get(), droppingSlab(TFBlocks.time_slab.get()));
		registerDropSelfLootTable(TFBlocks.time_button.get());
		registerDropSelfLootTable(TFBlocks.time_fence.get());
		registerDropSelfLootTable(TFBlocks.time_gate.get());
		registerDropSelfLootTable(TFBlocks.time_plate.get());
		registerLootTable(TFBlocks.time_door.get(), (b) -> droppingWhen(b, DoorBlock.HALF, DoubleBlockHalf.LOWER));
		registerDropSelfLootTable(TFBlocks.time_trapdoor.get());

		registerDropSelfLootTable(TFBlocks.transformation_log.get());
		registerDropSelfLootTable(TFBlocks.transformation_wood.get());
		registerDropSelfLootTable(TFBlocks.transformation_sapling.get());
		registerLootTable(TFBlocks.transformation_leaves.get(), droppingWithChancesAndSticks(TFBlocks.transformation_leaves.get(), TFBlocks.transformation_sapling.get(), RARE_SAPLING_DROP_RATES));
		registerDropSelfLootTable(TFBlocks.trans_planks.get());
		registerDropSelfLootTable(TFBlocks.trans_stairs.get());
		registerLootTable(TFBlocks.trans_slab.get(), droppingSlab(TFBlocks.trans_slab.get()));
		registerDropSelfLootTable(TFBlocks.trans_button.get());
		registerDropSelfLootTable(TFBlocks.trans_fence.get());
		registerDropSelfLootTable(TFBlocks.trans_gate.get());
		registerDropSelfLootTable(TFBlocks.trans_plate.get());
		registerLootTable(TFBlocks.trans_door.get(), (b) -> droppingWhen(b, DoorBlock.HALF, DoubleBlockHalf.LOWER));
		registerDropSelfLootTable(TFBlocks.trans_trapdoor.get());

		registerDropSelfLootTable(TFBlocks.mining_log.get());
		registerDropSelfLootTable(TFBlocks.mining_wood.get());
		registerDropSelfLootTable(TFBlocks.mining_sapling.get());
		registerLootTable(TFBlocks.mining_leaves.get(), droppingWithChancesAndSticks(TFBlocks.mining_leaves.get(), TFBlocks.mining_sapling.get(), RARE_SAPLING_DROP_RATES));
		registerDropSelfLootTable(TFBlocks.mine_planks.get());
		registerDropSelfLootTable(TFBlocks.mine_stairs.get());
		registerLootTable(TFBlocks.mine_slab.get(), droppingSlab(TFBlocks.mine_slab.get()));
		registerDropSelfLootTable(TFBlocks.mine_button.get());
		registerDropSelfLootTable(TFBlocks.mine_fence.get());
		registerDropSelfLootTable(TFBlocks.mine_gate.get());
		registerDropSelfLootTable(TFBlocks.mine_plate.get());
		registerLootTable(TFBlocks.mine_door.get(), (b) -> droppingWhen(b, DoorBlock.HALF, DoubleBlockHalf.LOWER));
		registerDropSelfLootTable(TFBlocks.mine_trapdoor.get());

		registerDropSelfLootTable(TFBlocks.sorting_log.get());
		registerDropSelfLootTable(TFBlocks.sorting_wood.get());
		registerDropSelfLootTable(TFBlocks.sorting_sapling.get());
		registerLootTable(TFBlocks.sorting_leaves.get(), droppingWithChancesAndSticks(TFBlocks.sorting_leaves.get(), TFBlocks.sorting_sapling.get(), RARE_SAPLING_DROP_RATES));
		registerDropSelfLootTable(TFBlocks.sort_planks.get());
		registerDropSelfLootTable(TFBlocks.sort_stairs.get());
		registerLootTable(TFBlocks.sort_slab.get(), droppingSlab(TFBlocks.sort_slab.get()));
		registerDropSelfLootTable(TFBlocks.sort_button.get());
		registerDropSelfLootTable(TFBlocks.sort_fence.get());
		registerDropSelfLootTable(TFBlocks.sort_gate.get());
		registerDropSelfLootTable(TFBlocks.sort_plate.get());
		registerLootTable(TFBlocks.sort_door.get(), (b) -> droppingWhen(b, DoorBlock.HALF, DoubleBlockHalf.LOWER));
		registerDropSelfLootTable(TFBlocks.sort_trapdoor.get());
	}

	@Override
	protected Iterable<Block> getKnownBlocks() {
		return knownBlocks;
	}
}
