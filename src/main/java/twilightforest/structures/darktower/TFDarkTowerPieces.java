package twilightforest.structures.darktower;

import net.minecraft.world.gen.structure.MapGenStructureIO;


public class TFDarkTowerPieces {

    public static void registerPieces()
    {
        MapGenStructureIO.func_143031_a(ComponentTFDarkTowerBalcony.class, "TFDTBal");
        MapGenStructureIO.func_143031_a(ComponentTFDarkTowerBeard.class, "TFDTBea");
        MapGenStructureIO.func_143031_a(ComponentTFDarkTowerBossBridge.class, "TFDTBB");
        MapGenStructureIO.func_143031_a(ComponentTFDarkTowerBossTrap.class, "TFDTBT");
        MapGenStructureIO.func_143031_a(ComponentTFDarkTowerBridge.class, "TFDTBri");
        MapGenStructureIO.func_143031_a(ComponentTFDarkTowerEntrance.class, "TFDTEnt");
        MapGenStructureIO.func_143031_a(ComponentTFDarkTowerEntranceBridge.class, "TFDTEB");
        MapGenStructureIO.func_143031_a(ComponentTFDarkTowerMain.class, "TFDTMai");
        MapGenStructureIO.func_143031_a(ComponentTFDarkTowerMainBridge.class, "TFDTMB");
        MapGenStructureIO.func_143031_a(ComponentTFDarkTowerRoof.class, "TFDTRooS");
        MapGenStructureIO.func_143031_a(ComponentTFDarkTowerRoofAntenna.class, "TFDTRA");
        MapGenStructureIO.func_143031_a(ComponentTFDarkTowerRoofCactus.class, "TFDTRC");
        MapGenStructureIO.func_143031_a(ComponentTFDarkTowerRoofFourPost.class, "TFDTRFP");
        MapGenStructureIO.func_143031_a(ComponentTFDarkTowerRoofRings.class, "TFDTRR");
        MapGenStructureIO.func_143031_a(ComponentTFDarkTowerWing.class, "TFDTWin");

    }
	
}