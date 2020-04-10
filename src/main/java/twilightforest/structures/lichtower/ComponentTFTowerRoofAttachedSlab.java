package twilightforest.structures.lichtower;

import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;

import java.util.Random;

public class ComponentTFTowerRoofAttachedSlab extends ComponentTFTowerRoofSlab {

	public ComponentTFTowerRoofAttachedSlab(TemplateManager manager, CompoundNBT nbt) {
		super(TFLichTowerPieces.TFLTRAS, nbt);
	}

	public ComponentTFTowerRoofAttachedSlab(TFFeature feature, int i, ComponentTFTowerWing wing) {
		super(TFLichTowerPieces.TFLTRAS, feature, i, wing);
	}

	/**
	 * Makes a flat, pyramid-shaped roof that is connected to the parent tower
	 */
	@Override
	public boolean generate(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn) {
		return makeConnectedCap(world.getWorld(), Blocks.BIRCH_SLAB.getDefaultState(), Blocks.BIRCH_PLANKS.getDefaultState(), sbb);
	}
}
