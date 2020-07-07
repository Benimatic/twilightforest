package twilightforest.entity.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.ForgeEventFactory;
import twilightforest.entity.EntityTFRedcap;

public class EntityAITFRedcapPlantTNT extends EntityAITFRedcapBase {

	public EntityAITFRedcapPlantTNT(EntityTFRedcap entityTFRedcap) {
		super(entityTFRedcap);
	}

	@Override
	public boolean shouldExecute() {
		LivingEntity attackTarget = this.redcap.getAttackTarget();
		return attackTarget != null
				&& !redcap.heldTNT.isEmpty()
				&& redcap.getDistanceSq(attackTarget) < 25
				&& !isTargetLookingAtMe(attackTarget)
				&& ForgeEventFactory.getMobGriefingEvent(redcap.world, redcap)
				&& !isLitTNTNearby(8)
				&& findBlockTNTNearby(5) == null;
	}

	@Override
	public void startExecuting() {
		BlockPos entityPos = new BlockPos(redcap.func_233580_cy_());

		this.redcap.setItemStackToSlot(EquipmentSlotType.MAINHAND, redcap.heldTNT);

		if (this.redcap.world.isAirBlock(entityPos)) {
			redcap.heldTNT.shrink(1);
			redcap.playAmbientSound();
			redcap.world.setBlockState(entityPos, Blocks.TNT.getDefaultState());
		}
	}

	@Override
	public void resetTask() {
		this.redcap.setItemStackToSlot(EquipmentSlotType.MAINHAND, redcap.heldPick);
	}
}
