package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.BlockGetter;
import twilightforest.tileentity.TFTileEntities;

import javax.annotation.Nullable;

import net.minecraft.world.level.block.state.BlockBehaviour;

public class FireflyBlock extends CritterBlock {

	protected FireflyBlock(BlockBehaviour.Properties props) {
		super(props);
	}

	@Nullable
	@Override
	public BlockEntity createTileEntity(BlockState state, BlockGetter world) {
		return TFTileEntities.FIREFLY.get().create();
	}

	@Override
	public ItemStack getSquishResult() {
		return new ItemStack(Items.GLOWSTONE_DUST);
	}
}
