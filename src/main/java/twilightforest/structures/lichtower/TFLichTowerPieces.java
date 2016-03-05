package twilightforest.structures.lichtower;

import net.minecraft.world.gen.structure.MapGenStructureIO;

public class TFLichTowerPieces {

    public static void registerPieces()
    {
        MapGenStructureIO.func_143031_a(ComponentTFTowerBeard.class, "TFLTBea");
        MapGenStructureIO.func_143031_a(ComponentTFTowerBeardAttached.class, "TFLTBA");
        MapGenStructureIO.func_143031_a(ComponentTFTowerBridge.class, "TFLTBri");
        MapGenStructureIO.func_143031_a(ComponentTFTowerMain.class, "TFLTMai");
        MapGenStructureIO.func_143031_a(ComponentTFTowerOutbuilding.class, "TFLTOut");
        MapGenStructureIO.func_143031_a(ComponentTFTowerRoof.class, "TFLTRoo");
        MapGenStructureIO.func_143031_a(ComponentTFTowerRoofAttachedSlab.class, "TFLTRAS");
        MapGenStructureIO.func_143031_a(ComponentTFTowerRoofFence.class, "TFLTRF");
        MapGenStructureIO.func_143031_a(ComponentTFTowerRoofGableForwards.class, "TFLTRGF");
        MapGenStructureIO.func_143031_a(ComponentTFTowerRoofPointy.class, "TFLTRP");
        MapGenStructureIO.func_143031_a(ComponentTFTowerRoofPointyOverhang.class, "TFLTRPO");
        MapGenStructureIO.func_143031_a(ComponentTFTowerRoofSlab.class, "TFLTRS");
        MapGenStructureIO.func_143031_a(ComponentTFTowerRoofSlabForwards.class, "TFLTRSF");
        MapGenStructureIO.func_143031_a(ComponentTFTowerRoofStairs.class, "TFLTRSt");
        MapGenStructureIO.func_143031_a(ComponentTFTowerRoofStairsOverhang.class, "TFLTRStO");
        MapGenStructureIO.func_143031_a(ComponentTFTowerWing.class, "TFLTWin");
        
    }
}
