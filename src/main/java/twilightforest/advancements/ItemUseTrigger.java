package twilightforest.advancements;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import twilightforest.TwilightForestMod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.advancements.CriterionTrigger.Listener;

/**
 THIS WILL ONLY WORK ON SPECIFIC ITEMS
*/
public class ItemUseTrigger implements CriterionTrigger<ItemUseTrigger.Instance> {

    public static final ResourceLocation ID = TwilightForestMod.prefix("on_item_use");
    private final Map<PlayerAdvancements, ItemUseTrigger.Listeners> listeners = Maps.newHashMap();

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public void addPlayerListener(PlayerAdvancements playerAdvancementsIn, Listener<ItemUseTrigger.Instance> listener) {
        ItemUseTrigger.Listeners listeners = this.listeners.computeIfAbsent(playerAdvancementsIn, Listeners::new);
        listeners.add(listener);
    }

    @Override
    public void removePlayerListener(PlayerAdvancements playerAdvancementsIn, Listener<ItemUseTrigger.Instance> listener) {
        ItemUseTrigger.Listeners listeners = this.listeners.get(playerAdvancementsIn);
        if (listeners != null) {
            listeners.remove(listener);
            if (listeners.isEmpty()) {
                this.listeners.remove(playerAdvancementsIn);
            }
        }
    }

    @Override
    public void removePlayerListeners(PlayerAdvancements playerAdvancementsIn) {
        this.listeners.remove(playerAdvancementsIn);
    }

    @Override
    public Instance createInstance(JsonObject json, DeserializationContext condition) {
		EntityPredicate.Composite player = EntityPredicate.Composite.fromJson(json, "player", condition);
		ItemPredicate item = ItemPredicate.fromJson(json.get("item"));
		BlockPredicate block = BlockPredicate.deserialize(json.get("block"));
		return new ItemUseTrigger.Instance(player, item, block);
    }

    public void trigger(ServerPlayer player, ItemStack item, Level world, BlockPos pos) {
        ItemUseTrigger.Listeners listeners = this.listeners.get(player.getAdvancements());
        if (listeners != null) {
            listeners.trigger(item, world, pos);
        }
    }

    public static class Instance extends AbstractCriterionTriggerInstance {

        private final ItemPredicate item;
        private final BlockPredicate block;

        public Instance(EntityPredicate.Composite player, ItemPredicate item, BlockPredicate block) {
            super(ItemUseTrigger.ID, player);
            this.item = item;
            this.block = block;
        }

        public boolean test(ItemStack item, Level world, BlockPos pos) {
            return this.item.matches(item) && this.block.test(world, pos);
        }
    }

    static class Listeners {

        private final PlayerAdvancements playerAdvancements;
        private final Set<Listener<Instance>> listeners = Sets.newHashSet();

        public Listeners(PlayerAdvancements playerAdvancements) {
            this.playerAdvancements = playerAdvancements;
        }

        public boolean isEmpty() {
            return this.listeners.isEmpty();
        }

        public void add(Listener<Instance> listener) {
            this.listeners.add(listener);
        }

        public void remove(Listener<Instance> listener) {
            this.listeners.remove(listener);
        }

        public void trigger(ItemStack item, Level world, BlockPos pos) {

            List<Listener<Instance>> list = new ArrayList<>();

            for (Listener<Instance> listener : this.listeners) {
                if (listener.getTriggerInstance().test(item, world, pos)) {
                    list.add(listener);
                }
            }

            for (Listener<Instance> listener : list) {
                listener.run(this.playerAdvancements);
            }
        }
    }
}
