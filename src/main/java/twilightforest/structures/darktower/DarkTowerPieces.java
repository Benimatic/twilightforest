package twilightforest.structures.darktower;

import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import twilightforest.TFFeature;

public class DarkTowerPieces {

//	public static final IStructurePieceType TFDT = TFFeature.registerPiece("TFDT", StructureStartDarkTower::new);

	public static final StructurePieceType TFDTBal = TFFeature.registerPiece("TFDTBal", DarkTowerBalconyComponent::new);
	public static final StructurePieceType TFDTBea = TFFeature.registerPiece("TFDTBea", DarkTowerBeardComponent::new);
	public static final StructurePieceType TFDTBB = TFFeature.registerPiece("TFDTBB", DarkTowerBossBridgeComponent::new);
	public static final StructurePieceType TFDTBT = TFFeature.registerPiece("TFDTBT", DarkTowerBossTrapComponent::new);
	public static final StructurePieceType TFDTBri = TFFeature.registerPiece("TFDTBri", DarkTowerBridgeComponent::new);
	public static final StructurePieceType TFDTEnt = TFFeature.registerPiece("TFDTEnt", DarkTowerEntranceComponent::new);
	public static final StructurePieceType TFDTEB = TFFeature.registerPiece("TFDTEB", DarkTowerEntranceBridgeComponent::new);
	public static final StructurePieceType TFDTMai = TFFeature.registerPiece("TFDTMai", DarkTowerMainComponent::new);
	public static final StructurePieceType TFDTMB = TFFeature.registerPiece("TFDTMB", DarkTowerMainBridgeComponent::new);
	public static final StructurePieceType TFDTRooS = TFFeature.registerPiece("TFDTRooS", DarkTowerRoofComponent::new);
	public static final StructurePieceType TFDTRA = TFFeature.registerPiece("TFDTRA", DarkTowerRoofAntennaComponent::new);
	public static final StructurePieceType TFDTRC = TFFeature.registerPiece("TFDTRC", DarkTowerRoofCactusComponent::new);
	public static final StructurePieceType TFDTRFP = TFFeature.registerPiece("TFDTRFP", DarkTowerRoofFourPostComponent::new);
	public static final StructurePieceType TFDTRR = TFFeature.registerPiece("TFDTRR", DarkTowerRoofRingsComponent::new);
	public static final StructurePieceType TFDTWin = TFFeature.registerPiece("TFDTWin", DarkTowerWingComponent::new);
}