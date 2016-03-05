package twilightforest.structures.trollcave;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import twilightforest.block.TFBlocks;
import twilightforest.structures.StructureTFComponent;


public class ComponentTFCloudTree extends StructureTFComponent {
	
	public ComponentTFCloudTree() {}


	public ComponentTFCloudTree(int index, int x, int y, int z) {
		this.setCoordBaseMode(0);
		
		// adjust x, y, z
    	x = (x >> 2) << 2;
    	y = (y >> 2) << 2;
    	z = (z >> 2) << 2;
    	
		this.boundingBox = StructureTFComponent.getComponentToAddBoundingBox(x, y, z, -8, 0, -8, 20, 28, 20, 0);

		// spawn list!
		this.spawnListIndex = 1;
	}

	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		
		// leaves
		this.fillWithMetadataBlocks(world, sbb, 0, 12, 0, 19, 19, 19, TFBlocks.giantLeaves, 0,TFBlocks.giantLeaves, 0, false); 
		this.fillWithMetadataBlocks(world, sbb, 4, 20, 4, 15, 23, 15, TFBlocks.giantLeaves, 0,TFBlocks.giantLeaves, 0, false); 
		this.fillWithMetadataBlocks(world, sbb, 8, 24, 4, 11, 27, 15, TFBlocks.giantLeaves, 0,TFBlocks.giantLeaves, 0, false); 
		this.fillWithMetadataBlocks(world, sbb, 4, 24, 8, 15, 27, 11, TFBlocks.giantLeaves, 0,TFBlocks.giantLeaves, 0, false); 
		
		// trunk
		this.fillWithMetadataBlocks(world, sbb, 8, 0, 8, 11, 23, 11, TFBlocks.giantLog, 0,TFBlocks.giantLog, 0, false);
		
		// cloud base
		this.fillWithMetadataBlocks(world, sbb, 8, -4, 8, 11, -1, 11, TFBlocks.fluffyCloud, 0,TFBlocks.fluffyCloud, 0, false); 

		return true;
	}

}
