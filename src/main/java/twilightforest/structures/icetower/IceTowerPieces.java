package twilightforest.structures.icetower;

import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import twilightforest.TFFeature;

public class IceTowerPieces {

//	public static final IStructurePieceType TFAP = TFFeature.registerPiece("TFAP", StructureStartAuroraPalace::new);

	public static final IStructurePieceType TFITMai = TFFeature.registerPiece("TFITMai", IceTowerMainComponent::new);
	public static final IStructurePieceType TFITWin = TFFeature.registerPiece("TFITWin", IceTowerWingComponent::new);
	public static final IStructurePieceType TFITRoof = TFFeature.registerPiece("TFITRoof", IceTowerRoofComponent::new);
	public static final IStructurePieceType TFITBea = TFFeature.registerPiece("TFITBea", IceTowerBeardComponent::new);
	public static final IStructurePieceType TFITBoss = TFFeature.registerPiece("TFITBoss", IceTowerBossWingComponent::new);
	public static final IStructurePieceType TFITEnt = TFFeature.registerPiece("TFITEnt", IceTowerEntranceComponent::new);
	public static final IStructurePieceType TFITBri = TFFeature.registerPiece("TFITBri", IceTowerBridgeComponent::new);
	public static final IStructurePieceType TFITSt = TFFeature.registerPiece("TFITSt", IceTowerStairsComponent::new);
}
