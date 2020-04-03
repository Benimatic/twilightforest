package twilightforest.potions;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.TwilightForestMod;
import twilightforest.client.GuiUtils;

import java.util.UUID;

public class PotionFrosted extends Effect {

	public static final UUID MODIFIER_UUID = UUID.fromString("CE9DBC2A-EE3F-43F5-9DF7-F7F1EE4915A9");

	private static final ResourceLocation sprite = TwilightForestMod.prefix("textures/gui/frosty.png");

	public PotionFrosted(EffectType typeIn, int liquidColorIn) {
		super(typeIn, liquidColorIn);

		addAttributesModifier(SharedMonsterAttributes.MOVEMENT_SPEED, PotionFrosted.MODIFIER_UUID.toString(), -0.15000000596046448D, AttributeModifier.Operation.MULTIPLY_TOTAL);
	}
}
