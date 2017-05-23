package twilightforest.item;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import twilightforest.block.BlockTFSapling;
import twilightforest.block.TFBlocks;
import twilightforest.block.enums.SaplingVariant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Mod.EventBusSubscriber(Side.CLIENT)
@SideOnly(Side.CLIENT)
public class TFRegisterItemRenderingEvent {
    @SubscribeEvent
    public static void onModelRegistryReady(ModelRegistryEvent event) {
        registerFromMetaSubTypes(Item.getItemFromBlock(TFBlocks.sapling), BlockTFSapling.TF_TYPE);
    }

    private static void registerFromMetaSubTypes(Item rootItem, PropertyHelper... properties) {
        ArrayList<PropertyHelper> propertyHelpers = Lists.newArrayList(properties);
        propertyHelpers.sort(Comparator.comparing(PropertyHelper::getName));

        List<ItemStack> subItems = Lists.newArrayList();
        rootItem.getSubItems(rootItem, TFItems.creativeTab, subItems);
        for (ItemStack subItem : subItems) {
            Item item = subItem.getItem();
            if(!(item instanceof ItemBlock)) {
                throw new RuntimeException("Wrong item rendering registration method used - Item is not a block");
            }
            ItemBlock itemBlock = (ItemBlock)item;
            IBlockState stateFromMeta = itemBlock.block.getStateFromMeta(subItem.getItemDamage());
            boolean first = true;
            StringBuilder sb = new StringBuilder();
            sb.append("inventory,");
            for (PropertyHelper property : properties) {
                Comparable value = stateFromMeta.getValue(property);
                if (!first) {
                    sb.append(',');
                }
                sb.append(property.getName().toLowerCase());
                sb.append('=');
                sb.append(value.toString().toLowerCase());
                first = false;
            }
            ModelLoader.setCustomModelResourceLocation(item, subItem.getItemDamage(), new ModelResourceLocation(item.getRegistryName(), sb.toString()));
        }




    }
}
