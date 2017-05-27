package twilightforest.item;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;

import com.google.common.collect.Multimap;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import twilightforest.client.ModelRegisterCallback;

public class ItemTFKnightlyPick extends ItemPickaxe implements ModelRegisterCallback {

	protected ItemTFKnightlyPick(Item.ToolMaterial par2EnumToolMaterial) {
		super(par2EnumToolMaterial);
		this.setCreativeTab(TFItems.creativeTab);
	}

    @Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
    	return EnumRarity.RARE;
	}
    
    @Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
        return par2ItemStack.getItem() == TFItems.knightMetal || super.getIsRepairable(par1ItemStack, par2ItemStack);
    }

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List<String> par3List, boolean par4) {
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		par3List.add(I18n.translateToLocal(getUnlocalizedName() + ".tooltip"));
	}
}
