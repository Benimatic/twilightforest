package twilightforest.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;
import twilightforest.block.enums.MagicWoodVariant;
import twilightforest.item.TFItems;

public class BlockTFMagicLeaves extends BlockLeaves {

	protected BlockTFMagicLeaves() {
		this.setHardness(0.2F);
		this.setLightOpacity(2);
		this.setCreativeTab(TFItems.creativeTab);
		this.setDefaultState(blockState.getBaseState().withProperty(CHECK_DECAY, true).withProperty(DECAYABLE, true)
				.withProperty(BlockTFMagicLog.VARIANT, MagicWoodVariant.TIME));
	}

	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, BlockTFMagicLog.VARIANT, CHECK_DECAY, DECAYABLE);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = state.getValue(BlockTFMagicLog.VARIANT).ordinal();
		if (state.getValue(CHECK_DECAY))
			meta |= 0b1000;
		if (state.getValue(DECAYABLE))
			meta |= 0b100;
		return meta;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		int variant = meta & 0b11;
		boolean checkDecay = (meta & 0b1000) > 0;
		boolean decayable = (meta & 0b100) > 0;
		return getDefaultState().withProperty(CHECK_DECAY, checkDecay)
				.withProperty(DECAYABLE, decayable)
				.withProperty(BlockTFMagicLog.VARIANT, MagicWoodVariant.values()[variant]);
	}

    /**
     * Returns the color this block should be rendered. Used by leaves.
     */
    @Override
	public int getRenderColor(int par1)
    {
    	switch (par1 & 3)
    	{
    	case META_TIME :
        	return 106 << 16 | 156 << 8 | 23;
    	case META_TRANS :
        	return 108 << 16 | 204 << 8 | 234;
    	case META_MINE :
        	return 252 << 16 | 241 << 8 | 68;
    	case META_SORT :
        	return 54 << 16 | 76 << 8 | 3;
    	}
    	
    	return 16777215;
    }

    /**
     * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
     * when first determining what to render.
     */
    @Override
	public int colorMultiplier(IBlockAccess world, int x, int y, int z)
    {
    	int leafType = world.getBlockMetadata(x, y, z) & 0x03;

    	int red = 0;
    	int green = 0;
    	int blue = 0;

    	if (leafType == META_TIME)
    	{
        	int fade = x * 16 + y * 16 + z * 16;
        	if ((fade & 256) != 0)
        	{
        		fade = 255 - (fade & 255);
        	}
        	fade &= 255;
        	
        	float spring = (255 - fade) / 255F;
        	float fall = fade / 255F;
        	
	    	red = (int) (spring * 106 + fall * 251);
	    	green = (int) (spring * 156 + fall * 108);
	    	blue = (int) (spring * 23 + fall * 27);
    	}
    	else if (leafType == META_MINE)
    	{
        	int fade = x * 31 + y * 33 + z * 32;
        	if ((fade & 256) != 0)
        	{
        		fade = 255 - (fade & 255);
        	}
        	fade &= 255;
        	
        	float spring = (255 - fade) / 255F;
        	float fall = fade / 255F;
        	
	    	red = (int) (spring * 252 + fall * 237);
	    	green = (int) (spring * 241 + fall * 172);
	    	blue = (int) (spring * 68 + fall * 9);
    	}
    	else if (leafType == META_TRANS)
    	{
        	int fade = x * 27 + y * 63 + z * 39;
        	if ((fade & 256) != 0)
        	{
        		fade = 255 - (fade & 255);
        	}
        	fade &= 255;
        	
        	float spring = (255 - fade) / 255F;
        	float fall = fade / 255F;
        	
	    	red = (int) (spring * 108 + fall * 96);
	    	green = (int) (spring * 204 + fall * 107);
	    	blue = (int) (spring * 234 + fall * 121);
    	}
    	else if (leafType == META_SORT)
    	{
        	int fade = x * 63 + y * 63 + z * 63;
        	if ((fade & 256) != 0)
        	{
        		fade = 255 - (fade & 255);
        	}
        	fade &= 255;
        	
        	float spring = (255 - fade) / 255F;
        	float fall = fade / 255F;
        	
	    	red = (int) (spring * 54 + fall * 168);
	    	green = (int) (spring * 76 + fall * 199);
	    	blue = (int) (spring * 3 + fall * 43);
    	}
    	
    	return red << 16 | green << 8 | blue;

    }
    
    @Override
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess iblockaccess, BlockPos pos, EnumFacing side)
    {
    	return Blocks.LEAVES.shouldSideBeRendered(state, iblockaccess, pos, side);
    }
    
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> par3List)
    {
    	par3List.add(new ItemStack(par1, 1, 0));
    	par3List.add(new ItemStack(par1, 1, 1));
    	par3List.add(new ItemStack(par1, 1, 2));
    	par3List.add(new ItemStack(par1, 1, 3));
    }

    @Override
	public void randomDisplayTick(IBlockState state, World par1World, BlockPos pos, Random par5Random)
    {
    	if (state.getValue(BlockTFMagicLog.VARIANT) == MagicWoodVariant.TRANS)
    	{
    		for (int i = 0; i < 1; ++i) {
    			this.sparkleRunes(par1World, pos, par5Random);
    		}
    	}
    }

	@Override
	public BlockPlanks.EnumType getWoodType(int meta) {
		return BlockPlanks.EnumType.OAK;
	}

	private void sparkleRunes(World world, BlockPos pos, Random rand)
    {
    	double offset = 0.0625D;

    	int side = rand.nextInt(5) + 1;
    	double rx = x + rand.nextFloat();
    	double ry = y + rand.nextFloat();
    	double rz = z + rand.nextFloat();

    	if (side == 0 && world.isAirBlock(x, y + 1, z))
    	{
    		ry = y + 1 + offset;
    	}

    	if (side == 1 && world.isAirBlock(x, y - 1, z))
    	{
    		ry = y + 0 - offset;
    	}

    	if (side == 2 && world.isAirBlock(x, y, z + 1))
    	{
    		rz = z + 1 + offset;
    	}

    	if (side == 3 && world.isAirBlock(x, y, z - 1))
    	{
    		rz = z + 0 - offset;
    	}

    	if (side == 4 && world.isAirBlock(x + 1, y, z))
    	{
    		rx = x + 1 + offset;
    	}

    	if (side == 5 && world.isAirBlock(x - 1, y, z))
    	{
    		rx = x + 0 - offset;
    	}

    	if (rx < x || rx > x + 1 || ry < y|| ry > y + 1 || rz < z || rz > z + 1)
    	{
    		TwilightForestMod.proxy.spawnParticle(world, "leafrune", rx, ry, rz, 0.0D, 0.0D, 0.0D);
    	}
    }

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
		return null; // todo 1.9
	}
}
