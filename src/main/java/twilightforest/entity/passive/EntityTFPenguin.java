package twilightforest.entity.passive;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import twilightforest.TFAchievementPage;


public class EntityTFPenguin extends EntityTFBird {

	public EntityTFPenguin(World world) {
		super(world);
		//texture = TwilightForestMod.MODEL_DIR + "penguin.png";
		
        this.setSize(0.5F, 0.9F);
		
		// reset tasks for fish
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(1, new EntityAIPanic(this, 1.75F));
        tasks.addTask(2, new EntityAIMate(this, 1.0F));
        tasks.addTask(3, new EntityAITempt(this, 0.75F, Items.fish, false));
        tasks.addTask(4, new EntityAIFollowParent(this, 1.15F));
        tasks.addTask(5, new EntityAIWander(this, 1.0F));
        tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6F));
        tasks.addTask(7, new EntityAIWatchClosest2(this, twilightforest.entity.passive.EntityTFPenguin.class, 5F, 0.02F));
        tasks.addTask(8, new EntityAILookIdle(this));

	}
	
    /**
     * Returns the sound this mob makes while it's alive.
     */
	@Override
    protected String getLivingSound()
    {
        return null;//"mob.chicken";
    }
    
    
    /**
     * [This function is used when two same-species animals in 'love mode' breed to generate the new baby animal.]
     */
	@Override
	public EntityAnimal createChild(EntityAgeable entityanimal)
    {
        return new EntityTFPenguin(worldObj);
    }
	
    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
	@Override
    public boolean isBreedingItem(ItemStack par1ItemStack)
    {
        return par1ItemStack != null && par1ItemStack.getItem() == Items.fish;
    }


    /**
     * Trigger achievement when killed
     */
	@Override
	public void onDeath(DamageSource par1DamageSource) {
		super.onDeath(par1DamageSource);
		if (par1DamageSource.getSourceOfDamage() instanceof EntityPlayer) {
			((EntityPlayer)par1DamageSource.getSourceOfDamage()).triggerAchievement(TFAchievementPage.twilightHunter);
		}
	}

	/**
	 * Set monster attributes
	 */
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D+twilightforest.TwilightForestMod.Scatter.nextInt(10)-twilightforest.TwilightForestMod.Scatter.nextInt(10)); // max health
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2D);
    }
}
