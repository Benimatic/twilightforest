package twilightforest.structures.minotaurmaze;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import twilightforest.TFTreasure;
import twilightforest.entity.boss.EntityTFMinoshroom;

public class ComponentTFMazeRoomBoss extends ComponentTFMazeRoom {


	public ComponentTFMazeRoomBoss() {
		super();
		// TODO Auto-generated constructor stub
	}


	private boolean taurPlaced = false;


	public ComponentTFMazeRoomBoss(int i, Random rand, int x, int y, int z) {
		super(i, rand, x, y, z);
	}


	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		// doorways
		if (this.getBlockAtCurrentPosition(world, 7, 1, 0, sbb) == Blocks.AIR)
		{
			fillWithBlocks(world, sbb, 6, 1, 0, 9, 4, 0, Blocks.FENCE, Blocks.AIR, false);
		}

		if (this.getBlockAtCurrentPosition(world, 7, 1, 15, sbb) == Blocks.AIR)
		{
			fillWithBlocks(world, sbb, 6, 1, 15, 9, 4, 15, Blocks.FENCE, Blocks.AIR, false);
		}

		if (this.getBlockAtCurrentPosition(world, 0, 1, 7, sbb) == Blocks.AIR)
		{
			fillWithBlocks(world, sbb, 0, 1, 6, 0, 4, 9, Blocks.FENCE, Blocks.AIR, false);
		}

		if (this.getBlockAtCurrentPosition(world, 15, 1, 7, sbb) == Blocks.AIR)
		{
			fillWithBlocks(world, sbb, 15, 1, 6, 15, 4, 9, Blocks.FENCE, Blocks.AIR, false);
		}

		// mycelium / small mushrooms on floor
		for (int x = 1; x < 14; x++)
		{
			for (int z = 1; z < 14; z++)
			{
				// calculate distance from middle
				int dist = (int) Math.round(7 / Math.sqrt((7.5 - x) * (7.5 - x) + (7.5 - z) * (7.5 - z)));
				boolean mycelium = rand.nextInt(dist + 1) > 0;
				boolean mushroom = rand.nextInt(dist) > 0;
				boolean mushRed = rand.nextBoolean();

				// make part of the floor mycelium
				if (mycelium)
				{
					this.placeBlockAtCurrentPosition(world, Blocks.MYCELIUM, 0, x, 0, z, sbb);
				}
				// add small mushrooms all over
				if (mushroom)
				{
					this.placeBlockAtCurrentPosition(world, mushRed ? Blocks.RED_MUSHROOM : Blocks.BROWN_MUSHROOM, 0, x, 1, z, sbb);
				}
			}
		}
		
		// mushroom chest shelves in corner
		fillWithMetadataBlocks(world, sbb, 1, 1, 1, 3, 1, 3, Blocks.RED_MUSHROOM_BLOCK, 14, Blocks.AIR, 0, false);
		fillWithMetadataBlocks(world, sbb, 1, 2, 1, 1, 3, 4, Blocks.RED_MUSHROOM_BLOCK, 14, Blocks.AIR, 0, false);
		fillWithMetadataBlocks(world, sbb, 2, 2, 1, 4, 3, 1, Blocks.RED_MUSHROOM_BLOCK, 14, Blocks.AIR, 0, false);
		fillWithMetadataBlocks(world, sbb, 1, 4, 1, 3, 4, 3, Blocks.RED_MUSHROOM_BLOCK, 14, Blocks.AIR, 0, false);
		placeTreasureAtCurrentPosition(world, rand, 3, 2, 3, TFTreasure.labyrinth_room, sbb);

		fillWithMetadataBlocks(world, sbb, 12, 1, 12, 14, 1, 14, Blocks.RED_MUSHROOM_BLOCK, 14, Blocks.AIR, 0, false);
		fillWithMetadataBlocks(world, sbb, 14, 2, 11, 14, 3, 14, Blocks.RED_MUSHROOM_BLOCK, 14, Blocks.AIR, 0, false);
		fillWithMetadataBlocks(world, sbb, 11, 2, 14, 14, 3, 14, Blocks.RED_MUSHROOM_BLOCK, 14, Blocks.AIR, 0, false);
		fillWithMetadataBlocks(world, sbb, 12, 4, 12, 14, 4, 14, Blocks.RED_MUSHROOM_BLOCK, 14, Blocks.AIR, 0, false);
		placeTreasureAtCurrentPosition(world, rand, 12, 2, 12, TFTreasure.labyrinth_room, sbb);

		fillWithMetadataBlocks(world, sbb, 1, 1, 12, 3, 1, 14, Blocks.BROWN_MUSHROOM_BLOCK, 14, Blocks.AIR, 0, false);
		fillWithMetadataBlocks(world, sbb, 1, 2, 11, 1, 3, 14, Blocks.BROWN_MUSHROOM_BLOCK, 14, Blocks.AIR, 0, false);
		fillWithMetadataBlocks(world, sbb, 2, 2, 14, 4, 3, 14, Blocks.BROWN_MUSHROOM_BLOCK, 14, Blocks.AIR, 0, false);
		fillWithMetadataBlocks(world, sbb, 1, 4, 12, 3, 4, 14, Blocks.BROWN_MUSHROOM_BLOCK, 14, Blocks.AIR, 0, false);
		placeTreasureAtCurrentPosition(world, rand, 3, 2, 12, TFTreasure.labyrinth_room, sbb);

		fillWithMetadataBlocks(world, sbb, 12, 1, 1, 14, 1, 3, Blocks.BROWN_MUSHROOM_BLOCK, 14, Blocks.AIR, 0, false);
		fillWithMetadataBlocks(world, sbb, 11, 2, 1, 14, 3, 1, Blocks.BROWN_MUSHROOM_BLOCK, 14, Blocks.AIR, 0, false);
		fillWithMetadataBlocks(world, sbb, 14, 2, 2, 14, 3, 4, Blocks.BROWN_MUSHROOM_BLOCK, 14, Blocks.AIR, 0, false);
		fillWithMetadataBlocks(world, sbb, 12, 4, 1, 14, 4, 3, Blocks.BROWN_MUSHROOM_BLOCK, 14, Blocks.AIR, 0, false);
		placeTreasureAtCurrentPosition(world, rand, 12, 2, 3, TFTreasure.labyrinth_room, sbb);

		// a few more ceilingshrooms
		fillWithMetadataBlocks(world, sbb, 5, 4, 5, 7, 5, 7, Blocks.BROWN_MUSHROOM_BLOCK, 14, Blocks.AIR, 0, false);
		fillWithMetadataBlocks(world, sbb, 8, 4, 8, 10, 5, 10, Blocks.RED_MUSHROOM_BLOCK, 14, Blocks.AIR, 0, false);

		
		// the moo-cen-mino-shrom-taur!
		if (!taurPlaced) {
			int bx = this.getXWithOffset(7, 7);
			int by = this.getYWithOffset(1);
			int bz = this.getZWithOffset(7, 7);
			
			if (sbb.isVecInside(bx, by, bz)) {
				taurPlaced  = true;
				
				EntityTFMinoshroom taur = new EntityTFMinoshroom(world);
				taur.setPosition(bx, by, bz);
				taur.setHomeArea(bx, by, bz, 7);
				
				world.spawnEntityInWorld(taur);
			}
		}
		
		
		return true;
	}
}
