package twilightforest.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import twilightforest.TwilightForestMod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTF extends Item {
	
	private boolean isRare = false;

	protected ItemTF() {
		super();
		this.setCreativeTab(TFItems.creativeTab);
	}
	
    /**
     * Return an item rarity from EnumRarity
     * 
     * This is automatically uncommon
     */    
    @Override
    @SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack par1ItemStack) {
    	return isRare ? EnumRarity.rare : EnumRarity.uncommon;
	}

	/**
	 * Set rarity during creation
	 */
	public ItemTF makeRare()
	{
		this.isRare = true;
		return this;
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
