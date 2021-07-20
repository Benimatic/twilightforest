package twilightforest.structures.finalcastle;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.block.TFBlocks;
import twilightforest.structures.TFStructureComponentOld;
import twilightforest.util.RotationUtil;

import java.util.List;
import java.util.Random;

public class FinalCastleRoof48CrenellatedComponent extends TFStructureComponentOld {

	public FinalCastleRoof48CrenellatedComponent(TemplateManager manager, CompoundNBT nbt) {
		super(FinalCastlePieces.TFFCRo48Cr, nbt);
	}

	//TODO: Parameter "rand" is unused. Remove?
	public FinalCastleRoof48CrenellatedComponent(TFFeature feature, Random rand, int i, TFStructureComponentOld keep) {
		super(FinalCastlePieces.TFFCRo48Cr,feature, i);

		int height = 5;

		this.setCoordBaseMode(keep.getCoordBaseMode());
		this.boundingBox = new MutableBoundingBox(keep.getBoundingBox().minX - 2, keep.getBoundingBox().maxY - 1, keep.getBoundingBox().minZ - 2, keep.getBoundingBox().maxX + 2, keep.getBoundingBox().maxY + height - 1, keep.getBoundingBox().maxZ + 2);

	}

	@Override
	public void buildComponent(StructurePiece parent, List<StructurePiece> list, Random rand) {
		if (parent != null && parent instanceof TFStructureComponentOld) {
			this.deco = ((TFStructureComponentOld) parent).deco;
		}
	}

	@Override
	public boolean func_230383_a_(ISeedReader world, StructureManager manager, ChunkGenerator generator, Random randomIn, MutableBoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		// add second layer of floor
		final BlockState castleMagic = TFBlocks.castle_rune_brick_purple.get().getDefaultState();
		this.fillWithBlocks(world, sbb, 2, 2, 2, 50, 2, 50, castleMagic, castleMagic, false);

		// crenellations
		for (Rotation rotation : RotationUtil.ROTATIONS) {
			this.fillBlocksRotated(world, sbb, 3, 1, 1, 45, 3, 1, deco.blockState, rotation);

			for (int i = 10; i < 41; i += 5) {
				this.fillBlocksRotated(world, sbb, i, 1, 0, i + 2, 5, 2, deco.blockState, rotation);
				this.setBlockStateRotated(world, deco.blockState, i + 1, 0, 1, rotation, sbb);
			}
		}

		return true;
	}
}
