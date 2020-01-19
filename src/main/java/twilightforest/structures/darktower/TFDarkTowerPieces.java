package twilightforest.structures.darktower;

import net.minecraft.world.gen.feature.StructureIO;
import twilightforest.structures.start.StructureStartDarkTower;


public class TFDarkTowerPieces {

	public static void registerPieces() {
		StructureIO.registerStructure(StructureStartDarkTower.class, "TFDT");

		StructureIO.registerStructureComponent(ComponentTFDarkTowerBalcony.class, "TFDTBal");
		StructureIO.registerStructureComponent(ComponentTFDarkTowerBeard.class, "TFDTBea");
		StructureIO.registerStructureComponent(ComponentTFDarkTowerBossBridge.class, "TFDTBB");
		StructureIO.registerStructureComponent(ComponentTFDarkTowerBossTrap.class, "TFDTBT");
		StructureIO.registerStructureComponent(ComponentTFDarkTowerBridge.class, "TFDTBri");
		StructureIO.registerStructureComponent(ComponentTFDarkTowerEntrance.class, "TFDTEnt");
		StructureIO.registerStructureComponent(ComponentTFDarkTowerEntranceBridge.class, "TFDTEB");
		StructureIO.registerStructureComponent(ComponentTFDarkTowerMain.class, "TFDTMai");
		StructureIO.registerStructureComponent(ComponentTFDarkTowerMainBridge.class, "TFDTMB");
		StructureIO.registerStructureComponent(ComponentTFDarkTowerRoof.class, "TFDTRooS");
		StructureIO.registerStructureComponent(ComponentTFDarkTowerRoofAntenna.class, "TFDTRA");
		StructureIO.registerStructureComponent(ComponentTFDarkTowerRoofCactus.class, "TFDTRC");
		StructureIO.registerStructureComponent(ComponentTFDarkTowerRoofFourPost.class, "TFDTRFP");
		StructureIO.registerStructureComponent(ComponentTFDarkTowerRoofRings.class, "TFDTRR");
		StructureIO.registerStructureComponent(ComponentTFDarkTowerWing.class, "TFDTWin");

	}

}