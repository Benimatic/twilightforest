package twilightforest.block;

import net.minecraft.block.BlockBreakable;
import net.minecraft.block.BreakableBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.item.TFItems;

import java.util.Random;

public class BlockTFWispyCloud extends BreakableBlock {

	protected BlockTFWispyCloud() {
		super(new Material(MaterialColor.SNOW), false);
		this.setSoundType(SoundType.CLOTH);
		this.setCreativeTab(TFItems.creativeTab);
		this.setHardness(0.3F);
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		return true;
	}

	@Override
	public int quantityDropped(Random random) {
		return 0;
	}
}
