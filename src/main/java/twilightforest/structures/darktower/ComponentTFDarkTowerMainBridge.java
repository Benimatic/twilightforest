package twilightforest.structures.darktower;

import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import twilightforest.TFFeature;

import java.util.List;
import java.util.Random;

public class ComponentTFDarkTowerMainBridge extends ComponentTFDarkTowerBridge {

	public ComponentTFDarkTowerMainBridge() {
	}

	protected ComponentTFDarkTowerMainBridge(TFFeature feature, int i, int x, int y, int z, int pSize, int pHeight, Direction direction) {
		super(feature, i, x, y, z, 15, pHeight, direction);
	}


	@Override
	public boolean makeTowerWing(List<StructurePiece> list, Random rand, int index, int x, int y, int z, int wingSize, int wingHeight, Rotation rotation) {
		// make another size 15 main tower
		Direction direction = getStructureRelativeRotation(rotation);
		int[] dx = offsetTowerCoords(x, y, z, 19, direction);

		ComponentTFDarkTowerMain wing = new ComponentTFDarkTowerMain(getFeatureType(), null, rand, index, dx[0], dx[1], dx[2], direction);

		list.add(wing);
		wing.buildComponent(this, list, rand);
		addOpening(x, y, z, rotation);
		return true;
	}
}
