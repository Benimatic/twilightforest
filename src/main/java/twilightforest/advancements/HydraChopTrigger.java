package twilightforest.advancements;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import twilightforest.TwilightForestMod;

import java.util.Optional;

public class HydraChopTrigger extends SimpleCriterionTrigger<HydraChopTrigger.Instance> {

    public static final ResourceLocation ID = TwilightForestMod.prefix("consume_hydra_chop_on_low_hunger");

    @Override
    protected Instance createInstance(JsonObject json, Optional<ContextAwarePredicate> player, DeserializationContext ctx) {
        return new HydraChopTrigger.Instance(player);
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, (instance) -> true);
    }

    public static class Instance extends AbstractCriterionTriggerInstance {
        public Instance(Optional<ContextAwarePredicate> player) {
            super(player);
        }

        public static Instance eatChop() {
            return new Instance(Optional.empty());
        }
    }
}
