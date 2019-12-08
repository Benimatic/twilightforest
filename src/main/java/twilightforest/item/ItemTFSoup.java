package twilightforest.item;

import net.minecraft.item.Food;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SoupItem;

public class ItemTFSoup extends SoupItem {
	public ItemTFSoup(Food food, Properties props) {
		super(props.food(food).group(ItemGroup.FOOD));
	}
}
