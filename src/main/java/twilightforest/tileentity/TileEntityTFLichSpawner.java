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
    	EntityPlayer closestPlayer = worldObj.getClosestPlayer(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, 9D);
    	
        return closestPlayer != null && closestPlayer.posY > yCoord - 4;
    }
	
}
