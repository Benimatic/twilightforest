package twilightforest.item;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;
import twilightforest.block.TFBlocks;
import twilightforest.entity.EntityTFMoonwormShot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTFMoonwormQueen extends ItemTF
{

	private static final int FIRING_TIME = 12;
	
	private IIcon[] icons;
	private String[] iconNames = new String[] {"moonwormQueen", "moonwormQueenAlt"};

	protected ItemTFMoonwormQueen() {
		super();
		this.setCreativeTab(TFItems.creativeTab);
		this.maxStackSize = 1;
        this.setMaxDamage(256);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World world, EntityPlayer player) {
		if (par1ItemStack.getItemDamage() < this.getMaxDamage()) 
		{
			player.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		}
		else 
		{
			player.stopUsingItem();
		}
		return par1ItemStack;
	}

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
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
        
        if (currentBlockID == Blocks.snow)
        {
            side = 1;
        }
        else if (currentBlockID != Blocks.vine && currentBlockID != Blocks.tallgrass && currentBlockID != Blocks.deadbush
                && (currentBlockID == Blocks.air || !currentBlockID.isReplaceable(world, x, y, z)))
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
	
	public String getSound()
	{
		return "mob.slime.big";
	}

    /**
     * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
     */
    @Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World world, EntityPlayer player, int useRemaining)
    {
    	int useTime = this.getMaxItemUseDuration(par1ItemStack) - useRemaining;


    	if (!world.isRemote && useTime > FIRING_TIME && (par1ItemStack.getItemDamage() + 1) < this.getMaxDamage()) 
    	{
    		boolean fired = world.spawnEntityInWorld(new EntityTFMoonwormShot(world, player));

    		if (fired)
    		{
    			par1ItemStack.damageItem(2, player);

    			world.playSoundAtEntity(player, this.getSound(), 1.0F, 1.0F);
    		}
    	}

    }
	
    /**
     * Player, Render pass, and item usage sensitive version of getIconIndex.
     *   
     * @param stack The item stack to get the icon for. (Usually this, and usingItem will be the same if usingItem is not null)
     * @param renderPass The pass to get the icon for, 0 is default.
     * @param player The player holding the item
     * @param usingItem The item the player is actively using. Can be null if not using anything.
     * @param useRemaining The ticks remaining for the active item.
     * @return The icon index
     */
    @Override
    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
    	if (usingItem != null && usingItem.getItem() == this)
    	{
    		int useTime = usingItem.getMaxItemUseDuration() - useRemaining;
    		if (useTime >= FIRING_TIME) 
    		{
    			return (useTime >> 1) % 2 == 0 ? this.icons[0] : this.icons[1];
    		}
    	}
    	return this.icons[0];

    }
    
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
        super.registerIcons(par1IconRegister);
        this.icons = new IIcon[iconNames.length];

        for (int i = 0; i < this.iconNames.length; ++i)
        {
            this.icons[i] = par1IconRegister.registerIcon(TwilightForestMod.ID + ":" + iconNames[i]);
        }
    }

	/**
     * returns the action that specifies what animation to play when the items is being used
     */
    @Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.bow;
    }
    
    /**
     * How long it takes to use or consume an item
     */
    @Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 72000;
    }

}
