package twilightforest.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;


public class SlotTFGoblinCraftResult extends SlotCrafting {
	
    /** The player that is using the GUI where this slot resides. */
	protected EntityPlayer thePlayer;
	protected IInventory inputSlot;
	protected InventoryTFGoblinUncrafting uncraftingMatrix;
	protected InventoryCrafting assemblyMatrix;
	protected IInventory resultSlot;

	public SlotTFGoblinCraftResult(EntityPlayer player, IInventory input,  IInventory uncraftingMatrix, IInventory assemblyMatrix, IInventory result, int slotIndex, int x, int y) {
		super(player, (InventoryCrafting) assemblyMatrix, result, slotIndex, x, y);
		this.thePlayer = player;
		this.inputSlot = input;
		this.uncraftingMatrix = (InventoryTFGoblinUncrafting) uncraftingMatrix;
		this.assemblyMatrix = (InventoryCrafting) assemblyMatrix;
	}

    /**
     * Called when the player picks up an item from an inventory slot
     */
	@Override
	public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par1ItemStack) {
		// let's see, if the assembly matrix can produce this item, then it's a normal recipe, if not, it's combined.  Will that work?
		boolean combined = true;
		
		if (ItemStack.areItemStacksEqual(CraftingManager.getInstance().findMatchingRecipe(this.assemblyMatrix, this.thePlayer.world), par1ItemStack)) {
			combined = false;
		}
		
		if (combined) {
			// charge the player before the stacks empty
			if (this.uncraftingMatrix.recraftingCost > 0) {
				this.thePlayer.addExperienceLevel(-this.uncraftingMatrix.recraftingCost);
				
				//System.out.println("Charging the player " + this.uncraftingMatrix.recraftingCost + " experience for recrafting");
			}

			// if we are using a combined recipe, wipe the uncrafting matrix and decrement the input appropriately
			for (int i = 0; i < uncraftingMatrix.getSizeInventory(); i++) {
				this.uncraftingMatrix.setInventorySlotContents(i, null);
				this.inputSlot.decrStackSize(0, this.uncraftingMatrix.numberOfInputItems);
			}
		}
		
		super.onPickupFromSlot(par1EntityPlayer, par1ItemStack);
	}

	
	
	
}
