package twilightforest.structures.trollcave;

import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import twilightforest.TFFeature;

public class TrollCavePieces {

//	public static final IStructurePieceType TFTC = TFFeature.registerPiece("TFTC", StructureStartTrollCave::new);

	public static final StructurePieceType TFTCMai = TFFeature.registerPiece("TFTCMai", TrollCaveMainComponent::new);
	public static final StructurePieceType TFTCCon = TFFeature.registerPiece("TFTCCon", TrollCaveConnectComponent::new);
	public static final StructurePieceType TFTCGard = TFFeature.registerPiece("TFTCGard", TrollCaveGardenComponent::new);
	public static final StructurePieceType TFTCloud = TFFeature.registerPiece("TFTCloud", TrollCloudComponent::new);
	public static final StructurePieceType TFClCa = TFFeature.registerPiece("TFClCa", CloudCastleComponent::new);
	public static final StructurePieceType TFClTr = TFFeature.registerPiece("TFClTr", CloudTreeComponent::new);
	public static final StructurePieceType TFTCVa = TFFeature.registerPiece("TFTCVa", TrollVaultComponent::new);
}