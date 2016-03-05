package twilightforest.structures.trollcave;

import java.util.List;
import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import twilightforest.block.TFBlocks;
import twilightforest.entity.EntityTFArmoredGiant;
import twilightforest.entity.EntityTFGiantMiner;
import twilightforest.structures.StructureTFComponent;

public class ComponentTFCloudCastle extends StructureTFComponent {

	private boolean minerPlaced = false;
	private boolean warriorPlaced = false;

	public ComponentTFCloudCastle() { }
	
	public ComponentTFCloudCastle(int index, int x, int y, int z) {
		super(index);
		this.setCoordBaseMode(0);

		
		// adjust x, y, z
    	x = (x >> 2) << 2;
    	y = (y >> 2) << 2;
    	z = (z >> 2) << 2;
    	
		// spawn list!
		this.spawnListIndex = 1;
    	
		this.boundingBox = StructureTFComponent.getComponentToAddBoundingBox(x, y, z, -8, 0, -8, 16, 16, 16, 0);
	}
	
	/**
	 * Save to NBT
	 */
	@Override
	protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
		super.func_143012_a(par1NBTTagCompound);
		
        par1NBTTagCompound.setBoolean("minerPlaced", this.minerPlaced);
        par1NBTTagCompound.setBoolean("warriorPlaced", this.warriorPlaced);
	}

	/**
	 * Load from NBT
	 */
	@Override
	protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
		super.func_143011_b(par1NBTTagCompound);
        this.minerPlaced = par1NBTTagCompound.getBoolean("minerPlaced");
        this.warriorPlaced = par1NBTTagCompound.getBoolean("warriorPlaced");

	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void buildComponent(StructureComponent parent, List list, Random rand) {
		// up to two trees
		// tree in x direction
		boolean plus = rand.nextBoolean();
		int offset = rand.nextInt(5) - rand.nextInt(5);
		ComponentTFCloudTree treeX = new ComponentTFCloudTree(this.getComponentType() + 1, boundingBox.minX + (plus ? 16 : -16), 168, boundingBox.minZ - 8 + (offset * 4));
		list.add(treeX);
		treeX.buildComponent(this, list, rand);

		// tree in z direction
		plus = rand.nextBoolean();
		offset = rand.nextInt(5) - rand.nextInt(5);
		ComponentTFCloudTree treeZ = new ComponentTFCloudTree(this.getComponentType() + 1, boundingBox.minX - 8 + (offset * 4), 168, boundingBox.minZ + (plus ? 16 : -16));
		list.add(treeZ);
		treeZ.buildComponent(this, list, rand);

	}
	
	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		// make haus
		this.fillWithMetadataBlocks(world, sbb, 0, -4, 0, 15, -1, 15, TFBlocks.fluffyCloud, 0,TFBlocks.fluffyCloud, 0, false); 
		this.fillWithMetadataBlocks(world, sbb, 0, 0, 0, 15, 11, 15, TFBlocks.giantCobble, 0, TFBlocks.giantCobble, 0, false); 
		this.fillWithMetadataBlocks(world, sbb, 0, 12, 0, 15, 15, 15, TFBlocks.giantLog, 0, TFBlocks.giantLog, 0, false); 
		
		
		// clear inside
		this.fillWithAir(world, sbb, 4, 0, 4, 11, 11, 11);

		// clear door
		this.fillWithAir(world, sbb, 0, 0, 4, 4, 7, 7);
		
		
		// add giants
		if (!this.minerPlaced) {
			int bx = this.getXWithOffset(6, 6);
			int by = this.getYWithOffset(0);
			int bz = this.getZWithOffset(6, 6);
			
			if (sbb.isVecInside(bx, by, bz)) {
				this.minerPlaced = true;
				
				EntityTFGiantMiner miner = new EntityTFGiantMiner(world);
				miner.setPosition(bx, by, bz);
				miner.makeNonDespawning();
				
				world.spawnEntityInWorld(miner);
			}
		}
		if (!this.warriorPlaced) {
			int bx = this.getXWithOffset(9, 9);
			int by = this.getYWithOffset(0);
			int bz = this.getZWithOffset(9, 9);
			
			if (sbb.isVecInside(bx, by, bz)) {
				this.warriorPlaced = true;
				
				EntityTFArmoredGiant warrior = new EntityTFArmoredGiant(world);
				warrior.setPosition(bx, by, bz);
				warrior.makeNonDespawning();
				
				world.spawnEntityInWorld(warrior);
			}
		}
		
		return true;
	}

}
