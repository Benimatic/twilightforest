package twilightforest.tileentity;

import java.util.Random;

import net.minecraft.block.BlockLog;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import twilightforest.block.BlockTFCinderFurnace;
import twilightforest.block.BlockTFCinderLog;
import twilightforest.block.TFBlocks;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityTFCinderFurnace extends TileEntityFurnace {
    private static final int SMELT_LOG_FACTOR = 10;

	// [VanillaCopy] of superclass, edits noted
	@Override
    public void update()
    {
        boolean flag = this.isBurning();
        boolean flag1 = false;

        if (this.isBurning())
        {
            --this.furnaceBurnTime;
        }

        if (!this.world.isRemote)
        {
            if (this.isBurning() || this.furnaceItemStacks[1] != null && this.furnaceItemStacks[0] != null)
            {
                if (!this.isBurning() && this.canSmelt())
                {
                    this.furnaceBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);
                    this.currentItemBurnTime = this.furnaceBurnTime;

                    if (this.isBurning())
                    {
                        flag1 = true;

                        if (this.furnaceItemStacks[1] != null)
                        {
                            --this.furnaceItemStacks[1].stackSize;

                            if (this.furnaceItemStacks[1].stackSize == 0)
                            {
                                this.furnaceItemStacks[1] = furnaceItemStacks[1].getItem().getContainerItem(furnaceItemStacks[1]);
                            }
                        }
                    }
                }

                if (this.isBurning() && this.canSmelt())
                {
                    // TF - cook faster
                    this.cookTime += this.getCurrentSpeedMultiplier();

                    if (this.cookTime >= this.totalCookTime) // TF - change to geq since we can increment by >1
                    {
                        this.cookTime = 0;
                        this.totalCookTime = this.getCookTime(this.furnaceItemStacks[0]);
                        this.smeltItem();
                        flag1 = true;
                    }
                }
                else
                {
                    this.cookTime = 0;
                }
            }
            else if (!this.isBurning() && this.cookTime > 0)
            {
                this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.totalCookTime);
            }

            if (flag != this.isBurning())
            {
                flag1 = true;
                BlockTFCinderFurnace.setState(this.isBurning(), this.world, this.pos); // TF - use our furnace
            }

            // TF - occasionally cinderize nearby logs
            if (this.isBurning() && this.furnaceBurnTime % 5 == 0) {
                this.cinderizeNearbyLog();
            }
        }

        if (flag1)
        {
            this.markDirty();
        }
    }

    private void cinderizeNearbyLog() {
    	Random rand = this.getWorld().rand;
    	
		int dx = rand.nextInt(2) - rand.nextInt(2);
		int dy = rand.nextInt(2) - rand.nextInt(2);
		int dz = rand.nextInt(2) - rand.nextInt(2);
		BlockPos pos = getPos().add(dx, dy, dz);

		if (this.world.isBlockLoaded(pos)) {
			Block nearbyBlock = this.getWorld().getBlockState(pos).getBlock();

			if (nearbyBlock != TFBlocks.cinderLog && this.isLog(nearbyBlock)) {
				this.getWorld().setBlockState(pos, TFBlocks.cinderLog.getDefaultState().withProperty(BlockTFCinderLog.LOG_AXIS, getCinderFacing(dx, dy, dz)), 2);
				this.getWorld().playEvent(2004, pos, 0);
				this.getWorld().playEvent(2004, pos, 0);
				this.getWorld().playSound(null, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}
		}
	}

    /**
     * What meta should we set the log block with the specified offset to?
     */
    private BlockLog.EnumAxis getCinderFacing(int dx, int dy, int dz) {
		if (dz == 0 && dx != 0) {
			return dy == 0 ? BlockLog.EnumAxis.X : BlockLog.EnumAxis.Z;
		} else if (dx == 0 && dz != 0) {
			return dy == 0 ? BlockLog.EnumAxis.Z : BlockLog.EnumAxis.X;
		} else if (dx == 0 && dz == 0) {
			return BlockLog.EnumAxis.Y;
		} else {
			return dy == 0 ? BlockLog.EnumAxis.Y : BlockLog.EnumAxis.NONE;
		}
		
	}

	private boolean isLog(Block nearbyBlock) {
    	int[] oreIDs = OreDictionary.getOreIDs(new ItemStack(nearbyBlock));
    	for (int id : oreIDs) {
        	if (id == OreDictionary.getOreID("logWood")) {
    			return true;
    		}
    	}
    	
    	return false;
	}

	/**
     * What is the current speed multiplier, as an int.
     */
    private int getCurrentSpeedMultiplier() {
		return getCurrentMultiplier(2);
	}

    /**
     * Returns a number that is based on the number of nearby logs divided by the factor given.
     */
	private int getCurrentMultiplier(int factor) {
		int logs = this.countNearbyLogs();
		
		if (logs < factor) {
			return 1;
		} else {
			return (logs / factor) + (this.world.rand.nextInt(factor) >= (logs % factor) ? 0 : 1);
		}
	}

    private int countNearbyLogs() {
    	int count = 0;
    	
		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				for (int dz = -1; dz <= 1; dz++) {
				    BlockPos pos = getPos().add(dx, dy, dz);
					if (this.world.isBlockLoaded(pos) && this.getWorld().getBlockState(pos).getBlock() == TFBlocks.cinderLog) {
						count++;
					}
				}
			}
		}
		
		return count;
	}

	// [VanillaCopy] of superclass ver, changes noted
    private boolean canSmelt()
    {
        if (this.furnaceItemStacks[0] == null)
        {
            return false;
        }
        else
        {
            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.furnaceItemStacks[0]);
            if (itemstack == null) return false;
            if (this.furnaceItemStacks[2] == null) return true;
            if (!this.furnaceItemStacks[2].isItemEqual(itemstack)) return false;
            int result = furnaceItemStacks[2].stackSize + getMaxOutputStacks(this.furnaceItemStacks[0], itemstack); // TF - account for multiplying
            return result <= getInventoryStackLimit() && result <= this.furnaceItemStacks[2].getMaxStackSize(); //Forge BugFix: Make it respect stack sizes properly.
        }
    }

    /**
     * Return the max number of items in the output stack, given our current multiplier
     */
    public int getMaxOutputStacks(ItemStack input, ItemStack output) {
    	if (this.canMultiply(input, output)) {
    		return output.stackSize * this.getCurrentMaxSmeltMultiplier();
    	} else {
    		return output.stackSize;
    	}
    }

    // [VanillaCopy] superclass, using our own canSmelt and multiplying output
    @Override
    public void smeltItem()
    {
        if (this.canSmelt())
        {
            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.furnaceItemStacks[0]);

            // TF - multiply output
            itemstack = itemstack.copy();
            itemstack.stackSize *= getCurrentSmeltMultiplier();

            if (this.furnaceItemStacks[2] == null)
            {
                this.furnaceItemStacks[2] = itemstack.copy();
            }
            else if (this.furnaceItemStacks[2].getItem() == itemstack.getItem())
            {
                this.furnaceItemStacks[2].stackSize += itemstack.stackSize; // Forge BugFix: Results may have multiple items
            }

            if (this.furnaceItemStacks[0].getItem() == Item.getItemFromBlock(Blocks.SPONGE) && this.furnaceItemStacks[0].getMetadata() == 1 && this.furnaceItemStacks[1] != null && this.furnaceItemStacks[1].getItem() == Items.BUCKET)
            {
                this.furnaceItemStacks[1] = new ItemStack(Items.WATER_BUCKET);
            }

            --this.furnaceItemStacks[0].stackSize;

            if (this.furnaceItemStacks[0].stackSize <= 0)
            {
                this.furnaceItemStacks[0] = null;
            }
        }
    }

    private boolean canMultiply(ItemStack input, ItemStack output) {
    	int[] oreIDs = OreDictionary.getOreIDs(input);
    	for (int id : oreIDs) {
    		if (OreDictionary.getOreName(id).startsWith("ore") || id == OreDictionary.getOreID("logWood")) {
    			return true;
    		}
    	}

		return false;
	}

	/**
     * What is the current speed multiplier, as an int.
     */
    private int getCurrentSmeltMultiplier() {
		return getCurrentMultiplier(SMELT_LOG_FACTOR);
	}

	/**
     * What is the current speed multiplier, as an int.
     */
    private int getCurrentMaxSmeltMultiplier() {
		return (int)Math.ceil((float)this.countNearbyLogs() / (float)SMELT_LOG_FACTOR);
	}
}
