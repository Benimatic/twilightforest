package twilightforest.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.world.World;

public class EntityIceArrow extends EntityArrow {

	public EntityIceArrow(World par1World) {
		super(par1World);
	}

	public EntityIceArrow(World world, EntityPlayer player, float velocity) {
		super(world, player, velocity);
	}

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate() {
        super.onUpdate();
    }
}
