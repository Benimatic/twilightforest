package twilightforest.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class ItemBlockTFLog extends ItemBlock {

    public static final String woodNames[] = {
        "oak", "canopy", "mangrove", "darkwood", "x", "root", "oreroot", "rotten"
    };

    public ItemBlockTFLog(Block log)
    {
        super(log);
        setHasSubtypes(true);
        setMaxDamage(0);
    }
    
    @Override
    public String getUnlocalizedName(ItemStack itemstack)
    {
    	int meta = itemstack.getItemDamage();
    	if ((meta & 8) == 0) {
    		// wood
        	int i = MathHelper.clamp(meta, 0, 7);
            return (new StringBuilder()).append(super.getUnlocalizedName()).append(".").append(woodNames[i]).toString();
    	}
    	else {
    		// log
    		meta &= 7;
        	int i = MathHelper.clamp(meta, 0, 7);
            return (new StringBuilder()).append(super.getUnlocalizedName()).append(".").append(woodNames[i]).append(".log").toString();
    	}
    }

    @Override
    public int getMetadata(int i)
    {
        return i;
    }

    
//    /**
//     * Display meta in tooltip
//     */
//	@Override
//	public void addInformation(ItemStack par1ItemStack, List par2List) {
//		par2List.add("Meta = " + par1ItemStack.getItemDamage());
//	}


}
