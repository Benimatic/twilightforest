package twilightforest.item;

import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import twilightforest.TwilightForestMod;
import twilightforest.entity.EntityTFCubeOfAnnihilation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ItemTFCubeOfAnnihilation extends ItemTF {
	protected ItemTFCubeOfAnnihilation() {
        this.maxStackSize = 1;
		this.setCreativeTab(TFItems.creativeTab);
		this.addPropertyOverride(new ResourceLocation(TwilightForestMod.ID, "thrown"), new IItemPropertyGetter() {
			@SideOnly(Side.CLIENT)
			@Override
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
				return isThrown(stack) ? 1 : 0;
			}
		});
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
		player.setActiveHand(hand);

		if (!world.isRemote && !isThrown(stack)) {
			EntityTFCubeOfAnnihilation launchedCube = new EntityTFCubeOfAnnihilation(world, player);

			world.spawnEntity(launchedCube);

			setCubeAsThrown(stack);
		}


		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}

	private static boolean isThrown(ItemStack stack) {
		return stack.getTagCompound() != null && stack.getTagCompound().getBoolean("thrown");
	}

	private static void setCubeAsThrown(ItemStack stack) {
		// set NBT tag for stack
		if (stack.getTagCompound() == null) {
			stack.setTagCompound(new NBTTagCompound());
		}
		stack.getTagCompound().setBoolean("thrown", true);
	}

	private static void setCubeAsReturned(ItemStack stack) {
		// set NBT tag for stack
		if (stack.getTagCompound() == null) {
			stack.setTagCompound(new NBTTagCompound());
		}
		stack.getTagCompound().setBoolean("thrown", false);
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
