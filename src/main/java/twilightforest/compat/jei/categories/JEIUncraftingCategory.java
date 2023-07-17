package twilightforest.compat.jei.categories;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import twilightforest.TwilightForestMod;
import twilightforest.data.tags.ItemTagGenerator;
import twilightforest.init.TFBlocks;
import twilightforest.item.recipe.UncraftingRecipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JEIUncraftingCategory implements IRecipeCategory<CraftingRecipe> {
	public static final RecipeType<CraftingRecipe> UNCRAFTING = RecipeType.create(TwilightForestMod.ID, "uncrafting", CraftingRecipe.class);
	public static final int width = 116;
	public static final int height = 54;
	private final IDrawable background;
	private final IDrawable icon;
	private final Component localizedName;

	public JEIUncraftingCategory(IGuiHelper guiHelper) {
		ResourceLocation location = TwilightForestMod.getGuiTexture("uncrafting_jei.png");
		this.background = guiHelper.createDrawable(location, 0, 0, width, height);
		this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(TFBlocks.UNCRAFTING_TABLE.get()));
		this.localizedName = Component.translatable("gui.twilightforest.uncrafting_jei");
	}

	@Override
	public RecipeType<CraftingRecipe> getRecipeType() {
		return UNCRAFTING;
	}

	@Override
	public Component getTitle() {
		return this.localizedName;
	}

	@Override
	public IDrawable getBackground() {
		return this.background;
	}

	@Override
	public IDrawable getIcon() {
		return this.icon;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, CraftingRecipe recipe, IFocusGroup focuses) {
		List<Ingredient> outputs = new ArrayList<>(recipe.getIngredients()); //Collect each ingredient
		for (int i = 0; i < outputs.size(); i++) {
			outputs.set(i, Ingredient.of(Arrays.stream(outputs.get(i).getItems())
					.filter(o -> !(o.is(ItemTagGenerator.BANNED_UNCRAFTING_INGREDIENTS)))
					.filter(o -> !(o.getItem().hasCraftingRemainingItem(o)))));//Remove any banned items
		}

		for (int j = 0, k = 0; j - k < outputs.size() && j < 9; j++) {
			int x = j % 3, y = j / 3;
			if ((recipe.canCraftInDimensions(x, 3) | recipe.canCraftInDimensions(3, y)) && !(recipe instanceof ShapelessRecipe)) {
				k++;
				continue;
			} //Skips empty spaces in shaped recipes
			builder.addSlot(RecipeIngredientRole.OUTPUT, x * 18 + 63, y * 18 + 1).addIngredients(outputs.get(j - k)); //Set input as output and place in the grid
		}

		if (recipe instanceof UncraftingRecipe uncraftingRecipe) {
			ItemStack[] stacks = uncraftingRecipe.getIngredient().getItems();
			ItemStack[] stackedStacks = new ItemStack[stacks.length];
			for (int i = 0; i < stacks.length; i++) stackedStacks[i] = new ItemStack(stacks[0].getItem(), uncraftingRecipe.count());
			builder.addSlot(RecipeIngredientRole.INPUT, 5, 19).addIngredients(Ingredient.of(stackedStacks));//If the recipe is an uncrafting recipe, we need to get the ingredient instead of an itemStack
		} else {
			builder.addSlot(RecipeIngredientRole.INPUT, 5, 19).addItemStack(recipe.getResultItem(Minecraft.getInstance().level.registryAccess()));//Set the outputs as inputs and draw the item you're uncrafting in the right spot as well
		}
	}
}