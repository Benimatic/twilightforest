package twilightforest.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTFFieryPick extends ItemPickaxe {

	protected ItemTFFieryPick(Item.ToolMaterial par2EnumToolMaterial) {
		super(par2EnumToolMaterial);
		this.setCreativeTab(TFItems.creativeTab);
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase living) {
		if (super.onBlockDestroyed(stack, world, state, pos, living) && this.canHarvestBlock(state))
		{
			// we are just displaying the fire animation here, so check if we're on the client
			if (world.isRemote)
			{
				List<ItemStack> items = state.getBlock().getDrops(world, pos, state, 0);

				for (ItemStack input : items)
				{
					// does it smelt?
					ItemStack result = FurnaceRecipes.instance().getSmeltingResult(input);
					if (result != null)
					{

						// display fire animation
						for (int var1 = 0; var1 < 5; ++var1)
						{
							double rx = itemRand.nextGaussian() * 0.02D;
							double ry = itemRand.nextGaussian() * 0.02D;
							double rz = itemRand.nextGaussian() * 0.02D;
							double magnitude = 20.0;
							world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5 + (rx * magnitude), pos.getY() + 0.5 + (ry * magnitude), pos.getZ() + 0.5 + (rz * magnitude), -rx, -ry, -rz);
						}

					}

				}
			}


			return true;
		}
		else
		{
			return false;
		}
	}
	
    @Override
	public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLiving, EntityLivingBase par3EntityLiving) {
		boolean result = super.hitEntity(par1ItemStack, par2EntityLiving, par3EntityLiving);
		
		if (result && !par2EntityLiving.isImmuneToFire())
		{
			if (par2EntityLiving.world.isRemote)
			{
				// fire animation!
		        for (int var1 = 0; var1 < 20; ++var1)
		        {
		            double var2 = itemRand.nextGaussian() * 0.02D;
		            double var4 = itemRand.nextGaussian() * 0.02D;
		            double var6 = itemRand.nextGaussian() * 0.02D;
		            double var8 = 10.0D;
		            par2EntityLiving.world.spawnParticle(EnumParticleTypes.FLAME, par2EntityLiving.posX + itemRand.nextFloat() * par2EntityLiving.width * 2.0F - par2EntityLiving.width - var2 * var8, par2EntityLiving.posY + itemRand.nextFloat() * par2EntityLiving.height - var4 * var8, par2EntityLiving.posZ + itemRand.nextFloat() * par2EntityLiving.width * 2.0F - par2EntityLiving.width - var6 * var8, var2, var4, var6);
		        }
			}
			else
			{
				par2EntityLiving.setFire(15);
			}
		}
		
		return result;
	}

    @Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
    	return EnumRarity.RARE;
	}
    
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List<String> par3List, boolean par4) {
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		par3List.add(I18n.translateToLocal(getUnlocalizedName() + ".tooltip"));
	}
    
    @Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
    	// repair with fiery ingots
        return par2ItemStack.getItem() == TFItems.fieryIngot ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
    }
    
	@Override
    public boolean canHarvestBlock(IBlockState state)
    {
        return state.getBlock() == Blocks.OBSIDIAN ? true : super.canHarvestBlock(state);
    }
}
