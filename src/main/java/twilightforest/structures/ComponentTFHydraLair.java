package twilightforest.structures;

import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import twilightforest.block.TFBlocks;

public class ComponentTFHydraLair extends ComponentTFHollowHill {

	public ComponentTFHydraLair() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ComponentTFHydraLair(World world, Random rand, int i, int x, int y, int z) {
		super(world, rand, i, 2, x, y + 2, z);
	}

	/**
	 * Add on any other components we need.  Override with noop since we don't need a maze
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void buildComponent(StructureComponent structurecomponent, List list, Random random) {
		;
	}
	
	/**
	 * Add in all the blocks we're adding.
	 */
	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		int stalacts = 64;
		int stalags = 8;

		// fill in features
		// ore or glowing stalactites! (smaller, less plentiful)
		for (int i = 0; i < stalacts; i++)
		{
			int[] dest = getCoordsInHill2D(rand);
			generateOreStalactite(world, dest[0], 1, dest[1], sbb);
		}
		// stone stalactites!
		for (int i = 0; i < stalacts; i++)
		{
			int[] dest = getCoordsInHill2D(rand);
			generateBlockStalactite(world, Blocks.stone, 1.0F, true, dest[0], 1, dest[1], sbb);
		}
		// stone stalagmites!
		for (int i = 0; i < stalags; i++)
		{
			int[] dest = getCoordsInHill2D(rand);
			generateBlockStalactite(world, Blocks.stone, 0.9F, false, dest[0], 1, dest[1], sbb);
		}

		// boss spawner seems important
		placeBlockAtCurrentPosition(world, TFBlocks.bossSpawner, 2, 27, 3, 27, sbb);

		return true;
	}

}
