package twilightforest.item;

import java.util.HashMap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import twilightforest.TwilightForestMod;
import twilightforest.entity.EntityTFChainBlock;
import twilightforest.entity.EntityTFCubeOfAnnihilation;
import twilightforest.entity.EntityTFTwilightWandBolt;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemTFCubeOfAnnihilation extends ItemTF {
	
	private IIcon annihilateIcon;


	private HashMap<ItemStack, Entity> launchedCubesMap = new HashMap<ItemStack, Entity>();
	
	protected ItemTFCubeOfAnnihilation() {
		super();
        this.maxStackSize = 1;
		this.setCreativeTab(TFItems.creativeTab);

	}
	
    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		return false;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World worldObj, EntityPlayer player) {
		player.setItemInUse(stack, this.getMaxItemUseDuration(stack));

		if (!worldObj.isRemote && !this.hasLaunchedCube(stack)) {
			EntityTFCubeOfAnnihilation launchedCube = new EntityTFCubeOfAnnihilation(worldObj, player);

			worldObj.spawnEntityInWorld(launchedCube);
			
			this.setLaunchedCube(stack, launchedCube);
			setCubeAsThrown(stack);
		}


		return stack;
	}
	
	/**
	 * Set this item as having been thrown
	 * @param stack
	 */
	public static void setCubeAsThrown(ItemStack stack) {
		// set NBT tag for stack
		if (stack.getTagCompound() == null) {
			stack.setTagCompound(new NBTTagCompound());
		}
		stack.getTagCompound().setBoolean("thrown", true);
	}

	/**
	 * Set the cube for this item as returned to the player
	 * @param stack
	 */
	public static void setCubeAsReturned(ItemStack stack) {
		// set NBT tag for stack
		if (stack.getTagCompound() == null) {
			stack.setTagCompound(new NBTTagCompound());
		}
		stack.getTagCompound().setBoolean("thrown", false);
	}
	

	/**
	 * Method for the client to determine if the cube has been thrown or not.  Not as accurate as server method due to lag, etc.
	 * @param stack
	 */
	public static boolean doesTalismanHaveCube(ItemStack stack) {
		if (stack.getTagCompound() == null) {
			return true;
		} else {
			return !stack.getTagCompound().getBoolean("thrown");
		}
	}

	
	/**
	 * Set the cube belonging to the player as returned
	 * @param player
	 */
	public static void setCubeAsReturned(EntityPlayer player) {
		if (player != null && player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == TFItems.cubeOfAnnihilation) {
			setCubeAsReturned(player.getCurrentEquippedItem());
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
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
		
		if (this.doesTalismanHaveCube(stack)) {
			return this.itemIcon;
		} else {
			return TFItems.cubeTalisman.getIconIndex(stack);
		}
	}

	public boolean hasLaunchedCube(ItemStack stack) {
		Entity cube = this.launchedCubesMap.get(stack);
		
		return cube != null && !cube.isDead;
	}
	
	public void setLaunchedCube(ItemStack stack, EntityTFCubeOfAnnihilation launchedCube) {
		this.launchedCubesMap.put(stack, launchedCube);
	}

	/**
	 * Called each tick while using an item.
	 * @param stack The Item being used
	 * @param player The Player using the item
     * @param count The amount of time in tick the item has been used for continuously
     */
    @Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
//		if (stack.getItemDamage() >= this.getMaxDamage()) {
//			// do not use
//			player.stopUsingItem();
//			return;
//		}
    	


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
	 * Properly register icon source
	 */
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon(TwilightForestMod.ID + ":" + this.getUnlocalizedName().substring(5));
        this.annihilateIcon = par1IconRegister.registerIcon(TwilightForestMod.ID + ":annihilate_particle");
    }
    
    public IIcon getAnnihilateIcon() {
    	return this.annihilateIcon;
    }
    
}
