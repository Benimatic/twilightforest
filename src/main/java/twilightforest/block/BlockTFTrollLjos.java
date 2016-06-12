package twilightforest.block;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import twilightforest.item.TFItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;

public class BlockTFTrollLjos extends Block {
	
	protected BlockTFTrollLjos() {
		super(Material.rock);

        this.setHardness(2F);
        this.setResistance(15F);
        this.setStepSound(Block.soundTypeStone);
		this.setCreativeTab(TFItems.creativeTab);
		
        this.setLightLevel(1.0F);

	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		this.blockIcon = Blocks.LIT_REDSTONE_LAMP.getIcon(0, 0);
	}
}
