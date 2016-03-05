package twilightforest.block;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import twilightforest.tileentity.TileEntityTFCicada;

public class BlockTFCicada extends BlockTFCritter {

	public static int sprCicada = 5;
	
	
	protected BlockTFCicada() {
		super();
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new TileEntityTFCicada();
	}
}
