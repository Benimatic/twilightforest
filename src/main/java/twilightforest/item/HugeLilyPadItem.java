package twilightforest.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.LilyPadItem;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import twilightforest.block.HugeLilyPadBlock;

import static twilightforest.block.HugeLilyPadBlock.FACING;
import static twilightforest.block.HugeLilyPadBlock.PIECE;
import static twilightforest.enums.HugeLilypadPiece.NE;
import static twilightforest.enums.HugeLilypadPiece.NW;
import static twilightforest.enums.HugeLilypadPiece.SE;
import static twilightforest.enums.HugeLilypadPiece.SW;

public class HugeLilyPadItem extends LilyPadItem {

	public HugeLilyPadItem(HugeLilyPadBlock block, Properties props) {
		super(block, props);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
		ItemStack itemstack = player.getHeldItem(hand);
		RayTraceResult raytraceresult = rayTrace(world, player, RayTraceContext.FluidMode.SOURCE_ONLY);
		if (raytraceresult.getType() == RayTraceResult.Type.MISS) {
			return ActionResult.resultPass(itemstack);
		} else {
			if (raytraceresult.getType() == RayTraceResult.Type.BLOCK) {
				BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult)raytraceresult;
				BlockPos blockpos = blockraytraceresult.getPos();
				Direction direction = blockraytraceresult.getFace();
				if (!world.isBlockModifiable(player, blockpos) || !player.canPlayerEdit(blockpos.offset(direction), direction, itemstack)
								// TF - check east, south, southeast as well
								|| !world.isBlockModifiable(player, blockpos.east()) || !player.canPlayerEdit(blockpos.offset(direction).east(), direction, itemstack)
								|| !world.isBlockModifiable(player, blockpos.south()) || !player.canPlayerEdit(blockpos.offset(direction).south(), direction, itemstack)
								|| !world.isBlockModifiable(player, blockpos.east().south()) || !player.canPlayerEdit(blockpos.offset(direction).east().south(), direction, itemstack)
				) {
					return ActionResult.resultFail(itemstack);
				}

				BlockPos blockpos1 = blockpos.up();
				BlockState blockstate = world.getBlockState(blockpos);
				Material material = blockstate.getMaterial();
				FluidState ifluidstate = world.getFluidState(blockpos);
				if ((ifluidstate.getFluid() == Fluids.WATER || material == Material.ICE) && world.isAirBlock(blockpos1)
								// TF - check east, south, southeast as well
								&& (world.getFluidState(blockpos.east()).getFluid() == Fluids.WATER || world.getBlockState(blockpos.east()).getMaterial() == Material.ICE) && world.isAirBlock(blockpos1.east())
								&& (world.getFluidState(blockpos.south()).getFluid() == Fluids.WATER || world.getBlockState(blockpos.south()).getMaterial() == Material.ICE) && world.isAirBlock(blockpos1.south())
								&& (world.getFluidState(blockpos.east().south()).getFluid() == Fluids.WATER || world.getBlockState(blockpos.east().south()).getMaterial() == Material.ICE) && world.isAirBlock(blockpos1.east().south())
				) {
					// TF - use our own block. dispense with the blocksnapshot stuff for now due to complexity. FIXME: Implement it
					final BlockState lilypad = getBlock().getDefaultState().with(FACING, player.getHorizontalFacing());
					world.setBlockState(blockpos1, lilypad.with(PIECE, NW), 11);
					world.setBlockState(blockpos1.east(), lilypad.with(PIECE, NE), 11);
					world.setBlockState(blockpos1.east().south(), lilypad.with(PIECE, SE), 11);
					world.setBlockState(blockpos1.south(), lilypad.with(PIECE, SW), 11);

					if (player instanceof ServerPlayerEntity) {
						CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity)player, blockpos1, itemstack);
						CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity)player, blockpos1.east(), itemstack);
						CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity)player, blockpos1.east().south(), itemstack);
						CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity)player, blockpos1.south(), itemstack);
					}

					if (!player.abilities.isCreativeMode) {
						itemstack.shrink(1);
					}

					player.addStat(Stats.ITEM_USED.get(this));
					world.playSound(player, blockpos, SoundEvents.BLOCK_LILY_PAD_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
					return ActionResult.resultSuccess(itemstack);
				}
			}

			return ActionResult.resultFail(itemstack);
		}
	}
}
