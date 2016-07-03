package twilightforest.entity.boss;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.inventory.EntityEquipmentSlot;
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
        
        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(TFItems.minotaurAxe));
        this.setDropChance(EntityEquipmentSlot.MAINHAND, 1.1F); // > 1 means it is not randomly damaged when dropped
	}

	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(120.0D);
    }

    @Override
	protected Item getDropItem()
    {
        return TFItems.meefStroganoff;
    }

    @Override
	protected void dropFewItems(boolean par1, int par2)
    {
        int numDrops = this.rand.nextInt(4) + 2 + this.rand.nextInt(1 + par2);

        for (int i = 0; i < numDrops; ++i)
        {
            this.dropItem(TFItems.meefStroganoff, 1);
        }
    }
    
    @Override
	protected boolean canDespawn()
    {
        return false;
    }

}
