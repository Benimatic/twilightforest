package twilightforest.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import twilightforest.init.TFLoot;

// Loot condition for checking that if a mod exists, then swap original item with its deserialized item.
public class ModItemSwap extends LootItemConditionalFunction {

	private final Item item;
	private final Item oldItem;
	private final boolean success;

	protected ModItemSwap(LootItemCondition[] conditions, Item item, Item old, boolean success) {
		super(conditions);
		this.item = item;
		this.oldItem = old;
		this.success = success;
	}

	@Override
	public LootItemFunctionType getType() {
		return TFLoot.ITEM_OR_DEFAULT.get();
	}

	@Override
	public ItemStack run(ItemStack stack, LootContext context) {
		ItemStack newStack = new ItemStack(this.item, stack.getCount());

		newStack.setTag(stack.getTag());

		return newStack;
	}

	public static ModItemSwap.Builder builder() {
		return new ModItemSwap.Builder();
	}

	public static class Builder extends LootItemConditionalFunction.Builder<ModItemSwap.Builder> {
		private String idtocheck;
		private Item item;
		private Item oldItem;

		protected ModItemSwap.Builder getThis() {
			return this;
		}

		public ModItemSwap.Builder apply(String modid, Item item, Item old) {
			this.idtocheck = modid;
			this.item = item;
			this.oldItem = old;
			return this;
		}

		public LootItemFunction build() {
			return new ModItemSwap(this.getConditions(), this.item, this.oldItem, ModList.get().isLoaded(this.idtocheck));
		}
	}

	public static class Serializer extends LootItemConditionalFunction.Serializer<ModItemSwap> {

		@Override
		public void serialize(JsonObject object, ModItemSwap function, JsonSerializationContext serializationContext) {
			if (function.success)
				object.addProperty("item", ForgeRegistries.ITEMS.getKey(function.item).toString());
			else
				object.addProperty("default", ForgeRegistries.ITEMS.getKey(function.item).toString());
			object.addProperty("default", ForgeRegistries.ITEMS.getKey(function.oldItem).toString());
		}

		@Override
		public ModItemSwap deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootItemCondition[] conditions) {
			Item item;
			boolean success;

			try {
				item = GsonHelper.getAsItem(object, "item");
				success = true;
			} catch (JsonSyntaxException e) {
				item = GsonHelper.getAsItem(object, "default");
				success = false;
			}

			return new ModItemSwap(conditions, item, GsonHelper.getAsItem(object, "default"), success);
		}
	}
}
