package twilightforest.data.custom;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import oshi.util.tuples.Pair;
import twilightforest.TwilightForestMod;
import twilightforest.init.TFRecipes;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public abstract class CrumbleHornProvider implements DataProvider {

	private final PackOutput output;
	private final String modId;
	private final ExistingFileHelper helper;
	protected final Map<String, Pair<BlockState, BlockState>> builders = Maps.newLinkedHashMap();

	public CrumbleHornProvider(PackOutput output, final String modId, final ExistingFileHelper helper) {
		this.output = output;
		this.modId = modId;
		this.helper = helper;
	}

	public abstract void registerTransforms();

	@Override
	public CompletableFuture<?> run(CachedOutput cache) {
		this.builders.clear();
		this.registerTransforms();

		ImmutableList.Builder<CompletableFuture<?>> futuresBuilder = new ImmutableList.Builder<>();

		this.builders.forEach((name, transform) -> {
			List<String> list = builders.keySet().stream()
					.filter(s -> ForgeRegistries.BLOCKS.containsValue(transform.getA().getBlock()))
					.filter(s -> ForgeRegistries.BLOCKS.containsValue(transform.getB().getBlock()))
					.filter(s -> !this.builders.containsKey(s))
					.filter(this::missing)
					.collect(Collectors.toList());

			if (!list.isEmpty()) {
				throw new IllegalArgumentException(String.format("Duplicate Crumble Horn Transformations: %s", list.stream().map(Objects::toString).collect(Collectors.joining(", "))));
			} else {
				JsonObject obj = serializeToJson(transform.getA(), transform.getB());
				Path path = createPath(new ResourceLocation(modId, name));
				futuresBuilder.add(DataProvider.saveStable(cache, obj, path));
			}
		});
		return CompletableFuture.allOf(futuresBuilder.build().toArray(CompletableFuture[]::new));
	}

	private boolean missing(String name) {
		return !this.helper.exists(new ResourceLocation(modId, name), new ExistingFileHelper.ResourceType(PackType.SERVER_DATA, ".json", "crumble_horn"));
	}

	private Path createPath(ResourceLocation name) {
		return this.output.getOutputFolder().resolve("data/" + name.getNamespace() + "/recipes/crumble_horn/" + name.getPath() + ".json");
	}

	private JsonObject serializeToJson(BlockState transformFrom, BlockState transformTo) {
		JsonObject jsonobject = new JsonObject();

		jsonobject.addProperty("type", ForgeRegistries.RECIPE_SERIALIZERS.getKey(TFRecipes.CRUMBLE_SERIALIZER.get()).toString());
		jsonobject.addProperty("from", ForgeRegistries.BLOCKS.getKey(transformFrom.getBlock()).toString());
		jsonobject.addProperty("to", ForgeRegistries.BLOCKS.getKey(transformTo.getBlock()).toString());
		return jsonobject;
	}

	@Override
	public String getName() {
		return this.modId + " Crumble Horn Transformations";
	}

	//helper methods
	public void addTransform(Block from, Block to) {
		this.builders.put(ForgeRegistries.BLOCKS.getKey(from).getPath() + "_to_" + ForgeRegistries.BLOCKS.getKey(to).getPath(), new Pair<>(from.defaultBlockState(), to.defaultBlockState()));
	}

	public void addDissolve(Block dissolveBlock) {
		this.builders.put("dissolve_" + ForgeRegistries.BLOCKS.getKey(dissolveBlock).getPath(), new Pair<>(dissolveBlock.defaultBlockState(), Blocks.AIR.defaultBlockState()));
	}
}
