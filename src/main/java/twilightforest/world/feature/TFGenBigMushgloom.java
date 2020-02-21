package twilightforest.world.feature;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HugeMushroomBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import twilightforest.block.TFBlocks;
import twilightforest.util.FeatureUtil;

import java.util.Random;
import java.util.function.Function;

public class TFGenBigMushgloom<T extends NoFeatureConfig> extends Feature<T> {

	public TFGenBigMushgloom(Function<Dynamic<?>, T> config) {
		super(config);
	}

	@Override
	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, T config) {
		int height = 3 + rand.nextInt(2) + rand.nextInt(2);

		if (!FeatureUtil.isAreaSuitable(world, rand, pos.add(-1, 0, -1), 3, height, 3)) {
			return false;
		}

		Block blockUnder = world.getBlockState(pos.down()).getBlock();
		if (blockUnder != Blocks.DIRT && blockUnder != Blocks.GRASS && blockUnder != Blocks.MYCELIUM) {
			return false;
		}

		// generate!
		for (int dy = 0; dy < height - 2; dy++) {
			world.setBlockState(pos.up(dy), Blocks.MUSHROOM_STEM.getDefaultState(), 3);
		}

		makeMushroomCap(world.getWorld(), pos.up(height - 2));
		if (rand.nextBoolean()) {
			makeMushroomCap(world.getWorld(), pos.up(height - 1));
		}

		return true;
	}

	//TODO: Please verify I did these right
	private void makeMushroomCap(World world, BlockPos pos) {
		BlockState defState = TFBlocks.huge_mushgloom.get().getDefaultState().with(HugeMushroomBlock.DOWN, false); //All blocks don't have the texture on the bottom
		world.setBlockState(pos.add(-1, 0, -1), defState.with(HugeMushroomBlock.SOUTH, false).with(HugeMushroomBlock.EAST, false));
		world.setBlockState(pos.add(0, 0, -1), defState.with(HugeMushroomBlock.SOUTH, false).with(HugeMushroomBlock.EAST, false).with(HugeMushroomBlock.WEST, false));
		world.setBlockState(pos.add(1, 0, -1), defState.with(HugeMushroomBlock.SOUTH, false).with(HugeMushroomBlock.WEST, false));
		world.setBlockState(pos.add(-1, 0, 0), defState.with(HugeMushroomBlock.NORTH, false).with(HugeMushroomBlock.SOUTH, false).with(HugeMushroomBlock.EAST, false));
		world.setBlockState(pos, defState.with(HugeMushroomBlock.NORTH, false).with(HugeMushroomBlock.SOUTH, false).with(HugeMushroomBlock.EAST, false).with(HugeMushroomBlock.WEST, false));
		world.setBlockState(pos.add(1, 0, 0), defState.with(HugeMushroomBlock.NORTH, false).with(HugeMushroomBlock.SOUTH, false).with(HugeMushroomBlock.WEST, false));
		world.setBlockState(pos.add(-1, 0, 1), defState.with(HugeMushroomBlock.NORTH, false).with(HugeMushroomBlock.EAST, false));
		world.setBlockState(pos.add(0, 0, 1), defState.with(HugeMushroomBlock.NORTH, false).with(HugeMushroomBlock.EAST, false).with(HugeMushroomBlock.WEST, false));
		world.setBlockState(pos.add(1, 0, 1), defState.with(HugeMushroomBlock.NORTH, false).with(HugeMushroomBlock.WEST, false));
//		world.setBlockState(pos.add(-1, 0, -1), defState.with(BlockTFHugeGloomBlock.VARIANT, BlockHugeMushroom.EnumType.NORTH_WEST));
//		world.setBlockState(pos.add(0, 0, -1), defState.with(BlockTFHugeGloomBlock.VARIANT, BlockHugeMushroom.EnumType.NORTH));
//		world.setBlockState(pos.add(1, 0, -1), defState.with(BlockTFHugeGloomBlock.VARIANT, BlockHugeMushroom.EnumType.NORTH_EAST));
//		world.setBlockState(pos.add(-1, 0, 0), defState.with(BlockTFHugeGloomBlock.VARIANT, BlockHugeMushroom.EnumType.WEST));
//		world.setBlockState(pos, defState.with(BlockTFHugeGloomBlock.VARIANT, BlockHugeMushroom.EnumType.CENTER));
//		world.setBlockState(pos.add(1, 0, 0), defState.with(BlockTFHugeGloomBlock.VARIANT, BlockHugeMushroom.EnumType.EAST));
//		world.setBlockState(pos.add(-1, 0, 1), defState.with(BlockTFHugeGloomBlock.VARIANT, BlockHugeMushroom.EnumType.SOUTH_WEST));
//		world.setBlockState(pos.add(0, 0, 1), defState.with(BlockTFHugeGloomBlock.VARIANT, BlockHugeMushroom.EnumType.SOUTH));
//		world.setBlockState(pos.add(1, 0, 1), defState.with(BlockTFHugeGloomBlock.VARIANT, BlockHugeMushroom.EnumType.SOUTH_EAST));
	}
}
