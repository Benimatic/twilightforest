package twilightforest.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import twilightforest.entity.TFCreatures;

public class TileEntityTFTowerBossSpawner extends TileEntityTFBossSpawner {

	public TileEntityTFTowerBossSpawner() {
		this.mobID = TFCreatures.getSpawnerNameFor("Tower Boss");
	}
	
    @Override
	public boolean anyPlayerInRange()
    {
    	EntityPlayer closestPlayer = world.getClosestPlayer(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 9D, false);
    	
        return closestPlayer != null && closestPlayer.posY > pos.getY() - 4;
    }
	
}
