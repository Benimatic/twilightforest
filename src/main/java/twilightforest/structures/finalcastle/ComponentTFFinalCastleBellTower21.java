package twilightforest.structures.finalcastle;

import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import twilightforest.block.TFBlocks;
import twilightforest.structures.StructureTFComponent;
import java.util.List;
import java.util.Random;

public class ComponentTFFinalCastleBellTower21 extends ComponentTFFinalCastleMazeTower13
{

	private static final int FLOORS = 8;

	public ComponentTFFinalCastleBellTower21() { }

	public ComponentTFFinalCastleBellTower21(Random rand, int i, int x, int y, int z, EnumFacing direction) {
		super(rand, i, x, y, z, FLOORS, 1, 1, direction);
		this.size = 21;
		int floors = FLOORS;
		this.height = floors * 8 + 1;
		this.boundingBox = StructureTFComponent.getComponentToAddBoundingBox2(x, y, z, -6, -8, -this.size / 2, this.size - 1, this.height, this.size - 1, direction);
		this.openings.clear();
	    addOpening(0, 9, size / 2, 2);
	}

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void buildComponent(StructureComponent parent, List list, Random rand) {
	    if (parent != null && parent instanceof StructureTFComponent) {
		    this.deco = ((StructureTFComponent)parent).deco;
	    }

	    // add foundation
	    ComponentTFFinalCastleBellFoundation21 foundation = new ComponentTFFinalCastleBellFoundation21(rand, 4, this);
	    list.add(foundation);
	    foundation.buildComponent(this, list, rand);

	    // add roof
	    StructureTFComponent roof = new ComponentTFFinalCastleRoof13Crenellated(rand, 4, this);
	    list.add(roof);
	    roof.buildComponent(this, list, rand);
    }

	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		super.addComponentParts(world, rand, sbb);

		// openings!
		Block fieldBlock = TFBlocks.forceField;
		int fieldMeta = 4;
        for (int rotation = 0; rotation < 4; rotation++) {
	        int y = 48;
	        for (int x = 5; x < this.size - 4; x += 2) {
//	        		for (int wy = 0; wy < 15; wy++) {
//		        		fieldMeta = rand.nextInt(4) + 1;
//	        			this.setBlockStateRotated(world, fieldBlock, fieldMeta, x, y + wy, 0, rotation, sbb);
//	        		}
//	        		fieldMeta = rand.nextInt(5);
		        this.fillBlocksRotated(world, sbb, x, y, 0, x, y + 14, 0, fieldBlock, fieldMeta, rotation);
	        }
	        y = 24;
	        for (int x = 1; x < this.size - 1; x += 8) {
//	        		for (int wy = 0; wy < 15; wy++) {
//		        		fieldMeta = rand.nextInt(4) + 1;
//	        			this.setBlockStateRotated(world, fieldBlock, fieldMeta, x, y + wy, 0, rotation, sbb);
//		        		fieldMeta = rand.nextInt(4) + 1;
//	        			this.setBlockStateRotated(world, fieldBlock, fieldMeta, x + 2, y + wy, 0, rotation, sbb);
//	        		}
//	        		fieldMeta = rand.nextInt(5);
		        this.fillBlocksRotated(world, sbb, x, y, 0, x, y + 14, 0, fieldBlock, fieldMeta, rotation);
//	        		fieldMeta = rand.nextInt(5);
		        this.fillBlocksRotated(world, sbb, x + 2, y, 0, x + 2, y + 14, 0, fieldBlock, fieldMeta, rotation);
	        }
        }

        // sign
        this.placeSignAtCurrentPosition(world, 7, 9, 8, "Parkour area 2", "mini-boss 1", sbb);

		return true;
	}
}
