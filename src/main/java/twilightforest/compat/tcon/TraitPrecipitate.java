package twilightforest.compat.tcon;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.PlayerEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class TraitPrecipitate extends AbstractTrait {
    @SuppressWarnings("WeakerAccess")
    public TraitPrecipitate() {
        super("precipitate", TextFormatting.DARK_GREEN);
    }

    @Override
    public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event) {
        EntityLivingBase entity = event.getEntityLiving();
        float maxHealth = entity.getMaxHealth();

        event.setNewSpeed(event.getNewSpeed() + ( ((maxHealth - entity.getHealth()) / maxHealth) * event.getOriginalSpeed() ) );
    }
}
