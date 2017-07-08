package twilightforest.structures.hollowtree;

import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;
import twilightforest.TwilightForestMod;
import twilightforest.structures.StructureTFComponent;
import twilightforest.world.TFWorld;

import java.util.Iterator;
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
	public void generateStructure(World worldIn, Random rand, StructureBoundingBox sbb)
	{
		// first non-leaves
		Iterator<StructureComponent> iterator = this.components.iterator();

		while (iterator.hasNext())
		{
			StructureComponent sc = (StructureComponent)iterator.next();
			if (sc.getBoundingBox().intersectsWith(sbb))
			{
				if (sc instanceof StructureTFTreeComponent) {
					((StructureTFTreeComponent)sc).addComponentParts(worldIn, rand, sbb, false);
				}
			}
		}

		// now leaves
		iterator = this.components.iterator();

		while (iterator.hasNext())
		{
			StructureComponent sc = (StructureComponent)iterator.next();
			if (sc.getBoundingBox().intersectsWith(sbb))
			{
				if (sc instanceof StructureTFTreeComponent) {
					((StructureTFTreeComponent)sc).addComponentParts(worldIn, rand, sbb, true);
				}
			}
		}

	}
}
