package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import twilightforest.tileentity.*;

import javax.annotation.Nullable;

public class BlockTFAntibuilder extends Block {

	public BlockTFAntibuilder() {
		super(Properties.create(Material.WOOD, MaterialColor.SAND).hardnessAndResistance(10.0F, 35.0F).sound(SoundType.WOOD).lightValue(10).noDrops());
		//this.setCreativeTab(TFItems.creativeTab); TODO 1.14
	}

	@Override
	public int tickRate(IWorldReader world) {
		return 15;
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new TileEntityTFAntibuilder();
	}

	//TODO: Move to client
//	@Override
//	@OnlyIn(Dist.CLIENT)
//	public BlockRenderLayer getRenderLayer() {
//		return BlockRenderLayer.CUTOUT;
//	}
}
