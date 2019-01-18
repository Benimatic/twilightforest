package twilightforest.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import twilightforest.entity.TFCreatures;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityTFLichSpawner extends TileEntityTFBossSpawner {

	public TileEntityTFLichSpawner() {
		this.mobID = TFCreatures.getSpawnerNameFor("Twilight Lich");
	}
    protected AxisAlignedBB aabb;

    @Override
    public void validate() {
    	aabb = AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
    	return aabb;
    }
	
    @Override
	public boolean anyPlayerInRange()
    {
    	EntityPlayer closestPlayer = worldObj.getClosestPlayer(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, 9D);
    	
        return closestPlayer != null && closestPlayer.posY > yCoord - 4;
    }
	
}
