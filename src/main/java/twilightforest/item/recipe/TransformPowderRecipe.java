package twilightforest.item.recipe;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.ForgeRegistries;
import twilightforest.init.TFRecipes;

import org.jetbrains.annotations.Nullable;

public record TransformPowderRecipe(EntityType<?> input, EntityType<?> result, boolean isReversible) implements Recipe<Container> {

	@Override
	public boolean matches(Container container, Level level) {
		return true;
	}

	@Override
	public ItemStack assemble(Container container, RegistryAccess access) {
		return ItemStack.EMPTY;
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return true;
	}

	@Override
	public ItemStack getResultItem(RegistryAccess access) {
		return ItemStack.EMPTY;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return TFRecipes.TRANSFORMATION_SERIALIZER.get();
	}

	@Override
	public RecipeType<?> getType() {
		return TFRecipes.TRANSFORM_POWDER_RECIPE.get();
	}

	public static class Serializer implements RecipeSerializer<TransformPowderRecipe> {

		private static final Codec<TransformPowderRecipe> CODEC = RecordCodecBuilder.create(
				instance -> instance.group(
						ForgeRegistries.ENTITY_TYPES.getCodec().fieldOf("from").forGetter(o -> o.input),
						ForgeRegistries.ENTITY_TYPES.getCodec().fieldOf("to").forGetter(o -> o.result),
						Codec.BOOL.fieldOf("reversible").forGetter(o -> o.isReversible)
				).apply(instance, TransformPowderRecipe::new));

		@Override
		public Codec<TransformPowderRecipe> codec() {
			return CODEC;
		}

		@Nullable
		@Override
		public TransformPowderRecipe fromNetwork(FriendlyByteBuf buffer) {
			EntityType<?> input = buffer.readRegistryIdUnsafe(ForgeRegistries.ENTITY_TYPES);
			EntityType<?> output = buffer.readRegistryIdUnsafe(ForgeRegistries.ENTITY_TYPES);
			boolean reversible = buffer.readBoolean();
			return new TransformPowderRecipe(input, output, reversible);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, TransformPowderRecipe recipe) {
			buffer.writeRegistryIdUnsafe(ForgeRegistries.ENTITY_TYPES, recipe.input());
			buffer.writeRegistryIdUnsafe(ForgeRegistries.ENTITY_TYPES, recipe.result());
			buffer.writeBoolean(recipe.isReversible());
		}
	}
}
