package twilightforest.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.core.Vec3i;

import javax.annotation.Nullable;

public class StructureBoundingBoxUtils {
	public static Vec3i getCenter(BoundingBox sbb) {
		return new BlockPos(sbb.x0 + (sbb.x1 - sbb.x0 + 1) / 2, sbb.y0 + (sbb.y1 - sbb.y0 + 1) / 2, sbb.z0 + (sbb.z1 - sbb.z0 + 1) / 2);
	}

	@SuppressWarnings("unused")
	@Nullable
	public static BoundingBox getUnionOfSBBs(BoundingBox sbbIn, BoundingBox sbbMask) {
		if (!sbbIn.intersects(sbbMask))
			return null;

		return new BoundingBox(
						Math.max(sbbIn.x0, sbbMask.x0),
						Math.max(sbbIn.y0, sbbMask.y0),
						Math.max(sbbIn.z0, sbbMask.z0),
						Math.min(sbbIn.x1, sbbMask.x1),
						Math.min(sbbIn.y1, sbbMask.y1),
						Math.min(sbbIn.z1, sbbMask.z1));
	}
}
