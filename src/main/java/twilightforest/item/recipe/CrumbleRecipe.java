package twilightforest.item.recipe;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import twilightforest.init.TFRecipes;

import org.jetbrains.annotations.Nullable;

public record CrumbleRecipe(ResourceLocation recipeID, BlockState input, BlockState result) implements Recipe<Container> {

	@Override
	public boolean matches(Container container, Level level) {
		return true;
	}

	@Override
	public ItemStack assemble(Container container) {
		return ItemStack.EMPTY;
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return true;
	}

	@Override
	public ItemStack getResultItem() {
		return ItemStack.EMPTY;
	}

	@Override
	public ResourceLocation getId() {
		return this.recipeID;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return TFRecipes.CRUMBLE_SERIALIZER.get();
	}

	@Override
	public RecipeType<?> getType() {
		return TFRecipes.CRUMBLE_RECIPE.get();
	}

	public static class Serializer implements RecipeSerializer<CrumbleRecipe> {

		@Override
		public CrumbleRecipe fromJson(ResourceLocation id, JsonObject object) {
			Block input = ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(GsonHelper.getAsString(object, "from")));
			Block output = ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(GsonHelper.getAsString(object, "to")));
			if (input != null && output != null) {
				return new CrumbleRecipe(id, input.defaultBlockState(), output.defaultBlockState());
			}
			return new CrumbleRecipe(id, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState());
		}

		@Nullable
		@Override
		public CrumbleRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
			Block input = buffer.readRegistryIdUnsafe(ForgeRegistries.BLOCKS);
			Block output = buffer.readRegistryIdUnsafe(ForgeRegistries.BLOCKS);
			return new CrumbleRecipe(id, input.defaultBlockState(), output.defaultBlockState());
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, CrumbleRecipe recipe) {
			buffer.writeRegistryIdUnsafe(ForgeRegistries.BLOCKS, recipe.input.getBlock());
			buffer.writeRegistryIdUnsafe(ForgeRegistries.BLOCKS, recipe.result.getBlock());
		}
	}
}
