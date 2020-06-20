package twilightforest.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import twilightforest.TFConfig;

import javax.annotation.Nullable;

public class InventoryTFGoblinUncrafting implements IInventory {

	private final NonNullList<ItemStack> contents = NonNullList.withSize(9, ItemStack.EMPTY);

	public int numberOfInputItems;
	public int uncraftingCost;
	public int recraftingCost;

	@Override
	public int getSizeInventory() {
		return 9;
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack stack : contents) {
			if (!stack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return contents.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int amount) {
		ItemStack stack = this.contents.get(index);
		if (stack.isEmpty()) return ItemStack.EMPTY;
		if (stack.getCount() <= amount) {
			this.contents.set(index, ItemStack.EMPTY);
			return stack;
		} else {
			return stack.split(amount);
		}
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		ItemStack stack = this.contents.get(index);
		if (stack.isEmpty()) return ItemStack.EMPTY;
		this.contents.set(index, ItemStack.EMPTY);
		return stack;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		this.contents.set(index, stack);

		if (!stack.isEmpty() && stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}

		this.markDirty();
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void markDirty() {
	}

	@Override
	public boolean isUsableByPlayer(PlayerEntity player) {
		return !TFConfig.COMMON_CONFIG.disableUncrafting.get();
	}

	@Override
	public void openInventory(PlayerEntity player) {
	}

	@Override
	public void closeInventory(PlayerEntity player) {
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return false;
	}

	@Override
	public void clear() {
		contents.clear();
	}
}
