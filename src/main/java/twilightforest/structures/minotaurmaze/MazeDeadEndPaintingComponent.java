package twilightforest.structures.minotaurmaze;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import twilightforest.TFFeature;

import java.util.Random;

public class MazeDeadEndPaintingComponent extends MazeDeadEndComponent {

	public MazeDeadEndPaintingComponent(ServerLevel level, CompoundTag nbt) {
		super(MinotaurMazePieces.TFMMDEP, nbt);
	}

	public MazeDeadEndPaintingComponent(TFFeature feature, int i, int x, int y, int z, Direction rotation) {
		super(MinotaurMazePieces.TFMMDEP, feature, i, x, y, z, rotation);
	}

	@Override
	public boolean postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator generator, Random rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		// normal doorway
		super.postProcess(world, manager, generator, rand, sbb, chunkPosIn, blockPos);

		// torches
		this.placeBlock(world, Blocks.WALL_TORCH.defaultBlockState().setValue(WallTorchBlock.FACING, Direction.WEST), 1, 3, 3, sbb);
		this.placeBlock(world, Blocks.WALL_TORCH.defaultBlockState().setValue(WallTorchBlock.FACING, Direction.EAST), 4, 3, 3, sbb);

//		// painting
//		EntityPainting painting = new EntityPainting(world, pCoords.posX, pCoords.posY, pCoords.posZ, this.get); 
//		painting.art = getPaintingOfSize(rand, minSize);
//		painting.setDirection(direction);
//		
//		world.addEntity(painting);

		return true;
	}
}
