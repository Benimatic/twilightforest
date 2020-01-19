package twilightforest.structures.lichtower;

import net.minecraft.world.gen.feature.StructureIO;
import twilightforest.structures.start.StructureStartLichTower;

public class TFLichTowerPieces {

	public static void registerPieces() {
		StructureIO.registerStructure(StructureStartLichTower.class, "TFLT");

		StructureIO.registerStructureComponent(ComponentTFTowerBeard.class, "TFLTBea");
		StructureIO.registerStructureComponent(ComponentTFTowerBeardAttached.class, "TFLTBA");
		StructureIO.registerStructureComponent(ComponentTFTowerBridge.class, "TFLTBri");
		StructureIO.registerStructureComponent(ComponentTFTowerMain.class, "TFLTMai");
		StructureIO.registerStructureComponent(ComponentTFTowerOutbuilding.class, "TFLTOut");
		StructureIO.registerStructureComponent(ComponentTFTowerRoof.class, "TFLTRoo");
		StructureIO.registerStructureComponent(ComponentTFTowerRoofAttachedSlab.class, "TFLTRAS");
		StructureIO.registerStructureComponent(ComponentTFTowerRoofFence.class, "TFLTRF");
		StructureIO.registerStructureComponent(ComponentTFTowerRoofGableForwards.class, "TFLTRGF");
		StructureIO.registerStructureComponent(ComponentTFTowerRoofPointy.class, "TFLTRP");
		StructureIO.registerStructureComponent(ComponentTFTowerRoofPointyOverhang.class, "TFLTRPO");
		StructureIO.registerStructureComponent(ComponentTFTowerRoofSlab.class, "TFLTRS");
		StructureIO.registerStructureComponent(ComponentTFTowerRoofSlabForwards.class, "TFLTRSF");
		StructureIO.registerStructureComponent(ComponentTFTowerRoofStairs.class, "TFLTRSt");
		StructureIO.registerStructureComponent(ComponentTFTowerRoofStairsOverhang.class, "TFLTRStO");
		StructureIO.registerStructureComponent(ComponentTFTowerWing.class, "TFLTWin");

	}
}
