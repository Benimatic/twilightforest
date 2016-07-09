package twilightforest.item;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.EntityEquipmentSlot;
import twilightforest.block.BlockTFGiantBlock;
import twilightforest.block.TFBlocks;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;

import com.google.common.collect.Multimap;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTFGiantPick extends ItemPickaxe {

	private float damageVsEntity;

	protected ItemTFGiantPick(Item.ToolMaterial par2EnumToolMaterial) {
		super(par2EnumToolMaterial);
		this.setCreativeTab(TFItems.creativeTab);
		this.damageVsEntity = 4 + par2EnumToolMaterial.getDamageVsEntity();

	}

    @Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
    	return EnumRarity.RARE;
	}
    
    @Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
    	// repair with knightmetal ingots
        return par2ItemStack.getItem() == TFItems.knightMetal ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
    }
  	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List<String> par3List, boolean par4) {
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		par3List.add(I18n.translateToLocal(getUnlocalizedName() + ".tooltip"));
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
    {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
        // remove old damage value
        multimap.removeAll(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName());
        // add new one
        multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", (double)this.damageVsEntity, 0));
        return multimap;
    }

	@Override
	public float getStrVsBlock(ItemStack par1ItemStack, IBlockState par2Block) {
		float strVsBlock = super.getStrVsBlock(par1ItemStack, par2Block);
		// extra 64X strength vs giant obsidian
		strVsBlock *= (par2Block  == TFBlocks.giantObsidian) ? 64 : 1;
		// 64x strength vs giant blocks
		return isGiantBlock(par2Block.getBlock()) ? strVsBlock * 64 : strVsBlock;
	}

	private boolean isGiantBlock(Block block) {
		return block instanceof BlockTFGiantBlock;
	}


}
