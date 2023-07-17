package twilightforest.item.recipe;

import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import twilightforest.init.TFRecipes;
import twilightforest.init.TFItems;

public class MazeMapCloningRecipe extends CustomRecipe {

	public MazeMapCloningRecipe(ResourceLocation id, CraftingBookCategory category) {
		super(id, category);
	}

	@Override
	public boolean matches(CraftingContainer container, Level level) {
		int i = 0;
		ItemStack itemstack = ItemStack.EMPTY;

		for(int j = 0; j < container.getContainerSize(); j++) {
			ItemStack itemstack1 = container.getItem(j);
			if (!itemstack1.isEmpty()) {
				if (itemstack1.is(TFItems.FILLED_MAZE_MAP.get())) {
					if (!itemstack.isEmpty()) {
						return false;
					}

					itemstack = itemstack1;
				} else {
					if (!itemstack1.is(TFItems.MAZE_MAP.get())) {
						return false;
					}

					i++;
				}
			}
		}

		return !itemstack.isEmpty() && i > 0;
	}

	@Override
	public ItemStack assemble(CraftingContainer container, RegistryAccess access) {
		int i = 0;
		ItemStack itemstack = ItemStack.EMPTY;

		for(int j = 0; j < container.getContainerSize(); j++) {
			ItemStack itemstack1 = container.getItem(j);
			if (!itemstack1.isEmpty()) {
				if (itemstack1.is(TFItems.FILLED_MAZE_MAP.get())) {
					if (!itemstack.isEmpty()) {
						return ItemStack.EMPTY;
					}

					itemstack = itemstack1;
				} else {
					if (!itemstack1.is(TFItems.MAZE_MAP.get())) {
						return ItemStack.EMPTY;
					}

					i++;
				}
			}
		}

		if (!itemstack.isEmpty() && i >= 1) {
			ItemStack itemstack2 = itemstack.copy();
			itemstack2.setCount(i + 1);
			return itemstack2;
		} else {
			return ItemStack.EMPTY;
		}
	}

	public boolean canCraftInDimensions(int x, int y) {
		return x >= 3 && y >= 3;
	}

	public RecipeSerializer<?> getSerializer() {
		return TFRecipes.MAZE_MAP_CLONING_RECIPE.get();
	}
}
