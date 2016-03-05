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
    	this.setTextureName(TwilightForestMod.ID + ":icebow");
		this.setCreativeTab(TFItems.creativeTab);
    }

	/**
	 * Get the arrow for this specific bow
	 */
    @Override
	protected EntityArrow getArrow(World world, EntityPlayer entityPlayer, float velocity) {
		return new EntityIceArrow(world, entityPlayer, velocity);
	}

    /**
     * Return whether this item is repairable in an anvil.
     */
    @Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
    	// repair with ice blocks
        return par2ItemStack.getItem() == Item.getItemFromBlock(Blocks.ice) ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
    }
}
