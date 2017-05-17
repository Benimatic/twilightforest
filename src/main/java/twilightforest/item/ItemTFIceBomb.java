package twilightforest.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;
import twilightforest.entity.boss.EntityTFIceBomb;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTFIceBomb extends ItemTF {

	public ItemTFIceBomb() {
		this.setMaxStackSize(16);
	}

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
	    player.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!world.isRemote) {
			if (!player.capabilities.isCreativeMode) {
				--stack.stackSize;
			}
			EntityTFIceBomb ice = new EntityTFIceBomb(world, player);
			ice.setHeadingFromThrower(player, player.rotationPitch, player.rotationYaw, -20.0F, 0.75F, 1.0F);
        	world.spawnEntity(ice);
        }

        return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
    }
}
