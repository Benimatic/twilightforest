//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package twilightforest.client.texture.render;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
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
	public boolean isConnected(IBlockAccess world, BlockPos current, BlockPos connection, EnumFacing dir, IBlockState state) {
		BlockPos difference = current.subtract(connection);

		return super.isConnected(world, current, current.add(new BlockPos(-difference.getX(), difference.getZ(), difference.getY())), dir, state);
	}
}
