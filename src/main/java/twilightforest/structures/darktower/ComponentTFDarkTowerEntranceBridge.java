package twilightforest.structures.darktower;

import java.util.List;
import java.util.Random;

import net.minecraft.world.gen.structure.StructureComponent;

public class ComponentTFDarkTowerEntranceBridge extends ComponentTFDarkTowerBridge 
{

	public ComponentTFDarkTowerEntranceBridge() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected ComponentTFDarkTowerEntranceBridge(int i, int x, int y, int z, int pSize, int pHeight, int direction) {
		super(i, x, y, z, pSize, pHeight, direction);
	}

	@Override
	public boolean makeTowerWing(List<StructureComponent> list, Random rand, int index, int x, int y, int z, int wingSize, int wingHeight, int rotation) {
		// make an entrance tower
		int direction = (getCoordBaseMode() + rotation) % 4;
		int[] dx = offsetTowerCoords(x, y, z, wingSize, direction);

		ComponentTFDarkTowerWing wing = new ComponentTFDarkTowerEntrance(index, dx[0], dx[1], dx[2], wingSize, wingHeight, direction);

		list.add(wing);
		wing.buildComponent(this, list, rand);
		addOpening(x, y, z, rotation);
		return true;
	}
}
