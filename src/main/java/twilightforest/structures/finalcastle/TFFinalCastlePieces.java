package twilightforest.structures.finalcastle;

import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import twilightforest.TFFeature;

public class TFFinalCastlePieces {
//	public static final IStructurePieceType TFFC = TFFeature.registerPiece("TFFC", StructureStartFinalCastle::new);

	public static final IStructurePieceType TFFCMain = TFFeature.registerPiece("TFFCMain", ComponentTFFinalCastleMain::new);
	public static final IStructurePieceType TFFCStTo = TFFeature.registerPiece("TFFCStTo", ComponentTFFinalCastleStairTower::new);
	public static final IStructurePieceType TFFCLaTo = TFFeature.registerPiece("TFFCLaTo", ComponentTFFinalCastleLargeTower::new);
	public static final IStructurePieceType TFFCMur = TFFeature.registerPiece("TFFCMur", ComponentTFFinalCastleMural::new);
	public static final IStructurePieceType TFFCToF48 = TFFeature.registerPiece("TFFCToF48", ComponentTFFinalCastleFoundation48::new);
	public static final IStructurePieceType TFFCRo48Cr = TFFeature.registerPiece("TFFCRo48Cr", ComponentTFFinalCastleRoof48Crenellated::new);
	public static final IStructurePieceType TFFCBoGaz = TFFeature.registerPiece("TFFCBoGaz", ComponentTFFinalCastleBossGazebo::new);
	public static final IStructurePieceType TFFCSiTo = TFFeature.registerPiece("TFFCSiTo", ComponentTFFinalCastleMazeTower13::new);
	public static final IStructurePieceType TFFCDunSt = TFFeature.registerPiece("TFFCDunSt", ComponentTFFinalCastleDungeonSteps::new);
	public static final IStructurePieceType TFFCDunEn = TFFeature.registerPiece("TFFCDunEn", ComponentTFFinalCastleDungeonEntrance::new);
	public static final IStructurePieceType TFFCDunR31 = TFFeature.registerPiece("TFFCDunR31", ComponentTFFinalCastleDungeonRoom31::new);
	public static final IStructurePieceType TFFCDunEx = TFFeature.registerPiece("TFFCDunEx", ComponentTFFinalCastleDungeonExit::new);
	public static final IStructurePieceType TFFCDunBoR = TFFeature.registerPiece("TFFCDunBoR", ComponentTFFinalCastleDungeonForgeRoom::new);
	public static final IStructurePieceType TFFCRo9Cr = TFFeature.registerPiece("TFFCRo9Cr", ComponentTFFinalCastleRoof9Crenellated::new);
	public static final IStructurePieceType TFFCRo13Cr = TFFeature.registerPiece("TFFCRo13Cr", ComponentTFFinalCastleRoof13Crenellated::new);
	public static final IStructurePieceType TFFCRo13Con = TFFeature.registerPiece("TFFCRo13Con", ComponentTFFinalCastleRoof13Conical::new);
	public static final IStructurePieceType TFFCRo13Pk = TFFeature.registerPiece("TFFCRo13Pk", ComponentTFFinalCastleRoof13Peaked::new);
	public static final IStructurePieceType TFFCEnTo = TFFeature.registerPiece("TFFCEnTo", ComponentTFFinalCastleEntranceTower::new);
	public static final IStructurePieceType TFFCEnSiTo = TFFeature.registerPiece("TFFCEnSiTo", ComponentTFFinalCastleEntranceSideTower::new);
	public static final IStructurePieceType TFFCEnBoTo = TFFeature.registerPiece("TFFCEnBoTo", ComponentTFFinalCastleEntranceBottomTower::new);
	public static final IStructurePieceType TFFCEnSt = TFFeature.registerPiece("TFFCEnSt", ComponentTFFinalCastleEntranceStairs::new);
	public static final IStructurePieceType TFFCBelTo = TFFeature.registerPiece("TFFCBelTo", ComponentTFFinalCastleBellTower21::new);
	public static final IStructurePieceType TFFCBri = TFFeature.registerPiece("TFFCBri", ComponentTFFinalCastleBridge::new);
	public static final IStructurePieceType TFFCToF13 = TFFeature.registerPiece("TFFCToF13", ComponentTFFinalCastleFoundation13::new);
	public static final IStructurePieceType TFFCBeF21 = TFFeature.registerPiece("TFFCBeF21", ComponentTFFinalCastleBellFoundation21::new);
	public static final IStructurePieceType TFFCFTh21 = TFFeature.registerPiece("TFFCFTh21", ComponentTFFinalCastleFoundation13Thorns::new);
	public static final IStructurePieceType TFFCDamT = TFFeature.registerPiece("TFFCDamT", ComponentTFFinalCastleDamagedTower::new);
	public static final IStructurePieceType TFFCWrT = TFFeature.registerPiece("TFFCWrT", ComponentTFFinalCastleWreckedTower::new);
}
