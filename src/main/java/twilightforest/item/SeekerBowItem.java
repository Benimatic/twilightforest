package twilightforest.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import twilightforest.entity.TFEntities;
import twilightforest.entity.projectile.SeekerArrowEntity;

import net.minecraft.world.item.Item.Properties;

public class SeekerBowItem extends BowItem {

	public SeekerBowItem(Properties props) {
		super(props);
	}

	@Override
	public AbstractArrow customArrow(AbstractArrow arrow) {
		if (arrow.getOwner() instanceof LivingEntity) {
			return new SeekerArrowEntity(TFEntities.seeker_arrow, arrow.level, (LivingEntity) arrow.getOwner());
		}
		return arrow;
	}
}
