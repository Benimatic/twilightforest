package twilightforest.advancements;

import com.google.gson.JsonObject;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import twilightforest.TwilightForestMod;

import java.util.Optional;

public class StructureClearedTrigger extends SimpleCriterionTrigger<StructureClearedTrigger.TriggerInstance> {

	public static final ResourceLocation ID = TwilightForestMod.prefix("structure_cleared");

	@Override
	public StructureClearedTrigger.TriggerInstance createInstance(JsonObject json, Optional<ContextAwarePredicate> player, DeserializationContext condition) {
		String structureName = GsonHelper.getAsString(json, "structure");
		return new StructureClearedTrigger.TriggerInstance(player, structureName);
	}

	public void trigger(ServerPlayer player, String structureName) {
		this.trigger(player, (instance) -> instance.test(structureName));
	}

	public static class TriggerInstance extends AbstractCriterionTriggerInstance {

		private final String structureName;

		public TriggerInstance(Optional<ContextAwarePredicate> player, String structureName) {
			super(player);
			this.structureName = structureName;
		}

		public static Criterion<StructureClearedTrigger.TriggerInstance> clearedStructure(String name) {
			return TFAdvancements.STRUCTURE_CLEARED.createCriterion(new StructureClearedTrigger.TriggerInstance(Optional.empty(), name));
		}

		boolean test(String structureName) {
			return this.structureName.equals(structureName);
		}
	}
}
