package twilightforest.structures.finalcastle;

import net.minecraft.block.StairsBlock;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.structures.StructureTFComponentOld;

import java.util.List;
import java.util.Random;

/**
 * Stair blocks heading to the entrance tower doors
 */
public class ComponentTFFinalCastleEntranceStairs extends StructureTFComponentOld {

	public ComponentTFFinalCastleEntranceStairs(TemplateManager manager, CompoundNBT nbt) {
		super(TFFinalCastlePieces.TFFCEnSt, nbt);
	}

	public ComponentTFFinalCastleEntranceStairs(TFFeature feature, int index, int x, int y, int z, Direction direction) {
		super(TFFinalCastlePieces.TFFCEnSt, feature, index);
		this.setCoordBaseMode(direction);
		this.boundingBox = StructureTFComponentOld.getComponentToAddBoundingBox2(x, y, z, 0, -1, -5, 12, 0, 12, direction);
	}

	@Override
	public void buildComponent(StructurePiece parent, List<StructurePiece> list, Random rand) {
		if (parent != null && parent instanceof StructureTFComponentOld) {
			this.deco = ((StructureTFComponentOld) parent).deco;
		}
	}

	@Override
	public boolean func_230383_a_(ISeedReader world, StructureManager manager, ChunkGenerator generator, Random randomIn, MutableBoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		int size = 13;

		for (int x = 1; x < size; x++) {

			this.placeStairs(world, sbb, x, 1 - x, 5, Direction.EAST);

			for (int z = 0; z <= x; z++) {

				if (z > 0 && z <= size / 2) {
					this.placeStairs(world, sbb, x, 1 - x, 5 - z, Direction.EAST);
					this.placeStairs(world, sbb, x, 1 - x, 5 + z, Direction.EAST);
				}

				if (x <= size / 2) {
					this.placeStairs(world, sbb, z, 1 - x, 5 - x, Direction.NORTH);
					this.placeStairs(world, sbb, z, 1 - x, 5 + x, Direction.SOUTH);
				}
			}
		}

		this.replaceAirAndLiquidDownwards(world, deco.blockState, 0, 0, 5, sbb);

		return true;
	}

	private void placeStairs(ISeedReader world, MutableBoundingBox sbb, int x, int y, int z, Direction facing) {
		if (this.getBlockStateFromPos(world, x, y, z, sbb).getMaterial().isReplaceable()) { //TODO: Probably doesn't support replaceable blocks
			//this.setBlockState(world, deco.blockState, x, y, z, sbb);
			this.setBlockState(world, deco.stairState.with(StairsBlock.FACING, facing), x, y, z, sbb);
			this.replaceAirAndLiquidDownwards(world, deco.blockState, x, y - 1, z, sbb);
		}
	}
}
