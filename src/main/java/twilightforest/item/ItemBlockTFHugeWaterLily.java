package twilightforest.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.LilyPadItem;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemBlockTFHugeWaterLily extends LilyPadItem {

	public ItemBlockTFHugeWaterLily(Block block, Properties props) {
		super(block, props);
	}

	// [VanillaCopy] ItemLilyPad.onItemRightClick, edits noted
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
				if (!world.isBlockModifiable(player, blockpos) || !player.canPlayerEdit(blockpos.offset(direction), direction, itemstack)) {
					return ActionResult.resultFail(itemstack);
				}

				BlockPos blockpos1 = blockpos.up();
				BlockState blockstate = world.getBlockState(blockpos);
				Material material = blockstate.getMaterial();
				FluidState ifluidstate = world.getFluidState(blockpos);
				if ((ifluidstate.getFluid() == Fluids.WATER || material == Material.ICE) && world.isAirBlock(blockpos1)) {

					// special case for handling block placement with water lilies
					net.minecraftforge.common.util.BlockSnapshot blocksnapshot = net.minecraftforge.common.util.BlockSnapshot.create(world, blockpos1);
					// TF - getBlock() instead of hardcoded lilypad
					world.setBlockState(blockpos1, getBlock().getDefaultState(), 11);
					if (net.minecraftforge.event.ForgeEventFactory.onBlockPlace(player, blocksnapshot, net.minecraft.util.Direction.UP)) {
						blocksnapshot.restore(true, false);
						return ActionResult.resultFail(itemstack);
					}

					if (player instanceof ServerPlayerEntity) {
						CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity)player, blockpos1, itemstack);
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
