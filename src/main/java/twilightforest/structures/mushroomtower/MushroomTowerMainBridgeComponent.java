package twilightforest.structures.mushroomtower;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import twilightforest.TFFeature;

import java.util.List;
import java.util.Random;

public class MushroomTowerMainBridgeComponent extends MushroomTowerBridgeComponent {

	public MushroomTowerMainBridgeComponent(StructureManager manager, CompoundTag nbt) {
		super(MushroomTowerPieces.TFMTMB, nbt);
	}

	protected MushroomTowerMainBridgeComponent(TFFeature feature, int i, int x, int y, int z, int pHeight, Direction direction) {
		// bridge only 11 long
		super(MushroomTowerPieces.TFMTMB, feature, i, x, y, z, 11, pHeight, direction);
	}

	@Override
	public boolean makeTowerWing(List<StructurePiece> list, Random rand, int index, int x, int y, int z, int wingSize, int wingHeight, Rotation rotation) {

		// make a new size 15 main tower
		Direction direction = getStructureRelativeRotation(rotation);
		int[] dx = offsetTowerCoords(x, y, z, 15, direction);

		// adjust coordinates to fit an existing tower
		dx = adjustCoordinates(dx[0], dx[1], dx[2], 15, direction, list);

		MushroomTowerMainComponent wing = new MushroomTowerMainComponent(getFeatureType(), index, dx[0], dx[1], dx[2], 15, wingHeight, direction);

		list.add(wing);
		wing.addChildren(list.get(0), list, rand);
		addOpening(x, y, z, rotation);

		return true;
	}
}
