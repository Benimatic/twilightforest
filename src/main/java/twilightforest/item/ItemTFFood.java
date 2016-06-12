package twilightforest.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTFFood extends ItemFood {
	
	protected Item looksLike; 
	protected boolean isSoup;


	/**
	 * Soup constructor
	 */
	public ItemTFFood(int par2) 
	{
		super(par2, false);
		this.setCreativeTab(TFItems.creativeTab);
		this.setSoup(true);
	}

	public ItemTFFood(int par2, float par3, boolean par4) 
	{
		super(par2, par3, par4);
		this.setCreativeTab(TFItems.creativeTab);
	}

	

	public Item getLooksLike() {
		return looksLike;
	}

	/**
	 * Set this food to look like the specified item
	 */
	public ItemTFFood setLooksLike(Item itemLike) {
		this.looksLike = itemLike;
		return this;
	}
	
    /**
     * Gets an icon index based on an item's damage value and the given render pass
     */
    public IIcon getIconFromDamage(int par1)
    {
    	if (this.looksLike != null)
    	{
            return looksLike.getIconFromDamage(0);
    	}
    	else
    	{
    		return super.getIconFromDamage(par1);
    	}
    }
    
	
	/**
	 * Properly register icon source
	 */
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
    	// only try to load if we don't have a lookalike
        if (this.looksLike == null)
        {
            this.itemIcon = par1IconRegister.registerIcon(TwilightForestMod.ID + ":" + this.getUnlocalizedName().substring(5));
        }
    }

    
	public boolean isSoup() {
		return isSoup;
	}

	public void setSoup(boolean isSoup) {
		this.isSoup = isSoup;
        this.setMaxStackSize(1);
	}

	/**
	 * Give a bowl back if we are soup
	 */
    public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        super.onEaten(par1ItemStack, par2World, par3EntityPlayer);
        
        if (isSoup)
        {
        	return new ItemStack(Items.bowl);
        }
        else
        {
        	return par1ItemStack;
        }
    }

	
}
