package twilightforest.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCompressed;
import net.minecraft.block.material.MapColor;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;
import twilightforest.item.TFItems;

public class BlockTFKnightmetalBlock extends Block {

	private static final float BLOCK_DAMAGE = 4;

	public BlockTFKnightmetalBlock() {
		super(MapColor.ironColor);
		this.setHardness(5.0F);
		this.setResistance(41.0F);
		this.setStepSound(Block.soundTypeMetal);
		this.setBlockTextureName(TwilightForestMod.ID + ":knightmetal_block");
		this.setCreativeTab(TFItems.creativeTab);

	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, World world, BlockPos pos)
	{
		float f = 1F / 16F;
		return new AxisAlignedBB(x + f, y + f, z + f, x + 1 - f, y + 1 - f, z + 1 - f);

	}

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
    {
    	entity.attackEntityFrom(DamageSource.cactus, BLOCK_DAMAGE);
    }

    @Override
	public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
	public int getRenderType()
    {
    	return TwilightForestMod.proxy.getKnightmetalBlockRenderID();
    }

    @Override
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess par1IBlockAccess, BlockPos pos, EnumFacing side)
    {
        return true;
    }
}
