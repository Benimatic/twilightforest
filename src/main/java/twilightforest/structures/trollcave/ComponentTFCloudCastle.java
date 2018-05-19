package twilightforest.structures.trollcave;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.block.TFBlocks;
import twilightforest.entity.EntityTFArmoredGiant;
import twilightforest.entity.EntityTFGiantMiner;
import twilightforest.structures.StructureTFComponentOld;

import java.util.List;
import java.util.Random;

public class ComponentTFCloudCastle extends StructureTFComponentOld {

	private boolean minerPlaced = false;
	private boolean warriorPlaced = false;

	public ComponentTFCloudCastle() {
	}

	public ComponentTFCloudCastle(TFFeature feature, int index, int x, int y, int z) {
		super(feature, index);
		this.setCoordBaseMode(EnumFacing.SOUTH);

		// round to nearest mult of 4
		x &= ~0b11;
		y &= ~0b11;
		z &= ~0b11;

		// spawn list!
		this.spawnListIndex = 1;

		this.boundingBox = StructureTFComponentOld.getComponentToAddBoundingBox(x, y, z, -8, 0, -8, 16, 16, 16, EnumFacing.SOUTH);
	}

	@Override
	protected void writeStructureToNBT(NBTTagCompound tagCompound) {
		super.writeStructureToNBT(tagCompound);

		tagCompound.setBoolean("minerPlaced", this.minerPlaced);
		tagCompound.setBoolean("warriorPlaced", this.warriorPlaced);
	}

	@Override
	protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager templateManager) {
		super.readStructureFromNBT(tagCompound, templateManager);
		this.minerPlaced = tagCompound.getBoolean("minerPlaced");
		this.warriorPlaced = tagCompound.getBoolean("warriorPlaced");
	}

	@Override
	public void buildComponent(StructureComponent parent, List<StructureComponent> list, Random rand) {
		// up to two trees
		// tree in x direction
		boolean plus = rand.nextBoolean();
		int offset = rand.nextInt(5) - rand.nextInt(5);
		ComponentTFCloudTree treeX = new ComponentTFCloudTree(this.getComponentType() + 1, boundingBox.minX + (plus ? 16 : -16), 168, boundingBox.minZ - 8 + (offset * 4));
		list.add(treeX);
		treeX.buildComponent(this, list, rand);

		// tree in z direction
		plus = rand.nextBoolean();
		offset = rand.nextInt(5) - rand.nextInt(5);
		ComponentTFCloudTree treeZ = new ComponentTFCloudTree(this.getComponentType() + 1, boundingBox.minX - 8 + (offset * 4), 168, boundingBox.minZ + (plus ? 16 : -16));
		list.add(treeZ);
		treeZ.buildComponent(this, list, rand);

	}

	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		// make haus
		this.fillWithBlocks(world, sbb, 0, -4, 0, 15, -1, 15, TFBlocks.fluffy_cloud.getDefaultState(), TFBlocks.fluffy_cloud.getDefaultState(), false);
		this.fillWithBlocks(world, sbb, 0, 0, 0, 15, 11, 15, TFBlocks.giant_cobblestone.getDefaultState(), TFBlocks.giant_cobblestone.getDefaultState(), false);
		this.fillWithBlocks(world, sbb, 0, 12, 0, 15, 15, 15, TFBlocks.giant_log.getDefaultState(), TFBlocks.giant_log.getDefaultState(), false);


		// clear inside
		this.fillWithAir(world, sbb, 4, 0, 4, 11, 11, 11);

		// clear door
		this.fillWithAir(world, sbb, 0, 0, 4, 4, 7, 7);


		// add giants
		if (!this.minerPlaced) {
			int bx = this.getXWithOffset(6, 6);
			int by = this.getYWithOffset(0);
			int bz = this.getZWithOffset(6, 6);
			BlockPos pos = new BlockPos(bx, by, bz);

			if (sbb.isVecInside(pos)) {
				this.minerPlaced = true;

				EntityTFGiantMiner miner = new EntityTFGiantMiner(world);
				miner.setPosition(bx, by, bz);
				miner.enablePersistence();
				miner.onInitialSpawn(world.getDifficultyForLocation(pos), null);

				world.spawnEntity(miner);
			}
		}
		if (!this.warriorPlaced) {
			int bx = this.getXWithOffset(9, 9);
			int by = this.getYWithOffset(0);
			int bz = this.getZWithOffset(9, 9);
			BlockPos pos = new BlockPos(bx, by, bz);

			if (sbb.isVecInside(pos)) {
				this.warriorPlaced = true;

				EntityTFArmoredGiant warrior = new EntityTFArmoredGiant(world);
				warrior.setPosition(bx, by, bz);
				warrior.enablePersistence();
				warrior.onInitialSpawn(world.getDifficultyForLocation(pos), null);

				world.spawnEntity(warrior);
			}
		}

		return true;
	}

}
