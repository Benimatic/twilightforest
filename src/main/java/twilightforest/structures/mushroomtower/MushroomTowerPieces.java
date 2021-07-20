package twilightforest.structures.mushroomtower;

import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import twilightforest.TFFeature;

public class MushroomTowerPieces {

//	public static final IStructurePieceType TFMT = TFFeature.registerPiece("TFMT", StructureStartMushroomTower::new);

	public static final IStructurePieceType TFMTMai = TFFeature.registerPiece("TFMTMai", MushroomTowerMainComponent::new);
	public static final IStructurePieceType TFMTWin = TFFeature.registerPiece("TFMTWin", MushroomTowerWingComponent::new);
	public static final IStructurePieceType TFMTBri = TFFeature.registerPiece("TFMTBri", MushroomTowerBridgeComponent::new);
	public static final IStructurePieceType TFMTMB = TFFeature.registerPiece("TFMTMB", MushroomTowerMainBridgeComponent::new);
	public static final IStructurePieceType TFMTRoofMush = TFFeature.registerPiece("TFMTRoofMush", TowerRoofMushroomComponent::new);
}
