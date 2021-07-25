package twilightforest.structures.finalcastle;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import twilightforest.TFFeature;
import twilightforest.structures.TFStructureComponentOld;
import twilightforest.util.RotationUtil;

import java.util.Random;

/**
 * A larger foundation that comes all the way from the top of a tower
 *
 * @author benma_000
 */
public class FinalCastleBellFoundation21Component extends FinalCastleFoundation13Component {

	public FinalCastleBellFoundation21Component(StructureManager manager, CompoundTag nbt) {
		super(FinalCastlePieces.TFFCBeF21, nbt);
	}

	public FinalCastleBellFoundation21Component(TFFeature feature, Random rand, int i, TFStructureComponentOld sideTower) {
		super(FinalCastlePieces.TFFCBeF21, feature, rand, i, sideTower);

		this.boundingBox = new BoundingBox(sideTower.getBoundingBox().x0 - 2, sideTower.getBoundingBox().y1 - 1, sideTower.getBoundingBox().z0 - 2, sideTower.getBoundingBox().x1 + 2, sideTower.getBoundingBox().y1, sideTower.getBoundingBox().z1 + 2);
	}

	@Override
	public boolean postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator generator, Random rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		int mid = 16;
		int low = 32;

		// assume square
		int size = this.boundingBox.x1 - this.boundingBox.x0;

		for (Rotation rotation : RotationUtil.ROTATIONS) {
			// do corner
			this.replaceAirAndLiquidDownwardsRotated(world, deco.blockState, 2, -1, 1, rotation, sbb);
			this.replaceAirAndLiquidDownwardsRotated(world, deco.blockState, 2, -mid, 0, rotation, sbb);
			this.replaceAirAndLiquidDownwardsRotated(world, deco.blockState, 1, -1, 2, rotation, sbb);
			this.replaceAirAndLiquidDownwardsRotated(world, deco.blockState, 0, -mid, 2, rotation, sbb);

			this.replaceAirAndLiquidDownwardsRotated(world, deco.blockState, 1, -low, 1, rotation, sbb);
			this.replaceAirAndLiquidDownwardsRotated(world, deco.blockState, 0, -low, 1, rotation, sbb);
			this.replaceAirAndLiquidDownwardsRotated(world, deco.blockState, 1, -low, 0, rotation, sbb);
			this.replaceAirAndLiquidDownwardsRotated(world, deco.blockState, 0, -low, 0, rotation, sbb);

			for (int x = 6; x < (size - 3); x += 4) {
				this.replaceAirAndLiquidDownwardsRotated(world, deco.blockState, x, -1, 1, rotation, sbb);
				this.replaceAirAndLiquidDownwardsRotated(world, deco.blockState, x, -mid, 0, rotation, sbb);
			}
		}

		return true;
	}
}
