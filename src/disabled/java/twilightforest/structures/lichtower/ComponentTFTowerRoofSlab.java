package twilightforest.structures.lichtower;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;


public class ComponentTFTowerRoofSlab extends ComponentTFTowerRoof {

	public ComponentTFTowerRoofSlab() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ComponentTFTowerRoofSlab(int i, ComponentTFTowerWing wing) {
		super(i, wing);

		// same alignment
		this.setCoordBaseMode(wing.getCoordBaseMode());
		// same size
		this.size = wing.size; // assuming only square towers and roofs right now.
		this.height = size / 2;
		
		// just hang out at the very top of the tower
		makeCapBB(wing);
		
	}

	/**
	 * Makes a flat, pyramid-shaped roof
	 */
	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		int slabMeta = 2; 
		return makePyramidCap(world, slabMeta, sbb);
	}

	protected boolean makePyramidCap(World world, int slabMeta, StructureBoundingBox sbb) {
		for (int y = 0; y <= height; y++) {
			int min = 2 * y;
			int max = size - (2 * y) - 1;
			for (int x = min; x <= max; x++) {
				for (int z = min; z <= max; z++) {
					if (x == min || x == max || z == min || z == max) {
						setBlockState(world, Blocks.WOODEN_SLAB, slabMeta, x, y, z, sbb);
					}
					else
					{
						setBlockState(world, Blocks.PLANKS, slabMeta, x, y, z, sbb);
					}
				}
			}
		}        
        return true;
	}

	protected boolean makeConnectedCap(World world, int slabMeta, StructureBoundingBox sbb) {
		for (int y = 0; y < height; y++) {
			int min = 2 * y;
			int max = size - (2 * y) - 1;
			for (int x = 0; x <= max; x++) {
				for (int z = min; z <= max; z++) {
					if (x == max || z == min || z == max) {
						setBlockState(world, Blocks.WOODEN_SLAB, slabMeta, x, y, z, sbb);
					}
					else
					{
						setBlockState(world, Blocks.PLANKS, slabMeta, x, y, z, sbb);
					}
				}
			}
		}        
        return true;
	}

}
