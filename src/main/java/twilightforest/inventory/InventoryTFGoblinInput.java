package twilightforest.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;


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

    @Override
	public String getName()
    {
        return "Input";
    }

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

    @Override
	public ItemStack removeStackFromSlot(int par1)
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

    @Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        this.stackInput[par1] = par2ItemStack;
        this.craftingContainer.onCraftMatrixChanged(this);
    }

    @Override
	public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
	public void markDirty() {
    	this.craftingContainer.onCraftMatrixChanged(this);
    }

    @Override
	public boolean isUsableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return true;
    }

    @Override
	public void openInventory(EntityPlayer player) {}

    @Override
	public void closeInventory(EntityPlayer player) {}

    @Override
    public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
    {
        return true;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        stackInput[0] = null;
    }

    @Override
	public boolean hasCustomName() {
		return false;
	}

    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentString(getName());
    }


}
