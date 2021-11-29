package twilightforest.item.recipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import twilightforest.TwilightForestMod;

public class TFRecipes {
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, TwilightForestMod.ID);

	public static final RegistryObject<SimpleRecipeSerializer<?>> MOONWORM_QUEEN_REPAIR_RECIPE = RECIPE_SERIALIZERS.register("moonworm_queen_repair_recipe", () -> new SimpleRecipeSerializer<>(MoonwormQueenRepairRecipe::new));
}
