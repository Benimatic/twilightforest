package twilightforest.data;

import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.Half;
import net.minecraft.state.properties.SlabType;
import net.minecraft.state.properties.StairsShape;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ModelFile;
import twilightforest.TwilightForestMod;
import twilightforest.block.TFBlocks;

import javax.annotation.Nonnull;

import static twilightforest.TwilightForestMod.prefix;

public class BlockstateGenerator extends BlockStateProvider {
	public BlockstateGenerator(DataGenerator gen, ExistingFileHelper exFileHelper) {
		super(gen, TwilightForestMod.ID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		registerWoodBlocks();
	}

	private void registerWoodBlocks() {
		woodBlock(TFBlocks.twilight_oak_planks.get(), TFBlocks.twilight_oak_slab.get(), TFBlocks.twilight_oak_stairs.get(), TFBlocks.twilight_oak_button.get(), "planks_twilight_oak");
		woodBlock(TFBlocks.canopy_planks.get(), TFBlocks.canopy_slab.get(), TFBlocks.canopy_stairs.get(), TFBlocks.canopy_button.get(), "planks_canopy");
		woodBlock(TFBlocks.mangrove_planks.get(), TFBlocks.mangrove_slab.get(), TFBlocks.mangrove_stairs.get(), TFBlocks.mangrove_button.get(), "planks_mangrove");
		woodBlock(TFBlocks.dark_planks.get(), TFBlocks.dark_slab.get(), TFBlocks.dark_stairs.get(), TFBlocks.dark_button.get(), "planks_darkwood");
		woodBlock(TFBlocks.time_planks.get(), TFBlocks.time_slab.get(), TFBlocks.time_stairs.get(), TFBlocks.time_button.get(), "planks_time");
		woodBlock(TFBlocks.trans_planks.get(), TFBlocks.trans_slab.get(), TFBlocks.trans_stairs.get(), TFBlocks.trans_button.get(), "planks_trans");
		woodBlock(TFBlocks.mine_planks.get(), TFBlocks.mine_slab.get(), TFBlocks.mine_stairs.get(), TFBlocks.mine_button.get(), "planks_mine");
		woodBlock(TFBlocks.sort_planks.get(), TFBlocks.sort_slab.get(), TFBlocks.sort_stairs.get(), TFBlocks.sort_button.get(), "planks_sort");
	}

	private void woodBlock(Block plank, Block slab, StairsBlock stair, Block button, String texName) {
		ResourceLocation tex0 = prefix("blocks/wood/" + texName + "_0");
		ResourceLocation tex1 = prefix("blocks/wood/" + texName + "_1");
		ResourceLocation tex2 = prefix("blocks/wood/" + texName + "_2");
		ResourceLocation tex3 = prefix("blocks/wood/" + texName + "_3");
		ConfiguredModel[] plankModels = ConfiguredModel.builder()
						.weight(10).modelFile(models().cubeAll(plank.getRegistryName().getPath() + "_0", tex0)).nextModel()
						.weight(10).modelFile(models().cubeAll(plank.getRegistryName().getPath() + "_1", tex1)).nextModel()
						.weight(1).modelFile(models().cubeAll(plank.getRegistryName().getPath() + "_2", tex2)).nextModel()
						.weight(1).modelFile(models().cubeAll(plank.getRegistryName().getPath() + "_3", tex3)).build();
		getVariantBuilder(plank).partialState().setModels(plankModels);

		ConfiguredModel[] bottomSlabModels = ConfiguredModel.builder()
						.weight(10).modelFile(models().slab(slab.getRegistryName().getPath() + "_0", tex0, tex0, tex0)).nextModel()
						.weight(10).modelFile(models().slab(slab.getRegistryName().getPath() + "_1", tex1, tex1, tex1)).nextModel()
						.weight(1).modelFile(models().slab(slab.getRegistryName().getPath() + "_2", tex2, tex2, tex2)).nextModel()
						.weight(1).modelFile(models().slab(slab.getRegistryName().getPath() + "_3", tex3, tex3, tex3)).build();
		ConfiguredModel[] topSlabModels = ConfiguredModel.builder()
						.weight(10).uvLock(true).rotationX(180).modelFile(bottomSlabModels[0].model).nextModel()
						.weight(10).uvLock(true).rotationX(180).modelFile(bottomSlabModels[1].model).nextModel()
						.weight(1).uvLock(true).rotationX(180).modelFile(bottomSlabModels[2].model).nextModel()
						.weight(1).uvLock(true).rotationX(180).modelFile(bottomSlabModels[3].model).build();
		getVariantBuilder(slab).partialState().with(SlabBlock.TYPE, SlabType.BOTTOM).setModels(bottomSlabModels);
		getVariantBuilder(slab).partialState().with(SlabBlock.TYPE, SlabType.TOP).setModels(topSlabModels);
		getVariantBuilder(slab).partialState().with(SlabBlock.TYPE, SlabType.DOUBLE).setModels(plankModels);

		woodStairs(stair, texName);
		woodButton(button, texName);
	}

	private void woodButton(Block button, String texName) {
		ResourceLocation tex0 = prefix("blocks/wood/" + texName + "_0");
		ResourceLocation tex1 = prefix("blocks/wood/" + texName + "_1");
		ResourceLocation tex2 = prefix("blocks/wood/" + texName + "_2");
		ResourceLocation tex3 = prefix("blocks/wood/" + texName + "_3");
		ModelFile unpressed0 = models().withExistingParent(button.getRegistryName().getPath() + "_0", "button").texture("texture", tex0);
		ModelFile pressed0 = models().withExistingParent(button.getRegistryName().getPath() + "_pressed_0", "button_pressed").texture("texture", tex0);
		ModelFile unpressed1 = models().withExistingParent(button.getRegistryName().getPath() + "_1", "button").texture("texture", tex1);
		ModelFile pressed1 = models().withExistingParent(button.getRegistryName().getPath() + "_pressed_1", "button_pressed").texture("texture", tex1);
		ModelFile unpressed2 = models().withExistingParent(button.getRegistryName().getPath() + "_2", "button").texture("texture", tex2);
		ModelFile pressed2 = models().withExistingParent(button.getRegistryName().getPath() + "_pressed_2", "button_pressed").texture("texture", tex2);
		ModelFile unpressed3 = models().withExistingParent(button.getRegistryName().getPath() + "_3", "button").texture("texture", tex3);
		ModelFile pressed3 = models().withExistingParent(button.getRegistryName().getPath() + "_pressed_3", "button_pressed").texture("texture", tex3);

		getVariantBuilder(button).forAllStates(state -> {
			ModelFile model0 = state.get(AbstractButtonBlock.POWERED) ? pressed0 : unpressed0;
			ModelFile model1 = state.get(AbstractButtonBlock.POWERED) ? pressed1 : unpressed1;
			ModelFile model2 = state.get(AbstractButtonBlock.POWERED) ? pressed2 : unpressed2;
			ModelFile model3 = state.get(AbstractButtonBlock.POWERED) ? pressed3 : unpressed3;
			int rotX = 0;
			switch (state.get(AbstractButtonBlock.FACE)) {
			case WALL:
				rotX = 90;
				break;
			case FLOOR:
				rotX = 0;
				break;
			case CEILING:
				rotX = 180;
				break;
			}
			int rotY = 0;
			if (state.get(AbstractButtonBlock.FACE) == AttachFace.CEILING)  {
				switch (state.get(AbstractButtonBlock.HORIZONTAL_FACING)) {
				case NORTH: rotY = 180; break;
				case SOUTH: rotY = 0; break;
				case WEST: rotY = 90; break;
				case EAST: rotY = 270; break;
				}
			} else {
				switch (state.get(AbstractButtonBlock.HORIZONTAL_FACING)) {
				case NORTH: rotY = 0; break;
				case SOUTH: rotY = 180; break;
				case WEST: rotY = 270; break;
				case EAST: rotY = 90; break;
				}
			}
			boolean uvlock = state.get(AbstractButtonBlock.FACE) == AttachFace.WALL;

			return ConfiguredModel.builder()
							.weight(10).uvLock(uvlock).rotationX(rotX).rotationY(rotY).modelFile(model0).nextModel()
							.weight(10).uvLock(uvlock).rotationX(rotX).rotationY(rotY).modelFile(model1).nextModel()
							.weight(1).uvLock(uvlock).rotationX(rotX).rotationY(rotY).modelFile(model2).nextModel()
							.weight(1).uvLock(uvlock).rotationX(rotX).rotationY(rotY).modelFile(model3)
							.build();
		});
	}

	private void woodStairs(StairsBlock block, String texName) {
		ResourceLocation tex0 = prefix("blocks/wood/" + texName + "_0");
		ResourceLocation tex1 = prefix("blocks/wood/" + texName + "_1");
		ResourceLocation tex2 = prefix("blocks/wood/" + texName + "_2");
		ResourceLocation tex3 = prefix("blocks/wood/" + texName + "_3");
		ModelFile main0 = models().stairs(block.getRegistryName().getPath() + "_0", tex0, tex0, tex0);
		ModelFile main1 = models().stairs(block.getRegistryName().getPath() + "_1", tex1, tex1, tex1);
		ModelFile main2 = models().stairs(block.getRegistryName().getPath() + "_2", tex2, tex2, tex2);
		ModelFile main3 = models().stairs(block.getRegistryName().getPath() + "_3", tex3, tex3, tex3);
		ModelFile inner0 = models().stairsInner(block.getRegistryName().getPath() + "_inner_0", tex0, tex0, tex0);
		ModelFile inner1 = models().stairsInner(block.getRegistryName().getPath() + "_inner_1", tex1, tex1, tex1);
		ModelFile inner2 = models().stairsInner(block.getRegistryName().getPath() + "_inner_2", tex2, tex2, tex2);
		ModelFile inner3 = models().stairsInner(block.getRegistryName().getPath() + "_inner_3", tex3, tex3, tex3);
		ModelFile outer0 = models().stairsOuter(block.getRegistryName().getPath() + "_outer_0", tex0, tex0, tex0);
		ModelFile outer1 = models().stairsOuter(block.getRegistryName().getPath() + "_outer_1", tex1, tex1, tex1);
		ModelFile outer2 = models().stairsOuter(block.getRegistryName().getPath() + "_outer_2", tex2, tex2, tex2);
		ModelFile outer3 = models().stairsOuter(block.getRegistryName().getPath() + "_outer_3", tex3, tex3, tex3);
		// [VanillaCopy] super.stairsBlock, but multiple files returned each time
		getVariantBuilder(block)
						.forAllStatesExcept(state -> {
							Direction facing = state.get(StairsBlock.FACING);
							Half half = state.get(StairsBlock.HALF);
							StairsShape shape = state.get(StairsBlock.SHAPE);
							int yRot = (int) facing.rotateY().getHorizontalAngle(); // Stairs model is rotated 90 degrees clockwise for some reason
							if (shape == StairsShape.INNER_LEFT || shape == StairsShape.OUTER_LEFT) {
								yRot += 270; // Left facing stairs are rotated 90 degrees clockwise
							}
							if (shape != StairsShape.STRAIGHT && half == Half.TOP) {
								yRot += 90; // Top stairs are rotated 90 degrees clockwise
							}
							yRot %= 360;
							boolean uvlock = yRot != 0 || half == Half.TOP; // Don't set uvlock for states that have no rotation
							return ConfiguredModel.builder()
											.weight(10)
											.modelFile(shape == StairsShape.STRAIGHT ? main0 : shape == StairsShape.INNER_LEFT || shape == StairsShape.INNER_RIGHT ? inner0 : outer0)
											.rotationX(half == Half.BOTTOM ? 0 : 180).rotationY(yRot).uvLock(uvlock)
											.nextModel()

											.weight(10)
											.modelFile(shape == StairsShape.STRAIGHT ? main1 : shape == StairsShape.INNER_LEFT || shape == StairsShape.INNER_RIGHT ? inner1 : outer1)
											.rotationX(half == Half.BOTTOM ? 0 : 180).rotationY(yRot).uvLock(uvlock)
											.nextModel()

											.weight(1)
											.modelFile(shape == StairsShape.STRAIGHT ? main2 : shape == StairsShape.INNER_LEFT || shape == StairsShape.INNER_RIGHT ? inner2 : outer2)
											.rotationX(half == Half.BOTTOM ? 0 : 180).rotationY(yRot).uvLock(uvlock)
											.nextModel()

											.weight(1)
											.modelFile(shape == StairsShape.STRAIGHT ? main3 : shape == StairsShape.INNER_LEFT || shape == StairsShape.INNER_RIGHT ? inner3 : outer3)
											.rotationX(half == Half.BOTTOM ? 0 : 180).rotationY(yRot).uvLock(uvlock)
											.build();
						}, StairsBlock.WATERLOGGED);
	}

	@Nonnull
	@Override
	public String getName() {
		return "TwilightForest blockstates and block models";
	}
}
