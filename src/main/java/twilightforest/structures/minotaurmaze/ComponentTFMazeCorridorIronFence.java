package twilightforest.structures.minotaurmaze;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import twilightforest.block.TFBlocks;

public class ComponentTFMazeCorridorIronFence extends ComponentTFMazeCorridor {

	public ComponentTFMazeCorridorIronFence() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ComponentTFMazeCorridorIronFence(int i, int x, int y, int z, EnumFacing rotation) {
		super(i, x, y, z, rotation);
	}

	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {		
		this.fillWithMetadataBlocks(world, sbb, 1, 4, 2, 4, 4, 3, TFBlocks.mazestone, 3, Blocks.AIR, 0, false);
		this.fillWithMetadataBlocks(world, sbb, 1, 1, 2, 4, 3, 3, TFBlocks.mazestone, 2, Blocks.AIR, 0, false);
		this.fillWithBlocks(world, sbb, 2, 1, 2, 3, 3, 3, Blocks.IRON_BARS, Blocks.AIR, false);
		return true;
	}
}
