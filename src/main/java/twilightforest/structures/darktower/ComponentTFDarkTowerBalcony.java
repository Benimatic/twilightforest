package twilightforest.structures.darktower;

import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import twilightforest.structures.StructureTFComponent;
import twilightforest.structures.lichtower.ComponentTFTowerWing;

public class ComponentTFDarkTowerBalcony extends ComponentTFTowerWing
{
	
	public ComponentTFDarkTowerBalcony() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected ComponentTFDarkTowerBalcony(int i, int x, int y, int z, int direction) {
		super(i, x, y, z, 5, 5, direction);
	}

	@SuppressWarnings("rawtypes")
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

		// make floor
		fillWithMetadataBlocks(world, sbb, 0, 0, 0, 2, 0, 4, deco.accentID, deco.accentMeta, Blocks.AIR, 0, false);
		fillWithMetadataBlocks(world, sbb, 0, 0, 1, 1, 0, 3, deco.blockID, deco.blockMeta, Blocks.AIR, 0, false);
		
		fillWithMetadataBlocks(world, sbb, 0, 1, 0, 2, 1, 4, deco.fenceID, deco.fenceMeta, Blocks.AIR, 0, false);

		this.placeBlockAtCurrentPosition(world, deco.accentID, deco.accentMeta, 2, 1, 0, sbb);
		this.placeBlockAtCurrentPosition(world, deco.accentID, deco.accentMeta, 2, 1, 4, sbb);

		// clear inside
		fillWithAir(world, sbb, 0, 1, 1, 1, 1, 3);
		
		return true;
	}


}
