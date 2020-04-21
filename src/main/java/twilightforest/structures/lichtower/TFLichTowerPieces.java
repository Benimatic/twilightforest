package twilightforest.structures.lichtower;

import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import twilightforest.TFFeature;

public class TFLichTowerPieces {

//	public static final IStructurePieceType TFLT = TFFeature.registerPiece("TFLT", StructureStartLichTower::new);

	public static final IStructurePieceType TFLTBea = TFFeature.registerPiece("TFLTBea", ComponentTFTowerBeard::new);
	public static final IStructurePieceType TFLTBA = TFFeature.registerPiece("TFLTBA", ComponentTFTowerBeardAttached::new);
	public static final IStructurePieceType TFLTBri = TFFeature.registerPiece("TFLTBri", ComponentTFTowerBridge::new);
	public static final IStructurePieceType TFLTMai = TFFeature.registerPiece("TFLTMai", ComponentTFTowerMain::new);
	public static final IStructurePieceType TFLTOut = TFFeature.registerPiece("TFLTOut", ComponentTFTowerOutbuilding::new);
	public static final IStructurePieceType TFLTRoo = TFFeature.registerPiece("TFLTRoo", ComponentTFTowerRoof::new);
	public static final IStructurePieceType TFLTRAS = TFFeature.registerPiece("TFLTRAS", ComponentTFTowerRoofAttachedSlab::new);
	public static final IStructurePieceType TFLTRF = TFFeature.registerPiece("TFLTRF", ComponentTFTowerRoofFence::new);
	public static final IStructurePieceType TFLTRGF = TFFeature.registerPiece("TFLTRGF", ComponentTFTowerRoofGableForwards::new);
	public static final IStructurePieceType TFLTRP = TFFeature.registerPiece("TFLTRP", ComponentTFTowerRoofPointy::new);
	public static final IStructurePieceType TFLTRPO = TFFeature.registerPiece("TFLTRPO", ComponentTFTowerRoofPointyOverhang::new);
	public static final IStructurePieceType TFLTRS = TFFeature.registerPiece("TFLTRS", ComponentTFTowerRoofSlab::new);
	public static final IStructurePieceType TFLTRSF = TFFeature.registerPiece("TFLTRSF", ComponentTFTowerRoofSlabForwards::new);
	public static final IStructurePieceType TFLTRSt = TFFeature.registerPiece("TFLTRSt", ComponentTFTowerRoofStairs::new);
	public static final IStructurePieceType TFLTRStO = TFFeature.registerPiece("TFLTRStO", ComponentTFTowerRoofStairsOverhang::new);
	public static final IStructurePieceType TFLTWin = TFFeature.registerPiece("TFLTWin", ComponentTFTowerWing::new);
}
