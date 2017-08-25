package twilightforest.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;
import twilightforest.util.PlayerHelper;

public class ItemTFHydraChops extends ItemTFFood {

	public ItemTFHydraChops(int amount, float saturation) {
		super(amount, saturation, true);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack itemStack, World world, EntityLivingBase living) {
		// if the player is at zero food, achievements
		if (living instanceof EntityPlayerMP && ((EntityPlayerMP) living).getFoodStats().getFoodLevel() <= 0) {
			PlayerHelper.grantAdvancement((EntityPlayerMP) living, new ResourceLocation(TwilightForestMod.ID, "hydra_chop"));
		}
		// then normal effects
		return super.onItemUseFinish(itemStack, world, living);
	}
}
