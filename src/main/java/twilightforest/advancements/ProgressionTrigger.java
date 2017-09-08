package twilightforest.advancements;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.critereon.AbstractCriterionInstance;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import twilightforest.TwilightForestMod;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ProgressionTrigger implements ICriterionTrigger<ProgressionTrigger.Instance> {
    private static final ResourceLocation ID = new ResourceLocation(TwilightForestMod.ID + ":" + "progression");
    private final Map<PlayerAdvancements, ProgressionTrigger.Listeners> listeners = Maps.newHashMap();

    private static ArrayList<ResourceLocation> advancementCache = new ArrayList<>(4);

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public void addListener(PlayerAdvancements playerAdvancementsIn, Listener<Instance> listener) {
        ProgressionTrigger.Listeners listeners = this.listeners.computeIfAbsent(playerAdvancementsIn, Listeners::new);

        listeners.add(listener);
    }

    @Override
    public void removeListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<ProgressionTrigger.Instance> listener)
    {
        ProgressionTrigger.Listeners listeners = this.listeners.get(playerAdvancementsIn);

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
        ResourceLocation advancementLocation = new ResourceLocation(JsonUtils.getString(json, "advancement"));

        //MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        //Advancement advancement = server.getAdvancementManager().getAdvancement(advancementLocation);

        if (!advancementCache.contains(advancementLocation)) {
            advancementCache.add(advancementLocation);
        }

        return new ProgressionTrigger.Instance(advancementLocation);
    }

    public void trigger(EntityPlayer player) {
        ProgressionTrigger.Listeners listeners;

        FMLCommonHandler.instance().getMinecraftServerInstance();

        if (player instanceof EntityPlayerMP) {
            TwilightForestMod.LOGGER.info("testing advancements for player " + player.getDisplayName());

            listeners = this.listeners.get(((EntityPlayerMP)player).getAdvancements());

            if (listeners != null) listeners.trigger();
        }
    }

    class Instance extends AbstractCriterionInstance {
        ResourceLocation advancementLocation;

        Instance(ResourceLocation advancementLocation) {
            super(ProgressionTrigger.ID);
            this.advancementLocation = advancementLocation;
        }

        boolean test(ResourceLocation advancementLocation) {
            TwilightForestMod.LOGGER.info("testing " + advancementLocation + " to instance's " + this.advancementLocation);
            return this.advancementLocation.equals(advancementLocation);
        }
    }

    private static class Listeners {
        private final PlayerAdvancements playerAdvancements;
        private final Set<Listener<ProgressionTrigger.Instance>> listeners = Sets.newHashSet();

        Listeners(PlayerAdvancements playerAdvancements) {
            this.playerAdvancements = playerAdvancements;
        }

        public boolean isEmpty()
        {
            return this.listeners.isEmpty();
        }

        public void add(ICriterionTrigger.Listener<ProgressionTrigger.Instance> listener) {
            this.listeners.add(listener);
        }

        public void remove(ICriterionTrigger.Listener<ProgressionTrigger.Instance> listener) {
            this.listeners.remove(listener);
        }

        public void trigger() {
            List<Listener<ProgressionTrigger.Instance>> list = null;

            for (ResourceLocation location : advancementCache) {
                for (ICriterionTrigger.Listener<ProgressionTrigger.Instance> listener : this.listeners) {
                    if (listener.getCriterionInstance().test(location)) {
                        if (list == null) {
                            list = Lists.newArrayList();
                        }

                        list.add(listener);
                    }
                }
            }

            if (list != null) {
                for (ICriterionTrigger.Listener<ProgressionTrigger.Instance> listener : list) {
                    listener.grantCriterion(this.playerAdvancements);
                }
            }
        }
    }
}
