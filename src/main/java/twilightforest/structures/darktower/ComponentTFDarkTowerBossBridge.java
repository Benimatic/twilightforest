package twilightforest.structures.darktower;

import java.util.List;
import java.util.Random;

import net.minecraft.world.gen.structure.StructureComponent;

public class ComponentTFDarkTowerBossBridge extends ComponentTFDarkTowerBridge {

	public ComponentTFDarkTowerBossBridge() {
		super();
		// TODO Auto-generated constructor stub
	}


	protected ComponentTFDarkTowerBossBridge(int i, int x, int y, int z, int pSize, int pHeight, int direction) {
		super(i, x, y, z, pSize, pHeight, direction);
	}

	
	public boolean makeTowerWing(List<StructureComponent> list, Random rand, int index, int x, int y, int z, int wingSize, int wingHeight, int rotation) {
		// make another size 15 main tower
		int direction = (getCoordBaseMode() + rotation) % 4;
		int[] dx = offsetTowerCoords(x, y, z, wingSize, direction);

		ComponentTFDarkTowerBossTrap wing = new ComponentTFDarkTowerBossTrap(index, dx[0], dx[1], dx[2], wingSize, wingHeight, direction);
		list.add(wing);
		wing.buildComponent(this, list, rand);
		addOpening(x, y, z, rotation);
		return true;
	}
}
