package twilightforest.structures.trollcave;

import net.minecraft.world.gen.structure.MapGenStructureIO;

public class TFTrollCavePieces {

    public static void registerPieces()
    {
        MapGenStructureIO.func_143031_a(ComponentTFTrollCaveMain.class, "TFTCMai");
        MapGenStructureIO.func_143031_a(ComponentTFTrollCaveConnect.class, "TFTCCon");
        MapGenStructureIO.func_143031_a(ComponentTFTrollCaveGarden.class, "TFTCGard");
        MapGenStructureIO.func_143031_a(ComponentTFTrollCloud.class, "TFTCloud");
        MapGenStructureIO.func_143031_a(ComponentTFCloudCastle.class, "TFClCa");
        MapGenStructureIO.func_143031_a(ComponentTFCloudTree.class, "TFClTr");
        MapGenStructureIO.func_143031_a(ComponentTFTrollVault.class, "TFTCVa");
    }
}