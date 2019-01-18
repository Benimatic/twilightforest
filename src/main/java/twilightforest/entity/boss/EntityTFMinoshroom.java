package twilightforest.entity.boss;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import twilightforest.entity.EntityTFMinotaur;
import twilightforest.item.TFItems;

public class EntityTFMinoshroom extends EntityTFMinotaur {
	
	public EntityTFMinoshroom(World par1World) {
		super(par1World);
		//this.texture = TwilightForestMod.MODEL_DIR + "minoshroomtaur.png";
		this.setSize(1.49F, 2.9F);
		
        this.experienceValue = 100;
        
        this.setCurrentItemOrArmor(0, new ItemStack(TFItems.minotaurAxe));
        this.equipmentDropChances[0] = 0.0F;
	}

	/**
	 * Set monster attributes
	 */
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(120.0D*1.5F+twilightforest.TwilightForestMod.Scatter.nextInt(60)-twilightforest.TwilightForestMod.Scatter.nextInt(60)); // max health
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    @Override
	protected Item getDropItem()
    {
        return TFItems.meefStroganoff;
    }

    /**
     * Drop 0-2 items of this living's type
     */
    @Override
	protected void dropFewItems(boolean par1, int par2)
    {
        int numDrops = this.rand.nextInt(4) + 2 + this.rand.nextInt(1 + par2);

        for (int i = 0; i < numDrops; ++i)
        {
            this.dropItem(TFItems.meefStroganoff, 1);
        }
    }
    
    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    @Override
	protected boolean canDespawn()
    {
        return false;
    }
    
    /**
     * Drop the equipment for this entity.
     */
    protected void dropEquipment(boolean par1, int par2)
    {
        super.dropEquipment(par1, par2);
        this.entityDropItem(new ItemStack(TFItems.minotaurAxe), 0.0F);

    }

    
}
