package twilightforest.item;

import twilightforest.TFAchievementPage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemTFHydraChops extends ItemTFFood {

	public ItemTFHydraChops(int par2, float par3, boolean par4) {
		super(par2, par3, par4);
	}

	
    public ItemStack onEaten(ItemStack itemStack, World world, EntityPlayer player) {
    	// if the player is at zero food, achievements
        if (player.getFoodStats().getFoodLevel() <= 0) {
        	player.addStat(TFAchievementPage.twilightHydraChop);
        }
        // then normal effects
        return super.onEaten(itemStack, world, player);
    }
}
