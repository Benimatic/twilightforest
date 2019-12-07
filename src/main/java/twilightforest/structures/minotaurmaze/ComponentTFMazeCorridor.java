package twilightforest.structures.minotaurmaze;

import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import twilightforest.TFFeature;
import twilightforest.structures.StructureTFComponentOld;

import java.util.Random;

public class ComponentTFMazeCorridor extends StructureTFComponentOld {

	public ComponentTFMazeCorridor() {
		super();
	}

	public ComponentTFMazeCorridor(TFFeature feature, int i, int x, int y, int z, Direction rotation) {
		super(feature, i);
		this.setCoordBaseMode(rotation);
		this.boundingBox = new StructureBoundingBox(x, y, z, x + 5, y + 5, z + 5);
	}

	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {

		//arch
		this.fillWithBlocks(world, sbb, 1, 1, 2, 4, 4, 3, Blocks.OAK_FENCE.getDefaultState(), AIR, false);
		this.fillWithAir(world, sbb, 2, 1, 2, 3, 3, 3);

		return true;
	}

}
