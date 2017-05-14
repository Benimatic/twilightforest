package twilightforest.structures.minotaurmaze;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import twilightforest.structures.StructureTFComponent;

public class ComponentTFMazeDeadEnd extends StructureTFComponent {

	public ComponentTFMazeDeadEnd() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ComponentTFMazeDeadEnd(int i, int x, int y, int z, EnumFacing rotation) {
		super(i);
        this.coordBaseMode = rotation;
        this.boundingBox = new StructureBoundingBox(x, y, z, x + 5, y + 5, z + 5);

	}

	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {		
		this.fillWithMetadataBlocks(world, sbb, 1, 1, 0, 4, 4, 0, Blocks.FENCE, 0, Blocks.AIR, 0, false);
		this.fillWithAir(world, sbb, 2, 1, 0, 3, 3, 0);
		return true;
	}
}
