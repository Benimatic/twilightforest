package twilightforest.structures.hollowtree;

import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.StructureStart;
import twilightforest.structures.StructureTFComponent;

import java.util.Random;

public class StructureTFHollowTreeStart extends StructureStart {

	public StructureTFHollowTreeStart() {
	}

	public StructureTFHollowTreeStart(World world, Random rand, int chunkX, int chunkZ) {
		int x = (chunkX << 4) + 8;
		int z = (chunkZ << 4) + 8;
		int y = TFWorld.SEALEVEL + 1;

		StructureTFComponent trunk = new ComponentTFHollowTreeTrunk(world, rand, 0, x, y, z);
		components.add(trunk);
		trunk.buildComponent(trunk, components, rand);
		updateBoundingBox();
	}

	/**
	 * Do everything except leaves before we do leaves.
	 */
	@Override
	public void generateStructure(IWorld worldIn, Random rand, MutableBoundingBox sbb, ChunkPos pos) {
		// first wood
		for (StructurePiece sc : components) {
			if (sc.getBoundingBox().intersectsWith(sbb) && sc instanceof StructureTFTreeComponent) {
				((StructureTFTreeComponent)sc).addComponentParts(worldIn.getWorld(), rand, sbb, false);
			}
		}

		// now leaves
		for (StructurePiece sc : components) {
			if (sc.getBoundingBox().intersectsWith(sbb) && sc instanceof StructureTFTreeComponent) {
				((StructureTFTreeComponent)sc).addComponentParts(worldIn.getWorld(), rand, sbb, true);
			}
		}
	}
}
