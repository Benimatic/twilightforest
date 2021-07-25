package twilightforest.tileentity;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.TickableBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.block.TFBlocks;

public class TrophyTileEntity extends BlockEntity implements TickableBlockEntity {

	private int animatedTicks;
	private boolean animated;

	public TrophyTileEntity() {
		super(TFTileEntities.TROPHY.get());
	}

	@Override
	public void tick() {
		BlockState blockstate = this.getBlockState();
		if (blockstate.is(TFBlocks.ur_ghast_trophy.get()) || blockstate.is(TFBlocks.ur_ghast_wall_trophy.get())) {
			this.animated = true;
			++this.animatedTicks;
		}
	}

	@OnlyIn(Dist.CLIENT)
	public float getAnimationProgress(float time) {
		return this.animated ? this.animatedTicks + time : (float) this.animatedTicks;
	}
}
