package twilightforest.block;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.DoubleSidedInventory;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;
import twilightforest.enums.MagicWoodVariant;
import twilightforest.biomes.TFBiomes;
import twilightforest.item.ItemTFOreMagnet;
import twilightforest.network.PacketChangeBiome;
import twilightforest.network.TFPacketHandler;
import twilightforest.util.WorldUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockTFMagicLogSpecial extends LogBlock {

	private final MagicWoodVariant magicWoodVariant;
	public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

	protected BlockTFMagicLogSpecial(MaterialColor topColor, MaterialColor sideColor, MagicWoodVariant variant) {
		super(topColor, Properties.create(Material.WOOD, sideColor).hardnessAndResistance(2.0F).sound(SoundType.WOOD).lightValue(15));

		magicWoodVariant = variant;
		setDefaultState(stateContainer.getBaseState().with(ACTIVE, false));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> container) {
		super.fillStateContainer(container);
		container.add(ACTIVE);
	}

	@Override
	public int tickRate(IWorldReader world) {
		return 20;
	}

	@Override
	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean isMoving) {
		world.getPendingBlockTicks().scheduleTick(pos, this, this.tickRate(world));
	}

	@Override
	@Deprecated
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
		if (world.isRemote || !state.get(ACTIVE)) return;

		switch (this.magicWoodVariant) {
			case TIME:
				world.playSound(null, pos, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 0.1F, 0.5F);
				doTreeOfTimeEffect(world, pos, rand);
				break;
			case TRANS:
				world.playSound(null, pos, SoundEvents.BLOCK_NOTE_BLOCK_HARP, SoundCategory.BLOCKS, 0.1F, rand.nextFloat() * 2F);
				doTreeOfTransformationEffect(world, pos, rand);
				break;
			case MINE:
				doMinersTreeEffect(world, pos, rand);
				break;
			case SORT:
				doSortingTreeEffect(world, pos, rand);
				break;
		}

		world.getPendingBlockTicks().scheduleTick(pos, this, this.tickRate(world));
	}

	@Override
	@Deprecated
	public ActionResultType onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (!state.get(ACTIVE)) {
			world.setBlockState(pos, state.with(ACTIVE, true));
			world.getPendingBlockTicks().scheduleTick(pos, this, this.tickRate(world));
			return ActionResultType.SUCCESS;
		} else if (state.get(ACTIVE)) {
			world.setBlockState(pos, state.with(ACTIVE, false));
			return ActionResultType.SUCCESS;
		}

		return ActionResultType.PASS;
	}

	/**
	 * The tree of time adds extra ticks to blocks, so that they have twice the normal chance to get a random tick
	 */
	private void doTreeOfTimeEffect(World world, BlockPos pos, Random rand) {

		int numticks = 8 * 3 * this.tickRate(world);

		for (int i = 0; i < numticks; i++) {

			BlockPos dPos = WorldUtil.randomOffset(rand, pos, 16);

			BlockState state = world.getBlockState(dPos);

			if (state.ticksRandomly()) {
				state.scheduledTick((ServerWorld) world, dPos, rand);
			}

			TileEntity te = world.getTileEntity(dPos);
			if (te instanceof ITickableTileEntity && !te.isRemoved()) {
				((ITickableTileEntity) te).tick();
			}
		}
	}

	/**
	 * The tree of transformation transforms the biome in the area near it into the enchanted forest biome.
	 * TODO: also change entities
	 */
	private void doTreeOfTransformationEffect(World world, BlockPos pos, Random rand) {

		Biome targetBiome = TFBiomes.enchantedForest.get();

		for (int i = 0; i < 16; i++) {

			BlockPos dPos = WorldUtil.randomOffset(rand, pos, 16, 0, 16);
			if (dPos.distanceSq(pos) > 256.0) continue;

			Biome biomeAt = world.getBiome(dPos);
			if (biomeAt == targetBiome) continue;

			Chunk chunkAt = world.getChunk(dPos.getX() >> 4, dPos.getZ() >> 4);
			// todo 1.15 reflect/AT into BiomeManager.data

			if (world instanceof ServerWorld) {
				sendChangedBiome(chunkAt, dPos, targetBiome);
			}
			break;
		}
	}

	/**
	 * Send a tiny update packet to the client to inform it of the changed biome
	 */
	private void sendChangedBiome(Chunk chunk, BlockPos pos, Biome biome) {
		PacketChangeBiome message = new PacketChangeBiome(pos, biome);
		TFPacketHandler.CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> chunk), message);
	}

	/**
	 * The miner's tree generates the ore magnet effect randomly every second
	 */
	private void doMinersTreeEffect(World world, BlockPos pos, Random rand) {
		BlockPos dPos = WorldUtil.randomOffset(rand, pos, 32);
		int moved = ItemTFOreMagnet.doMagnet(world, pos, dPos);

		if (moved > 0) {
			world.playSound(null, pos, SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.BLOCKS, 0.1F, 1.0F);
		}
	}

	/**
	 * The sorting tree finds two chests nearby and then attempts to sort a random item.
	 */
	private void doSortingTreeEffect(World world, BlockPos pos, Random rand) {

		// find all the chests nearby
		List<IInventory> chests = new ArrayList<>();
		int itemCount = 0;

		for (BlockPos iterPos : WorldUtil.getAllAround(pos, 16)) {

			IInventory chestInventory = null, teInventory = null;

			Block block = world.getBlockState(iterPos).getBlock();
			if (block instanceof ChestBlock) {
				chestInventory = ChestBlock.getInventory((ChestBlock) block, block.getDefaultState(), world, iterPos, true);
			}

			TileEntity te = world.getTileEntity(iterPos);
			if (te instanceof IInventory && !te.isRemoved()) {
				teInventory = (IInventory) te;
			}

			// make sure we haven't counted this chest
			if (chestInventory != null && teInventory != null && !checkIfChestsContains(chests, teInventory)) {

				boolean empty = true;
				// count items
				for (int i = 0; i < chestInventory.getSizeInventory(); i++) {
					if (!chestInventory.getStackInSlot(i).isEmpty()) {
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
			IInventory chest = chests.get(i);
			for (int slotNum = 0; slotNum < chest.getSizeInventory(); slotNum++) {
				ItemStack currentItem = chest.getStackInSlot(slotNum);

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
			IInventory chest = chests.get(chestNum);
			int currentChestMatches = 0;

			for (int slotNum = 0; slotNum < chest.getSizeInventory(); slotNum++) {

				ItemStack currentItem = chest.getStackInSlot(slotNum);
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
			IInventory moveChest = chests.get(matchChestNum);
			IInventory oldChest = chests.get(sortedChestNum);

			// is there an empty inventory slot in the new chest?
			int moveSlot = getEmptySlotIn(moveChest);

			if (moveSlot >= 0) {
				// remove old item
				oldChest.setInventorySlotContents(sortedSlotNum, ItemStack.EMPTY);

				// add new item
				moveChest.setInventorySlotContents(moveSlot, beingSorted);
			}
		}

		// if the stack is not full, combine items from other stacks
		if (beingSorted.getCount() < beingSorted.getMaxStackSize()) {
			for (IInventory chest : chests) {
				for (int slotNum = 0; slotNum < chest.getSizeInventory(); slotNum++) {
					ItemStack currentItem = chest.getStackInSlot(slotNum);

					if (!currentItem.isEmpty() && currentItem != beingSorted && beingSorted.isItemEqual(currentItem)) {
						if (currentItem.getCount() <= (beingSorted.getMaxStackSize() - beingSorted.getCount())) {
							chest.setInventorySlotContents(slotNum, ItemStack.EMPTY);
							beingSorted.grow(currentItem.getCount());
							currentItem.setCount(0);
						}
					}
				}
			}
		}
	}

	private boolean isSortingMatch(ItemStack beingSorted, ItemStack currentItem) {
		return beingSorted.getItem().getGroup() == currentItem.getItem().getGroup();
	}

	/**
	 * Is the chest we're testing part of our chest list already?
	 */
	private boolean checkIfChestsContains(List<IInventory> chests, IInventory testChest) {
		for (IInventory chest : chests) {
			if (chest == testChest) {
				return true;
			}

			if (chest instanceof DoubleSidedInventory && ((DoubleSidedInventory) chest).isPartOfLargeChest(testChest)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return an empty slot number in the chest, or -1 if the chest is full
	 */
	private int getEmptySlotIn(IInventory chest) {
		for (int i = 0; i < chest.getSizeInventory(); i++) {
			if (chest.getStackInSlot(i).isEmpty()) {
				return i;
			}
		}

		return -1;
	}
}
