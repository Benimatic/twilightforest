package twilightforest.block;

import net.minecraft.block.*;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.FurnaceTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.TwilightForestMod;
import twilightforest.item.TFItems;
import twilightforest.tileentity.TileEntityTFCinderFurnace;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockTFCinderFurnace extends Block {

	private static boolean keepInventory;
	private static final BooleanProperty LIT = BooleanProperty.create("lit");
	private static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

	BlockTFCinderFurnace(boolean isLit) {
		super(Material.WOOD);

		this.LIT = isLit;

		this.setHardness(7.0F);

		this.setLightLevel(isLit ? 1F : 0);

		if (!isLit) {
			this.setCreativeTab(TFItems.creativeTab);
		}

		this.setDefaultState(stateContainer.getBaseState().with(FACING, Direction.NORTH).with(LIT, false));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(LIT, FACING);
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new TileEntityTFCinderFurnace();
	}

	@Override
	@Deprecated
	public boolean eventReceived(BlockState state, World worldIn, BlockPos pos, int id, int param) {
		super.eventReceived(state, worldIn, pos, id, param);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		return tileentity != null && tileentity.receiveClientEvent(id, param);
	}

	@Override
	public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (!world.isRemote && world.getTileEntity(pos) instanceof TileEntityTFCinderFurnace) {
			player.openGui(TwilightForestMod.instance, TwilightForestMod.GUI_ID_FURNACE, world, pos.getX(), pos.getY(), pos.getZ());
		}

		return true;
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		if (stack.hasDisplayName()) {
			((FurnaceTileEntity) world.getTileEntity(pos)).setCustomName(stack.getDisplayName());
		}
	}

	public static void setState(boolean active, World worldIn, BlockPos pos) {
		BlockState iblockstate = worldIn.getBlockState(pos);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		keepInventory = true;

		if (active) {
			worldIn.setBlockState(pos, TFBlocks.cinder_furnace_lit.getDefaultState().with(BlockFurnace.FACING, iblockstate.getValue(BlockFurnace.FACING)), 3);
		} else {
			worldIn.setBlockState(pos, TFBlocks.cinder_furnace.getDefaultState().with(BlockFurnace.FACING, iblockstate.getValue(BlockFurnace.FACING)), 3);
		}

		keepInventory = false;

		if (tileentity != null) {
			tileentity.validate();
			worldIn.setTileEntity(pos, tileentity);
		}
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void animateTick(BlockState state, World world, BlockPos pos, Random random) {
		if (state.get(LIT)) {
			Blocks.FURNACE.animateTick(state, world, pos, random);
		}
	}

	@Override
	public void breakBlock(World world, BlockPos pos, BlockState state) {
		if (!keepInventory) {
			TileEntity tileentity = world.getTileEntity(pos);

			if (tileentity instanceof TileEntityFurnace) {
				InventoryHelper.dropInventoryItems(world, pos, (TileEntityFurnace) tileentity);
				world.updateComparatorOutputLevel(pos, this);
			}
		}

		super.breakBlock(world, pos, state);
	}

//	@Override
//	public Item getItemDropped(BlockState state, Random rand, int fortune) {
//		return Item.getItemFromBlock(TFBlocks.cinder_furnace);
//	}
//
//	@Override
//	public ItemStack getPickBlock(BlockState state, RayTraceResult target, World world, BlockPos pos, PlayerEntity player) {
//		return new ItemStack(Item.getItemFromBlock(TFBlocks.cinder_furnace));
//	}
}
