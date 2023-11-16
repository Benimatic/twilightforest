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

public class ActivateGhastTrapTrigger extends SimpleCriterionTrigger<ActivateGhastTrapTrigger.TriggerInstance> {

    public static final ResourceLocation ID = TwilightForestMod.prefix("activate_ghast_trap");

    @Override
    protected ActivateGhastTrapTrigger.TriggerInstance createInstance(JsonObject json, Optional<ContextAwarePredicate> player, DeserializationContext ctx) {
        return new ActivateGhastTrapTrigger.TriggerInstance(player);
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, (instance) -> true);
    }


    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        public TriggerInstance(Optional<ContextAwarePredicate> player) {
            super(player);
        }

        public static Criterion<ActivateGhastTrapTrigger.TriggerInstance> activateTrap() {
            return TFAdvancements.ACTIVATED_GHAST_TRAP.createCriterion(new TriggerInstance(Optional.empty()));
        }
    }
}
