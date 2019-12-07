package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.TwilightForestMod;
import twilightforest.enums.StructureVariant;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.item.TFItems;

public class BlockTFMiniatureStructure extends Block implements ModelRegisterCallback {

    public static final IProperty<StructureVariant> VARIANT = PropertyEnum.create("variant", StructureVariant.class);

    public BlockTFMiniatureStructure() {
        super(Material.BARRIER);
        this.setDefaultState(blockState.getBaseState().withProperty(VARIANT, StructureVariant.TWILIGHT_PORTAL));
    }

    @Override
    public boolean isOpaqueCube(BlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(BlockState state)
    {
        return false;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, BlockState state, BlockPos pos, Direction face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, VARIANT);
    }

    @Override
    public int getMetaFromState(BlockState state) {
        return state.getValue(VARIANT).ordinal();
    }

    @Override
    @Deprecated
    public BlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(VARIANT, StructureVariant.values()[meta]);
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        for (StructureVariant variation : StructureVariant.values() ) {
            list.add(new ItemStack(this, 1, variation.ordinal()));
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void registerModel() {
        for (StructureVariant variation : StructureVariant.values()) {
            ModelLoader.setCustomModelResourceLocation( TFItems.miniature_structure, variation.ordinal(), new ModelResourceLocation(TwilightForestMod.ID + ":miniature_structure", "inventory_"+variation.getName()));
        }
    }
}
