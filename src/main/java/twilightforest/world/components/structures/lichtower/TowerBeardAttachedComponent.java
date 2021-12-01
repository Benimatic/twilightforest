package twilightforest.world.components.structures.lichtower;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import twilightforest.world.components.structures.TFStructureComponentOld;
import twilightforest.world.registration.TFFeature;

import java.util.Random;

public class TowerBeardAttachedComponent extends TowerBeardComponent {

	public TowerBeardAttachedComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
		super(LichTowerPieces.TFLTBA, nbt);
	}

	public TowerBeardAttachedComponent(TFFeature feature, int i, TowerWingComponent wing, int x, int y, int z) {
		super(LichTowerPieces.TFLTBA, feature, i, wing, x, y, z);

		// just hang out at the very bottom of the tower
		this.boundingBox = new BoundingBox(wing.getBoundingBox().minX(), wing.getBoundingBox().minY() - this.height - 1, wing.getBoundingBox().minZ(), wing.getBoundingBox().maxX(), wing.getBoundingBox().maxY() - 1, wing.getBoundingBox().maxZ());
	}

	/**
	 * Makes a pyramid-shaped beard
	 */
	@Override
	public void postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator generator, Random rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		makeAttachedBeard(world, rand, sbb);
	}

	private void makeAttachedBeard(WorldGenLevel world, Random rand, BoundingBox sbb) {
		for (int y = 0; y <= height; y++) {
			int min = y + 1;
			int max = size - y;

			generateBox(world, sbb, 0, height - y, min, max, height - y, max, false, rand, TFStructureComponentOld.getStrongholdStones());
		}
	}
}
