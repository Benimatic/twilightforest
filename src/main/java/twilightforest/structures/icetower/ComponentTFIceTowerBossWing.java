package twilightforest.structures.icetower;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import twilightforest.block.BlockTFBossSpawner;
import twilightforest.block.TFBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import twilightforest.block.enums.SpawnerVariant;

public class ComponentTFIceTowerBossWing extends ComponentTFIceTowerWing {

	public ComponentTFIceTowerBossWing() {
		super();
		// no spawns
		this.spawnListIndex = -1;
	}
	
	public ComponentTFIceTowerBossWing(int index, int x, int y, int z, int wingSize, int wingHeight, EnumFacing direction) {
		super(index, x, y, z, wingSize, wingHeight, direction);
		// no spawns
		this.spawnListIndex = -1;
	}
	
	@Override
	protected boolean shouldHaveBase(Random rand) {
		return false;
	}

	
	/**
	 * Put down planks or whatevs for a floor
	 */
	@Override
	protected void placeFloor(World world, Random rand, StructureBoundingBox sbb, int floorHeight, int floor) {
		for (int x = 1; x < size - 1; x++) {
			for (int z = 1; z < size - 1; z++) {
				
				IBlockState ice = (rand.nextInt(4) == 0 ? Blocks.ICE : Blocks.PACKED_ICE).getDefaultState();
				int thickness = 1 + rand.nextInt(2) + rand.nextInt(2) + rand.nextInt(2);
				
				for (int y = 0; y < thickness; y++) {
					setBlockState(world, ice, x, (floor * floorHeight) + floorHeight - y, z, sbb);
				}
			}
		}
	}
	
	/**
	 * Called to decorate each floor.  This is responsible for adding a ladder up, the stub of the ladder going down, then picking a theme for each floor and executing it.
	 * 
	 * @param floor
	 * @param bottom
	 * @param top
	 * @param ladderUpDir
	 * @param ladderDownDir
	 */
	@Override
	protected void decorateFloor(World world, Random rand, int floor, int bottom, int top, int ladderUpDir, int ladderDownDir, StructureBoundingBox sbb) {

		for (int y = 0; y < 3; y++) {
			int rotation = (ladderDownDir + y) % 4;
			int rotation2 = (ladderDownDir + y + 2) % 4;

			placeIceStairs(world, sbb, rand, bottom + (y * 3), rotation);
			placeIceStairs(world, sbb, rand, bottom + (y * 3), rotation2);

		}
		
	}

	private void placeIceStairs(World world, StructureBoundingBox sbb, Random rand, int y, int rotation) {
		final IBlockState packedIce = Blocks.PACKED_ICE.getDefaultState();
		this.fillBlocksRotated(world, sbb, 8, y + 1, 1, 10, y + 1, 3, packedIce, rotation);
		if (y > 1) {
			this.randomlyFillBlocksRotated(world, sbb, rand, 0.5F, 8, y + 0, 1, 10, y + 0, 3, packedIce, AIR, rotation);
		}
		this.fillBlocksRotated(world, sbb, 11, y + 2, 1, 13, y + 2, 3, packedIce, rotation);
		this.randomlyFillBlocksRotated(world, sbb, rand, 0.5F, 11, y + 1, 1, 13, y + 1, 3, packedIce, AIR, rotation);
		this.fillBlocksRotated(world, sbb, 11, y + 3, 4, 13, y + 3, 6, packedIce, rotation);
		this.randomlyFillBlocksRotated(world, sbb, rand, 0.5F, 11, y + 2, 4, 13, y + 2, 6, packedIce, AIR, rotation);
	}
	
	@Override
	protected void decorateTopFloor(World world, Random rand, int floor, int bottom, int top, int ladderUpDir, int ladderDownDir, StructureBoundingBox sbb) {
		for (int x = 1; x < size - 1; x++) {
			for (int z = 1; z < size - 1; z++) {
				
				IBlockState ice = (rand.nextInt(10) == 0 ? Blocks.GLOWSTONE : Blocks.PACKED_ICE).getDefaultState();
				int thickness = rand.nextInt(2) + rand.nextInt(2);
				
				for (int y = 0; y < thickness; y++) {
					setBlockState(world, ice, x, top - 1 - y, z, sbb);
				}
			}
		}

		final IBlockState snowQueenSpawner = TFBlocks.bossSpawner.getDefaultState().withProperty(BlockTFBossSpawner.VARIANT, SpawnerVariant.SNOW_QUEEN);

		this.setBlockStateRotated(world, snowQueenSpawner, 7, top - 6, 7, 0, sbb);
		
		this.fillWithAir(world, sbb, 5, top - 3, 5, 9, top - 1, 9);
	}
}
