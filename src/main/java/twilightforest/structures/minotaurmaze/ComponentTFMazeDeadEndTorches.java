package twilightforest.structures.minotaurmaze;

import net.minecraft.block.Blocks;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;

import java.util.Random;

public class ComponentTFMazeDeadEndTorches extends ComponentTFMazeDeadEnd {

	public ComponentTFMazeDeadEndTorches(TemplateManager manager, CompoundNBT nbt) {
		super(TFMinotaurMazePieces.TFMMDET, nbt);
	}

	public ComponentTFMazeDeadEndTorches(TFFeature feature, int i, int x, int y, int z, Direction rotation) {
		super(TFMinotaurMazePieces.TFMMDET, feature, i, x, y, z, rotation);
	}

	@Override
	public boolean generate(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn) {
		// normal doorway
		super.generate(world, generator, rand, sbb, chunkPosIn);

		// torches!
		this.fillWithBlocks(world, sbb, 2, 1, 4, 3, 4, 4, Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.HORIZONTAL_FACING, Direction.SOUTH), AIR, false);
		this.fillWithBlocks(world, sbb, 1, 1, 1, 1, 4, 4, Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.HORIZONTAL_FACING, Direction.WEST), AIR, false);
		this.fillWithBlocks(world, sbb, 4, 1, 1, 4, 4, 4, Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.HORIZONTAL_FACING, Direction.EAST), AIR, false);


		return true;
	}
}
