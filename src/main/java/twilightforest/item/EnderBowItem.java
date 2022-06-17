package twilightforest.item;

import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;

public class EnderBowItem extends BowItem {
	public static final String KEY = "twilightforest:ender";

	public EnderBowItem(Properties properties) {
		super(properties);
	}

	@Override
	public AbstractArrow customArrow(AbstractArrow arrow) {
		arrow.getPersistentData().putBoolean(KEY, true);
		return arrow;
	}
}