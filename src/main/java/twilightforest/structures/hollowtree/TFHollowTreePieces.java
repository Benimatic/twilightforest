package twilightforest.structures.hollowtree;

import net.minecraft.world.gen.structure.MapGenStructureIO;

public class TFHollowTreePieces {

	public static void registerPieces() {
        MapGenStructureIO.func_143031_a(ComponentTFHollowTreeLargeBranch.class, "TFHTLB");
        MapGenStructureIO.func_143031_a(ComponentTFHollowTreeMedBranch.class, "TFHTMB");
        MapGenStructureIO.func_143031_a(ComponentTFHollowTreeSmallBranch.class, "TFHTSB");
        MapGenStructureIO.func_143031_a(ComponentTFHollowTreeTrunk.class, "TFHTTr");
        MapGenStructureIO.func_143031_a(ComponentTFLeafSphere.class, "TFHTLS");
        MapGenStructureIO.func_143031_a(ComponentTFHollowTreeRoot.class, "TFHTRo");
        MapGenStructureIO.func_143031_a(StructureTFHollowTreeStart.class, "TFHTLSt");
        MapGenStructureIO.func_143031_a(ComponentTFHollowTreeLeafDungeon.class, "TFHTLD");

	}
}
