package twilightforest.structures.icetower;

import java.util.List;
import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import twilightforest.structures.StructureTFComponent;

public class ComponentTFIceTowerBridge extends StructureTFComponent {

	private int length;


	public ComponentTFIceTowerBridge() {
		super();
	}

	
	public ComponentTFIceTowerBridge(int index, int x, int y, int z, int length, EnumFacing direction) {
		super(index);
		this.length = length;
		this.setCoordBaseMode(direction);
		
		this.boundingBox = StructureTFComponent.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, length, 6, 5, direction);

	}
	
	/**
	 * Save to NBT
	 */
	@Override
	protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
		super.func_143012_a(par1NBTTagCompound);
		
        par1NBTTagCompound.setInteger("bridgeLength", this.length);
	}
	
	/**
	 * Load from NBT
	 */
	@Override
	protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
		super.func_143011_b(par1NBTTagCompound);
        this.length = par1NBTTagCompound.getInteger("bridgeLength");
	}


	@SuppressWarnings({ "rawtypes" })
	@Override
	public void buildComponent(StructureComponent parent, List list, Random rand) 
	{
		if (parent != null && parent instanceof StructureTFComponent)
		{
			this.deco = ((StructureTFComponent)parent).deco;
		}
	}


	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		fillWithAir(world, sbb, 0, 1, 0, length, 5, 4);
		
		// make floor/ceiling
		fillWithBlocks(world, sbb, 0, 0, 0, length, 0, 4, deco.blockID, deco.blockID, false);
		fillWithBlocks(world, sbb, 0, 6, 0, length, 6, 4, deco.blockID, deco.blockID, false);
		
		// pillars
		for (int x = 2; x < length; x += 3) {
			fillWithMetadataBlocks(world, sbb, x, 1, 0, x, 5, 0, deco.pillarID, deco.pillarMeta, deco.pillarID, deco.pillarMeta, false);
			fillWithMetadataBlocks(world, sbb, x, 1, 4, x, 5, 4, deco.pillarID, deco.pillarMeta, deco.pillarID, deco.pillarMeta, false);
		}
		
		return true;
	}
}
