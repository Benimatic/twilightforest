package twilightforest.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import twilightforest.TwilightForestMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTFNagaArmor extends ItemArmor {

	public ItemTFNagaArmor(ItemArmor.ArmorMaterial par2EnumArmorMaterial, int par3, int par4) {
		super(par2EnumArmorMaterial, par3, par4);
		this.setCreativeTab(TFItems.creativeTab);
	}

	@Override
	public String getArmorTexture(ItemStack itemstack, Entity entity, int slot, String layer) {
		
		
        if(itemstack.getItem() == TFItems.plateNaga)
        {
                return TwilightForestMod.ARMOR_DIR + "naga_scale_1.png";
        }
        if(itemstack.getItem() == TFItems.legsNaga)
        {
                return TwilightForestMod.ARMOR_DIR + "naga_scale_2.png";
        }
        return TwilightForestMod.ARMOR_DIR + "naga_scale_1.png";
	}

    /**
     * Return whether this item is repairable in an anvil.
     */
    @Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
    	// repair with naga scale
        return par2ItemStack.getItem() == TFItems.nagaScale ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
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
