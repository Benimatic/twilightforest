package twilightforest.block;

import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.TwilightForestMod;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.tileentity.critters.TileEntityTFFireflyTicking;

import javax.annotation.Nullable;

public class BlockTFFirefly extends BlockTFCritter {

	protected BlockTFFirefly() {
		super(Properties.create(Material.MISCELLANEOUS).lightValue(15));
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return TwilightForestMod.proxy.getNewFireflyTE();
	}

	@Override
	public ItemStack getSquishResult() {
		return new ItemStack(Items.GLOWSTONE_DUST);
	}

	//Atomic: Forge would like to get rid of registerTESRItemStack, but there's no alternative yet (as at 1.11)
	@SuppressWarnings("deprecation")
	@OnlyIn(Dist.CLIENT)
	@Override
	public void registerModel() {
		ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(BlockDirectional.FACING).build());
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
		ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(this), 0, TileEntityTFFireflyTicking.class);
	}
}
