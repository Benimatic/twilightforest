package twilightforest.item;

import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;

public class BehaviorTFMobEggDispense extends BehaviorDefaultDispenseItem
{
    private final MinecraftServer mcServer;

    public BehaviorTFMobEggDispense(MinecraftServer par1)
    {
        this.mcServer = par1;
    }

    @Override
    public ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
    {
        EnumFacing facing = EnumFacing.getFront(par1IBlockSource.getBlockMetadata());
        double x = par1IBlockSource.getX() + (double)facing.getFrontOffsetX();
        double y = (double)((float)par1IBlockSource.getY() + 0.2F);
        double z = par1IBlockSource.getZ() + (double)facing.getFrontOffsetZ();
        ItemTFSpawnEgg.spawnCreature(par1IBlockSource.getWorld(), par2ItemStack.getItemDamage(), x, y, z);
        par2ItemStack.splitStack(1);
        return par2ItemStack;
    }
}
