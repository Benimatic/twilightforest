package twilightforest.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;
import twilightforest.block.BlockTFTowerDevice;
import twilightforest.block.TFBlocks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTFTowerKey extends ItemTF 
{

	@Override
	public EnumActionResult onItemUse(ItemStack itemStack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float fx, float fy, float fz)
	{
		if (!world.isRemote && world.getBlock(x, y, z) == TFBlocks.towerDevice && world.getBlockMetadata(x, y, z) == BlockTFTowerDevice.META_VANISH_LOCKED)
		{
			BlockTFTowerDevice.unlockBlock(world, x, y, z);
			--itemStack.stackSize;

			return true;
		}

		return false;
	}

}
