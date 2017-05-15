package twilightforest.structures.icetower;

import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import twilightforest.structures.StructureTFComponent;
import twilightforest.structures.lichtower.ComponentTFTowerWing;

public class ComponentTFIceTowerBeard extends StructureTFComponent {

	protected int size;
	protected int height;
	
	public ComponentTFIceTowerBeard() {}
	
	public ComponentTFIceTowerBeard(int i, ComponentTFTowerWing wing) {
		super(i);
		
		// same alignment
		this.setCoordBaseMode(wing.getCoordBaseMode());
		// same size
		this.size = wing.size; // assuming only square towers and roofs right now.
		this.height = Math.round(this.size * 1.414F);
		
		this.deco = wing.deco;
		
		// just hang out at the very bottom of the tower
		this.boundingBox = new StructureBoundingBox(wing.getBoundingBox().minX, wing.getBoundingBox().minY - this.height, wing.getBoundingBox().minZ, wing.getBoundingBox().maxX, wing.getBoundingBox().minY, wing.getBoundingBox().maxZ);
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
	 * Makes a dark tower type beard
	 */
	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) 
	{
		for (int x = 0; x < this.size; x++) {
			for (int z = 0; z < this.size; z++) {
				//int rHeight = this.size - (int) MathHelper.sqrt_float(x * z); // interesting office building pattern
				int rHeight = Math.round(MathHelper.sqrt_float(x * x + z * z));
				//int rHeight = MathHelper.ceiling_float_int(Math.min(x * x / 9F, z * z / 9F));
				
				for (int y = 0; y < rHeight; y++) {
					this.setBlockState(world, deco.blockID, deco.blockMeta, x, this.height - y, z, sbb);

				}
			}
		}
        return true;
	}

}
