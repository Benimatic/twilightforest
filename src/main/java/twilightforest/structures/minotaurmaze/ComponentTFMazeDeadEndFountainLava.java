package twilightforest.structures.minotaurmaze;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public class ComponentTFMazeDeadEndFountainLava extends ComponentTFMazeDeadEndFountain {
	
	public ComponentTFMazeDeadEndFountainLava() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ComponentTFMazeDeadEndFountainLava(int i, int x, int y, int z, EnumFacing rotation) {
		super(i, x, y, z, rotation);
	}

	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {		
		// normal fountain
		super.addComponentParts(world, rand, sbb);
		
		// remove water
		this.placeBlockAtCurrentPosition(world, Blocks.AIR, 0, 2, 3, 4, sbb);
		this.placeBlockAtCurrentPosition(world, Blocks.AIR, 0, 3, 3, 4, sbb);
		
		// lava instead of water
		this.placeBlockAtCurrentPosition(world, Blocks.FLOWING_LAVA, 0, 2, 3, 4, sbb);
		this.placeBlockAtCurrentPosition(world, Blocks.FLOWING_LAVA, 0, 3, 3, 4, sbb);
		
		return true;
	}


}
