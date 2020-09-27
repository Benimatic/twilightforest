package twilightforest.client;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

import java.util.*;

public class ModelUtils {

	public static void registerToState(Block b, int itemMeta, IBlockState state) {
		registerToState(b, itemMeta, state, new DefaultStateMapper());
	}

	public static void registerToState(Block b, int itemMeta, IBlockState state, IStateMapper stateMapper) {
		ModelResourceLocation mrl = stateMapper.putStateModelLocations(state.getBlock()).get(state);
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(b), itemMeta, mrl);
	}

	public static <T extends Comparable<T>> void registerToStateSingleVariant(Block b, IProperty<T> variant) {
		registerToStateSingleVariant(b, variant, new DefaultStateMapper());
	}

	public static <T extends Comparable<T>> void registerToStateSingleVariant(Block b, IProperty<T> variant, IStateMapper stateMapper) {
		List<T> variants = new ArrayList<>(variant.getAllowedValues());
		for (int i = 0; i < variants.size(); i++) {
			registerToState(b, i, b.getDefaultState().withProperty(variant, variants.get(i)), stateMapper);
		}
	}

	public static void registerIncludingItemModels(Block b, String inventoryPrefix, IProperty<?>... blockIgnorables) {
		registerIncludingItemModels(b, inventoryPrefix, blockIgnorables, new IProperty[0]);
	}

	public static void registerIncludingItemModels(Block b, String inventoryPrefix, IProperty<?>[] blockIgnorables, IProperty<?>[] itemIgnorables) {
		Set<IProperty<?>> properties = new HashSet<>(b.getBlockState().getProperties());
		final Item item = Item.getItemFromBlock(b);

		ModelLoader.setCustomStateMapper(b, new StateMap.Builder().ignore(blockIgnorables).build());

		if (item != Items.AIR) {
			if (itemIgnorables.length > 0) {
				for (IProperty<?> ignore : itemIgnorables) properties.remove(ignore);

				Set<IBlockState> states = new HashSet<>();
				final IBlockState defaultState = b.getDefaultState();
				states.add(defaultState);

				for (IProperty<?> prop : properties) {
					ImmutableSet.Builder<IBlockState> statesIn = ImmutableSet.builder();
					statesIn.addAll(states);
					swizzleStatesWithPropertyKey(defaultState, states, statesIn.build(), prop);
				}

				ResourceLocation rl = item.getRegistryName();

				if (rl != null) {
					for (IBlockState state : states) {
						int meta = b.getMetaFromState(state);
						ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(rl, inventoryPrefix + "_" + meta));
					}
				}
			} else {
				ResourceLocation rl = item.getRegistryName();
				if (rl != null) ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(rl, inventoryPrefix));
			}
		}
	}

	private static <T extends Comparable<T>> void swizzleStatesWithPropertyKey(IBlockState defaultState, Collection<IBlockState> target, ImmutableCollection<IBlockState> statesIn, IProperty<T> property) {
		Set<T> values = new HashSet<>(property.getAllowedValues());

		values.remove(defaultState.getValue(property));

		for (T value : values)
			for (IBlockState state : statesIn)
				target.add(state.withProperty(property, value));
	}
}
