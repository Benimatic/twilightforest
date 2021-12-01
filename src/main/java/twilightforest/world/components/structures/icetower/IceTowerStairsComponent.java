package twilightforest.world.components.structures.icetower;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import twilightforest.world.components.structures.TFStructureComponentOld;
import twilightforest.world.components.structures.lichtower.TowerWingComponent;
import twilightforest.world.registration.TFFeature;

import java.util.Random;

public class IceTowerStairsComponent extends TowerWingComponent {

	public IceTowerStairsComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
		super(IceTowerPieces.TFITSt, nbt);
	}

	public IceTowerStairsComponent(TFFeature feature, int index, int x, int y, int z, int size, int height, Direction direction) {
		super(IceTowerPieces.TFITSt, feature, index, x, y, z, size, height, direction);
	}

	@Override
	public void addChildren(StructurePiece parent, StructurePieceAccessor list, Random rand) {
		if (parent != null && parent instanceof TFStructureComponentOld) {
			this.deco = ((TFStructureComponentOld) parent).deco;
		}
	}

	@Override
	public void postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator generator, Random rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		for (int x = 1; x < this.size; x++) {

			this.placeStairs(world, sbb, x, 1 - x, 5);

			for (int z = 0; z <= x; z++) {

				if (z > 0 && z <= this.size / 2) {
					this.placeStairs(world, sbb, x, 1 - x, 5 - z);
					this.placeStairs(world, sbb, x, 1 - x, 5 + z);
				}

				if (x <= this.size / 2) {
					this.placeStairs(world, sbb, z, 1 - x, 5 - x);
					this.placeStairs(world, sbb, z, 1 - x, 5 + x);
				}
			}
		}

		this.placeBlock(world, deco.blockState, 0, 0, 5, sbb);
	}

	private void placeStairs(WorldGenLevel world, BoundingBox sbb, int x, int y, int z) {
		if (this.getBlock(world, x, y, z, sbb).getMaterial().isReplaceable()) {
			this.placeBlock(world, deco.blockState, x, y, z, sbb);
			this.placeBlock(world, deco.blockState, x, y - 1, z, sbb);
		}
	}
}
