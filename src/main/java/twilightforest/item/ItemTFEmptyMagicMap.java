package twilightforest.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemMapBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;
import twilightforest.TFAchievementPage;
import twilightforest.TFMagicMapData;
import twilightforest.TwilightForestMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTFEmptyMagicMap extends ItemMapBase
{
    protected ItemTFEmptyMagicMap()
    {
        super();
		this.setCreativeTab(TFItems.creativeTab);
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    @Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        ItemStack mapItem = new ItemStack(TFItems.magicMap, 1, par2World.getUniqueDataId(ItemTFMagicMap.STR_ID));
        String mapName = ItemTFMagicMap.STR_ID + "_" + mapItem.getItemDamage();
        MapData mapData = new TFMagicMapData(mapName);
        par2World.setItemData(mapName, mapData);
        mapData.scale = 4;
        int step = 128 * (1 << mapData.scale);
        mapData.xCenter = (int)(Math.round(par3EntityPlayer.posX / step) * step);
        mapData.zCenter = (int)(Math.round(par3EntityPlayer.posZ / step) * step);
        mapData.dimension = (byte)par2World.provider.dimensionId;
        mapData.markDirty();
        --par1ItemStack.stackSize;
        
        //cheevo
    	if (mapItem.getItem() == TFItems.magicMap) {
    		par3EntityPlayer.triggerAchievement(TFAchievementPage.twilightMagicMap);
    	}	

        if (par1ItemStack.stackSize <= 0)
        {
            return mapItem;
        }
        else
        {
            if (!par3EntityPlayer.inventory.addItemStackToInventory(mapItem.copy()))
            {
                par3EntityPlayer.dropPlayerItemWithRandomChoice(mapItem, false);
            }

            return par1ItemStack;
        }
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
