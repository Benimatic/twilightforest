package twilightforest.data.custom;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Triple;
import twilightforest.init.TFRecipes;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public abstract class TransformationPowderProvider implements DataProvider {
	private final PackOutput output;
	private final String modId;
	private final ExistingFileHelper helper;
	protected final Map<String, Triple<EntityType<?>, EntityType<?>, Boolean>> builders = Maps.newLinkedHashMap();

	public TransformationPowderProvider(PackOutput output, final String modId, final ExistingFileHelper helper) {
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
					.filter(s -> ForgeRegistries.ENTITY_TYPES.containsValue(transform.getLeft()))
					.filter(s -> ForgeRegistries.ENTITY_TYPES.containsValue(transform.getMiddle()))
					.filter(s -> !this.builders.containsKey(s))
					.filter(this::missing)
					.toList();

			if (!list.isEmpty()) {
				throw new IllegalArgumentException(String.format("Duplicate Transformation Powder Transformations: %s", list.stream().map(Objects::toString).collect(Collectors.joining(", "))));
			} else {
				JsonObject obj = serializeToJson(transform.getLeft(), transform.getMiddle(), transform.getRight());
				Path path = createPath(new ResourceLocation(this.modId, name));
				futuresBuilder.add(DataProvider.saveStable(cache, obj, path));
			}
		});
		return CompletableFuture.allOf(futuresBuilder.build().toArray(CompletableFuture[]::new));
	}

	private boolean missing(String name) {
		return this.helper == null || !this.helper.exists(new ResourceLocation(this.modId, name), new ExistingFileHelper.ResourceType(net.minecraft.server.packs.PackType.SERVER_DATA, ".json", "transformation_powder"));
	}

	private Path createPath(ResourceLocation name) {
		return this.output.getOutputFolder().resolve("data/" + name.getNamespace() + "/recipes/transformation_powder/" + name.getPath() + ".json");
	}

	private JsonObject serializeToJson(EntityType<?> transformFrom, EntityType<?> transformTo, boolean reversible) {
		JsonObject jsonobject = new JsonObject();

		jsonobject.addProperty("type", ForgeRegistries.RECIPE_SERIALIZERS.getKey(TFRecipes.TRANSFORMATION_SERIALIZER.get()).toString());
		jsonobject.addProperty("from", ForgeRegistries.ENTITY_TYPES.getKey(transformFrom).toString());
		jsonobject.addProperty("to", ForgeRegistries.ENTITY_TYPES.getKey(transformTo).toString());
		jsonobject.addProperty("reversible", reversible);
		return jsonobject;
	}

	@Override
	public String getName() {
		return this.modId + " Transformation Powder Transformations";
	}

	//helper methods
	public void addOneWayTransform(EntityType<?> from, EntityType<?> to) {
		this.builders.put(ForgeRegistries.ENTITY_TYPES.getKey(from).getPath() + "_to_" + ForgeRegistries.ENTITY_TYPES.getKey(to).getPath(), Triple.of(from, to, false));
	}

	public void addTwoWayTransform(EntityType<?> from, EntityType<?> to) {
		this.builders.put(ForgeRegistries.ENTITY_TYPES.getKey(from).getPath() + "_to_" + ForgeRegistries.ENTITY_TYPES.getKey(to).getPath(), Triple.of(from, to, true));
	}
}
