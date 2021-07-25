package twilightforest.structures.icetower;

import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import twilightforest.TFFeature;

public class IceTowerPieces {

//	public static final IStructurePieceType TFAP = TFFeature.registerPiece("TFAP", StructureStartAuroraPalace::new);

	public static final StructurePieceType TFITMai = TFFeature.registerPiece("TFITMai", IceTowerMainComponent::new);
	public static final StructurePieceType TFITWin = TFFeature.registerPiece("TFITWin", IceTowerWingComponent::new);
	public static final StructurePieceType TFITRoof = TFFeature.registerPiece("TFITRoof", IceTowerRoofComponent::new);
	public static final StructurePieceType TFITBea = TFFeature.registerPiece("TFITBea", IceTowerBeardComponent::new);
	public static final StructurePieceType TFITBoss = TFFeature.registerPiece("TFITBoss", IceTowerBossWingComponent::new);
	public static final StructurePieceType TFITEnt = TFFeature.registerPiece("TFITEnt", IceTowerEntranceComponent::new);
	public static final StructurePieceType TFITBri = TFFeature.registerPiece("TFITBri", IceTowerBridgeComponent::new);
	public static final StructurePieceType TFITSt = TFFeature.registerPiece("TFITSt", IceTowerStairsComponent::new);
}
