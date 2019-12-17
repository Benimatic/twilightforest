package twilightforest.block;

import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Items;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.TwilightForestMod;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.tileentity.critters.TileEntityTFMoonwormTicking;

import java.util.Random;

public class BlockTFMoonworm extends BlockTFCritter {

	protected BlockTFMoonworm() {
		this.setLightLevel(0.9375F);
	}

	@Override
	public float getWidth() {
		return 0.25F;
	}

	@Override
	public TileEntity createTileEntity(World world, BlockState state) {
		return TwilightForestMod.proxy.getNewMoonwormTE();
	}

	@Override
	protected boolean checkAndDrop(World world, BlockPos pos, BlockState state) {
		Direction facing = state.getValue(BlockDirectional.FACING);
		if (!canPlaceAt(world, pos.offset(facing.getOpposite()), facing)) {
			world.destroyBlock(pos, false);
			return false;
		}
		return true;
	}

	@Override
	public int quantityDropped(BlockState state, int fortune, Random random) {
		return 0;
	}

	@Override
	public ItemStack getSquishResult() {
		return new ItemStack(Items.DYE, 1, DyeColor.LIME.getDyeDamage());
	}

	//Atomic: Forge would like to get rid of registerTESRItemStack, but there's no alternative yet (as at 1.11)
	@SuppressWarnings("deprecation")
	@OnlyIn(Dist.CLIENT)
	@Override
	public void registerModel() {
		ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(BlockDirectional.FACING).build());
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
		ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(this), 0, TileEntityTFMoonwormTicking.class);
	}
}
