package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.math.MathHelper;
import twilightforest.client.model.entity.ModelTFIceExploder;
import twilightforest.entity.EntityUnstableIceCore;

public class RenderTFIceExploder<T extends EntityUnstableIceCore, M extends ModelTFIceExploder<T>> extends RenderTFBiped<T, M> {

	public RenderTFIceExploder(EntityRendererManager manager, M model) {
		super(manager, model, 0.4F, "iceexploder.png");
	}

	@Override
	protected void scale(T entity, MatrixStack stack, float partialTicks) {
		float bounce = entity.ticksExisted + partialTicks;

		stack.translate(0F, MathHelper.sin((bounce) * 0.2F) * 0.15F, 0F);

		// flash
		float f1 = entity.deathTime;
		if (f1 > 0) {
			float f2 = 1.0F + MathHelper.sin(f1 * 100.0F) * f1 * 0.01F;

			if (f1 < 0.0F) {
				f1 = 0.0F;
			}

			if (f1 > 1.0F) {
				f1 = 1.0F;
			}

			f1 *= f1;
			f1 *= f1;
			float f3 = (1.0F + f1 * 0.4F) * f2;
			float f4 = (1.0F + f1 * 0.1F) / f2;
			stack.scale(f3, f4, f3);
		}
	}

	@Override
	protected void setupTransforms(T entity, MatrixStack stack, float ageInTicks, float rotationYaw, float partialTicks) {
		stack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180 - rotationYaw));
	}

	@Override
	protected float getWhiteOverlayProgress(T entity, float partialTicks) {
		if (entity.deathTime > 0) {
			float f2 = entity.deathTime + partialTicks;

			if ((int) (f2 / 2) % 2 == 0) {
				return 0;
			} else {
				int i = (int) (f2 * 0.2F * 255.0F);

				if (i < 0) {
					i = 0;
				}

				if (i > 255) {
					i = 255;
				}

				short short1 = 255;
				short short2 = 255;
				short short3 = 255;
				return i << 24 | short1 << 16 | short2 << 8 | short3;
			}
		} else {
			return 0;
		}
	}
}


