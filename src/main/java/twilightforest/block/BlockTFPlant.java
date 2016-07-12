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
import net.minecraft.util.EnumParticleTypes;
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

	protected BlockTFPlant() {
		super(Material.PLANTS);
        this.setTickRandomly(true);
        float var3 = 0.4F;
        this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, 0.8F, 0.5F + var3);
        this.setHardness(0.0F);
		this.setCreativeTab(TFItems.creativeTab);
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
		world.scheduleUpdate(pos, this, world.rand.nextInt(50) + 20);
	}
	
    @Override
	public boolean canReplace(World par1World, BlockPos pos, EnumFacing side, ItemStack par6ItemStack)
    {
    	IBlockState state = par1World.getBlockState(pos);
        return (state.getBlock().isAir(state, par1World, pos) || state.getMaterial().isReplaceable()) && canBlockStay(par1World, pos, state);
    }

    @Override
	public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
    	
    	//System.out.println("Can block stay? meta is " + meta);
        IBlockState soil = world.getBlockState(pos.down());
    	
       	switch (state.getValue(VARIANT)) {
    	case TORCHBERRY :
    	case ROOT_STRAND :
    		return BlockTFPlant.canPlaceRootBelow(world, pos.up());
    	case FORESTGRASS:
    	case DEADBUSH:
    		return soil.getBlock().canSustainPlant(soil, world, pos.down(), EnumFacing.UP, this);
    	case MUSHGLOOM:
    	case MOSSPATCH:
    		return soil.isSideSolid(world, pos, EnumFacing.UP);
     	default :
            return (world.getLight(pos) >= 3 || world.canSeeSky(pos)) &&
                    soil.getBlock().canSustainPlant(soil, world, pos.down(), EnumFacing.UP, this);
    	}
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        PlantVariant variant = state.getValue(VARIANT);

        if (variant == PlantVariant.MOSSPATCH)
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
        else if (variant == PlantVariant.CLOVERPATCH)
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
        else if (variant == PlantVariant.MAYAPPLE)
        {
            return new AxisAlignedBB(4F / 16F, 0, 4F / 16F, 13F / 16F, 6F / 16F, 13F / 16F);
        }
        else
        {
            return FULL_BLOCK_AABB;
        }
    }
    
    @Override
	public int getRenderColor(int par1)
    {
        return isGrassColor[par1] ? ColorizerFoliage.getFoliageColorBasic() : 0xFFFFFF;
    }

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
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        if (world.getBlockState(pos).getBlock() != this) {
            return world.getBlockState(pos).getLightValue(world, pos);
        } else {
            switch (state.getValue(VARIANT)) {
                case MUSHGLOOM: return 3;
                case TORCHBERRY: return 8;
                default: return 0;
            }
        }
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
        switch (state.getValue(VARIANT)) {
        case TORCHBERRY:
        	ret.add(new ItemStack(TFItems.torchberries));
        	break;
        case MOSSPATCH :
        case MAYAPPLE :
        case CLOVERPATCH :
        case ROOT_STRAND :
        case FORESTGRASS :
        case DEADBUSH :
        	break;
        default :
        	ret.add(new ItemStack(this, 1, damageDropped(state)));
        	break;
        }

        return ret;
    }

    @Override
	public int damageDropped(IBlockState state)
    {
        return getMetaFromState(state);
    }

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
		return true;
	}

	@Override
	public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        ret.add(new ItemStack(this, 1, damageDropped(world.getBlockState(pos))));
        return ret;
	}

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack)
    {
    	// do not call normal harvest if the player is shearing
        if (world.isRemote || stack == null || stack.getItem() != Items.SHEARS)
        {
            super.harvestBlock(world, player, pos, state, te, stack);
        }
    }

	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> par3List)
    {
        par3List.add(new ItemStack(this, 1, PlantVariant.MOSSPATCH.meta));
        par3List.add(new ItemStack(this, 1, PlantVariant.MAYAPPLE.meta));
        //par3List.add(new ItemStack(this, 1, META_CLOVERPATCH));
        par3List.add(new ItemStack(this, 1, 8));
        par3List.add(new ItemStack(this, 1, 9));
        par3List.add(new ItemStack(this, 1, PlantVariant.FORESTGRASS.meta));
        par3List.add(new ItemStack(this, 1, PlantVariant.DEADBUSH.meta));
        par3List.add(new ItemStack(this, 1, 13));
        par3List.add(new ItemStack(this, 1, 14));

    }
	
    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
    {
    	switch (world.getBlockState(pos).getValue(VARIANT))
    	{
    	case MOSSPATCH :
    	case MUSHGLOOM :
    		return EnumPlantType.Cave;
    	default :
    		return EnumPlantType.Plains;
    	}
    }

   /* @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos)
    {
        return this; todo 1.9
    }*/

    @Override
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World par1World, BlockPos pos, Random par5Random)
    {
        super.randomDisplayTick(state, par1World, pos, par5Random);

        if (state.getValue(VARIANT) == PlantVariant.MOSSPATCH && par5Random.nextInt(10) == 0)
        {
            par1World.spawnParticle(EnumParticleTypes.TOWN_AURA, pos.getX() + par5Random.nextFloat(), pos.getY() + 0.1F, pos.getZ() + par5Random.nextFloat(), 0.0D, 0.0D, 0.0D);
        }
    
    }
}
