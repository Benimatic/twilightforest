package twilightforest.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemMapBase;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;
import twilightforest.TFAchievementPage;
import twilightforest.TFFeature;
import twilightforest.TFMazeMapData;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.world.WorldProviderTwilightForest;

public class ItemTFEmptyMazeMap extends ItemMapBase implements ModelRegisterCallback
{
	boolean mapOres; 
	
    protected ItemTFEmptyMazeMap(boolean mapOres)
    {
		this.setCreativeTab(TFItems.creativeTab);
        this.mapOres = mapOres;
    }

    // [VanillaCopy] ItemEmptyMap.onItemRightClick calling own setup method
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = ItemTFMazeMap.setupNewMap(worldIn, playerIn.posX, playerIn.posZ, (byte)0, true, false, playerIn.posY);
        ItemStack itemstack1 = playerIn.getHeldItem(handIn);
        itemstack1.shrink(1);

        if (itemstack1.isEmpty())
        {
            return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
        }
        else
        {
            if (!playerIn.inventory.addItemStackToInventory(itemstack.copy()))
            {
                playerIn.dropItem(itemstack, false);
            }

            playerIn.addStat(StatList.getObjectUseStats(this));
            return new ActionResult<>(EnumActionResult.SUCCESS, itemstack1);
        }
    }
}
