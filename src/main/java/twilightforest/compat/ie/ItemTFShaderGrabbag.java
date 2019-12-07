package twilightforest.compat.ie;

import blusunrize.immersiveengineering.api.shader.ShaderRegistry;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.TwilightForestMod;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.client.model.item.ShaderGrabbagStackRenderer;
import twilightforest.item.TFItems;

import javax.annotation.Nullable;
import java.util.List;

public class ItemTFShaderGrabbag extends Item implements ModelRegisterCallback {

    public ItemTFShaderGrabbag() {
        this.setHasSubtypes(true);
        this.setCreativeTab(TFItems.creativeTab);
    }

    private static final String TAG_SHADER = "shader_rarity";
    public static final ItemTFShaderGrabbag shader_bag = new ItemTFShaderGrabbag();

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (tab == TFItems.creativeTab) {
            for(int i = ShaderRegistry.sortedRarityMap.size() - 1; i >= 0; i--) {
                ItemStack stack = new ItemStack(this, 1, 0);

                CompoundNBT compound = new CompoundNBT();
                compound.setString(TAG_SHADER, ShaderRegistry.sortedRarityMap.get(i).toString());

                stack.setTagCompound(compound);

                items.add(stack);
            }
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        CompoundNBT compound = stack.getTagCompound();

        if (compound == null) {
            stack.setTagCompound(new CompoundNBT());
            compound = stack.getTagCompound();
        }

        String rarityString = compound.getString(TAG_SHADER);

        for(EnumRarity rarity : EnumRarity.values())
            if(rarity.toString().equalsIgnoreCase(rarityString))
                return rarity;

        return EnumRarity.COMMON;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        String name = this.getRarity(stack).rarityName;

        return I18n.translateToLocalFormatted(this.getTranslationKey(stack), name);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);

        if (!worldIn.isRemote) {
            EnumRarity rarity = stack.getRarity();

            if (rarity == TwilightForestMod.getRarity()) {
                List<ShaderRegistry.ShaderRegistryEntry> list = IEShaderRegister.getAllNonbossShaders();

                return randomShader(list.get(playerIn.getRNG().nextInt(list.size())).name, stack, playerIn);
            } else if (ShaderRegistry.totalWeight.containsKey(rarity)) {
                return randomShader(ShaderRegistry.getRandomShader(playerIn.getName(), playerIn.getRNG(), rarity, true), stack, playerIn);
            }
        }

        return new ActionResult<>(EnumActionResult.PASS, stack);
    }

    private static ActionResult<ItemStack> randomShader(@Nullable String shader, ItemStack stack, PlayerEntity playerIn) {
        if ( shader == null || shader.isEmpty() )
            return new ActionResult<>(EnumActionResult.FAIL, stack);

        ItemStack shaderItem = new ItemStack(ItemTFShader.shader);
        CompoundNBT compound = shaderItem.getTagCompound();

        if (compound == null) {
            shaderItem.setTagCompound(new CompoundNBT());
            compound = shaderItem.getTagCompound();
        }

        compound.setString(ItemTFShader.TAG_SHADER, shader);

        stack.shrink(1);

        if (stack.getCount() <= 0)
            return new ActionResult<>(EnumActionResult.SUCCESS, shaderItem);

        if (!playerIn.inventory.addItemStackToInventory(shaderItem))
            playerIn.dropItem(shaderItem, false, true);

        return new ActionResult<>(EnumActionResult.PASS, stack);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void registerModel() {
        ModelResourceLocation mrl = new ModelResourceLocation(TwilightForestMod.ID + ":grabbag_tesr", "inventory");
        ModelLoader.setCustomModelResourceLocation(this, 0, mrl);
        ClientRegistry.bindTileEntitySpecialRenderer(ShaderGrabbagStackRenderer.DummyTile.class, new ShaderGrabbagStackRenderer(mrl));
        ForgeHooksClient.registerTESRItemStack(this, 0, ShaderGrabbagStackRenderer.DummyTile.class);
    }
}
