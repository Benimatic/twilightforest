package twilightforest.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import twilightforest.entity.boss.EntityTFIceBomb;

public class ItemTFIceBomb extends ItemTF {

	public ItemTFIceBomb(Properties props) {
		super(props.maxStackSize(16));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
		player.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));

		if (!world.isRemote) {
			if (!player.abilities.isCreativeMode) {
				player.getHeldItem(hand).shrink(1);
			}
			EntityTFIceBomb ice = new EntityTFIceBomb(world, player);
			ice.shoot(player, player.rotationPitch, player.rotationYaw, -20.0F, 0.75F, 1.0F);
			world.addEntity(ice);
		}

		return ActionResult.newResult(ActionResultType.SUCCESS, player.getHeldItem(hand));
	}
}
