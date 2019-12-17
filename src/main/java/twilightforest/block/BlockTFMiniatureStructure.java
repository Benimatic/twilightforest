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

public class BlockTFMiniatureStructure extends Block {

	//TODO 1.14: Flatten
    public static final IProperty<StructureVariant> VARIANT = PropertyEnum.create("variant", StructureVariant.class);

    public BlockTFMiniatureStructure() {
        super(Material.BARRIER);
        this.setDefaultState(blockState.getBaseState().with(VARIANT, StructureVariant.TWILIGHT_PORTAL));
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
        return getDefaultState().with(VARIANT, StructureVariant.values()[meta]);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}
