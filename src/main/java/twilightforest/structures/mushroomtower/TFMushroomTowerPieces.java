package twilightforest.structures.mushroomtower;

import net.minecraft.world.gen.structure.MapGenStructureIO;

public class TFMushroomTowerPieces {

	public static void registerPieces() {
        MapGenStructureIO.func_143031_a(ComponentTFMushroomTowerMain.class, "TFMTMai");
        MapGenStructureIO.func_143031_a(ComponentTFMushroomTowerWing.class, "TFMTWin");
        MapGenStructureIO.func_143031_a(ComponentTFMushroomTowerBridge.class, "TFMTBri");
        MapGenStructureIO.func_143031_a(ComponentTFMushroomTowerMainBridge.class, "TFMTMB");
        MapGenStructureIO.func_143031_a(ComponentTFTowerRoofMushroom.class, "TFMTRoofMush");
	}

}
