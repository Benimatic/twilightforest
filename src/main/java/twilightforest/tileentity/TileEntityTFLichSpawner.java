package twilightforest.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import twilightforest.entity.TFCreatures;

public class TileEntityTFLichSpawner extends TileEntityTFBossSpawner {

	public TileEntityTFLichSpawner() {
		this.mobID = TFCreatures.getSpawnerNameFor("Twilight Lich");
	}
	
    @Override
	public boolean anyPlayerInRange()
    {
    	EntityPlayer closestPlayer = worldObj.getClosestPlayer(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 9D, false);
    	
        return closestPlayer != null && closestPlayer.posY > pos.getY() - 4;
    }
	
}
