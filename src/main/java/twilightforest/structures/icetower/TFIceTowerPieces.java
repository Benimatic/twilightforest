package twilightforest.structures.icetower;

import net.minecraft.world.gen.structure.MapGenStructureIO;

public class TFIceTowerPieces {

    public static void registerPieces()
    {
        MapGenStructureIO.func_143031_a(ComponentTFIceTowerMain.class, "TFITMai");
        MapGenStructureIO.func_143031_a(ComponentTFIceTowerWing.class, "TFITWin");
        MapGenStructureIO.func_143031_a(ComponentTFIceTowerRoof.class, "TFITRoof");
        MapGenStructureIO.func_143031_a(ComponentTFIceTowerBeard.class, "TFITBea");
        MapGenStructureIO.func_143031_a(ComponentTFIceTowerBossWing.class, "TFITBoss");
        MapGenStructureIO.func_143031_a(ComponentTFIceTowerEntrance.class, "TFITEnt");
        MapGenStructureIO.func_143031_a(ComponentTFIceTowerBridge.class, "TFITBri");
        MapGenStructureIO.func_143031_a(ComponentTFIceTowerStairs.class, "TFITSt");
    }
}
