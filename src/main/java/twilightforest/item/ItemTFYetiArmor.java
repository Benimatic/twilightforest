package twilightforest.item;

import java.util.List;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import twilightforest.TwilightForestMod;
import twilightforest.enchantment.TFEnchantment;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTFYetiArmor extends ItemArmor {

	public ItemTFYetiArmor(ItemArmor.ArmorMaterial par2EnumArmorMaterial, int renderIndex, int armorType) {
		super(par2EnumArmorMaterial, renderIndex, armorType);
		this.setCreativeTab(TFItems.creativeTab);
	}

	/**
	 * Return an item rarity from EnumRarity
	 */    
	@Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return EnumRarity.epic;
	}
	
    /**
     * Called by RenderBiped and RenderPlayer to determine the armor texture that 
     * should be use for the currently equiped item.
     * This will only be called on instances of ItemArmor. 
     * 
     * Returning null from this function will use the default value.
     * 
     * @param stack ItemStack for the equpt armor
     * @param entity The entity wearing the armor
     * @param slot The slot the armor is in
     * @param layer The render layer, either 1 or 2, 2 is only used for CLOTH armor by default
     * @return Path of texture to bind, or null to use default
     */
	public String getArmorTexture(ItemStack itemstack, Entity entity, int slot, String layer) {
		switch (slot) {
		case 0 : 
		case 3 :
		default :
			return TwilightForestMod.ARMOR_DIR + "yetiarmor_1.png";
		case 1 : 
		case 2 :
			return TwilightForestMod.ARMOR_DIR + "yetiarmor_2.png";

		}
	}
	
    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
    	ItemStack istack = new ItemStack(par1, 1, 0);
    	switch (this.armorType) {
    	case 0:
    		istack.addEnchantment(Enchantment.protection, 2);
            break;	
    	case 1:
    		istack.addEnchantment(Enchantment.protection, 2);
            break;	
    	case 2:
    		istack.addEnchantment(Enchantment.protection, 2);
            break;	
    	case 3:
    		istack.addEnchantment(Enchantment.protection, 2);
    		istack.addEnchantment(Enchantment.featherFalling, 4);
            break;	
    	}
    	par3List.add(istack);
    }
    
    /**
     * Return whether this item is repairable in an anvil.
     */
    @Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
    	// repair with ?????
        return par2ItemStack.getItem() == TFItems.alphaFur ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
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
    
    /**
     * Override this method to have an item handle its own armor rendering.
     * 
     * @param  entityLiving  The entity wearing the armor 
     * @param  itemStack  The itemStack to render the model of 
     * @param  armorSlot  0=head, 1=torso, 2=legs, 3=feet
     * 
     * @return  A ModelBiped to render instead of the default
     */
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot)
    {
        return TwilightForestMod.proxy.getYetiArmorModel(armorSlot);
    }
    
    /**
     * allows items to add custom lines of information to the mouseover description
     */
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		par3List.add(StatCollector.translateToLocal(getUnlocalizedName() + ".tooltip"));
	}
}
