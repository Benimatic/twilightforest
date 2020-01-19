package twilightforest.structures.hollowtree;

import net.minecraft.world.gen.feature.StructureIO;

public class TFHollowTreePieces {

	public static void registerPieces() {
		StructureIO.registerStructure(StructureTFHollowTreeStart.class, "TFHTLSt");

		StructureIO.registerStructureComponent(ComponentTFHollowTreeLargeBranch.class, "TFHTLB");
		StructureIO.registerStructureComponent(ComponentTFHollowTreeMedBranch.class, "TFHTMB");
		StructureIO.registerStructureComponent(ComponentTFHollowTreeSmallBranch.class, "TFHTSB");
		StructureIO.registerStructureComponent(ComponentTFHollowTreeTrunk.class, "TFHTTr");
		StructureIO.registerStructureComponent(ComponentTFHollowTreeRoot.class, "TFHTRo");
		StructureIO.registerStructureComponent(ComponentTFHollowTreeLeafDungeon.class, "TFHTLD");

	}
}
