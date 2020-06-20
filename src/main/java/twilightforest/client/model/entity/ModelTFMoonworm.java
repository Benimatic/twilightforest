// Date: 11/8/2012 9:54:59 AM
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX

package twilightforest.client.model.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import twilightforest.client.BugModelAnimationHelper;
import twilightforest.tileentity.TileEntityTFMoonwormTicking;

import javax.annotation.Nullable;

public class ModelTFMoonworm extends Model {
	ModelRenderer shape1;
	ModelRenderer shape2;
	ModelRenderer shape3;
	ModelRenderer head;

	public ModelTFMoonworm() {
		super(RenderType::getEntityCutoutNoCull);
		textureWidth = 32;
		textureHeight = 32;

		shape1 = new ModelRenderer(this, 0, 4);
		shape1.addCuboid(-1F, -1F, -1F, 4, 2, 2);
		shape1.setRotationPoint(-1F, 7F, 3F);

		shape2 = new ModelRenderer(this, 0, 8);
		shape2.addCuboid(-1F, -1F, -1F, 2, 2, 4);
		shape2.setRotationPoint(3F, 7F, 0F);

		shape3 = new ModelRenderer(this, 0, 14);
		shape3.addCuboid(-1F, -1F, -1F, 2, 2, 2);
		shape3.setRotationPoint(2F, 7F, -2F);

		head = new ModelRenderer(this, 0, 0);
		head.addCuboid(-1F, -1F, -1F, 2, 2, 2);
		head.setRotationPoint(-3F, 7F, 2F);
	}

	public void setAngles(@Nullable TileEntityTFMoonwormTicking moonworm, float partialTime) {
		head.rotationPointY = 7F;
		shape1.rotationPointY = 7F;
		shape2.rotationPointY = 7F;
		shape3.rotationPointY = 7F;

		if (moonworm != null && moonworm.yawDelay == 0) {
			float time = (moonworm.desiredYaw - moonworm.currentYaw) - partialTime;

			// moving
			head.rotationPointY += Math.min(0, MathHelper.sin(time / 2));
			shape1.rotationPointY += Math.min(0, MathHelper.sin(time / 2 + 1));
			shape2.rotationPointY += Math.min(0, MathHelper.sin(time / 2 + 2));
			shape3.rotationPointY += Math.min(0, MathHelper.sin(time / 2 + 3));
		} else if (moonworm == null && BugModelAnimationHelper.yawWriggleDelay == 0) {
			float time = (BugModelAnimationHelper.desiredRotation - BugModelAnimationHelper.currentRotation) - partialTime;

			// moving
			head.rotationPointY += Math.min(0, MathHelper.sin(time / 2));
			shape1.rotationPointY += Math.min(0, MathHelper.sin(time / 2 + 1));
			shape2.rotationPointY += Math.min(0, MathHelper.sin(time / 2 + 2));
			shape3.rotationPointY += Math.min(0, MathHelper.sin(time / 2 + 3));
		}
	}

	@Override
	public void render(MatrixStack ms, IVertexBuilder buffer, int light, int overlay, float r, float g, float b, float a) {
		shape1.render(ms, buffer, light, overlay, r, g, b, a);
		shape2.render(ms, buffer, light, overlay, r, g, b, a);
		shape3.render(ms, buffer, light, overlay, r, g, b, a);
		head.render(ms, buffer, light, overlay, r, g, b, a);
	}
}
