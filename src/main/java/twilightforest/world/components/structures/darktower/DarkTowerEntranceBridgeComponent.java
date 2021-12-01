package twilightforest.world.components.structures.darktower;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import twilightforest.world.registration.TFFeature;

import java.util.Random;

public class DarkTowerEntranceBridgeComponent extends DarkTowerBridgeComponent {

	public DarkTowerEntranceBridgeComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
		super(DarkTowerPieces.TFDTEB, nbt);
	}

	protected DarkTowerEntranceBridgeComponent(TFFeature feature, int i, int x, int y, int z, int pSize, int pHeight, Direction direction) {
		super(DarkTowerPieces.TFDTEB, feature, i, x, y, z, pSize, pHeight, direction);
	}

	@Override
	public boolean makeTowerWing(StructurePieceAccessor list, Random rand, int index, int x, int y, int z, int wingSize, int wingHeight, Rotation rotation) {
		// make an entrance tower
		Direction direction = getStructureRelativeRotation(rotation);
		int[] dx = offsetTowerCoords(x, y, z, wingSize, direction);

		DarkTowerWingComponent wing = new DarkTowerEntranceComponent(getFeatureType(), index, dx[0], dx[1], dx[2], wingSize, wingHeight, direction);

		list.addPiece(wing);
		wing.addChildren(this, list, rand);
		addOpening(x, y, z, rotation);
		return true;
	}
}
