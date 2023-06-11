package twilightforest.advancements;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import twilightforest.TwilightForestMod;

public class KillBugTrigger extends SimpleCriterionTrigger<KillBugTrigger.Instance> {
	public static final ResourceLocation ID = TwilightForestMod.prefix("kill_bug");

	@Override
	public ResourceLocation getId() {
		return ID;
	}

	@Override
	protected Instance createInstance(JsonObject json, ContextAwarePredicate player, DeserializationContext ctx) {
		Block bug = deserializeBug(json);
		return new Instance(player, bug);
	}

	@Nullable
	private static Block deserializeBug(JsonObject object) {
		if (object.has("bug")) {
			ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.getAsString(object, "bug"));
			return ForgeRegistries.BLOCKS.getValue(resourcelocation);
		} else {
			return null;
		}
	}

	public void trigger(ServerPlayer player, BlockState bug) {
		this.trigger(player, (instance) -> instance.matches(bug));
	}

	public static class Instance extends AbstractCriterionTriggerInstance {

		@Nullable
		private final Block bugType;

		public Instance(ContextAwarePredicate player, @Nullable Block bugType) {
			super(ID, player);
			this.bugType = bugType;
		}

		public static Instance killBug(Block bug) {
			return new Instance(ContextAwarePredicate.ANY, bug);
		}

		public boolean matches(BlockState bug) {
			return this.bugType == null || bug.is(this.bugType);
		}

		@Override
		@SuppressWarnings("deprecation")
		public JsonObject serializeToJson(SerializationContext ctx) {
			JsonObject object = super.serializeToJson(ctx);
			if (bugType != null) {
				object.addProperty("bug", ForgeRegistries.BLOCKS.getKey(this.bugType).toString());
			}
			return object;
		}
	}
}
