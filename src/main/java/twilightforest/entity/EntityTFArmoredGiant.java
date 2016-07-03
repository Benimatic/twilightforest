package twilightforest.entity;

import net.minecraft.inventory.EntityEquipmentSlot;
import twilightforest.item.TFItems;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityTFArmoredGiant extends EntityTFGiantMiner {

	public EntityTFArmoredGiant(World par1World) {
		super(par1World);
		
        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SWORD));
        this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
        this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
        this.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(Items.IRON_LEGGINGS));
        this.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(Items.IRON_BOOTS));

	}

    @Override
    protected Item getDropItem()
    {
        return TFItems.giantSword;
    }
}
