package twilightforest.block;

import net.minecraft.block.BlockDirectional;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import twilightforest.TwilightForestMod;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.tileentity.critters.TileEntityTFMoonwormTicking;

import java.util.Random;

public class BlockTFMoonworm extends BlockTFCritter implements ModelRegisterCallback {

	protected BlockTFMoonworm() {
		this.setLightLevel(0.9375F);
	}

	@Override
	public float getWidth() {
		return 0.25F;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return TwilightForestMod.proxy.getNewMoonwormTE();
	}

	@Override
	protected boolean checkAndDrop(World world, BlockPos pos, IBlockState state) {
		EnumFacing facing = state.getValue(BlockDirectional.FACING);
		if (!canPlaceAt(world, pos.offset(facing.getOpposite()), facing)) {
			world.destroyBlock(pos, false);
			return false;
		}
		return true;
	}

	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
		return 0;
	}

	@Override
	public ItemStack getSquishResult() {
		return new ItemStack(Items.DYE, 1, EnumDyeColor.LIME.getDyeDamage());
	}

	//Atomic: Forge would like to get rid of registerTESRItemStack, but there's no alternative yet (as at 1.11)
	@SuppressWarnings("deprecation")
	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel() {
		ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(BlockDirectional.FACING).build());
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
		ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(this), 0, TileEntityTFMoonwormTicking.class);
	}
}
