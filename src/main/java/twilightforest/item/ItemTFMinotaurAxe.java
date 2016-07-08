package twilightforest.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;

import com.google.common.collect.Multimap;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTFMinotaurAxe extends ItemAxe {

	public static final int BONUS_CHARGING_DAMAGE = 7;
	private Entity bonusDamageEntity;
	private EntityPlayer bonusDamagePlayer;
	private float damageVsEntity;

	protected ItemTFMinotaurAxe(Item.ToolMaterial par2EnumToolMaterial) {
		super(par2EnumToolMaterial);
		this.damageVsEntity = 4 + par2EnumToolMaterial.getDamageVsEntity();
		this.setCreativeTab(TFItems.creativeTab);
	}

    @Override
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> par3List)
    {
    	ItemStack istack = new ItemStack(par1, 1, 0);
    	//istack.addEnchantment(Enchantments.EFFICIENCY, 2);
        par3List.add(istack);
    }
    
    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) 
    {
    	// if the player is sprinting, keep the entity, we will do extra damage to it
    	if (player.isSprinting())
    	{
    		this.bonusDamageEntity = entity;
    		this.bonusDamagePlayer = player;
    	}
    	
        return false;
    }
    
    /**
     * Returns the damage against a given entity.
     */
    public float getDamageVsEntity(Entity par1Entity, ItemStack itemStack)
    {
       	if (this.bonusDamagePlayer != null && this.bonusDamageEntity != null && par1Entity == this.bonusDamageEntity)
       	{
       		//System.out.println("Minotaur Axe extra damage!");
       		this.bonusDamagePlayer.onEnchantmentCritical(par1Entity);
       		this.bonusDamagePlayer = null;
       		this.bonusDamageEntity = null;
       		return damageVsEntity + BONUS_CHARGING_DAMAGE;
       	}
       	else
       	{
       		return damageVsEntity;
       	}
    }
    
    @Override
    public int getItemEnchantability()
    {
        return Item.ToolMaterial.GOLD.getEnchantability();
    }
    
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List<String> par3List, boolean par4) {
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		par3List.add(I18n.translateToLocal(getUnlocalizedName() + ".tooltip"));
	}

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
    {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
        // remove old damage value
        multimap.removeAll(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName());
        // add new one
        multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", (double)this.damageVsEntity, 0));
        return multimap;
    }
}

