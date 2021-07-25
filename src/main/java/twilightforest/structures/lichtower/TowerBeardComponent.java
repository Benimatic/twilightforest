package twilightforest.structures.lichtower;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;
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

	public TowerBeardComponent(StructureManager manager, CompoundTag nbt) {
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
		this.boundingBox = new BoundingBox(wing.getBoundingBox().x0 + 1, wing.getBoundingBox().y0 - this.height - 1, wing.getBoundingBox().z0 + 1, wing.getBoundingBox().x1 - 1, wing.getBoundingBox().y0 - 1, wing.getBoundingBox().z1 - 1);
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag tagCompound) {
		super.addAdditionalSaveData(tagCompound);
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
