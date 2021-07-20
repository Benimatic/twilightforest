package twilightforest.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.BowItem;
import twilightforest.entity.TFEntities;
import twilightforest.entity.projectile.SeekerArrowEntity;

public class SeekerBowItem extends BowItem {

	public SeekerBowItem(Properties props) {
		super(props);
	}

	@Override
	public AbstractArrowEntity customArrow(AbstractArrowEntity arrow) {
		if (arrow.getShooter() instanceof LivingEntity) {
			return new SeekerArrowEntity(TFEntities.seeker_arrow, arrow.world, (LivingEntity) arrow.getShooter());
		}
		return arrow;
	}
}
