package twilightforest.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTFHugeGloomBlock extends Block {

    @SideOnly(Side.CLIENT)
    private IIcon capTex;
    @SideOnly(Side.CLIENT)
    private IIcon stemTex;
    @SideOnly(Side.CLIENT)
    private IIcon insideTex;
    
    public BlockTFHugeGloomBlock()
    {
        super(Material.WOOD);
        this.setHardness(0.2F);
        this.setStepSound(soundTypeWood);
        this.setBlockTextureName(TwilightForestMod.ID + ":huge_gloom");
        this.setLightLevel(5F / 16F);
    }

    /**
     * Gets the block's texture. Args: side, meta
     */
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return meta == 10 && side > 1 ? this.stemTex : (meta >= 1 && meta <= 9 && side == 1 ? this.capTex : (meta >= 1 && meta <= 3 && side == 2 ? this.capTex : (meta >= 7 && meta <= 9 && side == 3 ? this.capTex : ((meta == 1 || meta == 4 || meta == 7) && side == 4 ? this.capTex : ((meta == 3 || meta == 6 || meta == 9) && side == 5 ? this.capTex : (meta == 14 ? this.capTex : (meta == 15 ? this.stemTex : this.insideTex)))))));
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random rand)
    {
        int i = rand.nextInt(10) - 7;

        if (i < 0)
        {
            i = 0;
        }

        return i;
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return Item.getItemById(Block.getIdFromBlock(TFBlocks.plant));
    }

    /**
     * Gets an item for the block being called on. Args: world, x, y, z
     */
    @SideOnly(Side.CLIENT)
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
        return Item.getItemById(Block.getIdFromBlock(TFBlocks.plant));
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.capTex = iconRegister.registerIcon(this.getTextureName() + "_cap");
        this.insideTex = iconRegister.registerIcon(this.getTextureName() + "_inside");
        this.stemTex = iconRegister.registerIcon(this.getTextureName() + "_stem");
    }
}