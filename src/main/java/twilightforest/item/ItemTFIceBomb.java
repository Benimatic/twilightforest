package twilightforest.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;
import twilightforest.entity.boss.EntityTFIceBomb;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTFIceBomb extends ItemTF {


	private IIcon[] snowIcon = new IIcon[4];

	
	public ItemTFIceBomb() {
		this.setMaxStackSize(16);
	}
	
	/**
	 * Properly register icon source
	 */
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon(TwilightForestMod.ID + ":" + this.getUnlocalizedName().substring(5));
        for (int i = 0; i < 4; i++) {
        	this.snowIcon[i] = par1IconRegister.registerIcon(TwilightForestMod.ID + ":snow_" + i);
        }
    }
    
    public IIcon getSnowIcon(int i) {
    	return snowIcon[i];
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, EnumHand hand) {
        if (!par3EntityPlayer.capabilities.isCreativeMode) {
        	--par1ItemStack.stackSize;
        }

        par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!par2World.isRemote) {
        	par2World.spawnEntityInWorld(new EntityTFIceBomb(par2World, par3EntityPlayer));
        }

        return ActionResult.newResult(EnumActionResult.SUCCESS, par1ItemStack);
    }
}
