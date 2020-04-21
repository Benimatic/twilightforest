package twilightforest.structures.minotaurmaze;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HugeMushroomBlock;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.util.MushroomUtil;

import java.util.Random;

public class ComponentTFMazeMushRoom extends ComponentTFMazeRoom {

	public ComponentTFMazeMushRoom(TemplateManager manager, CompoundNBT nbt) {
		super(TFMinotaurMazePieces.TFMMMR, nbt);
	}

	public ComponentTFMazeMushRoom(TFFeature feature, int i, Random rand, int x, int y, int z) {
		super(TFMinotaurMazePieces.TFMMMR, feature, i, rand, x, y, z);

		this.setCoordBaseMode(Direction.SOUTH); // let's just make this easy on us?
	}

	@Override
	public boolean generate(IWorld worldIn, ChunkGenerator<?> generator, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn) {
		World world = worldIn.getWorld();
		super.generate(world, generator, rand, sbb, chunkPosIn);

		for (int x = 1; x < 14; x++) {
			for (int z = 1; z < 14; z++) {
				// calculate distance from middle
				int dist = (int) Math.round(7 / Math.sqrt((7.5 - x) * (7.5 - x) + (7.5 - z) * (7.5 - z)));

				// make part of the floor mycelium
				if (rand.nextInt(dist + 1) > 0) {
					this.setBlockState(world, Blocks.MYCELIUM.getDefaultState(), x, 0, z, sbb);
				}
				// add small mushrooms all over
				if (rand.nextInt(dist) > 0) {
					this.setBlockState(world, (rand.nextBoolean() ? Blocks.RED_MUSHROOM : Blocks.BROWN_MUSHROOM).getDefaultState(), x, 1, z, sbb);
				}
			}
		}

		final BlockState redMushroomBlock = Blocks.RED_MUSHROOM_BLOCK.getDefaultState();
		final BlockState brownMushroomBlock = Blocks.BROWN_MUSHROOM_BLOCK.getDefaultState();
		final BlockState stemMushroomBlock = Blocks.MUSHROOM_STEM.getDefaultState();

		// add our medium mushrooms

		makeMediumMushroom(world, sbb, 5, 2, 9, redMushroomBlock);
		makeMediumMushroom(world, sbb, 5, 3, 9, redMushroomBlock);
		makeMediumMushroom(world, sbb, 9, 2, 5, redMushroomBlock);
		makeMediumMushroom(world, sbb, 6, 3, 4, brownMushroomBlock);
		makeMediumMushroom(world, sbb, 10, 1, 9, brownMushroomBlock);

		// bracket mushrooms on the wall
		this.setBlockState(world, stemMushroomBlock, 1, 2, 1, sbb);
		this.setBlockState(world, MushroomUtil.getState(MushroomUtil.Type.CENTER, redMushroomBlock), 1, 3, 1, sbb);
		this.setBlockState(world, MushroomUtil.getState(MushroomUtil.Type.NORTH_WEST, redMushroomBlock), 2, 3, 1, sbb);
		this.setBlockState(world, MushroomUtil.getState(MushroomUtil.Type.NORTH_WEST, redMushroomBlock), 1, 3, 2, sbb);

		this.setBlockState(world, stemMushroomBlock, 14, 3, 1, sbb);
		this.setBlockState(world, MushroomUtil.getState(MushroomUtil.Type.CENTER, brownMushroomBlock), 14, 4, 1, sbb);
		this.setBlockState(world, MushroomUtil.getState(MushroomUtil.Type.NORTH_EAST, brownMushroomBlock), 13, 4, 1, sbb);
		this.setBlockState(world, MushroomUtil.getState(MushroomUtil.Type.NORTH_EAST, brownMushroomBlock), 14, 4, 2, sbb);

		this.setBlockState(world, stemMushroomBlock, 1, 1, 14, sbb);
		this.setBlockState(world, MushroomUtil.getState(MushroomUtil.Type.CENTER, brownMushroomBlock), 1, 2, 14, sbb);
		this.setBlockState(world, MushroomUtil.getState(MushroomUtil.Type.SOUTH_WEST, brownMushroomBlock), 2, 2, 14, sbb);
		this.setBlockState(world, MushroomUtil.getState(MushroomUtil.Type.SOUTH_WEST, brownMushroomBlock), 1, 2, 13, sbb);

		this.setBlockState(world, MushroomUtil.getState(MushroomUtil.Type.CENTER, brownMushroomBlock), 14, 1, 14, sbb);
		this.setBlockState(world, MushroomUtil.getState(MushroomUtil.Type.SOUTH_EAST, brownMushroomBlock), 13, 1, 14, sbb);
		this.setBlockState(world, MushroomUtil.getState(MushroomUtil.Type.SOUTH_EAST, brownMushroomBlock), 14, 1, 13, sbb);

		// mushroom ceiling spots?

		return true;
	}

	/**
	 * Make a 3x3 square mushroom centered on the specified coords.
	 */
	private void makeMediumMushroom(World world, MutableBoundingBox sbb, int mx, int my, int mz, BlockState redMushroomBlock) {
		final BlockState mushroomStem = Blocks.MUSHROOM_STEM.getDefaultState().with(HugeMushroomBlock.DOWN, false).with(HugeMushroomBlock.UP, false);

		// cap
		this.setBlockState(world, MushroomUtil.getState(MushroomUtil.Type.CENTER, redMushroomBlock), mx + 0, my, mz + 0, sbb);
		this.setBlockState(world, MushroomUtil.getState(MushroomUtil.Type.WEST, redMushroomBlock), mx + 1, my, mz + 0, sbb);
		this.setBlockState(world, MushroomUtil.getState(MushroomUtil.Type.NORTH_WEST, redMushroomBlock), mx + 1, my, mz + 1, sbb);
		this.setBlockState(world, MushroomUtil.getState(MushroomUtil.Type.NORTH, redMushroomBlock), mx + 0, my, mz + 1, sbb);
		this.setBlockState(world, MushroomUtil.getState(MushroomUtil.Type.NORTH_EAST, redMushroomBlock), mx - 1, my, mz + 1, sbb);
		this.setBlockState(world, MushroomUtil.getState(MushroomUtil.Type.EAST, redMushroomBlock), mx - 1, my, mz + 0, sbb);
		this.setBlockState(world, MushroomUtil.getState(MushroomUtil.Type.SOUTH_EAST, redMushroomBlock), mx - 1, my, mz - 1, sbb);
		this.setBlockState(world, MushroomUtil.getState(MushroomUtil.Type.SOUTH, redMushroomBlock), mx + 0, my, mz - 1, sbb);
		this.setBlockState(world, MushroomUtil.getState(MushroomUtil.Type.SOUTH_WEST, redMushroomBlock), mx + 1, my, mz - 1, sbb);

		// stem
		for (int y = 1; y < my; y++) {
			this.setBlockState(world, mushroomStem, mx + 0, y, mz + 0, sbb);
		}
	}
}
