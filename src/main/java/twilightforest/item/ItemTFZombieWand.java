package twilightforest.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;
import twilightforest.entity.EntityTFLoyalZombie;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;



public class ItemTFZombieWand extends ItemTF {

	protected ItemTFZombieWand() {
		super();
        this.maxStackSize = 1;
        this.setMaxDamage(9);
		this.setCreativeTab(TFItems.creativeTab);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World worldObj, EntityPlayer player) {
		
		if (par1ItemStack.getItemDamage() < this.getMaxDamage()) {
			player.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
			
			if (!worldObj.isRemote) {
				// what block is the player pointing at?
				MovingObjectPosition mop = getPlayerPointVec(worldObj, player, 20.0F);
				
				if (mop != null) {
					// make a zombie there
					EntityTFLoyalZombie zombie = new EntityTFLoyalZombie(worldObj);
					zombie.setPositionAndRotation(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord, 1.0F, 1.0F);  /// NPE here needs to be fixed
					zombie.setTamed(true);
					try {
						zombie.func_152115_b(player.getUniqueID().toString());
						//zombie.setOwner(player.getCommandSenderName());
					} catch (NoSuchMethodError ex) {
						// ignore?
						FMLLog.warning("[TwilightForest] Could not determine player name for loyal zombie, ignoring error.");
					}
					zombie.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 1200, 1));
					worldObj.spawnEntityInWorld(zombie);

					par1ItemStack.damageItem(1, player);
				}
			}
		}
		else {
			player.stopUsingItem();
		}
		
		return par1ItemStack;
	}
	
	/**
	 * What block is the player pointing the wand at?
	 * 
	 * This very similar to player.rayTrace, but that method is not available on the server.
	 * 
	 * @return
	 */
	private MovingObjectPosition getPlayerPointVec(World worldObj, EntityPlayer player, float range) {
        Vec3 position = Vec3.createVectorHelper(player.posX, player.posY + player.getEyeHeight(), player.posZ);
        Vec3 look = player.getLook(1.0F);
        Vec3 dest = position.addVector(look.xCoord * range, look.yCoord * range, look.zCoord * range);
        return worldObj.rayTraceBlocks(position, dest);
	}


    /**
     * How long it takes to use or consume an item
     */
    @Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 30;
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
     * Return an item rarity from EnumRarity
     */    
    @Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
    	return EnumRarity.rare;
	}
    
    /**
     * Display charges left in tooltip
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		par3List.add((par1ItemStack.getMaxDamage() -  par1ItemStack.getItemDamage()) + " charges left");
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
