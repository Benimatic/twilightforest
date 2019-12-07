package twilightforest.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public abstract class EntityTFArrow extends AbstractArrowEntity implements ITFProjectile {

	public EntityTFArrow(World worldIn) {
		super(worldIn);
	}

	public EntityTFArrow(World worldIn, LivingEntity shooter) {
		super(worldIn, shooter);
	}

	@Override
	protected ItemStack getArrowStack() {
		return new ItemStack(Items.ARROW);
	}

	@Override
	public Entity getThrower() {
		return this.shootingEntity;
	}

	@Override
	public void setThrower(Entity entity) {
		this.shootingEntity = entity;
	}
}
