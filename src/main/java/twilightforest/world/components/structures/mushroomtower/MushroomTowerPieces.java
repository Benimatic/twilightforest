package twilightforest.world.components.structures.mushroomtower;

import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import twilightforest.world.registration.TFFeature;

public class MushroomTowerPieces {

//	public static final IStructurePieceType TFMT = TFFeature.registerPiece("TFMT", StructureStartMushroomTower::new);

	public static final StructurePieceType TFMTMai = TFFeature.registerPiece("TFMTMai", MushroomTowerMainComponent::new);
	public static final StructurePieceType TFMTWin = TFFeature.registerPiece("TFMTWin", MushroomTowerWingComponent::new);
	public static final StructurePieceType TFMTBri = TFFeature.registerPiece("TFMTBri", MushroomTowerBridgeComponent::new);
	public static final StructurePieceType TFMTMB = TFFeature.registerPiece("TFMTMB", MushroomTowerMainBridgeComponent::new);
	public static final StructurePieceType TFMTRoofMush = TFFeature.registerPiece("TFMTRoofMush", TowerRoofMushroomComponent::new);
}
