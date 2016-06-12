package twilightforest.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemMapBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import twilightforest.TFAchievementPage;
import twilightforest.TFFeature;
import twilightforest.TFMazeMapData;
import twilightforest.TwilightForestMod;
import twilightforest.world.WorldProviderTwilightForest;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTFEmptyMazeMap extends ItemMapBase
{
	boolean mapOres; 
	
    protected ItemTFEmptyMazeMap(boolean mapOres)
    {
        super();
		this.setCreativeTab(TFItems.creativeTab);
        this.mapOres = mapOres;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    @Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        ItemStack mapItem = new ItemStack(mapOres ? TFItems.oreMap : TFItems.mazeMap, 1, par2World.getUniqueDataId(ItemTFMazeMap.STR_ID));
        String var5 = "mazemap_" + mapItem.getItemDamage();
        TFMazeMapData mapData = new TFMazeMapData(var5);
        par2World.setItemData(var5, mapData);
        mapData.scale = 0;
        int step = 128 * (1 << mapData.scale);
        // need to fix center for feature offset
        if (par2World.provider instanceof WorldProviderTwilightForest && TFFeature.getFeatureForRegion(MathHelper.floor_double(par3EntityPlayer.posX) >> 4, MathHelper.floor_double(par3EntityPlayer.posZ) >> 4, par2World) == TFFeature.labyrinth) {
        	BlockPos mc = TFFeature.getNearestCenterXYZ(MathHelper.floor_double(par3EntityPlayer.posX) >> 4, MathHelper.floor_double(par3EntityPlayer.posZ) >> 4, par2World);
            mapData.xCenter = mc.posX;
            mapData.zCenter = mc.posZ;
            mapData.yCenter = MathHelper.floor_double(par3EntityPlayer.posY);
        } else {
            mapData.xCenter = (int)(Math.round(par3EntityPlayer.posX / step) * step) + 10; // mazes are offset slightly
            mapData.zCenter = (int)(Math.round(par3EntityPlayer.posZ / step) * step) + 10; // mazes are offset slightly
            mapData.yCenter = MathHelper.floor_double(par3EntityPlayer.posY);
        }
        mapData.dimension = par2World.provider.dimensionId;
        mapData.markDirty();
        --par1ItemStack.stackSize;
        
        //cheevos
    	if (mapItem.getItem() == TFItems.mazeMap) {
    		par3EntityPlayer.addStat(TFAchievementPage.twilightMazeMap);
    	}
    	if (mapItem.getItem() == TFItems.oreMap) {
    		par3EntityPlayer.addStat(TFAchievementPage.twilightOreMap);
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
