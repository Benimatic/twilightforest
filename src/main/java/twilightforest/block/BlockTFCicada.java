package twilightforest.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import twilightforest.tileentity.TileEntityTFCicada;

public class BlockTFCicada extends BlockTFCritter {

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityTFCicada();
	}
}
