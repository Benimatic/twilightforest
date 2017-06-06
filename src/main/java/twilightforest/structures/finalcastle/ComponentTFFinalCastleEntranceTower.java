package twilightforest.structures.finalcastle;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import twilightforest.structures.StructureTFComponent;
import java.util.List;
import java.util.Random;

public class ComponentTFFinalCastleEntranceTower extends ComponentTFFinalCastleMazeTower13
{

	public ComponentTFFinalCastleEntranceTower() { }

	public ComponentTFFinalCastleEntranceTower(Random rand, int i, int x, int y, int z, int direction) {
		super(rand, i, x, y, z, 3, 2, 0, direction);
	}

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void buildComponent(StructureComponent parent, List list, Random rand) {
	    if (parent != null && parent instanceof StructureTFComponent) {
		    this.deco = ((StructureTFComponent)parent).deco;
	    }

	    // add foundation
	    ComponentTFFinalCastleFoundation13 foundation = new ComponentTFFinalCastleFoundation13(rand, 4, this);
	    list.add(foundation);
	    foundation.buildComponent(this, list, rand);

	    // add roof
	    StructureTFComponent roof = new ComponentTFFinalCastleRoof13Peaked(rand, 4, this);
	    list.add(roof);
	    roof.buildComponent(this, list, rand);

	    // how many floors until the bottom?
	    int missingFloors = (this.boundingBox.minY - 127) / 8;

	    // place half on the bottom
	    int bottomFloors = missingFloors / 2;
	    // how many are left for the middle?
	    int middleFloors = missingFloors - bottomFloors;

	    // what direction can we put the side tower in, if any?
	    int direction = 1;
	    int howFar = 20;
	    if (!this.buildSideTower(list, rand, middleFloors + 1, direction, howFar)) {
		    direction = 3;
	        if (!this.buildSideTower(list, rand, middleFloors + 1, direction, howFar)) {
		        direction = 0;
	            if (!this.buildSideTower(list, rand, middleFloors + 1, direction, howFar)) {
		            // side tower no worky
	            }
	        }
        }

	    // add bottom tower
	    EnumFacing brDirection = (direction + this.coordBaseMode) % 4;
		ComponentTFFinalCastleEntranceBottomTower eTower = new ComponentTFFinalCastleEntranceBottomTower(rand, this.getComponentType() + 1, this.boundingBox.minX + 6, this.boundingBox.minY - (middleFloors) * 8, this.boundingBox.minZ + 6, bottomFloors + 1, bottomFloors, (brDirection + 2) % 4);
		list.add(eTower);
		eTower.buildComponent(this, list, rand);

	    // add bridge to bottom
		BlockPos opening = this.getValidOpeningCC(rand, direction);
		opening.getY() -= middleFloors * 8;

		BlockPos bc = this.offsetTowerCCoords(opening.getX(), opening.getY(), opening.getZ(), 1, brDirection);
		ComponentTFFinalCastleBridge bridge = new ComponentTFFinalCastleBridge(this.getComponentType() + 1, bc.getX(), bc.getY(), bc.getZ(), howFar - 7, brDirection);
		list.add(bridge);
		bridge.buildComponent(this, list, rand);
	}

	private boolean buildSideTower(List list, Random rand, int middleFloors, int direction, int howFar) {
		BlockPos opening = this.getValidOpeningCC(rand, direction);

		direction += this.coordBaseMode;
		direction %= 4;

		// build towards
		BlockPos tc = this.offsetTowerCCoords(opening.getX(), opening.getY(), opening.getZ(), howFar, direction);

		//System.out.println("Our coord mode is " + this.getCoordBaseMode() + ", and direction is " + direction + ", so our door is going to be at " + opening + " and the new tower will appear at " + tc);

		ComponentTFFinalCastleEntranceSideTower eTower = new ComponentTFFinalCastleEntranceSideTower(rand, this.getComponentType() + 1, tc.getX(), tc.getY(), tc.getZ(), middleFloors, middleFloors - 1, direction);

		StructureBoundingBox largerBB = new StructureBoundingBox(eTower.getBoundingBox());

		largerBB.minX -= 6;
		largerBB.minZ -= 6;
		largerBB.maxX += 6;
		largerBB.maxZ += 6;

		StructureComponent intersect = StructureComponent.findIntersecting(list, largerBB);

		if (intersect == null) {
			list.add(eTower);
			eTower.buildComponent(this, list, rand);
			// add bridge
			BlockPos bc = this.offsetTowerCCoords(opening.getX(), opening.getY(), opening.getZ(), 1, direction);
			ComponentTFFinalCastleBridge bridge = new ComponentTFFinalCastleBridge(this.getComponentType() + 1, bc.getX(), bc.getY(), bc.getZ(), howFar - 7, direction);
			list.add(bridge);
			bridge.buildComponent(this, list, rand);

			// opening
		    addOpening(opening.getX(), opening.getY() + 1, opening.getZ(), direction);

			return true;
		} else {
			System.out.println("side entrance tower blocked");
			return false;
		}

	}

	/**
	 * Gets a random position in the specified direction that connects to a floor currently in the tower.
	 */
	@Override
	public BlockPos getValidOpeningCC(Random rand, int direction) {
		// for directions 0 or 2, the wall lies along the z axis
		if (direction == 0 || direction == 2) {
			int rx = direction == 0 ? 12 : 0;
			int rz = 6;
			int ry = 0;

			return new BlockPos(rx, ry, rz);
		}

		// for directions 1 or 3, the wall lies along the x axis
		if (direction == 1 || direction == 3) {
			int rx = 6;
			int rz = direction == 1 ? 12 : 0;
			int ry = 0;

			return new BlockPos(rx, ry, rz);
		}


		return new BlockPos(0, 0, 0);
	}


}
