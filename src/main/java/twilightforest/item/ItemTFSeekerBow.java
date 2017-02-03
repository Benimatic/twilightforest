package twilightforest.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;
import twilightforest.entity.EntitySeekerArrow;

public class ItemTFSeekerBow extends ItemTFBowBase {
	
    
    public ItemTFSeekerBow() {
		this.setCreativeTab(TFItems.creativeTab);
    }

    @Override
	protected EntityArrow getArrow(World world, EntityPlayer entityPlayer) {
		return new EntitySeekerArrow(world, entityPlayer);
	}

}
