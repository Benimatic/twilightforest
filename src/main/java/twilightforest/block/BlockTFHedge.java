package twilightforest.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import twilightforest.TwilightForestMod;
import twilightforest.item.TFItems;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTFHedge extends BlockLeaves {
	
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
	
    @Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, World world, BlockPos pos)
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
    
    @Override
	public boolean isOpaqueCube(IBlockState state)
    {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess par1IBlockAccess, BlockPos pos, EnumFacing side)
    {
        Block i1 = par1IBlockAccess.getBlock(par2, par3, par4);
        return !this.field_150121_P && i1 == this ? false : super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
    }

    @Override
	public int damageDropped(IBlockState state) {
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
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
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
	public void onEntityWalk(World world, BlockPos pos, Entity entity)
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
	public void onBlockClicked(World world, BlockPos pos, EntityPlayer entityplayer)
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
    
    @Override
	public void harvestBlock(World world, EntityPlayer entityplayer, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack)
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
    
    
	@Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random)
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
        Vec3d position = new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);
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

    @Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing side) {
    	int metadata = world.getBlockMetadata(x, y, z);
		return metadata == 1 ? 1 : 0;
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing side) {
		return 0;
	}

    @Override
	public int quantityDropped(Random par1Random)
    {
    	return par1Random.nextInt(40) == 0 ? 1 : 0;
    }

    @Override
	public Item getItemDropped(IBlockState state, Random par2Random, int par3)
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

    @Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(this, 1, world.getBlockMetadata(x, y, z));
    }

    @Override
	public void dropBlockAsItemWithChance(World par1World, BlockPos pos, IBlockState state, float par6, int fortune)
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

