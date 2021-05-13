package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import twilightforest.tileentity.TFTileEntities;

import javax.annotation.Nullable;

public class BlockTFCicada extends BlockTFCritter {

	protected BlockTFCicada(Block.Properties props) {
		super(props);
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return TFTileEntities.CICADA.get().create();
	}

	@Override
	public ItemStack getSquishResult() {
		return new ItemStack(Items.GRAY_DYE, 1);
	}

	//TODO: Immersive Engineering is unavailable
	/*@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);

		if (TFCompat.IMMERSIVEENGINEERING.isActivated()) {
			tooltip.add(TextFormatting.ITALIC.toString() + TwilightForestMod.getRarity().color.toString() + I18n.translateToLocalFormatted("tile.twilightforest.Cicada.desc"));
		}
	}*/
}
