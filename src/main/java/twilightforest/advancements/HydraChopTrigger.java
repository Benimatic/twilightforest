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

public class HydraChopTrigger extends SimpleCriterionTrigger<HydraChopTrigger.TriggerInstance> {

    public static final ResourceLocation ID = TwilightForestMod.prefix("consume_hydra_chop_on_low_hunger");

    @Override
    protected HydraChopTrigger.TriggerInstance createInstance(JsonObject json, Optional<ContextAwarePredicate> player, DeserializationContext ctx) {
        return new HydraChopTrigger.TriggerInstance(player);
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, (instance) -> true);
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        public TriggerInstance(Optional<ContextAwarePredicate> player) {
            super(player);
        }

        public static Criterion<HydraChopTrigger.TriggerInstance> eatChop() {
            return TFAdvancements.CONSUME_HYDRA_CHOP.createCriterion(new TriggerInstance(Optional.empty()));
        }
    }
}
