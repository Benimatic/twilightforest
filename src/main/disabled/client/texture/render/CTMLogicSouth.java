//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package twilightforest.client.texture.render;

import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import team.chisel.ctm.client.util.CTMLogic;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class CTMLogicSouth extends CTMLogic {
	public static CTMLogicSouth getInstance() {
		return new CTMLogicSouth();
	}

	@Override
	public boolean isConnected(IBlockAccess world, BlockPos current, BlockPos connection, Direction dir, BlockState state) {
		BlockPos difference = current.subtract(connection);

		return super.isConnected(world, current, current.add(new BlockPos(-difference.getX(), difference.getZ(), difference.getY())), dir, state);
	}
}
