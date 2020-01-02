package twilightforest.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import twilightforest.block.TFBlocks;

public class ItemBlockTFHugeWaterLily extends BlockItem {

	public ItemBlockTFHugeWaterLily(Block block, Properties props) {
		super(block, props);
	}

	// [VanillaCopy] ItemLilyPad.onItemRightClick, edits noted
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
				Direction direction = blockRayTraceResult.getFace();

				if (!worldIn.isBlockModifiable(playerIn, blockpos) || !playerIn.canPlayerEdit(blockpos.offset(direction), direction, itemstack)) {
					return new ActionResult<>(ActionResultType.FAIL, itemstack);
				}

				BlockPos blockpos1 = blockpos.up();
				BlockState iblockstate = worldIn.getBlockState(blockpos);

				if (iblockstate.getMaterial() == Material.WATER && iblockstate.get(FlowingFluidBlock.LEVEL) == 0 && worldIn.isAirBlock(blockpos1)) {
					// special case for handling block placement with water lilies
					net.minecraftforge.common.util.BlockSnapshot blocksnapshot = net.minecraftforge.common.util.BlockSnapshot.getBlockSnapshot(worldIn, blockpos1);
					worldIn.setBlockState(blockpos1, TFBlocks.huge_waterlily.get().getDefaultState()); // TF - our block
					if (net.minecraftforge.event.ForgeEventFactory.onBlockPlace(playerIn, blocksnapshot, net.minecraft.util.Direction.UP)) {
						blocksnapshot.restore(true, false);
						return new ActionResult<ItemStack>(ActionResultType.FAIL, itemstack);
					}

					// TF - our block
					worldIn.setBlockState(blockpos1, TFBlocks.huge_waterlily.get().getDefaultState(), 11);

					if (playerIn instanceof ServerPlayerEntity) {
						CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) playerIn, blockpos1, itemstack);
					}

					if (!playerIn.abilities.isCreativeMode) {
						itemstack.shrink(1);
					}

					playerIn.addStat(Stats.ITEM_USED.get(this));
					worldIn.playSound(playerIn, blockpos, SoundEvents.BLOCK_LILY_PAD_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
					return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
				}
			}

			return new ActionResult<>(ActionResultType.FAIL, itemstack);
		}
	}
}
