package twilightforest.potions;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import java.util.UUID;

public class PotionFrosted extends Effect {

	public static final UUID MODIFIER_UUID = UUID.fromString("CE9DBC2A-EE3F-43F5-9DF7-F7F1EE4915A9");

	public PotionFrosted(EffectType typeIn, int liquidColorIn) {
		super(typeIn, liquidColorIn);

		addAttributesModifier(Attributes.field_233821_d_, PotionFrosted.MODIFIER_UUID.toString(), -0.15000000596046448D, AttributeModifier.Operation.MULTIPLY_TOTAL);
	}
}
