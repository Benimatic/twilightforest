package twilightforest.structures.darktower;

import net.minecraft.block.Blocks;
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
import twilightforest.structures.TFStructureComponentOld;
import twilightforest.structures.lichtower.TowerWingComponent;

import java.util.List;
import java.util.Random;

public class DarkTowerBalconyComponent extends TowerWingComponent {

	public DarkTowerBalconyComponent(TemplateManager manager, CompoundNBT nbt) {
		super(DarkTowerPieces.TFDTBal, nbt);
	}

	protected DarkTowerBalconyComponent(TFFeature feature, int i, int x, int y, int z, Direction direction) {
		super(DarkTowerPieces.TFDTBal, feature, i, x, y, z, 5, 5, direction);
	}

	@Override
	public void buildComponent(StructurePiece parent, List<StructurePiece> list, Random rand) {
		if (parent != null && parent instanceof TFStructureComponentOld) {
			this.deco = ((TFStructureComponentOld) parent).deco;
		}
	}

	@Override
	public boolean func_230383_a_(ISeedReader world, StructureManager manager, ChunkGenerator generator, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		// make floor
		fillWithBlocks(world, sbb, 0, 0, 0, 2, 0, 4, deco.accentState, Blocks.AIR.getDefaultState(), false);
		fillWithBlocks(world, sbb, 0, 0, 1, 1, 0, 3, deco.blockState, Blocks.AIR.getDefaultState(), false);

		fillWithBlocks(world, sbb, 0, 1, 0, 2, 1, 4, deco.fenceState, Blocks.AIR.getDefaultState(), false);

		this.setBlockState(world, deco.accentState, 2, 1, 0, sbb);
		this.setBlockState(world, deco.accentState, 2, 1, 4, sbb);

		// clear inside
		fillWithAir(world, sbb, 0, 1, 1, 1, 1, 3);

		return true;
	}
}
