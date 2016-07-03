package twilightforest.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;
import twilightforest.entity.EntityIceArrow;

public class ItemTFIceBow extends ItemTFBowBase {
	
	
    public ItemTFIceBow() {
		this.setCreativeTab(TFItems.creativeTab);
    }

    @Override
	protected EntityArrow getArrow(World world, EntityPlayer entityPlayer, float velocity) {
		return new EntityIceArrow(world, entityPlayer, velocity);
	}

    @Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
    	// repair with ice blocks
        return par2ItemStack.getItem() == Item.getItemFromBlock(Blocks.ICE) ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
    }
}
