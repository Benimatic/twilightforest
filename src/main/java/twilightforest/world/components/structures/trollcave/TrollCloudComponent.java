package twilightforest.world.components.structures.trollcave;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.StructureFeatureManager;
import twilightforest.world.components.structures.TFStructureComponentOld;

import java.util.Random;

public class TrollCloudComponent extends TFStructureComponentOld {

	private final int size;
	private final int height;

	public TrollCloudComponent(ServerLevel level, CompoundTag nbt) {
		super(TrollCavePieces.TFTCloud, nbt);
		this.size = nbt.getInt("size");
		this.height = nbt.getInt("height");
	}

	@Override
	protected void addAdditionalSaveData(ServerLevel level, CompoundTag tagCompound) {
		super.addAdditionalSaveData(level, tagCompound);
		tagCompound.putInt("size", this.size);
		tagCompound.putInt("height", this.height);
	}

	@Override
	public boolean postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator generator, Random rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		placeCloud(world, sbb, 0, 0, 0, this.size - 1, 6, this.size - 1);

		return true;
	}

	protected void placeCloud(WorldGenLevel world, BoundingBox sbb, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		this.generateBox(world, sbb, minX, minY, minZ, maxX, maxY, maxZ, Blocks.WHITE_STAINED_GLASS.defaultBlockState(), Blocks.WHITE_STAINED_GLASS.defaultBlockState(), false);
		this.generateBox(world, sbb, minX + 2, minY + 2, minZ + 2, maxX - 2, maxY - 1, maxZ - 2, Blocks.QUARTZ_BLOCK.defaultBlockState(), Blocks.QUARTZ_BLOCK.defaultBlockState(), false);

	}
}
