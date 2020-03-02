package twilightforest.structures.minotaurmaze;

import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import twilightforest.TFFeature;
import twilightforest.structures.start.StructureStartLabyrinth;

public class TFMinotaurMazePieces {

	public static final IStructurePieceType TFLr = TFFeature.registerPiece("TFLr", StructureStartLabyrinth::new);

	public static final IStructurePieceType TFMMC = TFFeature.registerPiece("TFMMC", ComponentTFMazeCorridor::new);
	public static final IStructurePieceType TFMMCIF = TFFeature.registerPiece("TFMMCIF", ComponentTFMazeCorridorIronFence::new);
	public static final IStructurePieceType TFMMCR = TFFeature.registerPiece("TFMMCR", ComponentTFMazeCorridorRoots::new);
	public static final IStructurePieceType TFMMCS = TFFeature.registerPiece("TFMMCS", ComponentTFMazeCorridorShrooms::new);
	public static final IStructurePieceType TFMMDE = TFFeature.registerPiece("TFMMDE", ComponentTFMazeDeadEnd::new);
	public static final IStructurePieceType TFMMDEC = TFFeature.registerPiece("TFMMDEC", ComponentTFMazeDeadEndChest::new);
	public static final IStructurePieceType TFMMDEF = TFFeature.registerPiece("TFMMDEF", ComponentTFMazeDeadEndFountain::new);
	public static final IStructurePieceType TFMMDEFL = TFFeature.registerPiece("TFMMDEFL", ComponentTFMazeDeadEndFountainLava::new);
	public static final IStructurePieceType TFMMDEP = TFFeature.registerPiece("TFMMDEP", ComponentTFMazeDeadEndPainting::new);
	public static final IStructurePieceType TFMMDER = TFFeature.registerPiece("TFMMDER", ComponentTFMazeDeadEndRoots::new);
	public static final IStructurePieceType TFMMDES = TFFeature.registerPiece("TFMMDES", ComponentTFMazeDeadEndShrooms::new);
	public static final IStructurePieceType TFMMDET = TFFeature.registerPiece("TFMMDET", ComponentTFMazeDeadEndTorches::new);
	public static final IStructurePieceType TFMMDETrC = TFFeature.registerPiece("TFMMDETrC", ComponentTFMazeDeadEndTrappedChest::new);
	public static final IStructurePieceType TFMMDETC = TFFeature.registerPiece("TFMMDETC", ComponentTFMazeDeadEndTripwireChest::new);
	public static final IStructurePieceType TFMMES = TFFeature.registerPiece("TFMMES", ComponentTFMazeEntranceShaft::new);
	public static final IStructurePieceType TFMMMound = TFFeature.registerPiece("TFMMMound", ComponentTFMazeMound::new);
	public static final IStructurePieceType TFMMMR = TFFeature.registerPiece("TFMMMR", ComponentTFMazeMushRoom::new);
	public static final IStructurePieceType TFMMR = TFFeature.registerPiece("TFMMR", ComponentTFMazeRoom::new);
	public static final IStructurePieceType TFMMRB = TFFeature.registerPiece("TFMMRB", ComponentTFMazeRoomBoss::new);
	public static final IStructurePieceType TFMMRC = TFFeature.registerPiece("TFMMRC", ComponentTFMazeRoomCollapse::new);
	public static final IStructurePieceType TFMMRE = TFFeature.registerPiece("TFMMRE", ComponentTFMazeRoomExit::new);
	public static final IStructurePieceType TFMMRF = TFFeature.registerPiece("TFMMRF", ComponentTFMazeRoomFountain::new);
	public static final IStructurePieceType TFMMRSC = TFFeature.registerPiece("TFMMRSC", ComponentTFMazeRoomSpawnerChests::new);
	public static final IStructurePieceType TFMMRV = TFFeature.registerPiece("TFMMRV", ComponentTFMazeRoomVault::new);
	public static final IStructurePieceType TFMMRuins = TFFeature.registerPiece("TFMMRuins", ComponentTFMazeRuins::new);
	public static final IStructurePieceType TFMMUE = TFFeature.registerPiece("TFMMUE", ComponentTFMazeUpperEntrance::new);
	public static final IStructurePieceType TFMMaze = TFFeature.registerPiece("TFMMaze", ComponentTFMinotaurMaze::new);
}