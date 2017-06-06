package twilightforest.structures.finalcastle;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import twilightforest.block.TFBlocks;
import twilightforest.structures.StructureTFComponent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ComponentTFFinalCastleDamagedTower extends ComponentTFFinalCastleMazeTower13
{

	public ComponentTFFinalCastleDamagedTower(Random rand, int i, int x, int y, int z, int direction) {
		super(rand, i, x, y, z, 2, direction);  //TODO: change rune type
	}

    @Override
    public void buildComponent(StructureComponent parent, List<StructureComponent> list, Random rand) {
	    if (parent != null && parent instanceof StructureTFComponent) {
		    this.deco = ((StructureTFComponent)parent).deco;
	    }

	    // add foundation
	    ComponentTFFinalCastleFoundation13 foundation = new ComponentTFFinalCastleFoundation13(rand, 0, this);
	    list.add(foundation);
	    foundation.buildComponent(this, list, rand);

	    // add thorns
	    ComponentTFFinalCastleFoundation13 thorns = new ComponentTFFinalCastleFoundation13Thorns(rand, 0, this);
	    list.add(thorns);
	    thorns.buildComponent(this, list, rand);

//    		// add roof
//    		StructureTFComponent roof = rand.nextBoolean() ? new Roof13Conical(rand, 4, this) :  new Roof13Crenellated(rand, 4, this);
//    		list.add(roof);
//    		roof.buildComponent(this, list, rand);


	    // keep on building?
	    this.buildNonCriticalTowers(parent, list, rand);
    }


	@Override
	protected ComponentTFFinalCastleMazeTower13 makeNewDamagedTower(Random rand, int direction, BlockPos tc) {
		return new ComponentTFFinalCastleWreckedTower(rand, this.getComponentType() + 1, tc.getX(), tc.getY(), tc.getZ(), direction);
	}



	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		super.addComponentParts(world, rand, sbb);
		Random decoRNG = new Random(world.getSeed() + (this.boundingBox.minX * 321534781) ^ (this.boundingBox.minZ * 756839));

		this.destroyTower(world, decoRNG, sbb);

		return true;
	}

	public void destroyTower(World world, Random rand, StructureBoundingBox sbb) {

		// make list of destroyed areas
		ArrayList<DestroyArea> areas = makeInitialDestroyList(rand);

		boolean hitDeadRock = false;

		// go down from the top of the tower to the ground, taking out rectangular chunks
		//for (int y = this.boundingBox.maxY; y > this.boundingBox.minY; y--) {
		for (int y = this.boundingBox.maxY; !hitDeadRock && y > 64; y--) {
			for (int x = this.boundingBox.minX - 2; x <= this.boundingBox.maxX + 2; x++) {
				for (int z = this.boundingBox.minZ - 2; z <= this.boundingBox.maxZ + 2; z++) {
					if (sbb.isVecInside(x, y, z)) {
						if (world.getBlock(x, y, z) == TFBlocks.deadrock) {
							hitDeadRock = true;
						}
						determineBlockDestroyed(world, areas, y, x, z);
					}
				}
			}

			// check to see if any of our DestroyAreas are entirely above the current y value
			DestroyArea removeArea = null;

			for (DestroyArea dArea : areas) {
				if (dArea == null || dArea.isEntirelyAbove(y)) {
					removeArea = dArea;
				}
			}
			// if so, replace them with new ones
			if (removeArea != null) {
				areas.remove(removeArea);
				areas.add(DestroyArea.createNonIntersecting(this.getBoundingBox(), rand, y, areas));

			}
		}
	}

	protected ArrayList<DestroyArea> makeInitialDestroyList(Random rand) {
		ArrayList<DestroyArea> areas = new ArrayList<DestroyArea>(2);

		areas.add(DestroyArea.createNonIntersecting(this.getBoundingBox(), rand, this.getBoundingBox().maxY - 1, areas));
		areas.add(DestroyArea.createNonIntersecting(this.getBoundingBox(), rand, this.getBoundingBox().maxY - 1, areas));
		areas.add(DestroyArea.createNonIntersecting(this.getBoundingBox(), rand, this.getBoundingBox().maxY - 1, areas));
		return areas;
	}

	protected void determineBlockDestroyed(World world, ArrayList<DestroyArea> areas, int y, int x, int z) {
		for (DestroyArea dArea : areas) {
			if (dArea != null && dArea.isVecInside(x, y, z)) {
				world.setBlockToAir(x, y, z);
			}
		}
	}

}
