package twilightforest.structures.darktower;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.structures.lichtower.TowerWingComponent;

import java.util.Random;

public class DarkTowerRoofAntennaComponent extends DarkTowerRoofComponent {

	public DarkTowerRoofAntennaComponent(TemplateManager manager, CompoundNBT nbt) {
		super(DarkTowerPieces.TFDTRA, nbt);
	}

	public DarkTowerRoofAntennaComponent(TFFeature feature, int i, TowerWingComponent wing) {
		super(DarkTowerPieces.TFDTRA, feature, i, wing);
	}

	/**
	 * Stick with a ball on top antenna
	 */
	@Override
	public boolean func_230383_a_(ISeedReader world, StructureManager manager, ChunkGenerator generator, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		super.func_230383_a_(world, manager, generator, rand, sbb, chunkPosIn, blockPos);

		// antenna
		for (int y = 1; y < 10; y++) {
			setBlockState(world, deco.accentState, size / 2, y, size / 2, sbb);
		}

		setBlockState(world, deco.accentState, size / 2 - 1, 1, size / 2, sbb);
		setBlockState(world, deco.accentState, size / 2 + 1, 1, size / 2, sbb);
		setBlockState(world, deco.accentState, size / 2, 1, size / 2 - 1, sbb);
		setBlockState(world, deco.accentState, size / 2, 1, size / 2 + 1, sbb);

		for (int y = 7; y < 10; y++) {
			setBlockState(world, deco.accentState, size / 2 - 1, y, size / 2, sbb);
			setBlockState(world, deco.accentState, size / 2 + 1, y, size / 2, sbb);
			setBlockState(world, deco.accentState, size / 2, y, size / 2 - 1, sbb);
			setBlockState(world, deco.accentState, size / 2, y, size / 2 + 1, sbb);
		}

		setBlockState(world, deco.accentState, size / 2 - 1, 8, size / 2 - 1, sbb);
		setBlockState(world, deco.accentState, size / 2 - 1, 8, size / 2 + 1, sbb);
		setBlockState(world, deco.accentState, size / 2 + 1, 8, size / 2 - 1, sbb);
		setBlockState(world, deco.accentState, size / 2 + 1, 8, size / 2 + 1, sbb);

		return true;
	}
}
