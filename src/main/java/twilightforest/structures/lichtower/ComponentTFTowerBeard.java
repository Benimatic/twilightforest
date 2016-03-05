package twilightforest.structures.lichtower;

import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import twilightforest.structures.StructureTFComponent;


public class ComponentTFTowerBeard extends StructureTFComponent {

	int size;
	int height;

	public ComponentTFTowerBeard() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ComponentTFTowerBeard(int i, ComponentTFTowerWing wing) {
		super(i);
		
		this.setCoordBaseMode(wing.getCoordBaseMode());
		this.size = wing.size - 2;
		this.height = size / 2;
		
		// just hang out at the very bottom of the tower
		this.boundingBox = new StructureBoundingBox(wing.getBoundingBox().minX + 1, wing.getBoundingBox().minY - this.height - 1, wing.getBoundingBox().minZ + 1, wing.getBoundingBox().maxX - 1, wing.getBoundingBox().minY - 1, wing.getBoundingBox().maxZ - 1);

	}
	
	/**
	 * Save to NBT
	 */
	@Override
	protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
		super.func_143012_a(par1NBTTagCompound);
		
        par1NBTTagCompound.setInteger("beardSize", this.size);
        par1NBTTagCompound.setInteger("beardHeight", this.height);
	}
	
	/**
	 * Load from NBT
	 */
	@Override
	protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
		super.func_143011_b(par1NBTTagCompound);
        this.size = par1NBTTagCompound.getInteger("beardSize");
        this.height = par1NBTTagCompound.getInteger("beardHeight");
	}

	/**
	 * Makes a pyramid-shaped beard
	 */
	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		return makePyramidBeard(world, rand, sbb);
	}

	private boolean makePyramidBeard(World world, Random rand, StructureBoundingBox sbb) {
		for (int y = 0; y <= height ; y++) {
			int min = y;
			int max = size - y - 1;
			
			fillWithRandomizedBlocks(world, sbb, min, height - y, min, max, height - y, max, false, rand, StructureTFComponent.getStrongholdStones());
		}        
        return true;
	}


}
