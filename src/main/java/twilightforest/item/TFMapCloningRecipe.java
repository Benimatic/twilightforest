package twilightforest.item;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class TFMapCloningRecipe implements IRecipe
{
	public Item fullMapID;
	public Item blankMapID;
	
	public TFMapCloningRecipe(Item magicMap, Item emptyMagicMap)
	{
		this.fullMapID = magicMap;
		this.blankMapID = emptyMagicMap;
	}
	
    /**
     * Used to check if a recipe matches current crafting inventory
     */
    @Override
	public boolean matches(InventoryCrafting par1InventoryCrafting, World par2World)
    {
        int var3 = 0;
        ItemStack var4 = null;

        for (int var5 = 0; var5 < par1InventoryCrafting.getSizeInventory(); ++var5)
        {
            ItemStack var6 = par1InventoryCrafting.getStackInSlot(var5);

            if (var6 != null)
            {
                if (var6.getItem() == this.fullMapID)
                {
                    if (var4 != null)
                    {
                        return false;
                    }

                    var4 = var6;
                }
                else
                {
                    if (var6.getItem() != this.blankMapID)
                    {
                        return false;
                    }

                    ++var3;
                }
            }
        }

        return var4 != null && var3 > 0;
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    @Override
	public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting)
    {
        int var2 = 0;
        ItemStack var3 = null;

        for (int var4 = 0; var4 < par1InventoryCrafting.getSizeInventory(); ++var4)
        {
            ItemStack var5 = par1InventoryCrafting.getStackInSlot(var4);

            if (var5 != null)
            {
                if (var5.getItem() == this.fullMapID)
                {
                    if (var3 != null)
                    {
                        return null;
                    }

                    var3 = var5;
                }
                else
                {
                    if (var5.getItem() != this.blankMapID)
                    {
                        return null;
                    }

                    ++var2;
                }
            }
        }

        if (var3 != null && var2 >= 1)
        {
            ItemStack var6 = new ItemStack(this.fullMapID, var2 + 1, var3.getItemDamage());

            if (var3.hasDisplayName())
            {
                var6.setStackDisplayName(var3.getDisplayName());
            }

            return var6;
        }
        else
        {
            return null;
        }
    }

    /**
     * Returns the size of the recipe area
     */
    @Override
	public int getRecipeSize()
    {
        return 9;
    }

    @Override
	public ItemStack getRecipeOutput()
    {
        return null;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        return ForgeHooks.defaultRecipeGetRemainingItems(inv);
    }
}
