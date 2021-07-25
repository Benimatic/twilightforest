package twilightforest.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import twilightforest.entity.projectile.IceArrowEntity;

import net.minecraft.world.item.Item.Properties;

public class IceBowItem extends BowItem {

	public IceBowItem(Properties props) {
		super(props);
	}

	@Override
	public AbstractArrow customArrow(AbstractArrow arrow) {
		if (arrow.getOwner() instanceof LivingEntity) {
			return new IceArrowEntity(arrow.level, (LivingEntity) arrow.getOwner());
		}
		return arrow;
	}

	@Override
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repairWith) {
		return repairWith.getItem() == Blocks.ICE.asItem() || super.isValidRepairItem(toRepair, repairWith);
	}
}
