package twilightforest.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import twilightforest.block.TFBlocks;

public class ItemTFExperiment115 extends ItemTFFood {

	public ItemTFExperiment115(Food food, Properties props) {
		super(food, props);
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		World world = context.getWorld();
		BlockPos pos = context.getPos();
		BlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		PlayerEntity player = context.getPlayer();
		Direction facing = context.getFace();

		// Let eating take priority over block placement
		if (player.canEat(false))
			return ActionResultType.PASS;

		BlockState e115 = TFBlocks.experiment_115.getDefaultState();

		if (!block.isReplaceable(world, pos)) pos = pos.offset(facing);
		ItemStack itemstack = player.getHeldItem(context.getHand());

		if (!itemstack.isEmpty() && player.canPlayerEdit(pos, facing, itemstack) && world.mayPlace(e115.getBlock(), pos, false, facing, null)) {
			if (world.setBlockState(pos, e115, 11)) {
				TFBlocks.experiment_115.onBlockPlacedBy(world, pos, e115, player, itemstack);
				SoundType soundtype = world.getBlockState(pos).getBlock().getSoundType(world.getBlockState(pos), world, pos, player);
				world.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
				itemstack.shrink(1);
			}

			if (player instanceof ServerPlayerEntity) CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) player, pos, itemstack);

			return ActionResultType.SUCCESS;
		} else return super.onItemUse(context);
	}
}