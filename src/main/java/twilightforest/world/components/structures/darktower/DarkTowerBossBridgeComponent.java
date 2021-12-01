package twilightforest.world.components.structures.darktower;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import twilightforest.world.registration.TFFeature;

import java.util.Random;

public class DarkTowerBossBridgeComponent extends DarkTowerBridgeComponent {

	public DarkTowerBossBridgeComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
		super(DarkTowerPieces.TFDTBB, nbt);
	}

	protected DarkTowerBossBridgeComponent(TFFeature feature, int i, int x, int y, int z, int pSize, int pHeight, Direction direction) {
		super(DarkTowerPieces.TFDTBB, feature, i, x, y, z, pSize, pHeight, direction);
	}

	@Override
	public boolean makeTowerWing(StructurePieceAccessor list, Random rand, int index, int x, int y, int z, int wingSize, int wingHeight, Rotation rotation) {
		// make another size 15 main tower

		Direction direction = getStructureRelativeRotation(rotation);
		int[] dx = offsetTowerCoords(x, y, z, wingSize, direction);

		DarkTowerBossTrapComponent wing = new DarkTowerBossTrapComponent(getFeatureType(), index, dx[0], dx[1], dx[2], wingSize, wingHeight, direction);
		list.addPiece(wing);
		wing.addChildren(this, list, rand);
		addOpening(x, y, z, rotation);
		return true;
	}
}
