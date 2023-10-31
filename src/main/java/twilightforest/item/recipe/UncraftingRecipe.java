package twilightforest.item.recipe;

import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.crafting.IShapedRecipe;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.Nullable;
import twilightforest.init.TFRecipes;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public record UncraftingRecipe(int cost, int width, int height, Ingredient input, int count,
							   NonNullList<Ingredient> resultItems) implements CraftingRecipe, IShapedRecipe<CraftingContainer> {

	@Override //This method is never used, but it has to be implemented
	public boolean matches(CraftingContainer container, Level level) {
		return false;
	}

	@Override //We have to implement this method, can't really be used since we have multiple outputs
	public ItemStack assemble(CraftingContainer container, RegistryAccess access) {
		return ItemStack.EMPTY;
	}

	@Override //We have to implement this method, returns the count just in case
	public ItemStack getResultItem(RegistryAccess access) {
		return new ItemStack(Items.AIR, this.count);
	}

	@Override //Could probably be set to return true, since the recipe serializer doesn't let a bigger number through.
	public boolean canCraftInDimensions(int width, int height) {
		return (width >= this.width && height >= this.height);
	}

	//Checks if the itemStack is a part of the ingredient when UncraftingMenu's getRecipesFor() method iterates through all recipes.
	public boolean isItemStackAnIngredient(ItemStack stack) {
		return Arrays.stream(this.input().getItems()).anyMatch(i -> (stack.getItem() == i.getItem() && stack.getCount() >= this.count()));
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return TFRecipes.UNCRAFTING_SERIALIZER.get();
	}

	@Override
	public RecipeType<?> getType() {
		return TFRecipes.UNCRAFTING_RECIPE.get();
	}

	@Override
	public CraftingBookCategory category() {
		return CraftingBookCategory.MISC;
	}

	@Override
	public int getRecipeWidth() {
		return this.width();
	}

	@Override
	public int getRecipeHeight() {
		return this.height();
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		return this.resultItems();
	}

	public static class Serializer implements RecipeSerializer<UncraftingRecipe> {

		private static final Codec<UncraftingRecipe> CODEC = RawRecipe.CODEC.flatXmap(recipe -> {
			String[] astring = shrink(recipe.pattern);
			int i = astring[0].length();
			int j = astring.length;
			NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i * j, Ingredient.EMPTY);
			Set<String> set = Sets.newHashSet(recipe.key.keySet());

			for (int k = 0; k < astring.length; ++k) {
				String s = astring[k];

				for (int l = 0; l < s.length(); ++l) {
					String s1 = s.substring(l, l + 1);
					Ingredient ingredient = s1.equals(" ") ? Ingredient.EMPTY : recipe.key.get(s1);
					if (ingredient == null) {
						return DataResult.error(() -> "Pattern references symbol '" + s1 + "' but it's not defined in the key");
					}

					set.remove(s1);
					nonnulllist.set(l + i * k, ingredient);
				}
			}

			if (!set.isEmpty()) {
				return DataResult.error(() -> "Key defines symbols that aren't used in pattern: " + set);
			} else {
				UncraftingRecipe result = new UncraftingRecipe(recipe.cost, i, j, recipe.input, recipe.count, nonnulllist);
				return DataResult.success(result);
			}
		}, recipe -> {
			//FIXME keep an eye on ShapedRecipe.Serializer.CODEC. Once mojang adds serialization for ShapedRecipes add it here
			throw new NotImplementedException("Serializing UncraftingRecipe is not implemented yet.");
		});

		private static String[] shrink(List<String> strings) {
			int i = Integer.MAX_VALUE;
			int j = 0;
			int k = 0;
			int l = 0;

			for (int i1 = 0; i1 < strings.size(); ++i1) {
				String s = strings.get(i1);
				i = Math.min(i, firstNonSpace(s));
				int j1 = lastNonSpace(s);
				j = Math.max(j, j1);
				if (j1 < 0) {
					if (k == i1) {
						++k;
					}

					++l;
				} else {
					l = 0;
				}
			}

			if (strings.size() == l) {
				return new String[0];
			} else {
				String[] astring = new String[strings.size() - l - k];

				for (int k1 = 0; k1 < astring.length; ++k1) {
					astring[k1] = strings.get(k1 + k).substring(i, j + 1);
				}

				return astring;
			}
		}

		private static int firstNonSpace(String first) {
			int i;
			i = 0;
			while (i < first.length() && first.charAt(i) == ' ') ++i;
			return i;
		}

		private static int lastNonSpace(String last) {
			int i;
			i = last.length() - 1;
			while (i >= 0 && last.charAt(i) == ' ') --i;
			return i;
		}

		@Override
		public Codec<UncraftingRecipe> codec() {
			return CODEC;
		}

		@Nullable
		@Override
		public UncraftingRecipe fromNetwork(FriendlyByteBuf buffer) {
			int cost = buffer.readVarInt();
			int width = buffer.readVarInt();
			int height = buffer.readVarInt();
			Ingredient result = Ingredient.fromNetwork(buffer);
			int count = buffer.readVarInt();
			NonNullList<Ingredient> ingredients = NonNullList.withSize(width * height, Ingredient.EMPTY);
			ingredients.replaceAll(ignored -> Ingredient.fromNetwork(buffer));
			return new UncraftingRecipe(cost, width, height, result, count, ingredients);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, UncraftingRecipe recipe) {
			buffer.writeVarInt(recipe.cost());
			buffer.writeVarInt(recipe.width());
			buffer.writeVarInt(recipe.height());
			recipe.input().toNetwork(buffer);
			buffer.writeVarInt(recipe.count());
			for (Ingredient i : recipe.resultItems()) i.toNetwork(buffer);
		}

		private record RawRecipe(int cost, Ingredient input, int count, Map<String, Ingredient> key,
								 List<String> pattern) {
			public static final Codec<RawRecipe> CODEC = RecordCodecBuilder.create(instance ->
					instance.group(
									Codec.INT.fieldOf("cost").forGetter(o -> o.cost),
									Ingredient.CODEC_NONEMPTY.fieldOf("input").forGetter(o -> o.input),
									Codec.INT.optionalFieldOf("count", 1).forGetter(o -> o.count),
									ExtraCodecs.strictUnboundedMap(ShapedRecipe.Serializer.SINGLE_CHARACTER_STRING_CODEC, Ingredient.CODEC_NONEMPTY).fieldOf("key").forGetter(o -> o.key),
									ShapedRecipe.Serializer.PATTERN_CODEC.fieldOf("pattern").forGetter(o -> o.pattern))
							.apply(instance, RawRecipe::new)
			);
		}
	}
}