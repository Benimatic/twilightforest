package twilightforest.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTFPeacockFan extends ItemTF
{
	protected ItemTFPeacockFan() {
		super();
		this.setCreativeTab(TFItems.creativeTab);
		this.maxStackSize = 1;
        this.setMaxDamage(1024);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World world, EntityPlayer player) {

		if (!world.isRemote) 
		{
			if (!player.onGround)
			{
				player.addPotionEffect(new PotionEffect(MobEffects.JUMP.id, 45, 0));
			}
			else
			{
				int fanned = 0;

				fanned = doFan(world, player);

				if (fanned > 0)
				{
					par1ItemStack.damageItem(fanned, player);

				}
			}
			
		}
		else
		{

			
			// jump if the player is in the air
			//TODO: only one extra jump per jump
			if (!player.onGround && !player.isPotionActive(MobEffects.JUMP.id))
			{
				player.motionX *= 3F;
				player.motionY = 1.5F;
				player.motionZ *= 3F;
				player.fallDistance = 0.0F;
			}
			else
			{
				AxisAlignedBB fanBox = getEffectAABB(world, player);
				Vec3d lookVec = player.getLookVec();

				// particle effect
				for (int i = 0; i < 30; i++)
				{
					world.spawnParticle("cloud", fanBox.minX + world.rand.nextFloat() * (fanBox.maxX - fanBox.minX), 
							fanBox.minY + world.rand.nextFloat() * (fanBox.maxY - fanBox.minY), 
							fanBox.minZ + world.rand.nextFloat() * (fanBox.maxZ - fanBox.minZ), 
							lookVec.xCoord, lookVec.yCoord, lookVec.zCoord);
				}
				
			}

			world.playSound(player.posX + 0.5D, player.posY + 0.5D, player.posZ + 0.5D, "random.breath", 1.0F + itemRand.nextFloat(), itemRand.nextFloat() * 0.7F + 0.3F, false);

		}
		
		player.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		
		return par1ItemStack;
	}
	
    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    @Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.block;
    }
    
    /**
     * How long it takes to use or consume an item
     */
    @Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 20;
    }
    
    /**
     * Returns True is the item is renderer in full 3D when hold.
     */
    @Override
	public boolean isFull3D()
    {
        return true;
    }

	/**
	 * Fannnn
	 */
	private int doFan(World world, EntityPlayer player)
	{
		AxisAlignedBB fanBox = getEffectAABB(world, player);
		
		fanBlocksInAABB(world, player, fanBox);
		
		fanEntitiesInAABB(world, player, fanBox);
		
		return 1;
	}

	/**
	 * Move entities in the box
	 */
	private void fanEntitiesInAABB(World world, EntityPlayer player, AxisAlignedBB fanBox) 
	{
		Vec3d moveVec = player.getLookVec();
		
		List<Entity> inBox = world.getEntitiesWithinAABB(Entity.class, fanBox);
		
		float force = 2.0F;
		
		for (Entity entity : inBox)
		{
			if (entity.canBePushed() || entity instanceof EntityItem)
			{
				entity.motionX = moveVec.xCoord * force;
				entity.motionY = moveVec.yCoord * force;
				entity.motionZ = moveVec.zCoord * force;
			}
		}
		
	}

	private AxisAlignedBB getEffectAABB(World world, EntityPlayer player) {
		double range = 3.0D;
		double radius = 2.0D;
		Vec3d srcVec = new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);
		Vec3d lookVec = player.getLookVec();
		Vec3d destVec = srcVec.addVector(lookVec.xCoord * range, lookVec.yCoord * range, lookVec.zCoord * range);
		
		AxisAlignedBB crumbleBox =  new AxisAlignedBB(destVec.xCoord - radius, destVec.yCoord - radius, destVec.zCoord - radius, destVec.xCoord + radius, destVec.yCoord + radius, destVec.zCoord + radius);
		return crumbleBox;
	}


	/**
     * Do fan effects on blocks in the bounding box
	 * @param player 
     */
    private int fanBlocksInAABB(World world, EntityPlayer player, AxisAlignedBB par1AxisAlignedBB)
    {
    	
        int minX = MathHelper.floor_double(par1AxisAlignedBB.minX);
        int minY = MathHelper.floor_double(par1AxisAlignedBB.minY);
        int minZ = MathHelper.floor_double(par1AxisAlignedBB.minZ);
        int maxX = MathHelper.floor_double(par1AxisAlignedBB.maxX);
        int maxY = MathHelper.floor_double(par1AxisAlignedBB.maxY);
        int maxZ = MathHelper.floor_double(par1AxisAlignedBB.maxZ);

        int fan = 0;

        for (int dx = minX; dx <= maxX; ++dx)
        {
            for (int dy = minY; dy <= maxY; ++dy)
            {
                for (int dz = minZ; dz <= maxZ; ++dz)
                {
                    fan += fanBlock(world, player, dx, dy, dz);
                }
            }
        }
        


        return fan;
    }

	private int fanBlock(World world, EntityPlayer player, int dx, int dy, int dz) {
		int cost = 0;
		
		Block currentID = world.getBlock(dx, dy, dz);
		
		if (currentID != Blocks.AIR)
		{
			int currentMeta = world.getBlockMetadata(dx, dy, dz);
			
			if (currentID instanceof BlockFlower)
			{
				if(currentID.canHarvestBlock(player, currentMeta) && itemRand.nextInt(3) == 0)
				{
					currentID.harvestBlock(world, player, dx, dy, dz, currentMeta);
					world.setBlock(dx, dy, dz, Blocks.AIR, 0, 3);
					world.playAuxSFX(2001, dx, dy, dz, Block.getIdFromBlock(currentID) + (currentMeta << 12));
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
