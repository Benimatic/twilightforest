package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.enums.CastlePillarVariant;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.client.ModelUtils;
import twilightforest.item.TFItems;

import static net.minecraft.block.BlockLog.LOG_AXIS;

public class BlockTFCastlePillar extends Block implements ModelRegisterCallback {

    public static final IProperty<CastlePillarVariant> VARIANT = PropertyEnum.create("variant", CastlePillarVariant.class);

    BlockTFCastlePillar() {
        super(Material.ROCK, MaterialColor.QUARTZ);
        this.setHardness(100F);
        this.setResistance(35F);
        this.setSoundType(SoundType.STONE);
        this.setCreativeTab(TFItems.creativeTab);
        this.setDefaultState(blockState.getBaseState().withProperty(VARIANT, CastlePillarVariant.ENCASED).withProperty(LOG_AXIS, BlockLog.EnumAxis.Y));
    }

    @Override
    public BlockState getStateForPlacement(World world, BlockPos pos, Direction facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, Hand hand) {
        ItemStack stack = placer.getHeldItem(hand);
        return this.getDefaultState()
                .withProperty(LOG_AXIS, (stack.getMetadata() & 1) == 1 ? BlockLog.EnumAxis.NONE : BlockLog.EnumAxis.fromFacingAxis(facing.getAxis()))
                .withProperty(VARIANT, CastlePillarVariant.values()[stack.getMetadata() / 2 & 1]);
    }

    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, LOG_AXIS, VARIANT);
    }

    @Override
    public int getMetaFromState(BlockState state) {
        return state.getValue(VARIANT).ordinal() << 2 | state.getValue(LOG_AXIS).ordinal();
    }

    @Override
    @Deprecated
    public BlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(VARIANT, CastlePillarVariant.values()[(meta & 0b1100) >> 2 & 1]).withProperty(LOG_AXIS, BlockLog.EnumAxis.values()[meta & 0b11]);
    }

    @Override
    public void getSubBlocks(CreativeTabs creativeTab, NonNullList<ItemStack> list) {
        for (CastlePillarVariant variant : CastlePillarVariant.values()) {
            list.add(new ItemStack(this, 1, variant.ordinal()*2));
            list.add(new ItemStack(this, 1, (variant.ordinal()*2)+1));
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void registerModel() {
        CastlePillarVariant[] variantsList = CastlePillarVariant.values();

        for (int i = 0; i < variantsList.length; i++) {
            ModelUtils.registerToState(this, i*2, this.getDefaultState()
                    .withProperty(LOG_AXIS, BlockLog.EnumAxis.Y)
                    .withProperty(VARIANT, variantsList[i]));

            ModelUtils.registerToState(this, (i*2)+1, this.getDefaultState()
                    .withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE)
                    .withProperty(VARIANT, variantsList[i]));
        }
    }

    @Override
    public int damageDropped(BlockState state) {
        return state.getValue(VARIANT).ordinal() << 1 | (state.getValue(LOG_AXIS) == BlockLog.EnumAxis.NONE ? 1 : 0);
    }

    @Override
    protected ItemStack getSilkTouchDrop(BlockState state) {
        return new ItemStack(this, 1, damageDropped(state));
    }
}