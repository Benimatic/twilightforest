package twilightforest.structures.finalcastle;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.structure.StructureComponent;
import twilightforest.structures.StructureTFComponent;
import java.util.List;
import java.util.Random;

public class ComponentTFFinalCastleEntranceBottomTower extends ComponentTFFinalCastleMazeTower13
{
	public ComponentTFFinalCastleEntranceBottomTower() {}

	public ComponentTFFinalCastleEntranceBottomTower(Random rand, int i, int x, int y, int z, int floors, int entranceFloor, int direction) {
		super(rand, i, x, y, z, floors, entranceFloor, 0, direction);

//    		addOpening(12, 1, size / 2, 0);
//    		addOpening(size / 2, 1, 0, 1);
//    		addOpening(0, 1, size / 2, 2);
//    		addOpening(size / 2, 1, 12, 3);
	}

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void buildComponent(StructureComponent parent, List list, Random rand) {
	    if (parent != null && parent instanceof StructureTFComponent) {
		    this.deco = ((StructureTFComponent)parent).deco;
	    }

	    // stairs
	    addStairs(list, rand, this.getComponentType() + 1, this.size - 1, 1, size / 2, 0);
	    addStairs(list, rand, this.getComponentType() + 1, 0, 1, size / 2, 2);
	    addStairs(list, rand, this.getComponentType() + 1, this.size / 2, 1, 0, 3);
	    addStairs(list, rand, this.getComponentType() + 1, this.size / 2, 1, this.size - 1, 1);

    }


    /**
     * Add some stairs leading to this tower
     */
    private boolean addStairs(List<StructureComponent> list, Random rand, int index, int x, int y, int z, int rotation) {
	    // add door
	    this.addOpening(x, y, z, rotation);

	    EnumFacing direction = getStructureRelativeRotation(rotation);
	    BlockPos dx = offsetTowerCCoords(x, y, z, 0, direction);

	    ComponentTFFinalCastleEntranceStairs stairs = new ComponentTFFinalCastleEntranceStairs(index, dx.getX(), dx.getY(), dx.getZ(), direction);

	    list.add(stairs);
	    stairs.buildComponent(list.get(0), list, rand);
	    return true;
    }

	@Override
	protected boolean hasAccessibleRoof() {
		return false;
	}

}
