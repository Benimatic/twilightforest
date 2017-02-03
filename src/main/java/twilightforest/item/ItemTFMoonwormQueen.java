package twilightforest.item;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import twilightforest.block.TFBlocks;
import twilightforest.entity.EntityTFMoonwormShot;

public class ItemTFMoonwormQueen extends ItemTF
{

	private static final int FIRING_TIME = 12;

	protected ItemTFMoonwormQueen() {
		this.setCreativeTab(TFItems.creativeTab);
		this.maxStackSize = 1;
        this.setMaxDamage(256);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack par1ItemStack, World world, EntityPlayer player, EnumHand hand) {
		if (par1ItemStack.getItemDamage() < this.getMaxDamage()) 
		{
			player.setActiveHand(hand);
		}
		else 
		{
			player.resetActiveHand();
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, par1ItemStack);
	}

	@Override
	public EnumActionResult onItemUse(ItemStack par1ItemStack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		// adjust x, y, z for which block we're placing onto
        Block currentBlockID = world.getBlock(x, y, z);
        
        if (currentBlockID == TFBlocks.moonworm)
        {
        	return false;
        }

        // don't wear item out, leave it at 0 uses left so that it can be recharged
		if (par1ItemStack != null && par1ItemStack.getItemDamage() == this.getMaxDamage()) 
		{
			return false;
		}
        
        if (currentBlockID == Blocks.SNOW)
        {
            side = 1;
        }
        else if (currentBlockID != Blocks.VINE && currentBlockID != Blocks.TALLGRASS && currentBlockID != Blocks.DEADBUSH
                && (currentBlockID == Blocks.AIR || !currentBlockID.isReplaceable(world, x, y, z)))
        {
            if (side == 0)
            {
                --y;
            }

            if (side == 1)
            {
                ++y;
            }

            if (side == 2)
            {
                --z;
            }

            if (side == 3)
            {
                ++z;
            }

            if (side == 4)
            {
                --x;
            }

            if (side == 5)
            {
                ++x;
            }
        }
        
        // try to place firefly
		if (world.canPlaceEntityOnSide(TFBlocks.moonworm, x, y, z, false, side, player, par1ItemStack))
		{
	        int placementMeta = TFBlocks.moonworm.onBlockPlaced(world, x, y, z, side, hitX, hitY, hitZ, 0);
			if (world.setBlock(x, y, z, TFBlocks.moonworm, placementMeta, 3))
			{
				if (world.getBlock(x, y, z) == TFBlocks.moonworm)
				{
					//((BlockTFMoonworm) TFBlocks.moonworm).updateBlockMetadata(world, x, y, z, side, hitX, hitY, hitZ);
					TFBlocks.moonworm.onBlockPlacedBy(world, x, y, z, player, par1ItemStack);
				}

				world.playSoundEffect((double)(x + 0.5F), (double)(y + 0.5F), (double)(z + 0.5F), this.getSound(), TFBlocks.moonworm.stepSound.getVolume() / 2.0F, TFBlocks.moonworm.stepSound.getPitch() * 0.8F);
				
				if (par1ItemStack != null)
				{
	    			par1ItemStack.damageItem(1, player);
					player.stopUsingItem();
				}
			}


			return true;
		}
		else
		{
			return false;
		}
	}
	
	public SoundEvent getSound()
	{
		return "mob.slime.big";
	}

    @Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World world, EntityLivingBase living, int useRemaining)
    {
    	int useTime = this.getMaxItemUseDuration(par1ItemStack) - useRemaining;


    	if (!world.isRemote && useTime > FIRING_TIME && (par1ItemStack.getItemDamage() + 1) < this.getMaxDamage()) 
    	{
    		boolean fired = world.spawnEntity(new EntityTFMoonwormShot(world, living));

    		if (fired)
    		{
    			par1ItemStack.damageItem(2, living);

				world.playSound(null, living.posX, living.posY, living.posZ, getSound(), living instanceof EntityPlayer ? SoundCategory.PLAYERS : SoundCategory.NEUTRAL, 1, 1);
    		}
    	}

    }

    @Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.BOW;
    }
    
    @Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 72000;
    }

}
