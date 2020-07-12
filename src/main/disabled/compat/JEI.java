package twilightforest.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import twilightforest.block.TFBlocks;

import static twilightforest.TwilightForestMod.prefix;

@JeiPlugin
public class JEI implements IModPlugin {
    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(TFBlocks.uncrafting_table.get()), VanillaRecipeCategoryUid.CRAFTING);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(new UncraftingRecipeTransferHandler());
    }

    @Override
    public ResourceLocation getPluginUid() {
        return prefix("jei_plugin");
    }
}
