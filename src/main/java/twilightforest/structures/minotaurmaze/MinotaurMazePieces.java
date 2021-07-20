package twilightforest.structures.minotaurmaze;

import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import twilightforest.TFFeature;

public class MinotaurMazePieces {

//	public static final IStructurePieceType TFLr = TFFeature.registerPiece("TFLr", StructureStartLabyrinth::new);

	public static final IStructurePieceType TFMMC = TFFeature.registerPiece("TFMMC", MazeCorridorComponent::new);
	public static final IStructurePieceType TFMMCIF = TFFeature.registerPiece("TFMMCIF", MazeCorridorIronFenceComponent::new);
	public static final IStructurePieceType TFMMCR = TFFeature.registerPiece("TFMMCR", MazeCorridorRootsComponent::new);
	public static final IStructurePieceType TFMMCS = TFFeature.registerPiece("TFMMCS", MazeCorridorShroomsComponent::new);
	public static final IStructurePieceType TFMMDE = TFFeature.registerPiece("TFMMDE", MazeDeadEndComponent::new);
	public static final IStructurePieceType TFMMDEC = TFFeature.registerPiece("TFMMDEC", MazeDeadEndChestComponent::new);
	public static final IStructurePieceType TFMMDEF = TFFeature.registerPiece("TFMMDEF", MazeDeadEndFountainComponent::new);
	public static final IStructurePieceType TFMMDEFL = TFFeature.registerPiece("TFMMDEFL", MazeDeadEndFountainLavaComponent::new);
	public static final IStructurePieceType TFMMDEP = TFFeature.registerPiece("TFMMDEP", MazeDeadEndPaintingComponent::new);
	public static final IStructurePieceType TFMMDER = TFFeature.registerPiece("TFMMDER", MazeDeadEndRootsComponent::new);
	public static final IStructurePieceType TFMMDES = TFFeature.registerPiece("TFMMDES", MazeDeadEndShroomsComponent::new);
	public static final IStructurePieceType TFMMDET = TFFeature.registerPiece("TFMMDET", MazeDeadEndTorchesComponent::new);
	public static final IStructurePieceType TFMMDETrC = TFFeature.registerPiece("TFMMDETrC", MazeDeadEndTrappedChestComponent::new);
	public static final IStructurePieceType TFMMDETC = TFFeature.registerPiece("TFMMDETC", MazeDeadEndTripwireChestComponent::new);
	public static final IStructurePieceType TFMMES = TFFeature.registerPiece("TFMMES", MazeEntranceShaftComponent::new);
	public static final IStructurePieceType TFMMMound = TFFeature.registerPiece("TFMMMound", MazeMoundComponent::new);
	public static final IStructurePieceType TFMMMR = TFFeature.registerPiece("TFMMMR", MazeMushRoomComponent::new);
	public static final IStructurePieceType TFMMR = TFFeature.registerPiece("TFMMR", MazeRoomComponent::new);
	public static final IStructurePieceType TFMMRB = TFFeature.registerPiece("TFMMRB", MazeRoomBossComponent::new);
	public static final IStructurePieceType TFMMRC = TFFeature.registerPiece("TFMMRC", MazeRoomCollapseComponent::new);
	public static final IStructurePieceType TFMMRE = TFFeature.registerPiece("TFMMRE", MazeRoomExitComponent::new);
	public static final IStructurePieceType TFMMRF = TFFeature.registerPiece("TFMMRF", MazeRoomFountainComponent::new);
	public static final IStructurePieceType TFMMRSC = TFFeature.registerPiece("TFMMRSC", MazeRoomSpawnerChestsComponent::new);
	public static final IStructurePieceType TFMMRV = TFFeature.registerPiece("TFMMRV", MazeRoomVaultComponent::new);
	public static final IStructurePieceType TFMMRuins = TFFeature.registerPiece("TFMMRuins", MazeRuinsComponent::new);
	public static final IStructurePieceType TFMMUE = TFFeature.registerPiece("TFMMUE", MazeUpperEntranceComponent::new);
	public static final IStructurePieceType TFMMaze = TFFeature.registerPiece("TFMMaze", MinotaurMazeComponent::new);
}