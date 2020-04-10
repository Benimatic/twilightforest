package twilightforest.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

import java.util.Random;

public class WoolUtil {

	private static final Block[] WOOL_BLOCKS = {
			Blocks.WHITE_WOOL,
			Blocks.ORANGE_WOOL,
			Blocks.MAGENTA_WOOL,
			Blocks.LIGHT_BLUE_WOOL,
			Blocks.YELLOW_WOOL,
			Blocks.LIME_WOOL,
			Blocks.PINK_WOOL,
			Blocks.GRAY_WOOL,
			Blocks.LIGHT_GRAY_WOOL,
			Blocks.CYAN_WOOL,
			Blocks.PURPLE_WOOL,
			Blocks.BLUE_WOOL,
			Blocks.BROWN_WOOL,
			Blocks.GREEN_WOOL,
			Blocks.RED_WOOL,
			Blocks.BLACK_WOOL
	};

	public static BlockState getStateById(int index) {
		return WOOL_BLOCKS[index].getDefaultState();
	}

	public static Block getRandomBlock(Random rand) {
		return WOOL_BLOCKS[rand.nextInt(16)];
	}
}
