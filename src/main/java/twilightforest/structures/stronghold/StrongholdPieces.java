package twilightforest.structures.stronghold;

import net.minecraft.core.Direction;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import twilightforest.TFFeature;
import twilightforest.structures.TFStructureComponentOld;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StrongholdPieces {

	private static final StrongholdPieceWeight[] pieceWeightArray = new StrongholdPieceWeight[]{
			new StrongholdPieceWeight(StrongholdSmallHallwayComponent::new, 40, 0),
			new StrongholdPieceWeight(StrongholdLeftTurnComponent::new, 20, 0),
			new StrongholdPieceWeight(StrongholdCrossingComponent::new, 10, 4),
			new StrongholdPieceWeight(StrongholdRightTurnComponent::new, 20, 0),
			new StrongholdPieceWeight(StrongholdDeadEndComponent::new, 5, 0),
			new StrongholdPieceWeight(StrongholdBalconyRoomComponent::new, 10, 3, 2),
			new StrongholdPieceWeight(StrongholdTrainingRoomComponent::new, 10, 2),
			new StrongholdPieceWeight(StrongholdSmallStairsComponent::new, 10, 0),
			new StrongholdPieceWeight(StrongholdTreasureCorridorComponent::new, 5, 0),
			new StrongholdPieceWeight(StrongholdAtriumComponent::new, 5, 2, 3),
			new StrongholdPieceWeight(StrongholdFoundryComponent::new, 5, 1, 4),
			new StrongholdPieceWeight(StrongholdTreasureRoomComponent::new, 5, 1, 4),
			new StrongholdPieceWeight(StrongholdBossRoomComponent::new, 10, 1, 4)};

	private List<StrongholdPieceWeight> pieceList;
	static int totalWeight = 0;

	private static StrongholdPieceWeight lastPieceMade;

	//public static final IStructurePieceType TFKSt = TFFeature.registerPiece("TFKSt", StructureStartKnightStronghold::new);

	public static final StructurePieceType TFSSH = TFFeature.registerPiece("TFSSH", StrongholdSmallHallwayComponent::new);
	public static final StructurePieceType TFSLT = TFFeature.registerPiece("TFSLT", StrongholdLeftTurnComponent::new);
	public static final StructurePieceType TFSCr = TFFeature.registerPiece("TFSCr", StrongholdCrossingComponent::new);
	public static final StructurePieceType TFSRT = TFFeature.registerPiece("TFSRT", StrongholdRightTurnComponent::new);
	public static final StructurePieceType TFSDE = TFFeature.registerPiece("TFSDE", StrongholdDeadEndComponent::new);
	public static final StructurePieceType TFSBalR = TFFeature.registerPiece("TFSBalR", StrongholdBalconyRoomComponent::new);
	public static final StructurePieceType TFSTR = TFFeature.registerPiece("TFSTR", StrongholdTrainingRoomComponent::new);
	public static final StructurePieceType TFSSS = TFFeature.registerPiece("TFSSS", StrongholdSmallStairsComponent::new);
	public static final StructurePieceType TFSTC = TFFeature.registerPiece("TFSTC", StrongholdTreasureCorridorComponent::new);
	public static final StructurePieceType TFSAt = TFFeature.registerPiece("TFSAt", StrongholdAtriumComponent::new);
	public static final StructurePieceType TFSFo = TFFeature.registerPiece("TFSFo", StrongholdFoundryComponent::new);
	public static final StructurePieceType TFTreaR = TFFeature.registerPiece("TFTreaR", StrongholdTreasureRoomComponent::new);
	public static final StructurePieceType TFSBR = TFFeature.registerPiece("TFSBR", StrongholdBossRoomComponent::new);
	public static final StructurePieceType TFSAC = TFFeature.registerPiece("TFSAC", StrongholdAccessChamberComponent::new);
	public static final StructurePieceType TFSEnter = TFFeature.registerPiece("TFSEnter", StrongholdEntranceComponent::new);
	public static final StructurePieceType TFSUA = TFFeature.registerPiece("TFSUA", StrongholdUpperAscenderComponent::new);
	public static final StructurePieceType TFSULT = TFFeature.registerPiece("TFSULT", StrongholdUpperLeftTurnComponent::new);
	public static final StructurePieceType TFSURT = TFFeature.registerPiece("TFSURT", StrongholdUpperRightTurnComponent::new);
	public static final StructurePieceType TFSUCo = TFFeature.registerPiece("TFSUCo", StrongholdUpperCorridorComponent::new);
	public static final StructurePieceType TFSUTI = TFFeature.registerPiece("TFSUTI", StrongholdUpperTIntersectionComponent::new);
	public static final StructurePieceType TFSShield = TFFeature.registerPiece("TFSShield", StrongholdShieldStructure::new);

	/**
	 * sets up Arrays with the Structure pieces and their weights
	 */
	public void prepareStructurePieces() {
		pieceList = new ArrayList<>();

		for (StrongholdPieceWeight piece : pieceWeightArray) {
			piece.instancesSpawned = 0;
			pieceList.add(piece);
		}
	}

	public void markBossRoomUsed() {
		// let's assume the boss room is the last one on the list
		pieceList.remove(pieceList.size() - 1);
	}

	private boolean hasMoreLimitedPieces() {
		boolean flag = false;
		totalWeight = 0;

		for (StrongholdPieceWeight piece : pieceList) {
			totalWeight += piece.pieceWeight;

			if (piece.instancesLimit > 0 && piece.instancesSpawned < piece.instancesLimit) {
				flag = true;
			}
		}

		return flag;
	}

	public StructureTFStrongholdComponent getNextComponent(StructurePiece parent, List<StructurePiece> list, Random random, TFFeature feature, int index, Direction facing, int x, int y, int z) {
		if (!hasMoreLimitedPieces()) {
			return null;
		} else {
			// repeat up to 5 times if we're not getting the right thing
			for (int i = 0; i < 5; i++) {
				int counter = random.nextInt(totalWeight);

				for (StrongholdPieceWeight piece : pieceList) {
					counter -= piece.pieceWeight;

					if (counter < 0) {

						if (!piece.isDeepEnough(index) || piece == lastPieceMade) {
							break;
						}

						// we're here!
						StructureTFStrongholdComponent component = piece.factory.newInstance(feature, index, facing, x, y, z);

						if (StructurePiece.findCollisionPiece(list, component.getBoundingBox()) == null) {
							++piece.instancesSpawned;

							if (!piece.canSpawnMoreStructures()) {
								pieceList.remove(piece);
							}

							lastPieceMade = piece;

							return component;
						}
					}
				}
			}
		}

		// dead end?
		StructureTFStrongholdComponent deadEnd = new StrongholdDeadEndComponent(parent instanceof TFStructureComponentOld ? ((TFStructureComponentOld) parent).getFeatureType() : TFFeature.NOTHING, index, facing, x, y, z);

		if (StructurePiece.findCollisionPiece(list, deadEnd.getBoundingBox()) == null) {
			return deadEnd;
		} else {
			return null;
		}
	}
}
