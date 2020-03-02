package twilightforest.structures.trollcave;

import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import twilightforest.TFFeature;
import twilightforest.structures.start.StructureStartTrollCave;

public class TFTrollCavePieces {

	public static final IStructurePieceType TFTC = TFFeature.registerPiece("TFTC", StructureStartTrollCave::new);

	public static final IStructurePieceType TFTCMai = TFFeature.registerPiece("TFTCMai", ComponentTFTrollCaveMain::new);
	public static final IStructurePieceType TFTCCon = TFFeature.registerPiece("TFTCCon", ComponentTFTrollCaveConnect::new);
	public static final IStructurePieceType TFTCGard = TFFeature.registerPiece("TFTCGard", ComponentTFTrollCaveGarden::new);
	public static final IStructurePieceType TFTCloud = TFFeature.registerPiece("TFTCloud", ComponentTFTrollCloud::new);
	public static final IStructurePieceType TFClCa = TFFeature.registerPiece("TFClCa", ComponentTFCloudCastle::new);
	public static final IStructurePieceType TFClTr = TFFeature.registerPiece("TFClTr", ComponentTFCloudTree::new);
	public static final IStructurePieceType TFTCVa = TFFeature.registerPiece("TFTCVa", ComponentTFTrollVault::new);
}