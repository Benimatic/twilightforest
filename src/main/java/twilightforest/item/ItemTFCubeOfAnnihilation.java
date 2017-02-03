package twilightforest.item;

import java.util.HashMap;

import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import twilightforest.entity.EntityTFCubeOfAnnihilation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemTFCubeOfAnnihilation extends ItemTF {
	private HashMap<ItemStack, Entity> launchedCubesMap = new HashMap<ItemStack, Entity>();
	
	protected ItemTFCubeOfAnnihilation() {
        this.maxStackSize = 1;
		this.setCreativeTab(TFItems.creativeTab);

	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
		player.setActiveHand(hand);

		if (!world.isRemote && !this.hasLaunchedCube(stack)) {
			EntityTFCubeOfAnnihilation launchedCube = new EntityTFCubeOfAnnihilation(world, player);

			world.spawnEntity(launchedCube);
			
			this.setLaunchedCube(stack, launchedCube);
			setCubeAsThrown(stack);
		}


		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
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
		if (player != null && player.getActiveItemStack() != null && player.getActiveItemStack().getItem() == TFItems.cubeOfAnnihilation) {
			setCubeAsReturned(player.getActiveItemStack());
		}
	}

	public boolean hasLaunchedCube(ItemStack stack) {
		Entity cube = this.launchedCubesMap.get(stack);
		
		return cube != null && !cube.isDead;
	}
	
	public void setLaunchedCube(ItemStack stack, EntityTFCubeOfAnnihilation launchedCube) {
		this.launchedCubesMap.put(stack, launchedCube);
	}

    @Override
	public void onUsingTick(ItemStack stack, EntityLivingBase living, int count) {
//		if (stack.getItemDamage() >= this.getMaxDamage()) {
//			// do not use
//			player.stopUsingItem();
//			return;
//		}
	}

    @Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 72000;
    }
    
    @Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.BLOCK;
    }
}
