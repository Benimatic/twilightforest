package twilightforest.structures.lichtower;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import twilightforest.TFFeature;

import java.util.Random;

public class TowerRoofAttachedSlabComponent extends TowerRoofSlabComponent {

	public TowerRoofAttachedSlabComponent(ServerLevel level, CompoundTag nbt) {
		super(LichTowerPieces.TFLTRAS, nbt);
	}

	public TowerRoofAttachedSlabComponent(TFFeature feature, int i, TowerWingComponent wing, int x, int y, int z) {
		super(LichTowerPieces.TFLTRAS, feature, i, wing, x, y, z);
	}

	/**
	 * Makes a flat, pyramid-shaped roof that is connected to the parent tower
	 */
	@Override
	public boolean postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator generator, Random rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		return makeConnectedCap(world, Blocks.BIRCH_SLAB.defaultBlockState(), Blocks.BIRCH_PLANKS.defaultBlockState(), sbb);
	}
}
