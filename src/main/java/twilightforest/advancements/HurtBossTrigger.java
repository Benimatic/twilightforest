package twilightforest.advancements;

import com.google.gson.JsonObject;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import twilightforest.TwilightForestMod;

import java.util.Optional;

public class HurtBossTrigger extends SimpleCriterionTrigger<HurtBossTrigger.TriggerInstance> {

	public static final ResourceLocation ID = TwilightForestMod.prefix("hurt_boss");

	@Override
	protected HurtBossTrigger.TriggerInstance createInstance(JsonObject json, Optional<ContextAwarePredicate> player, DeserializationContext ctx) {
		Optional<ContextAwarePredicate> composite = EntityPredicate.fromJson(json, "hurt_entity", ctx);
		return new HurtBossTrigger.TriggerInstance(player, composite);
	}

	public void trigger(ServerPlayer player, Entity hurt) {
		LootContext entity = EntityPredicate.createContext(player, hurt);
		this.trigger(player, (instance) -> instance.matches(entity));
	}

	public static class TriggerInstance extends AbstractCriterionTriggerInstance {
		private final Optional<ContextAwarePredicate> hurt;

		public TriggerInstance(Optional<ContextAwarePredicate> player, Optional<ContextAwarePredicate> hurt) {
			super(player);
			this.hurt = hurt;
		}

		public boolean matches(LootContext hurt) {
			return this.hurt.isEmpty() || this.hurt.get().matches(hurt);
		}

		public static Criterion<HurtBossTrigger.TriggerInstance> hurtBoss(EntityPredicate.Builder hurt) {
			return TFAdvancements.HURT_BOSS.createCriterion(new TriggerInstance(Optional.empty(), Optional.of(EntityPredicate.wrap(hurt.build()))));
		}

		@Override
		public JsonObject serializeToJson() {
			JsonObject json = super.serializeToJson();
			this.hurt.ifPresent(predicate -> json.add("hurt_entity", predicate.toJson()));
			return json;
		}
	}
}
