package twilightforest.item;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;
import twilightforest.block.TFBlocks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTFCrumbleHorn extends ItemTF
{
	private static final int CHANCE_HARVEST = 20;
	private static final int CHANCE_CRUMBLE = 5;

	protected ItemTFCrumbleHorn() {
		super();
		this.setCreativeTab(TFItems.creativeTab);
		this.maxStackSize = 1;
        this.setMaxDamage(1024);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World world, EntityPlayer player) {
		
		player.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));

		world.playSoundAtEntity(player, "mob.sheep.say", 1.0F, 0.8F);

		return par1ItemStack;
	}
	
	
    /**
     * Called each tick while using an item.
     * @param stack The Item being used
     * @param player The Player using the item
     * @param count The amount of time in tick the item has been used for continuously
     */
    @Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count) 
    {
		if (count > 10 && count % 5 == 0 && !player.worldObj.isRemote) 
		{
			int crumbled = doCrumble(player.worldObj, player);

			if (crumbled > 0)
			{
				stack.damageItem(crumbled, player);

			}
			
			player.worldObj.playSoundAtEntity(player, "mob.sheep.say", 1.0F, 0.8F);

		}
		
    }
	
    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    @Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.bow;
    }
    
    /**
     * How long it takes to use or consume an item
     */
    @Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 72000;
    }

	/**
	 * Bleeeat!
	 */
	private int doCrumble(World world, EntityPlayer player)
	{
		double range = 3.0D;
		double radius = 2.0D;
		Vec3d srcVec = new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);
		Vec3d lookVec = player.getLookVec();
		Vec3d destVec = srcVec.addVector(lookVec.xCoord * range, lookVec.yCoord * range, lookVec.zCoord * range);
		
		AxisAlignedBB crumbleBox =  new AxisAlignedBB(destVec.xCoord - radius, destVec.yCoord - radius, destVec.zCoord - radius, destVec.xCoord + radius, destVec.yCoord + radius, destVec.zCoord + radius);
		
		return crumbleBlocksInAABB(world, player, crumbleBox);
	}


	/**
     * Crumble block in the box
     */
    private int crumbleBlocksInAABB(World world, EntityPlayer player, AxisAlignedBB par1AxisAlignedBB)
    {
    	//System.out.println("Destroying blocks in " + par1AxisAlignedBB);
    	
        int minX = MathHelper.floor_double(par1AxisAlignedBB.minX);
        int minY = MathHelper.floor_double(par1AxisAlignedBB.minY);
        int minZ = MathHelper.floor_double(par1AxisAlignedBB.minZ);
        int maxX = MathHelper.floor_double(par1AxisAlignedBB.maxX);
        int maxY = MathHelper.floor_double(par1AxisAlignedBB.maxY);
        int maxZ = MathHelper.floor_double(par1AxisAlignedBB.maxZ);

        int crumbled = 0;

        for (int dx = minX; dx <= maxX; ++dx)
        {
            for (int dy = minY; dy <= maxY; ++dy)
            {
                for (int dz = minZ; dz <= maxZ; ++dz)
                {
                    crumbled += crumbleBlock(world, player, dx, dy, dz);
                }
            }
        }
        


        return crumbled;
    }

    /**
     * Crumble a specific block.
     */
	private int crumbleBlock(World world, EntityPlayer player, int dx, int dy, int dz) {
		int cost = 0;
		
		Block currentID = world.getBlock(dx, dy, dz);
		
		if (currentID != Blocks.AIR)
		{
			int currentMeta = world.getBlockMetadata(dx, dy, dz);
			
			if (currentID == Blocks.STONE && world.rand.nextInt(CHANCE_CRUMBLE) == 0)
		    {
		        world.setBlock(dx, dy, dz, Blocks.COBBLESTONE, 0, 3);
				world.playAuxSFX(2001, dx, dy, dz, Block.getIdFromBlock(currentID) + (currentMeta << 12));
		        cost++;
		    }
			
			if (currentID == Blocks.STONEBRICK && currentMeta == 0 && world.rand.nextInt(CHANCE_CRUMBLE) == 0)
		    {
		        world.setBlock(dx, dy, dz, Blocks.STONEBRICK, 2, 3);
				world.playAuxSFX(2001, dx, dy, dz, Block.getIdFromBlock(currentID) + (currentMeta << 12));
		        cost++;
		    }
			
			if (currentID == TFBlocks.mazestone && currentMeta == 1 && world.rand.nextInt(CHANCE_CRUMBLE) == 0)
		    {
		        world.setBlock(dx, dy, dz, TFBlocks.mazestone, 4, 3);
				world.playAuxSFX(2001, dx, dy, dz, Block.getIdFromBlock(currentID) + (currentMeta << 12));
		        cost++;
		    }
			
			if (currentID == Blocks.COBBLESTONE && world.rand.nextInt(CHANCE_CRUMBLE) == 0)
		    {
		        world.setBlock(dx, dy, dz, Blocks.GRAVEL, 0, 3);
				world.playAuxSFX(2001, dx, dy, dz, Block.getIdFromBlock(currentID) + (currentMeta << 12));
		        cost++;
		    }
			
			if (currentID == Blocks.GRAVEL || currentID == Blocks.DIRT)
			{
				if (currentID.canHarvestBlock(player, currentMeta) && world.rand.nextInt(CHANCE_HARVEST) == 0)
				{
					world.setBlock(dx, dy, dz, Blocks.AIR, 0, 3);
					currentID.harvestBlock(world, player, dx, dy, dz, currentMeta);
					world.playAuxSFX(2001, dx, dy, dz, Block.getIdFromBlock(currentID) + (currentMeta << 12));
					cost++;
				}
			}

		}
		
		return cost;
	}

	/**
	 * Properly register icon source
	 */
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon(TwilightForestMod.ID + ":" + this.getUnlocalizedName().substring(5));
    }
}
