package twilightforest.structures.finalcastle;

import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.Rotation;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import twilightforest.TFFeature;
import twilightforest.block.BlockTFBossSpawner;
import twilightforest.block.BlockTFCastleMagic;
import twilightforest.block.BlockTFForceField;
import twilightforest.block.TFBlocks;
import twilightforest.enums.BossVariant;
import twilightforest.structures.StructureTFComponentOld;
import twilightforest.util.RotationUtil;

import java.util.List;
import java.util.Random;

public class ComponentTFFinalCastleBossGazebo extends StructureTFComponentOld {

	@SuppressWarnings("unused")
	public ComponentTFFinalCastleBossGazebo() {
	}

	public ComponentTFFinalCastleBossGazebo(TFFeature feature, Random rand, int i, StructureTFComponentOld keep) {
		super(feature, i);
		this.spawnListIndex = -1; // no monsters

		this.setCoordBaseMode(keep.getCoordBaseMode());
		this.boundingBox = new StructureBoundingBox(keep.getBoundingBox().minX + 14, keep.getBoundingBox().maxY + 2, keep.getBoundingBox().minZ + 14, keep.getBoundingBox().maxX - 14, keep.getBoundingBox().maxY + 13, keep.getBoundingBox().maxZ - 14);

	}

	@Override
	public void buildComponent(StructureComponent parent, List<StructureComponent> list, Random rand) {
		this.deco = new StructureTFDecoratorCastle();
		this.deco.blockState = TFBlocks.castle_rune_brick.getDefaultState().withProperty(BlockTFCastleMagic.COLOR, EnumDyeColor.BLUE);

		this.deco.fenceState = TFBlocks.force_field.getDefaultState().withProperty(BlockTFForceField.COLOR, EnumDyeColor.PURPLE);
	}

	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		// walls
		for (Rotation rotation : RotationUtil.ROTATIONS) {
			this.fillBlocksRotated(world, sbb, 0, 0, 0, 0, 10, 20, deco.fenceState, rotation);
		}

		// roof
		this.fillWithBlocks(world, sbb, 0, 11, 0, 20, 11, 20, deco.fenceState, deco.fenceState, false);

		//this.placeSignAtCurrentPosition(world, 10, 0, 10, sbb, "Final Boss Here", "You win!", "discord.gg/6v3z26B");

		setInvisibleTextEntity(world, 10, 0, 10, sbb, "Final Boss Here", true, 2.3f);
		setInvisibleTextEntity(world, 10, 0, 10, sbb, "You win!", true, 2.0f);
		setInvisibleTextEntity(world, 10, 0, 10, sbb, "You can join the Twilight Forest Discord server to follow",true, 1.0f);
		setInvisibleTextEntity(world, 10, 0, 10, sbb, "the latest updates on this castle and other content at:",true, 0.7f);
		setInvisibleTextEntity(world, 10, 0, 10, sbb, "discord.experiment115.com", true, 0.4f);

		setBlockState(world, TFBlocks.boss_spawner.getDefaultState().withProperty(BlockTFBossSpawner.VARIANT, BossVariant.FINAL_BOSS), 10, 1, 10, sbb);

		return true;
	}


}
