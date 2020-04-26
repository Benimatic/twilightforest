package twilightforest.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import twilightforest.TwilightForestMod;

public class TFEnchantments {

	public static final DeferredRegister<Enchantment> ENCHANTMENTS = new DeferredRegister<>(ForgeRegistries.ENCHANTMENTS, TwilightForestMod.ID);

	public static final RegistryObject<Enchantment> FIRE_REACT = ENCHANTMENTS.register("fire_react", () -> new EnchantmentTFFireReact(Enchantment.Rarity.UNCOMMON));
	public static final RegistryObject<Enchantment> CHILL_AURA = ENCHANTMENTS.register("chill_aura", () -> new EnchantmentTFChillAura(Enchantment.Rarity.UNCOMMON));
}
