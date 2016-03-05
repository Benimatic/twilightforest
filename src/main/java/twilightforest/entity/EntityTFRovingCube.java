package twilightforest.entity;

import twilightforest.TwilightForestMod;
import twilightforest.entity.ai.EntityAICubeCenterOnSymbol;
import twilightforest.entity.ai.EntityAICubeMoveToRedstoneSymbols;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;

public class EntityTFRovingCube  extends EntityMob {
	
	// data needed for cube AI

	// last circle visited
	public boolean hasFoundSymbol = false;
	public int symbolX = 0;
	public int symbolY = 0;
	public int symbolZ = 0;

	// direction traveling

	// blocks traveled

	public EntityTFRovingCube(World world) {
		super(world);
        setSize(1.2F, 2.1F);
        
        this.tasks.addTask(0, new EntityAICubeMoveToRedstoneSymbols(this, 1.0D));
        this.tasks.addTask(1, new EntityAICubeCenterOnSymbol(this, 1.0D));

	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5.0D);
	}


	/**
	 * Returns true if the newer Entity AI code should be run
	 */
	protected boolean isAIEnabled()
	{
		return true;
	}

    
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
    	super.onLivingUpdate();
    	// make annihilation particles
    	for (int i = 0; i < 3; i++) {
	    	float px = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.75F;
	    	float py = this.getEyeHeight() - 0.25F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.75F;
	    	float pz = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.75F;
	    	
			TwilightForestMod.proxy.spawnParticle(this.worldObj, "annihilate", this.lastTickPosX + px, this.lastTickPosY + py, this.lastTickPosZ + pz, 0, 0, 0);
    	}

    }


}
