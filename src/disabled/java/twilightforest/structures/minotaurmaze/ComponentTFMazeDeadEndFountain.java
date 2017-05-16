package twilightforest.structures.minotaurmaze;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import twilightforest.block.TFBlocks;

public class ComponentTFMazeDeadEndFountain extends ComponentTFMazeDeadEnd {

	public ComponentTFMazeDeadEndFountain() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ComponentTFMazeDeadEndFountain(int i, int x, int y, int z, EnumFacing rotation) {
		super(i, x, y, z, rotation);
	}

	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {		
		// normal doorway
		super.addComponentParts(world, rand, sbb);
		
		// back wall brick
		this.fillWithMetadataBlocks(world, sbb, 1, 1, 4, 4, 4, 4, TFBlocks.mazestone, 1, AIR, false);

		// water
		this.setBlockState(world, Blocks.FLOWING_WATER, 0, 2, 3, 4, sbb);
		this.setBlockState(world, Blocks.FLOWING_WATER, 0, 3, 3, 4, sbb);

		// receptacle
		this.setBlockState(world, AIR, 2, 0, 3, sbb);
		this.setBlockState(world, AIR, 3, 0, 3, sbb);
		
		return true;
	}
}
