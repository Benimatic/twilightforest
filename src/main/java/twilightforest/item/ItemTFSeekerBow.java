package twilightforest.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.BowItem;
import twilightforest.entity.TFEntities;
import twilightforest.entity.projectile.EntitySeekerArrow;

public class ItemTFSeekerBow extends BowItem {

	public ItemTFSeekerBow(Properties props) {
		super(props);
	}

	@Override
	public AbstractArrowEntity customeArrow(AbstractArrowEntity arrow) {
		if (arrow.getShooter() instanceof LivingEntity) {
			return new EntitySeekerArrow(TFEntities.seeker_arrow.get(), arrow.world, (LivingEntity) arrow.getShooter());
		}
		return arrow;
	}
}
