package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.item.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import twilightforest.client.ModelRegisterCallback;

import java.util.Random;

public class BlockTFDoor extends BlockDoor implements ModelRegisterCallback {

    private final MaterialColor mapColor;
    private final ResourceLocation itemLocation;

    private Item item;

    protected BlockTFDoor(Material material, MaterialColor mapColor, ResourceLocation itemLocation) {
        super(material);
        this.mapColor = mapColor;
        this.itemLocation = itemLocation;
    }

    @Override
    public Block setSoundType(SoundType sound) {
        return super.setSoundType(sound);
    }

    @Override
    public MaterialColor getMaterialColor(BlockState state, IBlockAccess world, BlockPos pos) {
        return mapColor;
    }

    @Override
    public Item getItemDropped(BlockState state, Random rand, int fortune) {
        return state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER ? Items.AIR : this.getItem();
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, BlockState state) {
        return new ItemStack(this.getItem());
    }

    private Item getItem() {
        if (item == null) {
            item = Item.REGISTRY.getObject(itemLocation);
        }
        return item;
    }
}
