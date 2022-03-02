package twilightforest.world.components.structures.lichtower;

import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import twilightforest.world.registration.TFFeature;

public class LichTowerPieces {

//	public static final IStructurePieceType TFLT = TFFeature.registerPiece("TFLT", StructureStartLichTower::new);

	public static final StructurePieceType TFLTBea = TFFeature.registerPiece("TFLTBea", TowerBeardComponent::new);
	public static final StructurePieceType TFLTBA = TFFeature.registerPiece("TFLTBA", TowerBeardAttachedComponent::new);
	public static final StructurePieceType TFLTBri = TFFeature.registerPiece("TFLTBri", TowerBridgeComponent::new);
	public static final StructurePieceType TFLTMai = TFFeature.registerPiece("TFLTMai", TowerMainComponent::new);
	public static final StructurePieceType TFLTOut = TFFeature.registerPiece("TFLTOut", TowerOutbuildingComponent::new);
	public static final StructurePieceType TFLTRoo = TFFeature.registerPiece("TFLTRoo", TowerRoofComponent::new);
	public static final StructurePieceType TFLTRAS = TFFeature.registerPiece("TFLTRAS", TowerRoofAttachedSlabComponent::new);
	public static final StructurePieceType TFLTRF = TFFeature.registerPiece("TFLTRF", TowerRoofFenceComponent::new);
	public static final StructurePieceType TFLTRGF = TFFeature.registerPiece("TFLTRGF", TowerRoofGableForwardsComponent::new);
	public static final StructurePieceType TFLTRP = TFFeature.registerPiece("TFLTRP", TowerRoofPointyComponent::new);
	public static final StructurePieceType TFLTRPO = TFFeature.registerPiece("TFLTRPO", TowerRoofPointyOverhangComponent::new);
	public static final StructurePieceType TFLTRS = TFFeature.registerPiece("TFLTRS", TowerRoofSlabComponent::new);
	public static final StructurePieceType TFLTRSF = TFFeature.registerPiece("TFLTRSF", TowerRoofSlabForwardsComponent::new);
	public static final StructurePieceType TFLTRSt = TFFeature.registerPiece("TFLTRSt", TowerRoofStairsComponent::new);
	public static final StructurePieceType TFLTRStO = TFFeature.registerPiece("TFLTRStO", TowerRoofStairsOverhangComponent::new);
	public static final StructurePieceType TFLTWin = TFFeature.registerPiece("TFLTWin", TowerWingComponent::new);
}
