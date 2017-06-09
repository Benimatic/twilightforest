package twilightforest.util;

import net.minecraft.util.Rotation;
import java.util.Random;

import static net.minecraft.util.Rotation.NONE;
import static net.minecraft.util.Rotation.CLOCKWISE_90;
import static net.minecraft.util.Rotation.CLOCKWISE_180;
import static net.minecraft.util.Rotation.COUNTERCLOCKWISE_90;

public final class RotationUtil
{
	public static final Rotation[] ROTATIONS = Rotation.values();

	private RotationUtil() {}

	public static Rotation getRandomRotation(Random random) {
		return ROTATIONS[random.nextInt(ROTATIONS.length)];
	}

	public static Rotation add(Rotation original, int rotations) {
		return original.add(ROTATIONS[(rotations + 4) & 3]);
	}

	@SuppressWarnings("Duplicates")
	public static Rotation subtract(Rotation original, Rotation rotation)
	{
		switch (rotation)
		{
			case CLOCKWISE_180:

				switch (original)
				{
					case NONE:
						return CLOCKWISE_180;
					case CLOCKWISE_90:
						return COUNTERCLOCKWISE_90;
					case CLOCKWISE_180:
						return NONE;
					case COUNTERCLOCKWISE_90:
						return CLOCKWISE_90;
				}

			case COUNTERCLOCKWISE_90:

				switch (original)
				{
					case NONE:
						return CLOCKWISE_90;
					case CLOCKWISE_90:
						return CLOCKWISE_180;
					case CLOCKWISE_180:
						return COUNTERCLOCKWISE_90;
					case COUNTERCLOCKWISE_90:
						return NONE;
				}

			case CLOCKWISE_90:

				switch (original)
				{
					case NONE:
						return COUNTERCLOCKWISE_90;
					case CLOCKWISE_90:
						return NONE;
					case CLOCKWISE_180:
						return CLOCKWISE_90;
					case COUNTERCLOCKWISE_90:
						return CLOCKWISE_180;
				}

			default:
				return original;
		}
	}
}
