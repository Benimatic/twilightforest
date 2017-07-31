package twilightforest.block;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.common.util.ForgeDirection;
import twilightforest.TwilightForestMod;
import twilightforest.item.TFItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTFPlant extends BlockBush implements IShearable {

    //boolean[] isGrassColor = {false, false, false, false, true, true, false, false, true, false, true, false, false, false, false, false};
    BitSet isGrassColor;
    //int[] lightValue = {0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 8, 0, 0};//Bogdan-G: index - value: 9 - 3, 13 - 8; range of values 0-15? if true byte[] enough, but will lead to int; array identical for everyone class BlockTFPlant?
    
	private IIcon[] icons;
	private String[] iconNames = new String[] {null, null, null, "mosspatch", "mayapple", "cloverpatch", null, null, "fiddlehead", "mushgloom", null, null, null, "torchberry", "rootstrand", null};
	public static IIcon mayappleSide;
    
    public static final int META_MOSSPATCH = 3;
    public static final int META_MAYAPPLE = 4;
    public static final int META_CLOVERPATCH = 5;
    public static final int META_FIDDLEHEAD = 8;
    public static final int META_MUSHGLOOM = 9;
    public static final int META_FORESTGRASS = 10;
    public static final int META_DEADBUSH = 11;
    public static final int META_TORCHBERRY = 13;
	public static final int META_ROOT_STRAND = 14;

	
	protected BlockTFPlant() {
		super(Material.plants);
        this.setTickRandomly(true);
        float var3 = 0.4F;
        this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, 0.8F, 0.5F + var3);
        this.setHardness(0.0F);
        this.setStepSound(Block.soundTypeGrass);
		this.setCreativeTab(TFItems.creativeTab);
	boolean[] isGrassColor_oldtype = {false, false, false, false, true, true, false, false, true, false, true, false, false, false, false, false};
	this.isGrassColor=new BitSet(isGrassColor_oldtype.length);
	for (int i=0;i<isGrassColor_oldtype.length;i++) if (isGrassColor_oldtype[i]) this.isGrassColor.set(i);
	}

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    @Override
	public IIcon getIcon(int side, int metadata)
    {
        return this.icons[metadata];
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        this.icons = new IIcon[iconNames.length];

        for (int i = 0; i < this.icons.length; ++i)
        {
        	if (this.iconNames[i] != null)
        	{
        		this.icons[i] = par1IconRegister.registerIcon(TwilightForestMod.ID + ":" + iconNames[i]);
        	}
        }
        
        this.icons[META_FORESTGRASS] = Blocks.tallgrass.getIcon(2, 1);
        this.icons[META_DEADBUSH] = Blocks.deadbush.getBlockTextureFromSide(2);
        
        BlockTFPlant.mayappleSide = par1IconRegister.registerIcon(TwilightForestMod.ID + ":mayapple_side");
    }
    @Override
	public int getBlockColor()
    {
        double var1 = 0.5D;
        double var3 = 1.0D;
        return ColorizerGrass.getGrassColor(var1, var3);
    }
    
	/**
	 * Schedule an update to try to get lighting right
	 */
	@Override
	public void onBlockAdded(World world, int i, int j, int k) {
		world.scheduleBlockUpdate(i, j, k, this, world.rand.nextInt(50) + 20);
	}
	
    @Override
	public boolean canReplace(World par1World, int x, int y, int z, int par5, ItemStack par6ItemStack)
    {
    	// we need to get the metadata
    	Block blockAt = par1World.getBlock(x, y, z);
    	
        return (blockAt == Blocks.air || blockAt.getMaterial().isReplaceable()) && canBlockStay(par1World, x, y, z, par6ItemStack.getItemDamage());
    }

	/**
     * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
     */
    @Override
	public boolean canBlockStay(World world, int x, int y, int z) {
    	int meta = world.getBlockMetadata(x, y, z);
    	
    	return canBlockStay(world, x, y, z, meta);
    }
    
	/**
     * Can this block stay at this position?  with metadata
     */
	public boolean canBlockStay(World world, int x, int y, int z, int meta) {
    	
    	//System.out.println("Can block stay? meta is " + meta);
        Block soil = world.getBlock(x, y - 1, z);
    	
       	switch (meta) {
    	case META_TORCHBERRY :
    	case META_ROOT_STRAND :
    		return BlockTFPlant.canPlaceRootBelow(world, x, y + 1, z);
    	case 0: // let's make this happen
    	case META_FORESTGRASS:
    	case META_DEADBUSH:
    		return (soil != null && soil.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, this));
    	case META_MUSHGLOOM:
    	case META_MOSSPATCH:
    		return soil != null && soil.isSideSolid(world, x, y, z, ForgeDirection.UP);
     	default :
            return (world.getFullBlockLightValue(x, y, z) >= 3 || world.canBlockSeeTheSky(x, y, z)) && 
                    (soil != null && soil.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, this));
    	}
    }
    

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    @Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int x, int y, int z)
    {
        int meta = par1IBlockAccess.getBlockMetadata(x, y, z);

        if (meta == META_MOSSPATCH)
        {
            long seed = x * 3129871L ^ y * 116129781L ^ z;
            seed = seed * seed * 42317861L + seed * 11L;
            
            int xOff0 = (int) (seed >> 12 & 3L);
            int xOff1 = (int) (seed >> 15 & 3L);
            int zOff0 = (int) (seed >> 18 & 3L);
            int zOff1 = (int) (seed >> 21 & 3L);
            
            boolean xConnect0 = par1IBlockAccess.getBlock(x + 1, y, z) == this && par1IBlockAccess.getBlockMetadata(x + 1, y, z) == META_MOSSPATCH;
        	boolean xConnect1 = par1IBlockAccess.getBlock(x - 1, y, z) == this && par1IBlockAccess.getBlockMetadata(x - 1, y, z) == META_MOSSPATCH;
        	boolean zConnect0 = par1IBlockAccess.getBlock(x, y, z + 1) == this && par1IBlockAccess.getBlockMetadata(x, y, z + 1) == META_MOSSPATCH;
        	boolean zConnect1 = par1IBlockAccess.getBlock(x, y, z - 1) == this && par1IBlockAccess.getBlockMetadata(x, y, z - 1) == META_MOSSPATCH;
        	
            this.setBlockBounds(xConnect1 ? 0F : (1F + xOff1) / 16F, 0.0F, zConnect1 ? 0F : (1F + zOff1) / 16F, 
            		xConnect0 ? 1F : (15F - xOff0) / 16F, 1F / 16F, zConnect0 ? 1F : (15F - zOff0) / 16F);

        }
        else if (meta == META_CLOVERPATCH)
        {
            long seed = x * 3129871L ^ y * 116129781L ^ z;
            seed = seed * seed * 42317861L + seed * 11L;
            
            int xOff0 = (int) (seed >> 12 & 3L);
            int xOff1 = (int) (seed >> 15 & 3L);
            int zOff0 = (int) (seed >> 18 & 3L);
            int zOff1 = (int) (seed >> 21 & 3L);
            
            int yOff0 = (int) (seed >> 24 & 1L);
            int yOff1 = (int) (seed >> 27 & 1L);
            

        	boolean xConnect0 = par1IBlockAccess.getBlock(x + 1, y, z) == this && par1IBlockAccess.getBlockMetadata(x + 1, y, z) == META_CLOVERPATCH;
        	boolean xConnect1 = par1IBlockAccess.getBlock(x - 1, y, z) == this && par1IBlockAccess.getBlockMetadata(x - 1, y, z) == META_CLOVERPATCH;
        	boolean zConnect0 = par1IBlockAccess.getBlock(x, y, z + 1) == this && par1IBlockAccess.getBlockMetadata(x, y, z + 1) == META_CLOVERPATCH;
        	boolean zConnect1 = par1IBlockAccess.getBlock(x, y, z - 1) == this && par1IBlockAccess.getBlockMetadata(x, y, z - 1) == META_CLOVERPATCH;
        	
            this.setBlockBounds(xConnect1 ? 0F : (1F + xOff1) / 16F, 0.0F, zConnect1 ? 0F : (1F + zOff1) / 16F, 
            		xConnect0 ? 1F : (15F - xOff0) / 16F, (1F + yOff0 + yOff1) / 16F, zConnect0 ? 1F : (15F - zOff0) / 16F);
        	
            //this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2F / 16F, 1.0F);
        }
        else if (meta == META_MAYAPPLE)
        {
            this.setBlockBounds(4F / 16F, 0, 4F / 16F, 13F / 16F, 6F / 16F, 13F / 16F);
        }
        else
        {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
    }
    

	/**
     * Returns the color this block should be rendered. Used by leaves.
     */
    @Override
	public int getRenderColor(int par1)
    {
        return isGrassColor.get(par1) ? ColorizerFoliage.getFoliageColorBasic() : 0xFFFFFF;
    }

    /**
     * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
     * when first determining what to render.
     */
    @Override
	public int colorMultiplier(IBlockAccess par1IBlockAccess, int x, int y, int z)
    {
    	int meta = par1IBlockAccess.getBlockMetadata(x, y, z);
    	return isGrassColor.get(meta) ? par1IBlockAccess.getBiomeGenForCoords(x, z).getBiomeGrassColor(x, y, z) : 0xFFFFFF; 
    }
    
    
    
    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    @Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int x, int y, int z)
    {
    	par1World.getBlockMetadata(x, y, z);
    	
    	return null;
    }
    
    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    @Override
	public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    @Override
	public boolean renderAsNormalBlock()
    {
        return false;
    }
	
    /**
     * The type of render function that is called for this block
     */
	@Override
	public int getRenderType() {
		return TwilightForestMod.proxy.getPlantBlockRenderID();
	}
	
    /**
     * Ticks the block if it's been scheduled
     */
    @Override
	public void updateTick(World par1World, int x, int y, int z, Random par5Random)
    {
    	/*int meta = par1World.getBlockMetadata(x, y, z);
		if (par1World.getBlockLightValue(x, y, z) < lightValue[meta]) {
			//par1World.updateLightByType(EnumSkyBlock.Block, x, y, z);
			//par1World.markBlockForUpdate(x, y, z); // do we need this now?
		}*/

    }
	
    /**
     * Get a light value for this block, normal ranges are between 0 and 15
     * 
     * @param world The current world
     * @param x X Position
     * @param y Y position
     * @param z Z position
     * @return The light value
     */
	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
    	int meta = world.getBlockMetadata(x, y, z);
    	return meta==9 ? 3 : meta==13 ? 8 : 0;//lightValue[meta]; 
	}
	
    
    /**
     * Root-specific method.
     * @return true if the root can be placed in the block immediately below this one
     */
    public static boolean canPlaceRootBelow(World world, int x, int y, int z) {
    	Block blockID = world.getBlock(x, y, z);
    	if (blockID != null && (blockID.getMaterial() == Material.ground || blockID.getMaterial() == Material.grass)) {
    		// can always hang below dirt blocks
    		return true;
    	}
    	else {
    		int blockMeta = world.getBlockMetadata(x, y, z);
    		return (blockID == TFBlocks.plant && blockMeta == META_ROOT_STRAND) || (blockID == TFBlocks.root && blockMeta == BlockTFRoots.ROOT_META);
    	}
    	
    }
	  

//    /**
//     * Drops the block items with a specified chance of dropping the specified items
//     */
//    public void dropBlockAsItemWithChance(World var1, int var2, int var3, int var4, int var5, float var6, int var7)
//    {
//        super.dropBlockAsItemWithChance(var1, var2, var3, var4, var5, var6, var7);
//
//        if (!var1.isRemote && var5 == 1)
//        {
//            this.dropBlockAsItem_do(var1, var2, var3, var4, new ItemStack(mod_ThaumCraft.itemComponents, 1, 2));
//        }
//
//        if (!var1.isRemote && var5 == 3)
//        {
//            this.dropBlockAsItem_do(var1, var2, var3, var4, new ItemStack(mod_ThaumCraft.itemPlants, 1, 3));
//        }
//
//        if (!var1.isRemote && (var5 == 2 || var5 == 4) && var1.rand.nextInt(10) == 0)
//        {
//            this.dropBlockAsItem_do(var1, var2, var3, var4, new ItemStack(mod_ThaumCraft.itemArtifactTainted, 1, 0));
//        }
//    }
//    
    
    /**
     * This returns a complete list of items dropped from this block.
     * 
     * @param world The current world
     * @param x X Position
     * @param Y Y Position
     * @param Z Z Position
     * @param metadata Current metadata
     * @param fortune Breakers fortune level
     * @return A ArrayList containing all items this block drops
     */
    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune)
    {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        
        //TODO: this needs to not drop if the player is shearing!  Grrr!
        
        // blah
        switch (meta) {
        case META_TORCHBERRY :
        	ret.add(new ItemStack(TFItems.torchberries));
        	break;
        case META_MOSSPATCH :
        case META_MAYAPPLE :
        case META_CLOVERPATCH :
        case META_ROOT_STRAND :
        case META_FORESTGRASS :
        case META_DEADBUSH :
        	// Just don't drop anythin
        	break;
        default :
        	ret.add(new ItemStack(this, 1, meta));
        	break;
        }

        return ret;
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    @Override
	public int damageDropped(int par1)
    {
        return par1;
    }

	/**
     * Checks if the object is currently shearable
     * Example: Sheep return false when they have no wool
     *
     * @param item The itemstack that is being used, Possible to be null
     * @param world The current world
     * @param x The X Position
     * @param y The Y Position
     * @param z The Z Position
     * @return If this is shearable, and onSheared should be called.
     */
	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, int x, int y, int z) {
		// TODO Auto-generated method stub
		return true;
	}
	
    /**
     * Performs the shear function on this object.
     * This is called for both client, and server.
     * The object should perform all actions related to being sheared,
     * except for dropping of the items.
     *
     * Returns a list of items that resulted from the shearing process.
     *
     * For entities, they should trust there internal location information
     * over the values passed into this function.
     *
     * @param item The itemstack that is being used, Possible to be null
     * @param world The current world
     * @param x The X Position
     * @param y The Y Position
     * @param z The Z Position
     * @param fortune The fortune level of the shears being used
     * @return A ArrayList containing all items from this shearing. Possible to be null.
     */
	@Override
	public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, int x, int y, int z, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        ret.add(new ItemStack(this, 1, world.getBlockMetadata(x, y, z)));
        //world.setBlockToAir(x, y, z);
        return ret;
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

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(this, 1, META_MOSSPATCH));
        par3List.add(new ItemStack(this, 1, META_MAYAPPLE));
        //par3List.add(new ItemStack(this, 1, META_CLOVERPATCH));
        par3List.add(new ItemStack(this, 1, 8));
        par3List.add(new ItemStack(this, 1, 9));
        par3List.add(new ItemStack(this, 1, META_FORESTGRASS));
        par3List.add(new ItemStack(this, 1, META_DEADBUSH));
        par3List.add(new ItemStack(this, 1, 13));
        par3List.add(new ItemStack(this, 1, 14));

    }
	
	/**
	 * 
	 */
	@Override
	public void setBlockBoundsForItemRender() {
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}
	
    @Override
    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z)
    {
    	int meta = world.getBlockMetadata(x, y, z);

    	switch (meta)
    	{
    	case META_MOSSPATCH :
    	case META_MUSHGLOOM :
    		return EnumPlantType.Cave;
    	default :
    		return EnumPlantType.Plains;
    	}
    }

    @Override
    public Block getPlant(IBlockAccess world, int x, int y, int z)
    {
        return this;
    }

    @Override
    public int getPlantMetadata(IBlockAccess world, int x, int y, int z)
    {
        return world.getBlockMetadata(x, y, z);
    }
	

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    @Override
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(World par1World, int x, int y, int z, Random par5Random)
    {
        super.randomDisplayTick(par1World, x, y, z, par5Random);

    	int meta = par1World.getBlockMetadata(x, y, z);

        if (meta == META_MOSSPATCH && par5Random.nextInt(10) == 0)
        {
            par1World.spawnParticle("townaura", x + par5Random.nextFloat(), y + 0.1F, z + par5Random.nextFloat(), 0.0D, 0.0D, 0.0D);
        }
    
    }

    @Override
    public int tickRate(World p_149738_1_)
    {
        return 20;
    }
}
