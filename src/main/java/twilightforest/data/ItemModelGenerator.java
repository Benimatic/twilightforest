package twilightforest.data;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import twilightforest.TwilightForestMod;
import twilightforest.block.TFBlocks;

import static twilightforest.TwilightForestMod.prefix;

public class ItemModelGenerator extends ItemModelProvider {
	public ItemModelGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, TwilightForestMod.ID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		toBlock(TFBlocks.twilight_oak_planks.get());
		toBlock(TFBlocks.twilight_oak_stairs.get());
		toBlock(TFBlocks.twilight_oak_slab.get());
		woodenButton(TFBlocks.twilight_oak_button.get(), "twilight_oak");
		woodenFence(TFBlocks.twilight_oak_fence.get(), "twilight_oak");
		toBlock(TFBlocks.twilight_oak_gate.get());
		toBlock(TFBlocks.twilight_oak_plate.get());
		toBlockModel(TFBlocks.twilight_oak_trapdoor.get(), "twilight_oak_trapdoor_bottom");
		toBlock(TFBlocks.canopy_planks.get());
		toBlock(TFBlocks.canopy_stairs.get());
		toBlock(TFBlocks.canopy_slab.get());
		woodenButton(TFBlocks.canopy_button.get(), "canopy");
		woodenFence(TFBlocks.canopy_fence.get(), "canopy");
		toBlock(TFBlocks.canopy_gate.get());
		toBlock(TFBlocks.canopy_plate.get());
		toBlockModel(TFBlocks.canopy_trapdoor.get(), "canopy_trapdoor_bottom");
		toBlock(TFBlocks.mangrove_planks.get());
		toBlock(TFBlocks.mangrove_stairs.get());
		toBlock(TFBlocks.mangrove_slab.get());
		woodenButton(TFBlocks.mangrove_button.get(), "mangrove");
		woodenFence(TFBlocks.mangrove_fence.get(), "mangrove");
		toBlock(TFBlocks.mangrove_gate.get());
		toBlock(TFBlocks.mangrove_plate.get());
		toBlockModel(TFBlocks.mangrove_trapdoor.get(), "mangrove_trapdoor_bottom");
		toBlock(TFBlocks.dark_planks.get());
		toBlock(TFBlocks.dark_stairs.get());
		toBlock(TFBlocks.dark_slab.get());
		woodenButton(TFBlocks.dark_button.get(), "darkwood");
		woodenFence(TFBlocks.dark_fence.get(), "darkwood");
		toBlock(TFBlocks.dark_gate.get());
		toBlock(TFBlocks.dark_plate.get());
		toBlockModel(TFBlocks.dark_trapdoor.get(), "dark_trapdoor_bottom");
		toBlock(TFBlocks.time_planks.get());
		toBlock(TFBlocks.time_stairs.get());
		toBlock(TFBlocks.time_slab.get());
		woodenButton(TFBlocks.time_button.get(), "time");
		woodenFence(TFBlocks.time_fence.get(), "time");
		toBlock(TFBlocks.time_gate.get());
		toBlock(TFBlocks.time_plate.get());
		toBlockModel(TFBlocks.time_trapdoor.get(), "time_trapdoor_bottom");
		toBlock(TFBlocks.trans_planks.get());
		toBlock(TFBlocks.trans_stairs.get());
		toBlock(TFBlocks.trans_slab.get());
		woodenButton(TFBlocks.trans_button.get(), "trans");
		woodenFence(TFBlocks.trans_fence.get(), "trans");
		toBlock(TFBlocks.trans_gate.get());
		toBlock(TFBlocks.trans_plate.get());
		toBlockModel(TFBlocks.trans_trapdoor.get(), "trans_trapdoor_bottom");
		toBlock(TFBlocks.mine_planks.get());
		toBlock(TFBlocks.mine_stairs.get());
		toBlock(TFBlocks.mine_slab.get());
		woodenButton(TFBlocks.mine_button.get(), "mine");
		woodenFence(TFBlocks.mine_fence.get(), "mine");
		toBlock(TFBlocks.mine_gate.get());
		toBlock(TFBlocks.mine_plate.get());
		toBlockModel(TFBlocks.mine_trapdoor.get(), "mine_trapdoor_bottom");
		toBlock(TFBlocks.sort_planks.get());
		toBlock(TFBlocks.sort_stairs.get());
		toBlock(TFBlocks.sort_slab.get());
		woodenButton(TFBlocks.sort_button.get(), "sort");
		woodenFence(TFBlocks.sort_fence.get(), "sort");
		toBlock(TFBlocks.sort_gate.get());
		toBlock(TFBlocks.sort_plate.get());
		toBlockModel(TFBlocks.sort_trapdoor.get(), "sort_trapdoor_bottom");
	}

	private void woodenButton(Block button, String variant) {
		getBuilder(button.getRegistryName().getPath())
						.parent(getExistingFile(mcLoc("block/button_inventory")))
						.texture("texture", "blocks/wood/planks_" + variant + "_0");
	}

	private void woodenFence(Block fence, String variant) {
		getBuilder(fence.getRegistryName().getPath())
						.parent(getExistingFile(mcLoc("block/fence_inventory")))
						.texture("texture", "blocks/wood/planks_" + variant + "_0");
	}

	private void toBlock(Block b) {
		toBlockModel(b, b.getRegistryName().getPath());
	}

	private void toBlockModel(Block b, String model) {
		withExistingParent(b.getRegistryName().getPath(), prefix("block/" + model));
	}

	@Override
	public String getName() {
		return "TwilightForest item and itemblock models";
	}
}
