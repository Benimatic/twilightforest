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

public class StrongholdRightTurnComponent extends StructureTFStrongholdComponent {

	public StrongholdRightTurnComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
		super(StrongholdPieces.TFSRT, nbt);
	}

	public StrongholdRightTurnComponent(TFFeature feature, int i, Direction facing, int x, int y, int z) {
		super(StrongholdPieces.TFSRT, feature, i, facing, x, y, z);
	}

	@Override
	public BoundingBox generateBoundingBox(Direction facing, int x, int y, int z) {
		return StructureTFStrongholdComponent.getComponentToAddBoundingBox(x, y, z, -4, -1, 0, 9, 7, 9, facing);
	}

	@Override
	public void addChildren(StructurePiece parent, StructurePieceAccessor list, Random random) {
		super.addChildren(parent, list, random);

		// entrance
		this.addDoor(4, 1, 0);

		// make a random component to the right
		addNewComponent(parent, list, random, Rotation.CLOCKWISE_90, -1, 1, 4);
	}

	@Override
	public void postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator generator, Random rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		placeStrongholdWalls(world, sbb, 0, 0, 0, 8, 6, 8, rand, deco.randomBlocks);

		// clear inside
		generateAirBox(world, sbb, 1, 1, 1, 7, 5, 7);

//		// entrance doorway
//		placeDoorwayAt(world, rand, 2, 4, 1, 0, sbb);
//		
//		// right turn doorway
//		placeDoorwayAt(world, rand, 1, 0, 1, 4, sbb);

		// statue
		placeCornerStatue(world, 6, 1, 6, 3, sbb);

		// doors
		placeDoors(world, sbb);
	}
}
