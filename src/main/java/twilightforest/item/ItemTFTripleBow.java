package twilightforest.item;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import twilightforest.TwilightForestMod;

public class ItemTFTripleBow extends ItemTFBowBase {
	
	
    public ItemTFTripleBow() {
    	this.setTextureName(TwilightForestMod.ID + ":triplebow");
		this.setCreativeTab(TFItems.creativeTab);
    }

    /**
     * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
     */
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
    {
        int j = this.getMaxItemUseDuration(par1ItemStack) - par4;

        ArrowLooseEvent event = new ArrowLooseEvent(par3EntityPlayer, par1ItemStack, j);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled())
        {
            return;
        }
        j = event.charge;

        boolean flag = par3EntityPlayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, par1ItemStack) > 0;

        if (flag || par3EntityPlayer.inventory.hasItem(Items.ARROW))
        {
            float f = (float)j / 20.0F;
            f = (f * f + f * 2.0F) / 3.0F;

            if ((double)f < 0.1D)
            {
                return;
            }

            if (f > 1.0F)
            {
                f = 1.0F;
            }

            EntityArrow entityarrow = new EntityArrow(par2World, par3EntityPlayer, f * 2.0F);
            // other arrows with slight deviation
            EntityArrow entityarrow1 = new EntityArrow(par2World, par3EntityPlayer, f * 2.0F);
            entityarrow1.motionY += 0.007499999832361937D * 20F;
            entityarrow1.posY += 0.025F;
            EntityArrow entityarrow2 = new EntityArrow(par2World, par3EntityPlayer, f * 2.0F);
            entityarrow2.motionY -= 0.007499999832361937D * 20F;
            entityarrow2.posY -= 0.025F;

            if (f == 1.0F)
            {
                entityarrow.setIsCritical(true);
                entityarrow1.setIsCritical(true);
                entityarrow2.setIsCritical(true);
            }

            int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, par1ItemStack);

            if (k > 0)
            {
                entityarrow.setDamage(entityarrow.getDamage() + (double)k * 0.5D + 0.5D);
                entityarrow1.setDamage(entityarrow.getDamage() + (double)k * 0.5D + 0.5D);
                entityarrow2.setDamage(entityarrow.getDamage() + (double)k * 0.5D + 0.5D);
            }

            int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, par1ItemStack);

            if (l > 0)
            {
                entityarrow.setKnockbackStrength(l);
                entityarrow1.setKnockbackStrength(l);
                entityarrow2.setKnockbackStrength(l);
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, par1ItemStack) > 0)
            {
                entityarrow.setFire(100);
                entityarrow1.setFire(100);
                entityarrow2.setFire(100);
            }

            par1ItemStack.damageItem(1, par3EntityPlayer);
            par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

            if (flag)
            {
                entityarrow.canBePickedUp = 2;
            }
            else
            {
                par3EntityPlayer.inventory.consumeInventoryItem(Items.ARROW);
            }
            entityarrow1.canBePickedUp = 2;
            entityarrow2.canBePickedUp = 2;


            if (!par2World.isRemote)
            {
                par2World.spawnEntityInWorld(entityarrow);
                par2World.spawnEntityInWorld(entityarrow1);
                par2World.spawnEntityInWorld(entityarrow2);
            }
        }
    }

}
