package twilightforest.structures.courtyard;

import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import twilightforest.TFFeature;

public class NagaCourtyardPieces {

//	public static final IStructurePieceType TFNC = TFFeature.registerPiece("TFNC", StructureStartCourtyard::new);

	public static final IStructurePieceType TFNCMn = TFFeature.registerPiece("TFNCMn", NagaCourtyardMainComponent::new);
	public static final IStructurePieceType TFNCCp = TFFeature.registerPiece("TFNCCp", NagaCourtyardHedgeCapComponent::new);
	public static final IStructurePieceType TFNCCpP = TFFeature.registerPiece("TFNCCpP", NagaCourtyardHedgeCapPillarComponent::new);
	public static final IStructurePieceType TFNCCr = TFFeature.registerPiece("TFNCCr", NagaCourtyardHedgeCornerComponent::new);
	public static final IStructurePieceType TFNCLn = TFFeature.registerPiece("TFNCLn", NagaCourtyardHedgeLineComponent::new);
	public static final IStructurePieceType TFNCT = TFFeature.registerPiece("TFNCT", NagaCourtyardHedgeTJunctionComponent::new);
	public static final IStructurePieceType TFNCIs = TFFeature.registerPiece("TFNCIs", NagaCourtyardHedgeIntersectionComponent::new);
	public static final IStructurePieceType TFNCPd = TFFeature.registerPiece("TFNCPd", NagaCourtyardHedgePadderComponent::new);
	public static final IStructurePieceType TFNCTr = TFFeature.registerPiece("TFNCTr", NagaCourtyardTerraceBrazierComponent::new);
	public static final IStructurePieceType TFNCDu = TFFeature.registerPiece("TFNCDu", NagaCourtyardTerraceDuctComponent::new);
	public static final IStructurePieceType TFNCSt = TFFeature.registerPiece("TFNCSt", NagaCourtyardTerraceStatueComponent::new);
	public static final IStructurePieceType TFNCPa = TFFeature.registerPiece("TFNCPa", NagaCourtyardPathComponent::new);
	public static final IStructurePieceType TFNCWl = TFFeature.registerPiece("TFNCWl", NagaCourtyardWallComponent::new);
	public static final IStructurePieceType TFNCWP = TFFeature.registerPiece("TFNCWP", NagaCourtyardWallPadderComponent::new);
	public static final IStructurePieceType TFNCWC = TFFeature.registerPiece("TFNCWC", NagaCourtyardWallCornerComponent::new);
	public static final IStructurePieceType TFNCWA = TFFeature.registerPiece("TFNCWA", NagaCourtyardWallCornerAltComponent::new);
}
