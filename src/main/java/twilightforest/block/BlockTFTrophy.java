package twilightforest.block;

import net.minecraft.block.BlockSkull;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.crafting.IInfusionStabiliser;
import twilightforest.TFSounds;
import twilightforest.enums.BossVariant;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.item.TFItems;
import twilightforest.tileentity.TileEntityTFTrophy;

import java.util.List;
import java.util.Random;

@Optional.Interface(modid = "thaumcraft", iface = "thaumcraft.api.crafting.IInfusionStabiliser")
public class BlockTFTrophy extends BlockSkull implements ModelRegisterCallback, IInfusionStabiliser {

	private static final AxisAlignedBB HYDRA_Y_BB = new AxisAlignedBB(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
	private static final AxisAlignedBB HYDRA_EAST_BB = new AxisAlignedBB(0.0F, 0.25F, 0.25F, 0.5F, 0.75F, 0.75F);
	private static final AxisAlignedBB HYDRA_WEST_BB = new AxisAlignedBB(0.5F, 0.25F, 0.25F, 1.0F, 0.75F, 0.75F);
	private static final AxisAlignedBB HYDRA_SOUTH_BB = new AxisAlignedBB(0.25F, 0.25F, 0.0F, 0.75F, 0.75F, 0.5F);
	private static final AxisAlignedBB HYDRA_NORTH_BB = new AxisAlignedBB(0.25F, 0.25F, 0.5F, 0.75F, 0.75F, 1.0F);
	private static final AxisAlignedBB URGHAST_BB = new AxisAlignedBB(0.25F, 0.5F, 0.25F, 0.75F, 1F, 0.75F);

	public BlockTFTrophy() {
		setDefaultState(blockState.getBaseState().withProperty(BlockSkull.NODROP, false).withProperty(BlockSkull.FACING, EnumFacing.UP));
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess access, BlockPos pos) {
		TileEntity te = access.getTileEntity(pos);
		if (te instanceof TileEntityTFTrophy) {
			switch (BossVariant.getVariant(((TileEntityTFTrophy) te).getSkullType())) {
				case HYDRA:
					// hydra bounds TODO: actually use non-default bounds here
					switch (state.getValue(BlockSkull.FACING)) {
						case UP:
						default:
							return HYDRA_Y_BB;
						case NORTH:
							return HYDRA_NORTH_BB;
						case SOUTH:
							return HYDRA_SOUTH_BB;
						case WEST:
							return HYDRA_WEST_BB;
						case EAST:
							return HYDRA_EAST_BB;
					}
				case UR_GHAST:
					return URGHAST_BB;
				// TODO: also add case for Questing Ram?
			}
		}
		return super.getBoundingBox(state, access, pos);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntity te = worldIn.getTileEntity(pos);
		if (te instanceof TileEntityTFTrophy) {
			SoundEvent sound = null;
			float volume = 1.0F;
			switch (BossVariant.getVariant(((TileEntityTFTrophy) te).getSkullType())) {
				case NAGA:
					sound = TFSounds.NAGA_RATTLE;
					volume = 1.25F;
					break;
				case LICH:
					sound = SoundEvents.ENTITY_BLAZE_AMBIENT;
					volume = 0.35F;
					break;
				case HYDRA:
					sound = TFSounds.HYDRA_GROWL;
					break;
				case UR_GHAST:
					sound = SoundEvents.ENTITY_GHAST_AMBIENT;
					break;
				case SNOW_QUEEN:
					sound = TFSounds.ICE_AMBIENT;
					break;
				case KNIGHT_PHANTOM:
					sound = TFSounds.WRAITH;
					break;
				case MINOSHROOM:
					sound = SoundEvents.ENTITY_COW_AMBIENT;
					volume = 0.5F;
					break;
				case QUEST_RAM:
					sound = SoundEvents.ENTITY_SHEEP_AMBIENT;
					break;
			}
			if (sound != null)
				worldIn.playSound(playerIn, pos, sound, SoundCategory.BLOCKS, volume, 16.0F);
		}
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityTFTrophy();
	}

	@Override
	public ItemStack getItem(World world, BlockPos pos, IBlockState state) {
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileEntityTFTrophy) {
			return new ItemStack(TFItems.trophy, 1, ((TileEntityTFTrophy) te).getSkullType());
		}
		return ItemStack.EMPTY;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return TFItems.trophy;
	}

	// [VanillaCopy] of superclass, relevant edits indicated
	@Override
	public List<ItemStack> getDrops(IBlockAccess worldIn, BlockPos pos, IBlockState state, int fortune) {
		java.util.List<ItemStack> ret = new java.util.ArrayList<ItemStack>();
		{
			if (!((Boolean) state.getValue(NODROP)).booleanValue()) {
				TileEntity tileentity = worldIn.getTileEntity(pos);

				if (tileentity instanceof TileEntitySkull) {
					TileEntitySkull tileentityskull = (TileEntitySkull) tileentity;
					ItemStack itemstack = new ItemStack(TFItems.trophy, 1, tileentityskull.getSkullType()); // TF - use our item

                    /*if (tileentityskull.getSkullType() == 3 && tileentityskull.getPlayerProfile() != null)
					{
                        itemstack.setTagCompound(new NBTTagCompound());
                        NBTTagCompound nbttagcompound = new NBTTagCompound();
                        NBTUtil.writeGameProfile(nbttagcompound, tileentityskull.getPlayerProfile());
                        itemstack.getTagCompound().setTag("SkullOwner", nbttagcompound);
                    }*/// TF - don't set player skins

					ret.add(itemstack);
				}
			}
		}
		return ret;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel() {
		ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(NODROP).ignore(FACING).build());
	}

	@Override
	public boolean canStabaliseInfusion(World world, BlockPos blockPos) {
		return true;
	}
}
