package twilightforest.entity.projectile;

import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

public abstract class TFArrow extends AbstractArrow implements ITFProjectile {

	public TFArrow(EntityType<? extends TFArrow> type, Level worldIn) {
		super(type, worldIn);
	}

	public TFArrow(EntityType<? extends TFArrow> type, Level worldIn, Entity shooter) {
		super(type, worldIn);
		this.setOwner(shooter);
		this.setPos(shooter.getX(), shooter.getEyeY() - 0.1D, shooter.getZ());
	}

	@Override
	protected ItemStack getPickupItem() {
		return new ItemStack(Items.ARROW);
	}
}
