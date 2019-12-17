package twilightforest.block;

import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Items;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.TwilightForestMod;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.compat.TFCompat;
import twilightforest.tileentity.critters.TileEntityTFCicadaTicking;

import javax.annotation.Nullable;
import java.util.List;

public class BlockTFCicada extends BlockTFCritter {

	@Override
	public TileEntity createTileEntity(World world, BlockState state) {
		return TwilightForestMod.proxy.getNewCicadaTE();
	}

	@Override
	public ItemStack getSquishResult() {
		return new ItemStack(Items.GRAY_DYE, 1);
	}

	//Atomic: Forge would like to get rid of registerTESRItemStack, but there's no alternative yet (as at 1.11)
	@SuppressWarnings("deprecation")
	@OnlyIn(Dist.CLIENT)
	@Override
	public void registerModel() {
		ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(BlockDirectional.FACING).build());
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
		ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(this), 0, TileEntityTFCicadaTicking.class);
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
