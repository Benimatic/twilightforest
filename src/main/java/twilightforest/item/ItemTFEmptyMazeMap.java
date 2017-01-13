package twilightforest.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemMapBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import twilightforest.TFAchievementPage;
import twilightforest.TFFeature;
import twilightforest.TFMazeMapData;
import twilightforest.world.WorldProviderTwilightForest;

public class ItemTFEmptyMazeMap extends ItemMapBase
{
	boolean mapOres; 
	
    protected ItemTFEmptyMazeMap(boolean mapOres)
    {
		this.setCreativeTab(TFItems.creativeTab);
        this.mapOres = mapOres;
    }

    @Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, EnumHand hand)
    {
        ItemStack mapItem = new ItemStack(mapOres ? TFItems.oreMap : TFItems.mazeMap, 1, par2World.getUniqueDataId(ItemTFMazeMap.STR_ID));
        String var5 = "mazemap_" + mapItem.getItemDamage();
        TFMazeMapData mapData = new TFMazeMapData(var5);
        par2World.setItemData(var5, mapData);
        mapData.scale = 0;
        int step = 128 * (1 << mapData.scale);
        // need to fix center for feature offset
        if (par2World.provider instanceof WorldProviderTwilightForest && TFFeature.getFeatureForRegion(MathHelper.floor(par3EntityPlayer.posX) >> 4, MathHelper.floor(par3EntityPlayer.posZ) >> 4, par2World) == TFFeature.labyrinth) {
        	BlockPos mc = TFFeature.getNearestCenterXYZ(MathHelper.floor(par3EntityPlayer.posX) >> 4, MathHelper.floor(par3EntityPlayer.posZ) >> 4, par2World);
            mapData.xCenter = mc.getX();
            mapData.zCenter = mc.getZ();
            mapData.yCenter = MathHelper.floor(par3EntityPlayer.posY);
        } else {
            mapData.xCenter = (int)(Math.round(par3EntityPlayer.posX / step) * step) + 10; // mazes are offset slightly
            mapData.zCenter = (int)(Math.round(par3EntityPlayer.posZ / step) * step) + 10; // mazes are offset slightly
            mapData.yCenter = MathHelper.floor(par3EntityPlayer.posY);
        }
        mapData.dimension = par2World.provider.getDimension();
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
            return ActionResult.newResult(EnumActionResult.SUCCESS, par1ItemStack);
        }
        else
        {
            if (!par3EntityPlayer.inventory.addItemStackToInventory(mapItem.copy()))
            {
                par3EntityPlayer.dropItem(mapItem, false);
            }

            return ActionResult.newResult(EnumActionResult.SUCCESS, par1ItemStack);
        }
    }
}
