package twilightforest.item;

import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class ItemTFFood extends Item {
	public ItemTFFood(Food food, Properties props) {
		super(props.food(food).group(ItemGroup.FOOD));
	}
}
