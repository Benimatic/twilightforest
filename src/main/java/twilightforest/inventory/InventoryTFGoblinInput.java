package twilightforest.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;


public class InventoryTFGoblinInput implements IInventory {
	
	private ItemStack[] stackInput = new ItemStack[1];
	private ContainerTFUncrafting craftingContainer;

    public InventoryTFGoblinInput(ContainerTFUncrafting containerTFGoblinCrafting) {
		this.craftingContainer = containerTFGoblinCrafting;
	}

	/**
     * Returns the number of slots in the inventory.
     */
    @Override
	public int getSizeInventory()
    {
        return 1;
    }

    /**
     * Returns the stack in slot i
     */
    @Override
	public ItemStack getStackInSlot(int par1)
    {
        return this.stackInput[par1];
    }

    /**
     * Returns the name of the inventory.
     */
    @Override
	public String getInventoryName()
    {
        return "Input";
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
     * stack.
     */
    @Override
	public ItemStack decrStackSize(int slotNum, int amount)
    {
    	
        if (this.stackInput[slotNum] != null)
        {
            ItemStack takenStack;

            if (this.stackInput[slotNum].stackSize <= amount)
            {
                takenStack = this.stackInput[slotNum];
                this.stackInput[slotNum] = null;
                this.craftingContainer.onCraftMatrixChanged(this);
                return takenStack;
            }
            else
            {
                takenStack = this.stackInput[slotNum].splitStack(amount);

                if (this.stackInput[slotNum].stackSize == 0)
                {
                    this.stackInput[slotNum] = null;
                }

                this.craftingContainer.onCraftMatrixChanged(this);
                return takenStack;
            }
        }
        else
        {
            return null;
        }
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    @Override
	public ItemStack getStackInSlotOnClosing(int par1)
    {
        if (this.stackInput[par1] != null)
        {
            ItemStack var2 = this.stackInput[par1];
            this.stackInput[par1] = null;
            return var2;
        }
        else
        {
            return null;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    @Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        this.stackInput[par1] = par2ItemStack;
        this.craftingContainer.onCraftMatrixChanged(this);
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    @Override
	public int getInventoryStackLimit()
    {
        return 64;
    }

    /**
     * Called when an the contents of an Inventory change, usually
     */
    @Override
	public void markDirty() {
    	this.craftingContainer.onCraftMatrixChanged(this);
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    @Override
	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return true;
    }

    @Override
	public void openInventory() {}

    @Override
	public void closeInventory() {}

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     */
    @Override
    public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
    {
        return true;
    }

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}


}
