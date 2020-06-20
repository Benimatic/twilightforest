package twilightforest.structures.lichtower;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;

public class ComponentTFTowerRoofPointyOverhang extends ComponentTFTowerRoofPointy {

	public ComponentTFTowerRoofPointyOverhang(TemplateManager manager, CompoundNBT nbt) {
		super(TFLichTowerPieces.TFLTRPO, nbt);
	}

	public ComponentTFTowerRoofPointyOverhang(TFFeature feature, int i, ComponentTFTowerWing wing) {
		super(TFLichTowerPieces.TFLTRPO, feature, i, wing);

		// same facing, but it doesn't matter
		this.setCoordBaseMode(wing.getCoordBaseMode());

		this.size = wing.size + 2; // assuming only square towers and roofs right now.
		this.height = size;

		// just hang out at the very top of the tower
		makeOverhangBB(wing);
	}
}
