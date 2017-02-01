package twilightforest.entity.passive;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import twilightforest.TFAchievementPage;
import twilightforest.item.TFItems;

import javax.annotation.Nullable;


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


    @Override
    protected SoundEvent getAmbientSound()
    {
    	return null;
    }

    @Override
	protected void playStepSound(BlockPos pos, Block par4) {}
    
    /**
     * Not milkable
     */
    @Override
    public boolean processInteract(EntityPlayer entityplayer, EnumHand hand, @Nullable ItemStack stack)
    {
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if(itemstack != null && itemstack.getItem() == Items.BUCKET)
        {
        	// specifically do not respond to this
            return false;
        } else
        {
            return super.processInteract(entityplayer, hand, stack);
        }
    }

    @Override
    protected void dropFewItems(boolean par1, int par2)
    {
        // todo 1.9 this is hidden by loot tables
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

    @Override
	public EntityCow createChild(EntityAgeable entityanimal)
    {
        return new EntityTFDeer(world);
    }

    @Override
	public void onDeath(DamageSource par1DamageSource) {
		super.onDeath(par1DamageSource);
		if (par1DamageSource.getSourceOfDamage() instanceof EntityPlayer) {
			((EntityPlayer)par1DamageSource.getSourceOfDamage()).addStat(TFAchievementPage.twilightHunter);
		}
	}

}
