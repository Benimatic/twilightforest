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

public class QuestRamCompletionTrigger extends SimpleCriterionTrigger<QuestRamCompletionTrigger.TriggerInstance> {

    public static final ResourceLocation ID = TwilightForestMod.prefix("complete_quest_ram");

    @Override
    public QuestRamCompletionTrigger.TriggerInstance createInstance(JsonObject json, Optional<ContextAwarePredicate> player, DeserializationContext condition) {
		return new QuestRamCompletionTrigger.TriggerInstance(player);
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, (instance) -> true);
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        public TriggerInstance(Optional<ContextAwarePredicate> player) {
            super(player);
        }

        public static Criterion<QuestRamCompletionTrigger.TriggerInstance> completeRam() {
            return TFAdvancements.QUEST_RAM_COMPLETED.createCriterion(new TriggerInstance(Optional.empty()));
        }
    }
}
