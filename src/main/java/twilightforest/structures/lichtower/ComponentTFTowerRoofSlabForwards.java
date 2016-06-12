package twilightforest.structures.lichtower;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;


/**
 * A flat tower roof using slabs that is larger than the tower under it.
 * 
 * @author Ben
 *
 */
public class ComponentTFTowerRoofSlabForwards extends ComponentTFTowerRoofSlab {

	public ComponentTFTowerRoofSlabForwards() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ComponentTFTowerRoofSlabForwards(int i, ComponentTFTowerWing wing) {
		super(i, wing);
		
		// same alignment
		this.setCoordBaseMode(wing.getCoordBaseMode());
		// the overhang roof is like a cap roof that's 2 sizes bigger
		this.size = wing.size + 2; // assuming only square towers and roofs right now.
		this.height = size / 2;
		
		// bounding box
		makeAttachedOverhangBB(wing);

	}

	/**
	 * Makes flat hip roof
	 */
	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		for (int y = 0; y <= height; y++) {
			int min = 2 * y;
			int max = size - (2 * y) - 1;
			for (int x = 0; x <= max - 1; x++) {
				for (int z = min; z <= max; z++) {
					if (x == max - 1 || z == min || z == max) {
						placeBlockAtCurrentPosition(world, Blocks.WOODEN_SLAB, 2, x, y, z, sbb);
					}
					else
					{
						placeBlockAtCurrentPosition(world, Blocks.DOUBLE_WOODEN_SLAB, 2, x, y, z, sbb);
					}
				}
			}
		}        
        return true;
	}
}
