package twilightforest.compat;

import blusunrize.immersiveengineering.api.shader.IShaderItem;
import blusunrize.immersiveengineering.api.shader.ShaderCase;
import blusunrize.immersiveengineering.api.shader.ShaderRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import twilightforest.TwilightForestMod;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.item.TFItems;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

// TODO Move to shader capability, where we can then just turn item Trophies into the shaders
public class ItemTFShader extends Item implements IShaderItem, ModelRegisterCallback {
    public ItemTFShader() {
        this.setHasSubtypes(true);
        this.setCreativeTab(TFItems.creativeTab);
    }

    private static final String TAG_SHADER = "shader_type";
    private static final List<ShaderRegistry.ShaderRegistryEntry> SHADERS;
    public static final ItemTFShader shader = new ItemTFShader();

    @Override
    public ShaderCase getShaderCase(ItemStack shader, ItemStack tool, String shaderType) {
        return ShaderRegistry.getShader(getShaderType(shader), shaderType);
    }

    @Override
    public String getShaderName(ItemStack stack) {
        return getShaderType(stack);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        String rawShaderName = this.getShaderName(stack);
        String unlocalizedShaderName = "twilightforest.shader." + rawShaderName.replace(' ', '_').toLowerCase(Locale.ROOT);
        String localizedShaderName = I18n.translateToLocal(unlocalizedShaderName);

        if (unlocalizedShaderName.equals(localizedShaderName))
            return I18n.translateToLocalFormatted(this.getUnlocalizedName(stack), rawShaderName); // Translation failure
        else
            return I18n.translateToLocalFormatted(this.getUnlocalizedName(stack), localizedShaderName);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return TwilightForestMod.getRarity();
    }

    @Nonnull
    private static String getShaderType(ItemStack stack) {
        NBTTagCompound compound = stack.getTagCompound();

        if (compound != null) {
            String string = stack.getTagCompound().getString(TAG_SHADER);

            if (!string.isEmpty() && ShaderRegistry.shaderRegistry.containsKey(string))
                return string;
        }

        return "Twilight";
    }

    public static int getShaderColors(ItemStack stack, int layer) {
        ShaderRegistry.ShaderRegistryEntry entry = ShaderRegistry.shaderRegistry.get(getShaderType(stack));

        if (entry != null) {
            ShaderCase shaderCase = entry.getCase("immersiveengineering:item");

            if (shaderCase != null) {
                ShaderCase.ShaderLayer[] layers = shaderCase.getLayers();

                return layers[layer % layers.length].getColour();
            }
        }

        return 0xFF_FF_FF_FF;
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        for (ShaderRegistry.ShaderRegistryEntry entry : SHADERS) {
            NBTTagCompound compound = new NBTTagCompound();

            compound.setString(TAG_SHADER, entry.getName());

            ItemStack stack = new ItemStack(this, 1, 0);

            stack.setTagCompound(compound);

            items.add(stack);
        }
    }

    static {
        if (TwilightForestMod.getRarity() != EnumRarity.EPIC)
            ShaderRegistry.rarityWeightMap.put(TwilightForestMod.getRarity(), 1);

        SHADERS = Arrays.asList( // MAIN COLOR, MINOR COLOR (EDGES), SECONDARY COLOR (GRIP, etc)
                ShaderRegistry.registerShader("Twilight", "0", TwilightForestMod.getRarity(), 0xFF_00_AA_00, 0xFF_28_25_3F, 0xFF_4C_64_5B, 0xFF_FF_FF_FF, null, 0xFF_FF_FF_FF, false, true).setInfo("Twilight Forest", "Twilight Forest", "twilightforest"),

                //ShaderRegistry.registerShader("Snakestone", "0", TwilightForestMod.getRarity(), 0xFF_00_AA_00, 0xFF_28_25_3F, 0xFF_4C_64_5B, 0xFF_80_80_80, null, 0xFF_FF_FF_FF, false, true).setInfo("Twilight Forest", "Twilight Forest", "twilightforest"),
                ShaderRegistry.registerShader("Naga", "0", TwilightForestMod.getRarity(), 0xFF_32_5D_25, 0xFF_17_29_11, 0xFF_A5_D4_16, 0xFF_80_80_80, null, 0xFF_FF_FF_FF, false, true).setInfo("Twilight Forest", "Twilight Forest", "twilightforest"),
                ShaderRegistry.registerShader("Lich", "0", TwilightForestMod.getRarity(), 0xFF_DF_D9_CC, 0xFF_C3_9C_00, 0xFF_3A_04_75, 0xFF_80_80_80, null, 0xFF_FF_FF_FF, false, true).setInfo("Twilight Forest", "Twilight Forest", "twilightforest"),

                ShaderRegistry.registerShader("Mazestone", "0", TwilightForestMod.getRarity(), 0xFF_70_7B_70, 0xFF_8E_99_8E, 0xFF_65_6E_65, 0xFF_FF_FF_FF, null, 0xFF_FF_FF_FF, false, true).setInfo("Twilight Forest", "Twilight Forest", "twilightforest"),
                ShaderRegistry.registerShader("Minoshroom", "0", TwilightForestMod.getRarity(), 0xFF_A8_10_12, 0xFF_B3_B3_B3, 0xFF_33_EB_CB, 0xFF_80_80_80, null, 0xFF_FF_FF_FF, false, true).setInfo("Twilight Forest", "Twilight Forest", "twilightforest"),
                ShaderRegistry.registerShader("Hydra", "0", TwilightForestMod.getRarity(), 0xFF_14_29_40, 0xFF_F1_0A_92, 0xFF_29_80_6B, 0xFF_80_80_80, null, 0xFF_FF_FF_FF, false, true).setInfo("Twilight Forest", "Twilight Forest", "twilightforest"),

                ShaderRegistry.registerShader("Underbrick", "0", TwilightForestMod.getRarity(), 0xFF_85_68_45, 0xFF_76_7F_76, 0xFF_61_4D_33, 0xFF_80_80_80, null, 0xFF_FF_FF_FF, false, true).setInfo("Twilight Forest", "Twilight Forest", "twilightforest"),
                ShaderRegistry.registerShader("Knight Phantom", "0", TwilightForestMod.getRarity(), 0xFF_40_6D_05, 0xFF_36_35_34, 0xFF_7A_5C_49, 0xFF_80_80_80, null, 0xFF_FF_FF_FF, false, true).setInfo("Twilight Forest", "Twilight Forest", "twilightforest"),
                ShaderRegistry.registerShader("Towerwood", "0", TwilightForestMod.getRarity(), 0xFF_83_5A_35, 0xFF_F5_DA_93, 0xFF_A6_65_3A, 0xFF_80_80_80, null, 0xFF_FF_FF_FF, false, true).setInfo("Twilight Forest", "Twilight Forest", "twilightforest"),
                ShaderRegistry.registerShader("Ur-Ghast", "0", TwilightForestMod.getRarity(), 0xFF_F9_F9_F9, 0xFF_9A_37_37, 0xFF_56_56_56, 0xFF_80_80_80, null, 0xFF_FF_FF_FF, false, true).setInfo("Twilight Forest", "Twilight Forest", "twilightforest"),

                ShaderRegistry.registerShader("Alpha Yeti", "0", TwilightForestMod.getRarity(), 0xFF_FC_FC_FC, 0xFF_4A_80_CE, 0xFF_25_3F_66, 0xFF_80_80_80, null, 0xFF_FF_FF_FF, false, true).setInfo("Twilight Forest", "Twilight Forest", "twilightforest"),
                //ShaderRegistry.registerShader("Auroralized", "0", TwilightForestMod.getRarity(), 0xFF_00_AA_00, 0xFF_28_25_3F, 0xFF_4C_64_5B, 0xFF_80_80_80, null, 0xFF_FF_FF_FF, false, true).setInfo("Twilight Forest", "Twilight Forest", "twilightforest"),
                ShaderRegistry.registerShader("Snow Queen", "0", TwilightForestMod.getRarity(), 0xFF_DC_FB_FF, 0xFF_C3_9C_00, 0xFF_03_05_89, 0xFF_80_80_80, null, 0xFF_FF_FF_FF, false, true).setInfo("Twilight Forest", "Twilight Forest", "twilightforest"),

                ShaderRegistry.registerShader("Knightly", "0", TwilightForestMod.getRarity(), 0xFF_E7_FC_CD, 0xFF_4D_4C_4B, 0xFF_80_8C_72, 0xFF_FF_FF_FF, null, 0xFF_FF_FF_FF, false, true).setInfo("Twilight Forest", "Twilight Forest", "twilightforest"),
                ShaderRegistry.registerShader("Fiery", "0", TwilightForestMod.getRarity(), 0xFF_19_13_13, 0xFF_FD_D4_5D, 0xFF_77_35_11, 0xFF_FF_FF_FF, null, 0xFF_FF_FF_FF, false, true).setInfo("Twilight Forest", "Twilight Forest", "twilightforest"),

                ShaderRegistry.registerShader("Final Castle", "0", TwilightForestMod.getRarity(), 0xFF_ECEAE6, 0xFF_00_FF_FF, 0xFF_C8_BB_BC, 0xFF_FF_FF_FF, null, 0xFF_FF_FF_FF, false, true).setInfo("Twilight Forest", "Final Castle", "finalcastle")
        );
    }
}
