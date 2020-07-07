package twilightforest.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;
import net.minecraftforge.fml.ModList;
import twilightforest.TwilightForestMod;

import javax.annotation.Nonnull;

// Loot condition for checking if a mod exists.
public class LootConditionModExists implements ILootCondition {

    private final boolean exists;
    private final String modID;

    public LootConditionModExists(String modID) {
        this.exists = ModList.get().isLoaded(modID);
        this.modID = modID;
    }

    @Override
    public boolean test(LootContext context) {
        return exists;
    }

    public static class Serializer extends ILootCondition.AbstractSerializer<LootConditionModExists> {

        protected Serializer() {
            super(TwilightForestMod.prefix("mod_exists"), LootConditionModExists.class);
        }

        @Override
        public void serialize(@Nonnull JsonObject json, @Nonnull LootConditionModExists value, @Nonnull JsonSerializationContext context) {
            json.addProperty("mod_id", value.modID);
        }

        @Nonnull
        @Override
        public LootConditionModExists deserialize(@Nonnull JsonObject json, @Nonnull JsonDeserializationContext context) {
            return new LootConditionModExists(JSONUtils.getString(json, "mod_id"));
        }
    }
}
