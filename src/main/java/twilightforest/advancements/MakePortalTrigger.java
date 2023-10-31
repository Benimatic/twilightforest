package twilightforest.advancements;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import twilightforest.TwilightForestMod;

import java.util.Optional;

public class MakePortalTrigger extends SimpleCriterionTrigger<MakePortalTrigger.Instance> {

    public static final ResourceLocation ID = TwilightForestMod.prefix("make_tf_portal");

    @Override
    public Instance createInstance(JsonObject json, Optional<ContextAwarePredicate> player, DeserializationContext condition) {
		return new MakePortalTrigger.Instance(player);
    }

    public void trigger(ServerPlayer player) {
       this.trigger(player, (instance) -> true);
    }

    public static class Instance extends AbstractCriterionTriggerInstance {

        public Instance(Optional<ContextAwarePredicate> player) {
            super(player);
        }

        public static Instance makePortal() {
            return new Instance(Optional.empty());
        }
    }
}
