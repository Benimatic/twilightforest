package twilightforest.item;

import java.util.Random;

import twilightforest.block.TFBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemLilyPad;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;


public class ItemBlockTFHugeLilyPad extends ItemLilyPad {
	
	Random rand = new org.bogdang.modifications.random.XSTR();

	public ItemBlockTFHugeLilyPad(Block block) {
		super(block);
	}

	
    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
    	MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, true);

        if (movingobjectposition == null) {
            return itemStack;
        } else {
            if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
            {
                int x = movingobjectposition.blockX;
                int y = movingobjectposition.blockY;
                int z = movingobjectposition.blockZ;
                
                // 2x2 lily pads go in a larger grid
            	int bx = (x >> 1) << 1;
            	int by = y;
            	int bz = (z >> 1) << 1;
            	
            	if (this.canPlacePadOn(itemStack, world, player, bx, by, bz) && this.canPlacePadOn(itemStack, world, player, bx + 1, by, bz) 
            			&& this.canPlacePadOn(itemStack, world, player, bx, by, bz + 1) && this.canPlacePadOn(itemStack, world, player, bx + 1, by, bz + 1)) {
            		
            		// this seems like a difficult way to generate 2 pseudorandom bits
            		rand.setSeed(8890919293L);
            		rand.setSeed((bx * rand.nextLong()) ^ (bz * rand.nextLong()) ^ 8890919293L);
            		int orient = rand.nextInt(4) << 2;
            		
                    world.setBlock(bx, by + 1, bz, TFBlocks.hugeLilyPad, 0 | orient, 2);
                    world.setBlock(bx + 1, by + 1, bz, TFBlocks.hugeLilyPad, 1 | orient, 2);
                    world.setBlock(bx + 1, by + 1, bz + 1, TFBlocks.hugeLilyPad, 2 | orient, 2);
                    world.setBlock(bx, by + 1, bz + 1, TFBlocks.hugeLilyPad, 3 | orient, 2);
                    
                    if (!player.capabilities.isCreativeMode)
                    {
                        --itemStack.stackSize;
                    }

            	}
            }

            return itemStack;
        }
    }
    
    public boolean canPlacePadOn(ItemStack itemStack, World world, EntityPlayer player, int x, int y, int z) {
        if (!world.canMineBlock(player, x, y, z)) {
            return false;
        }
        
        if (!player.canPlayerEdit(x, y, z, 1, itemStack)) {
            return false;
        }
        
        return world.getBlock(x, y, z).getMaterial() == Material.water && world.getBlockMetadata(x, y, z) == 0 && world.isAirBlock(x, y + 1, z);
        
    }

}
