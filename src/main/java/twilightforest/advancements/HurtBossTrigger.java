package twilightforest.advancements;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import twilightforest.TwilightForestMod;

import java.util.Optional;

public class HurtBossTrigger extends SimpleCriterionTrigger<HurtBossTrigger.Instance> {

	public static final ResourceLocation ID = TwilightForestMod.prefix("hurt_boss");

	@Override
	protected Instance createInstance(JsonObject json, Optional<ContextAwarePredicate> player, DeserializationContext ctx) {
		Optional<ContextAwarePredicate> composite = EntityPredicate.fromJson(json, "hurt_entity", ctx);
		return new Instance(player, composite);
	}

	public void trigger(ServerPlayer player, Entity hurt) {
		LootContext entity = EntityPredicate.createContext(player, hurt);
		this.trigger(player, (instance) -> instance.matches(entity));
	}

	public static class Instance extends AbstractCriterionTriggerInstance {
		private final Optional<ContextAwarePredicate> hurt;

		public Instance(Optional<ContextAwarePredicate> player, Optional<ContextAwarePredicate> hurt) {
			super(player);
			this.hurt = hurt;
		}

		public boolean matches(LootContext hurt) {
			return this.hurt.isEmpty() || this.hurt.get().matches(hurt);
		}

		public static Instance hurtBoss(EntityPredicate.Builder hurt) {
			return new Instance(Optional.empty(), Optional.of(EntityPredicate.wrap(hurt.build())));
		}

		@Override
		public JsonObject serializeToJson() {
			JsonObject json = super.serializeToJson();
			this.hurt.ifPresent(predicate -> json.add("hurt_entity", predicate.toJson()));
			return json;
		}
	}
}
