package twilightforest.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.text.translation.I18n;
import twilightforest.TwilightForestMod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTFFierySword extends ItemSword {

	public ItemTFFierySword(Item.ToolMaterial par2EnumToolMaterial) {
		super(par2EnumToolMaterial);
		this.setCreativeTab(TFItems.creativeTab);
	}

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> par3List)
    {
    	ItemStack istack = new ItemStack(par1, 1, 0);
    	//istack.addEnchantment(Enchantments.FIREASPECT, 1);
        par3List.add(istack);
    }
    
    @Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
    	return EnumRarity.RARE;
	}

    
    @Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
    	// repair with fiery ingots
        return par2ItemStack.getItem() == TFItems.fieryIngot ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
    }
	
    @Override
	public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLiving, EntityLivingBase par3EntityLiving) {
		boolean result = super.hitEntity(par1ItemStack, par2EntityLiving, par3EntityLiving);
		
		if (result && !par2EntityLiving.isImmuneToFire())
		{
			if (par2EntityLiving.worldObj.isRemote)
			{
				// fire animation!
		        for (int var1 = 0; var1 < 20; ++var1)
		        {
		            double var2 = itemRand.nextGaussian() * 0.02D;
		            double var4 = itemRand.nextGaussian() * 0.02D;
		            double var6 = itemRand.nextGaussian() * 0.02D;
		            double var8 = 10.0D;
		            par2EntityLiving.worldObj.spawnParticle("flame", par2EntityLiving.posX + itemRand.nextFloat() * par2EntityLiving.width * 2.0F - par2EntityLiving.width - var2 * var8, par2EntityLiving.posY + itemRand.nextFloat() * par2EntityLiving.height - var4 * var8, par2EntityLiving.posZ + itemRand.nextFloat() * par2EntityLiving.width * 2.0F - par2EntityLiving.width - var6 * var8, var2, var4, var6);
		        }
			}
			else
			{
				par2EntityLiving.setFire(15);
			}
		}
		
		return result;
	}
    
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List<String> par3List, boolean par4) {
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		par3List.add(I18n.translateToLocal(getUnlocalizedName() + ".tooltip"));
	}
}
