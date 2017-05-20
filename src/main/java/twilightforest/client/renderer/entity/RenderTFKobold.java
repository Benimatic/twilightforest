package twilightforest.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import twilightforest.entity.EntityTFKobold;

public class RenderTFKobold extends RenderTFBiped<EntityTFKobold> {

	public RenderTFKobold(RenderManager manager, ModelBiped modelBiped, float scale, String textureName) {
		super(manager, modelBiped, scale, textureName);
        this.addLayer(new LayerHeldItem(this));
	}

	/**
	 * How far down the arm should we render the equipped item?
	 */
	//TODO: AtomicBlom: Somehow introduce this into LayerHeldItem
    protected void func_82422_c()
    {
        GlStateManager.translate(0.0F, 0.01875F, 0.0F);
    }

}
