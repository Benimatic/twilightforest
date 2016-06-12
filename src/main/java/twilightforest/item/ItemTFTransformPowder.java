package twilightforest.item;

import java.util.HashMap;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;
import twilightforest.entity.EntityTFHedgeSpider;
import twilightforest.entity.EntityTFHostileWolf;
import twilightforest.entity.EntityTFMinotaur;
import twilightforest.entity.EntityTFRedcap;
import twilightforest.entity.EntityTFSkeletonDruid;
import twilightforest.entity.EntityTFSwarmSpider;
import twilightforest.entity.EntityTFWraith;
import twilightforest.entity.passive.EntityTFBighorn;
import twilightforest.entity.passive.EntityTFBoar;
import twilightforest.entity.passive.EntityTFDeer;
import twilightforest.entity.passive.EntityTFPenguin;
import twilightforest.entity.passive.EntityTFRaven;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTFTransformPowder extends ItemTF 
{
	
	HashMap<Class<? extends EntityLivingBase>, Class<? extends EntityLivingBase>> transformMap;

	protected ItemTFTransformPowder() 
	{
		super();
        this.maxStackSize = 64;
		this.setCreativeTab(TFItems.creativeTab);
        
        transformMap = new HashMap<Class<? extends EntityLivingBase>, Class<? extends EntityLivingBase>>();
        
        
        addTwoWayTransformation(EntityTFMinotaur.class, EntityPigZombie.class);
        addTwoWayTransformation(EntityTFDeer.class, EntityCow.class);
        addTwoWayTransformation(EntityTFBighorn.class, EntitySheep.class);
        addTwoWayTransformation(EntityTFBoar.class, EntityPig.class);
        addTwoWayTransformation(EntityTFRaven.class, EntityBat.class);
        addTwoWayTransformation(EntityTFHostileWolf.class, EntityWolf.class);
        addTwoWayTransformation(EntityTFPenguin.class, EntityChicken.class);
        addTwoWayTransformation(EntityTFHedgeSpider.class, EntitySpider.class);
        addTwoWayTransformation(EntityTFSwarmSpider.class, EntityCaveSpider.class);
        addTwoWayTransformation(EntityTFWraith.class, EntityBlaze.class);
        addTwoWayTransformation(EntityTFRedcap.class, EntityVillager.class);
        addTwoWayTransformation(EntityTFSkeletonDruid.class, EntityWitch.class);
	}

    public void addTwoWayTransformation(Class<? extends EntityLivingBase> class1, Class<? extends EntityLivingBase> class2) {

    	transformMap.put(class1, class2);
    	transformMap.put(class2, class1);
	}

	/**
     * dye sheep, place saddles, etc ...
     */
	@Override
	public boolean itemInteractionForEntity(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, EntityLivingBase target) 
	{
		// this is where we transform normal entities into twilight entities and vice versa
		
		Class<?> transformClass = getMonsterTransform(target.getClass());
		
		if (transformClass != null)
		{
			if (target.worldObj.isRemote)
			{
				// particles, sound
				if (target instanceof EntityLiving)
				{
					((EntityLiving) target).spawnExplosionParticle();
					((EntityLiving) target).spawnExplosionParticle();
				}
				
				target.worldObj.playSound(target.posX + 0.5D, target.posY + 0.5D, target.posZ + 0.5D, "mob.zombie.remedy", 1.0F + itemRand.nextFloat(), itemRand.nextFloat() * 0.7F + 0.3F, false);
			}
			else
			{
				// add new monster with old monster's attributes
				EntityLivingBase newMonster = null;
				try 
				{
					newMonster = (EntityLivingBase)transformClass.getConstructor(new Class[] {World.class}).newInstance(new Object[] {target.worldObj});
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				
				if (newMonster == null)
				{
					return false;
				}
				else
				{
					newMonster.setPositionAndRotation(target.posX, target.posY, target.posZ, target.rotationYaw, target.rotationPitch);
					//newMonster.initCreature();
					
					target.worldObj.spawnEntityInWorld(newMonster);
					
					// remove original monster
					target.setDead();
				}
			}
			
            --par1ItemStack.stackSize;
			
			return true;
		}
		else
		{
			return false;
		}
	}

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World world, EntityPlayer player)
	{
		if (world.isRemote)
		{
			AxisAlignedBB fanBox = getEffectAABB(world, player);
			
			// particle effect
			for (int i = 0; i < 30; i++)
			{
				world.spawnParticle("magicCrit", fanBox.minX + world.rand.nextFloat() * (fanBox.maxX - fanBox.minX), 
						fanBox.minY + world.rand.nextFloat() * (fanBox.maxY - fanBox.minY), 
						fanBox.minZ + world.rand.nextFloat() * (fanBox.maxZ - fanBox.minZ), 
						0, 0, 0);
			}

		}

		
		return par1ItemStack;
	}
	
	private AxisAlignedBB getEffectAABB(World world, EntityPlayer player) {
		double range = 2.0D;
		double radius = 1.0D;
		Vec3 srcVec = Vec3.createVectorHelper(player.posX, player.posY + player.getEyeHeight(), player.posZ);
		Vec3 lookVec = player.getLookVec();
		Vec3 destVec = srcVec.addVector(lookVec.xCoord * range, lookVec.yCoord * range, lookVec.zCoord * range);
		
		return AxisAlignedBB.getBoundingBox(destVec.xCoord - radius, destVec.yCoord - radius, destVec.zCoord - radius, destVec.xCoord + radius, destVec.yCoord + radius, destVec.zCoord + radius);
	}
	
	/**
	 * Which class should the specified monster class transform into?
	 */
	public Class<? extends EntityLivingBase> getMonsterTransform(Class<? extends EntityLivingBase> originalMonster)
	{
		return transformMap.get(originalMonster);
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