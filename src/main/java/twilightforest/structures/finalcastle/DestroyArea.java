package twilightforest.structures.finalcastle;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

import java.util.ArrayList;
import java.util.Random;

/**
 * An area that we're going to destroy.  Default is just a MutableBoundingBox
 */
public class DestroyArea {

	BoundingBox destroyBox;

	public DestroyArea(BoundingBox tower, Random rand, int y) {
		// make a 4x4 area that's entirely within the tower bounding box

		int bx = tower.x0 - 2 + rand.nextInt(tower.getXSpan());
		int bz = tower.z0 - 2 + rand.nextInt(tower.getZSpan());

		this.destroyBox = new BoundingBox(bx, y - 10, bz, bx + 4, y, bz + 4);
	}

	public boolean isEntirelyAbove(int y) {
		return this.destroyBox.y0 > y;
	}

	public boolean isVecInside(BlockPos pos) {
		return destroyBox.isInside(pos);
	}

	/**
	 * construct a new area that does not intersect any other areas in the list
	 */
	public static DestroyArea createNonIntersecting(BoundingBox tower, Random rand, int y, ArrayList<DestroyArea> otherAreas) {
		int attempts = 100;

		DestroyArea area = null;
		for (int i = 0; i < attempts && area == null; i++) {
			DestroyArea testArea = new DestroyArea(tower, rand, y);

			if (otherAreas.size() == 0) {
				area = testArea;
			} else {
				for (DestroyArea otherArea : otherAreas) {
					if (otherArea == null || !testArea.intersectsWith(otherArea)) {
						area = testArea;
					}
				}
			}
		}
		return area;
	}

	/**
	 * We check if the box would intersect even if it was one block larger in the x and z directions
	 */
	private boolean intersectsWith(DestroyArea otherArea) {
		return this.destroyBox.intersects(otherArea.destroyBox.x0 - 1, otherArea.destroyBox.z0 - 1, otherArea.destroyBox.x1 + 1, otherArea.destroyBox.x1 + 1);
	}
}
