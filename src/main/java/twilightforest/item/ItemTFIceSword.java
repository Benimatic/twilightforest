package twilightforest.item;

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
		this.setTextureName(TwilightForestMod.ID + ":iceSword");
	}

    /**
     * Return whether this item is repairable in an anvil.
     */
    @Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
    	// repair with packed ice
        return par2ItemStack.getItem() == Item.getItemFromBlock(Blocks.PACKED_ICE) ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
    }

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    @Override
	public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLiving, EntityLivingBase par3EntityLiving) {
		boolean result = super.hitEntity(par1ItemStack, par2EntityLiving, par3EntityLiving);
		
		if (result) {
			int chillLevel = 2;
			par2EntityLiving.addPotionEffect(new PotionEffect(MobEffects.MOVESLOWDOWN.id, 20 * 10, chillLevel, true));
		}
		
		return result;
	}
    
    
    /**
     * Called when the player Left Clicks (attacks) an entity.
     * Processed before damage is done, if return value is true further processing is canceled
     * and the entity is not attacked.
     *
     * @param stack The Item being used
     * @param player The player that is attacking
     * @param entity The entity being attacked
     * @return True to cancel the rest of the interaction.
     */
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
    	if (player.worldObj.isRemote) {
			// snow animation!
	        for (int var1 = 0; var1 < 20; ++var1) {
	    		double px = entity.posX + itemRand.nextFloat() * entity.width * 2.0F - entity.width;
				double py = entity.posY + itemRand.nextFloat() * entity.height;
				double pz = entity.posZ + itemRand.nextFloat() * entity.width * 2.0F - entity.width;
				TwilightForestMod.proxy.spawnParticle(entity.worldObj, "snowstuff", px, py, pz, 0, 0, 0);
	        }
    	}
        return false;
    }
}
