package twilightforest.structures.lichtower;

import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import twilightforest.TFFeature;

public class LichTowerPieces {

//	public static final IStructurePieceType TFLT = TFFeature.registerPiece("TFLT", StructureStartLichTower::new);

	public static final IStructurePieceType TFLTBea = TFFeature.registerPiece("TFLTBea", TowerBeardComponent::new);
	public static final IStructurePieceType TFLTBA = TFFeature.registerPiece("TFLTBA", TowerBeardAttachedComponent::new);
	public static final IStructurePieceType TFLTBri = TFFeature.registerPiece("TFLTBri", TowerBridgeComponent::new);
	public static final IStructurePieceType TFLTMai = TFFeature.registerPiece("TFLTMai", TowerMainComponent::new);
	public static final IStructurePieceType TFLTOut = TFFeature.registerPiece("TFLTOut", TowerOutbuildingComponent::new);
	public static final IStructurePieceType TFLTRoo = TFFeature.registerPiece("TFLTRoo", TowerRoofComponent::new);
	public static final IStructurePieceType TFLTRAS = TFFeature.registerPiece("TFLTRAS", TowerRoofAttachedSlabComponent::new);
	public static final IStructurePieceType TFLTRF = TFFeature.registerPiece("TFLTRF", TowerRoofFenceComponent::new);
	public static final IStructurePieceType TFLTRGF = TFFeature.registerPiece("TFLTRGF", TowerRoofGableForwardsComponent::new);
	public static final IStructurePieceType TFLTRP = TFFeature.registerPiece("TFLTRP", TowerRoofPointyComponent::new);
	public static final IStructurePieceType TFLTRPO = TFFeature.registerPiece("TFLTRPO", TowerRoofPointyOverhangComponent::new);
	public static final IStructurePieceType TFLTRS = TFFeature.registerPiece("TFLTRS", TowerRoofSlabComponent::new);
	public static final IStructurePieceType TFLTRSF = TFFeature.registerPiece("TFLTRSF", TowerRoofSlabForwardsComponent::new);
	public static final IStructurePieceType TFLTRSt = TFFeature.registerPiece("TFLTRSt", TowerRoofStairsComponent::new);
	public static final IStructurePieceType TFLTRStO = TFFeature.registerPiece("TFLTRStO", TowerRoofStairsOverhangComponent::new);
	public static final IStructurePieceType TFLTWin = TFFeature.registerPiece("TFLTWin", TowerWingComponent::new);
}
