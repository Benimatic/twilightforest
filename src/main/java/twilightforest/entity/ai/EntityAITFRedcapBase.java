package twilightforest.entity.ai;

import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import twilightforest.entity.EntityTFRedcap;

public abstract class EntityAITFRedcapBase extends Goal {

	protected final EntityTFRedcap redcap;

	protected EntityAITFRedcapBase(EntityTFRedcap entity) {
		this.redcap = entity;
	}

	/**
	 * Fairly straightforward.  Returns true in a 120 degree arc in front of the target's view.
	 */
	public boolean isTargetLookingAtMe(LivingEntity attackTarget) {
		// find angle of approach
		double dx = redcap.getPosX() - attackTarget.getPosX();
		double dz = redcap.getPosZ() - attackTarget.getPosZ();
		float angle = (float) ((Math.atan2(dz, dx) * 180D) / Math.PI) - 90F;

		float difference = MathHelper.abs((attackTarget.rotationYaw - angle) % 360);

		return difference < 60 || difference > 300;
	}

	public BlockPos findBlockTNTNearby(int range) {
		BlockPos entityPos = new BlockPos(redcap.func_233580_cy_());

		for (int x = -range; x <= range; x++) {
			for (int y = -range; y <= range; y++) {
				for (int z = -range; z <= range; z++) {
					if (redcap.world.getBlockState(entityPos.add(x, y, z)).getBlock() == Blocks.TNT) {
						return entityPos.add(x, y, z);
					}
				}
			}
		}

		return null;
	}

	public boolean isLitTNTNearby(int range) {
		AxisAlignedBB expandedBox = redcap.getBoundingBox().grow(range, range, range);
		return !redcap.world.getEntitiesWithinAABB(TNTEntity.class, expandedBox).isEmpty();
	}
}
