package twilightforest.world.components.structures.stronghold;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import twilightforest.world.registration.TFFeature;

import java.util.Random;

public class StrongholdUpperCorridorComponent extends StructureTFStrongholdComponent {

	public StrongholdUpperCorridorComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
		super(StrongholdPieces.TFSUCo, nbt);
	}

	public StrongholdUpperCorridorComponent(TFFeature feature, int i, Direction facing, int x, int y, int z) {
		super(StrongholdPieces.TFSUCo, feature, i, facing, x, y, z);
	}

	@Override
	public BoundingBox generateBoundingBox(Direction facing, int x, int y, int z) {
		return BoundingBox.orientBox(x, y, z, -2, -1, 0, 5, 5, 9, facing);
	}

	@Override
	public void addChildren(StructurePiece parent, StructurePieceAccessor list, Random random) {
		super.addChildren(parent, list, random);

		// make a random component at the end
		addNewUpperComponent(parent, list, random, Rotation.NONE, 2, 1, 9);
	}

	@Override
	public void postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator generator, Random rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		/*if (this.edgesLiquid(world, sbb)) {
			return false;
		} else */{
			placeUpperStrongholdWalls(world, sbb, 0, 0, 0, 4, 4, 8, rand, deco.randomBlocks);

			// entrance doorway
			placeSmallDoorwayAt(world, 2, 2, 1, 0, sbb);

			// end
			placeSmallDoorwayAt(world, 2, 2, 1, 8, sbb);
		}
	}

	@Override
	public boolean isComponentProtected() {
		return false;
	}
}
