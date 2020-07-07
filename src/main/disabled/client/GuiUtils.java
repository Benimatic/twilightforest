package twilightforest.client;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class GuiUtils {

	// [VanillaCopy] of Gui.drawModalRectWithCustomSizedTexture, but accepting a z-level parameter
	public static void drawModalRectWithCustomSizedTexture(int x, int y, float z, float u, float v, int width, int height, float textureWidth, float textureHeight) {
		float f = 1.0F / textureWidth;
		float f1 = 1.0F / textureHeight;
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.vertex((double)x, (double)(y + height), (double)z).texture((u * f), ((v + (float)height) * f1)).endVertex();
		bufferbuilder.vertex((double)(x + width), (double)(y + height), (double)z).texture(((u + (float)width) * f), ((v + (float)height) * f1)).endVertex();
		bufferbuilder.vertex((double)(x + width), (double)y, (double)z).texture(((u + (float)width) * f), (v * f1)).endVertex();
		bufferbuilder.vertex((double)x, (double)y, (double)z).texture((u * f), (v * f1)).endVertex();
		tessellator.draw();
	}
}
