package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import twilightforest.entity.EntityTFTowerTermite;
import twilightforest.entity.TFEntities;

/**
 * Tower wood is a type of plank block that forms the walls of Dark Towers
 *
 * @author Ben
 */
public class BlockTFTowerWood extends Block {

	public BlockTFTowerWood(MaterialColor color, float hardness) {
		super(Properties.create(Material.WOOD, color).hardnessAndResistance(hardness, 10.0F).sound(SoundType.WOOD));
		//this.setCreativeTab(TFItems.creativeTab); TODO 1.14
	}

//	@Override
//	public int quantityDropped(BlockState state, int fortune, Random random) {
//		if (state.getValue(VARIANT) == TowerWoodVariant.INFESTED) {
//			return 0;
//		} else {
//			return super.quantityDropped(state, fortune, random);
//		}
//	}

	@Override
	@Deprecated
	public void spawnAdditionalDrops(BlockState state, World world, BlockPos pos, ItemStack stack) {
		if (!world.isRemote && state.getBlock() == TFBlocks.tower_wood_infested.get()) {
			EntityTFTowerTermite termite = new EntityTFTowerTermite(TFEntities.tower_termite.get(), world);
			termite.setLocationAndAngles(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, 0.0F, 0.0F);
			world.addEntity(termite);
			termite.spawnExplosionParticle();
		}

		super.spawnAdditionalDrops(state, world, pos, stack);
	}

	@Override
	public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return 1;
	}

	@Override
	public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return 0;
	}
}
