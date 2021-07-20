package twilightforest.structures.finalcastle;

import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import twilightforest.TFFeature;

public class FinalCastlePieces {
//	public static final IStructurePieceType TFFC = TFFeature.registerPiece("TFFC", StructureStartFinalCastle::new);

	public static final IStructurePieceType TFFCMain = TFFeature.registerPiece("TFFCMain", FinalCastleMainComponent::new);
	public static final IStructurePieceType TFFCStTo = TFFeature.registerPiece("TFFCStTo", FinalCastleStairTowerComponent::new);
	public static final IStructurePieceType TFFCLaTo = TFFeature.registerPiece("TFFCLaTo", FinalCastleLargeTowerComponent::new);
	public static final IStructurePieceType TFFCMur = TFFeature.registerPiece("TFFCMur", FinalCastleMuralComponent::new);
	public static final IStructurePieceType TFFCToF48 = TFFeature.registerPiece("TFFCToF48", FinalCastleFoundation48Component::new);
	public static final IStructurePieceType TFFCRo48Cr = TFFeature.registerPiece("TFFCRo48Cr", FinalCastleRoof48CrenellatedComponent::new);
	public static final IStructurePieceType TFFCBoGaz = TFFeature.registerPiece("TFFCBoGaz", FinalCastleBossGazeboComponent::new);
	public static final IStructurePieceType TFFCSiTo = TFFeature.registerPiece("TFFCSiTo", FinalCastleMazeTower13Component::new);
	public static final IStructurePieceType TFFCDunSt = TFFeature.registerPiece("TFFCDunSt", FinalCastleDungeonStepsComponent::new);
	public static final IStructurePieceType TFFCDunEn = TFFeature.registerPiece("TFFCDunEn", FinalCastleDungeonEntranceComponent::new);
	public static final IStructurePieceType TFFCDunR31 = TFFeature.registerPiece("TFFCDunR31", FinalCastleDungeonRoom31Component::new);
	public static final IStructurePieceType TFFCDunEx = TFFeature.registerPiece("TFFCDunEx", FinalCastleDungeonExitComponent::new);
	public static final IStructurePieceType TFFCDunBoR = TFFeature.registerPiece("TFFCDunBoR", FinalCastleDungeonForgeRoomComponent::new);
	public static final IStructurePieceType TFFCRo9Cr = TFFeature.registerPiece("TFFCRo9Cr", FinalCastleRoof9CrenellatedComponent::new);
	public static final IStructurePieceType TFFCRo13Cr = TFFeature.registerPiece("TFFCRo13Cr", FinalCastleRoof13CrenellatedComponent::new);
	public static final IStructurePieceType TFFCRo13Con = TFFeature.registerPiece("TFFCRo13Con", FinalCastleRoof13ConicalComponent::new);
	public static final IStructurePieceType TFFCRo13Pk = TFFeature.registerPiece("TFFCRo13Pk", FinalCastleRoof13PeakedComponent::new);
	public static final IStructurePieceType TFFCEnTo = TFFeature.registerPiece("TFFCEnTo", FinalCastleEntranceTowerComponent::new);
	public static final IStructurePieceType TFFCEnSiTo = TFFeature.registerPiece("TFFCEnSiTo", FinalCastleEntranceSideTowerComponent::new);
	public static final IStructurePieceType TFFCEnBoTo = TFFeature.registerPiece("TFFCEnBoTo", FinalCastleEntranceBottomTowerComponent::new);
	public static final IStructurePieceType TFFCEnSt = TFFeature.registerPiece("TFFCEnSt", FinalCastleEntranceStairsComponent::new);
	public static final IStructurePieceType TFFCBelTo = TFFeature.registerPiece("TFFCBelTo", FinalCastleBellTower21Component::new);
	public static final IStructurePieceType TFFCBri = TFFeature.registerPiece("TFFCBri", FinalCastleBridgeComponent::new);
	public static final IStructurePieceType TFFCToF13 = TFFeature.registerPiece("TFFCToF13", FinalCastleFoundation13Component::new);
	public static final IStructurePieceType TFFCBeF21 = TFFeature.registerPiece("TFFCBeF21", FinalCastleBellFoundation21Component::new);
	public static final IStructurePieceType TFFCFTh21 = TFFeature.registerPiece("TFFCFTh21", FinalCastleFoundation13ComponentThorns::new);
	public static final IStructurePieceType TFFCDamT = TFFeature.registerPiece("TFFCDamT", FinalCastleDamagedTowerComponent::new);
	public static final IStructurePieceType TFFCWrT = TFFeature.registerPiece("TFFCWrT", FinalCastleWreckedTowerComponent::new);
}
