package twilightforest.block;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;
import twilightforest.item.TFItems;

public class BlockTFRipeTorchCluster extends BlockTFTrollRoot {

	protected BlockTFRipeTorchCluster() {
		super();

        this.setBlockTextureName(TwilightForestMod.ID + ":ripe_torch_cluster");
        this.setLightLevel(1.0F);

	}
	
    public Item getItemDropped(int meta, Random rand, int fortune)
    {
        return TFItems.torchberries;
    }
    
    /**
     * Metadata and fortune sensitive version, this replaces the old (int meta, Random rand)
     * version in 1.1.
     *
     * @param meta Blocks Metadata
     * @param fortune Current item fortune level
     * @param random Random number generator
     * @return The number of items to drop
     */
    public int quantityDropped(int meta, int fortune, Random random) {
        return quantityDroppedWithBonus(fortune, random);
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random rand)
    {
        return 4 + rand.nextInt(5);
    }

    /**
     * Returns the usual quantity dropped by the block plus a bonus of 1 to 'i' (inclusive).
     */
    public int quantityDroppedWithBonus(int bonus, Random rand)
    {
        if (bonus > 0 && Item.getItemFromBlock(this) != this.getItemDropped(0, rand, bonus))
        {
            int j = rand.nextInt(bonus + 2) - 1;

            if (j < 0)
            {
                j = 0;
            }

            return this.quantityDropped(rand) * (j + 1);
        }
        else
        {
            return this.quantityDropped(rand);
        }
    }
    
    /**
     * Called when the player destroys a block with an item that can harvest it. (i, j, k) are the coordinates of the
     * block and l is the block's subtype/damage.
     */
    public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta)
    {
    	// do not call normal harvest if the player is shearing
        if (world.isRemote || player.getCurrentEquippedItem() == null || player.getCurrentEquippedItem().getItem() != Items.shears)
        {
            super.harvestBlock(world, player, x, y, z, meta);
        }
    }
}
