package twilightforest.structures.minotaurmaze;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public class ComponentTFMazeDeadEndTorches extends ComponentTFMazeDeadEnd {

	public ComponentTFMazeDeadEndTorches() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ComponentTFMazeDeadEndTorches(int i, int x, int y, int z, int rotation) {
		super(i, x, y, z, rotation);
	}

	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {		
		// normal doorway
		super.addComponentParts(world, rand, sbb);
		
		// torches!
		this.fillWithMetadataBlocks(world, sbb, 2, 1, 4, 3, 4, 4, Blocks.TORCH, 0, Blocks.AIR, 0, false);
		this.fillWithMetadataBlocks(world, sbb, 1, 1, 1, 1, 4, 4, Blocks.TORCH, 0, Blocks.AIR, 0, false);
		this.fillWithMetadataBlocks(world, sbb, 4, 1, 1, 4, 4, 4, Blocks.TORCH, 0, Blocks.AIR, 0, false);

		
		return true;
	}
}
