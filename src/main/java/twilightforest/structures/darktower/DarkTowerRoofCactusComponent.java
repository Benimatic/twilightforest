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

public class DarkTowerRoofCactusComponent extends DarkTowerRoofComponent {

	public DarkTowerRoofCactusComponent(ServerLevel level, CompoundTag nbt) {
		super(DarkTowerPieces.TFDTRC, nbt);
	}

	public DarkTowerRoofCactusComponent(TFFeature feature, int i, TowerWingComponent wing) {
		super(DarkTowerPieces.TFDTRC, feature, i, wing);
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

		// cactus things
		placeBlock(world, deco.accentState, size / 2 + 1, 7, size / 2, sbb);
		placeBlock(world, deco.accentState, size / 2 + 2, 7, size / 2, sbb);
		placeBlock(world, deco.accentState, size / 2 + 2, 8, size / 2, sbb);
		placeBlock(world, deco.accentState, size / 2 + 2, 9, size / 2, sbb);
		placeBlock(world, deco.accentState, size / 2 + 3, 9, size / 2, sbb);

		placeBlock(world, deco.accentState, size / 2, 6, size / 2 + 1, sbb);
		placeBlock(world, deco.accentState, size / 2, 6, size / 2 + 2, sbb);
		placeBlock(world, deco.accentState, size / 2, 7, size / 2 + 2, sbb);
		placeBlock(world, deco.accentState, size / 2, 8, size / 2 + 2, sbb);
		placeBlock(world, deco.accentState, size / 2, 8, size / 2 + 3, sbb);

		placeBlock(world, deco.accentState, size / 2 - 1, 5, size / 2, sbb);
		placeBlock(world, deco.accentState, size / 2 - 2, 5, size / 2, sbb);
		placeBlock(world, deco.accentState, size / 2 - 2, 6, size / 2, sbb);
		placeBlock(world, deco.accentState, size / 2 - 2, 7, size / 2, sbb);
		placeBlock(world, deco.accentState, size / 2 - 3, 7, size / 2, sbb);

		placeBlock(world, deco.accentState, size / 2, 4, size / 2 - 1, sbb);
		placeBlock(world, deco.accentState, size / 2, 4, size / 2 - 2, sbb);
		placeBlock(world, deco.accentState, size / 2, 5, size / 2 - 2, sbb);
		placeBlock(world, deco.accentState, size / 2, 6, size / 2 - 2, sbb);
		placeBlock(world, deco.accentState, size / 2, 6, size / 2 - 3, sbb);

		return true;
	}
}
