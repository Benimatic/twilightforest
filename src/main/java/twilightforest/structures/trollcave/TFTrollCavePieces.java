package twilightforest.structures.trollcave;

import net.minecraft.world.gen.feature.StructureIO;
import twilightforest.structures.start.StructureStartTrollCave;

public class TFTrollCavePieces {

	public static void registerPieces() {
		StructureIO.registerStructure(StructureStartTrollCave.class, "TFTC");

		StructureIO.registerStructureComponent(ComponentTFTrollCaveMain.class, "TFTCMai");
		StructureIO.registerStructureComponent(ComponentTFTrollCaveConnect.class, "TFTCCon");
		StructureIO.registerStructureComponent(ComponentTFTrollCaveGarden.class, "TFTCGard");
		StructureIO.registerStructureComponent(ComponentTFTrollCloud.class, "TFTCloud");
		StructureIO.registerStructureComponent(ComponentTFCloudCastle.class, "TFClCa");
		StructureIO.registerStructureComponent(ComponentTFCloudTree.class, "TFClTr");
		StructureIO.registerStructureComponent(ComponentTFTrollVault.class, "TFTCVa");
	}
}