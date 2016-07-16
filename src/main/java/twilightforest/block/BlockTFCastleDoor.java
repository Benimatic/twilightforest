package twilightforest.block;

import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

import twilightforest.TFGenericPacketHandler;
import twilightforest.TwilightForestMod;
import twilightforest.item.TFItems;
import twilightforest.network.PacketAnnihilateBlock;
import twilightforest.world.ChunkProviderTwilightForest;
import twilightforest.world.WorldProviderTwilightForest;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockTFCastleDoor extends Block
{
	public static final PropertyBool ACTIVE = PropertyBool.create("active");
	private final boolean isVanished;

	public BlockTFCastleDoor(boolean isVanished)
    {
        super(isVanished ? Material.GLASS : Material.ROCK);
        
        this.isVanished = isVanished;
        this.lightOpacity = isVanished ? 0 : 255;

        this.setCreativeTab(TFItems.creativeTab);
		this.setDefaultState(blockState.getBaseState().withProperty(ACTIVE, false));
    }

	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, ACTIVE);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(ACTIVE) ? 8 : 0;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(ACTIVE, meta == 8);
	}
    
    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return !this.isVanished;
    }
    
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, World par1World, BlockPos pos) {
		return isVanished ? NULL_AABB : super.getCollisionBoundingBox(state, par1World, pos);
	}
	
    @Override
	public boolean isPassable(IBlockAccess par1IBlockAccess, BlockPos pos)
    {
    	return !this.isVanished;
    }

    @Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (!state.getValue(ACTIVE))
        {
        	if (isBlockLocked(world, pos))
        	{
        		world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.click", 1.0F, 0.3F);
        	}
        	else
        	{
        		changeToActiveBlock(world, pos);
        	}
            return true;
        }
        else 
        {
        	return false;
        }
    }

    private static void changeToActiveBlock(World par1World, BlockPos pos)
	{
		changeToBlockMeta(par1World, pos, true);
		playVanishSound(par1World, pos);

		Block blockAt = par1World.getBlockState(pos).getBlock();
		par1World.scheduleUpdate(pos, blockAt, 2 + par1World.rand.nextInt(5));
	}

	private static void changeToBlockMeta(World par1World, BlockPos pos, boolean active)
	{
		IBlockState stateAt = par1World.getBlockState(pos);

		if (stateAt.getBlock() == TFBlocks.castleDoor || stateAt.getBlock() == TFBlocks.castleDoorVanished)
		{
			par1World.setBlockState(pos, stateAt.withProperty(ACTIVE, active), 3);
			par1World.markBlockRangeForRenderUpdate(pos, pos);
			par1World.notifyNeighborsRespectDebug(pos, stateAt.getBlock());
		}
	}

	private static boolean isBlockLocked(World par1World, BlockPos pos) {
		// check if we are in a structure, and if that structure says that we are locked
		if (!par1World.isRemote && par1World.provider instanceof WorldProviderTwilightForest) {
			ChunkProviderTwilightForest chunkProvider = ((WorldProviderTwilightForest)par1World.provider).getChunkProvider();

			return chunkProvider.isStructureLocked(pos, meta);
		} else {
			return false;
		}
	}

    @Override
    public int tickRate(World world)
    {
        return 5;
    }

    @Override
	public void updateTick(World par1World, BlockPos pos, IBlockState state, Random par5Random)
    {
    	if (!par1World.isRemote)
    	{
    		//System.out.println("Update castle door");
    		
    		if (this.isVanished) {
    			if (state.getValue(ACTIVE)) {
                	par1World.setBlock(x, y, z, TFBlocks.castleDoor, meta & 7, 3);
                    par1World.notifyNeighborsRespectDebug(pos, this);
                    playVanishSound(par1World, pos);

                    //par1World.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
    			} else {
                	changeToActiveBlock(par1World, pos);
    			}
    		} else {

    			// if we have an active castle door, turn it into a vanished door block
    			if (state.getValue(ACTIVE))
    			{
    				par1World.setBlock(x, y, z, getOtherBlock(this), meta & 7, 3);
    				par1World.scheduleUpdate(pos, getOtherBlock(this), 80);

    				par1World.notifyNeighborsRespectDebug(pos, this);
    				playReappearSound(par1World, pos);
    				par1World.markBlockRangeForRenderUpdate(pos, pos);
    				
            		this.sendAnnihilateBlockPacket(par1World, pos);


    				// activate all adjacent inactive doors
					for (EnumFacing e : EnumFacing.VALUES) {
						checkAndActivateCastleDoor(par1World, pos.offset(e));
					}
    			}
    			
    			// inactive solid door blocks we don't care about updates
    		}

    	}
    }
    
	private void sendAnnihilateBlockPacket(World world, BlockPos pos) {
		// send packet
		IMessage message = new PacketAnnihilateBlock(pos);

		NetworkRegistry.TargetPoint targetPoint = new NetworkRegistry.TargetPoint(world.provider.dimensionId, x, y, z, 64);
		
		TwilightForestMod.genericChannel.sendToAllAround(message, targetPoint);
	}

	private static void playVanishSound(World par1World, BlockPos pos) {
		par1World.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.fizz", 0.125f, par1World.rand.nextFloat() * 0.25F + 1.75F);
//		par1World.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "note.harp", 0.2F, par1World.rand.nextFloat() * 2F);
	}

	private static void playReappearSound(World par1World, BlockPos pos) {
		par1World.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.fizz", 0.125f, par1World.rand.nextFloat() * 0.25F + 1.25F);
//		par1World.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "note.harp", 0.2F, par1World.rand.nextFloat() * 2F);
	}

	private static Block getOtherBlock(Block block) {
		return block == TFBlocks.castleDoor ? TFBlocks.castleDoorVanished : TFBlocks.castleDoor;
	}

    /**
     * If the targeted block is a vanishing block, activate it
     */
    public static void checkAndActivateCastleDoor(World world, BlockPos pos) {
    	IBlockState state = world.getBlockState(pos);
    	
    	if (state.getBlock() == TFBlocks.castleDoor && !state.getValue(ACTIVE) && !isBlockLocked(world, pos))
    	{
    		changeToActiveBlock(world, pos);
    	}
//    	if (block == TFBlocks.castleDoorVanished && !isMetaActive(meta) && !isBlockLocked(world, x, y, z))
//    	{
//    		changeToActiveBlock(world, x, y, z, meta);
//    	}
	}
    
    
	@Override
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World par1World, BlockPos pos, Random par5Random)
    {
    	if (state.getValue(ACTIVE));
    	{
    		for (int i = 0; i < 1; ++i) {
    			//this.sparkle(par1World, x, y, z, par5Random);
    		}
    	}
    }
	

    /**
     * Shine bright like a DIAMOND! (or actually, sparkle like redstone ore)
     */
    public void sparkle(World world, BlockPos pos, Random rand)
    {
        double offset = 0.0625D;

        for (int side = 0; side < 6; ++side)
        {
            double rx = x + rand.nextFloat();
            double ry = y + rand.nextFloat();
            double rz = z + rand.nextFloat();

            if (side == 0 && !world.getBlock(x, y + 1, z).isOpaqueCube())
            {
                ry = y + 1 + offset;
            }

            if (side == 1 && !world.getBlock(x, y - 1, z).isOpaqueCube())
            {
                ry = y + 0 - offset;
            }

            if (side == 2 && !world.getBlock(x, y, z + 1).isOpaqueCube())
            {
                rz = z + 1 + offset;
            }

            if (side == 3 && !world.getBlock(x, y, z - 1).isOpaqueCube())
            {
                rz = z + 0 - offset;
            }

            if (side == 4 && !world.getBlock(x + 1, y, z).isOpaqueCube())
            {
                rx = x + 1 + offset;
            }

            if (side == 5 && !world.getBlock(x - 1, y, z).isOpaqueCube())
            {
                rx = x + 0 - offset;
            }

            if (rx < x || rx > x + 1 || ry < 0.0D || ry > y + 1 || rz < z || rz > z + 1)
            {
                world.spawnParticle(EnumParticleTypes.REDSTONE, rx, ry, rz, 0.0D, 0.0D, 0.0D);
            }
        }
    }

}