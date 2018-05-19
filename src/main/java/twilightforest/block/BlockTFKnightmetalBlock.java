package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.client.ModelUtils;
import twilightforest.item.TFItems;

public class BlockTFKnightmetalBlock extends Block implements ModelRegisterCallback {

	private static final AxisAlignedBB AABB = new AxisAlignedBB(1 / 16F, 1 / 16F, 1 / 16F, 15 / 16F, 15 / 16F, 15 / 16F);
	private static final float BLOCK_DAMAGE = 4;

	public BlockTFKnightmetalBlock() {
		super(Material.IRON);
		this.setHardness(5.0F);
		this.setResistance(41.0F);
		this.setSoundType(SoundType.METAL);
		this.setCreativeTab(TFItems.creativeTab);
	}

	@Override
	@Deprecated
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return AABB;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
		entity.attackEntityFrom(DamageSource.CACTUS, BLOCK_DAMAGE);
	}

	@Override
	@Deprecated
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	@Deprecated
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	@Deprecated
	public boolean shouldSideBeRendered(IBlockState state, IBlockAccess par1IBlockAccess, BlockPos pos, EnumFacing side) {
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel() {
		ModelLoader.setCustomStateMapper(this, new StateMap.Builder().build());
		ModelUtils.registerToState(this, 0, getDefaultState());
	}
}
