package twilightforest.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import twilightforest.block.TFBlocks;

import static twilightforest.block.BlockTFHugeLilyPad.FACING;
import static twilightforest.block.BlockTFHugeLilyPad.PIECE;
import static twilightforest.enums.HugeLilypadPiece.NE;
import static twilightforest.enums.HugeLilypadPiece.NW;
import static twilightforest.enums.HugeLilypadPiece.SE;
import static twilightforest.enums.HugeLilypadPiece.SW;

public class ItemBlockTFHugeLilyPad extends BlockItem {

	public ItemBlockTFHugeLilyPad(Block block, Properties props) {
		super(block, props);
	}

	// [VanillaCopy] ItemLilyPad.onItemRightClick, with special logic
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand hand) {
		ItemStack itemstack = playerIn.getHeldItem(hand);
		RayTraceResult raytraceresult = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.SOURCE_ONLY);

		if (raytraceresult == null) {
			return new ActionResult<>(ActionResultType.PASS, itemstack);
		} else {
			if (raytraceresult.getType() == RayTraceResult.Type.BLOCK) {
				BlockRayTraceResult blockRayTraceResult = (BlockRayTraceResult)raytraceresult;
				BlockPos blockpos = blockRayTraceResult.getPos();
				BlockPos blockpos1 = blockpos.up();
				Direction directionraytrace = blockRayTraceResult.getFace();

				// TF - check the other 3 spots we touch as well
				if (!worldIn.isBlockModifiable(playerIn, blockpos) || !playerIn.canPlayerEdit(blockpos.offset(directionraytrace), directionraytrace, itemstack)
						|| !worldIn.isBlockModifiable(playerIn, blockpos.east()) || !playerIn.canPlayerEdit(blockpos.offset(directionraytrace).east(), directionraytrace, itemstack)
						|| !worldIn.isBlockModifiable(playerIn, blockpos.south()) || !playerIn.canPlayerEdit(blockpos.offset(directionraytrace).south(), directionraytrace, itemstack)
						|| !worldIn.isBlockModifiable(playerIn, blockpos.east().south()) || !playerIn.canPlayerEdit(blockpos.offset(directionraytrace).east().south(), directionraytrace, itemstack)) {
					return new ActionResult<>(ActionResultType.FAIL, itemstack);
				}


				// TF check 4 blocks here
				BlockState iblockstate = worldIn.getBlockState(blockpos);
				BlockState iblockstatee = worldIn.getBlockState(blockpos.east());
				BlockState iblockstatese = worldIn.getBlockState(blockpos.east().south());
				BlockState iblockstates = worldIn.getBlockState(blockpos.south());
				if (iblockstate.getMaterial() == Material.WATER && iblockstate.get(FlowingFluidBlock.LEVEL) == 0
						&& iblockstatee.getMaterial() == Material.WATER && iblockstatee.get(FlowingFluidBlock.LEVEL) == 0
						&& iblockstatee.getMaterial() == Material.WATER && iblockstatee.get(FlowingFluidBlock.LEVEL) == 0
						&& iblockstatese.getMaterial() == Material.WATER && iblockstatee.get(FlowingFluidBlock.LEVEL) == 0
						&& iblockstates.getMaterial() == Material.WATER && iblockstatee.get(FlowingFluidBlock.LEVEL) == 0
						&& worldIn.isAirBlock(blockpos1) && worldIn.isAirBlock(blockpos1.east()) && worldIn.isAirBlock(blockpos1.east().south()) && worldIn.isAirBlock(blockpos1.south())) {
					// TF - set 4 of them
					Direction direction = playerIn.getHorizontalFacing();

					final BlockState lilypad = TFBlocks.huge_lilypad.get().getDefaultState().with(FACING, direction);
					worldIn.setBlockState(blockpos1, lilypad.with(PIECE, NW), 10);
					worldIn.setBlockState(blockpos1.east(), lilypad.with(PIECE, NE), 10);
					worldIn.setBlockState(blockpos1.east().south(), lilypad.with(PIECE, SE), 10);
					worldIn.setBlockState(blockpos1.south(), lilypad.with(PIECE, SW), 11);

					if (playerIn instanceof ServerPlayerEntity) {
						CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) playerIn, blockpos1, itemstack);
						CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) playerIn, blockpos1.east(), itemstack);
						CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) playerIn, blockpos1.east().south(), itemstack);
						CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) playerIn, blockpos1.south(), itemstack);
					}

					if (!playerIn.abilities.isCreativeMode) {
						itemstack.shrink(1);
					}

					playerIn.addStat(Stats.ITEM_USED.get(this));
					worldIn.playSound(playerIn, blockpos, TFBlocks.huge_lilypad.get().getSoundType(iblockstate, worldIn, blockpos, playerIn).getPlaceSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
					return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
				}
			}

			return new ActionResult<>(ActionResultType.FAIL, itemstack);
		}
	}
}
