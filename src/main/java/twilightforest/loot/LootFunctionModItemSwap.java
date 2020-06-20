package twilightforest.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JSONUtils;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootFunction;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraft.world.storage.loot.functions.ILootFunction;
import twilightforest.TwilightForestMod;

import java.util.Random;

// Loot condition for checking that if a mod exists, then swap original item with its deserialized item.
public class LootFunctionModItemSwap extends LootFunction {

    private final Item item;
    private final boolean success;

    protected LootFunctionModItemSwap(ILootCondition[] conditionsIn, Item itemIn, boolean success) {
        super(conditionsIn);
        this.item = itemIn;
        this.success = success;
    }

    @Override
    public ItemStack doApply(ItemStack stack, LootContext context) {
        ItemStack newStack = new ItemStack(item, stack.getCount());

        newStack.setTag(stack.getTag());

        return newStack;
    }

    public static class Serializer extends LootFunction.Serializer<LootFunctionModItemSwap> {

        protected Serializer() {
            super(TwilightForestMod.prefix("item_or_default"), LootFunctionModItemSwap.class);
        }

        @Override
        public void serialize(JsonObject object, LootFunctionModItemSwap function, JsonSerializationContext serializationContext) {
            if (function.success)
                object.addProperty("item", function.item.getRegistryName().toString());
            else

                object.addProperty("default", function.item.getRegistryName().toString());
        }

        @Override
        public LootFunctionModItemSwap deserialize(JsonObject object, JsonDeserializationContext deserializationContext, ILootCondition[] conditionsIn) {
            Item item;
            boolean success;

            try {
                item = JSONUtils.getItem(object, "item");
                success = true;
            } catch (JsonSyntaxException e) {
                item = JSONUtils.getItem(object, "default");
                success = false;
            }

            return new LootFunctionModItemSwap(conditionsIn, item, success);
        }
    }
}
