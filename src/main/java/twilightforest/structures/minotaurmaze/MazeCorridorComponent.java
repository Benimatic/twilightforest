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
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import twilightforest.TFFeature;
import twilightforest.structures.TFStructureComponentOld;

import java.util.Random;

public class MazeCorridorComponent extends TFStructureComponentOld {

	public MazeCorridorComponent(ServerLevel level, CompoundTag nbt) {
		this(MinotaurMazePieces.TFMMC, nbt);
	}

	public MazeCorridorComponent(StructurePieceType piece, CompoundTag nbt) {
		super(piece, nbt);
	}

	public MazeCorridorComponent(StructurePieceType type, TFFeature feature, int i, int x, int y, int z, Direction rotation) {
		super(type, feature, i);
		this.setOrientation(rotation);
		this.boundingBox = new BoundingBox(x, y, z, x + 5, y + 5, z + 5);
	}

	@Override
	public boolean postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator generator, Random rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		//arch
		this.generateBox(world, sbb, 1, 1, 2, 4, 4, 3, Blocks.OAK_FENCE.defaultBlockState(), AIR, false);
		this.generateAirBox(world, sbb, 2, 1, 2, 3, 3, 3);

		return true;
	}
}
