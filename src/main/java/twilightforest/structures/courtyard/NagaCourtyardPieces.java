package twilightforest.structures.courtyard;

import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import twilightforest.TFFeature;

public class NagaCourtyardPieces {

//	public static final IStructurePieceType TFNC = TFFeature.registerPiece("TFNC", StructureStartCourtyard::new);

	public static final StructurePieceType TFNCMn = TFFeature.registerPiece("TFNCMn", NagaCourtyardMainComponent::new);
	public static final StructurePieceType TFNCCp = TFFeature.registerPiece("TFNCCp", NagaCourtyardHedgeCapComponent::new);
	public static final StructurePieceType TFNCCpP = TFFeature.registerPiece("TFNCCpP", NagaCourtyardHedgeCapPillarComponent::new);
	public static final StructurePieceType TFNCCr = TFFeature.registerPiece("TFNCCr", NagaCourtyardHedgeCornerComponent::new);
	public static final StructurePieceType TFNCLn = TFFeature.registerPiece("TFNCLn", NagaCourtyardHedgeLineComponent::new);
	public static final StructurePieceType TFNCT = TFFeature.registerPiece("TFNCT", NagaCourtyardHedgeTJunctionComponent::new);
	public static final StructurePieceType TFNCIs = TFFeature.registerPiece("TFNCIs", NagaCourtyardHedgeIntersectionComponent::new);
	public static final StructurePieceType TFNCPd = TFFeature.registerPiece("TFNCPd", NagaCourtyardHedgePadderComponent::new);
	public static final StructurePieceType TFNCTr = TFFeature.registerPiece("TFNCTr", NagaCourtyardTerraceBrazierComponent::new);
	public static final StructurePieceType TFNCDu = TFFeature.registerPiece("TFNCDu", NagaCourtyardTerraceDuctComponent::new);
	public static final StructurePieceType TFNCSt = TFFeature.registerPiece("TFNCSt", NagaCourtyardTerraceStatueComponent::new);
	public static final StructurePieceType TFNCPa = TFFeature.registerPiece("TFNCPa", NagaCourtyardPathComponent::new);
	public static final StructurePieceType TFNCWl = TFFeature.registerPiece("TFNCWl", NagaCourtyardWallComponent::new);
	public static final StructurePieceType TFNCWP = TFFeature.registerPiece("TFNCWP", NagaCourtyardWallPadderComponent::new);
	public static final StructurePieceType TFNCWC = TFFeature.registerPiece("TFNCWC", NagaCourtyardWallCornerComponent::new);
	public static final StructurePieceType TFNCWA = TFFeature.registerPiece("TFNCWA", NagaCourtyardWallCornerAltComponent::new);
}
