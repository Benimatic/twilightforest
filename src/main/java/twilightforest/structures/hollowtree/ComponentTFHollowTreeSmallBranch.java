package twilightforest.structures.hollowtree;

import java.util.List;
import java.util.Random;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import twilightforest.block.TFBlocks;


public class ComponentTFHollowTreeSmallBranch extends
		ComponentTFHollowTreeMedBranch {

	public ComponentTFHollowTreeSmallBranch() {
		super();
	}

	protected ComponentTFHollowTreeSmallBranch(int i, int sx, int sy, int sz,
			double length, double angle, double tilt, boolean leafy) {
		super(i, sx, sy, sz, length, angle, tilt, leafy);
	}
	
	/**
	 * Add a leaf ball to the end
	 */
	@Override
	public void buildComponent(StructureComponent structurecomponent, List list, Random rand) {
//		int index = getComponentType();
//		
//		if (leafy) {
//			int leafRad = rand.nextInt(2) + 1;
//			ComponentTFLeafSphere leafBlob = new ComponentTFLeafSphere(index + 1, dest.posX, dest.posY, dest.posZ, leafRad);
//	        list.add(leafBlob);
//	        leafBlob.buildComponent(this, list, rand); // doesn't really need to be here for leaves.
//		}

	}
	
	@Override
	public boolean addComponentParts(World world, Random random, StructureBoundingBox sbb) {
		
		BlockPos rSrc = new BlockPos(src.posX - boundingBox.minX, src.posY - boundingBox.minY, src.posZ - boundingBox.minZ);
		BlockPos rDest = new BlockPos(dest.posX - boundingBox.minX, dest.posY - boundingBox.minY, dest.posZ - boundingBox.minZ);

		drawBresehnam(world, sbb, rSrc.posX, rSrc.posY, rSrc.posZ, rDest.posX, rDest.posY, rDest.posZ, TFBlocks.log, 12);
		
		// with leaves!
		if (leafy) {
			int leafRad = random.nextInt(2) + 1;
			makeLeafBlob(world, sbb, rDest.posX, rDest.posY, rDest.posZ, leafRad);		

		}


		return true;
	}


}
