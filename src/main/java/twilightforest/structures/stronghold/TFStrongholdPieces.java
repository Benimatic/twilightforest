package twilightforest.structures.stronghold;

import net.minecraft.util.Direction;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import twilightforest.TFFeature;
import twilightforest.structures.StructureTFComponentOld;
import twilightforest.structures.start.StructureStartKnightStronghold;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TFStrongholdPieces {

	private static final TFStrongholdPieceWeight[] pieceWeightArray = new TFStrongholdPieceWeight[]{
			new TFStrongholdPieceWeight(ComponentTFStrongholdSmallHallway::new, 40, 0),
			new TFStrongholdPieceWeight(ComponentTFStrongholdLeftTurn::new, 20, 0),
			new TFStrongholdPieceWeight(ComponentTFStrongholdCrossing::new, 10, 4),
			new TFStrongholdPieceWeight(ComponentTFStrongholdRightTurn::new, 20, 0),
			new TFStrongholdPieceWeight(ComponentTFStrongholdDeadEnd::new, 5, 0),
			new TFStrongholdPieceWeight(ComponentTFStrongholdBalconyRoom::new, 10, 3, 2),
			new TFStrongholdPieceWeight(ComponentTFStrongholdTrainingRoom::new, 10, 2),
			new TFStrongholdPieceWeight(ComponentTFStrongholdSmallStairs::new, 10, 0),
			new TFStrongholdPieceWeight(ComponentTFStrongholdTreasureCorridor::new, 5, 0),
			new TFStrongholdPieceWeight(ComponentTFStrongholdAtrium::new, 5, 2, 3),
			new TFStrongholdPieceWeight(ComponentTFStrongholdFoundry::new, 5, 1, 4),
			new TFStrongholdPieceWeight(ComponentTFStrongholdTreasureRoom::new, 5, 1, 4),
			new TFStrongholdPieceWeight(ComponentTFStrongholdBossRoom::new, 10, 1, 4)};

	private List<TFStrongholdPieceWeight> pieceList;
	static int totalWeight = 0;

	private static TFStrongholdPieceWeight lastPieceMade;

	public static final IStructurePieceType TFKSt = TFFeature.registerPiece("TFKSt", StructureStartKnightStronghold::new);

	public static final IStructurePieceType TFSSH = TFFeature.registerPiece("TFSSH", ComponentTFStrongholdSmallHallway::new);
	public static final IStructurePieceType TFSLT = TFFeature.registerPiece("TFSLT", ComponentTFStrongholdLeftTurn::new);
	public static final IStructurePieceType TFSCr = TFFeature.registerPiece("TFSCr", ComponentTFStrongholdCrossing::new);
	public static final IStructurePieceType TFSRT = TFFeature.registerPiece("TFSRT", ComponentTFStrongholdRightTurn::new);
	public static final IStructurePieceType TFSDE = TFFeature.registerPiece("TFSDE", ComponentTFStrongholdDeadEnd::new);
	public static final IStructurePieceType TFSBalR = TFFeature.registerPiece("TFSBalR", ComponentTFStrongholdBalconyRoom::new);
	public static final IStructurePieceType TFSTR = TFFeature.registerPiece("TFSTR", ComponentTFStrongholdTrainingRoom::new);
	public static final IStructurePieceType TFSSS = TFFeature.registerPiece("TFSSS", ComponentTFStrongholdSmallStairs::new);
	public static final IStructurePieceType TFSTC = TFFeature.registerPiece("TFSTC", ComponentTFStrongholdTreasureCorridor::new);
	public static final IStructurePieceType TFSAt = TFFeature.registerPiece("TFSAt", ComponentTFStrongholdAtrium::new);
	public static final IStructurePieceType TFSFo = TFFeature.registerPiece("TFSFo", ComponentTFStrongholdFoundry::new);
	public static final IStructurePieceType TFTreaR = TFFeature.registerPiece("TFTreaR", ComponentTFStrongholdTreasureRoom::new);
	public static final IStructurePieceType TFSBR = TFFeature.registerPiece("TFSBR", ComponentTFStrongholdBossRoom::new);
	public static final IStructurePieceType TFSAC = TFFeature.registerPiece("TFSAC", ComponentTFStrongholdAccessChamber::new);
	public static final IStructurePieceType TFSEnter = TFFeature.registerPiece("TFSEnter", ComponentTFStrongholdEntrance::new);
	public static final IStructurePieceType TFSUA = TFFeature.registerPiece("TFSUA", ComponentTFStrongholdUpperAscender::new);
	public static final IStructurePieceType TFSULT = TFFeature.registerPiece("TFSULT", ComponentTFStrongholdUpperLeftTurn::new);
	public static final IStructurePieceType TFSURT = TFFeature.registerPiece("TFSURT", ComponentTFStrongholdUpperRightTurn::new);
	public static final IStructurePieceType TFSUCo = TFFeature.registerPiece("TFSUCo", ComponentTFStrongholdUpperCorridor::new);
	public static final IStructurePieceType TFSUTI = TFFeature.registerPiece("TFSUTI", ComponentTFStrongholdUpperTIntersection::new);
	public static final IStructurePieceType TFSShield = TFFeature.registerPiece("TFSShield", StructureTFStrongholdShield::new);

	/**
	 * sets up Arrays with the Structure pieces and their weights
	 */
	public void prepareStructurePieces() {
		pieceList = new ArrayList<>();

		for (TFStrongholdPieceWeight piece : pieceWeightArray) {
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

		for (TFStrongholdPieceWeight piece : pieceList) {
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

				for (TFStrongholdPieceWeight piece : pieceList) {
					counter -= piece.pieceWeight;

					if (counter < 0) {

						if (!piece.isDeepEnough(index) || piece == lastPieceMade) {
							break;
						}

						// we're here!
						StructureTFStrongholdComponent component = piece.factory.newInstance(feature, index, facing, x, y, z);

						if (StructurePiece.findIntersecting(list, component.getBoundingBox()) == null) {
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
		StructureTFStrongholdComponent deadEnd = new ComponentTFStrongholdDeadEnd(parent instanceof StructureTFComponentOld ? ((StructureTFComponentOld) parent).getFeatureType() : TFFeature.NOTHING, index, facing, x, y, z);

		if (StructurePiece.findIntersecting(list, deadEnd.getBoundingBox()) == null) {
			return deadEnd;
		} else {
			return null;
		}
	}
}
