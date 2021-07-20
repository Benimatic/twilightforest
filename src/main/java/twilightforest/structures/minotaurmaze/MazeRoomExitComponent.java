package twilightforest.structures.minotaurmaze;

import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.block.TFBlocks;

import java.util.Random;

public class MazeRoomExitComponent extends MazeRoomComponent {

	public MazeRoomExitComponent(TemplateManager manager, CompoundNBT nbt) {
		super(MinotaurMazePieces.TFMMRE, nbt);
	}

	public MazeRoomExitComponent(TFFeature feature, int i, Random rand, int x, int y, int z) {
		super(MinotaurMazePieces.TFMMRE, feature, i, rand, x, y, z);
	}

	@Override
	public boolean func_230383_a_(ISeedReader world, StructureManager manager, ChunkGenerator generator, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		super.func_230383_a_(world, manager, generator, rand, sbb, chunkPosIn, blockPos);

		// shaft down
		this.fillWithBlocks(world, sbb, 5, -5, 5, 10, 0, 10, TFBlocks.maze_stone_brick.get().getDefaultState(), AIR, false);
		this.fillWithBlocks(world, sbb, 5, 1, 5, 10, 1, 10, TFBlocks.maze_stone_decorative.get().getDefaultState(), AIR, false);
		this.fillWithBlocks(world, sbb, 5, 2, 5, 10, 3, 10, Blocks.IRON_BARS.getDefaultState(), AIR, false);
		this.fillWithBlocks(world, sbb, 5, 4, 5, 10, 4, 10, TFBlocks.maze_stone_decorative.get().getDefaultState(), AIR, false);
		this.fillWithAir(world, sbb, 6, -5, 6, 9, 4, 9);

		return true;
	}
}
