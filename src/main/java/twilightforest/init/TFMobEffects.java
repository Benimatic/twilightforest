package twilightforest.init;

import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;
import twilightforest.TwilightForestMod;
import twilightforest.potions.FrostedEffect;

public class TFMobEffects {

	public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, TwilightForestMod.ID);

	public static final RegistryObject<MobEffect> FROSTY = MOB_EFFECTS.register("frosted", FrostedEffect::new);
}
