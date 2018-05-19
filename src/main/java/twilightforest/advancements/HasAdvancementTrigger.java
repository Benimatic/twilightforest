package twilightforest.advancements;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.critereon.AbstractCriterionInstance;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class HasAdvancementTrigger implements ICriterionTrigger<HasAdvancementTrigger.Instance> {
    private static final ResourceLocation ID = new ResourceLocation(TwilightForestMod.ID, "has_advancement");
    private final Map<PlayerAdvancements, HasAdvancementTrigger.Listeners> listeners = Maps.newHashMap();

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public void addListener(PlayerAdvancements playerAdvancementsIn, Listener<Instance> listener) {
        HasAdvancementTrigger.Listeners listeners = this.listeners.computeIfAbsent(playerAdvancementsIn, Listeners::new);

        listeners.add(listener);
    }

    @Override
    public void removeListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<HasAdvancementTrigger.Instance> listener)
    {
        HasAdvancementTrigger.Listeners listeners = this.listeners.get(playerAdvancementsIn);

        if (listeners != null)
        {
            listeners.remove(listener);

            if (listeners.isEmpty())
            {
                this.listeners.remove(playerAdvancementsIn);
            }
        }
    }

    @Override
    public void removeAllListeners(PlayerAdvancements playerAdvancementsIn) {
        this.listeners.remove(playerAdvancementsIn);
    }

    @Override
    public Instance deserializeInstance(JsonObject json, JsonDeserializationContext context) {
        ResourceLocation advancementId = new ResourceLocation(JsonUtils.getString(json, "advancement"));
        return new HasAdvancementTrigger.Instance(advancementId);
    }

    public void trigger(EntityPlayerMP player) {
        Listeners l = listeners.get(player.getAdvancements());
        if (l != null) {
            l.trigger(player);
        }
    }

    static class Instance extends AbstractCriterionInstance {
        private final ResourceLocation advancementLocation;

        Instance(ResourceLocation advancementLocation) {
            super(HasAdvancementTrigger.ID);
            this.advancementLocation = advancementLocation;
        }

        boolean test(EntityPlayerMP player) {
            return TwilightForestMod.proxy.doesPlayerHaveAdvancement(player, advancementLocation);
        }
    }

    private static class Listeners {
        private final PlayerAdvancements playerAdvancements;
        private final Set<Listener<HasAdvancementTrigger.Instance>> listeners = Sets.newHashSet();

        Listeners(PlayerAdvancements playerAdvancements) {
            this.playerAdvancements = playerAdvancements;
        }

        public boolean isEmpty()
        {
            return this.listeners.isEmpty();
        }

        public void add(ICriterionTrigger.Listener<HasAdvancementTrigger.Instance> listener) {
            this.listeners.add(listener);
        }

        public void remove(ICriterionTrigger.Listener<HasAdvancementTrigger.Instance> listener) {
            this.listeners.remove(listener);
        }

        public void trigger(EntityPlayerMP player) {
            List<Listener<HasAdvancementTrigger.Instance>> list = new ArrayList<>();

            for (ICriterionTrigger.Listener<HasAdvancementTrigger.Instance> listener : this.listeners) {
                if (listener.getCriterionInstance().test(player)) {
                    list.add(listener);
                }
            }

            for (ICriterionTrigger.Listener<HasAdvancementTrigger.Instance> listener : list) {
                listener.grantCriterion(this.playerAdvancements);
            }
        }
    }
}
