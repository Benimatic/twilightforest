package twilightforest.compat.tcon.trait;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.entity.EntityProjectileBase;
import slimeknights.tconstruct.library.traits.AbstractProjectileTrait;
import twilightforest.world.WorldProviderTwilightForest;

import javax.annotation.Nullable;
import java.util.List;

public class TraitTwilit extends AbstractProjectileTrait {
    private static final float bonus = 2.0f;

    public TraitTwilit() {
        super("twilit", TextFormatting.GOLD);
    }

    @Override
    public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event) {
        if (event.getEntity().getEntityWorld().provider instanceof WorldProviderTwilightForest)
            event.setNewSpeed(event.getNewSpeed() + bonus);
    }

    @Override
    public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
        if (target.getEntityWorld().provider instanceof WorldProviderTwilightForest)
            return super.damage(tool, player, target, damage, newDamage, isCritical);
        else
            return super.damage(tool, player, target, damage, newDamage + bonus, isCritical);
    }

    @Override
    public void onLaunch(EntityProjectileBase projectileBase, World world, @Nullable EntityLivingBase shooter) {
        if (!(projectileBase.getEntityWorld().provider instanceof WorldProviderTwilightForest)) return;

        projectileBase.motionX += (projectileBase.motionX * bonus);
        projectileBase.motionY += (projectileBase.motionY * bonus);
        projectileBase.motionZ += (projectileBase.motionZ * bonus);
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        String speed  = String.format(LOC_Extra + ".speed", getModifierIdentifier());
        String damage = String.format(LOC_Extra + ".damage", getModifierIdentifier());

        return ImmutableList.of(
                Util.translateFormatted(speed , Util.df.format(bonus)),
                Util.translateFormatted(damage, Util.df.format(bonus))
        );
    }
}
