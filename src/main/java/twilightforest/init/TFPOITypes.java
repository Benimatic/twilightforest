package twilightforest.init;

import com.google.common.collect.ImmutableSet;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import twilightforest.TwilightForestMod;

public class TFPOITypes {

	public static final DeferredRegister<PoiType> POIS = DeferredRegister.create(ForgeRegistries.POI_TYPES, TwilightForestMod.ID);

	public static final RegistryObject<PoiType> GHAST_TRAP = POIS.register("ghast_trap", () -> new PoiType(ImmutableSet.copyOf(TFBlocks.GHAST_TRAP.get().getStateDefinition().getPossibleStates()), 0, 1));

}
