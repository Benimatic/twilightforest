package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.stats.StatList;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.TwilightForestMod;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.item.TFItems;

public class BlockTFUncraftingTable extends Block implements ModelRegisterCallback {

	protected BlockTFUncraftingTable() {
		super(Material.WOOD);
		this.setHardness(2.5F);
		this.setSoundType(SoundType.WOOD);
		this.setCreativeTab(TFItems.creativeTab);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, BlockState state, PlayerEntity player, Hand hand, Direction side, float hitX, float hitY, float hitZ) {
		if (world.isRemote) return true;

		player.openGui(TwilightForestMod.instance, 1, world, pos.getX(), pos.getY(), pos.getZ());
		player.addStat(StatList.CRAFTING_TABLE_INTERACTION);
		return true;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void registerModel() {
		ModelResourceLocation mrl = new ModelResourceLocation(this.getRegistryName(), Loader.isModLoaded("ctm") ? "ctm" : "normal");
		ModelLoader.setCustomStateMapper(this, new SingleStateMapper(mrl));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, mrl);
	}
}
