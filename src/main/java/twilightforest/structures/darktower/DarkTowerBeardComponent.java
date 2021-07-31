package twilightforest.structures.darktower;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import twilightforest.TFFeature;
import twilightforest.block.TFBlocks;
import twilightforest.structures.TFStructureComponentOld;
import twilightforest.structures.lichtower.TowerWingComponent;

import java.util.Random;

public class DarkTowerBeardComponent extends TFStructureComponentOld {

	protected int size;
	protected int height;

	public DarkTowerBeardComponent(StructureManager manager, CompoundTag nbt) {
		super(DarkTowerPieces.TFDTBea, nbt);
		this.size = nbt.getInt("beardSize");
		this.height = nbt.getInt("beardHeight");
	}

	public DarkTowerBeardComponent(TFFeature feature, int i, TowerWingComponent wing) {
		super(DarkTowerPieces.TFDTBea, feature, i);

		this.setOrientation(wing.getOrientation());
		this.size = wing.size;
		this.height = size / 2;

		// just hang out at the very bottom of the tower
		this.boundingBox = new BoundingBox(wing.getBoundingBox().minX(), wing.getBoundingBox().minY() - this.height, wing.getBoundingBox().minZ(), wing.getBoundingBox().maxX(), wing.getBoundingBox().maxY(), wing.getBoundingBox().maxZ());
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag tagCompound) {
		super.addAdditionalSaveData(tagCompound);

		tagCompound.putInt("beardSize", this.size);
		tagCompound.putInt("beardHeight", this.height);
	}

	/**
	 * Makes a dark tower type beard
	 */
	@Override
	public boolean postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator generator, Random rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		makeDarkBeard(world, sbb, 0, 0, size - 1, height - 1, size - 1);

		return true;
	}

	protected void makeDarkBeard(WorldGenLevel world, BoundingBox sbb, int minX, int minZ, int maxX, int maxY, int maxZ) {
		BlockState frameState = TFBlocks.tower_wood_encased.get().defaultBlockState();

		for (int x = minX; x <= maxX; x++) {
			for (int z = minZ; z <= maxZ; z++) {
				if (x == minX || x == maxX || z == minZ || z == maxZ) {
					int length = Math.min(Math.abs(x - height) - 1, Math.abs(z - height) - 1);

					if (length == height - 1) {
						length++;
					}

					if (length == -1) {
						length = 1;
					}

					for (int y = maxY; y >= height - length; y--) {
						// wall
						this.placeBlock(world, frameState, x, y, z, sbb);
					}
				}
			}
		}
	}
}
