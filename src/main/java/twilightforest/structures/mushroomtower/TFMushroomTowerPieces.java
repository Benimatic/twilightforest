package twilightforest.structures.mushroomtower;

import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import twilightforest.TFFeature;
import twilightforest.structures.start.StructureStartMushroomTower;

public class TFMushroomTowerPieces {

	public static final IStructurePieceType TFMT = TFFeature.registerPiece("TFMT", StructureStartMushroomTower::new);

	public static final IStructurePieceType TFMTMai = TFFeature.registerPiece("TFMTMai", ComponentTFMushroomTowerMain::new);
	public static final IStructurePieceType TFMTWin = TFFeature.registerPiece("TFMTWin", ComponentTFMushroomTowerWing::new);
	public static final IStructurePieceType TFMTBri = TFFeature.registerPiece("TFMTBri", ComponentTFMushroomTowerBridge::new);
	public static final IStructurePieceType TFMTMB = TFFeature.registerPiece("TFMTMB", ComponentTFMushroomTowerMainBridge::new);
	public static final IStructurePieceType TFMTRoofMush = TFFeature.registerPiece("TFMTRoofMush", ComponentTFTowerRoofMushroom::new);
}
