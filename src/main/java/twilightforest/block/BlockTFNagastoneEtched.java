package twilightforest.block;

import net.minecraft.block.BlockDirectional;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.client.ModelUtils;
import twilightforest.item.TFItems;

public class BlockTFNagastoneEtched extends DirectionalBlock {
    protected BlockTFNagastoneEtched() {
        super(Properties.create(Material.ROCK).hardnessAndResistance(1.5F, 10.0F).sound(SoundType.STONE));
        //this.setCreativeTab(TFItems.creativeTab); TODO 1.14
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.DOWN));
    }

    @Override
    public BlockState getStateForPlacement(World worldIn, BlockPos pos, Direction facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().with(FACING, placer.isSneaking() ? facing.getOpposite() : facing);
    }

    @Override
    public BlockState getStateFromMeta(int meta) {
        return this.getDefaultState().with(FACING, Direction.values()[meta % Direction.values().length]);
    }

    @Override
    public int getMetaFromState(BlockState state) {
        return state.getValue(FACING).ordinal();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public BlockState withRotation(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState withMirror(BlockState state, Mirror mirrorIn) {
        return state.with(FACING, mirrorIn.mirror(state.getValue(FACING)));
    }

    @Override
    protected ItemStack getSilkTouchDrop(BlockState state) {
        return new ItemStack(Item.getItemFromBlock(this));
    }
}
