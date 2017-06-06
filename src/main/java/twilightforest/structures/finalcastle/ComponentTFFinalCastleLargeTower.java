package twilightforest.structures.finalcastle;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import twilightforest.block.TFBlocks;
import twilightforest.structures.StructureTFComponent;
import twilightforest.structures.lichtower.ComponentTFTowerWing;
import java.util.List;
import java.util.Random;

public class ComponentTFFinalCastleLargeTower extends ComponentTFTowerWing
{

    public ComponentTFFinalCastleLargeTower() {
    }

    public ComponentTFFinalCastleLargeTower(Random rand, int i, int x, int y, int z, int rotation) {
	    this.setCoordBaseMode(rotation);
	    this.size = 13;
	    this.height = 61;
	    this.boundingBox = StructureTFComponent.getComponentToAddBoundingBox(x, y, z, -6, 0, -6, 12, 60, 12, 0);

    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void buildComponent(StructureComponent parent, List list, Random rand) {
	    if (parent != null && parent instanceof StructureTFComponent) {
		    this.deco = ((StructureTFComponent)parent).deco;
	    }
	    // add crown
	    ComponentTFFinalCastleRoof9Crenellated roof = new ComponentTFFinalCastleRoof9Crenellated(rand, 4, this);
	    list.add(roof);
	    roof.buildComponent(this, list, rand);
    }

    @Override
    public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		Random decoRNG = new Random(world.getSeed() + (this.boundingBox.minX * 321534781) ^ (this.boundingBox.minZ * 756839));

		fillWithRandomizedBlocks(world, sbb, 0, 0, 0, 12, 59, 12, false, rand, deco.randomBlocks);

        // add branching runes
		int numBranches = 6 + decoRNG.nextInt(4);
		for (int i = 0; i < numBranches; i++) {
			makeGlyphBranches(world, decoRNG, this.getGlyphMeta(), sbb);
		}

	    // beard
	    for (int i = 1; i < 4; i++) {
	        fillWithRandomizedBlocks(world, sbb, i, 0 - (i * 2), i, 8 - i, 1 - (i * 2), 8 - i, false, rand, deco.randomBlocks);
	    }
	    this.setBlockState(world, deco.blockState, 4, -7, 4, sbb);

	    // door, first floor
	    this.fillWithBlocks(world, sbb, 0, 1, 1, 0, 4, 3, TFBlocks.castleDoor, 0, Blocks.AIR, this.getGlyphMeta(), false);

        this.placeSignAtCurrentPosition(world, 6, 1, 6, "Parkour area 1", "Unique monster?", sbb);

	    return true;
    }


	public int getGlyphMeta() {
		return 0;
	}


}
