package twilightforest.world.components.structures.darktower;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import twilightforest.world.components.structures.lichtower.TowerWingComponent;
import twilightforest.world.registration.TFFeature;

import java.util.Random;

public class DarkTowerRoofRingsComponent extends DarkTowerRoofComponent {

	public DarkTowerRoofRingsComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
		super(DarkTowerPieces.TFDTRR, nbt);
	}

	public DarkTowerRoofRingsComponent(TFFeature feature, int i, TowerWingComponent wing, int x, int y, int z) {
		super(DarkTowerPieces.TFDTRR, feature, i, wing, x, y, z);
	}

	@Override
	public boolean postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator generator, Random rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		super.postProcess(world, manager, generator, rand, sbb, chunkPosIn, blockPos);

		// antenna
		for (int y = 1; y < 10; y++) {
			placeBlock(world, deco.blockState, size / 2, y, size / 2, sbb);
		}

		placeBlock(world, deco.accentState, size / 2, 10, size / 2, sbb);

		placeBlock(world, deco.accentState, size / 2 - 1, 1, size / 2, sbb);
		placeBlock(world, deco.accentState, size / 2 + 1, 1, size / 2, sbb);
		placeBlock(world, deco.accentState, size / 2, 1, size / 2 - 1, sbb);
		placeBlock(world, deco.accentState, size / 2, 1, size / 2 + 1, sbb);

		makeARing(world, 6, sbb);
		makeARing(world, 8, sbb);

		return true;
	}

	protected void makeARing(WorldGenLevel world, int y, BoundingBox sbb) {
		placeBlock(world, deco.accentState, size / 2 - 2, y, size / 2 + 1, sbb);
		placeBlock(world, deco.accentState, size / 2 - 2, y, size / 2, sbb);
		placeBlock(world, deco.accentState, size / 2 - 2, y, size / 2 - 1, sbb);
		placeBlock(world, deco.accentState, size / 2 + 2, y, size / 2 + 1, sbb);
		placeBlock(world, deco.accentState, size / 2 + 2, y, size / 2, sbb);
		placeBlock(world, deco.accentState, size / 2 + 2, y, size / 2 - 1, sbb);
		placeBlock(world, deco.accentState, size / 2 + 1, y, size / 2 - 2, sbb);
		placeBlock(world, deco.accentState, size / 2, y, size / 2 - 2, sbb);
		placeBlock(world, deco.accentState, size / 2 - 1, y, size / 2 - 2, sbb);
		placeBlock(world, deco.accentState, size / 2 + 1, y, size / 2 + 2, sbb);
		placeBlock(world, deco.accentState, size / 2, y, size / 2 + 2, sbb);
		placeBlock(world, deco.accentState, size / 2 - 1, y, size / 2 + 2, sbb);
	}
}
