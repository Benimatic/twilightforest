package twilightforest.entity.projectile;

import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

/**
 * Marker interface for our projectiles, used for parrying.
 */
public interface ITFProjectile {
	@Nullable
	ResourceLocation getTexture();
}
