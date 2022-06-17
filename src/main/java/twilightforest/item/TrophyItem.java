package twilightforest.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.Nullable;
import twilightforest.compat.CuriosCompat;
import twilightforest.compat.TFCompat;

public class TrophyItem extends StandingAndWallBlockItem {

	public TrophyItem(Block floorBlock, Block wallBlock, Properties properties) {
		super(floorBlock, wallBlock, properties);
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
		if (ModList.get().isLoaded(TFCompat.CURIOS_ID)) {
			return CuriosCompat.setupCuriosCapability(stack);
		}
		return super.initCapabilities(stack, tag);
	}
}