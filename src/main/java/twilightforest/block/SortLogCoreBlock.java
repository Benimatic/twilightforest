package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import twilightforest.util.WorldUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SortLogCoreBlock extends SpecialMagicLogBlock {

	public SortLogCoreBlock(Properties props) {
		super(props);
	}

	/**
	 * The sorting tree finds two chests nearby and then attempts to sort a random item.
	 */
	@Override
	void performTreeEffect(Level world, BlockPos pos, Random rand) {
		// find all the chests nearby
		List<Container> chests = new ArrayList<>();
		int itemCount = 0;

		for (BlockPos iterPos : WorldUtil.getAllAround(pos, 16)) {

			Container chestInventory = null, teInventory = null;

			Block block = world.getBlockState(iterPos).getBlock();
			if (block instanceof ChestBlock) {
				chestInventory = ChestBlock.getContainer((ChestBlock) block, block.defaultBlockState(), world, iterPos, true);
			}

			BlockEntity te = world.getBlockEntity(iterPos);
			if (te instanceof Container && !te.isRemoved()) {
				teInventory = (Container) te;
			}

			// make sure we haven't counted this chest
			if (chestInventory != null && teInventory != null && !checkIfChestsContains(chests, teInventory)) {

				boolean empty = true;
				// count items
				for (int i = 0; i < chestInventory.getContainerSize(); i++) {
					if (!chestInventory.getItem(i).isEmpty()) {
						empty = false;
						itemCount++;
					}
				}

				// only add non-empty chests
				if (!empty) {
					chests.add(chestInventory);
				}
			}
		}

		// find a random item in one of the chests
		ItemStack beingSorted = ItemStack.EMPTY;
		int sortedChestNum = -1;
		int sortedSlotNum = -1;

		if (itemCount == 0) return;

		int itemNumber = rand.nextInt(itemCount);
		int currentNumber = 0;

		for (int i = 0; i < chests.size(); i++) {
			Container chest = chests.get(i);
			for (int slotNum = 0; slotNum < chest.getContainerSize(); slotNum++) {
				ItemStack currentItem = chest.getItem(slotNum);

				if (!currentItem.isEmpty()) {
					if (currentNumber++ == itemNumber) {
						beingSorted = currentItem;
						sortedChestNum = i;
						sortedSlotNum = slotNum;
					}
				}
			}
		}

		if (beingSorted.isEmpty()) return;

		int matchChestNum = -1;
		int matchCount = 0;

		// decide where to put it, if anywhere
		for (int chestNum = 0; chestNum < chests.size(); chestNum++) {
			Container chest = chests.get(chestNum);
			int currentChestMatches = 0;

			for (int slotNum = 0; slotNum < chest.getContainerSize(); slotNum++) {

				ItemStack currentItem = chest.getItem(slotNum);
				if (!currentItem.isEmpty() && isSortingMatch(beingSorted, currentItem)) {
					currentChestMatches += currentItem.getCount();
				}
			}

			if (currentChestMatches > matchCount) {
				matchCount = currentChestMatches;
				matchChestNum = chestNum;
			}
		}

		// soooo, did we find a better match?
		if (matchChestNum >= 0 && matchChestNum != sortedChestNum) {
			Container moveChest = chests.get(matchChestNum);
			Container oldChest = chests.get(sortedChestNum);

			// is there an empty inventory slot in the new chest?
			int moveSlot = getEmptySlotIn(moveChest);

			if (moveSlot >= 0) {
				// remove old item
				oldChest.setItem(sortedSlotNum, ItemStack.EMPTY);

				// add new item
				moveChest.setItem(moveSlot, beingSorted);
			}
		}

		// if the stack is not full, combine items from other stacks
		if (beingSorted.getCount() < beingSorted.getMaxStackSize()) {
			for (Container chest : chests) {
				for (int slotNum = 0; slotNum < chest.getContainerSize(); slotNum++) {
					ItemStack currentItem = chest.getItem(slotNum);

					if (!currentItem.isEmpty() && currentItem != beingSorted && beingSorted.sameItem(currentItem)) {
						if (currentItem.getTag() != null && beingSorted.getTag() != null) {
							if (beingSorted.getTag().equals(currentItem.getTag())) {
								if (currentItem.getCount() <= (beingSorted.getMaxStackSize() - beingSorted.getCount())) {
									chest.setItem(slotNum, ItemStack.EMPTY);
									beingSorted.grow(currentItem.getCount());
									currentItem.setCount(0);
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Is the chest we're testing part of our chest list already?
	 */
	private boolean checkIfChestsContains(List<Container> chests, Container testChest) {
		for (Container chest : chests) {
			if (chest == testChest) {
				return true;
			}

			if (chest instanceof CompoundContainer && ((CompoundContainer) chest).contains(testChest)) {
				return true;
			}
		}
		return false;
	}

	private boolean isSortingMatch(ItemStack beingSorted, ItemStack currentItem) {
		return beingSorted.getItem().getItemCategory() == currentItem.getItem().getItemCategory();
	}

	/**
	 * @return an empty slot number in the chest, or -1 if the chest is full
	 */
	private int getEmptySlotIn(Container chest) {
		for (int i = 0; i < chest.getContainerSize(); i++) {
			if (chest.getItem(i).isEmpty()) {
				return i;
			}
		}

		return -1;
	}
}
