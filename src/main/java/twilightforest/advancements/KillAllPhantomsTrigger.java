package twilightforest.advancements;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import twilightforest.TwilightForestMod;

import java.util.Optional;

public class KillAllPhantomsTrigger extends SimpleCriterionTrigger<KillAllPhantomsTrigger.Instance> {

	public static final ResourceLocation ID = TwilightForestMod.prefix("kill_all_phantoms");

	@Override
	protected Instance createInstance(JsonObject json, Optional<ContextAwarePredicate> player, DeserializationContext ctx) {
		return new Instance(player);
	}

	public void trigger(ServerPlayer player) {
		this.trigger(player, (instance) -> true);
	}

	public static class Instance extends AbstractCriterionTriggerInstance {
		public Instance(Optional<ContextAwarePredicate> player) {
			super(player);
		}

		public static Instance killThemAll() {
			return new Instance(Optional.empty());
		}
	}
}