package twilightforest.structures.darktower;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;

import java.util.List;
import java.util.Random;

public class ComponentTFDarkTowerEntranceBridge extends ComponentTFDarkTowerBridge {

	public ComponentTFDarkTowerEntranceBridge(TemplateManager manager, CompoundNBT nbt) {
		super(TFDarkTowerPieces.TFDTEB, nbt);
	}

	protected ComponentTFDarkTowerEntranceBridge(TFFeature feature, int i, int x, int y, int z, int pSize, int pHeight, Direction direction) {
		super(feature, i, x, y, z, pSize, pHeight, direction);
	}

	@Override
	public boolean makeTowerWing(List<StructurePiece> list, Random rand, int index, int x, int y, int z, int wingSize, int wingHeight, Rotation rotation) {
		// make an entrance tower
		Direction direction = getStructureRelativeRotation(rotation);
		int[] dx = offsetTowerCoords(x, y, z, wingSize, direction);

		ComponentTFDarkTowerWing wing = new ComponentTFDarkTowerEntrance(getFeatureType(), index, dx[0], dx[1], dx[2], wingSize, wingHeight, direction);

		list.add(wing);
		wing.buildComponent(this, list, rand);
		addOpening(x, y, z, rotation);
		return true;
	}
}
