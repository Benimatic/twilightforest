package twilightforest.structures;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import twilightforest.entity.passive.EntityTFQuestRam;


public class ComponentTFQuestGrove extends StructureTFComponent {
	
	public static final int RADIUS = 13;
	
	protected boolean beastPlaced = false; 
	protected boolean dispenserPlaced = false; 


	public ComponentTFQuestGrove() {
		super();
	}

	public ComponentTFQuestGrove(World world, Random rand, int i, int x, int y, int z) {
		super(i);
		
		this.setCoordBaseMode(0);
		
		// the maze is 25 x 25 for now
		this.boundingBox = StructureTFComponent.getComponentToAddBoundingBox(x, y, z, -RADIUS, 0, -RADIUS, RADIUS * 2, 10, RADIUS * 2, 0);
	}

	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		for (int i = 0; i < 4; i++) {
			// make the rings
			makeWallSide(world, rand, i, sbb);
		}
		
		// a small platform
		for (int x = 10; x < 17; x++) {
			for (int z = 10; z < 17; z++) {
				if (x == 10 || x == 16 || z == 10 || z == 16) {
					if (rand.nextInt(2) > 0) {
						placeBlockAtCurrentPosition(world, Blocks.stonebrick, 1, x, -1, z, sbb);
					}
				}
				else if (x == 11 || x == 15 || z == 11 || z == 15) {
					if (rand.nextInt(3) > 0) {
						placeBlockAtCurrentPosition(world, Blocks.stonebrick, 1, x, -1, z, sbb);
					}				
				}
				else {
					placeBlockAtCurrentPosition(world, Blocks.stonebrick, 1, x, -1, z, sbb);
				}
			}
		}
		
		// dispenser frame and button
		placeBlockAtCurrentPosition(world, Blocks.stone_button, 4, 13, 5, 19, sbb);
		
		placeBlockAtCurrentPosition(world, Blocks.stonebrick, 1, 12, 7, 20, sbb);
		placeBlockAtCurrentPosition(world, Blocks.stonebrick, 1, 13, 7, 20, sbb);
		placeBlockAtCurrentPosition(world, Blocks.stonebrick, 1, 14, 7, 20, sbb);
		placeBlockAtCurrentPosition(world, Blocks.stonebrick, 1, 12, 7, 21, sbb);
		placeBlockAtCurrentPosition(world, Blocks.stonebrick, 1, 13, 7, 21, sbb);
		placeBlockAtCurrentPosition(world, Blocks.stonebrick, 1, 14, 7, 21, sbb);
		
		
		// the dispenser
		if (!dispenserPlaced) {
			int bx = this.getXWithOffset(13, 20);
			int by = this.getYWithOffset(6);
			int bz = this.getZWithOffset(13, 20);
			
			if (sbb.isVecInside(bx, by, bz)) {
				dispenserPlaced = true;
				
				world.setBlock(bx, by, bz, Blocks.dispenser, 2, 4);
				TileEntityDispenser ted = (TileEntityDispenser)world.getTileEntity(bx, by, bz);
				
				// add 4 random wool blocks
				for (int i = 0; i < 4; i++) {
					ted.setInventorySlotContents(i, new ItemStack(Blocks.wool, 1, rand.nextInt(16)));
				}
				
			}
		}
		
		// the quest beast!
		if (!beastPlaced) {
			int bx = this.getXWithOffset(13, 13);
			int by = this.getYWithOffset(0);
			int bz = this.getZWithOffset(13, 13);
			
			if (sbb.isVecInside(bx, by, bz)) {
				beastPlaced = true;
				
				EntityTFQuestRam ram = new EntityTFQuestRam(world);
				ram.setPosition(bx, by, bz);
				ram.setHomeArea(bx, by, bz, 13);
				
				world.spawnEntityInWorld(ram);
			}
		}
		
		return true;
	}

	private void makeWallSide(World world, Random rand, int direction, StructureBoundingBox sbb) {
		int temp = this.getCoordBaseMode();
		this.setCoordBaseMode(direction);
		
		// arches
		placeOuterArch(world, 3, -1, sbb);
		placeOuterArch(world, 11, -1, sbb);
		placeOuterArch(world, 19, -1, sbb);
		
		// connecting thingers
		placeBlockAtCurrentPosition(world, Blocks.stonebrick, 3, 0, 0, 0, sbb);
		placeBlockAtCurrentPosition(world, Blocks.stonebrick, 3, 0, 1, 0, sbb);
		placeBlockAtCurrentPosition(world, Blocks.stonebrick, 3, 0, 2, 0, sbb);

		placeBlockAtCurrentPosition(world, Blocks.stonebrick, 3, 0, 3, 0, sbb);
		placeBlockAtCurrentPosition(world, Blocks.stonebrick, 3, 1, 3, 0, sbb);
		placeBlockAtCurrentPosition(world, Blocks.stonebrick, 3, 2, 3, 0, sbb);

		placeBlockAtCurrentPosition(world, Blocks.stonebrick, 3, 8, 3, 0, sbb);
		placeBlockAtCurrentPosition(world, Blocks.stonebrick, 3, 9, 3, 0, sbb);
		placeBlockAtCurrentPosition(world, Blocks.stonebrick, 3, 10, 3, 0, sbb);

		placeBlockAtCurrentPosition(world, Blocks.stonebrick, 3, 16, 3, 0, sbb);
		placeBlockAtCurrentPosition(world, Blocks.stonebrick, 3, 17, 3, 0, sbb);
		placeBlockAtCurrentPosition(world, Blocks.stonebrick, 3, 18, 3, 0, sbb);

		placeBlockAtCurrentPosition(world, Blocks.stonebrick, 3, 24, 3, 0, sbb);
		placeBlockAtCurrentPosition(world, Blocks.stonebrick, 3, 25, 3, 0, sbb);
		
		// inner arch
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				for (int z = 0; z < 2; z++) {
					if (x == 0 || x == 1 || x == 7 || x == 8 || y == 0 || y == 1 || y == 7 || y == 8) {
						placeBlockAtCurrentPosition(world, Blocks.stonebrick, 1, x + 9, y - 2, z + 5, sbb);
					}
				}
			}
		}
		
		// connecting thingers
		placeBlockAtCurrentPosition(world, Blocks.stonebrick, 3, 6, 0, 6, sbb);
		placeBlockAtCurrentPosition(world, Blocks.stonebrick, 3, 6, 1, 6, sbb);
		placeBlockAtCurrentPosition(world, Blocks.stonebrick, 3, 6, 2, 6, sbb);
		placeBlockAtCurrentPosition(world, Blocks.stonebrick, 3, 6, 3, 6, sbb);
		
		placeBlockAtCurrentPosition(world, Blocks.stonebrick, 3, 6, 4, 6, sbb);
		placeBlockAtCurrentPosition(world, Blocks.stonebrick, 3, 7, 4, 6, sbb);
		placeBlockAtCurrentPosition(world, Blocks.stonebrick, 3, 8, 4, 6, sbb);

		placeBlockAtCurrentPosition(world, Blocks.stonebrick, 3, 18, 4, 6, sbb);
		placeBlockAtCurrentPosition(world, Blocks.stonebrick, 3, 19, 4, 6, sbb);
		
		this.setCoordBaseMode(temp);
	}

	private void placeOuterArch(World world, int ox, int oy, StructureBoundingBox sbb) {
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 6; y++) {
				if (x == 0 || x == 4 || y == 0 || y == 5) {
					placeBlockAtCurrentPosition(world, Blocks.stonebrick, 1, x + ox, y + oy, 0, sbb);
				}
			}
		}
	}

}
