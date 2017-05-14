package twilightforest.structures.lichtower;

import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;


public class ComponentTFTowerOutbuilding extends ComponentTFTowerWing {

	public ComponentTFTowerOutbuilding() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected ComponentTFTowerOutbuilding(int i, int x, int y, int z, int pSize, int pHeight, EnumFacing direction) {
		super(i, x, y, z, pSize, pHeight, direction);
		// TODO Auto-generated constructor stub
	}

	/**
	 * NO BEARDS!
	 */
	@Override
	public void makeABeard(StructureComponent parent, List<StructureComponent> list, Random rand) {
		return;
	}

	/**
	 * Outbuildings should not make new wings close to the ground.
	 */
	@Override
	public boolean makeTowerWing(List<StructureComponent> list, Random rand, int index, int x, int y, int z, int wingSize, int wingHeight, int direction) {
		if (y > 7) {
			return super.makeTowerWing(list, rand, index, x, y, z, wingSize, wingHeight, direction);
		}
		else {
			return false;
		}
	}

	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		for (int x = 0; x < this.size; x++)
		{
			for (int z = 0; z < this.size; z++)
			{
				this.func_151554_b(world, Blocks.COBBLESTONE, 0, x, -1, z, sbb);
			}
		}
		return super.addComponentParts(world, rand, sbb);
	}

	
	
}
