package twilightforest.item.recipe;

import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import twilightforest.TwilightForestMod;
import twilightforest.block.TFBlocks;
import twilightforest.item.TFItems;

@Mod.EventBusSubscriber(modid = TwilightForestMod.ID)
public class TFRecipes {

	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {

		// recipe sorter
		// RecipeSorter.register(TwilightForestMod.ID + ":mapcloning", TFMapCloningRecipe.class, SHAPELESS, "after:minecraft:shapeless");

		event.getRegistry().register(new TFArmorDyeingRecipe().setRegistryName(TwilightForestMod.ID, "arctic_armor_dyeing"));

		event.getRegistry().register(new TFMapCloningRecipe(TFItems.magic_map, TFItems.magic_map_empty).setRegistryName(TwilightForestMod.ID, "magic_map_cloning"));
		event.getRegistry().register(new TFMapCloningRecipe(TFItems.maze_map, TFItems.maze_map_empty).setRegistryName(TwilightForestMod.ID, "maze_map_cloning"));
		event.getRegistry().register(new TFMapCloningRecipe(TFItems.ore_map, TFItems.ore_map_empty).setRegistryName(TwilightForestMod.ID, "ore_map_cloning"));
	}
}
