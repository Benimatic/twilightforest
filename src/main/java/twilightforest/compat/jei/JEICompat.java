package twilightforest.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import twilightforest.TFConfig;
import twilightforest.TwilightForestMod;
import twilightforest.client.UncraftingScreen;
import twilightforest.data.tags.ItemTagGenerator;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFMenuTypes;
import twilightforest.init.TFRecipes;
import twilightforest.inventory.UncraftingMenu;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEICompat implements IModPlugin {
	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(new ItemStack(TFBlocks.UNCRAFTING_TABLE.get()), RecipeTypes.CRAFTING);
		registration.addRecipeCatalyst(new ItemStack(TFBlocks.UNCRAFTING_TABLE.get()), JEIUncraftingCategory.UNCRAFTING);
	}

	@Override
	public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
		registration.addRecipeTransferHandler(UncraftingMenu.class, TFMenuTypes.UNCRAFTING.get(), RecipeTypes.CRAFTING, 11, 9, 20, 36);
	}

	@Override
	public ResourceLocation getPluginUid() {
		return TwilightForestMod.prefix("jei_plugin");
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		registration.addRecipeCategories(new JEIUncraftingCategory(registration.getJeiHelpers().getGuiHelper()));
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		RecipeManager manager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
		List<CraftingRecipe> recipes = manager.getAllRecipesFor(RecipeType.CRAFTING);
		if (recipes != null) {
			recipes.removeIf(recipe -> recipe.getResultItem().isEmpty() ||
					recipe.getResultItem().is(ItemTagGenerator.BANNED_UNCRAFTABLES) ||
					TFConfig.COMMON_CONFIG.UNCRAFTING_STUFFS.disableUncraftingRecipes.get().contains(recipe.getId().toString()) ||
					TFConfig.COMMON_CONFIG.UNCRAFTING_STUFFS.flipUncraftingModIdList.get() != TFConfig.COMMON_CONFIG.UNCRAFTING_STUFFS.blacklistedUncraftingModIds.get().contains(recipe.getId().getNamespace()));//Prevents things that are tagged as banned from showing up
			recipes.addAll(manager.getAllRecipesFor(TFRecipes.UNCRAFTING_RECIPE.get()));
		}
		registration.addRecipes(JEIUncraftingCategory.UNCRAFTING, recipes);
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
		registration.addRecipeClickArea(UncraftingScreen.class, 34, 33, 27, 20, JEIUncraftingCategory.UNCRAFTING);
		registration.addRecipeClickArea(UncraftingScreen.class, 115, 33, 27, 20, RecipeTypes.CRAFTING);
	}
}
