package twilightforest.tileentity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.world.EnumDifficulty;


public abstract class TileEntityTFBossSpawner extends TileEntity implements ITickable {
	
    protected String mobID = "Pig";
    protected int counter;

    protected Entity displayCreature = null;

    
	public TileEntityTFBossSpawner() {
		;
	}
	
	public boolean anyPlayerInRange()
    {
        return worldObj.getClosestPlayer(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, getRange(), false) != null;
    }

	@Override
	public void update()
    {
		this.counter++;

		if(anyPlayerInRange())
		{
			if (worldObj.isRemote)
			{
				// particles
				double rx = pos.getX() + worldObj.rand.nextFloat();
				double ry = pos.getY() + worldObj.rand.nextFloat();
				double rz = pos.getZ() + worldObj.rand.nextFloat();
				worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, rx, ry, rz, 0.0D, 0.0D, 0.0D);
				worldObj.spawnParticle(EnumParticleTypes.FLAME, rx, ry, rz, 0.0D, 0.0D, 0.0D);
			}
			else
			{
				//System.out.println("Thinking about boss!");

				
				if (worldObj.getDifficulty() != EnumDifficulty.PEACEFUL)
				{
					spawnMyBoss();

					// destroy block
					worldObj.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
					
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

		double rx = pos.getX() + 0.5D;
		double ry = pos.getY() + 0.5D;
		double rz = pos.getZ() + 0.5D;
		myCreature.setLocationAndAngles(rx, ry, rz, worldObj.rand.nextFloat() * 360F, 0.0F);

		// set creature's home to this
		initializeCreature(myCreature);

		// spawn it
		worldObj.spawnEntity(myCreature);
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
			((EntityCreature) myCreature).setHomePosAndDistance(pos, 46);
		}
	}

	protected int getRange() {
		return 50;
	}

	protected EntityLiving makeMyCreature() {
		return (EntityLiving)EntityList.createEntityByName(mobID, worldObj);
	}
}
