package twilightforest.inventory;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import twilightforest.TwilightForestMod;
import twilightforest.client.UncraftingGui;

public class TFContainers {

	public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, TwilightForestMod.ID);

	public static final RegistryObject<ContainerType<UncraftingContainer>> UNCRAFTING = CONTAINERS.register("uncrafting",
			() -> new ContainerType<>(UncraftingContainer::fromNetwork));

	@OnlyIn(Dist.CLIENT)
	public static void renderScreens() {
		ScreenManager.registerFactory(UNCRAFTING.get(), UncraftingGui::new);
	}
}
