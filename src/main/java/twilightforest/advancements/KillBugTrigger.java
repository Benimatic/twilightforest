package twilightforest.advancements;

import com.google.gson.JsonObject;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import twilightforest.TwilightForestMod;

import java.util.Optional;

public class KillBugTrigger extends SimpleCriterionTrigger<KillBugTrigger.TriggerInstance> {
	public static final ResourceLocation ID = TwilightForestMod.prefix("kill_bug");

	@Override
	protected KillBugTrigger.TriggerInstance createInstance(JsonObject json, Optional<ContextAwarePredicate> player, DeserializationContext ctx) {
		Block bug = deserializeBug(json);
		return new KillBugTrigger.TriggerInstance(player, bug);
	}

	@Nullable
	private static Block deserializeBug(JsonObject object) {
		if (object.has("bug")) {
			ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.getAsString(object, "bug"));
			return BuiltInRegistries.BLOCK.get(resourcelocation);
		} else {
			return null;
		}
	}

	public void trigger(ServerPlayer player, BlockState bug) {
		this.trigger(player, (instance) -> instance.matches(bug));
	}

	public static class TriggerInstance extends AbstractCriterionTriggerInstance {

		@Nullable
		private final Block bugType;

		public TriggerInstance(Optional<ContextAwarePredicate> player, @Nullable Block bugType) {
			super(player);
			this.bugType = bugType;
		}

		public static Criterion<TriggerInstance> killBug(Block bug) {
			return TFAdvancements.KILL_BUG.createCriterion(new TriggerInstance(Optional.empty(), bug));
		}

		public boolean matches(BlockState bug) {
			return this.bugType == null || bug.is(this.bugType);
		}

		@Override
		public JsonObject serializeToJson() {
			JsonObject object = super.serializeToJson();
			if (bugType != null) {
				object.addProperty("bug", BuiltInRegistries.BLOCK.getKey(this.bugType).toString());
			}
			return object;
		}
	}
}
