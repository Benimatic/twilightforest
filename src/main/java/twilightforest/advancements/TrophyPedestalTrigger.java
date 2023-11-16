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

public class TrophyPedestalTrigger extends SimpleCriterionTrigger<TrophyPedestalTrigger.TriggerInstance> {

    public static final ResourceLocation ID = TwilightForestMod.prefix("placed_on_trophy_pedestal");

	@Override
	public TrophyPedestalTrigger.TriggerInstance createInstance(JsonObject json, Optional<ContextAwarePredicate> player, DeserializationContext condition) {
		return new TrophyPedestalTrigger.TriggerInstance(player);
	}

	public void trigger(ServerPlayer player) {
        this.trigger(player, (instance) -> true);
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {

        public TriggerInstance(Optional<ContextAwarePredicate> player) {
            super(player);
        }

        public static Criterion<TrophyPedestalTrigger.TriggerInstance> activatePedestal() {
            return TFAdvancements.PLACED_TROPHY_ON_PEDESTAL.createCriterion(new TriggerInstance(Optional.empty()));
        }
    }
}
