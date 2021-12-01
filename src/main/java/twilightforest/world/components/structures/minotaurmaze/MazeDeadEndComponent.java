package twilightforest.world.components.structures.minotaurmaze;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import twilightforest.world.components.structures.TFStructureComponentOld;
import twilightforest.world.registration.TFFeature;

import java.util.Random;

public class MazeDeadEndComponent extends TFStructureComponentOld {

	public MazeDeadEndComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
		this(MinotaurMazePieces.TFMMDE, nbt);
	}

	public MazeDeadEndComponent(StructurePieceType piece, CompoundTag nbt) {
		super(piece, nbt);
	}

	public MazeDeadEndComponent(StructurePieceType type, TFFeature feature, int i, int x, int y, int z, Direction rotation) {
		super(type, feature, i, x, y, z);
		this.setOrientation(rotation);
		this.boundingBox = new BoundingBox(x, y, z, x + 5, y + 5, z + 5);
	}

	@Override
	public void postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator generator, Random rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		this.generateBox(world, sbb, 1, 1, 0, 4, 4, 0, Blocks.OAK_FENCE.defaultBlockState(), AIR, false);
		this.generateAirBox(world, sbb, 2, 1, 0, 3, 3, 0);
	}
}
