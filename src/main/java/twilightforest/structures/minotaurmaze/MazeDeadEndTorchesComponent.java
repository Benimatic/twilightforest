package twilightforest.structures.minotaurmaze;

import net.minecraft.block.Blocks;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;

import java.util.Random;

public class MazeDeadEndTorchesComponent extends MazeDeadEndComponent {

	public MazeDeadEndTorchesComponent(TemplateManager manager, CompoundNBT nbt) {
		super(MinotaurMazePieces.TFMMDET, nbt);
	}

	public MazeDeadEndTorchesComponent(TFFeature feature, int i, int x, int y, int z, Direction rotation) {
		super(MinotaurMazePieces.TFMMDET, feature, i, x, y, z, rotation);
	}

	@Override
	public boolean func_230383_a_(ISeedReader world, StructureManager manager, ChunkGenerator generator, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		// normal doorway
		super.func_230383_a_(world, manager, generator, rand, sbb, chunkPosIn, blockPos);

		// torches!
		this.fillWithBlocks(world, sbb, 2, 1, 4, 3, 4, 4, Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.HORIZONTAL_FACING, Direction.SOUTH), AIR, false);
		this.fillWithBlocks(world, sbb, 1, 1, 1, 1, 4, 4, Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.HORIZONTAL_FACING, Direction.WEST), AIR, false);
		this.fillWithBlocks(world, sbb, 4, 1, 1, 4, 4, 4, Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.HORIZONTAL_FACING, Direction.EAST), AIR, false);


		return true;
	}
}
