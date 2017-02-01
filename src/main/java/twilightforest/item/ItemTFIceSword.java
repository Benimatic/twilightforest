package twilightforest.item;

import net.minecraft.init.MobEffects;
import twilightforest.TwilightForestMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class ItemTFIceSword extends ItemSword {

	public ItemTFIceSword(Item.ToolMaterial par2EnumToolMaterial) {
		super(par2EnumToolMaterial);
		this.setCreativeTab(TFItems.creativeTab);
	}

    @Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
    	// repair with packed ice
        return par2ItemStack.getItem() == Item.getItemFromBlock(Blocks.PACKED_ICE) ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
    }

    @Override
	public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLiving, EntityLivingBase par3EntityLiving) {
		boolean result = super.hitEntity(par1ItemStack, par2EntityLiving, par3EntityLiving);
		
		if (result) {
			int chillLevel = 2;
			par2EntityLiving.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20 * 10, chillLevel, true, true));
		}
		
		return result;
	}

	@Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
    	if (player.world.isRemote) {
			// snow animation!
	        for (int var1 = 0; var1 < 20; ++var1) {
	    		double px = entity.posX + itemRand.nextFloat() * entity.width * 2.0F - entity.width;
				double py = entity.posY + itemRand.nextFloat() * entity.height;
				double pz = entity.posZ + itemRand.nextFloat() * entity.width * 2.0F - entity.width;
				TwilightForestMod.proxy.spawnParticle(entity.world, "snowstuff", px, py, pz, 0, 0, 0);
	        }
    	}
        return false;
    }
}
