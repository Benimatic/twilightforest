package twilightforest.advancements;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.critereon.AbstractCriterionInstance;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class AdvancementTrigger implements ICriterionTrigger<AdvancementTrigger.Instance> {
    private static final ResourceLocation ID = new ResourceLocation(TwilightForestMod.ID + ":" + "progression");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public void addListener(PlayerAdvancements playerAdvancementsIn, Listener<Instance> listener) {
    }

    @Override
    public void removeListener(PlayerAdvancements playerAdvancementsIn, Listener<Instance> listener) {
    }

    @Override
    public void removeAllListeners(PlayerAdvancements playerAdvancementsIn) {
    }

    @Override
    public Instance deserializeInstance(JsonObject json, JsonDeserializationContext context) {
        return null;
    }

    public class Instance extends AbstractCriterionInstance {
        public Instance() {
            super(AdvancementTrigger.ID);
        }
    }
}
