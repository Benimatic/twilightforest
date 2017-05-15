package twilightforest.structures.icetower;

import java.util.List;
import java.util.Random;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import twilightforest.structures.StructureTFComponent;

public class ComponentTFIceTowerEntrance extends ComponentTFIceTowerWing {

	public ComponentTFIceTowerEntrance() {}

	public ComponentTFIceTowerEntrance(int i, int x, int y, int z, int pSize, int pHeight, EnumFacing direction) {
		super(i, x, y, z, pSize, pHeight, direction);
	}
	
	
	@Override
	protected boolean shouldHaveBase(Random rand) {
		return true;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void buildComponent(StructureComponent parent, List list, Random rand) {
		if (parent != null && parent instanceof StructureTFComponent)
		{
			this.deco = ((StructureTFComponent)parent).deco;
		}
		
		// we should have a door where we started
		addOpening(0, 1, size / 2, 2);

		// stairs
		addStairs(list, rand, this.getComponentType() + 1, this.size - 1, 1, size / 2, 0);
		addStairs(list, rand, this.getComponentType() + 1, this.size / 2, 1, 0, 3);
		addStairs(list, rand, this.getComponentType() + 1, this.size / 2, 1, this.size - 1, 1);

		// should we build a base
		this.hasBase = this.shouldHaveBase(rand);

		// add a roof?
		makeARoof(parent, list, rand);
	}

	/**
	 * Add some stairs leading to this tower
	 */
	private boolean addStairs(List<StructureComponent> list, Random rand, int index, int x, int y, int z, int rotation) {
		// add door
		this.addOpening(x, y, z, rotation);

		EnumFacing direction = getStructureRelativeRotation(rotation);
		BlockPos dx = offsetTowerCCoords(x, y, z, this.size, direction);

		ComponentTFIceTowerStairs entrance = new ComponentTFIceTowerStairs(index, dx.posX, dx.posY, dx.posZ, this.size, this.height, direction);

		list.add(entrance);
		entrance.buildComponent(list.get(0), list, rand);
		return true;
	}

	/**
	 * Make a new wing
	 */
	@Override
	public boolean makeTowerWing(List<StructureComponent> list, Random rand, int index, int x, int y, int z, int wingSize, int wingHeight, int rotation) {
		return false;
	}
	
	/**
	 * No floors
	 */
	@Override
	protected void makeFloorsForTower(World world, Random rand, StructureBoundingBox sbb) {
		
		decoratePillarsCornersHigh(world, rand, 0, 11, 0, sbb);

	}

	
	protected void decoratePillarsCornersHigh(World world, Random rand, int bottom, int top, int rotation, StructureBoundingBox sbb) {
		int beamMetaNS = ((this.coordBaseMode + rotation) % 2 == 0) ? 4 : 8;
		int beamMetaEW = (beamMetaNS == 4) ? 8 : 4;
		
		this.fillBlocksRotated(world, sbb, 3, bottom + 5, 1, 3, bottom + 5, 9, deco.pillarID, deco.pillarMeta + beamMetaEW, rotation);
		this.fillBlocksRotated(world, sbb, 7, bottom + 5, 1, 7, bottom + 5, 9, deco.pillarID, deco.pillarMeta + beamMetaEW, rotation);
		this.fillBlocksRotated(world, sbb, 1, bottom + 5, 3, 9, bottom + 5, 3, deco.pillarID, deco.pillarMeta + beamMetaNS, rotation);
		this.fillBlocksRotated(world, sbb, 1, bottom + 5, 7, 9, bottom + 5, 7, deco.pillarID, deco.pillarMeta + beamMetaNS, rotation);
		
		this.fillAirRotated(world, sbb, 3, bottom + 5, 3, 7, bottom + 5, 7, rotation);
		
		// pillars connected only to ceiling
		this.fillBlocksRotated(world, sbb, 3, bottom + 5, 3, 3, top - 1, 3, deco.pillarID, deco.pillarMeta, rotation);
		this.fillBlocksRotated(world, sbb, 7, bottom + 5, 3, 7, top - 1, 3, deco.pillarID, deco.pillarMeta, rotation);
		this.fillBlocksRotated(world, sbb, 3, bottom + 5, 7, 3, top - 1, 7, deco.pillarID, deco.pillarMeta, rotation);
		this.fillBlocksRotated(world, sbb, 7, bottom + 5, 7, 7, top - 1, 7, deco.pillarID, deco.pillarMeta, rotation);
	}
}
