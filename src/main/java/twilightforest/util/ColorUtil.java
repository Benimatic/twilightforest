package twilightforest.util;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.DyeColor;

import java.util.Random;
import java.util.function.Function;

public record ColorUtil(Function<DyeColor, Block> function) {
	public static final ColorUtil WOOL = new ColorUtil(color -> switch (color) {
		case WHITE -> Blocks.WHITE_WOOL;
		case ORANGE -> Blocks.ORANGE_WOOL;
		case MAGENTA -> Blocks.MAGENTA_WOOL;
		case LIGHT_BLUE -> Blocks.LIGHT_BLUE_WOOL;
		case YELLOW -> Blocks.YELLOW_WOOL;
		case LIME -> Blocks.LIME_WOOL;
		case PINK -> Blocks.PINK_WOOL;
		case GRAY -> Blocks.GRAY_WOOL;
		case LIGHT_GRAY -> Blocks.LIGHT_GRAY_WOOL;
		case CYAN -> Blocks.CYAN_WOOL;
		case PURPLE -> Blocks.PURPLE_WOOL;
		case BLUE -> Blocks.BLUE_WOOL;
		case BROWN -> Blocks.BROWN_WOOL;
		case GREEN -> Blocks.GREEN_WOOL;
		case RED -> Blocks.RED_WOOL;
		case BLACK -> Blocks.BLACK_WOOL;
	});

	public static final ColorUtil TERRACOTTA = new ColorUtil(color -> switch (color) {
		case WHITE -> Blocks.WHITE_TERRACOTTA;
		case ORANGE -> Blocks.ORANGE_TERRACOTTA;
		case MAGENTA -> Blocks.MAGENTA_TERRACOTTA;
		case LIGHT_BLUE -> Blocks.LIGHT_BLUE_TERRACOTTA;
		case YELLOW -> Blocks.YELLOW_TERRACOTTA;
		case LIME -> Blocks.LIME_TERRACOTTA;
		case PINK -> Blocks.PINK_TERRACOTTA;
		case GRAY -> Blocks.GRAY_TERRACOTTA;
		case LIGHT_GRAY -> Blocks.LIGHT_GRAY_TERRACOTTA;
		case CYAN -> Blocks.CYAN_TERRACOTTA;
		case PURPLE -> Blocks.PURPLE_TERRACOTTA;
		case BLUE -> Blocks.BLUE_TERRACOTTA;
		case BROWN -> Blocks.BROWN_TERRACOTTA;
		case GREEN -> Blocks.GREEN_TERRACOTTA;
		case RED -> Blocks.RED_TERRACOTTA;
		case BLACK -> Blocks.BLACK_TERRACOTTA;
	});

	public static final ColorUtil STAINED_GLASS = new ColorUtil(color -> switch (color) {
		case WHITE -> Blocks.WHITE_STAINED_GLASS;
		case ORANGE -> Blocks.ORANGE_STAINED_GLASS;
		case MAGENTA -> Blocks.MAGENTA_STAINED_GLASS;
		case LIGHT_BLUE -> Blocks.LIGHT_BLUE_STAINED_GLASS;
		case YELLOW -> Blocks.YELLOW_STAINED_GLASS;
		case LIME -> Blocks.LIME_STAINED_GLASS;
		case PINK -> Blocks.PINK_STAINED_GLASS;
		case GRAY -> Blocks.GRAY_STAINED_GLASS;
		case LIGHT_GRAY -> Blocks.LIGHT_GRAY_STAINED_GLASS;
		case CYAN -> Blocks.CYAN_STAINED_GLASS;
		case PURPLE -> Blocks.PURPLE_STAINED_GLASS;
		case BLUE -> Blocks.BLUE_STAINED_GLASS;
		case BROWN -> Blocks.BROWN_STAINED_GLASS;
		case GREEN -> Blocks.GREEN_STAINED_GLASS;
		case RED -> Blocks.RED_STAINED_GLASS;
		case BLACK -> Blocks.BLACK_STAINED_GLASS;
	});


	public BlockState getColor(DyeColor color) {
		return this.function.apply(color).defaultBlockState();
	}

	public Block getRandomColor(Random rand) {
		DyeColor color = DyeColor.byId(rand.nextInt(16));
		return this.getColor(color).getBlock();
	}
}
