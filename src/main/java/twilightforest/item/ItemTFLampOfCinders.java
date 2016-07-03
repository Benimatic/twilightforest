package twilightforest.item;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import twilightforest.TFAchievementPage;
import twilightforest.TwilightForestMod;
import twilightforest.block.TFBlocks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTFLampOfCinders extends ItemTF {

	private static final int FIRING_TIME = 12;

	public ItemTFLampOfCinders() {
		this.setCreativeTab(TFItems.creativeTab);
		this.maxStackSize = 1;
        this.setMaxDamage(1024);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack par1ItemStack, World world, EntityPlayer player, EnumHand hand) {
		if (par1ItemStack.getItemDamage() < this.getMaxDamage()) 
		{
			player.setActiveHand(hand);
		}
		else 
		{
			player.resetActiveHand();
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, par1ItemStack);
	}
	
	@Override
	public EnumActionResult onItemUse(ItemStack par1ItemStack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
	{
        if (burnBlock(player, world, x, y, z)) {
    		world.playSoundAtEntity(player, this.getSound(), 0.5F, 1.5F);

    		// spawn flame particles
    		for (int i = 0; i < 10; i++) {
    			float dx =  x + 0.5F + (itemRand.nextFloat() - itemRand.nextFloat()) * 0.75F;
    			float dy =  y + 0.5F + (itemRand.nextFloat() - itemRand.nextFloat()) * 0.75F;
    			float dz =  z + 0.5F + (itemRand.nextFloat() - itemRand.nextFloat()) * 0.75F;
    			
                world.spawnParticle("smoke", dx, dy, dz, 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", dx, dy, dz, 0.0D, 0.0D, 0.0D);
    		}
    		
        	return true;
        } else {
        	return false;
        }
        
	}

	private boolean burnBlock(EntityPlayer player, World world, int x, int y, int z) {
		Block block = world.getBlock(x, y, z);
        if (block == TFBlocks.thorns) {
        	world.setBlock(x, y, z, TFBlocks.burntThorns, world.getBlockMetadata(x, y, z) & 12, 2);
        	return true;
        } else {
        	return false;
        }
	}
	
    @Override
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World world, EntityLivingBase living, int useRemaining)
    {
    	int useTime = this.getMaxItemUseDuration(par1ItemStack) - useRemaining;


    	if (useTime > FIRING_TIME && (par1ItemStack.getItemDamage() + 1) < this.getMaxDamage()) 
    	{
    		doBurnEffect(world, living);
    		
    		// trigger achievement
    		living.addStat(TFAchievementPage.twilightProgressTroll);
    	}

    }

	private void doBurnEffect(World world, EntityPlayer player) {
		int px = MathHelper.floor_double(player.lastTickPosX);
		int py = MathHelper.floor_double(player.lastTickPosY + player.getEyeHeight());
		int pz = MathHelper.floor_double(player.lastTickPosZ);

		int range = 4;
		
		if (!world.isRemote) {
			world.playSoundAtEntity(player, this.getSound(), 1.5F, 0.8F);

			// set nearby thorns to burnt
			for (int dx = -range; dx <=range; dx++) {
				for (int dy = -range; dy <=range; dy++) {
					for (int dz = -range; dz <=range; dz++) {
						this.burnBlock(player, world, px + dx, py + dy, pz + dz);
					}
				}
			}
		}

		for (int i = 0; i < 6; i++) {
			int rx = px + itemRand.nextInt(range) - itemRand.nextInt(range);
			int ry = py + itemRand.nextInt(2);
			int rz = pz + itemRand.nextInt(range) - itemRand.nextInt(range);

			world.playAuxSFXAtEntity(player, 2004, rx, ry, rz, 0);
		}
	}

	
	public String getSound()
	{
		return "mob.ghast.fireball";
	}
	
    @Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.BOW;
    }
    
    @Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 72000;
    }
}
