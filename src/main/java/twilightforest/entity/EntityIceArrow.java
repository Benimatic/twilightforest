package twilightforest.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityIceArrow extends EntityArrow {

	public EntityIceArrow(World par1World) {
		super(par1World);
	}

	public EntityIceArrow(World world, EntityPlayer player) {
		super(world, player);
	}

	@Override
	protected ItemStack getArrowStack() {
		return new ItemStack(Items.ARROW);
	}

}
