package twilightforest.structures.icetower;

import net.minecraft.world.gen.feature.StructureIO;
import twilightforest.structures.start.StructureStartAuroraPalace;


public class TFIceTowerPieces {

	public static void registerPieces() {
		StructureIO.registerStructure(StructureStartAuroraPalace.class, "TFAP");

		StructureIO.registerStructureComponent(ComponentTFIceTowerMain.class, "TFITMai");
		StructureIO.registerStructureComponent(ComponentTFIceTowerWing.class, "TFITWin");
		StructureIO.registerStructureComponent(ComponentTFIceTowerRoof.class, "TFITRoof");
		StructureIO.registerStructureComponent(ComponentTFIceTowerBeard.class, "TFITBea");
		StructureIO.registerStructureComponent(ComponentTFIceTowerBossWing.class, "TFITBoss");
		StructureIO.registerStructureComponent(ComponentTFIceTowerEntrance.class, "TFITEnt");
		StructureIO.registerStructureComponent(ComponentTFIceTowerBridge.class, "TFITBri");
		StructureIO.registerStructureComponent(ComponentTFIceTowerStairs.class, "TFITSt");
	}
}
