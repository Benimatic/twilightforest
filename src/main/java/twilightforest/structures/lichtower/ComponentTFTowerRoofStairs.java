package twilightforest.structures.lichtower;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;


public class ComponentTFTowerRoofStairs extends ComponentTFTowerRoof {

	public ComponentTFTowerRoofStairs() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ComponentTFTowerRoofStairs(int i, ComponentTFTowerWing wing) {
		super(i, wing);
		
		// always facing = 0.  This roof cannot rotate, due to stair facing issues.
		this.setCoordBaseMode(0);

		this.size = wing.size; // assuming only square towers and roofs right now.
		this.height = size / 2;
		
		// just hang out at the very top of the tower
		makeCapBB(wing);

	}

	/**
	 * Makes a pyramid-shaped roof out of stairs
	 */
	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		for (int y = 0; y <= height; y++) {
			int min = y;
			int max = size - y - 1;
			for (int x = min; x <= max; x++) {
				for (int z = min; z <= max; z++) {
					if (x == min) {
						if (z == min || z == max) {
							placeBlockAtCurrentPosition(world, Blocks.WOODEN_SLAB, 2, x, y, z, sbb);
						}
						else
						{
							placeBlockAtCurrentPosition(world, Blocks.BIRCH_STAIRS, 0, x, y, z, sbb);
						}
					}
					else if (x == max) {
						if (z == min || z == max) {
							placeBlockAtCurrentPosition(world, Blocks.WOODEN_SLAB, 2, x, y, z, sbb);
						}
						else
						{
							placeBlockAtCurrentPosition(world, Blocks.BIRCH_STAIRS, 1, x, y, z, sbb);
						}
					}
					else if (z == max) {
						placeBlockAtCurrentPosition(world, Blocks.BIRCH_STAIRS, 3, x, y, z, sbb);
					}
					else if (z == min) {
						placeBlockAtCurrentPosition(world, Blocks.BIRCH_STAIRS, 2, x, y, z, sbb);
					}
					else {
						placeBlockAtCurrentPosition(world, Blocks.PLANKS, 2, x, y, z, sbb);
					}
				}
			}
		}        
        return true;
	}

}
