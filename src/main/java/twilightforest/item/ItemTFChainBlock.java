package twilightforest.item;

import java.util.HashMap;

import com.google.common.collect.Sets;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import twilightforest.TwilightForestMod;
import twilightforest.entity.EntityTFChainBlock;
import twilightforest.entity.EntityTFCubeOfAnnihilation;
import twilightforest.entity.EntityTFTwilightWandBolt;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemTFChainBlock extends ItemTool {
	
	// which items have launched which blocks?
	private HashMap<ItemStack, Entity> launchedBlocksMap = new HashMap<ItemStack, Entity>();

	protected ItemTFChainBlock() {
		super(6, TFItems.TOOL_KNIGHTLY, Sets.newHashSet(new Block[] {Blocks.stone}));
        this.maxStackSize = 1;
        this.setMaxDamage(99);
		this.setCreativeTab(TFItems.creativeTab);

	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World worldObj, EntityPlayer player) {
		player.setItemInUse(stack, this.getMaxItemUseDuration(stack));

		if (!worldObj.isRemote && !this.hasLaunchedBlock(stack)) {

			worldObj.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F));


			EntityTFChainBlock launchedBlock = new EntityTFChainBlock(worldObj, player);
			worldObj.spawnEntityInWorld(launchedBlock);
			this.setLaunchedBlock(stack, launchedBlock);

			setChainAsThrown(stack);

			stack.damageItem(1, player);

		}
		return stack;
	}

	/**
	 * Set this item as having been thrown
	 * @param stack
	 */
	public static void setChainAsThrown(ItemStack stack) {
		// set NBT tag for stack
		if (stack.getTagCompound() == null) {
			stack.setTagCompound(new NBTTagCompound());
		}
		stack.getTagCompound().setBoolean("thrown", true);
	}

	/**
	 * Set the spike block for this item as returned to the player
	 * @param stack
	 */
	public static void setChainAsReturned(ItemStack stack) {
		// set NBT tag for stack
		if (stack.getTagCompound() == null) {
			stack.setTagCompound(new NBTTagCompound());
		}
		stack.getTagCompound().setBoolean("thrown", false);
	}
	

	/**
	 * Method for the client to determine if the block has been thrown or not.  Not as accurate as server method due to lag, etc.
	 * @param stack
	 */
	public static boolean doesChainHaveBlock(ItemStack stack) {
		if (stack.getTagCompound() == null) {
			return true;
		} else {
			return !stack.getTagCompound().getBoolean("thrown");
		}
	}

	
	/**
	 * Set the spike block belonging to the player as returned
	 * @param player
	 */
	public static void setChainAsReturned(EntityPlayer player) {
		if (player != null && player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == TFItems.chainBlock) {
			setChainAsReturned(player.getCurrentEquippedItem());
		}
	}

	/**
	 * Method on the server that determines definitively if the item has been thrown or not
	 */
	public boolean hasLaunchedBlock(ItemStack stack) {
		Entity cube = this.launchedBlocksMap.get(stack);
		
		return cube != null && !cube.isDead;
	}
	
	public void setLaunchedBlock(ItemStack stack, EntityTFChainBlock launchedCube) {
		this.launchedBlocksMap.put(stack, launchedCube);
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
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
		
		if (this.doesChainHaveBlock(stack)) {
			return this.itemIcon;
		} else {
			return TFItems.knightmetalRing.getIconIndex(stack);
		}
	}
	
	/**
     * How long it takes to use or consume an item
     */
    @Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 72000;
    }
    
    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    @Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.block;
    }
    

    /**
     * Called when a entity tries to play the 'swing' animation.
     *
     * @param entityLiving The entity swinging the item.
     * @param stack The Item stack
     * @return True to cancel any further processing by EntityLiving
     */
	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		return false;
	}
	
	
    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass)
    {
        if (toolClass != null && toolClass.equals("pickaxe")) {
            return 2;
        } else {
            return -1;
        }
    }
	
    /**
     * Return whether this item is repairable in an anvil.
     */
    @Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
    	// repair with knightmetal ingots
        return par2ItemStack.getItem() == TFItems.knightMetal ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
    }
}
