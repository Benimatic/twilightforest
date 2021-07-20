package twilightforest.entity.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import twilightforest.entity.BlockChainGoblinEntity;
import twilightforest.entity.SpikeBlockEntity;

import java.util.EnumSet;

public class ThrowSpikeBlockGoal extends Goal {

	protected BlockChainGoblinEntity attacker;
	protected SpikeBlockEntity spikeBlock;

	public ThrowSpikeBlockGoal(BlockChainGoblinEntity entityTFBlockGoblin, SpikeBlockEntity entitySpikeBlock) {
		this.attacker = entityTFBlockGoblin;
		this.spikeBlock = entitySpikeBlock;
		this.setMutexFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
	}

	@Override
	public boolean shouldExecute() {
		LivingEntity target = this.attacker.getAttackTarget();
		if (target == null || this.attacker.getDistanceSq(target) > 42) {
			return false;
		} else {
			return this.attacker.isAlive() && this.attacker.canEntityBeSeen(target) && this.attacker.world.rand.nextInt(56) == 0;
		}
	}

	@Override
	public boolean shouldContinueExecuting() {
		return this.attacker.getChainMoveLength() > 0;
	}

	@Override
	public void startExecuting() {
		this.attacker.setThrowing(true);
	}
}
