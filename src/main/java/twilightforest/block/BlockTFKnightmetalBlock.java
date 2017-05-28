package twilightforest.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import twilightforest.item.TFItems;

public class BlockTFKnightmetalBlock extends Block {

	private static final AxisAlignedBB AABB = new AxisAlignedBB(1/16F, 1/16F, 1/16F, 15/16F, 15/16F, 15/16F);
	private static final float BLOCK_DAMAGE = 4;

	public BlockTFKnightmetalBlock() {
		super(Material.IRON);
		this.setHardness(5.0F);
		this.setResistance(41.0F);
		this.setSoundType(SoundType.METAL);
		this.setCreativeTab(TFItems.creativeTab);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return AABB;
	}

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
    {
    	entity.attackEntityFrom(DamageSource.CACTUS, BLOCK_DAMAGE);
    }

    @Override
	public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess par1IBlockAccess, BlockPos pos, EnumFacing side)
    {
        return true;
    }
}
