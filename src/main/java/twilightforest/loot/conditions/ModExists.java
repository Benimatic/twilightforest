package twilightforest.loot.conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.fml.ModList;
import twilightforest.loot.TFTreasure;

// Loot condition for checking if a mod exists.
public class ModExists implements LootItemCondition {

    private final boolean exists;
    private final String modID;

    public ModExists(String modID) {
        this.exists = ModList.get().isLoaded(modID);
        this.modID = modID;
    }

    @Override
    public LootItemConditionType getType() {
        return TFTreasure.MOD_EXISTS;
    }

    @Override
    public boolean test(LootContext context) {
        return exists;
    }

    public static LootItemCondition.Builder builder(String modid) {
        return () -> new ModExists(modid);
    }

    public static class Serializer implements Serializer<ModExists> {

		@Override
		public void serialize(JsonObject json, ModExists value, JsonSerializationContext context) {
			json.addProperty("mod_id", value.modID);
		}

		@Override
		public ModExists deserialize(JsonObject json, JsonDeserializationContext context) {
			return new ModExists(GsonHelper.getAsString(json, "mod_id"));
		}
	}
}
