package twilightforest.structures.courtyard;

import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import twilightforest.TFFeature;

public class NagaCourtyardPieces {

//	public static final IStructurePieceType TFNC = TFFeature.registerPiece("TFNC", StructureStartCourtyard::new);

	public static final IStructurePieceType TFNCMn = TFFeature.registerPiece("TFNCMn", ComponentNagaCourtyardMain::new);
	public static final IStructurePieceType TFNCCp = TFFeature.registerPiece("TFNCCp", ComponentNagaCourtyardHedgeCap::new);
	public static final IStructurePieceType TFNCCpP = TFFeature.registerPiece("TFNCCpP", ComponentNagaCourtyardHedgeCapPillar::new);
	public static final IStructurePieceType TFNCCr = TFFeature.registerPiece("TFNCCr", ComponentNagaCourtyardHedgeCorner::new);
	public static final IStructurePieceType TFNCLn = TFFeature.registerPiece("TFNCLn", ComponentNagaCourtyardHedgeLine::new);
	public static final IStructurePieceType TFNCT = TFFeature.registerPiece("TFNCT", ComponentNagaCourtyardHedgeTJunction::new);
	public static final IStructurePieceType TFNCIs = TFFeature.registerPiece("TFNCIs", ComponentNagaCourtyardHedgeIntersection::new);
	public static final IStructurePieceType TFNCPd = TFFeature.registerPiece("TFNCPd", ComponentNagaCourtyardHedgePadder::new);
	public static final IStructurePieceType TFNCTr = TFFeature.registerPiece("TFNCTr", ComponentNagaCourtyardTerraceBrazier::new);
	public static final IStructurePieceType TFNCDu = TFFeature.registerPiece("TFNCDu", ComponentNagaCourtyardTerraceDuct::new);
	public static final IStructurePieceType TFNCSt = TFFeature.registerPiece("TFNCSt", ComponentNagaCourtyardTerraceStatue::new);
	public static final IStructurePieceType TFNCPa = TFFeature.registerPiece("TFNCPa", ComponentNagaCourtyardPath::new);
	public static final IStructurePieceType TFNCWl = TFFeature.registerPiece("TFNCWl", ComponentNagaCourtyardWall::new);
	public static final IStructurePieceType TFNCWP = TFFeature.registerPiece("TFNCWP", ComponentNagaCourtyardWallPadder::new);
	public static final IStructurePieceType TFNCWC = TFFeature.registerPiece("TFNCWC", ComponentNagaCourtyardWallCorner::new);
	public static final IStructurePieceType TFNCWA = TFFeature.registerPiece("TFNCWA", ComponentNagaCourtyardWallCornerAlt::new);
}
