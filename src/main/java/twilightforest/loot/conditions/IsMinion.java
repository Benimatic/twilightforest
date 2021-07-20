package twilightforest.loot.conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;
import twilightforest.entity.CarminiteGhastlingEntity;
import twilightforest.loot.TFTreasure;

import javax.annotation.Nonnull;

public class IsMinion implements ILootCondition {

	private final boolean inverse;

	public IsMinion(boolean inverse) {
		this.inverse = inverse;
	}

	@Override
	public LootConditionType getConditionType() {
		return TFTreasure.IS_MINION;
	}

	@Override
	public boolean test(@Nonnull LootContext context) {
		return context.get(LootParameters.THIS_ENTITY) instanceof CarminiteGhastlingEntity && ((CarminiteGhastlingEntity) context.get(LootParameters.THIS_ENTITY)).isMinion() == !inverse;
	}

	public static IBuilder builder(boolean inverse) {
		return () -> new IsMinion(inverse);
	}

	public static class Serializer implements ILootSerializer<IsMinion> {

		@Override
		public void serialize(JsonObject json, IsMinion value, JsonSerializationContext context) {
			json.addProperty("inverse", value.inverse);
		}

		@Override
		public IsMinion deserialize(JsonObject json, JsonDeserializationContext context) {
			return new IsMinion(JSONUtils.getBoolean(json, "inverse", false));
		}
	}
}
