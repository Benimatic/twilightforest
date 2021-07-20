package twilightforest.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class UncraftingInputInventory implements IInventory {

	private ItemStack stack = ItemStack.EMPTY;
	private final UncraftingContainer container;

	public UncraftingInputInventory(UncraftingContainer containerTFGoblinCrafting) {
		this.container = containerTFGoblinCrafting;
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public boolean isEmpty() {
		return stack.isEmpty();
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return index == 0 ? stack : ItemStack.EMPTY;
	}

	@Override
	public ItemStack decrStackSize(int index, int amount) {
		if (index == 0 && !this.stack.isEmpty()) {
			ItemStack takenStack;

			if (this.stack.getCount() <= amount) {
				takenStack = this.stack;
				this.stack = ItemStack.EMPTY;
				this.container.onCraftMatrixChanged(this);
				return takenStack;
			} else {
				takenStack = this.stack.split(amount);
				this.container.onCraftMatrixChanged(this);
				return takenStack;
			}
		} else {
			return ItemStack.EMPTY;
		}
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		if (index == 0 && !this.stack.isEmpty()) {
			ItemStack stack = this.stack;
			this.stack = ItemStack.EMPTY;
			return stack;
		} else {
			return ItemStack.EMPTY;
		}
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		if (index == 0) {
			this.stack = stack;
			this.container.onCraftMatrixChanged(this);
		}
	}

	@Override
	public void markDirty() {
		this.container.onCraftMatrixChanged(this);
	}

	@Override
	public boolean isUsableByPlayer(PlayerEntity player) {
		return true;
	}

	@Override
	public void clear() {
		stack = ItemStack.EMPTY;
	}
}
