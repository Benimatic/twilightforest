package twilightforest.structures.darktower;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import twilightforest.TFFeature;
import twilightforest.structures.lichtower.TowerWingComponent;

import java.util.Random;

public class DarkTowerRoofAntennaComponent extends DarkTowerRoofComponent {

	public DarkTowerRoofAntennaComponent(ServerLevel level, CompoundTag nbt) {
		super(DarkTowerPieces.TFDTRA, nbt);
	}

	public DarkTowerRoofAntennaComponent(TFFeature feature, int i, TowerWingComponent wing, int x, int y, int z) {
		super(DarkTowerPieces.TFDTRA, feature, i, wing, x, y, z);
	}

	/**
	 * Stick with a ball on top antenna
	 */
	@Override
	public boolean postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator generator, Random rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		super.postProcess(world, manager, generator, rand, sbb, chunkPosIn, blockPos);

		// antenna
		for (int y = 1; y < 10; y++) {
			placeBlock(world, deco.accentState, size / 2, y, size / 2, sbb);
		}

		placeBlock(world, deco.accentState, size / 2 - 1, 1, size / 2, sbb);
		placeBlock(world, deco.accentState, size / 2 + 1, 1, size / 2, sbb);
		placeBlock(world, deco.accentState, size / 2, 1, size / 2 - 1, sbb);
		placeBlock(world, deco.accentState, size / 2, 1, size / 2 + 1, sbb);

		for (int y = 7; y < 10; y++) {
			placeBlock(world, deco.accentState, size / 2 - 1, y, size / 2, sbb);
			placeBlock(world, deco.accentState, size / 2 + 1, y, size / 2, sbb);
			placeBlock(world, deco.accentState, size / 2, y, size / 2 - 1, sbb);
			placeBlock(world, deco.accentState, size / 2, y, size / 2 + 1, sbb);
		}

		placeBlock(world, deco.accentState, size / 2 - 1, 8, size / 2 - 1, sbb);
		placeBlock(world, deco.accentState, size / 2 - 1, 8, size / 2 + 1, sbb);
		placeBlock(world, deco.accentState, size / 2 + 1, 8, size / 2 - 1, sbb);
		placeBlock(world, deco.accentState, size / 2 + 1, 8, size / 2 + 1, sbb);

		return true;
	}
}
