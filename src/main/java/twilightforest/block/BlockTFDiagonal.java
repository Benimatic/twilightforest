package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.state.BooleanProperty;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import twilightforest.client.ModelRegisterCallback;

public class BlockTFDiagonal extends Block {

    public static final BooleanProperty IS_ROTATED = BooleanProperty.create("is_rotated");

    public BlockTFDiagonal(Material material) {
        super(material);
    }

    public BlockTFDiagonal(Material material, MaterialColor mapColor) {
        super(material, mapColor);
    }

    @Override
    public Block setSoundType(SoundType sound) {
        return super.setSoundType(sound);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, IS_ROTATED);
    }

    @Override
    public BlockState getStateFromMeta(int meta) {
        return this.getDefaultState().with(IS_ROTATED, meta != 0);
    }

    @Override
    public int getMetaFromState(BlockState state) {
        return state.getValue(IS_ROTATED) ? 1 : 0;
    }

    @Override
    public BlockState withRotation(BlockState state, Rotation rot) {
        return rot == Rotation.NONE || rot == Rotation.CLOCKWISE_180 ? state : state.with(IS_ROTATED, !state.getValue(IS_ROTATED));
    }

    @Override
    public BlockState withMirror(BlockState state, Mirror mirrorIn) {
        return mirrorIn == Mirror.NONE ? state : state.with(IS_ROTATED, !state.getValue(IS_ROTATED));
    }

    @Override
    public BlockState getStateForPlacement(World worldIn, BlockPos pos, Direction facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().with(IS_ROTATED, Direction.byHorizontalIndex(MathHelper.floor(placer.rotationYaw * 4.0F / 360.0F) & 1) == Direction.WEST);
    }
}
