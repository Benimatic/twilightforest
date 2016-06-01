package twilightforest.world;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;
import twilightforest.entity.TFCreatures;


public class TFGenWitchHut extends TFGenerator {

	/**
	 * Make a cute witch's hut
	 */
	public boolean generate(World world, Random rand, int x, int y, int z)
	{
		return generateTinyHut(world, rand, x, y, z);
	}
	
	/**
	 * Make the smallest size hut
	 */
	public boolean generateTinyHut(World world, Random rand, int x, int y, int z)
	{
		if (!isAreaSuitable(world, rand, x, y, z, 5, 7, 6))
		{
			return false;
		}
		
		// walls!
		
		setBlock(world, x + 1, y + 0, z + 1, randStone(rand, 1));
		setBlock(world, x + 2, y + 0, z + 1, randStone(rand, 1));
		setBlock(world, x + 3, y + 0, z + 1, randStone(rand, 1));
		setBlock(world, x + 5, y + 0, z + 1, randStone(rand, 1));
		setBlock(world, x + 0, y + 0, z + 2, Blocks.brick_block);
		setBlock(world, x + 1, y + 0, z + 2, Blocks.brick_block);
		setBlock(world, x + 5, y + 0, z + 2, randStone(rand, 1));
		setBlock(world, x + 0, y + 0, z + 3, Blocks.brick_block);
		setBlock(world, x + 5, y + 0, z + 3, randStone(rand, 1));
		setBlock(world, x + 0, y + 0, z + 4, Blocks.brick_block);
		setBlock(world, x + 1, y + 0, z + 4, Blocks.brick_block);
		setBlock(world, x + 5, y + 0, z + 4, randStone(rand, 1));
		setBlock(world, x + 1, y + 0, z + 5, randStone(rand, 1));
		setBlock(world, x + 2, y + 0, z + 5, randStone(rand, 1));
		setBlock(world, x + 3, y + 0, z + 5, randStone(rand, 1));
		setBlock(world, x + 5, y + 0, z + 5, randStone(rand, 1));

		setBlock(world, x + 1, y + 1, z + 1, randStone(rand, 2));
		setBlock(world, x + 3, y + 1, z + 1, randStone(rand, 2));
		setBlock(world, x + 5, y + 1, z + 1, randStone(rand, 2));
		setBlock(world, x + 0, y + 1, z + 2, Blocks.brick_block);
		setBlock(world, x + 1, y + 1, z + 2, Blocks.brick_block);
		setBlock(world, x + 5, y + 1, z + 2, randStone(rand, 2));
		setBlock(world, x + 0, y + 1, z + 3, Blocks.brick_block);
		setBlock(world, x + 0, y + 1, z + 4, Blocks.brick_block);
		setBlock(world, x + 1, y + 1, z + 4, Blocks.brick_block);
		setBlock(world, x + 5, y + 1, z + 4, randStone(rand, 2));
		setBlock(world, x + 1, y + 1, z + 5, randStone(rand, 2));
		setBlock(world, x + 3, y + 1, z + 5, randStone(rand, 2));
		setBlock(world, x + 5, y + 1, z + 5, randStone(rand, 2));

		setBlock(world, x + 1, y + 2, z + 1, randStone(rand, 3));
		setBlock(world, x + 2, y + 2, z + 1, randStone(rand, 3));
		setBlock(world, x + 3, y + 2, z + 1, randStone(rand, 3));
		setBlock(world, x + 4, y + 2, z + 1, randStone(rand, 3));
		setBlock(world, x + 5, y + 2, z + 1, randStone(rand, 3));
		setBlock(world, x + 0, y + 2, z + 2, Blocks.brick_block);
		setBlock(world, x + 1, y + 2, z + 2, Blocks.brick_block);
		setBlock(world, x + 5, y + 2, z + 2, randStone(rand, 3));
		setBlock(world, x + 0, y + 2, z + 3, Blocks.brick_block);
		setBlock(world, x + 5, y + 2, z + 3, randStone(rand, 3));
		setBlock(world, x + 0, y + 2, z + 4, Blocks.brick_block);
		setBlock(world, x + 1, y + 2, z + 4, Blocks.brick_block);
		setBlock(world, x + 5, y + 2, z + 4, randStone(rand, 1));
		setBlock(world, x + 1, y + 2, z + 5, randStone(rand, 3));
		setBlock(world, x + 2, y + 2, z + 5, randStone(rand, 3));
		setBlock(world, x + 3, y + 2, z + 5, randStone(rand, 3));
		setBlock(world, x + 4, y + 2, z + 5, randStone(rand, 3));
		setBlock(world, x + 5, y + 2, z + 5, randStone(rand, 3));

		setBlock(world, x + 0, y + 3, z + 2, Blocks.brick_block);
		setBlock(world, x + 0, y + 3, z + 3, Blocks.brick_block);
		setBlock(world, x + 0, y + 3, z + 4, Blocks.brick_block);
		setBlock(world, x + 2, y + 3, z + 1, randStone(rand, 4));
		setBlock(world, x + 3, y + 3, z + 1, randStone(rand, 4));
		setBlock(world, x + 4, y + 3, z + 1, randStone(rand, 4));
		setBlock(world, x + 2, y + 3, z + 5, randStone(rand, 4));
		setBlock(world, x + 3, y + 3, z + 5, randStone(rand, 4));
		setBlock(world, x + 4, y + 3, z + 5, randStone(rand, 4));

		setBlock(world, x + 0, y + 4, z + 3, Blocks.brick_block);
		setBlock(world, x + 3, y + 4, z + 1, randStone(rand, 5));
		setBlock(world, x + 3, y + 4, z + 5, randStone(rand, 5));

		setBlock(world, x + 0, y + 5, z + 3, Blocks.brick_block);

		setBlock(world, x + 0, y + 6, z + 3, Blocks.brick_block);

		// roof!
		setBlockAndMetadata(world, x + 0, y + 2, z + 0, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 0, y + 2, z + 1, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 0, y + 2, z + 5, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 0, y + 2, z + 6, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 6, y + 2, z + 0, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 6, y + 2, z + 1, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 6, y + 2, z + 2, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 6, y + 2, z + 3, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 6, y + 2, z + 4, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 6, y + 2, z + 5, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 6, y + 2, z + 6, Blocks.double_stone_slab, 2);
		
		setBlockAndMetadata(world, x + 1, y + 3, z + 0, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 1, y + 3, z + 1, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 1, y + 3, z + 2, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 1, y + 3, z + 4, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 1, y + 3, z + 5, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 1, y + 3, z + 6, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 5, y + 3, z + 0, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 5, y + 3, z + 1, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 5, y + 3, z + 2, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 5, y + 3, z + 3, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 5, y + 3, z + 4, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 5, y + 3, z + 5, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 5, y + 3, z + 6, Blocks.double_stone_slab, 2);
		
		setBlockAndMetadata(world, x + 1, y + 4, z + 0, Blocks.stone_slab, 2);
		setBlockAndMetadata(world, x + 2, y + 4, z + 0, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 2, y + 4, z + 1, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 2, y + 4, z + 2, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 2, y + 4, z + 3, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 2, y + 4, z + 4, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 2, y + 4, z + 5, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 2, y + 4, z + 6, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 1, y + 4, z + 6, Blocks.stone_slab, 2);
		setBlockAndMetadata(world, x + 5, y + 4, z + 0, Blocks.stone_slab, 2);
		setBlockAndMetadata(world, x + 4, y + 4, z + 0, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 4, y + 4, z + 1, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 4, y + 4, z + 2, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 4, y + 4, z + 3, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 4, y + 4, z + 4, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 4, y + 4, z + 5, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 4, y + 4, z + 6, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 5, y + 4, z + 6, Blocks.stone_slab, 2);

		setBlockAndMetadata(world, x + 2, y + 5, z + 0, Blocks.stone_slab, 2);
		setBlockAndMetadata(world, x + 2, y + 5, z + 1, Blocks.stone_slab, 2);
		setBlockAndMetadata(world, x + 4, y + 5, z + 0, Blocks.stone_slab, 2);
		setBlockAndMetadata(world, x + 4, y + 5, z + 1, Blocks.stone_slab, 2);
		setBlockAndMetadata(world, x + 3, y + 5, z + 0, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 3, y + 5, z + 1, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 3, y + 5, z + 2, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 3, y + 5, z + 3, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 3, y + 5, z + 4, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 3, y + 5, z + 5, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 3, y + 5, z + 6, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 2, y + 5, z + 5, Blocks.stone_slab, 2);
		setBlockAndMetadata(world, x + 2, y + 5, z + 6, Blocks.stone_slab, 2);
		setBlockAndMetadata(world, x + 4, y + 5, z + 5, Blocks.stone_slab, 2);
		setBlockAndMetadata(world, x + 4, y + 5, z + 6, Blocks.stone_slab, 2);

		setBlockAndMetadata(world, x + 3, y + 6, z + 0, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 3, y + 6, z + 1, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 3, y + 6, z + 2, Blocks.stone_slab, 2);
		setBlockAndMetadata(world, x + 3, y + 6, z + 4, Blocks.stone_slab, 2);
		setBlockAndMetadata(world, x + 3, y + 6, z + 5, Blocks.double_stone_slab, 2);
		setBlockAndMetadata(world, x + 3, y + 6, z + 6, Blocks.double_stone_slab, 2);

		setBlockAndMetadata(world, x + 3, y + 7, z + 0, Blocks.stone_slab, 2);
		setBlockAndMetadata(world, x + 3, y + 7, z + 6, Blocks.stone_slab, 2);

		// fire in chimney!
		setBlock(world, x + 1, y - 1, z + 3, Blocks.netherrack);
		setBlock(world, x + 1, y + 0, z + 3, Blocks.fire); // oh god the roof!
		
		// skeleton spawner!
        world.setBlock(x + 3, y + 1, z + 3, Blocks.mob_spawner, 0, 2);
        TileEntityMobSpawner ms = (TileEntityMobSpawner)world.getTileEntity(x + 3, y + 1, z + 3);
        MobSpawnerBaseLogic spawnerLogic = ms.func_145881_a();
        if (spawnerLogic != null) {
        	spawnerLogic.setEntityName(TFCreatures.getSpawnerNameFor("Skeleton Druid"));
        }


		
		return true;
	}
	
}
