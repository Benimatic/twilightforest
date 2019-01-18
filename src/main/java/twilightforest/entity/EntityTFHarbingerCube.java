package twilightforest.entity;

import twilightforest.entity.ai.EntityAITFGiantAttackOnCollide;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;


public class EntityTFHarbingerCube extends EntityMob {

	public EntityTFHarbingerCube(World world) {
		super(world);
		
        this.setSize(1.9F, 2.4F);
        
		this.tasks.addTask(1, new EntityAISwimming(this));

		this.tasks.addTask(1, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(2, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));

	}
	
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D*1.5D+twilightforest.TwilightForestMod.Scatter.nextInt(20)-twilightforest.TwilightForestMod.Scatter.nextInt(20));
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23D*1.5D);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D*1.5);
	}


    /**
     * Returns true if the newer Entity AI code should be run
     */
    @Override
	protected boolean isAIEnabled() {
        return true;
    }
}
