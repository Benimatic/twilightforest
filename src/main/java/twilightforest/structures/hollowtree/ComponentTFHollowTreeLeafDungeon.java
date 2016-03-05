package twilightforest.structures.hollowtree;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import twilightforest.TFTreasure;
import twilightforest.block.TFBlocks;
import twilightforest.entity.TFCreatures;
import twilightforest.structures.StructureTFComponent;


/**
 * A blob of leaves used to make trees 
 * 
 * @author Ben
 *
 */
public class ComponentTFHollowTreeLeafDungeon extends StructureTFComponent {
	
	int radius; // radius

	
	public ComponentTFHollowTreeLeafDungeon() {
		super();
	}

	/**
	 * Make a blob of leaves
	 * 
	 * @param index
	 * @param x
	 * @param y
	 * @param z
	 * @param radius
	 */
	protected ComponentTFHollowTreeLeafDungeon(int index, int x, int y, int z, int radius) {
		super(index);
		
		this.setCoordBaseMode(0);
		
		boundingBox = new StructureBoundingBox(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius);
		this.radius = radius;
	}
	
	/**
	 * Save to NBT
	 */
	@Override
	protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
		super.func_143012_a(par1NBTTagCompound);
		
        par1NBTTagCompound.setInteger("leafRadius", this.radius);

	}

	/**
	 * Load from NBT
	 */
	@Override
	protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
		super.func_143011_b(par1NBTTagCompound);

        this.radius = par1NBTTagCompound.getInteger("leafRadius");
	}
	
	/**
	 * Add other structure components to this one if needed
	 */
	@Override
	public void buildComponent(StructureComponent structurecomponent, List list, Random rand) {
		// the bounding box should be cubical, so we can rotate freely
		this.setCoordBaseMode(rand.nextInt(4));
	}

	/**
	 * Draw a giant blob of whatevs (okay, it's going to be leaves).
	 */
	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		
		// leaves on the outside
		drawBlockBlob(world, sbb, radius, radius, radius, 4, TFBlocks.leaves, 0, true);
		// then wood
		drawBlockBlob(world, sbb, radius, radius, radius, 3, TFBlocks.log, 12, false);
		// then air
		drawBlockBlob(world, sbb, radius, radius, radius, 2, Blocks.air, 0, false);
		
		// then treasure chest
		// which direction is this chest in?
		this.placeTreasureAtCurrentPosition(world, rand, radius + 2, radius - 1, radius, TFTreasure.tree_cache, sbb);
			
		// then spawner
		placeSpawnerAtCurrentPosition(world, rand, radius, radius, radius, TFCreatures.getSpawnerNameFor("Swarm Spider"), sbb);
		
		return true;
	}

	private void drawBlockBlob(World world, StructureBoundingBox sbb, int sx, int sy, int sz, int blobRadius, Block blockID, int metadata, boolean isLeaves) {
		// then trace out a quadrant
		for (byte dx = 0; dx <= blobRadius; dx++)
		{
			for (byte dy = 0; dy <= blobRadius; dy++)
			{
				for (byte dz = 0; dz <= blobRadius; dz++)
				{
					// determine how far we are from the center.
					byte dist = 0;

					if (dx >= dy && dx >= dz) {
						dist = (byte) (dx + (byte)((Math.max(dy, dz) * 0.5) + (Math.min(dy, dz) * 0.25)));
					} else if (dy >= dx && dy >= dz)
					{
						dist = (byte) (dy + (byte)((Math.max(dx, dz) * 0.5) + (Math.min(dx, dz) * 0.25)));
					} else {
						dist = (byte) (dz + (byte)((Math.max(dx, dy) * 0.5) + (Math.min(dx, dy) * 0.25)));
					}


					// if we're inside the blob, fill it
					if (dist <= blobRadius) {
						// do eight at a time for easiness!
						if (isLeaves)
						{
							placeLeafBlock(world, blockID, metadata, sx + dx, sy + dy, sz + dz, sbb);
							placeLeafBlock(world, blockID, metadata, sx + dx, sy + dy, sz - dz, sbb);
							placeLeafBlock(world, blockID, metadata, sx - dx, sy + dy, sz + dz, sbb);
							placeLeafBlock(world, blockID, metadata, sx - dx, sy + dy, sz - dz, sbb);
							placeLeafBlock(world, blockID, metadata, sx + dx, sy - dy, sz + dz, sbb);
							placeLeafBlock(world, blockID, metadata, sx + dx, sy - dy, sz - dz, sbb);
							placeLeafBlock(world, blockID, metadata, sx - dx, sy - dy, sz + dz, sbb);
							placeLeafBlock(world, blockID, metadata, sx - dx, sy - dy, sz - dz, sbb);
						}
						else
						{
							this.placeBlockAtCurrentPosition(world, blockID, metadata, sx + dx, sy + dy, sz + dz, sbb);
							this.placeBlockAtCurrentPosition(world, blockID, metadata, sx + dx, sy + dy, sz - dz, sbb);
							this.placeBlockAtCurrentPosition(world, blockID, metadata, sx - dx, sy + dy, sz + dz, sbb);
							this.placeBlockAtCurrentPosition(world, blockID, metadata, sx - dx, sy + dy, sz - dz, sbb);
							this.placeBlockAtCurrentPosition(world, blockID, metadata, sx + dx, sy - dy, sz + dz, sbb);
							this.placeBlockAtCurrentPosition(world, blockID, metadata, sx + dx, sy - dy, sz - dz, sbb);
							this.placeBlockAtCurrentPosition(world, blockID, metadata, sx - dx, sy - dy, sz + dz, sbb);
							this.placeBlockAtCurrentPosition(world, blockID, metadata, sx - dx, sy - dy, sz - dz, sbb);

						}

					}
				}
			}
		}
	}

	/**
	 * Puts a block only if leaves can go there.
	 */
	protected void placeLeafBlock(World world, Block blockID, int meta, int x, int y, int z, StructureBoundingBox sbb) {

		int offX = this.getXWithOffset(x, z);
		int offY = this.getYWithOffset(y);
		int offZ = this.getZWithOffset(x, z);

		if (sbb.isVecInside(offX, offY, offZ))
		{
			Block whatsThere = world.getBlock(offX, offY, offZ);

			if (whatsThere == null || whatsThere.canBeReplacedByLeaves(world, offX, offY, offZ))
			{
				world.setBlock(offX, offY, offZ, blockID, meta, 2);
			}
		}
	}

}
