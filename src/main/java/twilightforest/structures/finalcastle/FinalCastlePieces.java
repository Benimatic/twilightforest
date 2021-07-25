package twilightforest.structures.finalcastle;

import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import twilightforest.TFFeature;

public class FinalCastlePieces {
//	public static final IStructurePieceType TFFC = TFFeature.registerPiece("TFFC", StructureStartFinalCastle::new);

	public static final StructurePieceType TFFCMain = TFFeature.registerPiece("TFFCMain", FinalCastleMainComponent::new);
	public static final StructurePieceType TFFCStTo = TFFeature.registerPiece("TFFCStTo", FinalCastleStairTowerComponent::new);
	public static final StructurePieceType TFFCLaTo = TFFeature.registerPiece("TFFCLaTo", FinalCastleLargeTowerComponent::new);
	public static final StructurePieceType TFFCMur = TFFeature.registerPiece("TFFCMur", FinalCastleMuralComponent::new);
	public static final StructurePieceType TFFCToF48 = TFFeature.registerPiece("TFFCToF48", FinalCastleFoundation48Component::new);
	public static final StructurePieceType TFFCRo48Cr = TFFeature.registerPiece("TFFCRo48Cr", FinalCastleRoof48CrenellatedComponent::new);
	public static final StructurePieceType TFFCBoGaz = TFFeature.registerPiece("TFFCBoGaz", FinalCastleBossGazeboComponent::new);
	public static final StructurePieceType TFFCSiTo = TFFeature.registerPiece("TFFCSiTo", FinalCastleMazeTower13Component::new);
	public static final StructurePieceType TFFCDunSt = TFFeature.registerPiece("TFFCDunSt", FinalCastleDungeonStepsComponent::new);
	public static final StructurePieceType TFFCDunEn = TFFeature.registerPiece("TFFCDunEn", FinalCastleDungeonEntranceComponent::new);
	public static final StructurePieceType TFFCDunR31 = TFFeature.registerPiece("TFFCDunR31", FinalCastleDungeonRoom31Component::new);
	public static final StructurePieceType TFFCDunEx = TFFeature.registerPiece("TFFCDunEx", FinalCastleDungeonExitComponent::new);
	public static final StructurePieceType TFFCDunBoR = TFFeature.registerPiece("TFFCDunBoR", FinalCastleDungeonForgeRoomComponent::new);
	public static final StructurePieceType TFFCRo9Cr = TFFeature.registerPiece("TFFCRo9Cr", FinalCastleRoof9CrenellatedComponent::new);
	public static final StructurePieceType TFFCRo13Cr = TFFeature.registerPiece("TFFCRo13Cr", FinalCastleRoof13CrenellatedComponent::new);
	public static final StructurePieceType TFFCRo13Con = TFFeature.registerPiece("TFFCRo13Con", FinalCastleRoof13ConicalComponent::new);
	public static final StructurePieceType TFFCRo13Pk = TFFeature.registerPiece("TFFCRo13Pk", FinalCastleRoof13PeakedComponent::new);
	public static final StructurePieceType TFFCEnTo = TFFeature.registerPiece("TFFCEnTo", FinalCastleEntranceTowerComponent::new);
	public static final StructurePieceType TFFCEnSiTo = TFFeature.registerPiece("TFFCEnSiTo", FinalCastleEntranceSideTowerComponent::new);
	public static final StructurePieceType TFFCEnBoTo = TFFeature.registerPiece("TFFCEnBoTo", FinalCastleEntranceBottomTowerComponent::new);
	public static final StructurePieceType TFFCEnSt = TFFeature.registerPiece("TFFCEnSt", FinalCastleEntranceStairsComponent::new);
	public static final StructurePieceType TFFCBelTo = TFFeature.registerPiece("TFFCBelTo", FinalCastleBellTower21Component::new);
	public static final StructurePieceType TFFCBri = TFFeature.registerPiece("TFFCBri", FinalCastleBridgeComponent::new);
	public static final StructurePieceType TFFCToF13 = TFFeature.registerPiece("TFFCToF13", FinalCastleFoundation13Component::new);
	public static final StructurePieceType TFFCBeF21 = TFFeature.registerPiece("TFFCBeF21", FinalCastleBellFoundation21Component::new);
	public static final StructurePieceType TFFCFTh21 = TFFeature.registerPiece("TFFCFTh21", FinalCastleFoundation13ComponentThorns::new);
	public static final StructurePieceType TFFCDamT = TFFeature.registerPiece("TFFCDamT", FinalCastleDamagedTowerComponent::new);
	public static final StructurePieceType TFFCWrT = TFFeature.registerPiece("TFFCWrT", FinalCastleWreckedTowerComponent::new);
}
