package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import twilightforest.item.TFItems;

public class BlockTFBurntThorns extends BlockTFThorns {

	protected BlockTFBurntThorns() {
		super();
		this.setHardness(0.01F);
		this.setResistance(0.0F);
		this.setSoundType(SoundType.SAND);
		this.setCreativeTab(TFItems.creativeTab);
		
		this.setNames(new String[] {"burnt"});

	}

    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {
    	// dissolve
    	if (!world.isRemote && entity instanceof EntityLivingBase) {
	    	int metadata = world.getBlockMetadata(x, y, z);
	    	world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(this) + (metadata << 12));
	    	world.setBlockToAir(x, y, z);
    	}
    }

    /**
     * Break normally
     */
    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z) {
    	world.setBlockToAir(x, y, z);
    	return true;
    }
    
    @Override
    public boolean canSustainLeaves(IBlockAccess world, int x, int y, int z)
    {
        return false;
    }
    
	/**
	 * no need for leaf decay, I think
	 */
    public void breakBlock(World world, int x, int y, int z, Block logBlock, int metadata)
    {
    	;
    }
}
