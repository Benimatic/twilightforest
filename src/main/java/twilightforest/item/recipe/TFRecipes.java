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

		GameRegistry.addSmelting(TFBlocks.twilight_log, new ItemStack(Items.COAL, 1, 1), 0.1F);
		GameRegistry.addSmelting(TFBlocks.magic_log, new ItemStack(Items.COAL, 1, 1), 0.1F);
		GameRegistry.addSmelting(TFItems.ironwood_raw, new ItemStack(TFItems.ironwood_ingot, 2), 1.0F);
		GameRegistry.addSmelting(TFItems.raw_venison, new ItemStack(TFItems.cooked_venison), 0.3F);
		GameRegistry.addSmelting(TFItems.raw_meef, new ItemStack(TFItems.cooked_meef), 0.3F);
		GameRegistry.addSmelting(TFItems.armor_shard_cluster, new ItemStack(TFItems.knightmetal_ingot), 1.0F);

		event.getRegistry().register(new TFArmorDyeingRecipe().setRegistryName(TwilightForestMod.ID, "arctic_armor_dyeing"));

		event.getRegistry().register(new TFMapCloningRecipe(TFItems.magic_map, TFItems.magic_map_empty).setRegistryName(TwilightForestMod.ID, "magic_map_cloning"));
		event.getRegistry().register(new TFMapCloningRecipe(TFItems.maze_map, TFItems.maze_map_empty).setRegistryName(TwilightForestMod.ID, "maze_map_cloning"));
		event.getRegistry().register(new TFMapCloningRecipe(TFItems.ore_map, TFItems.ore_map_empty).setRegistryName(TwilightForestMod.ID, "ore_map_cloning"));
	}
}
