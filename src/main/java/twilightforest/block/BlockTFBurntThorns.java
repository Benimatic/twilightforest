package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import twilightforest.item.TFItems;

public class BlockTFBurntThorns extends BlockTFThorns {

	protected BlockTFBurntThorns() {
		this.setHardness(0.01F);
		this.setResistance(0.0F);
		this.setSoundType(SoundType.SAND);
		this.setCreativeTab(TFItems.creativeTab);
		
		this.setNames(new String[] {"burnt"});

	}

	@Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
    {
    	// dissolve
    	if (!world.isRemote && entity instanceof EntityLivingBase) {
	    	int metadata = world.getBlockMetadata(x, y, z);
	    	world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(this) + (metadata << 12));
	    	world.setBlockToAir(x, y, z);
    	}
    }

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean harvest) {
		world.setBlockToAir(x, y, z);
		return true;
	}

    @Override
    public boolean canSustainLeaves(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return false;
    }

	@Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
    	;
    }
}
