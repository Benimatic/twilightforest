package twilightforest.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import twilightforest.TwilightForestMod;
import twilightforest.item.TFItems;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTFHedge extends BlockLeavesBase {
	
	public int damageDone; 

	public static IIcon sprHedge;
	public static IIcon sprDarkwoodLeaves;

	protected BlockTFHedge() {
		super(Material.CACTUS, false);
		this.damageDone = 3;
		this.setHardness(2F);
		this.setResistance(10F);
		this.setStepSound(Block.soundTypeGrass);
		this.setCreativeTab(TFItems.creativeTab);
	}
	
    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    @Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
    	int meta = world.getBlockMetadata(x, y, z);
    	switch (meta) {
    	case 0:
    		float f = 0.0625F;
        	return new AxisAlignedBB(x, y, z, x + 1, y + 1 - f, z + 1);
    	default :
    	case 1 :
    		return new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1);
    	}
    	
    }
    
    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    @Override
	public boolean isOpaqueCube()
    {
        return true;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
     * coordinates.  Args: blockAccess, x, y, z, side
     */
    @Override
    public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        Block i1 = par1IBlockAccess.getBlock(par2, par3, par4);
        return !this.field_150121_P && i1 == this ? false : super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
    }
    
    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    @Override
	public int damageDropped(int meta) {
    	if (meta == 2) {
    		// temporary workaround
    		meta = 0;
    	}

    	if (meta == 1) {
    		// darkwood sapling for darkwood leaves
    		return 3;
    	}

		return meta;
	}
    
    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    @Override
	public IIcon getIcon(int side, int meta)
    {
    	switch (meta) {
    	case 1:
    		return BlockTFHedge.sprDarkwoodLeaves;
    	default :
    	case 0 :
    		return BlockTFHedge.sprHedge;
    	}
    }

    @Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {
    	int meta = world.getBlockMetadata(x, y, z);

    	if (meta == 2) {
    		// temporary workaround
    		meta = 0;
    	}

    	if (meta == 0 && shouldDamage(entity)) {
    		entity.attackEntityFrom(DamageSource.cactus, damageDone);
    	}
    }

    @Override
	public void onEntityWalking(World world, int x, int y, int z, Entity entity)
    {
    	int meta = world.getBlockMetadata(x, y, z);
    	if (meta == 2) {
    		// temporary workaround
    		meta = 0;
    	}

    	if (meta == 0 && shouldDamage(entity)) {
    		entity.attackEntityFrom(DamageSource.cactus, damageDone);
    	}
    }

    @Override
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer entityplayer)
    {
    	int meta = world.getBlockMetadata(x, y, z);
    	if (meta == 2) {
    		// temporary workaround
    		meta = 0;
    	}

    	if (meta == 0 && !world.isRemote) {
    		world.scheduleBlockUpdate(x, y, z, this, 10);
    	}
    }
    
    /**
     * Called when the player destroys a block with an item that can harvest it. (i, j, k) are the coordinates of the
     * block and l is the block's subtype/damage.
     */
    @Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int meta)
    {
    	super.harvestBlock(world, entityplayer, i, j, k, meta);
    	if (meta == 2) {
    		// temporary workaround
    		meta = 0;
    	}
    	if (meta == 0) {
    		entityplayer.attackEntityFrom(DamageSource.cactus, damageDone);
    	}
    }
    
    
    /**
     * This should be called 5 ticks after we've received a click event from a player.
     * If we see player nearby swinging at a hedge block, prick them
     */
    @SuppressWarnings("unchecked")
	@Override
    public void updateTick(World world, int x, int y, int z, Random random)
    {
    	double range = 4.0; // do we need to get this with a better method than hardcoding it?

    	// find players within harvest range
    	List<EntityPlayer> nearbyPlayers = world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1).expand(range, range, range));

    	// are they swinging?
    	for (EntityPlayer player : nearbyPlayers) {
    		if (player.isSwingInProgress) {
     			// are they pointing at this block?
    			RayTraceResult mop = getPlayerPointVec(world, player, range);

    			if (mop != null && world.getBlock(mop.blockX, mop.blockY, mop.blockZ) == this) {
    				// prick them!  prick them hard!
    				player.attackEntityFrom(DamageSource.cactus, damageDone);

    				// trigger this again!
    				world.scheduleBlockUpdate(x, y, z, this, 10);
    			}
    		}
    	}
    }

	
	/**
	 * What block is the player pointing the wand at?
	 * 
	 * This very similar to player.rayTrace, but that method is not available on the server.
	 * 
	 * @return
	 */
	private RayTraceResult getPlayerPointVec(World worldObj, EntityPlayer player, double range) {
        Vec3d position = Vec3d.createVectorHelper(player.posX, player.posY + player.getEyeHeight(), player.posZ);
        Vec3d look = player.getLook(1.0F);
        Vec3d dest = position.addVector(look.xCoord * range, look.yCoord * range, look.zCoord * range);
        return worldObj.rayTraceBlocks(position, dest);
	}
	
//	/**
//	 * Is the player swinging, server version.  Ugggh.  Okay, this sucks and we don't really need it
//	 */
//	private boolean isPlayerSwinging(EntityPlayer player) {
//		if (player instanceof EntityPlayerMP) {
//			ItemInWorldManager iiwm = ((EntityPlayerMP)player).itemInWorldManager;
//			// curblockDamage > initialDamage
//			return ((Integer)ModLoader.getPrivateValue(ItemInWorldManager.class, iiwm, 9)).intValue() > ((Integer)ModLoader.getPrivateValue(ItemInWorldManager.class, iiwm, 5)).intValue();
//			
////			for (int i = 0; i < ItemInWorldManager.class.getDeclaredFields().length; i++) {
////				// if we find a boolean in here, just assume that's it for the time being
////				if (ModLoader.getPrivateValue(ItemInWorldManager.class, iiwm, i) instanceof Boolean) {
////					return ((Boolean)ModLoader.getPrivateValue(ItemInWorldManager.class, iiwm, i)).booleanValue();
////				}
////			}
//		}
//		// we didn't find it
//		return false;
//	}

    
    private boolean shouldDamage(Entity entity) {
    	return !(entity instanceof EntitySpider) && !(entity instanceof EntityItem) && !entity.doesEntityNotTriggerPressurePlate();
    }
    
    /**
     * Chance that fire will spread and consume this block.
     * 300 being a 100% chance, 0, being a 0% chance.
     * 
     * @param world The current world
     * @param x The blocks X position
     * @param y The blocks Y position
     * @param z The blocks Z position
     * @param metadata The blocks current metadata
     * @param face The face that the fire is coming from
     * @return A number ranging from 0 to 300 relating used to determine if the block will be consumed by fire
     */
    @Override
	public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
    	int metadata = world.getBlockMetadata(x, y, z);
		return metadata == 1 ? 1 : 0;
	}

    /**
     * Called when fire is updating on a neighbor block.
     * The higher the number returned, the faster fire will spread around this block.
     * 
     * @param world The current world
     * @param x The blocks X position
     * @param y The blocks Y position
     * @param z The blocks Z position
     * @param metadata The blocks current metadata
     * @param face The face that the fire is coming from
     * @return A number that is used to determine the speed of fire growth around the block
     */
	@Override
	public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
		return 0;
	}

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    @Override
	public int quantityDropped(Random par1Random)
    {
    	return par1Random.nextInt(40) == 0 ? 1 : 0;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
	public Item getItemDropped(int meta, Random par2Random, int par3)
    {
    	if (meta == 1)
    	{
    		return Item.getItemFromBlock(TFBlocks.sapling);
    	}
    	else
    	{
    		return null;
    	}
    }

    /**
     * Called when a user uses the creative pick block button on this block
     *
     * @param target The full target the player is looking at
     * @return A ItemStack to add to the player's inventory, Null if nothing should be added.
     */
    @Override
	public ItemStack getPickBlock(RayTraceResult target, World world, int x, int y, int z)
    {
        return new ItemStack(this, 1, world.getBlockMetadata(x, y, z));
    }

    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    @Override
	public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int meta, float par6, int fortune)
    {
    	if (!par1World.isRemote && meta == 1)
    	{
    		if (par1World.rand.nextInt(40) == 0)
    		{
    			Item var9 = this.getItemDropped(meta, par1World.rand, fortune);
    			this.dropBlockAsItem(par1World, par2, par3, par4, new ItemStack(var9, 1, this.damageDropped(meta)));
    		}
    	}
    }
    
	/**
	 * Properly register icon source
	 */
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
    	BlockTFHedge.sprHedge = par1IconRegister.registerIcon(TwilightForestMod.ID + ":hedge");
    	BlockTFHedge.sprDarkwoodLeaves = par1IconRegister.registerIcon(TwilightForestMod.ID + ":darkwood_leaves");
    }

}

