package twilightforest.item;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

import com.google.common.collect.Sets;

import net.minecraft.init.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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
	private Map<ItemStack, Entity> launchedBlocksMap = new IdentityHashMap<ItemStack, Entity>();

	protected ItemTFChainBlock() {
		super(6, 1.6F, TFItems.TOOL_KNIGHTLY, Sets.newHashSet(Blocks.STONE)); // todo 1.9 attack speed
        this.maxStackSize = 1;
        this.setMaxDamage(99);
		this.setCreativeTab(TFItems.creativeTab);

	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
		player.setActiveHand(hand);

		if (!world.isRemote && !this.hasLaunchedBlock(stack)) {
			world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ARROW_SHOOT, player.getSoundCategory(), 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F));

			EntityTFChainBlock launchedBlock = new EntityTFChainBlock(world, player);
			world.spawnEntity(launchedBlock);
			this.setLaunchedBlock(stack, launchedBlock);

			setChainAsThrown(stack);

			stack.damageItem(1, player);

		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}

	/**
	 * Set this item as having been thrown
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
	 */
	public static boolean doesChainHaveBlock(ItemStack stack) {
		return stack.getTagCompound() == null || !stack.getTagCompound().getBoolean("thrown");
	}

	
	/**
	 * Set the spike block belonging to the player as returned
	 */
	public static void setChainAsReturned(EntityPlayer player) {
		if (player != null && player.getActiveItemStack() != null && player.getActiveItemStack().getItem() == TFItems.chainBlock) {
			setChainAsReturned(player.getActiveItemStack());
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
    
    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass)
    {
        if ("pickaxe".equals(toolClass)) {
            return 2;
        } else {
            return -1;
        }
    }
	
    @Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
    	// repair with knightmetal ingots
        return par2ItemStack.getItem() == TFItems.knightMetal ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
    }
}
