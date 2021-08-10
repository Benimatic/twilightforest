package twilightforest.util;

import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.core.Vec3i;

import javax.annotation.Nullable;

// TODO Do we need to keep this class? Delete if this remains untouched by 1.19
public class StructureBoundingBoxUtils {
	@Deprecated // Use `BoundingBox#getCenter` directly
	public static Vec3i getCenter(BoundingBox sbb) {
		return sbb.getCenter();
	}

	// This method has been renamed to be the intersection because it functionally is.
	// If you're looking for the union equivalent, use `BoundingBox#encapsulate`
	@SuppressWarnings("unused")
	@Nullable
	public static BoundingBox getIntersectionOfSBBs(BoundingBox box1, BoundingBox box2) {
		if (!box1.intersects(box2))
			return null;

		return new BoundingBox(
						Math.max(box1.minX(), box2.minX()),
						Math.max(box1.minY(), box2.minY()),
						Math.max(box1.minZ(), box2.minZ()),
						Math.min(box1.maxX(), box2.maxX()),
						Math.min(box1.maxY(), box2.maxY()),
						Math.min(box1.maxZ(), box2.maxZ()));
	}
}
