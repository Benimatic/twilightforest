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

public class DarkTowerRoofFourPostComponent extends DarkTowerRoofComponent {

	public DarkTowerRoofFourPostComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
		super(DarkTowerPieces.TFDTRFP, nbt);
	}

	public DarkTowerRoofFourPostComponent(TFFeature feature, int i, TowerWingComponent wing, int x, int y, int z) {
		super(DarkTowerPieces.TFDTRFP, feature, i, wing, x, y, z);
	}

	@Override
	public boolean postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator generator, Random rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		super.postProcess(world, manager, generator, rand, sbb, chunkPosIn, blockPos);

		makeSmallAntenna(world, sbb, 4, size - 2, size - 2);
		makeSmallAntenna(world, sbb, 5, 1, size - 2);
		makeSmallAntenna(world, sbb, 6, size - 2, 1);
		makeSmallAntenna(world, sbb, 7, 1, 1);

		return true;
	}

	private void makeSmallAntenna(WorldGenLevel world, BoundingBox sbb, int height, int x, int z) {
		// antenna
		for (int y = 1; y < height; y++) {
			placeBlock(world, deco.blockState, x, y, z, sbb);
		}
		placeBlock(world, deco.accentState, x, height, z, sbb);
		placeBlock(world, deco.accentState, x, height + 1, z, sbb);
		placeBlock(world, deco.accentState, x + 1, height + 1, z, sbb);
		placeBlock(world, deco.accentState, x - 1, height + 1, z, sbb);
		placeBlock(world, deco.accentState, x, height + 1, z + 1, sbb);
		placeBlock(world, deco.accentState, x, height + 1, z - 1, sbb);
		placeBlock(world, deco.accentState, x, height + 2, z, sbb);
	}
}
