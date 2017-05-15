package twilightforest.structures.mushroomtower;

import java.util.List;
import java.util.Random;

import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import twilightforest.structures.StructureTFComponent;
import twilightforest.structures.lichtower.ComponentTFTowerRoof;
import twilightforest.structures.lichtower.ComponentTFTowerWing;

public class ComponentTFTowerRoofMushroom extends ComponentTFTowerRoof {

	public ComponentTFTowerRoofMushroom(int i, ComponentTFTowerWing wing, float pHang) {
		super(i, wing);

		this.height = wing.size;
		
		int overhang = (int)(wing.size * pHang);
		
		this.size = wing.size + (overhang * 2);
		
		this.setCoordBaseMode(0);
		
		this.boundingBox = new StructureBoundingBox(wing.getBoundingBox().minX - overhang, wing.getBoundingBox().maxY + 2, wing.getBoundingBox().minZ - overhang, wing.getBoundingBox().maxX + overhang, wing.getBoundingBox().maxY + this.height + 1, wing.getBoundingBox().maxZ + overhang);

	}
	
	@Override
	public void buildComponent(StructureComponent parent, List list, Random rand) {
		if (parent != null && parent instanceof StructureTFComponent)
		{
			this.deco = ((StructureTFComponent)parent).deco;
		}
	}

	public ComponentTFTowerRoofMushroom() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Makes a pointy roof out of stuff
	 */
	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		
		for (int y = 0; y <= height; y++) {
			
			int radius = (int)(MathHelper.sin((y + height/1.2F) / (height * 2.05F) * 3.14F) * this.size / 2F);
			int hollow = MathHelper.floor_float(radius * .9F);
			
			if ((height - y) < 3)
			{
				hollow = -1;
			}

			makeCircle(world, y, radius, hollow, sbb);
		}   
		
        return true;
	}

	private void makeCircle(World world, int y, int radius, int hollow, StructureBoundingBox sbb) {
		
		int cx = size / 2;
		int cz = size / 2;

		// build the trunk upwards
		for (int dx = -radius; dx <= radius; dx++)
		{
			for (int dz = -radius; dz <= radius; dz++)
			{
				// determine how far we are from the center.
//				int ax = Math.abs(dx);
//				int az = Math.abs(dz);
				float dist = MathHelper.sqrt_float(dx * dx + dz * dz);

				// make a trunk!
				if (dist <= (radius + 0.5F)) 
				{
					if (dist > hollow)
					{
						setBlockState(world, deco.accentID, deco.accentMeta, dx + cx, y, dz + cz, sbb);
					}
					else
					{
						// spore
						setBlockState(world, deco.accentID, 0, dx + cx, y, dz + cz, sbb);

					}
				}
			}
		}
	}

}
