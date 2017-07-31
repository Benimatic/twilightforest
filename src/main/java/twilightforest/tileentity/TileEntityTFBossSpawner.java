package twilightforest.tileentity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.util.AxisAlignedBB;


public abstract class TileEntityTFBossSpawner extends TileEntity {
	
    protected String mobID = "Pig";
    protected int counter;

    protected Entity displayCreature = null;
    protected AxisAlignedBB aabb;

    
	public TileEntityTFBossSpawner() {
		;
	}

    @Override
    public void validate() {
    	aabb = AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
    	return aabb;
    }
	
	/**
	 * Is there a player in our detection range?
	 */
	public boolean anyPlayerInRange()
    {
        return worldObj.getClosestPlayer(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, getRange()) != null;
    }
	
    /**
     * Determines if this TileEntity requires update calls.
     * @return True if you want updateEntity() to be called, false if not
     */
	@Override
	public boolean canUpdate() {
		return true;
	}
	
    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
	@Override
	public void updateEntity()
    {
		this.counter++;

		if(anyPlayerInRange())
		{
			if (worldObj.isRemote)
			{
				// particles
				double rx = xCoord + worldObj.rand.nextFloat();
				double ry = yCoord + worldObj.rand.nextFloat();
				double rz = zCoord + worldObj.rand.nextFloat();
				worldObj.spawnParticle("smoke", rx, ry, rz, 0.0D, 0.0D, 0.0D);
				worldObj.spawnParticle("flame", rx, ry, rz, 0.0D, 0.0D, 0.0D);
			}
			else
			{
				//System.out.println("Thinking about boss!");

				
				if (worldObj.difficultySetting != EnumDifficulty.PEACEFUL) 
				{
					spawnMyBoss();

					// destroy block
					worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.air, 0, 2);
					
					//System.out.println("Spawning boss!");
				}
			}
		}

    }

	/**
	 * Spawn the boss
	 */
	protected void spawnMyBoss() {
		// spawn creature
		EntityLiving myCreature = makeMyCreature();

		double rx = xCoord + 0.5D;
		double ry = yCoord + 0.5D;
		double rz = zCoord + 0.5D;
		myCreature.setLocationAndAngles(rx, ry, rz, worldObj.rand.nextFloat() * 360F, 0.0F);

		// set creature's home to this
		initializeCreature(myCreature);

		// spawn it
		worldObj.spawnEntityInWorld(myCreature);
	}
	
	/**
	 * Get a temporary copy of the creature we're going to summon for display purposes
	 */
	public Entity getDisplayEntity()
	{
		if (this.displayCreature == null)
		{
			this.displayCreature = makeMyCreature();
		}
		
		return this.displayCreature;
	}

	/**
	 * Any post-creation initialization goes here
	 */
	protected void initializeCreature(EntityLiving myCreature) {
		if (myCreature instanceof EntityCreature)
		{
			((EntityCreature) myCreature).setHomeArea(xCoord, yCoord, zCoord, 46);
		}
	}

	/**
	 * Range?
	 */
	protected int getRange() {
		return 50;
	}

	/**
	 * Create a copy of what we spawn
	 */
	protected EntityLiving makeMyCreature() {
		return (EntityLiving)EntityList.createEntityByName(mobID, worldObj);
	}
}
