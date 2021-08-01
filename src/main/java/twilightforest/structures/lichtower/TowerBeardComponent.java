package twilightforest.structures.lichtower;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import twilightforest.TFFeature;
import twilightforest.structures.TFStructureComponentOld;

import java.util.Random;

public class TowerBeardComponent extends TFStructureComponentOld {

	int size;
	int height;

	public TowerBeardComponent(ServerLevel level, CompoundTag nbt) {
		this(LichTowerPieces.TFLTBea, nbt);
	}

	public TowerBeardComponent(StructurePieceType piece, CompoundTag nbt) {
		super(piece, nbt);
		this.size = nbt.getInt("beardSize");
		this.height = nbt.getInt("beardHeight");
	}

	public TowerBeardComponent(StructurePieceType piece, TFFeature feature, int i, TowerWingComponent wing) {
		super(piece, feature, i);

		this.setOrientation(wing.getOrientation());
		this.size = wing.size - 2;
		this.height = size / 2;

		// just hang out at the very bottom of the tower
		this.boundingBox = new BoundingBox(wing.getBoundingBox().minX() + 1, wing.getBoundingBox().minY() - this.height - 1, wing.getBoundingBox().minZ() + 1, wing.getBoundingBox().maxX() - 1, wing.getBoundingBox().minY() - 1, wing.getBoundingBox().maxZ() - 1);
	}

	@Override
	protected void addAdditionalSaveData(ServerLevel level, CompoundTag tagCompound) {
		super.addAdditionalSaveData(level, tagCompound);
		tagCompound.putInt("beardSize", this.size);
		tagCompound.putInt("beardHeight", this.height);
	}

	/**
	 * Makes a pyramid-shaped beard
	 */
	@Override
	public boolean postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator generator, Random rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		return makePyramidBeard(world, rand, sbb);
	}

	private boolean makePyramidBeard(WorldGenLevel world, Random rand, BoundingBox sbb) {
		for (int y = 0; y <= height; y++) {
			int min = y;
			int max = size - y - 1;

			generateBox(world, sbb, min, height - y, min, max, height - y, max, false, rand, TFStructureComponentOld.getStrongholdStones());
		}
		return true;
	}
}
