package twilightforest.block;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import twilightforest.TFAchievementPage;
import twilightforest.TFTeleporter;
import twilightforest.TwilightForestMod;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTFPortal extends BlockBreakable
{

	private static final AxisAlignedBB AABB = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);

    public BlockTFPortal()
    {
        super(Material.PORTAL, false);
        this.setHardness(-1F);
        this.setSoundType(SoundType.GLASS);
        this.setLightLevel(0.75F);
		//this.setCreativeTab(TFItems.creativeTab);
    }

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		return AABB;
	}

    @Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, World world, BlockPos pos)
    {
        return NULL_AABB;
    }

    @Override
	public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    /**
     * The function name says it all.  Tries to create a portal at the specified location.
     * In this case, the location is the location of a pool with very specific parameters.
     */
    public boolean tryToCreatePortal(World world, BlockPos pos)
    {
    	if (isGoodPortalPool(world, pos))
    	{
    		world.addWeatherEffect(new EntityLightningBolt(world, pos.getX(), pos.getY(), pos.getZ(), false));
    		
    		transmuteWaterToPortal(world, pos);
    		
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
    
    /**
     * Changes the pool it's been given to all portal.  No checks done, only does 4 squares.
     */
	private void transmuteWaterToPortal(World world, BlockPos pos)
    {
    	// adjust so that the other 3 water squares are in the +x, +z directions.
    	if (world.getBlockState(pos.west()).getMaterial() == Material.WATER)
    	{
			pos = pos.west();
    	}
    	if (world.getBlockState(pos.north()).getMaterial() == Material.WATER)
    	{
    		pos = pos.north();
    	}
    	
    	world.setBlockState(pos, TFBlocks.portal.getDefaultState(), 2);
    	world.setBlockState(pos.east(), TFBlocks.portal.getDefaultState(), 2);
    	world.setBlockState(pos.east().south(), TFBlocks.portal.getDefaultState(), 2);
    	world.setBlockState(pos.south(), TFBlocks.portal.getDefaultState(), 2);
    	
    	//System.out.println("Transmuting water to portal");
    }
    
    /**
     * If this spot, or a spot in any one of the 8 directions around me is good, we're good.
     */
	private boolean isGoodPortalPool(World world, BlockPos pos)
    {
		return isGoodPortalPoolStrict(world, pos)
				|| isGoodPortalPoolStrict(world, pos.north())
				|| isGoodPortalPoolStrict(world, pos.south())
				|| isGoodPortalPoolStrict(world, pos.west())
				|| isGoodPortalPoolStrict(world, pos.east())
				|| isGoodPortalPoolStrict(world, pos.west().north())
				|| isGoodPortalPoolStrict(world, pos.west().south())
				|| isGoodPortalPoolStrict(world, pos.east().north())
				|| isGoodPortalPoolStrict(world, pos.east().south());
    }
    
    /**
     * Returns true only if there is water here, and at dx + 1, dy + 1, grass surrounding it, and solid beneath.
     * 
     * 
     *   GGGG
     *   G+wG
     *   GwwG
     *   GGGG
     * 
     * 
     */
    public boolean isGoodPortalPoolStrict(World world, BlockPos pos)
    {
				// 4 squares of water
		return  world.getBlockState(pos).getMaterial() == Material.WATER
				&& world.getBlockState(pos.east()).getMaterial() == Material.WATER
				&& world.getBlockState(pos.east().south()).getMaterial() == Material.WATER
				&& world.getBlockState(pos.south()).getMaterial() == Material.WATER

				// grass in the 12 squares surrounding
				&& isGrassOrDirt(world, pos.west().north())
				&& isGrassOrDirt(world, pos.west())
				&& isGrassOrDirt(world, pos.west().south())
				&& isGrassOrDirt(world, pos.west().south(2))

				&& isGrassOrDirt(world, pos.north())
				&& isGrassOrDirt(world, pos.east().north())

				&& isGrassOrDirt(world, pos.south(2))
				&& isGrassOrDirt(world, pos.east().south(2))

				&& isGrassOrDirt(world, pos.east(2).north())
				&& isGrassOrDirt(world, pos.east(2))
				&& isGrassOrDirt(world, pos.east(2).south())
				&& isGrassOrDirt(world, pos.east(2).south(2))

    			// solid underneath
				&& world.getBlockState(pos.down()).getMaterial().isSolid()
				&& world.getBlockState(pos.down().east()).getMaterial().isSolid()
				&& world.getBlockState(pos.down().east().south()).getMaterial().isSolid()
				&& world.getBlockState(pos.down().south()).getMaterial().isSolid()

    			// 12 nature blocks above the grass?
				&& isNatureBlock(world, pos.up().west().north())
				&& isNatureBlock(world, pos.up().west())
				&& isNatureBlock(world, pos.up().west().south())
				&& isNatureBlock(world, pos.up().west().south(2))

				&& isNatureBlock(world, pos.up().north())
				&& isNatureBlock(world, pos.up().east().north())

				&& isNatureBlock(world, pos.up().south(2))
				&& isNatureBlock(world, pos.up().south(2).east())

				&& isNatureBlock(world, pos.up().east(2).north())
				&& isNatureBlock(world, pos.up().east(2))
				&& isNatureBlock(world, pos.up().east(2).south())
				&& isNatureBlock(world, pos.up().east(2).south(2));
    }
    
    /**
     * Does the block at this location count as a "nature" block for portal purposes?
     */
	private boolean isNatureBlock(World world, BlockPos pos)
    {
    	Material mat = world.getBlockState(pos).getMaterial();
		return mat == Material.PLANTS || mat == Material.VINE || mat == Material.LEAVES;
    }

    /**
     * Each twilight portal pool block should have grass or dirt on one side and a portal on the other.  If this is not true, delete this block, presumably causing a chain reaction.
     */
    @Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block notUsed)
    {
    	boolean good = true;
    	
    	if (world.getBlockState(pos.west()).getBlock() == this) {
    		good &= isGrassOrDirt(world, pos.east());
    	}
    	else if (world.getBlockState(pos.east()).getBlock() == this) {
    		good &= isGrassOrDirt(world, pos.west());
    	}
    	else
    	{
    		good = false;
    	}
    	
    	if (world.getBlockState(pos.north()).getBlock() == this) {
    		good &= isGrassOrDirt(world, pos.south());
    	}
    	else if (world.getBlockState(pos.south()).getBlock() == this) {
    		good &= isGrassOrDirt(world, pos.north());
    	}
    	else
    	{
    		good = false;
    	}
    	
    	// if we're not good, remove this block
    	if (!good)
    	{
			world.playEvent(2001, pos, Block.getStateId(state));
    		world.setBlockState(pos, Blocks.WATER.getDefaultState(), 3);
    	}
    }
    
    private boolean isGrassOrDirt(World world, BlockPos pos)
    {
    	return world.getBlockState(pos).getMaterial() == Material.GRASS
				|| world.getBlockState(pos).getMaterial() == Material.GROUND;
    }

    @Override
	public int quantityDropped(Random random)
    {
        return 0;
    }

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}

    @Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
    {
    	if(!entity.isRiding() && entity.getPassengers().isEmpty() && entity.timeUntilPortal <= 0)
    	{
    		if (entity instanceof EntityPlayerMP)
    		{
    			EntityPlayerMP playerMP = (EntityPlayerMP) entity;

    			if (playerMP.timeUntilPortal > 0)
    			{
    				// do not switch dimensions if the player has any time on this thinger
    				playerMP.timeUntilPortal = 10;
    			}
    			else {

    				// send to twilight
    				if (playerMP.dimension != TwilightForestMod.dimensionID) {
    					playerMP.addStat(TFAchievementPage.twilightPortal);
    					playerMP.addStat(TFAchievementPage.twilightArrival);
    					FMLLog.info("[TwilightForest] Player touched the portal block.  Sending the player to dimension " + TwilightForestMod.dimensionID);

    					playerMP.mcServer.getPlayerList().transferPlayerToDimension(playerMP, TwilightForestMod.dimensionID, new TFTeleporter(playerMP.mcServer.worldServerForDimension(TwilightForestMod.dimensionID)));
    					playerMP.addExperienceLevel(0);
    					playerMP.addStat(TFAchievementPage.twilightPortal);
    					playerMP.addStat(TFAchievementPage.twilightArrival);
    					
    					// set respawn point for TF dimension to near the arrival portal
    					int spawnX = MathHelper.floor(playerMP.posX);
    					int spawnY = MathHelper.floor(playerMP.posY);
    					int spawnZ = MathHelper.floor(playerMP.posZ);
    					
    					playerMP.setSpawnChunk(new BlockPos(spawnX, spawnY, spawnZ), true, TwilightForestMod.dimensionID);
    				}
    				else {
    					//System.out.println("Player touched the portal block.  Sending the player to dimension 0");
    					//playerMP.travelToDimension(0);
    					playerMP.mcServer.getPlayerList().transferPlayerToDimension(playerMP, 0, new TFTeleporter(playerMP.mcServer.worldServerForDimension(0)));
    					playerMP.addExperienceLevel(0);
    				}
    			}
    		}
    		else
    		{
    			if (entity.dimension != TwilightForestMod.dimensionID)
    			{
        			//sendEntityToDimension(entity, TwilightForestMod.dimensionID);
    			}
    			else
    			{
        			changeDimension(entity, 0);
    			}
    		}
    	}
        
    }

    /**
     * [VanillaCopy] Entity.changeDimension. Relevant edits noted.
	 * `this` -> `toTeleport`
	 * return value Entity -> void
     */
	private void changeDimension(Entity toTeleport, int dimensionIn) {
		if (!toTeleport.worldObj.isRemote && !toTeleport.isDead)
		{
			if (!net.minecraftforge.common.ForgeHooks.onTravelToDimension(toTeleport, dimensionIn)) return;
			toTeleport.worldObj.theProfiler.startSection("changeDimension");
			MinecraftServer minecraftserver = toTeleport.getServer();
			int i = toTeleport.dimension;
			WorldServer worldserver = minecraftserver.worldServerForDimension(i);
			WorldServer worldserver1 = minecraftserver.worldServerForDimension(dimensionIn);
			toTeleport.dimension = dimensionIn;

			if (i == 1 && dimensionIn == 1)
			{
				worldserver1 = minecraftserver.worldServerForDimension(0);
				toTeleport.dimension = 0;
			}

			toTeleport.worldObj.removeEntity(toTeleport);
			toTeleport.isDead = false;
			toTeleport.worldObj.theProfiler.startSection("reposition");
			// TF - "reposition" section completely replaced with call to following method.
			// TF - use custom Teleporter
			minecraftserver.getPlayerList().transferEntityToWorld(toTeleport, dimensionIn, worldserver, worldserver1, new TFTeleporter(worldserver1));
			BlockPos blockpos = new BlockPos(toTeleport); // TF - retain this line from old reposition section
			toTeleport.worldObj.theProfiler.endStartSection("reloading");
			Entity entity = EntityList.createEntityByName(EntityList.getEntityString(toTeleport), worldserver1);

			if (entity != null)
			{
				// todo 1.9.4 factor out reflectionmappings
				// TF - reflectively call copyDataFromOld
				Method copyDataFromOld =
						ReflectionHelper.findMethod(Entity.class, null, new String[]{ "copyDataFromOld", "func_180432_n", "a" }, Entity.class);
				try {
					copyDataFromOld.invoke(entity, toTeleport);
				} catch (IllegalAccessException | InvocationTargetException e) {
					// Try to recover
					NBTTagCompound cmp = toTeleport.writeToNBT(new NBTTagCompound());
					cmp.removeTag("Dimension");
					entity.readFromNBT(cmp);
				}

				if (i == 1 && dimensionIn == 1)
				{
					BlockPos blockpos1 = worldserver1.getTopSolidOrLiquidBlock(worldserver1.getSpawnPoint());
					entity.moveToBlockPosAndAngles(blockpos1, entity.rotationYaw, entity.rotationPitch);
				}
				else
				{
					entity.moveToBlockPosAndAngles(blockpos, entity.rotationYaw, entity.rotationPitch);
				}

				boolean flag = entity.forceSpawn;
				entity.forceSpawn = true;
				worldserver1.spawnEntity(entity);
				entity.forceSpawn = flag;
				worldserver1.updateEntityWithOptionalForce(entity, false);
			}

			toTeleport.isDead = true;
			toTeleport.worldObj.theProfiler.endSection();
			worldserver.resetUpdateEntityTick();
			worldserver1.resetUpdateEntityTick();
			toTeleport.worldObj.theProfiler.endSection();
		}
	}

	// Full [VanillaCopy] of BlockPortal.randomDisplayTick
    @Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
		if (rand.nextInt(100) == 0)
		{
			worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);
		}

		for (int i = 0; i < 4; ++i)
		{
			double d0 = (double)((float)pos.getX() + rand.nextFloat());
			double d1 = (double)((float)pos.getY() + rand.nextFloat());
			double d2 = (double)((float)pos.getZ() + rand.nextFloat());
			double d3 = ((double)rand.nextFloat() - 0.5D) * 0.5D;
			double d4 = ((double)rand.nextFloat() - 0.5D) * 0.5D;
			double d5 = ((double)rand.nextFloat() - 0.5D) * 0.5D;
			int j = rand.nextInt(2) * 2 - 1;

			if (worldIn.getBlockState(pos.west()).getBlock() != this && worldIn.getBlockState(pos.east()).getBlock() != this)
			{
				d0 = (double)pos.getX() + 0.5D + 0.25D * (double)j;
				d3 = (double)(rand.nextFloat() * 2.0F * (float)j);
			}
			else
			{
				d2 = (double)pos.getZ() + 0.5D + 0.25D * (double)j;
				d5 = (double)(rand.nextFloat() * 2.0F * (float)j);
			}

			worldIn.spawnParticle(EnumParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5, new int[0]);
		}
	}
    
	@Override
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
    }
}
