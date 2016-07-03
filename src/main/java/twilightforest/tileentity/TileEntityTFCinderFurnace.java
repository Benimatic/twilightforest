package twilightforest.tileentity;

import java.util.Random;

import net.minecraft.util.EnumFacing;
import twilightforest.block.BlockTFCinderFurnace;
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

public class TileEntityTFCinderFurnace extends TileEntity implements ISidedInventory {
	
    private static final int SMELT_LOG_FACTOR = 10;
	private static final int SLOT_INPUT = 0;
    private static final int SLOT_FUEL = 1;
	private static final int SLOT_OUTPUT = 2;
	
    private static final int[] slotsTop = new int[] {SLOT_INPUT};
    private static final int[] slotsBottom = new int[] {SLOT_OUTPUT, SLOT_FUEL};
    private static final int[] slotsSides = new int[] {SLOT_FUEL};
    
	/** The ItemStacks that hold the items currently being used in the furnace */
    private ItemStack[] furnaceItemStacks = new ItemStack[3];
    /** The number of ticks that the furnace will keep burning */
    public int furnaceBurnTime;
    /** The number of ticks that a fresh copy of the currently-burning item would keep the furnace burning for */
    public int currentItemBurnTime;
    /** The number of ticks that the current item has been cooking for */
    public int furnaceCookTime;
	private String customName;

	@Override
	public int getSizeInventory() {
		return this.furnaceItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return this.furnaceItemStacks[slot];
	}

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    @Override
    public ItemStack decrStackSize(int slot, int amount)
    {
        if (this.furnaceItemStacks[slot] != null)
        {
            ItemStack itemstack;

            if (this.furnaceItemStacks[slot].stackSize <= amount)
            {
                itemstack = this.furnaceItemStacks[slot];
                this.furnaceItemStacks[slot] = null;
                return itemstack;
            }
            else
            {
                itemstack = this.furnaceItemStacks[slot].splitStack(amount);

                if (this.furnaceItemStacks[slot].stackSize == 0)
                {
                    this.furnaceItemStacks[slot] = null;
                }

                return itemstack;
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
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        if (this.furnaceItemStacks[slot] != null)
        {
            ItemStack itemstack = this.furnaceItemStacks[slot];
            this.furnaceItemStacks[slot] = null;
            return itemstack;
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
    public void setInventorySlotContents(int slot, ItemStack itemStack)
    {
        this.furnaceItemStacks[slot] = itemStack;

        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit())
        {
            itemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    /**
     * Returns the name of the inventory
     */
    public String getInventoryName()
    {
        return this.hasCustomInventoryName() ? this.customName : "twilightforest.container.furnace";
    }

    /**
     * Returns if the inventory is named
     */
    public boolean hasCustomInventoryName()
    {
        return this.customName != null && this.customName.length() > 0;
    }
    
    
    @Override
    public void readFromNBT(NBTTagCompound nbtTags)
    {
        super.readFromNBT(nbtTags);
        NBTTagList nbttaglist = nbtTags.getTagList("Items", 10);
        this.furnaceItemStacks = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            byte slot = nbttagcompound1.getByte("Slot");

            if (slot >= 0 && slot < this.furnaceItemStacks.length)
            {
                this.furnaceItemStacks[slot] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

        this.furnaceBurnTime = nbtTags.getShort("BurnTime");
        this.furnaceCookTime = nbtTags.getShort("CookTime");
        this.currentItemBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);

        if (nbtTags.hasKey("CustomName", 8))
        {
            this.customName = nbtTags.getString("CustomName");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTags)
    {
        super.writeToNBT(nbtTags);
        nbtTags.setShort("BurnTime", (short)this.furnaceBurnTime);
        nbtTags.setShort("CookTime", (short)this.furnaceCookTime);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.furnaceItemStacks.length; ++i)
        {
            if (this.furnaceItemStacks[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.furnaceItemStacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        nbtTags.setTag("Items", nbttaglist);

        if (this.hasCustomInventoryName())
        {
            nbtTags.setString("CustomName", this.customName);
        }
    }

    /**
     * Returns an integer between 0 and the passed value representing how close the current item is to being completely
     * cooked
     */
    @SideOnly(Side.CLIENT)
    public int getCookProgressScaled(int p_145953_1_)
    {
        return this.furnaceCookTime * p_145953_1_ / 200;
    }

    /**
     * Returns an integer between 0 and the passed value representing how much burn time is left on the current fuel
     * item, where 0 means that the item is exhausted and the passed value means that the item is fresh
     */
    @SideOnly(Side.CLIENT)
    public int getBurnTimeRemainingScaled(int p_145955_1_)
    {
        if (this.currentItemBurnTime == 0)
        {
            this.currentItemBurnTime = 200;
        }

        return this.furnaceBurnTime * p_145955_1_ / this.currentItemBurnTime;
    }

    /**
     * Furnace isBurning
     */
    public boolean isBurning()
    {
        return this.furnaceBurnTime > 0;
    }

    public void updateEntity()
    {
        boolean flag = this.furnaceBurnTime > 0;
        boolean flag1 = false;
        

        if (this.furnaceBurnTime > 0)
        {
            --this.furnaceBurnTime;
        }

        if (!this.worldObj.isRemote)
        {
            if (this.furnaceBurnTime != 0 || this.furnaceItemStacks[1] != null && this.furnaceItemStacks[0] != null)
            {
                if (this.furnaceBurnTime == 0 && this.canSmelt())
                {
                    this.currentItemBurnTime = this.furnaceBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);

                    if (this.furnaceBurnTime > 0)
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
                    int speedMultiplier = this.getCurrentSpeedMultiplier();
                    
                    //System.out.println("cooking with cinder furnace, speed multiplier = " + speedMultiplier);
                	
                    this.furnaceCookTime += speedMultiplier;

                    if (this.furnaceCookTime >= 200)
                    {
                        this.furnaceCookTime = 0;
                        this.smeltItem();
                        flag1 = true;
                    }
                }
                else
                {
                    this.furnaceCookTime = 0;
                }
            }

            // update block if needed
            if (flag != this.furnaceBurnTime > 0)
            {
                flag1 = true;
                BlockTFCinderFurnace.updateFurnaceBlockState(this.furnaceBurnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
            
            // occasionally cinderize nearby logs
            if (this.isBurning() && this.furnaceBurnTime % 5 == 0) {
            	this.cinderizeNearbyLog();
            }
        }

        if (flag1)
        {
            this.markDirty();
        }
    }

    /**
     * Turn a nearby log into a cinder log
     */
    private void cinderizeNearbyLog() {
    	Random rand = this.getWorld().rand;
    	
		int dx = rand.nextInt(2) - rand.nextInt(2);
		int dy = rand.nextInt(2) - rand.nextInt(2);
		int dz = rand.nextInt(2) - rand.nextInt(2);

		if (this.worldObj.blockExists(this.xCoord + dx, this.yCoord + dy, this.zCoord + dz)) {
			Block nearbyBlock = this.getWorld().getBlock(this.xCoord + dx, this.yCoord + dy, this.zCoord + dz);

			if (nearbyBlock != TFBlocks.cinderLog && this.isLog(nearbyBlock)) {
				this.getWorld().setBlock(this.xCoord + dx, this.yCoord + dy, this.zCoord + dz, TFBlocks.cinderLog, getCinderMetaFor(dx, dy, dz), 2);
				// special effect?
				this.getWorld().playAuxSFX(2004, this.xCoord + dx, this.yCoord + dy, this.zCoord + dz, 0);
				this.getWorld().playAuxSFX(2004, this.xCoord + dx, this.yCoord + dy, this.zCoord + dz, 0);
				this.getWorld().playSoundEffect(this.xCoord + dx + 0.5F, this.yCoord + dy + 0.5F, this.zCoord + dz + 0.5F, "fire.fire", 1.0F, 1.0F);
			}
		}
	}

    /**
     * What meta should we set the log block with the specified offset to?
     */
    private int getCinderMetaFor(int dx, int dy, int dz) {
		if (dz == 0 && dx != 0) {
			return dy == 0 ? 4 : 8;
		} else if (dx == 0 && dz != 0) {
			return dy == 0 ? 8 : 4;
		} else if (dx == 0 && dz == 0) {
			return 0;
		} else {
			return dy == 0 ? 0 : 12;
		}
		
	}

	/**
     * Check the ore dictionary to see if a nearby block is a wood log block
     */
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
			return (logs / factor) + (this.worldObj.rand.nextInt(factor) >= (logs % factor) ? 0 : 1);
		}
	}

    /**
     * Search around the block and return how many (out of a possible 26) of the blocks nearby are cinder log blocks
     */
    private int countNearbyLogs() {
    	int count = 0;
    	
		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				for (int dz = -1; dz <= 1; dz++) {
					if (this.worldObj.blockExists(this.xCoord + dx, this.yCoord + dy, this.zCoord + dz) && this.getWorld().getBlock(this.xCoord + dx, this.yCoord + dy, this.zCoord + dz) == TFBlocks.cinderLog) {
						count++;
					}
				}
			}
		}
		
        //System.out.println("cooking with cinder furnace, log factor = " + count);

		
		return count;
	}

    private boolean canSmelt()
    {
        if (this.furnaceItemStacks[SLOT_INPUT] == null)
        {
            return false;
        }
        else
        {
            ItemStack outputStack = FurnaceRecipes.instance().getSmeltingResult(this.furnaceItemStacks[SLOT_INPUT]);
            if (outputStack == null) {
            	return false;
            }
            if (this.furnaceItemStacks[SLOT_OUTPUT] == null) {
            	return true;
            }
            if (!this.furnaceItemStacks[SLOT_OUTPUT].isItemEqual(outputStack)) {
            	return false;
            }
            
            int resultStackSize = furnaceItemStacks[SLOT_OUTPUT].stackSize + this.getMaxOutputStacks(this.furnaceItemStacks[SLOT_INPUT], outputStack);
            
            return resultStackSize <= getInventoryStackLimit() && resultStackSize <= this.furnaceItemStacks[2].getMaxStackSize();
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

    public void smeltItem()
    {
        if (this.canSmelt())
        {
            ItemStack outputStack = FurnaceRecipes.instance().getSmeltingResult(this.furnaceItemStacks[SLOT_INPUT]);
            
            if (this.canMultiply(this.furnaceItemStacks[SLOT_INPUT], outputStack)) {
	            
	            // multiply number of results by our current smelt factor
	            int smeltMultiplier = this.getCurrentSmeltMultiplier();
	            
	            if (smeltMultiplier > 1) {
	            	// copy output stack
	            	outputStack = outputStack.copy();

	            	// we shouldn't run into max stack size problems, since we've already checked in canSmelt()
	            	outputStack.stackSize *= smeltMultiplier;
	            }
	            
	            
            }

            if (this.furnaceItemStacks[SLOT_OUTPUT] == null)
            {
                this.furnaceItemStacks[SLOT_OUTPUT] = outputStack.copy();
            }
            else if (this.furnaceItemStacks[SLOT_OUTPUT].getItem() == outputStack.getItem())
            {
                this.furnaceItemStacks[SLOT_OUTPUT].stackSize += outputStack.stackSize; // Forge BugFix: Results may have multiple items
            }

            --this.furnaceItemStacks[SLOT_INPUT].stackSize;

            if (this.furnaceItemStacks[SLOT_INPUT].stackSize <= 0)
            {
                this.furnaceItemStacks[SLOT_INPUT] = null;
            }
        }
    }
    

    public boolean canMultiply(ItemStack input, ItemStack output) {

    	// check the input ore ID for ores
    	int[] oreIDs = OreDictionary.getOreIDs(input);
    	for (int id : oreIDs) {
        	// does the input have an oredictionary result that starts with "ore"?
    		if (OreDictionary.getOreName(id).startsWith("ore")) {
    			//System.out.println("cinder furnace cook result, ore found, name is " + OreDictionary.getOreName(id));
    			
    			return true;
    		} else if (id == OreDictionary.getOreID("logWood")) {
    			//System.out.println("cinder furnace cook result, log found");
    			return true;
    		}
    	}
    	
    	// does the in
    	
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


    public static int getItemBurnTime(ItemStack p_145952_0_)
    {
        if (p_145952_0_ == null)
        {
            return 0;
        }
        else
        {
            Item item = p_145952_0_.getItem();

            if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.AIR)
            {
                Block block = Block.getBlockFromItem(item);

                if (block == Blocks.WOODEN_SLAB)
                {
                    return 150;
                }

                if (block.getMaterial() == Material.WOOD)
                {
                    return 300;
                }

                if (block == Blocks.COAL_BLOCK)
                {
                    return 16000;
                }
            }

            if (item instanceof ItemTool && ((ItemTool)item).getToolMaterialName().equals("WOOD")) return 200;
            if (item instanceof ItemSword && ((ItemSword)item).getToolMaterialName().equals("WOOD")) return 200;
            if (item instanceof ItemHoe && ((ItemHoe)item).getToolMaterialName().equals("WOOD")) return 200;
            if (item == Items.STICK) return 100;
            if (item == Items.COAL) return 1600;
            if (item == Items.LAVA_BUCKET) return 20000;
            if (item == Item.getItemFromBlock(Blocks.SAPLING)) return 100;
            if (item == Items.BLAZE_ROD) return 2400;
            return GameRegistry.getFuelValue(p_145952_0_);
        }
    }

    public static boolean isItemFuel(ItemStack p_145954_0_)
    {
        return getItemBurnTime(p_145954_0_) > 0;
    }

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;

	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
        return slot == SLOT_OUTPUT ? false : (slot == SLOT_FUEL ? TileEntityFurnace.isItemFuel(itemStack) : true);
	}

	@Override
	public int[] getSlotsForFace(EnumFacing p_94128_1_) {
        return p_94128_1_ == EnumFacing.DOWN ? slotsBottom : (p_94128_1_ == EnumFacing.UP ? slotsTop : slotsSides);
	}

    /**
     * Returns true if automation can insert the given item in the given slot from the given side. Args: Slot, item,
     * side
     */
    public boolean canInsertItem(int slot, ItemStack itemStack, int side)
    {
        return this.isItemValidForSlot(slot, itemStack);
    }

    /**
     * Returns true if automation can extract the given item in the given slot from the given side. Args: Slot, item,
     * side
     */
    public boolean canExtractItem(int slot, ItemStack itemStack, int side)
    {
        return side != SLOT_INPUT || slot != SLOT_FUEL || itemStack.getItem() == Items.BUCKET;
    }

}
