package twilightforest.structures.trollcave;

import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import twilightforest.TFFeature;

public class TrollCavePieces {

//	public static final IStructurePieceType TFTC = TFFeature.registerPiece("TFTC", StructureStartTrollCave::new);

	public static final IStructurePieceType TFTCMai = TFFeature.registerPiece("TFTCMai", TrollCaveMainComponent::new);
	public static final IStructurePieceType TFTCCon = TFFeature.registerPiece("TFTCCon", TrollCaveConnectComponent::new);
	public static final IStructurePieceType TFTCGard = TFFeature.registerPiece("TFTCGard", TrollCaveGardenComponent::new);
	public static final IStructurePieceType TFTCloud = TFFeature.registerPiece("TFTCloud", TrollCloudComponent::new);
	public static final IStructurePieceType TFClCa = TFFeature.registerPiece("TFClCa", CloudCastleComponent::new);
	public static final IStructurePieceType TFClTr = TFFeature.registerPiece("TFClTr", CloudTreeComponent::new);
	public static final IStructurePieceType TFTCVa = TFFeature.registerPiece("TFTCVa", TrollVaultComponent::new);
}