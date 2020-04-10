package twilightforest.structures.finalcastle;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.structures.StructureTFComponentOld;

import java.util.Random;

public class ComponentTFFinalCastleDungeonForgeRoom extends StructureTFComponentOld {

	public ComponentTFFinalCastleDungeonForgeRoom(TemplateManager manager, CompoundNBT nbt) {
		super(TFFinalCastlePieces.TFFCDunBoR, nbt);
	}

	public ComponentTFFinalCastleDungeonForgeRoom(TFFeature feature, Random rand, int i, int x, int y, int z, Direction direction) {
		super(TFFinalCastlePieces.TFFCDunBoR, feature, i);
		this.spawnListIndex = 3; // forge monsters
		this.setCoordBaseMode(direction);
		this.boundingBox = StructureTFComponentOld.getComponentToAddBoundingBox2(x, y, z, -15, 0, -15, 50, 30, 50, direction);
	}

	@Override
	public boolean generate(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn) {
		this.fillWithAir(world, sbb, 0, 0, 0, 50, 30, 50);

		// sign
		this.placeSignAtCurrentPosition(world.getWorld(), 25, 0, 25, "Mini-boss 2", "Gives talisman", sbb);

		return true;
	}
}
