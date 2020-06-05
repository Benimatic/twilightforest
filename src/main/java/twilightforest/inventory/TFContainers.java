package twilightforest.inventory;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import twilightforest.TwilightForestMod;
import twilightforest.client.GuiTFGoblinCrafting;

public class TFContainers {

	public static final DeferredRegister<ContainerType<?>> CONTAINERS = new DeferredRegister<>(ForgeRegistries.CONTAINERS, TwilightForestMod.ID);

	public static final RegistryObject<ContainerType<ContainerTFUncrafting>> UNCRAFTING = CONTAINERS.register("uncrafting",
			() -> new ContainerType<>(ContainerTFUncrafting::fromNetwork));

	@OnlyIn(Dist.CLIENT)
	public static void renderScreens() {
		ScreenManager.registerFactory(UNCRAFTING.get(), GuiTFGoblinCrafting::new);
	}
}
