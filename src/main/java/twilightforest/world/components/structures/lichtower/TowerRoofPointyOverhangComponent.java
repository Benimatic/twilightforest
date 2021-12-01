package twilightforest.world.components.structures.lichtower;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import twilightforest.world.registration.TFFeature;

public class TowerRoofPointyOverhangComponent extends TowerRoofPointyComponent {

	public TowerRoofPointyOverhangComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
		super(LichTowerPieces.TFLTRPO, nbt);
	}

	public TowerRoofPointyOverhangComponent(TFFeature feature, int i, TowerWingComponent wing, int x, int y, int z) {
		super(LichTowerPieces.TFLTRPO, feature, i, wing, x, y, z);

		// same facing, but it doesn't matter
		this.setOrientation(wing.getOrientation());

		this.size = wing.size + 2; // assuming only square towers and roofs right now.
		this.height = size;

		// just hang out at the very top of the tower
		makeOverhangBB(wing);
	}
}
