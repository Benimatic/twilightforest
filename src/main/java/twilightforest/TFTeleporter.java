package twilightforest;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import twilightforest.biomes.TFBiomeBase;
import twilightforest.block.TFBlocks;
import twilightforest.world.TFWorld;
import net.minecraftforge.fml.common.FMLLog;


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

	/**
	 * Place an entity in a nearby portal, creating one if necessary.
	 */
	public void placeInPortal(Entity par1Entity, double x, double y, double z, float facing)
	{
		if (!this.placeInExistingPortal(par1Entity, x, y, z, facing))
		{
			// if we're in enforced progression mode, check the biomes for safety
			if (par1Entity.worldObj.getGameRules().getBoolean(TwilightForestMod.ENFORCED_PROGRESSION_RULE)) {
				int px = MathHelper.floor_double(par1Entity.posX);
				int pz = MathHelper.floor_double(par1Entity.posZ);
				if (!isSafeBiomeAt(px, pz, par1Entity)) {
					System.out.println("[TwilightForest] Portal destination looks unsafe, rerouting!");
					
					BlockPos safeCoords = findSafeCoords(200, px, pz, par1Entity);
					
					if (safeCoords != null) {
			            par1Entity.setLocationAndAngles(safeCoords.posX, par1Entity.posY, safeCoords.posZ, 90.0F, 0.0F);
			            x = safeCoords.posX;
			            z = safeCoords.posZ;
			            
			            System.out.println("[TwilightForest] Safely rerouted!");
					} else {
			            System.out.println("[TwilightForest] Did not find a safe spot at first try, trying again with longer range.");
						safeCoords = findSafeCoords(400, px, pz, par1Entity);
						if (safeCoords != null) {
				            par1Entity.setLocationAndAngles(safeCoords.posX, par1Entity.posY, safeCoords.posZ, 90.0F, 0.0F);
				            x = safeCoords.posX;
				            z = safeCoords.posZ;
				            
				            System.out.println("[TwilightForest] Safely rerouted to long range portal.  Return trip not guaranteed.");
						} else {
				            System.out.println("[TwilightForest] Did not find a safe spot.");
						}
					}
				}
				
			}
			
			
			this.makePortal(par1Entity);
			this.placeInExistingPortal(par1Entity, x, y, z, facing);
		}
	}
	
	/**
	 * Find some safe coords within range of the destination coords, or return null.
	 * 
	 * This could sure be a more methodic algorithm
	 */
	private BlockPos findSafeCoords(int range, int x, int z, Entity par1Entity) {
		for (int i = 0; i < 25; i++) {
			int dx = x + (rand.nextInt(range) - rand.nextInt(range));
			int dz = z + (rand.nextInt(range) - rand.nextInt(range));
			if (isSafeBiomeAt(dx, dz, par1Entity)) {
				return new BlockPos(dx, 100, dz);
			}
		}
		return null;
	}

	/**
	 * Check if the destination is safe
	 */
	boolean isSafeBiomeAt(int x, int z, Entity par1Entity) {
		Biome biomeAt = myWorld.getBiomeGenForCoords(x, z);

		if (biomeAt instanceof TFBiomeBase && par1Entity instanceof EntityPlayerMP) {
			TFBiomeBase tfBiome = (TFBiomeBase)biomeAt;
			EntityPlayerMP player = (EntityPlayerMP)par1Entity;
			
			return tfBiome.doesPlayerHaveRequiredAchievement(player);
			
		} else {
			// I guess it's safe, or maybe I just have no way to be sure!
			return true;
		}
	}


	@Override
	public boolean placeInExistingPortal(Entity entity, double par3, double par5, double par7, float par9) {
		
        int c = 200;
        double d = -1D;
        int i = 0;
        int j = 0;
        int k = 0;
        int l = MathHelper.floor_double(entity.posX);
        int i1 = MathHelper.floor_double(entity.posZ);
        for(int j1 = l - c; j1 <= l + c; j1++)
        {
            double d1 = (j1 + 0.5D) - entity.posX;
            for(int j2 = i1 - c; j2 <= i1 + c; j2++)
            {
                double d3 = (j2 + 0.5D) - entity.posZ;
                for (int k2 = TFWorld.MAXHEIGHT - 1; k2 >= 0; k2--)
                {
                    if(!isBlockPortal(myWorld, j1, k2, j2))
                    {
                        continue;
                    }
                    for(; isBlockPortal(myWorld, j1, k2 - 1, j2); k2--) { }
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
            int k1 = i;
            int l1 = j;
            int i2 = k;
            double portalX = k1 + 0.5D;
            double portalY = l1 + 0.5D;
            double portalZ = i2 + 0.5D;
            if(isBlockPortal(myWorld, k1 - 1, l1, i2))
            {
                portalX -= 0.5D;
            }
            if(isBlockPortal(myWorld, k1 + 1, l1, i2))
            {
                portalX += 0.5D;
            }
            if(isBlockPortal(myWorld, k1, l1, i2 - 1))
            {
                portalZ -= 0.5D;
            }
            if(isBlockPortal(myWorld, k1, l1, i2 + 1))
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
	
	/**
	 * Is this block either our portal or an existing nether portal?
	 */
	public boolean isBlockPortal(World world, int x, int y, int z)
	{
		return world.getBlock(x, y, z) == TFBlocks.portal;
	}

	@Override
	public boolean makePortal(Entity entity) {
		BlockPos spot = findPortalCoords(entity, true);
        
        if (spot != null)
        {
        	FMLLog.info("[TwilightForest] Found ideal portal spot");
        	makePortalAt(myWorld, spot.posX, spot.posY, spot.posZ);
        	return true;
        }
        else
        {
        	FMLLog.info("[TwilightForest] Did not find ideal portal spot, shooting for okay one");
        	spot = findPortalCoords(entity, false);
            if (spot != null)
            {
            	FMLLog.info("[TwilightForest] Found okay portal spot");
            	makePortalAt(myWorld, spot.posX, spot.posY, spot.posZ);
            	return true;
            }
        }

        // well I don't think we can actally just return false and fail here
    	FMLLog.info("[TwilightForest] Did not even find an okay portal spot, just making a random one");
    	
		// adjust the portal height based on what world we're traveling to
		double yFactor = myWorld.provider.dimensionId == 0 ? 2 : 0.5;
		// modified copy of base Teleporter method:
        int entityX = MathHelper.floor_double(entity.posX);
        int entityY = MathHelper.floor_double(entity.posY * yFactor);
        int entityZ = MathHelper.floor_double(entity.posZ);
        
        makePortalAt(myWorld, entityX, entityY, entityZ);

        return false;
	}

	public BlockPos findPortalCoords(Entity entity, boolean ideal) {
		// adjust the portal height based on what world we're traveling to
		double yFactor = myWorld.provider.dimensionId == 0 ? 2 : 0.5;
		// modified copy of base Teleporter method:
        int entityX = MathHelper.floor_double(entity.posX);
        int entityZ = MathHelper.floor_double(entity.posZ);

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
        			if(!myWorld.isAirBlock(rx, ry, rz))
        			{
        				continue;
        			}
        			for(; ry > 0 && myWorld.isAirBlock(rx, ry - 1, rz); ry--) { }

        			if (ideal ? isIdealPortal(rx, rz, ry) : isOkayPortal(rx, rz, ry))
        			{
        				double yWeight = (ry + 0.5D) - entity.posY * yFactor;
        				double rPosWeight = xWeight * xWeight + yWeight * yWeight + zWeight * zWeight;
        				
        				
        				if(spotWeight < 0.0D || rPosWeight < spotWeight)
        				{
        					spotWeight = rPosWeight;
        					spot = new BlockPos(rx, ry, rz);
        				}
        			}
        		}
        	}
        }
        
		return spot;
	}

	public boolean isIdealPortal(int rx, int rz, int ry) {
		for(int potentialZ = 0; potentialZ < 4; potentialZ++)
		{
			for(int potentialX = 0; potentialX < 4; potentialX++)
			{
				for(int potentialY = -1; potentialY < 3; potentialY++)
				{
					int tx = rx + (potentialX - 1);
					int ty = ry + potentialY;
					int tz = rz + (potentialZ - 1);
					if (potentialY == -1 && myWorld.getBlock(tx, ty, tz).getMaterial() != Material.GRASS || potentialY >= 0 && !myWorld.getBlock(tx, ty, tz).getMaterial().isReplaceable())
					{
						return false;
					}
				}

			}

		}
		return true;
	}

	public boolean isOkayPortal(int rx, int rz, int ry) {
		for(int potentialZ = 0; potentialZ < 4; potentialZ++)
		{
			for(int potentialX = 0; potentialX < 4; potentialX++)
			{
				for(int potentialY = -1; potentialY < 3; potentialY++)
				{
					int tx = rx + (potentialX - 1);
					int ty = ry + potentialY;
					int tz = rz + (potentialZ - 1);
					if (potentialY == -1 && !myWorld.getBlock(tx, ty, tz).getMaterial().isSolid() || potentialY >= 0 && !myWorld.getBlock(tx, ty, tz).getMaterial().isReplaceable())
					{
						return false;
					}
				}

			}

		}
		return true;
	}

	private void makePortalAt(World world, int px, int py, int pz) {

		if(py < 30)
		{
			py = 30;
		}
		world.getClass();
		if(py > 128 - 10)
		{
			world.getClass();
			py = 128 - 10;
		}
		
		// sink the portal 1 into the ground
		py--;

		
		// grass all around it
		world.setBlock(px - 1, py + 0, pz - 1, Blocks.GRASS);
		world.setBlock(px + 0, py + 0, pz - 1, Blocks.GRASS);
		world.setBlock(px + 1, py + 0, pz - 1, Blocks.GRASS);
		world.setBlock(px + 2, py + 0, pz - 1, Blocks.GRASS);

		world.setBlock(px - 1, py + 0, pz + 0, Blocks.GRASS);
		world.setBlock(px + 2, py + 0, pz + 0, Blocks.GRASS);

		world.setBlock(px - 1, py + 0, pz + 1, Blocks.GRASS);
		world.setBlock(px + 2, py + 0, pz + 1, Blocks.GRASS);

		world.setBlock(px - 1, py + 0, pz + 2, Blocks.GRASS);
		world.setBlock(px + 0, py + 0, pz + 2, Blocks.GRASS);
		world.setBlock(px + 1, py + 0, pz + 2, Blocks.GRASS);
		world.setBlock(px + 2, py + 0, pz + 2, Blocks.GRASS);

		// dirt under it
		world.setBlock(px + 0, py - 1, pz + 0, Blocks.DIRT);
		world.setBlock(px + 1, py - 1, pz + 0, Blocks.DIRT);
		world.setBlock(px + 0, py - 1, pz + 1, Blocks.DIRT);
		world.setBlock(px + 1, py - 1, pz + 1, Blocks.DIRT);
		
		// portal in it
		world.setBlock(px + 0, py + 0, pz + 0, TFBlocks.portal, 0, 2);
		world.setBlock(px + 1, py + 0, pz + 0, TFBlocks.portal, 0, 2);
		world.setBlock(px + 0, py + 0, pz + 1, TFBlocks.portal, 0, 2);
		world.setBlock(px + 1, py + 0, pz + 1, TFBlocks.portal, 0, 2);
		
		// meh, let's just make a bunch of air over it for 4 squares
		for (int dx = -1; dx <= 2; dx++) {
			for (int dz = -1; dz <= 2; dz++) {
				for (int dy = 1; dy <=5; dy++) {
					world.setBlock(px + dx, py + dy, pz + dz, Blocks.AIR);
				}
			}
		}

		// finally, "nature decorations"!
		world.setBlock(px - 1, py + 1, pz - 1, randNatureBlock(world.rand), 0, 2);
		world.setBlock(px + 0, py + 1, pz - 1, randNatureBlock(world.rand), 0, 2);
		world.setBlock(px + 1, py + 1, pz - 1, randNatureBlock(world.rand), 0, 2);
		world.setBlock(px + 2, py + 1, pz - 1, randNatureBlock(world.rand), 0, 2);

		world.setBlock(px - 1, py + 1, pz + 0, randNatureBlock(world.rand), 0, 2);
		world.setBlock(px + 2, py + 1, pz + 0, randNatureBlock(world.rand), 0, 2);

		world.setBlock(px - 1, py + 1, pz + 1, randNatureBlock(world.rand), 0, 2);
		world.setBlock(px + 2, py + 1, pz + 1, randNatureBlock(world.rand), 0, 2);

		world.setBlock(px - 1, py + 1, pz + 2, randNatureBlock(world.rand), 0, 2);
		world.setBlock(px + 0, py + 1, pz + 2, randNatureBlock(world.rand), 0, 2);
		world.setBlock(px + 1, py + 1, pz + 2, randNatureBlock(world.rand), 0, 2);
		world.setBlock(px + 2, py + 1, pz + 2, randNatureBlock(world.rand), 0, 2);
	}
	
	public Block randNatureBlock(Random random) {
		Block[] block = {Blocks.BROWN_MUSHROOM, Blocks.RED_MUSHROOM, Blocks.TALLGRASS, Blocks.RED_FLOWER, Blocks.YELLOW_FLOWER};
		
		return block[random.nextInt(block.length)];
	}

}
