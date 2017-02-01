package twilightforest.block;

import java.util.List;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import twilightforest.block.enums.HugeLilypadPiece;
import twilightforest.item.TFItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class BlockTFHugeLilyPad extends BlockBush {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyEnum<HugeLilypadPiece> PIECE = PropertyEnum.create("piece", HugeLilypadPiece.class);
	private static final AxisAlignedBB AABB = new AxisAlignedBB(0, 0, 0, 1, 0.015625, 1);

	private boolean isSelfDestructing = false;

	protected BlockTFHugeLilyPad() {
		super(Material.PLANTS);
		this.setSoundType(SoundType.PLANT);
		this.setCreativeTab(TFItems.creativeTab);
		this.setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(PIECE, HugeLilypadPiece.NW));
	}

	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, PIECE);
	}

	// todo 1.9
	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState();
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return AABB;
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos)
    {
        return world.getBlockState(pos.down()).getBlock() == Blocks.WATER;
    }

    @Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
    	if (!this.isSelfDestructing  && !canBlockStay(world, pos, state)) {
    		this.setGiantBlockToAir(world, pos);
    	}
    }
    
    private void setGiantBlockToAir(World world, BlockPos pos) {
    	// this flag is maybe not totally perfect
    	this.isSelfDestructing = true;
    	
    	int bx = pos.getX() & ~0b1;
    	int bz = pos.getZ() & ~0b1;

    	// this is the best loop over 3 items that I've ever programmed!
    	for (int dx = 0; dx < 2; dx++) {
    		for (int dz = 0; dz < 2; dz++) {
    			if (!(pos.getX() == bx + dx && pos.getZ() == bz + dz)) {
					BlockPos dPos = new BlockPos(bx + dx, pos.getY(), bz + dz);
					IBlockState state = world.getBlockState(dPos);
    				if (state.getBlock() == this) {
    					world.destroyBlock(dPos, false);
    				}
    			}
    		}
    	}

    	this.isSelfDestructing = false;
	}

	@Override
    public boolean canBlockStay(World world, BlockPos pos, IBlockState state)  {
    	int bx = pos.getX() & ~0b1;
    	int bz = pos.getZ() & ~0b1;

    	for (int dx = 0; dx < 2; dx++) {
    		for (int dz = 0; dz < 2; dz++) {
				BlockPos dPos = new BlockPos(bx + dx, pos.getY(), bz + dz);
				IBlockState dState = world.getBlockState(dPos);
				IBlockState dStateBelow = world.getBlockState(dPos.down());

				if (dState.getBlock() != this
						|| !(dStateBelow.getBlock() == Blocks.WATER || dStateBelow.getBlock() == Blocks.FLOWING_WATER)
						|| dStateBelow.getValue(BlockLiquid.LEVEL) != 0) {
					return false;
				}
    		}
    	}

    	return true;
    }

	// [VanillaCopy] of super without dropping
    @Override
	protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		if (!this.canBlockStay(worldIn, pos, state))
		{
			// this.dropBlockAsItem(worldIn, pos, state, 0); TF - nodrop
			worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
		}
	}

	@Override
    public EnumPushReaction getMobilityFlag(IBlockState state)
    {
        return EnumPushReaction.BLOCK;
    }
}
