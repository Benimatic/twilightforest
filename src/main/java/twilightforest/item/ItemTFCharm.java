package twilightforest.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import twilightforest.TwilightForestMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTFCharm extends ItemTF
{

	protected ItemTFCharm() {
		super();
		this.setCreativeTab(TFItems.creativeTab);
	}

	/**
	 * Properly register icon source
	 */
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon(TwilightForestMod.ID + ":" + this.getUnlocalizedName().substring(5));
    }
}
