package twilightforest.potions;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import twilightforest.TwilightForestMod;

public class TFPotions {

	public static final DeferredRegister<MobEffect> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, TwilightForestMod.ID);

	public static final RegistryObject<MobEffect> frosty = POTIONS.register("frosted",() -> new FrostedPotion(MobEffectCategory.HARMFUL, 0x56CBFD));
}
