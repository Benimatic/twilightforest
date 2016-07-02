package twilightforest.item;

import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import twilightforest.block.TFBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBlockTFHugeWaterLily extends ItemBlock {

	public ItemBlockTFHugeWaterLily(Block block) {
		super(block);
	}

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
	{
		RayTraceResult movingobjectposition = this.rayTrace(world, player, true);

        if (movingobjectposition == null)
        {
            return stack;
        }
        else
        {
            if (movingobjectposition.typeOfHit == RayTraceResult.Type.BLOCK)
            {
                int i = movingobjectposition.blockX;
                int j = movingobjectposition.blockY;
                int k = movingobjectposition.blockZ;

                if (!world.canMineBlock(player, i, j, k))
                {
                    return stack;
                }

                if (!player.canPlayerEdit(i, j, k, movingobjectposition.sideHit, stack))
                {
                    return stack;
                }

                if (world.getBlock(i, j, k).getMaterial() == Material.WATER && world.getBlockMetadata(i, j, k) == 0 && world.isAirBlock(i, j + 1, k))
                {
                    // special case for handling block placement with water lilies
                    net.minecraftforge.common.util.BlockSnapshot blocksnapshot = net.minecraftforge.common.util.BlockSnapshot.getBlockSnapshot(world, i, j + 1, k);
                    world.setBlock(i, j + 1, k, TFBlocks.hugeWaterLily);
                    if (net.minecraftforge.event.ForgeEventFactory.onPlayerBlockPlace(player, blocksnapshot, net.minecraftforge.common.util.ForgeDirection.UP).isCanceled())
                    {
                        blocksnapshot.restore(true, false);
                        return stack;
                    }

                    if (!player.capabilities.isCreativeMode)
                    {
                        --stack.stackSize;
                    }
                }
            }

            return stack;
        }
    }

}
