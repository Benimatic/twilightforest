package twilightforest.compat;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import twilightforest.TwilightForestMod;
import twilightforest.block.TFBlocks;
import twilightforest.item.TFItems;

public class Thaumcraft {
    // Use the thaumcraft API to register our things with aspects and biomes with values
    static void init() {
        try {
            // TODO Redo list, this all has to
            // items
            registerTCObjectTag(TFItems.nagaScale, -1, (new AspectList()).add(Aspect.MOTION, 2).add(Aspect.PROTECT, 3));

            TwilightForestMod.LOGGER.info("[TwilightForest] Loaded ThaumcraftApi integration.");
        } catch (Exception e) {
            TwilightForestMod.LOGGER.warn("[TwilightForest] Had an %s error while trying to register with ThaumcraftApi.", e.getLocalizedMessage());
            // whatever.
        }
    }

    // Register a block with Thaumcraft aspects
    private static void registerTCObjectTag(Block block, int meta, AspectList list) {
        if (meta == -1) meta = OreDictionary.WILDCARD_VALUE;
        ThaumcraftApi.registerObjectTag(new ItemStack(block, 1, meta), list);
    }

    // Register an item with Thaumcraft aspects
    private static void registerTCObjectTag(Item item, int meta, AspectList list) {
        if (meta == -1) meta = OreDictionary.WILDCARD_VALUE;
        ThaumcraftApi.registerObjectTag(new ItemStack(item, 1, meta), list);
    }
}
