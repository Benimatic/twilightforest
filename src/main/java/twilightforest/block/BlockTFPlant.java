package twilightforest.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.common.util.ForgeDirection;
import twilightforest.TwilightForestMod;
import twilightforest.block.enums.PlantVariant;
import twilightforest.item.TFItems;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTFPlant extends BlockBush implements IShearable {

    public static final PropertyEnum<PlantVariant> VARIANT = PropertyEnum.create("variant", PlantVariant.class);

    boolean[] isGrassColor = {false, false, false, false, true, true, false, false, true, false, true, false, false, false, false, false};
    int[] lightValue = {0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 8, 0, 0};
    
	private IIcon[] icons;
	private String[] iconNames = new String[] {null, null, null, "mosspatch", "mayapple", "cloverpatch", null, null, "fiddlehead", "mushgloom", null, null, null, "torchberry", "rootstrand", null};
	public static IIcon mayappleSide;
    
	protected BlockTFPlant() {
		super(Material.PLANTS);
        this.setTickRandomly(true);
        float var3 = 0.4F;
        this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, 0.8F, 0.5F + var3);
        this.setHardness(0.0F);
		this.setCreativeTab(TFItems.creativeTab);
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
        
        this.icons[META_FORESTGRASS] = Blocks.TALLGRASS.getIcon(2, 1);
        this.icons[META_DEADBUSH] = Blocks.DEADBUSH.getBlockTextureFromSide(2);
        
        BlockTFPlant.mayappleSide = par1IconRegister.registerIcon(TwilightForestMod.ID + ":mayapple_side");
    }
    @Override
	public int getBlockColor()
    {
        double var1 = 0.5D;
        double var3 = 1.0D;
        return ColorizerGrass.getGrassColor(var1, var3);
    }
    
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		world.scheduleBlockUpdate(i, j, k, this, world.rand.nextInt(50) + 20);
	}
	
    @Override
	public boolean canReplace(World par1World, BlockPos pos, EnumFacing side, ItemStack par6ItemStack)
    {
    	// we need to get the metadata
    	Block blockAt = par1World.getBlock(x, y, z);
    	
        return (blockAt == Blocks.AIR || blockAt.getMaterial().isReplaceable()) && canBlockStay(par1World, x, y, z, par6ItemStack.getItemDamage());
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
    
    @Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int x, int y, int z)
    {
        int meta = par1IBlockAccess.getBlockMetadata(x, y, z);

        if (meta == META_MOSSPATCH)
        {
            long seed = x * 3129871 ^ y * 116129781L ^ z;
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
            long seed = x * 3129871 ^ y * 116129781L ^ z;
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
        return isGrassColor[par1] ? ColorizerFoliage.getFoliageColorBasic() : 0xFFFFFF;
    }

    /**
     * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
     * when first determining what to render.
     */
    @Override
	public int colorMultiplier(IBlockAccess par1IBlockAccess, int x, int y, int z)
    {
    	int meta = par1IBlockAccess.getBlockMetadata(x, y, z);
    	return isGrassColor[meta] ? par1IBlockAccess.getBiomeGenForCoords(x, z).getBiomeGrassColor(x, y, z) : 0xFFFFFF; 
    }
    
    @Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, World par1World, BlockPos pos)
    {
    	return NULL_AABB;
    }
    
    @Override
	public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

	@Override
	public int getRenderType() {
		return TwilightForestMod.proxy.getPlantBlockRenderID();
	}
	
    @Override
	public void updateTick(World par1World, BlockPos pos, IBlockState state, Random par5Random)
    {
    	int meta = par1World.getBlockMetadata(x, y, z);
		if (par1World.getBlockLightValue(x, y, z) < lightValue[meta]) {
			//par1World.updateLightByType(EnumSkyBlock.Block, x, y, z);
			//par1World.markBlockForUpdate(x, y, z); // do we need this now?
		}

    }

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
    	int meta = world.getBlockMetadata(x, y, z);
    	return lightValue[meta]; 
	}
    
    /**
     * Root-specific method.
     * @return true if the root can be placed in the block immediately below this one
     */
    public static boolean canPlaceRootBelow(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
    	if (state.getMaterial() == Material.GROUND || state.getMaterial() == Material.GRASS) {
    		// can always hang below dirt blocks
    		return true;
    	}
    	else {
    		return (state.getBlock() == TFBlocks.plant && state.getValue(BlockTFPlant.VARIANT) == PlantVariant.ROOT_STRAND)
                    || state == TFBlocks.root.getDefaultState();
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

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
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

    @Override
	public int damageDropped(IBlockState state)
    {
        return par1;
    }

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
		return true;
	}

	@Override
	public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        ret.add(new ItemStack(this, 1, world.getBlockMetadata(x, y, z)));
        //world.setBlockToAir(x, y, z);
        return ret;
	}

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack)
    {
    	// do not call normal harvest if the player is shearing
        if (world.isRemote || player.getCurrentEquippedItem() == null || player.getCurrentEquippedItem().getItem() != Items.SHEARS)
        {
            super.harvestBlock(world, player, x, y, z, meta);
        }
    }

	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> par3List)
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
	
    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
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
    public IBlockState getPlant(IBlockAccess world, BlockPos pos)
    {
        return this;
    }

    @Override
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World par1World, BlockPos pos, Random par5Random)
    {
        super.randomDisplayTick(par1World, x, y, z, par5Random);

    	int meta = par1World.getBlockMetadata(x, y, z);

        if (meta == META_MOSSPATCH && par5Random.nextInt(10) == 0)
        {
            par1World.spawnParticle("townaura", x + par5Random.nextFloat(), y + 0.1F, z + par5Random.nextFloat(), 0.0D, 0.0D, 0.0D);
        }
    
    }
}
