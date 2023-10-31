package twilightforest.advancements;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.ItemLike;
import twilightforest.TwilightForestMod;

import java.util.Optional;

public class UncraftItemTrigger extends SimpleCriterionTrigger<UncraftItemTrigger.TriggerInstance> {
	public static final ResourceLocation ID = TwilightForestMod.prefix("uncraft_item");

	@Override
	protected UncraftItemTrigger.TriggerInstance createInstance(JsonObject json, Optional<ContextAwarePredicate> player, DeserializationContext ctx) {
		return new UncraftItemTrigger.TriggerInstance(player, ItemPredicate.fromJson(json.get("item")));
	}

	public void trigger(ServerPlayer player, ItemStack stack) {
		this.trigger(player, (instance) -> instance.matches(stack));
	}

	public static class TriggerInstance extends AbstractCriterionTriggerInstance {

		private final Optional<ItemPredicate> item;

		public TriggerInstance(Optional<ContextAwarePredicate> player, Optional<ItemPredicate> item) {
			super(player);
			this.item = item;
		}

		public static UncraftItemTrigger.TriggerInstance uncraftedItem() {
			return new UncraftItemTrigger.TriggerInstance(Optional.empty(), Optional.empty());
		}

		public static UncraftItemTrigger.TriggerInstance uncraftedItem(ItemPredicate predicate) {
			return new UncraftItemTrigger.TriggerInstance(Optional.empty(), Optional.of(predicate));
		}

		public static UncraftItemTrigger.TriggerInstance uncraftedItem(ItemLike item) {
			return new UncraftItemTrigger.TriggerInstance(Optional.empty(), new ItemPredicate((TagKey<Item>)null, ImmutableSet.of(item.asItem()), MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, EnchantmentPredicate.NONE, EnchantmentPredicate.NONE, (Potion)null, NbtPredicate.ANY));
		}

		public boolean matches(ItemStack item) {
			return this.item.isEmpty() || !this.item.get().matches(item);
		}

		@Override
		public JsonObject serializeToJson() {
			JsonObject jsonobject = super.serializeToJson();
			this.item.ifPresent(predicate -> jsonobject.add("item", predicate.serializeToJson()));
			return jsonobject;
		}
	}
}
