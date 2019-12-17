package twilightforest.block;

import net.minecraft.block.DoorBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.Material;

public class BlockTFDoor extends DoorBlock {

    protected BlockTFDoor(MaterialColor mapColor) {
        super(Properties.create(Material.WOOD, mapColor).hardnessAndResistance(3.0F).sound(SoundType.WOOD));
    }

//    @Override
//    public Item getItemDropped(BlockState state, Random rand, int fortune) {
//        return state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER ? Items.AIR : this.getItem();
//    }
//
//    @Override
//    public ItemStack getItem(World worldIn, BlockPos pos, BlockState state) {
//        return new ItemStack(this.getItem());
//    }
//
//    private Item getItem() {
//        if (item == null) {
//            item = Item.REGISTRY.getObject(itemLocation);
//        }
//        return item;
//    }
}
