package twilightforest.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import twilightforest.TwilightForestMod;
import twilightforest.block.TFBlocks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTFMazebreakerPick extends ItemPickaxe {

	protected ItemTFMazebreakerPick(Item.ToolMaterial par2EnumToolMaterial) {
		super(par2EnumToolMaterial);
		this.setCreativeTab(TFItems.creativeTab);
	}

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> par3List)
    {
    	ItemStack istack = new ItemStack(par1, 1, 0);
    	istack.addEnchantment(Enchantments.EFFICIENCY, 4);
    	istack.addEnchantment(Enchantments.UNBREAKING, 3);
    	istack.addEnchantment(Enchantments.FORTUNE, 2);
        par3List.add(istack);
    }

	@Override
	public float getStrVsBlock(ItemStack par1ItemStack, IBlockState state) {
		float strVsBlock = super.func_150893_a(par1ItemStack, par2Block);
		// 16x strength vs mazestone
		return par2Block == TFBlocks.mazestone ? strVsBlock * 16F : strVsBlock;
	}
}
