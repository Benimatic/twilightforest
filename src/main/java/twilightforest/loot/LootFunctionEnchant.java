package twilightforest.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootFunction;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraft.world.storage.loot.functions.ILootFunction;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IRegistryDelegate;
import twilightforest.TwilightForestMod;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

// Similar to EnchantRandomly but applies everything and with exact levels
public class LootFunctionEnchant extends LootFunction {

	private final Map<IRegistryDelegate<Enchantment>, Short> enchantments;

	protected LootFunctionEnchant(ILootCondition[] conditions, Map<IRegistryDelegate<Enchantment>, Short> enchantments) {
		super(conditions);
		this.enchantments = enchantments;
	}

	@Override
	public ItemStack doApply(ItemStack stack, LootContext context) {
		for (Map.Entry<IRegistryDelegate<Enchantment>, Short> e : enchantments.entrySet()) {
			if (stack.getItem() == Items.ENCHANTED_BOOK) {
				EnchantedBookItem.addEnchantment(stack, new EnchantmentData(e.getKey().get(), e.getValue()));
			} else {
				addEnchantment(stack, e.getKey().get(), e.getValue());
			}
		}
		return stack;
	}

	// Not using stack.addEnchantment because it doesn't handle duplicates like enchanted book does
	private void addEnchantment(ItemStack stack, Enchantment e, short level) {
		if (stack.getTag() == null) {
			stack.setTag(new CompoundNBT());
		}

		final String enchantedCompoundKey = stack.getItem() == Items.ENCHANTED_BOOK ? "StoredEnchantments" : "ench";

		if (!stack.getTag().contains(enchantedCompoundKey, Constants.NBT.TAG_LIST)) {
			stack.getTag().put(enchantedCompoundKey, new ListNBT());
		}

		ListNBT list = stack.getTag().getList(enchantedCompoundKey, Constants.NBT.TAG_COMPOUND);

		for (int i = 0; i < list.size(); i++) {
			CompoundNBT existing = list.getCompound(i);
			if (e.getRegistryName().equals(existing.getString("id"))) {
				existing.putShort("lvl", level);
				return;
			}
		}

		CompoundNBT newCmp = new CompoundNBT();
		newCmp.putString("id", e.getRegistryName().toString());
		newCmp.putShort("lvl", level);
		list.add(newCmp);
	}

	public static class Serializer extends LootFunction.Serializer<LootFunctionEnchant> {

		protected Serializer() {
			super(TwilightForestMod.prefix("enchant"), LootFunctionEnchant.class);
		}

		@Override
		public void serialize(JsonObject object, LootFunctionEnchant function, JsonSerializationContext ctx) {
			if (!function.enchantments.isEmpty()) {
				JsonObject obj = new JsonObject();

				for (Map.Entry<IRegistryDelegate<Enchantment>, Short> e : function.enchantments.entrySet()) {
					obj.addProperty(e.getKey().get().getRegistryName().toString(), e.getValue());
				}

				object.add("enchantments", obj);
			}
		}

		@Override
		public LootFunctionEnchant deserialize(JsonObject object, JsonDeserializationContext ctx, ILootCondition[] conditions) {
			Map<IRegistryDelegate<Enchantment>, Short> enchantments = new HashMap<>();

			if (object.has("enchantments")) {
				JsonObject enchantObj = JSONUtils.getJsonObject(object, "enchantments");

				for (Map.Entry<String, JsonElement> e : enchantObj.entrySet()) {
					ResourceLocation id = new ResourceLocation(e.getKey());
					if (!ForgeRegistries.ENCHANTMENTS.containsKey(id)) {
						throw new JsonSyntaxException("Can't find enchantment " + e.getKey());
					}

					Enchantment ench = ForgeRegistries.ENCHANTMENTS.getValue(id);
					short lvl = e.getValue().getAsShort();

					for (IRegistryDelegate<Enchantment> other : enchantments.keySet()) {
						if (!ench.isCompatibleWith(other.get())) {
							throw new JsonParseException(String.format("Enchantments %s and %s conflict", ench.getRegistryName(), other.get().getRegistryName()));
						}
					}

					enchantments.put(ench.delegate, lvl);
				}
			}

			return new LootFunctionEnchant(conditions, enchantments);
		}
	}
}
