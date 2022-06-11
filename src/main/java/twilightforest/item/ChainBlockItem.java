package twilightforest.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.TierSortingRegistry;
import twilightforest.init.TFEnchantments;
import twilightforest.init.TFSounds;
import twilightforest.entity.ChainBlock;
import twilightforest.init.TFEntities;
import twilightforest.util.TwilightItemTier;

import javax.annotation.Nullable;
import java.util.UUID;

public class ChainBlockItem extends DiggerItem {

	private static final String THROWN_UUID_KEY = "chainEntity";

	public ChainBlockItem(Properties props) {
		super(6, -3.0F, TwilightItemTier.KNIGHTMETAL, BlockTags.BASE_STONE_OVERWORLD, props);
	}

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity holder, int slot, boolean isSelected) {
		if (!world.isClientSide && getThrownUuid(stack) != null && getThrownEntity(world, stack) == null) {
			stack.getTag().remove(THROWN_UUID_KEY);
		}
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);

		if (getThrownUuid(stack) != null)
			return new InteractionResultHolder<>(InteractionResult.PASS, stack);

		player.playSound(TFSounds.BLOCKCHAIN_FIRED.get(), 0.5F, 1.0F / (world.random.nextFloat() * 0.4F + 1.2F));

		if (!world.isClientSide) {
			ChainBlock launchedBlock = new ChainBlock(TFEntities.CHAIN_BLOCK.get(), world, player, hand, stack);
			world.addFreshEntity(launchedBlock);
			setThrownEntity(stack, launchedBlock);

			stack.hurtAndBreak(1, player, (user) -> user.broadcastBreakEvent(hand));
		}

		player.startUsingItem(hand);
		return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
	}

	@Nullable
	public static UUID getThrownUuid(ItemStack stack) {
		if (stack.hasTag() && stack.getTag().hasUUID(THROWN_UUID_KEY)) {
			return stack.getTag().getUUID(THROWN_UUID_KEY);
		}

		return null;
	}

	@Nullable
	private static ChainBlock getThrownEntity(Level world, ItemStack stack) {
		if (world instanceof ServerLevel) {
			UUID id = getThrownUuid(stack);
			if (id != null) {
				Entity e = ((ServerLevel) world).getEntity(id);
				if (e instanceof ChainBlock) {
					return (ChainBlock) e;
				}
			}
		}

		return null;
	}

	private static void setThrownEntity(ItemStack stack, ChainBlock cube) {
		if (!stack.hasTag()) {
			stack.setTag(new CompoundTag());
		}
		stack.getTag().putUUID(THROWN_UUID_KEY, cube.getUUID());
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 72000;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.BLOCK;
	}

	@Override
	public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
		return true;
	}

	@Override
	public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
		if (state.is(BlockTags.MINEABLE_WITH_PICKAXE) || state.is(BlockTags.MINEABLE_WITH_HOE)
				|| state.is(BlockTags.MINEABLE_WITH_SHOVEL) || state.is(BlockTags.MINEABLE_WITH_AXE))
			return TierSortingRegistry.isCorrectTierForDrops(this.getHarvestLevel(stack), state);
		return super.isCorrectToolForDrops(stack, state);
	}

	public Tier getHarvestLevel(ItemStack stack) {
		int enchantLevel = EnchantmentHelper.getItemEnchantmentLevel(TFEnchantments.DESTRUCTION.get(), stack);
		if (enchantLevel == 2) {
			return Tiers.STONE;
		} else if (enchantLevel == 3) {
			return Tiers.IRON;
		} else {
			return Tiers.WOOD;
		}
	}
}
