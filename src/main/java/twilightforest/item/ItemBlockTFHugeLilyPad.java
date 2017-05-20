package twilightforest.item;

import java.util.Random;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemColored;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import twilightforest.block.TFBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemLilyPad;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import static twilightforest.block.BlockTFHugeLilyPad.FACING;
import static twilightforest.block.BlockTFHugeLilyPad.PIECE;
import static twilightforest.block.enums.HugeLilypadPiece.*;


public class ItemBlockTFHugeLilyPad extends ItemColored {
	
	private final Random rand = new Random();

	public ItemBlockTFHugeLilyPad(Block block) {
		super(block, false);
	}

	// [VanillaCopy] ItemLilyPad.onItemRightClick, with special logic
	@Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, true);

        if (raytraceresult == null)
        {
            return new ActionResult<>(EnumActionResult.PASS, itemStackIn);
        }
        else
        {
            if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK)
            {
                BlockPos blockpos = raytraceresult.getBlockPos();

                // TF - snap to bigger grid
                blockpos = new BlockPos((blockpos.getX() >> 1) << 1, blockpos.getY(), (blockpos.getZ() >> 1) << 1);

                // TF - check the other 3 spots we touch as well
                if (!worldIn.isBlockModifiable(playerIn, blockpos) || !playerIn.canPlayerEdit(blockpos.offset(raytraceresult.sideHit), raytraceresult.sideHit, itemStackIn)
                        || !worldIn.isBlockModifiable(playerIn, blockpos.east()) || !playerIn.canPlayerEdit(blockpos.offset(raytraceresult.sideHit).east(), raytraceresult.sideHit, itemStackIn)
                        || !worldIn.isBlockModifiable(playerIn, blockpos.south()) || !playerIn.canPlayerEdit(blockpos.offset(raytraceresult.sideHit).south(), raytraceresult.sideHit, itemStackIn)
                        || !worldIn.isBlockModifiable(playerIn, blockpos.east().south()) || !playerIn.canPlayerEdit(blockpos.offset(raytraceresult.sideHit).east().south(), raytraceresult.sideHit, itemStackIn))
                {
                    return new ActionResult<>(EnumActionResult.FAIL, itemStackIn);
                }

                BlockPos blockpos1 = blockpos.up();
                IBlockState iblockstate = worldIn.getBlockState(blockpos);

                if (iblockstate.getMaterial() == Material.WATER && iblockstate.getValue(BlockLiquid.LEVEL) == 0 && worldIn.isAirBlock(blockpos1))
                {
                    // TF - set 4 of them
                    // this seems like a difficult way to generate 2 pseudorandom bits
                    rand.setSeed((blockpos1.getX() * rand.nextLong()) ^ (blockpos1.getZ() * rand.nextLong()) ^ 8890919293L);
                    EnumFacing direction = EnumFacing.HORIZONTALS[rand.nextInt(4)];

                    final IBlockState lilypad = TFBlocks.hugeLilyPad.getDefaultState().withProperty(FACING, direction);
                    worldIn.setBlockState(blockpos1, lilypad.withProperty(PIECE, NW), 11);
                    worldIn.setBlockState(blockpos1.east(), lilypad.withProperty(PIECE, NE), 11);
                    worldIn.setBlockState(blockpos1.east().south(), lilypad.withProperty(PIECE, SE), 11);
                    worldIn.setBlockState(blockpos1.south(), lilypad.withProperty(PIECE, SW), 11);

                    if (!playerIn.capabilities.isCreativeMode)
                    {
                        --itemStackIn.stackSize;
                    }

                    playerIn.addStat(StatList.getObjectUseStats(this));
                    worldIn.playSound(playerIn, blockpos, TFBlocks.hugeLilyPad.getSoundType().getPlaceSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
                    return new ActionResult<>(EnumActionResult.SUCCESS, itemStackIn);
                }
            }

            return new ActionResult<>(EnumActionResult.FAIL, itemStackIn);
        }
    }
}
