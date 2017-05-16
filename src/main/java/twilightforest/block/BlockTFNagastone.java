package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import twilightforest.block.enums.NagastoneVariant;
import twilightforest.item.TFItems;

import javax.annotation.Nonnull;
import java.util.Random;

public class BlockTFNagastone extends Block {
	public static final PropertyEnum<NagastoneVariant> VARIANT = PropertyEnum.create("variant", NagastoneVariant.class);

	public BlockTFNagastone() {
		super(Material.ROCK);
		this.setHardness(1.5F);
		this.setResistance(10.0F);
		this.setSoundType(SoundType.STONE);
		this.setCreativeTab(TFItems.creativeTab);

		this.setDefaultState(blockState.getBaseState().withProperty(VARIANT, NagastoneVariant.SOLID));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(VARIANT).ordinal();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(VARIANT, NagastoneVariant.values()[(meta & 15)]);
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block) {
		world.scheduleUpdate(pos, this, this.tickRate(world));
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state){
		world.scheduleUpdate(pos, this, this.tickRate(world));
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState stateIn, Random rand) {
		if (NagastoneVariant.isHead(stateIn.getValue(VARIANT)))
			return;

		// If state is not a head then you may go ahead and update

		int connectionCount = 0;
		IBlockState stateOut = stateIn;
		EnumFacing[] facings = new EnumFacing[2];

		// get sides

		for(EnumFacing side : EnumFacing.VALUES)
			if (world.getBlockState(pos.offset(side)).getBlock() == TFBlocks.nagastone)
				if (++connectionCount > 2) break;
				else facings[connectionCount - 1] = side;


		// if there are 2 sides that don't line on same axis, use an elbow part, else use axis part
		// if there is 1 side, then use an axis part
		// if there are 0 or greater than 2 sides, use solid

		switch (connectionCount) {
			case 2:
				if (facings[0].getAxis() != facings[1].getAxis()) {
					stateIn = stateOut.withProperty(VARIANT, NagastoneVariant.getVariantFromDoubleFacing(facings[0] , facings[1]));
					break;
				}
			case 1:
				stateIn = stateOut.withProperty(VARIANT, NagastoneVariant.getVariantFromAxis(facings[0].getAxis()));
				break;
			default:
				stateOut = this.getDefaultState();
				break;
		}

		// if result matches state in world, no need to add more effort
		if (stateIn != stateOut)
			world.setBlockState(pos, stateOut);
	}

	@Nonnull
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, VARIANT);
	}
}
