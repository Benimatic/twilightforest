package twilightforest.structures.icetower;

import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import twilightforest.TFFeature;

public class TFIceTowerPieces {

//	public static final IStructurePieceType TFAP = TFFeature.registerPiece("TFAP", StructureStartAuroraPalace::new);

	public static final IStructurePieceType TFITMai = TFFeature.registerPiece("TFITMai", ComponentTFIceTowerMain::new);
	public static final IStructurePieceType TFITWin = TFFeature.registerPiece("TFITWin", ComponentTFIceTowerWing::new);
	public static final IStructurePieceType TFITRoof = TFFeature.registerPiece("TFITRoof", ComponentTFIceTowerRoof::new);
	public static final IStructurePieceType TFITBea = TFFeature.registerPiece("TFITBea", ComponentTFIceTowerBeard::new);
	public static final IStructurePieceType TFITBoss = TFFeature.registerPiece("TFITBoss", ComponentTFIceTowerBossWing::new);
	public static final IStructurePieceType TFITEnt = TFFeature.registerPiece("TFITEnt", ComponentTFIceTowerEntrance::new);
	public static final IStructurePieceType TFITBri = TFFeature.registerPiece("TFITBri", ComponentTFIceTowerBridge::new);
	public static final IStructurePieceType TFITSt = TFFeature.registerPiece("TFITSt", ComponentTFIceTowerStairs::new);
}
