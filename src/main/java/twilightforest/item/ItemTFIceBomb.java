package twilightforest.item;

import net.minecraft.entity.player.EntityPlayer;
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
        world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!world.isRemote) {
			if (!player.capabilities.isCreativeMode) {
				--stack.stackSize;
			}
        	world.spawnEntity(new EntityTFIceBomb(world, player));
        }

        return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
    }
}
