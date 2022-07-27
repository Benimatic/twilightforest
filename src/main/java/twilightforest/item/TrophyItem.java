package twilightforest.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.Nullable;
import twilightforest.compat.curios.CuriosCompat;

public class TrophyItem extends StandingAndWallBlockItem {

	public TrophyItem(Block floorBlock, Block wallBlock, Properties properties) {
		super(floorBlock, wallBlock, properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		EquipmentSlot equipmentslot = Mob.getEquipmentSlotForItem(itemstack);
		ItemStack itemstack1 = player.getItemBySlot(equipmentslot);
		if (itemstack1.isEmpty()) {
			player.setItemSlot(equipmentslot, itemstack.split(1));
			if (!level.isClientSide()) {
				player.awardStat(Stats.ITEM_USED.get(this));
			}

			return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
		} else {
			return InteractionResultHolder.fail(itemstack);
		}
	}

	@Override
	public boolean canEquip(ItemStack stack, EquipmentSlot slot, Entity entity) {
		return slot == EquipmentSlot.HEAD;
	}

	@Override
	@Nullable
	public EquipmentSlot getEquipmentSlot(ItemStack stack) {
		return EquipmentSlot.HEAD;
	}

	@Nullable
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag tag) {
		if (ModList.get().isLoaded("curios")) {
			return CuriosCompat.setupCuriosCapability(stack);
		}
		return super.initCapabilities(stack, tag);
	}
}