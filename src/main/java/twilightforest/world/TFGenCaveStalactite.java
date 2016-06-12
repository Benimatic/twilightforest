package twilightforest.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;



public class TFGenCaveStalactite extends TFGenerator {

	
	public static TFGenCaveStalactite diamond = new TFGenCaveStalactite(Blocks.DIAMOND_ORE, 0.5F, 4, 16);
	public static TFGenCaveStalactite lapis = new TFGenCaveStalactite(Blocks.LAPIS_ORE, 0.8F, 8, 1);
	public static TFGenCaveStalactite emerald = new TFGenCaveStalactite(Blocks.EMERALD_ORE, 0.5F, 3, 12);
	public static TFGenCaveStalactite gold = new TFGenCaveStalactite(Blocks.GOLD_ORE, 0.6F, 6, 1);
	public static TFGenCaveStalactite redstone = new TFGenCaveStalactite(Blocks.REDSTONE_ORE, 0.8F, 8, 1);
	public static TFGenCaveStalactite iron = new TFGenCaveStalactite(Blocks.IRON_ORE, 0.7F, 8, 1);
	public static TFGenCaveStalactite coal = new TFGenCaveStalactite(Blocks.COAL_ORE, 0.8F, 12, 1);
	public static TFGenCaveStalactite glowstone = new TFGenCaveStalactite(Blocks.GLOWSTONE, 0.5F, 8, 1);
	
	
	public Block blockID;
	public boolean hang;
	public float sizeFactor;
	public int maxLength;
	public int minHeight;

	/**
	 * Initializes a stalactite builder.  Actually also makes stalagmites
	 * 
	 * @param size
	 * @param stone
	 */
	public TFGenCaveStalactite(Block blockType, float size, boolean down)
	{
		this.blockID = blockType;
		this.sizeFactor = size;
		this.maxLength = -1;
		this.minHeight = -1;
		this.hang = down;
	}
	
	/**
	 * Initializes a stalactite builder
	 */
	public TFGenCaveStalactite(Block blockType, float size, int maxLength, int minHeight)
	{
		this.blockID = blockType;
		this.sizeFactor = size;
		this.maxLength = maxLength;
		this.minHeight = minHeight;
		this.hang = true;
	}
	
	/**
	 * Makes a random stalactite appropriate to the cave size
	 * 
	 * All include iron, coal and glowstone.
	 * 
	 * Gold and redstone appears in size 2 and larger caves.
	 * 
	 * Diamonds and lapis only appear in size 3 and larger caves.
	 */
	public static TFGenCaveStalactite makeRandomOreStalactite(Random rand, int hillSize)
	{
		if (hillSize >= 3 || (hillSize >= 2 && rand.nextInt(5) == 0))
		{
			int s3 = rand.nextInt(13);
			if (s3 == 0 || s3 == 1)
			{
				return diamond;
			}
			else if (s3 == 2 || s3 == 3)
			{
				return lapis;
			}
			else if (s3 == 4)
			{
				return emerald;
			}
		}
		if (hillSize >= 2 || (hillSize >= 1 && rand.nextInt(5) == 0))
		{
			int s2 = rand.nextInt(6);
			if (s2 == 0)
			{
				return gold;
			}
			else if (s2 == 1 || s2 == 2)
			{
				return redstone;
			}
		}
		
		// fall through to size 1
		int s1 = rand.nextInt(5);
		if (s1 == 0 || s1 == 1)
		{
			return iron;
		}
		else if (s1 == 2 || s1 == 3)
		{
			return coal;
		}
		else 
		{
			return glowstone;
		}
	}

	/**
	 * Generates a stalactite at the specified location. 
	 * The coordinates should be inside a cave.
	 * This will return false if it can't find a valid ceiling and floor, or if there are other errors.
	 */
	public boolean generate(World world, Random random, int x, int y, int z)
	{
		int ceiling = Integer.MAX_VALUE;
		int floor = -1;
		
		// find a ceiling
        for (int ty = y; ty < TFWorld.CHUNKHEIGHT; ty++)
        {
        	Material m = world.getBlock(x, ty, z).getMaterial();
        	// if we're in air, continue
        	if (m == Material.air)
        	{
        		continue;
        	}
        	// if we get something that's not cave material, fail!
        	if (m != Material.ground && m != Material.rock)
        	{
        		return false;
        	}
        	// okay, we found a valid ceiling.
        	ceiling = ty;
        	break;
        }
        // if we didn't find a ceiling, fail.
        if (ceiling == Integer.MAX_VALUE)
        {
        	return false;
        }
        
        // find a floor
        for (int ty = y; ty > 4; ty--)
        {
        	Material m = world.getBlock(x, ty, z).getMaterial();
        	// if we're in air, continue
        	if (m == Material.air)
        	{
        		continue;
        	}
        	// if we get something that's not cave material, fail!
        	// actually stalactites can hang above water or lava
        	if (m != Material.ground && m != Material.rock && (!hang && m != Material.water) && (!hang && m != Material.lava) )
        	{
        		return false;
        	}
        	// okay, we found a valid floor.
        	floor = ty;
        	break;
        }
		
        int length = (int)((ceiling - floor) * this.sizeFactor * random.nextFloat());
        
        // check max length
        if (this.maxLength > -1 && length > this.maxLength)
        {
        	length = this.maxLength;
        }
        
        // check minimum height
        if (this.minHeight > -1 && ceiling - floor - length < this.minHeight)
        {
        	return false;
        }
		
		return makeSpike(world, random, x, hang ? ceiling : floor, z, length);
	}
		

	public boolean makeSpike(World world, Random random, int x, int y, int z, int maxLength)
	{
		
		int diameter = (int)(maxLength / 4.5); // diameter of the base
		
		// let's see...
		for (int dx = -diameter; dx <= diameter; dx++)
		{
			for (int dz = -diameter; dz <= diameter; dz++)
			{
				// determine how long this spike will be.
				int absx = Math.abs(dx);
				int absz = Math.abs(dz);
				int dist = (int)(Math.max(absx, absz) + (Math.min(absx, absz) * 0.5));
				int spikeLength = 0;

				if (dist == 0)
				{
					spikeLength = maxLength;
				}
				
				if (dist > 0)
				{
					spikeLength = random.nextInt((int)(maxLength / (dist + 0.25)));
				}
				
				int dir = hang ? -1 : 1;
				
				// check if we're generating over anything
				if (!world.getBlock(x + dx, y - dir, z + dz).getMaterial().isSolid())
				{
					spikeLength = 0;
				}
				
				for (int dy = 0; dy != (spikeLength * dir); dy += dir)
				{
					setBlock(world, x + dx, y + dy, z + dz, blockID);
				}
			}
		}

		return true;
	}

}
