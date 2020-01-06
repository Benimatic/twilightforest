package twilightforest.structures.lichtower;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.MutableBoundingBox;
import twilightforest.TFFeature;

import java.util.Random;

public class ComponentTFTowerRoofAttachedSlab extends ComponentTFTowerRoofSlab {

	public ComponentTFTowerRoofAttachedSlab() {
		super();
	}

	public ComponentTFTowerRoofAttachedSlab(TFFeature feature, int i, ComponentTFTowerWing wing) {
		super(feature, i, wing);
	}

	/**
	 * Makes a flat, pyramid-shaped roof that is connected to the parent tower
	 */
	@Override
	public boolean addComponentParts(IWorld world, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn) {
		return makeConnectedCap(world.getWorld(), Blocks.BIRCH_SLAB.getDefaultState(), Blocks.BIRCH_PLANKS.getDefaultState(), sbb);
	}
}
