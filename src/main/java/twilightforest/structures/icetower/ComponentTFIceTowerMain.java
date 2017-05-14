package twilightforest.structures.icetower;

import java.util.List;
import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class ComponentTFIceTowerMain extends ComponentTFIceTowerWing 
{
	public boolean hasBossWing = false;
	
	public ComponentTFIceTowerMain() {
		super();
	}

	public ComponentTFIceTowerMain(World world, Random rand, int index, int x, int y, int z) {
		this(world, rand, index, x + SIZE, y + 40, z + SIZE, 2);
	}
	
	public ComponentTFIceTowerMain(World world, Random rand, int index, int x, int y, int z, EnumFacing rotation) {
		super(index, x, y, z, SIZE, 31 + (rand.nextInt(3) * 10), rotation);

		// decorator
		if (this.deco == null)
		{
			this.deco = new StructureDecoratorIceTower();
		}
	}

	
	protected ComponentTFIceTowerMain(int i, int x, int y, int z, int pSize, int pHeight, int direction) 
	{
		super(i, x, y, z, pSize, pHeight, direction);
	}

	/**
	 * Save to NBT
	 */
	@Override
	protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
		super.func_143012_a(par1NBTTagCompound);
		
        par1NBTTagCompound.setBoolean("hasBossWing", this.hasBossWing);
	}

	/**
	 * Load from NBT
	 */
	@Override
	protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
		super.func_143011_b(par1NBTTagCompound);
        this.hasBossWing = par1NBTTagCompound.getBoolean("hasBossWing");
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void buildComponent(StructureComponent parent, List list, Random rand) {
		super.buildComponent(parent, list, rand);
		
		// add entrance tower
		StructureBoundingBox towerBB = StructureBoundingBox.getNewBoundingBox();

        for (StructureComponent structurecomponent : (List<StructureComponent>)list) {
        	towerBB.expandTo(structurecomponent.getBoundingBox());
        }
        
        // TODO: make this more general
        BlockPos myDoor = this.openings.get(0);
        BlockPos entranceDoor = new BlockPos(myDoor);
        
        
        if (myDoor.posX == 0) {
        	int length = this.getBoundingBox().minX - towerBB.minX;
			//System.out.println("Offset for door -x = " + length);
        	if (length >= 0) {
	        	entranceDoor.posX -= length;
	    		makeEntranceBridge(list, rand, this.getComponentType() + 1, myDoor.posX, myDoor.posY, myDoor.posZ, length, 2);
        	}
        }
        if (myDoor.posX == this.size - 1) {
        	entranceDoor.posX += towerBB.maxX - this.getBoundingBox().maxX;
        }
        if (myDoor.posZ == 0) {
        	entranceDoor.posZ += towerBB.minZ - this.getBoundingBox().minZ;
        }
        if (myDoor.posX == this.size - 1) {
        	entranceDoor.posZ += towerBB.maxZ - this.getBoundingBox().maxZ;
        }
        
		makeEntranceTower(list, rand, this.getComponentType() + 1, entranceDoor.posX, entranceDoor.posY, entranceDoor.posZ, SIZE, 11, this.getCoordBaseMode());
	}

	private void makeEntranceBridge(List<StructureComponent> list, Random rand, int index, int x, int y, int z, int length, int rotation) {
		EnumFacing direction = (getCoordBaseMode() + rotation) % 4;
		BlockPos dest = offsetTowerCCoords(x, y, z, 5, direction);

		ComponentTFIceTowerBridge bridge = new ComponentTFIceTowerBridge(index, dest.posX, dest.posY, dest.posZ, length, direction);

		list.add(bridge);
		bridge.buildComponent(list.get(0), list, rand);
	}

	public boolean makeEntranceTower(List<StructureComponent> list, Random rand, int index, int x, int y, int z, int wingSize, int wingHeight, int rotation) {
		EnumFacing direction = (getCoordBaseMode() + rotation) % 4;
		int[] dx = offsetTowerCoords(x, y, z, wingSize, direction);

		ComponentTFIceTowerWing entrance = new ComponentTFIceTowerEntrance(index, dx[0], dx[1], dx[2], wingSize, wingHeight, direction);

		list.add(entrance);
		entrance.buildComponent(list.get(0), list, rand);
		addOpening(x, y, z, rotation);
		return true;
	}

}
