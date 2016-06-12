package twilightforest.entity;

import twilightforest.item.TFItems;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityTFArmoredGiant extends EntityTFGiantMiner {

	public EntityTFArmoredGiant(World par1World) {
		super(par1World);
		
        this.setCurrentItemOrArmor(0, new ItemStack(Items.STONE_SWORD));
        this.setCurrentItemOrArmor(1, new ItemStack(Items.IRON_HELMET));
        this.setCurrentItemOrArmor(2, new ItemStack(Items.IRON_CHESTPLATE));
        this.setCurrentItemOrArmor(3, new ItemStack(Items.IRON_LEGGINGS));
        this.setCurrentItemOrArmor(4, new ItemStack(Items.IRON_BOOTS));

	}

    protected Item getDropItem()
    {
        return TFItems.giantSword;
    }
}
