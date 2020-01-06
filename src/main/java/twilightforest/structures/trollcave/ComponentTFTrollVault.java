package twilightforest.structures.trollcave;

import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.MutableBoundingBox;
import twilightforest.TFFeature;
import twilightforest.loot.TFTreasure;
import twilightforest.block.TFBlocks;
import twilightforest.structures.StructureTFComponentOld;

import java.util.Random;

public class ComponentTFTrollVault extends StructureTFComponentOld {

	public ComponentTFTrollVault() {
	}

	public ComponentTFTrollVault(TFFeature feature, int index, int x, int y, int z) {
		super(feature, index);
		this.setCoordBaseMode(Direction.SOUTH);

		// adjust x, y, z
		x = (x >> 2) << 2;
		y = (y / 4) * 4;
		z = (z >> 2) << 2;

		// spawn list!
		this.spawnListIndex = -1;

		this.boundingBox = StructureTFComponentOld.getComponentToAddBoundingBox(x, y, z, -8, 0, -8, 12, 12, 12, Direction.SOUTH);
	}

	@Override
	public boolean addComponentParts(IWorld world, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn) {
		// make walls
		this.fillWithBlocks(world, sbb, 0, 0, 0, 11, 11, 11, TFBlocks.giant_obsidian.get().getDefaultState(), TFBlocks.giant_obsidian.get().getDefaultState(), false);

		// clear inside
		this.fillWithAir(world, sbb, 4, 4, 4, 7, 7, 7);

		// cobblestone platform
		this.fillWithBlocks(world, sbb, 5, 5, 5, 6, 5, 6, Blocks.COBBLESTONE.getDefaultState(), Blocks.COBBLESTONE.getDefaultState(), false);

		// chests
		this.setBlockState(world, Blocks.CHEST.getDefaultState(), 5, 6, 5, sbb);
		this.placeTreasureAtCurrentPosition(world.getWorld(), rand, 5, 6, 6, TFTreasure.troll_vault, false, sbb);

		this.placeTreasureAtCurrentPosition(world.getWorld(), rand, 6, 6, 5, TFTreasure.troll_garden, true, sbb);
		this.setBlockState(world, Blocks.TRAPPED_CHEST.getDefaultState(), 6, 6, 6, sbb);

		return true;
	}

}
