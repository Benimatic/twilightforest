package twilightforest.advancements;

import com.google.gson.JsonObject;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import twilightforest.TwilightForestMod;

import java.util.Optional;

public class KillAllPhantomsTrigger extends SimpleCriterionTrigger<KillAllPhantomsTrigger.TriggerInstance> {

	public static final ResourceLocation ID = TwilightForestMod.prefix("kill_all_phantoms");

	@Override
	protected KillAllPhantomsTrigger.TriggerInstance createInstance(JsonObject json, Optional<ContextAwarePredicate> player, DeserializationContext ctx) {
		return new KillAllPhantomsTrigger.TriggerInstance(player);
	}

	public void trigger(ServerPlayer player) {
		this.trigger(player, (instance) -> true);
	}

	public static class TriggerInstance extends AbstractCriterionTriggerInstance {
		public TriggerInstance(Optional<ContextAwarePredicate> player) {
			super(player);
		}

		public static Criterion<KillAllPhantomsTrigger.TriggerInstance> killThemAll() {
			return TFAdvancements.KILL_ALL_PHANTOMS.createCriterion(new TriggerInstance(Optional.empty()));
		}
	}
}