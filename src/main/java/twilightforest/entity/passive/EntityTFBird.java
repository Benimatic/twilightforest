package twilightforest.entity.passive;

import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public abstract class EntityTFBird extends AnimalEntity {

	protected static final Ingredient SEEDS = Ingredient.fromItems(Items.WHEAT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS);

	public float flapLength = 0.0F;
	public float flapIntensity = 0.0F;
	public float lastFlapIntensity;
	public float lastFlapLength;
	public float flapSpeed = 1.0F;

	public EntityTFBird(EntityType<? extends EntityTFBird> entity, World world) {
		super(entity, world);
	}

	@Override
	public void livingTick() {
		super.livingTick();
		this.lastFlapLength = this.flapLength;
		this.lastFlapIntensity = this.flapIntensity;
		this.flapIntensity = (float) (this.flapIntensity + (this.onGround ? -1 : 4) * 0.3D);

		if (this.flapIntensity < 0.0F) {
			this.flapIntensity = 0.0F;
		}

		if (this.flapIntensity > 1.0F) {
			this.flapIntensity = 1.0F;
		}

		if (!this.onGround && this.flapSpeed < 1.0F) {
			this.flapSpeed = 1.0F;
		}

		this.flapSpeed = (float) (this.flapSpeed * 0.9D);

		// don't fall as fast
		if (!this.onGround && this.getMotion().getY() < 0.0D) {
			this.setMotion(new Vec3d(getMotion().getX(), getMotion().getY() * 0.6D, getMotion().getZ()));
		}

		this.flapLength += this.flapSpeed * 2.0F;

//	    // rise up when we go fast?
//        if (this.getMoveHelper().getSpeed() > 0.39F && this.moveForward > 0.1F)
//        {
//        	this.motionY += (0.30000001192092896D - this.motionY) * 0.30000001192092896D;
//        	this.moveForward *= 2F;
//        }
	}

	@Override
	protected void updateFallState(double y, boolean onGroundIn, @Nonnull BlockState state, @Nonnull BlockPos pos) {
	}

	@Override
	public boolean handleFallDamage(float dist, float damageMultiplier) {
		return false;
	}

	@Override
	public boolean bypassesSteppingEffects() {
		return false;
	}

	@Override
	public AnimalEntity createChild(AgeableEntity entityanimal) {
		return null;
	}

	/**
	 * Overridden by flying birds
	 */
	public boolean isBirdLanded() {
		return true;
	}

}
