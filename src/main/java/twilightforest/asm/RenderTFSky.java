package twilightforest.asm;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import twilightforest.world.TFDimensions;

@SuppressWarnings("unused") // Not possible for the IDE to detect usages from runtime class transformations
public class RenderTFSky {

	/**
	 * Injection Point:<br>
	 * {@link net.minecraft.client.renderer.WorldRenderer#renderSky(MatrixStack, float)}<br>
	 * [FIRST INSTRUCTION]
	 */
	public static void render() {
		ClientWorld world = Minecraft.getInstance().world;
		if(world != null && world.func_234923_W_().func_240901_a_().equals(TFDimensions.twilightForestType.func_240901_a_())) {

		}
	}

}
