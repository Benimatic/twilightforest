package twilightforest.structures.hollowtree;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import twilightforest.block.TFBlocks;
import twilightforest.world.TFGenerator;

public class ComponentTFHollowTreeRoot extends ComponentTFHollowTreeMedBranch  {

	private int groundLevel = -1;


	public ComponentTFHollowTreeRoot() {
		super();
	}

	public ComponentTFHollowTreeRoot(int i, int sx, int sy, int sz, double length, double angle, double tilt, boolean leafy) {
		super(i, sx, sy, sz, length, angle, tilt, leafy);
		this.boundingBox = new StructureBoundingBox(Math.min(src.posX, dest.posX), Math.min(src.posY, dest.posY), Math.min(src.posZ, dest.posZ), Math.max(src.posX, dest.posX), Math.max(src.posY, dest.posY), Math.max(src.posZ, dest.posZ));
	}

	
	@Override
	public boolean addComponentParts(World world, Random random, StructureBoundingBox sbb) {
		
		// offset bounding box to average ground level
        if (this.groundLevel < 0)
        {
        	int rootHeight = this.boundingBox.maxY - this.boundingBox.minY;
        	
            this.groundLevel = this.getSampledDirtLevel(world, sbb);

            if (this.groundLevel < 0)
            {
                return true;
            }

            src.posY = this.groundLevel + 5;
            
            //System.out.println("Adjusting root bounding box to " + this.boundingBox.minY);
        }
		
		BlockPos rSrc = new BlockPos(src.posX - boundingBox.minX, src.posY - boundingBox.minY, src.posZ - boundingBox.minZ);
		BlockPos rDest = new BlockPos(dest.posX - boundingBox.minX, dest.posY - boundingBox.minY, dest.posZ - boundingBox.minZ);

		drawRootLine(world, sbb, rSrc.posX, rSrc.posY, rSrc.posZ, rDest.posX, rDest.posY, rDest.posZ, TFBlocks.root, 0);
		drawRootLine(world, sbb, rSrc.posX, rSrc.posY - 1, rSrc.posZ, rDest.posX, rDest.posY - 1, rDest.posZ, TFBlocks.root, 0);

		return true;
	}

	
	
	/**
	 * Draws a line
	 */
	protected void drawRootLine(World world, StructureBoundingBox sbb, int x1, int y1, int z1, int x2, int y2, int z2, Block blockValue, int metaValue) {
		BlockPos lineCoords[] = TFGenerator.getBresehnamArrayCoords(x1, y1, z1, x2, y2, z2);
		
		for (BlockPos coords : lineCoords)
		{
			Block block = this.getBlockAtCurrentPosition(world, coords.posX, coords.posY, coords.posZ, sbb);
			
			// three choices here
			if (!block.isNormalCube(world, coords.posX, coords.posY, coords.posZ) || (block != null && block.getMaterial() == Material.GRASS))
			{
				// air, other non-solid, or grass, make wood block
				this.placeBlockAtCurrentPosition(world, TFBlocks.log, 12, coords.posX, coords.posY, coords.posZ, sbb);
			}
			else if (block != null && block.getMaterial() == Material.WOOD)
			{
				// wood, do nothing

			}
			else
			{
				// solid, make root block
				this.placeBlockAtCurrentPosition(world, blockValue, metaValue, coords.posX, coords.posY, coords.posZ, sbb);
			}
		}
	}
}
