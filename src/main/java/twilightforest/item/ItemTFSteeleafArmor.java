package twilightforest.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import twilightforest.TwilightForestMod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTFSteeleafArmor extends ItemArmor {

	public ItemTFSteeleafArmor(ItemArmor.ArmorMaterial par2EnumArmorMaterial, int renderIndex, EntityEquipmentSlot armorType) {
		super(par2EnumArmorMaterial, renderIndex, armorType);
		this.setCreativeTab(TFItems.creativeTab);
	}

	@Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return EnumRarity.UNCOMMON;
	}

	@Override
	public String getArmorTexture(ItemStack itemstack, Entity entity, EntityEquipmentSlot slot, String layer) {
		if(itemstack.getItem() == TFItems.steeleafPlate || itemstack.getItem() == TFItems.steeleafHelm || itemstack.getItem() == TFItems.steeleafBoots)
		{
			return TwilightForestMod.ARMOR_DIR + "steeleaf_1.png";
		}
		if(itemstack.getItem() == TFItems.steeleafLegs)
		{
			return TwilightForestMod.ARMOR_DIR + "steeleaf_2.png";
		}
		return TwilightForestMod.ARMOR_DIR + "steeleaf_1.png";
	}
	
	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> par3List) {
    	ItemStack istack = new ItemStack(par1, 1, 0);
    	switch (this.armorType) {
    	case HEAD:
    		istack.addEnchantment(Enchantments.PROJECTILEPROTECTION, 2);
            break;	
    	case CHEST:
    		istack.addEnchantment(Enchantments.BLASTPROTECTION, 2);
            break;	
    	case LEGS:
    		istack.addEnchantment(Enchantments.FIREPROTECTION, 2);
            break;	
    	case FEET:
    		istack.addEnchantment(Enchantments.FEATHERFALLING, 2);
            break;	
    	}
    	par3List.add(istack);
    }

    @Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
    	// repair with steeleaf ingots
        return par2ItemStack.getItem() == TFItems.steeleafIngot ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
    }
}
