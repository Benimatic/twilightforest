package twilightforest.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.HugeMushroomBlock;

/**
 * Utility class for Huge Mushroom blocks. Contains presets
 */
public class MushroomUtil {

	public static BlockState getState(Type type, BlockState base) {
		return base
				.with(HugeMushroomBlock.UP, type.top)
				.with(HugeMushroomBlock.DOWN, type.bottom)
				.with(HugeMushroomBlock.NORTH, type.north)
				.with(HugeMushroomBlock.SOUTH, type.south)
				.with(HugeMushroomBlock.EAST, type.east)
				.with(HugeMushroomBlock.WEST, type.west);
	}

	public enum Type {

		CENTER(true, false, false, false, false, false),
		NORTH(true, false, true, false, false, false),
		SOUTH(true, false, false, true, false, false),
		EAST(true, false, false, false, true, false),
		WEST(true, false, false, false, false, true),
		NORTH_WEST(true, false, true, false, false, true),
		NORTH_EAST(true, false, true, false, true, false),
		SOUTH_WEST(true, false, false, true, false, true),
		SOUTH_EAST(true, false, false, true, true, false);

		private boolean top;
		private boolean bottom;
		private boolean north;
		private boolean south;
		private boolean east;
		private boolean west;

		Type(boolean t, boolean b, boolean n, boolean s, boolean e, boolean w) {
			top = t;
			bottom = b;
			north = n;
			south = s;
			east = e;
			west = w;
		}
	}
}
