package twilightforest.world.components.structures.finalcastle;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import twilightforest.world.registration.TFFeature;
import twilightforest.world.components.structures.TFStructureComponentOld;

import java.util.Random;

public class FinalCastleDungeonStepsComponent extends TFStructureComponentOld {

	public FinalCastleDungeonStepsComponent(ServerLevel level, CompoundTag nbt) {
		super(FinalCastlePieces.TFFCDunSt, nbt);
	}

	public FinalCastleDungeonStepsComponent(TFFeature feature, Random rand, int i, int x, int y, int z, Direction rotation) {
		super(FinalCastlePieces.TFFCDunSt, feature, i, x, y, z);
		this.spawnListIndex = 2; // dungeon monsters

		this.setOrientation(rotation);
		this.boundingBox = TFStructureComponentOld.getComponentToAddBoundingBox2(x, y, z, -2, -15, -3, 5, 15, 20, rotation);
	}

	@Override
	public void addChildren(StructurePiece parent, StructurePieceAccessor list, Random rand) {
		if (parent != null && parent instanceof TFStructureComponentOld) {
			this.deco = ((TFStructureComponentOld) parent).deco;
		}
	}

	/**
	 * build more steps towards the specified direction
	 */
	public FinalCastleDungeonStepsComponent buildMoreStepsTowards(StructurePiece parent, StructurePieceAccessor list, Random rand, Rotation rotation) {

		Direction direction = getStructureRelativeRotation(rotation);

		int sx = 2;
		int sy = 0;
		int sz = 17;

		switch (rotation) {
			case NONE -> sz -= 5;
			case CLOCKWISE_90 -> sx -= 5;
			case CLOCKWISE_180 -> sz += 5;
			case COUNTERCLOCKWISE_90 -> sx += 6;
		}

		// find center of landing
		int dx = this.getWorldX(sx, sz);
		int dy = this.getWorldY(sy);
		int dz = this.getWorldZ(sx, sz);


		// build a new stairway there
		FinalCastleDungeonStepsComponent steps = new FinalCastleDungeonStepsComponent(getFeatureType(), rand, this.genDepth + 1, dx, dy, dz, direction);
		list.addPiece(steps);
		steps.addChildren(this, list, rand);

		return steps;
	}

	/**
	 * build a new level under the exit
	 */
	public FinalCastleDungeonEntranceComponent buildLevelUnder(StructurePiece parent, StructurePieceAccessor list, Random rand, int level) {
		// find center of landing
		int dx = this.getWorldX(2, 19);
		int dy = this.getWorldY(-7);
		int dz = this.getWorldZ(2, 19);

		// build a new dungeon level under there
		FinalCastleDungeonEntranceComponent room = new FinalCastleDungeonEntranceComponent(getFeatureType(), rand, 8, dx, dy, dz, getOrientation(), level);
		list.addPiece(room);
		room.addChildren(this, list, rand);

		return room;
	}

	/**
	 * build the boss room
	 */
	public FinalCastleDungeonForgeRoomComponent buildBossRoomUnder(StructurePiece parent, StructurePieceAccessor list, Random rand) {
		// find center of landing
		int dx = this.getWorldX(2, 19);
		int dy = this.getWorldY(-31);
		int dz = this.getWorldZ(2, 19);

		// build a new dungeon level under there
		FinalCastleDungeonForgeRoomComponent room = new FinalCastleDungeonForgeRoomComponent(getFeatureType(), rand, 8, dx, dy, dz, this.getOrientation());
		list.addPiece(room);
		room.addChildren(this, list, rand);

		return room;
	}

	@Override
	public boolean postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator generator, Random rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		final BlockState stairState = deco.stairState.setValue(StairBlock.FACING, Direction.SOUTH);
		for (int z = 0; z < 15; z++) {
			int y = 14 - z;

			this.generateBox(world, sbb, 0, y, z, 4, y, z, stairState, stairState, false);
			this.generateAirBox(world, sbb, 0, y + 1, z, 4, y + 6, z);
		}
		this.generateAirBox(world, sbb, 0, 0, 15, 4, 5, 19);

		return true;
	}
}
