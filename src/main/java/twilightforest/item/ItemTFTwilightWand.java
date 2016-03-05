package twilightforest.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;
import twilightforest.entity.EntityTFTwilightWandBolt;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;



public class ItemTFTwilightWand extends ItemTF {

	protected ItemTFTwilightWand() {
		super();
        this.maxStackSize = 1;
        this.setMaxDamage(99);
		this.setCreativeTab(TFItems.creativeTab);

	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World worldObj, EntityPlayer player) {
		if (par1ItemStack.getItemDamage() < this.getMaxDamage()) 
		{
			player.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		}
		else 
		{
			player.stopUsingItem();
		}
		
		return par1ItemStack;
	}
	
    /**
     * Called each tick while using an item.
     * @param stack The Item being used
     * @param player The Player using the item
     * @param count The amount of time in tick the item has been used for continuously
     */
    @Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
		if (stack.getItemDamage() >= this.getMaxDamage()) {
			// do not use
			player.stopUsingItem();
			return;
		}
    	
    	if (count % 6 == 0) {
    		World worldObj = player.worldObj;
	    	
			worldObj.playSoundAtEntity(player, "mob.ghast.fireball", 1.0F, (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F + 1.0F);
			
			if (!worldObj.isRemote) {
				worldObj.spawnEntityInWorld(new EntityTFTwilightWandBolt(worldObj, player));
				
				stack.damageItem(1, player);
			}
    	}


	}

	/**
     * How long it takes to use or consume an item
     */
    @Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 72000;
    }
    
    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    @Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.bow;
    }
    
    /**
     * Return an item rarity from EnumRarity
     */    
    @Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
    	return EnumRarity.rare;
	}

    /**
     * Display charges left in tooltip
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		par3List.add((par1ItemStack.getMaxDamage() -  par1ItemStack.getItemDamage()) + " charges left");
	}
	
	/**
	 * Properly register icon source
	 */
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon(TwilightForestMod.ID + ":" + this.getUnlocalizedName().substring(5));
    }
}
