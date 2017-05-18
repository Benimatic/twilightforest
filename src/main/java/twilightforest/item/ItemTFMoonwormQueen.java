package twilightforest.item;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
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
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
		if (stack.getItemDamage() < getMaxDamage(stack))
		{
			player.setActiveHand(hand);
		}
		else 
		{
			player.resetActiveHand();
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		// adjust x, y, z for which block we're placing onto
		IBlockState currentState = world.getBlockState(pos);
		Block currentBlock = currentState.getBlock();

		if (currentBlock == TFBlocks.moonworm)
        {
        	return EnumActionResult.FAIL;
        }

        // don't wear item out, leave it at 0 uses left so that it can be recharged
		if (stack != null && stack.getItemDamage() == getMaxDamage(stack))
		{
			return EnumActionResult.FAIL;
		}
        
        if (currentBlock == Blocks.SNOW)
        {
            side = EnumFacing.UP;
        }
        else if (currentBlock != Blocks.VINE && currentBlock != Blocks.TALLGRASS && currentBlock != Blocks.DEADBUSH
                && (currentBlock == Blocks.AIR || !currentBlock.isReplaceable(world, pos)))
        {
        	pos = pos.offset(side);
        }
        
        // try to place firefly
		if (world.canBlockBePlaced(TFBlocks.moonworm, pos, false, side, player, stack))
		{
	        IBlockState placementState = TFBlocks.moonworm.getStateForPlacement(world, pos, side, hitX, hitY, hitZ, 0, player, null);
			if (world.setBlockState(pos, placementState, 3))
			{
				if (world.getBlockState(pos).getBlock() == TFBlocks.moonworm)
				{
					//((BlockTFMoonworm) TFBlocks.moonworm).updateBlockMetadata(world, x, y, z, side, hitX, hitY, hitZ);
					TFBlocks.moonworm.onBlockPlacedBy(world, pos, placementState, player, stack);
				}

				SoundType sound = TFBlocks.moonworm.getSoundType(placementState, world, pos, player);
				world.playSound(player, pos, sound.getPlaceSound(), SoundCategory.BLOCKS, sound.getVolume() / 2.0F, sound.getPitch() * 0.8F);
				
				if (stack != null)
				{
	    			stack.damageItem(1, player);
	    			player.stopActiveHand();
				}
			}


			return EnumActionResult.SUCCESS;
		}
		else
		{
			return EnumActionResult.FAIL;
		}
	}

    @Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase living, int useRemaining)
    {
    	int useTime = this.getMaxItemUseDuration(stack) - useRemaining;


    	if (!world.isRemote && useTime > FIRING_TIME && (stack.getItemDamage() + 1) < this.getMaxDamage())
    	{
    		boolean fired = world.spawnEntity(new EntityTFMoonwormShot(world, living));

    		if (fired)
    		{
    			stack.damageItem(2, living);

				world.playSound(null, living.posX, living.posY, living.posZ, SoundEvents.BLOCK_SLIME_HIT, living instanceof EntityPlayer ? SoundCategory.PLAYERS : SoundCategory.NEUTRAL, 1, 1);
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
