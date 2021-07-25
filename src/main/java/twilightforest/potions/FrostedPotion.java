package twilightforest.potions;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import java.util.UUID;

public class FrostedPotion extends MobEffect {

	public static final UUID MODIFIER_UUID = UUID.fromString("CE9DBC2A-EE3F-43F5-9DF7-F7F1EE4915A9");

	public FrostedPotion(MobEffectCategory typeIn, int liquidColorIn) {
		super(typeIn, liquidColorIn);

		addAttributeModifier(Attributes.MOVEMENT_SPEED, FrostedPotion.MODIFIER_UUID.toString(), -0.15000000596046448D, AttributeModifier.Operation.MULTIPLY_TOTAL);
	}
}
