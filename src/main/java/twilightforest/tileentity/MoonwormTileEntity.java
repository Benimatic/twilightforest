package twilightforest.tileentity;

import net.minecraft.world.level.block.entity.TickableBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;

public class MoonwormTileEntity extends BlockEntity implements TickableBlockEntity {
	public int yawDelay;
	public int currentYaw;
	public int desiredYaw;

	public MoonwormTileEntity() {
		super(TFTileEntities.MOONWORM.get());
		currentYaw = -1;
		yawDelay = 0;
		desiredYaw = 0;
	}

	@Override
	public void tick() {
		if (level.isClientSide) {
			if (currentYaw == -1) {
				currentYaw = level.random.nextInt(4) * 90;
			}

			if (yawDelay > 0) {
				yawDelay--;
			} else {
				if (desiredYaw == 0) {
					// make it rotate!
					yawDelay = 200 + level.random.nextInt(200);
					desiredYaw = level.random.nextInt(4) * 90;
				}

				currentYaw++;

				if (currentYaw > 360) {
					currentYaw = 0;
				}

				if (currentYaw == desiredYaw) {
					desiredYaw = 0;
				}
			}
		}
	}
}
