package twilightforest.item.recipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import twilightforest.TwilightForestMod;

public class TFRecipes {
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, TwilightForestMod.ID);

	public static final RegistryObject<SimpleRecipeSerializer<?>> MOONWORM_QUEEN_REPAIR_RECIPE = RECIPE_SERIALIZERS.register("moonworm_queen_repair_recipe", () -> new SimpleRecipeSerializer<>(MoonwormQueenRepairRecipe::new));
	public static final RegistryObject<RecipeSerializer<UncraftingRecipe>> UNCRAFTING_SERIALIZER = RECIPE_SERIALIZERS.register("uncrafting", UncraftingRecipe.Serializer::new);
	public static final RegistryObject<RecipeSerializer<CrumbleRecipe>> CRUMBLE_SERIALIZER = RECIPE_SERIALIZERS.register("crumble_horn", CrumbleRecipe.Serializer::new);
	public static final RegistryObject<RecipeSerializer<TransformPowderRecipe>> TRANSFORMATION_SERIALIZER = RECIPE_SERIALIZERS.register("transformation_powder", TransformPowderRecipe.Serializer::new);

	public static final RecipeType<UncraftingRecipe> UNCRAFTING_RECIPE = RecipeType.register("twilightforest:unique_uncrafting");
	public static final RecipeType<CrumbleRecipe> CRUMBLE_RECIPE = RecipeType.register("twilightforest:crumble_horn");
	public static final RecipeType<TransformPowderRecipe> TRANSFORM_POWDER_RECIPE = RecipeType.register("twilightforest:transformation_powder");
}
