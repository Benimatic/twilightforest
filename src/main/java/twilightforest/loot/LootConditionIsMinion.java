package twilightforest.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;
import twilightforest.TwilightForestMod;
import twilightforest.entity.EntityTFMiniGhast;

import javax.annotation.Nonnull;

public class LootConditionIsMinion implements ILootCondition {

	private final boolean inverse;

	public LootConditionIsMinion(boolean inverse) {
		this.inverse = inverse;
	}

	@Override
	public boolean test(@Nonnull LootContext context) {
		return context.get(LootParameters.THIS_ENTITY) instanceof EntityTFMiniGhast && ((EntityTFMiniGhast) context.get(LootParameters.THIS_ENTITY)).isMinion() == !inverse;
	}

	public static class Serializer extends ILootCondition.AbstractSerializer<LootConditionIsMinion> {

		protected Serializer() {
			super(TwilightForestMod.prefix("is_minion"), LootConditionIsMinion.class);
		}

		@Override
		public void serialize(@Nonnull JsonObject json, @Nonnull LootConditionIsMinion value, @Nonnull JsonSerializationContext context) {
			json.addProperty("inverse", value.inverse);
		}

		@Nonnull
		@Override
		public LootConditionIsMinion deserialize(@Nonnull JsonObject json, @Nonnull JsonDeserializationContext context) {
			return new LootConditionIsMinion(JSONUtils.getBoolean(json, "inverse", false));
		}
	}
}
