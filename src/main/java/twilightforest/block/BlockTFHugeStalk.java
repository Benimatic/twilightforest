package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import twilightforest.item.TFItems;

public class BlockTFHugeStalk extends Block {

	protected BlockTFHugeStalk() {
		super(Material.WOOD);
		
		this.setHardness(1.25F);
		this.setResistance(7.0F);
		
		this.setSoundType(SoundType.PLANT);
		this.setCreativeTab(TFItems.creativeTab);
	}
	
	@Override
	public boolean canSustainLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
		return true;
	}
	
	@Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        byte radius = 4;
        int rad1 = radius + 1;

        if (world.isAreaLoaded(pos, rad1)) {
            for (int dx = -radius; dx <= radius; ++dx) {
                for (int dy = -radius; dy <= radius; ++dy) {
                    for (int dz = -radius; dz <= radius; ++dz) {
						BlockPos pos_ = pos.add(dx, dy, dz);
                        IBlockState state_ = world.getBlockState(pos_);
						Block block = state.getBlock();
                        if (block.isLeaves(state_, world, pos_)) {
                            block.beginLeavesDecay(state_, world, pos_);
                        }
                    }
                }
            }
        }
    }
	
}
