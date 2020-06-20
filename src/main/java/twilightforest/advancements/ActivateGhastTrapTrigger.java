package twilightforest.advancements;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;

import java.util.Map;
import java.util.Set;

public class ActivateGhastTrapTrigger implements ICriterionTrigger<ActivateGhastTrapTrigger.Instance> {

    public static final ResourceLocation ID = TwilightForestMod.prefix("activate_ghast_trap");
    private final Map<PlayerAdvancements, ActivateGhastTrapTrigger.Listeners> listeners = Maps.newHashMap();

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public void addListener(PlayerAdvancements playerAdvancementsIn, Listener<ActivateGhastTrapTrigger.Instance> listener) {
        ActivateGhastTrapTrigger.Listeners listeners = this.listeners.computeIfAbsent(playerAdvancementsIn, Listeners::new);
        listeners.add(listener);
    }

    @Override
    public void removeListener(PlayerAdvancements playerAdvancementsIn, Listener<ActivateGhastTrapTrigger.Instance> listener) {
        ActivateGhastTrapTrigger.Listeners listeners = this.listeners.get(playerAdvancementsIn);
        if (listeners != null) {
            listeners.remove(listener);
            if (listeners.isEmpty()) {
                this.listeners.remove(playerAdvancementsIn);
            }
        }
    }

    @Override
    public void removeAllListeners(PlayerAdvancements playerAdvancementsIn) {
        this.listeners.remove(playerAdvancementsIn);
    }

    @Override
    public ActivateGhastTrapTrigger.Instance deserializeInstance(JsonObject json, JsonDeserializationContext context) {
        return new ActivateGhastTrapTrigger.Instance();
    }

    public void trigger(ServerPlayerEntity player) {
        ActivateGhastTrapTrigger.Listeners listeners = this.listeners.get(player.getAdvancements());
        if (listeners != null) {
            listeners.trigger();
        }
    }

    public static class Instance extends CriterionInstance {

        public Instance() {
            super(ActivateGhastTrapTrigger.ID);
        }
    }

    static class Listeners {

        private final PlayerAdvancements playerAdvancements;
        private final Set<Listener<ActivateGhastTrapTrigger.Instance>> listeners = Sets.newHashSet();

        public Listeners(PlayerAdvancements playerAdvancementsIn) {
            this.playerAdvancements = playerAdvancementsIn;
        }

        public boolean isEmpty() {
            return this.listeners.isEmpty();
        }

        public void add(Listener<ActivateGhastTrapTrigger.Instance> listener) {
            this.listeners.add(listener);
        }

        public void remove(Listener<ActivateGhastTrapTrigger.Instance> listener) {
            this.listeners.remove(listener);
        }

        public void trigger() {
            for (Listener<ActivateGhastTrapTrigger.Instance> listener : Lists.newArrayList(this.listeners)) {
                listener.grantCriterion(this.playerAdvancements);
            }
        }
    }
}
