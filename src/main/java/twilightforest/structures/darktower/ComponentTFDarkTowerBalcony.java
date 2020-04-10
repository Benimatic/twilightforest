package twilightforest.structures.darktower;

import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.structures.StructureTFComponentOld;
import twilightforest.structures.lichtower.ComponentTFTowerWing;

import java.util.List;
import java.util.Random;

public class ComponentTFDarkTowerBalcony extends ComponentTFTowerWing {

	public ComponentTFDarkTowerBalcony(TemplateManager manager, CompoundNBT nbt) {
		super(TFDarkTowerPieces.TFDTBal, nbt);
	}

	protected ComponentTFDarkTowerBalcony(TFFeature feature, int i, int x, int y, int z, Direction direction) {
		super(TFDarkTowerPieces.TFDTBal, feature, i, x, y, z, 5, 5, direction);
	}

	@Override
	public void buildComponent(StructurePiece parent, List<StructurePiece> list, Random rand) {
		if (parent != null && parent instanceof StructureTFComponentOld) {
			this.deco = ((StructureTFComponentOld) parent).deco;
		}
	}

	@Override
	public boolean generate(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn) {
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
