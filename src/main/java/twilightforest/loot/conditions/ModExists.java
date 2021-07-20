package twilightforest.loot.conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;
import net.minecraftforge.fml.ModList;
import twilightforest.loot.TFTreasure;

// Loot condition for checking if a mod exists.
public class ModExists implements ILootCondition {

    private final boolean exists;
    private final String modID;

    public ModExists(String modID) {
        this.exists = ModList.get().isLoaded(modID);
        this.modID = modID;
    }

    @Override
    public LootConditionType getConditionType() {
        return TFTreasure.MOD_EXISTS;
    }

    @Override
    public boolean test(LootContext context) {
        return exists;
    }

    public static ILootCondition.IBuilder builder(String modid) {
        return () -> new ModExists(modid);
    }

    public static class Serializer implements ILootSerializer<ModExists> {

		@Override
		public void serialize(JsonObject json, ModExists value, JsonSerializationContext context) {
			json.addProperty("mod_id", value.modID);
		}

		@Override
		public ModExists deserialize(JsonObject json, JsonDeserializationContext context) {
			return new ModExists(JSONUtils.getString(json, "mod_id"));
		}
	}
}
