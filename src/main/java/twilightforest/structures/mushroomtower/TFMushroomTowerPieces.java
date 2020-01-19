package twilightforest.structures.mushroomtower;

import net.minecraft.world.gen.feature.StructureIO;
import twilightforest.structures.start.StructureStartMushroomTower;

public class TFMushroomTowerPieces {

	public static void registerPieces() {
		StructureIO.registerStructure(StructureStartMushroomTower.class, "TFMT");

		StructureIO.registerStructureComponent(ComponentTFMushroomTowerMain.class, "TFMTMai");
		StructureIO.registerStructureComponent(ComponentTFMushroomTowerWing.class, "TFMTWin");
		StructureIO.registerStructureComponent(ComponentTFMushroomTowerBridge.class, "TFMTBri");
		StructureIO.registerStructureComponent(ComponentTFMushroomTowerMainBridge.class, "TFMTMB");
		StructureIO.registerStructureComponent(ComponentTFTowerRoofMushroom.class, "TFMTRoofMush");
	}

}
