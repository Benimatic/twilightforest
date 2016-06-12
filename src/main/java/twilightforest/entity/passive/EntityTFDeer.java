package twilightforest.entity.passive;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import twilightforest.TFAchievementPage;
import twilightforest.item.TFItems;


/**
 * Deer are like quiet, non-milkable cows!
 * 
 * Also they look like deer
 * 
 * @author Ben
 *
 */
public class EntityTFDeer extends EntityCow
{

    public EntityTFDeer(World world)
    {
        super(world);
        //texture = TwilightForestMod.MODEL_DIR + "wilddeer.png";
        setSize(0.7F, 2.3F);
        
//        this.tasks.taskEntries.clear();
//        
//        this.tasks.addTask(0, new EntityAISwimming(this));
//        this.tasks.addTask(1, new EntityAITFPanicOnFlockDeath(this, 0.38F));
//        this.tasks.addTask(2, new EntityAIMate(this, 0.2F));
//        this.tasks.addTask(3, new EntityAITempt(this, 0.25F, Items.WHEAT, false));
//        this.tasks.addTask(4, new EntityAIFollowParent(this, 0.25F));
//        this.tasks.addTask(5, new EntityAIWander(this, 0.2F));
//        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
//        this.tasks.addTask(7, new EntityAILookIdle(this));
    }
    
    public EntityTFDeer(World world, double x, double y, double z)
    {
        this(world);
        this.setPosition(x, y, z);
    }


    /**
     * No sounds when idle
     */
    @Override
    protected String getLivingSound()
    {
    	return null;
    }

    /**
     * Plays step sound at given x, y, z for the entity
     */
    @Override
	protected void func_145780_a(int par1, int par2, int par3, Block par4)
    {
        //this.worldObj.playSoundAtEntity(this, "mob.cow.step", 0.15F, 1.0F);
    }
    
    /**
     * Not milkable
     */
    @Override
    public boolean interact(EntityPlayer entityplayer)
    {
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if(itemstack != null && itemstack.getItem() == Items.BUCKET)
        {
        	// specifically do not respond to this
            return false;
        } else
        {
            return super.interact(entityplayer);
        }
    }
    

    /**
     * Drop 0-2 items of this living's type
     */
    @Override
    protected void dropFewItems(boolean par1, int par2)
    {
        int var3 = this.rand.nextInt(3) + this.rand.nextInt(1 + par2);
        int var4;

        for (var4 = 0; var4 < var3; ++var4)
        {
            this.dropItem(Items.LEATHER, 1);
        }

        var3 = this.rand.nextInt(3) + 1 + this.rand.nextInt(1 + par2);

        for (var4 = 0; var4 < var3; ++var4)
        {
            if (this.isBurning())
            {
                this.dropItem(TFItems.venisonCooked, 1);
            }
            else
            {
                this.dropItem(TFItems.venisonRaw, 1);
            }
        }
    }

    
    /**
     * What is our baby going to be?  Another deer?!
     */
    @Override
	public EntityCow createChild(EntityAgeable entityanimal)
    {
        return new EntityTFDeer(worldObj);
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

}
