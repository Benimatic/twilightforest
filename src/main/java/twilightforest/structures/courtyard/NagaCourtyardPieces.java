package twilightforest.structures.courtyard;

import net.minecraft.world.gen.feature.StructureIO;
import twilightforest.structures.start.StructureStartCourtyard;

public class NagaCourtyardPieces {
    public static void registerPieces() {
        StructureIO.registerStructure(StructureStartCourtyard.class, "TFNC");

        StructureIO.registerStructureComponent(ComponentNagaCourtyardMain.class, "TFNCMn");
        StructureIO.registerStructureComponent(ComponentNagaCourtyardHedgeCap.class, "TFNCCp");
        StructureIO.registerStructureComponent(ComponentNagaCourtyardHedgeCapPillar.class, "TFNCCpP");
        StructureIO.registerStructureComponent(ComponentNagaCourtyardHedgeCorner.class, "TFNCCr");
        StructureIO.registerStructureComponent(ComponentNagaCourtyardHedgeLine.class, "TFNCLn");
        StructureIO.registerStructureComponent(ComponentNagaCourtyardHedgeTJunction.class, "TFNCT");
        StructureIO.registerStructureComponent(ComponentNagaCourtyardHedgeIntersection.class, "TFNCIs");
        StructureIO.registerStructureComponent(ComponentNagaCourtyardHedgePadder.class, "TFNCPd");
        StructureIO.registerStructureComponent(ComponentNagaCourtyardTerraceBrazier.class, "TFNCTr");
        StructureIO.registerStructureComponent(ComponentNagaCourtyardTerraceDuct.class, "TFNCDu");
        StructureIO.registerStructureComponent(ComponentNagaCourtyardTerraceStatue.class, "TFNCSt");
        StructureIO.registerStructureComponent(ComponentNagaCourtyardPath.class, "TFNCPa");
        StructureIO.registerStructureComponent(ComponentNagaCourtyardWall.class, "TFNCWl");
        StructureIO.registerStructureComponent(ComponentNagaCourtyardWallPadder.class, "TFNCWP");
        StructureIO.registerStructureComponent(ComponentNagaCourtyardWallCorner.class, "TFNCWC");
        StructureIO.registerStructureComponent(ComponentNagaCourtyardWallCornerAlt.class, "TFNCWA");
    }
}
