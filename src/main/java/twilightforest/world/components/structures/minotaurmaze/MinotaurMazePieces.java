package twilightforest.world.components.structures.minotaurmaze;

import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import twilightforest.world.registration.TFFeature;

public class MinotaurMazePieces {

//	public static final IStructurePieceType TFLr = TFFeature.registerPiece("TFLr", StructureStartLabyrinth::new);

	public static final StructurePieceType TFMMC = TFFeature.registerPiece("TFMMC", MazeCorridorComponent::new);
	public static final StructurePieceType TFMMCIF = TFFeature.registerPiece("TFMMCIF", MazeCorridorIronFenceComponent::new);
	public static final StructurePieceType TFMMCR = TFFeature.registerPiece("TFMMCR", MazeCorridorRootsComponent::new);
	public static final StructurePieceType TFMMCS = TFFeature.registerPiece("TFMMCS", MazeCorridorShroomsComponent::new);
	public static final StructurePieceType TFMMDE = TFFeature.registerPiece("TFMMDE", MazeDeadEndComponent::new);
	public static final StructurePieceType TFMMDEC = TFFeature.registerPiece("TFMMDEC", MazeDeadEndChestComponent::new);
	public static final StructurePieceType TFMMDEF = TFFeature.registerPiece("TFMMDEF", MazeDeadEndFountainComponent::new);
	public static final StructurePieceType TFMMDEFL = TFFeature.registerPiece("TFMMDEFL", MazeDeadEndFountainLavaComponent::new);
	public static final StructurePieceType TFMMDEP = TFFeature.registerPiece("TFMMDEP", MazeDeadEndPaintingComponent::new);
	public static final StructurePieceType TFMMDER = TFFeature.registerPiece("TFMMDER", MazeDeadEndRootsComponent::new);
	public static final StructurePieceType TFMMDES = TFFeature.registerPiece("TFMMDES", MazeDeadEndShroomsComponent::new);
	public static final StructurePieceType TFMMDET = TFFeature.registerPiece("TFMMDET", MazeDeadEndTorchesComponent::new);
	public static final StructurePieceType TFMMDETrC = TFFeature.registerPiece("TFMMDETrC", MazeDeadEndTrappedChestComponent::new);
	public static final StructurePieceType TFMMDETC = TFFeature.registerPiece("TFMMDETC", MazeDeadEndTripwireChestComponent::new);
	public static final StructurePieceType TFMMES = TFFeature.registerPiece("TFMMES", MazeEntranceShaftComponent::new);
	public static final StructurePieceType TFMMMound = TFFeature.registerPiece("TFMMMound", MazeMoundComponent::new);
	public static final StructurePieceType TFMMMR = TFFeature.registerPiece("TFMMMR", MazeMushRoomComponent::new);
	public static final StructurePieceType TFMMR = TFFeature.registerPiece("TFMMR", MazeRoomComponent::new);
	public static final StructurePieceType TFMMRB = TFFeature.registerPiece("TFMMRB", MazeRoomBossComponent::new);
	public static final StructurePieceType TFMMRC = TFFeature.registerPiece("TFMMRC", MazeRoomCollapseComponent::new);
	public static final StructurePieceType TFMMRE = TFFeature.registerPiece("TFMMRE", MazeRoomExitComponent::new);
	public static final StructurePieceType TFMMRF = TFFeature.registerPiece("TFMMRF", MazeRoomFountainComponent::new);
	public static final StructurePieceType TFMMRSC = TFFeature.registerPiece("TFMMRSC", MazeRoomSpawnerChestsComponent::new);
	public static final StructurePieceType TFMMRV = TFFeature.registerPiece("TFMMRV", MazeRoomVaultComponent::new);
	public static final StructurePieceType TFMMRuins = TFFeature.registerPiece("TFMMRuins", MazeRuinsComponent::new);
	public static final StructurePieceType TFMMUE = TFFeature.registerPiece("TFMMUE", MazeUpperEntranceComponent::new);
	public static final StructurePieceType TFMMaze = TFFeature.registerPiece("TFMMaze", MinotaurMazeComponent::new);
}