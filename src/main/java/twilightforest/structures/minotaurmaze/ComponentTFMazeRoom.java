package twilightforest.structures.minotaurmaze;

import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import twilightforest.block.TFBlocks;
import twilightforest.structures.StructureTFComponent;

public class ComponentTFMazeRoom extends StructureTFComponent {

	public ComponentTFMazeRoom() {
		super();
	}

	public ComponentTFMazeRoom(int i, Random rand, int x, int y, int z) {
		super(i);
        this.coordBaseMode = rand.nextInt(4);

        this.boundingBox = new StructureBoundingBox(x, y, z, x + 15, y + 4, z + 15);
	}

	/**
	 * Initiates construction of the Structure Component picked, at the current Location of StructGen
	 */
	@Override
	public void buildComponent(StructureComponent structurecomponent, List list, Random random) {
		;
	}

	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		// floor border
		fillWithMetadataBlocks(world, sbb, 1, 0, 1, 14, 0, 14, TFBlocks.mazestone, 7, Blocks.air, 0, true);
		fillWithMetadataBlocks(world, sbb, 2, 0, 2, 13, 0, 13, TFBlocks.mazestone, 6, Blocks.air, 0, true);

		// doorways
		if (this.getBlockAtCurrentPosition(world, 7, 1, 0, sbb) == Blocks.air)
		{
			fillWithBlocks(world, sbb, 6, 1, 0, 9, 4, 0, Blocks.fence, Blocks.air, false);
			fillWithAir(world, sbb, 7, 1, 0, 8, 3, 0);
		}

		if (this.getBlockAtCurrentPosition(world, 7, 1, 15, sbb) == Blocks.air)
		{
			fillWithBlocks(world, sbb, 6, 1, 15, 9, 4, 15, Blocks.fence, Blocks.air, false);
			fillWithAir(world, sbb, 7, 1, 15, 8, 3, 15);
		}

		if (this.getBlockAtCurrentPosition(world, 0, 1, 7, sbb) == Blocks.air)
		{
			fillWithBlocks(world, sbb, 0, 1, 6, 0, 4, 9, Blocks.fence, Blocks.air, false);
			fillWithAir(world, sbb, 0, 1, 7, 0, 3, 8);
		}

		if (this.getBlockAtCurrentPosition(world, 15, 1, 7, sbb) == Blocks.air)
		{
			fillWithBlocks(world, sbb, 15, 1, 6, 15, 4, 9, Blocks.fence, Blocks.air, false);
			fillWithAir(world, sbb, 15, 1, 7, 15, 3, 8);
		}
		return true;
	}
}
