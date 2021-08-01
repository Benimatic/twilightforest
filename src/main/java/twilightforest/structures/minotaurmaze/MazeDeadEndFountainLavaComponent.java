package twilightforest.structures.minotaurmaze;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import twilightforest.TFFeature;

import java.util.Random;

public class MazeDeadEndFountainLavaComponent extends MazeDeadEndFountainComponent {

	public MazeDeadEndFountainLavaComponent(ServerLevel level, CompoundTag nbt) {
		super(MinotaurMazePieces.TFMMDEFL, nbt);
	}

	public MazeDeadEndFountainLavaComponent(TFFeature feature, int i, int x, int y, int z, Direction rotation) {
		super(MinotaurMazePieces.TFMMDEFL, feature, i, x, y, z, rotation);
	}

	@Override
	public boolean postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator generator, Random rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		// normal fountain
		super.postProcess(world, manager, generator, rand, sbb, chunkPosIn, blockPos);

		// remove water
		this.placeBlock(world, AIR, 2, 3, 4, sbb);
		this.placeBlock(world, AIR, 3, 3, 4, sbb);

		// lava instead of water
		this.placeBlock(world, Blocks.LAVA.defaultBlockState(), 2, 3, 4, sbb);
		this.placeBlock(world, Blocks.LAVA.defaultBlockState(), 3, 3, 4, sbb);

		return true;
	}
}
