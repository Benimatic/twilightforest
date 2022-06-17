package twilightforest.item;

import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import twilightforest.entity.projectile.SeekerArrow;

public class SeekerBowItem extends BowItem {

	public SeekerBowItem(Properties properties) {
		super(properties);
	}

	@Override
	public AbstractArrow customArrow(AbstractArrow arrow) {
		return new SeekerArrow(arrow.getLevel(), arrow.getOwner());
	}
}