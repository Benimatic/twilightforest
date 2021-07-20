package twilightforest.structures.darktower;

import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import twilightforest.TFFeature;

public class DarkTowerPieces {

//	public static final IStructurePieceType TFDT = TFFeature.registerPiece("TFDT", StructureStartDarkTower::new);

	public static final IStructurePieceType TFDTBal = TFFeature.registerPiece("TFDTBal", DarkTowerBalconyComponent::new);
	public static final IStructurePieceType TFDTBea = TFFeature.registerPiece("TFDTBea", DarkTowerBeardComponent::new);
	public static final IStructurePieceType TFDTBB = TFFeature.registerPiece("TFDTBB", DarkTowerBossBridgeComponent::new);
	public static final IStructurePieceType TFDTBT = TFFeature.registerPiece("TFDTBT", DarkTowerBossTrapComponent::new);
	public static final IStructurePieceType TFDTBri = TFFeature.registerPiece("TFDTBri", DarkTowerBridgeComponent::new);
	public static final IStructurePieceType TFDTEnt = TFFeature.registerPiece("TFDTEnt", DarkTowerEntranceComponent::new);
	public static final IStructurePieceType TFDTEB = TFFeature.registerPiece("TFDTEB", DarkTowerEntranceBridgeComponent::new);
	public static final IStructurePieceType TFDTMai = TFFeature.registerPiece("TFDTMai", DarkTowerMainComponent::new);
	public static final IStructurePieceType TFDTMB = TFFeature.registerPiece("TFDTMB", DarkTowerMainBridgeComponent::new);
	public static final IStructurePieceType TFDTRooS = TFFeature.registerPiece("TFDTRooS", DarkTowerRoofComponent::new);
	public static final IStructurePieceType TFDTRA = TFFeature.registerPiece("TFDTRA", DarkTowerRoofAntennaComponent::new);
	public static final IStructurePieceType TFDTRC = TFFeature.registerPiece("TFDTRC", DarkTowerRoofCactusComponent::new);
	public static final IStructurePieceType TFDTRFP = TFFeature.registerPiece("TFDTRFP", DarkTowerRoofFourPostComponent::new);
	public static final IStructurePieceType TFDTRR = TFFeature.registerPiece("TFDTRR", DarkTowerRoofRingsComponent::new);
	public static final IStructurePieceType TFDTWin = TFFeature.registerPiece("TFDTWin", DarkTowerWingComponent::new);
}