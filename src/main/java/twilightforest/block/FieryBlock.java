package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import twilightforest.item.TFItems;
import twilightforest.util.TFDamageSources;

public class FieryBlock extends Block {
	public FieryBlock(Properties properties) {
		super(properties);
	}

	@Override
	public float getShadeBrightness(BlockState state, BlockGetter getter, BlockPos pos) {
		return 1.0F;
	}

	@Override
	public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
		if ((!entity.fireImmune())
				&& entity instanceof LivingEntity living
				&& (!EnchantmentHelper.hasFrostWalker(living))
				&& !living.getItemBySlot(EquipmentSlot.FEET).is(TFItems.FIERY_BOOTS.get())) {
			entity.hurt(TFDamageSources.FIERY, 1.0F);
		}

		super.stepOn(level, pos, state, entity);
	}

	@Override
	public boolean isFireSource(BlockState state, LevelReader level, BlockPos pos, Direction direction) {
		return true;
	}
}
