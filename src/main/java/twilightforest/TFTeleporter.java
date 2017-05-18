package twilightforest;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import twilightforest.biomes.TFBiomeBase;
import twilightforest.block.TFBlocks;
import twilightforest.world.TFWorld;
import net.minecraftforge.fml.common.FMLLog;

// todo 1.10 modernize this whole thing
public class TFTeleporter extends Teleporter 
{
	protected WorldServer myWorld;
	protected Random rand;

	public TFTeleporter(WorldServer par1WorldServer) {
		super(par1WorldServer);
		myWorld = par1WorldServer;
		if (this.rand == null)
		{
			this.rand = new Random();
		}

	}

	@Override
	public void placeInPortal(Entity par1Entity, float facing)
	{
		if (!this.placeInExistingPortal(par1Entity, facing))
		{
			// if we're in enforced progression mode, check the biomes for safety
			if (par1Entity.world.getGameRules().getBoolean(TwilightForestMod.ENFORCED_PROGRESSION_RULE)) {
				int px = MathHelper.floor(par1Entity.posX);
				int pz = MathHelper.floor(par1Entity.posZ);
				BlockPos pos = new BlockPos(par1Entity);
				if (!isSafeBiomeAt(pos, par1Entity)) {
					TwilightForestMod.LOGGER.debug("Portal destination looks unsafe, rerouting!");
					
					BlockPos safeCoords = findSafeCoords(200, pos, par1Entity);
					
					if (safeCoords != null) {
			            par1Entity.setLocationAndAngles(safeCoords.getX(), par1Entity.posY, safeCoords.getZ(), 90.0F, 0.0F);

						TwilightForestMod.LOGGER.debug("Safely rerouted!");
					} else {
						TwilightForestMod.LOGGER.debug("Did not find a safe spot at first try, trying again with longer range.");
						safeCoords = findSafeCoords(400, pos, par1Entity);
						if (safeCoords != null) {
				            par1Entity.setLocationAndAngles(safeCoords.getX(), par1Entity.posY, safeCoords.getZ(), 90.0F, 0.0F);

							TwilightForestMod.LOGGER.debug("Safely rerouted to long range portal.  Return trip not guaranteed.");
						} else {
							TwilightForestMod.LOGGER.debug("Did not find a safe spot.");
						}
					}
				}
				
			}
			
			
			this.makePortal(par1Entity);
			this.placeInExistingPortal(par1Entity, facing);
		}
	}
	
	private BlockPos findSafeCoords(int range, BlockPos pos, Entity par1Entity) {
		for (int i = 0; i < 25; i++) {
			BlockPos dPos = new BlockPos(
					pos.getX() + rand.nextInt(range) - rand.nextInt(range),
					100,
					pos.getZ() + rand.nextInt(range) - rand.nextInt(range)
			);

			if (isSafeBiomeAt(dPos, par1Entity)) {
				return dPos;
			}
		}
		return null;
	}

	private boolean isSafeBiomeAt(BlockPos pos, Entity par1Entity) {
		Biome biomeAt = myWorld.getBiome(pos);

		if (biomeAt instanceof TFBiomeBase && par1Entity instanceof EntityPlayerMP) {
			TFBiomeBase tfBiome = (TFBiomeBase)biomeAt;
			EntityPlayerMP player = (EntityPlayerMP)par1Entity;
			
			return tfBiome.doesPlayerHaveRequiredAchievement(player);
		} else {
			return true;
		}
	}

	@Override
	public boolean placeInExistingPortal(Entity entity, float rotationYaw) {
        int c = 200;
        double d = -1D;
        int i = 0;
        int j = 0;
        int k = 0;
        int l = MathHelper.floor(entity.posX);
        int i1 = MathHelper.floor(entity.posZ);
        for(int j1 = l - c; j1 <= l + c; j1++)
        {
            double d1 = (j1 + 0.5D) - entity.posX;
            for(int j2 = i1 - c; j2 <= i1 + c; j2++)
            {
                double d3 = (j2 + 0.5D) - entity.posZ;
                for (int k2 = TFWorld.MAXHEIGHT - 1; k2 >= 0; k2--)
                {
                    if(!isBlockPortal(myWorld, new BlockPos(j1, k2, j2)))
                    {
                        continue;
                    }
                    for(; isBlockPortal(myWorld, new BlockPos(j1, k2 - 1, j2)); k2--) { }
                    double d5 = (k2 + 0.5D) - entity.posY;
                    double d7 = d1 * d1 + d5 * d5 + d3 * d3;
                    if(d < 0.0D || d7 < d)
                    {
                        d = d7;
                        i = j1;
                        j = k2;
                        k = j2;
                    }
                }

            }

        }

        if(d >= 0.0D)
        {
        	BlockPos pos = new BlockPos(i, j, k);
			double portalX = i + 0.5D;
            double portalY = j + 0.5D;
            double portalZ = k + 0.5D;
            if(isBlockPortal(myWorld, pos.west()))
            {
                portalX -= 0.5D;
            }
            if(isBlockPortal(myWorld, pos.east()))
            {
                portalX += 0.5D;
            }
            if(isBlockPortal(myWorld, pos.north()))
            {
                portalZ -= 0.5D;
            }
            if(isBlockPortal(myWorld, pos.south()))
            {
                portalZ += 0.5D;
            }
            int xOffset = 0;
            int zOffset = 0; 
            while(xOffset == zOffset && xOffset == 0)
            {
            	xOffset = rand.nextInt(3) - rand.nextInt(3);
            	zOffset = rand.nextInt(3) - rand.nextInt(3);
            }            
            entity.setLocationAndAngles(portalX + xOffset, portalY + 1, portalZ + zOffset, entity.rotationYaw, 0.0F);
            entity.motionX = entity.motionY = entity.motionZ = 0.0D;
            return true;
        } else
        {
            return false;
        }
    }
	
	private boolean isBlockPortal(World world, BlockPos pos)
	{
		return world.getBlockState(pos).getBlock() == TFBlocks.portal;
	}

	@Override
	public boolean makePortal(Entity entity) {
		BlockPos spot = findPortalCoords(entity, true);
        
        if (spot != null)
        {
			TwilightForestMod.LOGGER.debug("Found ideal portal spot");
        	makePortalAt(myWorld, spot);
        	return true;
        }
        else
        {
			TwilightForestMod.LOGGER.debug("Did not find ideal portal spot, shooting for okay one");
        	spot = findPortalCoords(entity, false);
            if (spot != null)
            {
				TwilightForestMod.LOGGER.debug("Found okay portal spot");
            	makePortalAt(myWorld, spot);
            	return true;
            }
        }

        // well I don't think we can actally just return false and fail here
		TwilightForestMod.LOGGER.debug("Did not even find an okay portal spot, just making a random one");
    	
		// adjust the portal height based on what world we're traveling to
		double yFactor = myWorld.provider.getDimension() == 0 ? 2 : 0.5;
		// modified copy of base Teleporter method:
        makePortalAt(myWorld, new BlockPos(entity.posX, entity.posY * yFactor, entity.posZ));

        return false;
	}

	private BlockPos findPortalCoords(Entity entity, boolean ideal) {
		// adjust the portal height based on what world we're traveling to
		double yFactor = myWorld.provider.getDimension() == 0 ? 2 : 0.5;
		// modified copy of base Teleporter method:
        int entityX = MathHelper.floor(entity.posX);
        int entityZ = MathHelper.floor(entity.posZ);

        double spotWeight = -1D;
        
        BlockPos spot = null;
        
        byte range = 16;
        for(int rx = entityX - range; rx <= entityX + range; rx++)
        {
        	double xWeight = (rx + 0.5D) - entity.posX;
        	for(int rz = entityZ - range; rz <= entityZ + range; rz++)
        	{
        		double zWeight = (rz + 0.5D) - entity.posZ;

        		for(int ry = 128 - 1; ry >= 0; ry--)
        		{
        			BlockPos pos = new BlockPos(rx, ry, rz);

        			if(!myWorld.isAirBlock(pos))
        			{
        				continue;
        			}

        			while (pos.getY() > 0 && myWorld.isAirBlock(pos.down())) pos = pos.down();

        			if (ideal ? isIdealPortal(pos) : isOkayPortal(pos))
        			{
        				double yWeight = (pos.getY() + 0.5D) - entity.posY * yFactor;
        				double rPosWeight = xWeight * xWeight + yWeight * yWeight + zWeight * zWeight;
        				
        				
        				if(spotWeight < 0.0D || rPosWeight < spotWeight)
        				{
        					spotWeight = rPosWeight;
        					spot = pos;
        				}
        			}
        		}
        	}
        }
        
		return spot;
	}

	private boolean isIdealPortal(BlockPos pos) {
		for(int potentialZ = 0; potentialZ < 4; potentialZ++)
		{
			for(int potentialX = 0; potentialX < 4; potentialX++)
			{
				for(int potentialY = -1; potentialY < 3; potentialY++)
				{
					BlockPos tPos = pos.add(potentialX - 1, potentialY, potentialZ - 1);
					if (potentialY == -1 && myWorld.getBlockState(tPos).getMaterial() != Material.GRASS || potentialY >= 0 && !myWorld.getBlockState(tPos).getMaterial().isReplaceable())
					{
						return false;
					}
				}

			}

		}
		return true;
	}

	private boolean isOkayPortal(BlockPos pos) {
		for(int potentialZ = 0; potentialZ < 4; potentialZ++)
		{
			for(int potentialX = 0; potentialX < 4; potentialX++)
			{
				for(int potentialY = -1; potentialY < 3; potentialY++)
				{
					BlockPos tPos = pos.add(potentialX - 1, potentialY, potentialZ - 1);
					if (potentialY == -1 && !myWorld.getBlockState(tPos).getMaterial().isSolid() || potentialY >= 0 && !myWorld.getBlockState(tPos).getMaterial().isReplaceable())
					{
						return false;
					}
				}

			}

		}
		return true;
	}

	private void makePortalAt(World world, BlockPos pos) {

		if(pos.getY() < 30)
		{
			pos = new BlockPos(pos.getX(), 30, pos.getZ());
		}

		if(pos.getY() > 128 - 10)
		{
			pos = new BlockPos(pos.getX(), 128 - 10, pos.getZ());
		}
		
		// sink the portal 1 into the ground
		pos = pos.down();

		
		// grass all around it
		world.setBlockState(pos.west().north(), Blocks.GRASS.getDefaultState());
		world.setBlockState(pos.north(), Blocks.GRASS.getDefaultState());
		world.setBlockState(pos.east().north(), Blocks.GRASS.getDefaultState());
		world.setBlockState(pos.east(2).north(), Blocks.GRASS.getDefaultState());

		world.setBlockState(pos.west(), Blocks.GRASS.getDefaultState());
		world.setBlockState(pos.east(2), Blocks.GRASS.getDefaultState());

		world.setBlockState(pos.west().south(), Blocks.GRASS.getDefaultState());
		world.setBlockState(pos.east(2).south(), Blocks.GRASS.getDefaultState());

		world.setBlockState(pos.west().south(2), Blocks.GRASS.getDefaultState());
		world.setBlockState(pos.south(2), Blocks.GRASS.getDefaultState());
		world.setBlockState(pos.east().south(2), Blocks.GRASS.getDefaultState());
		world.setBlockState(pos.east(2).south(2), Blocks.GRASS.getDefaultState());

		// dirt under it
		world.setBlockState(pos.down(), Blocks.DIRT.getDefaultState());
		world.setBlockState(pos.east().down(), Blocks.DIRT.getDefaultState());
		world.setBlockState(pos.south().down(), Blocks.DIRT.getDefaultState());
		world.setBlockState(pos.east().south().down(), Blocks.DIRT.getDefaultState());
		
		// portal in it
		world.setBlockState(pos, TFBlocks.portal.getDefaultState(), 2);
		world.setBlockState(pos.east(), TFBlocks.portal.getDefaultState(), 2);
		world.setBlockState(pos.south(), TFBlocks.portal.getDefaultState(), 2);
		world.setBlockState(pos.east().south(), TFBlocks.portal.getDefaultState(), 2);
		
		// meh, let's just make a bunch of air over it for 4 squares
		for (int dx = -1; dx <= 2; dx++) {
			for (int dz = -1; dz <= 2; dz++) {
				for (int dy = 1; dy <=5; dy++) {
					world.setBlockToAir(pos.add(dx, dy, dz));
				}
			}
		}

		// finally, "nature decorations"!
		world.setBlockState(pos.west().north().up(), randNatureBlock(world.rand), 2);
		world.setBlockState(pos.north().up(), randNatureBlock(world.rand), 2);
		world.setBlockState(pos.east().north().up(), randNatureBlock(world.rand), 2);
		world.setBlockState(pos.east(2).north().up(), randNatureBlock(world.rand), 2);

		world.setBlockState(pos.west().up(), randNatureBlock(world.rand), 2);
		world.setBlockState(pos.east(2).up(), randNatureBlock(world.rand), 2);

		world.setBlockState(pos.west().south().up(), randNatureBlock(world.rand), 2);
		world.setBlockState(pos.east(2).south().up(), randNatureBlock(world.rand), 2);

		world.setBlockState(pos.west().south(2).up(), randNatureBlock(world.rand), 2);
		world.setBlockState(pos.south(2).up(), randNatureBlock(world.rand), 2);
		world.setBlockState(pos.east().south(2).up(), randNatureBlock(world.rand), 2);
		world.setBlockState(pos.east(2).south(2).up(), randNatureBlock(world.rand), 2);
	}
	
	private IBlockState randNatureBlock(Random random) {
		Block[] block = {Blocks.BROWN_MUSHROOM, Blocks.RED_MUSHROOM, Blocks.TALLGRASS, Blocks.RED_FLOWER, Blocks.YELLOW_FLOWER};
		
		return block[random.nextInt(block.length)].getDefaultState();
	}

}
