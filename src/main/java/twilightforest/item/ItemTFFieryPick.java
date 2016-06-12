package twilightforest.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTFFieryPick extends ItemPickaxe {

	protected ItemTFFieryPick(Item.ToolMaterial par2EnumToolMaterial) {
		super(par2EnumToolMaterial);
		this.setCreativeTab(TFItems.creativeTab);
	}

	@Override
	public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, Block blockID, int x, int y, int z, EntityLivingBase par7EntityLiving) {
		if (super.onBlockDestroyed(par1ItemStack, par2World, blockID, x, y, z, par7EntityLiving) && this.func_150897_b(blockID))
		{
			// we are just displaying the fire animation here, so check if we're on the client
			if (par2World.isRemote)
			{
				int meta = par2World.getBlockMetadata(x, y, z); // I guess the block is still there at this point

				ArrayList<ItemStack> items = blockID.getDrops(par2World, x, y, z, meta, 0);

				for (ItemStack input : items)
				{
					// does it smelt?
					ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(input);
					if (result != null)
					{

						// display fire animation
						for (int var1 = 0; var1 < 5; ++var1)
						{
							double rx = itemRand.nextGaussian() * 0.02D;
							double ry = itemRand.nextGaussian() * 0.02D;
							double rz = itemRand.nextGaussian() * 0.02D;
							double magnitude = 20.0;
							par2World.spawnParticle("flame", x + 0.5 + (rx * magnitude), y + 0.5 + (ry * magnitude), z + 0.5 + (rz * magnitude), -rx, -ry, -rz);
						}

					}

				}
			}


			return true;
		}
		else
		{
			return false;
		}
	}
	
    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    @Override
	public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLiving, EntityLivingBase par3EntityLiving) {
		boolean result = super.hitEntity(par1ItemStack, par2EntityLiving, par3EntityLiving);
		
		if (result && !par2EntityLiving.isImmuneToFire())
		{
			if (par2EntityLiving.worldObj.isRemote)
			{
				// fire animation!
		        for (int var1 = 0; var1 < 20; ++var1)
		        {
		            double var2 = itemRand.nextGaussian() * 0.02D;
		            double var4 = itemRand.nextGaussian() * 0.02D;
		            double var6 = itemRand.nextGaussian() * 0.02D;
		            double var8 = 10.0D;
		            par2EntityLiving.worldObj.spawnParticle("flame", par2EntityLiving.posX + itemRand.nextFloat() * par2EntityLiving.width * 2.0F - par2EntityLiving.width - var2 * var8, par2EntityLiving.posY + itemRand.nextFloat() * par2EntityLiving.height - var4 * var8, par2EntityLiving.posZ + itemRand.nextFloat() * par2EntityLiving.width * 2.0F - par2EntityLiving.width - var6 * var8, var2, var4, var6);
		        }
			}
			else
			{
				par2EntityLiving.setFire(15);
			}
		}
		
		return result;
	}

	/**
     * Return an item rarity from EnumRarity
     * 
     * This is automatically rare
     */    
    @Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
    	return EnumRarity.rare;
	}
    
    /**
     * allows items to add custom lines of information to the mouseover description
     */
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		par3List.add(StatCollector.translateToLocal(getUnlocalizedName() + ".tooltip"));
	}
    
    /**
     * Return whether this item is repairable in an anvil.
     */
    @Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
    	// repair with fiery ingots
        return par2ItemStack.getItem() == TFItems.fieryIngot ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
    }
    
    
    /**
     * Returns if the item (tool) can harvest results from the block type. (canHarvestBlock)
     */
    public boolean func_150897_b(Block par1Block)
    {
        return par1Block == Blocks.obsidian ? true : super.func_150897_b(par1Block);
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
