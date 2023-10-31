package twilightforest.init;

import com.mojang.serialization.Codec;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;
import twilightforest.TwilightForestMod;
import twilightforest.loot.modifiers.FieryToolSmeltingModifier;
import twilightforest.loot.modifiers.GiantToolGroupingModifier;

public class TFLootModifiers {

	public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, TwilightForestMod.ID);

	public static final RegistryObject<Codec<FieryToolSmeltingModifier>> FIERY_PICK_SMELTING = LOOT_MODIFIERS.register("fiery_pick_smelting", () -> FieryToolSmeltingModifier.CODEC);
	public static final RegistryObject<Codec<GiantToolGroupingModifier>> GIANT_PICK_GROUPING = LOOT_MODIFIERS.register("giant_block_grouping", () -> GiantToolGroupingModifier.CODEC);
}
