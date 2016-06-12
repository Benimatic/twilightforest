package twilightforest.block;

import java.util.Random;

import twilightforest.TwilightForestMod;
import twilightforest.item.TFItems;
import twilightforest.tileentity.TileEntityTFCinderFurnace;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockTFCinderFurnace  extends BlockContainer{

	private static boolean isUpdating;
	private Boolean isLit;
	private IIcon topIcon;
	private Random furnaceRandom = new Random();

	protected BlockTFCinderFurnace(Boolean isLit) {
		super(Material.wood);
		
		this.isLit = isLit;
		
		this.setHardness(7.0F);
		
		this.setLightLevel(isLit ? 1F : 0);
		
		if (!isLit) {
			this.setCreativeTab(TFItems.creativeTab);
		}
		
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileEntityTFCinderFurnace();
	}

	
    /**
     * Gets the block's texture. Args: side, meta
     */
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        return side == 1 ? this.topIcon : (side == 0 ? this.topIcon : this.blockIcon);
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        this.blockIcon = p_149651_1_.registerIcon(this.isLit ? "furnace_front_on" : "furnace_front_off");
        this.topIcon = p_149651_1_.registerIcon("furnace_top");
    }
    

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        if (world.isRemote)
        {
            return true;
        }
        else
        {
            TileEntityTFCinderFurnace tileentityfurnace = (TileEntityTFCinderFurnace)world.getTileEntity(x, y, z);

            if (tileentityfurnace != null)
            {
            	player.openGui(TwilightForestMod.instance, TwilightForestMod.GUI_ID_FURNACE, world, x, y, z);
            	return true;
            }

            return true;
        }
    }
    
    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase p_149689_5_, ItemStack itemStack)
    {

        if (itemStack.hasDisplayName())
        {
            ((TileEntityFurnace)world.getTileEntity(x, y, z)).func_145951_a(itemStack.getDisplayName());
        }
    }


    /**
     * Update which block the furnace is using depending on whether or not it is burning
     */
    public static void updateFurnaceBlockState(boolean isBurning, World world, int x, int y, int z)
    {
        TileEntity tileentity = world.getTileEntity(x, y, z);
        isUpdating = true;

        if (isBurning)
        {
            world.setBlock(x, y, z, TFBlocks.cinderFurnaceLit);
        }
        else
        {
            world.setBlock(x, y, z, TFBlocks.cinderFurnace);
        }

        isUpdating = false;

        if (tileentity != null)
        {
            tileentity.validate();
            world.setTileEntity(x, y, z, tileentity);
        }
    }
    
    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random random)
    {
   	
        if (this.isLit)
        {
            float f = (float)x + 0.5F;
            float f1 = (float)y + 0.0F + random.nextFloat() * 6.0F / 16.0F;
            float f2 = (float)z + 0.5F;
            float f3 = 0.52F;
            float f4 = random.nextFloat() * 0.6F - 0.3F;
            
            int l = random.nextInt(4) + 2;

            if (l == 4)
            {
                world.spawnParticle("smoke", (double)(f - f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", (double)(f - f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
            }
            else if (l == 5)
            {
                world.spawnParticle("smoke", (double)(f + f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", (double)(f + f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
            }
            else if (l == 2)
            {
                world.spawnParticle("smoke", (double)(f + f4), (double)f1, (double)(f2 - f3), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", (double)(f + f4), (double)f1, (double)(f2 - f3), 0.0D, 0.0D, 0.0D);
            }
            else if (l == 3)
            {
                world.spawnParticle("smoke", (double)(f + f4), (double)f1, (double)(f2 + f3), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", (double)(f + f4), (double)f1, (double)(f2 + f3), 0.0D, 0.0D, 0.0D);
            }
        }
    }
    
    
    /**
     * Dump things all over if broken
     */
    public void breakBlock(World world, int x, int y, int z, Block block, int p_149749_6_)
    {
        if (!isUpdating)
        {
        	TileEntityTFCinderFurnace tileEntity = (TileEntityTFCinderFurnace)world.getTileEntity(x, y, z);

            if (tileEntity != null)
            {
                for (int i = 0; i < tileEntity.getSizeInventory(); ++i)
                {
                    ItemStack itemstack = tileEntity.getStackInSlot(i);

                    if (itemstack != null)
                    {
                        float dx = this.furnaceRandom.nextFloat() * 0.8F + 0.1F;
                        float dy = this.furnaceRandom.nextFloat() * 0.8F + 0.1F;
                        float dz = this.furnaceRandom.nextFloat() * 0.8F + 0.1F;

                        while (itemstack.stackSize > 0)
                        {
                            int j1 = this.furnaceRandom.nextInt(21) + 10;

                            if (j1 > itemstack.stackSize)
                            {
                                j1 = itemstack.stackSize;
                            }

                            itemstack.stackSize -= j1;
                            EntityItem entityitem = new EntityItem(world, (double)((float)x + dx), (double)((float)y + dy), (double)((float)z + dz), new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

                            if (itemstack.hasTagCompound())
                            {
                                entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                            }

                            float pointFive = 0.05F;
                            entityitem.motionX = (double)((float)this.furnaceRandom.nextGaussian() * pointFive);
                            entityitem.motionY = (double)((float)this.furnaceRandom.nextGaussian() * pointFive + 0.2F);
                            entityitem.motionZ = (double)((float)this.furnaceRandom.nextGaussian() * pointFive);
                            world.spawnEntityInWorld(entityitem);
                        }
                    }
                }

                world.func_147453_f(x, y, z, block);
            }
        }

        super.breakBlock(world, x, y, z, block, p_149749_6_);
    }
    
    public Item getItemDropped(int meta, Random rand, int fortune)
    {
        return Item.getItemFromBlock(TFBlocks.cinderFurnace);
    }
    
    /**
     * Gets an item for the block being called on. Args: world, x, y, z
     */
    @SideOnly(Side.CLIENT)
    public Item getItem(World world, int x, int y, int z)
    {
        return Item.getItemFromBlock(TFBlocks.cinderFurnace);
    }
}
