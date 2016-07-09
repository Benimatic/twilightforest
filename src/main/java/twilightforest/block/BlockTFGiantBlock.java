package twilightforest.block;

import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public abstract class BlockTFGiantBlock extends Block {

	private IBlockState baseState;
	private boolean isSelfDestructing;

	public BlockTFGiantBlock(IBlockState state) {
		super(state.getMaterial());
		this.setSoundType(state.getBlock().getSoundType());
		this.baseState = state;
	}

	@Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
		pos = roundCoords(pos);
    	
    	for (int dx = 0; dx < 4; dx++) {
    		for (int dy = 0; dy < 4; dy++) {
    			for (int dz = 0; dz < 4; dz++) {
					IBlockState state = world.getBlockState(pos.add(dx, dy, dz));
					if (!state.getBlock().isReplaceable(world, pos))
						return false;
    			}
    		}
    	}

        return super.canPlaceBlockAt(world, pos);
    }
    
	@Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, World world, BlockPos pos) {
    	int bx = (x >> 2) << 2;
    	int by = (y >> 2) << 2;
    	int bz = (z >> 2) << 2;

        return new AxisAlignedBB((double)bx + this.minX, (double)by + this.minY, (double)bz + this.minZ, (double)bx + this.maxX * 4F, (double)by + this.maxY * 4F, (double)bz + this.maxZ * 4F);
    }
    
    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase player, ItemStack itemStack) {
    	if (!world.isRemote) {
			pos = roundCoords(pos);

    		for (int dx = 0; dx < 4; dx++) {
    			for (int dy = 0; dy < 4; dy++) {
    				for (int dz = 0; dz < 4; dz++) {
    					world.setBlockState(pos.add(dx, dy, dz), getDefaultState(), 2);
    				}
    			}
    		}
    	}
    }

	@Override
    @SideOnly(Side.CLIENT)
	public boolean addDestroyEffects(World world, BlockPos pos, ParticleManager manager) {
		// todo can't we just play effect 2001 for every subblock?
    	int bx = (x >> 2) << 2;
    	int by = (y >> 2) << 2;
    	int bz = (z >> 2) << 2;
		
		Block blockThere = world.getBlock(x, y, z);
		int metaThere = world.getBlockMetadata(x, y, z);
		
        byte b0 = 16;

        for (int i1 = 0; i1 < b0; ++i1)
        {
            for (int j1 = 0; j1 < b0; ++j1)
            {
                for (int k1 = 0; k1 < b0; ++k1)
                {
                    double d0 = (double)bx + ((double)i1 + 0.5D) / 4F;
                    double d1 = (double)by + ((double)j1 + 0.5D) / 4F;
                    double d2 = (double)bz + ((double)k1 + 0.5D) / 4F;
                    effectRenderer.addEffect((new EntityDiggingFX(world, d0, d1, d2, d0 - (double)x - 0.5D, d1 - (double)y - 0.5D, d2 - (double)z - 0.5D, blockThere, metaThere)).applyColourMultiplier(x, y, z));
                }
            }
        }


		return true;
	}

	@Override
    public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		this.setGiantBlockToAir(world, pos);
    }

	@Override
    public void onBlockExploded(World world, BlockPos pos, Explosion explosion)
    {
        world.setBlockToAir(pos);
		this.setGiantBlockToAir(world, pos);
    }

	@Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
		super.breakBlock(world, pos, state);
    	if (!this.isSelfDestructing && !canBlockStay(world, pos)) {
    		this.setGiantBlockToAir(world, pos);
    	}
    }
    
    private void setGiantBlockToAir(World world, BlockPos pos) {
    	// this flag is maybe not totally perfect
    	this.isSelfDestructing = true;

		BlockPos bPos = roundCoords(pos);

    	for (int dx = 0; dx < 4; dx++) {
    		for (int dy = 0; dy < 4; dy++) {
    			for (int dz = 0; dz < 4; dz++) {
					BlockPos iterPos = bPos.add(dx, dy, dz);
    				if (!pos.equals(iterPos)) {
    					if (world.getBlockState(iterPos).getBlock() == this) {
    						world.setBlockToAir(iterPos);
    					}
    				}
    			}
    		}
    	}
    	
    	this.isSelfDestructing = false;
	}

    public boolean canBlockStay(World world, BlockPos pos)  {
    	pos = roundCoords(pos);

    	for (int dx = 0; dx < 4; dx++) {
    		for (int dy = 0; dy < 4; dy++) {
    			for (int dz = 0; dz < 4; dz++) {
					if (world.getBlockState(pos.add(dx, dy, dz)).getBlock() != this)
						return false;
    			}
    		}
    	}
    	
    	return true;
    }
    
	@Override
    public EnumPushReaction getMobilityFlag(IBlockState state)
    {
        return EnumPushReaction.BLOCK;
    }

	protected BlockPos roundCoords(BlockPos pos)
	{
		return new BlockPos(pos.getX() & ~0b11, pos.getY() & ~0b11, pos.getZ() & ~0b11);
	}

}
