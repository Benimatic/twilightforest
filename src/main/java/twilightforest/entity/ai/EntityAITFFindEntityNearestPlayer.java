package twilightforest.entity.ai;

import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.ai.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.player.PlayerEntity;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * Customized AI that checks more that 4 blocks up/down and also ignores sight
 */
public class EntityAITFFindEntityNearestPlayer extends EntityAIFindEntityNearestPlayer
{
	private final MobEntity entityLiving;
	private final Predicate<Entity> predicate;
	private final NearestAttackableTargetGoal.Sorter sorter;
	private LivingEntity entityTarget;

	/**
	 * VanillaCopy super, but change predicate to not check sight, or bother reducing range for sneaking/invisibility
	 */
	public EntityAITFFindEntityNearestPlayer(MobEntity entityLivingIn)
	{
		super(entityLivingIn);
		this.entityLiving = entityLivingIn;
		this.predicate = new Predicate<Entity>()
		{
			@Override
			public boolean apply(@Nullable Entity entity)
			{
				if (!(entity instanceof PlayerEntity))
				{
					return false;
				}
				else if (((PlayerEntity)entity).capabilities.disableDamage)
				{
					return false;
				}
				else
				{
					double maxRange = EntityAITFFindEntityNearestPlayer.this.maxTargetRange();

					return (double)entity.getDistance(EntityAITFFindEntityNearestPlayer.this.entityLiving) > maxRange ? false : TargetGoal.isSuitableTarget(EntityAITFFindEntityNearestPlayer.this.entityLiving, (LivingEntity)entity, false, false);
				}
			}
		};

		this.sorter = new NearestAttackableTargetGoal.Sorter(entityLivingIn);
	}

	/**
	 * VanillaCopy super, but change bounding box y expansion from 4 to full range
	 */
	@Override
	public boolean shouldExecute()
	{
		double maxRange = this.maxTargetRange();
		List<PlayerEntity> list = this.entityLiving.world.<PlayerEntity>getEntitiesWithinAABB(PlayerEntity.class, this.entityLiving.getBoundingBox().grow(maxRange), this.predicate);
		Collections.sort(list, this.sorter);

		if (list.isEmpty())
		{
			return false;
		}
		else
		{
			this.entityTarget = list.get(0);
			return true;
		}
	}

	/**
	 * Use our target instead of super's.
	 */
	@Override
	public void startExecuting()
	{
		this.entityLiving.setAttackTarget(this.entityTarget);
	}

	@Override
	public void resetTask()
	{
		this.entityTarget = null;
		super.resetTask();
	}
}
