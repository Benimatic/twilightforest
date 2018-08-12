package twilightforest;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class IMCHandler {

	private static final ImmutableSet.Builder<IBlockState> BLACKLIST_BUILDER = ImmutableSet.builder();
	private static final ImmutableList.Builder<IBlockState> HILL_BLOCKS_BUILDER = ImmutableList.builder();
	private static final ImmutableList.Builder<ItemStack> LOADING_ICONS_BUILDER = ImmutableList.builder();
	private static final ImmutableMultimap.Builder<IBlockState, IBlockState> CRUMBLE_BLOCKS_BUILDER = ImmutableMultimap.builder();

	/*
	 IMC NBT Format: You can send all of your requests as one big NBT list rather than needing to shotgun a ton of tiny NBT messages.

	 root:
		 • "Blacklist"                               - NBTTagList     : List of blockstates to blacklist from blockbreaking (antibuilders, naga, hydra, etc)
			 • List Entry                            - NBTTagCompound : An IBlockState
				 • "Name"                            - String         : Resource location of block. Is not allowed to be Air.
				 • "Properties"                      - NBTTagCompound : Additional blockstate modifications to apply to block
					 • [String Property Key]         - String         : Key is nameable to a property key, and the string value attached to it is value to property.

		 • "Hollow_Hill"                             - NBTTagList     : List of blockstates to add to hollow hills - May chance this to a function in the future
			 • List Entry                            - NBTTagCompound : An IBlockState
				 • "Name"                            - String         : Resource location of block. Is not allowed to be Air.
				 • "Properties"                      - NBTTagCompound : Additional blockstate modifications to apply to block
					 • [String Property Key]         - String         : Key is nameable to a property key, and the string value attached to it is value to property.

		 • "Crumbling"                               - NBTTagList     : List of blockstates to add to hollow hills - May chance this to a function in the future
			 • List Entry                            - NBTTagCompound : An IBlockState
				 • "Name"                            - String         : Resource location of block. Is not allowed to be Air.
				 • "Properties"                      - NBTTagCompound : Additional blockstate modifications to apply to block
				 • "Crumbles"                        - NBTTagList     : List of different blockstates that the blockstate can crumble into
					 • List Entry                    - NBTTagCompound : An IBlockState.
						 • "Name"                    - String         : Resource location of block. Can be Air.
						 • "Properties"              - NBTTagCompound : Additional blockstate modifications to apply to block
							 • [String Property Key] - String         : Key is nameable to a property key, and the string value attached to it is value to property.
	 */

	public static void handleMessage(FMLInterModComms.IMCMessage message) {
		if (message.isNBTMessage()) {
			NBTTagCompound imcCompound = message.getNBTValue();

			deserializeBlockstatesFromTagList(imcCompound.getTagList("Blacklist"  , Constants.NBT.TAG_COMPOUND), BLACKLIST_BUILDER     );
			deserializeBlockstatesFromTagList(imcCompound.getTagList("Hollow_Hill", Constants.NBT.TAG_COMPOUND), HILL_BLOCKS_BUILDER   );

			deserializeBlockstatesFromTagList(imcCompound.getTagList("Crumbling"  , Constants.NBT.TAG_COMPOUND), CRUMBLE_BLOCKS_BUILDER);
		}

		if (message.isItemStackMessage()) {
			if (message.key.equals("Loading_Icon")) {
				LOADING_ICONS_BUILDER.add(message.getItemStackValue());
			}
		}
	}

	private static void deserializeBlockstatesFromTagList(NBTTagList list, ImmutableMultimap.Builder<IBlockState, IBlockState> builder) {
		for (int blockAt = 0; blockAt < list.tagCount(); blockAt++) {
			NBTTagCompound main = list.getCompoundTagAt(blockAt);
			IBlockState key = NBTUtil.readBlockState(main);

			if (key.getBlock() != Blocks.AIR) {
				NBTTagList crumbles = main.getTagList("Crumbling", Constants.NBT.TAG_COMPOUND);

				for (int crumble = 0; crumble < crumbles.tagCount(); crumble++) {
					IBlockState value = NBTUtil.readBlockState(crumbles.getCompoundTagAt(crumble));

					builder.put(key, value);
				}
			}
		}
	}

	private static void deserializeBlockstatesFromTagList(NBTTagList list, ImmutableCollection.Builder<IBlockState> builder) {
		for (int blockAt = 0; blockAt < list.tagCount(); blockAt++) {
			IBlockState state = NBTUtil.readBlockState(list.getCompoundTagAt(blockAt));

			if (state.getBlock() != Blocks.AIR)
				builder.add(state);

			//Block block = Block.REGISTRY.getObject(new ResourceLocation(compound.getString("name")));

			//if (block != Blocks.AIR) {
			//	IBlockState blockState = block.getStateFromMeta(compound.getInteger("meta"));

			//	BlockStateContainer stateContainer = block.getBlockState();

			//	NBTTagList properties = compound.getTagList("state", Constants.NBT.TAG_COMPOUND);
			//	for (int stateAt = 0; stateAt < properties.tagCount(); stateAt++) {
			//		NBTTagCompound property = properties.getCompoundTagAt(stateAt);

			//		IProperty prop = stateContainer.getProperty(property.getString("property"));

			//		if (prop != null)
			//			blockState = applyBlockStateProperty(blockState, prop, prop.getValueClass(), prop.parseValue(property.getString("value")));
			//	}

			//	builder.add(blockState);
			//}
		}
	}

	/*private <V extends Comparable<V>> IBlockState applyBlockStateProperty(IBlockState state, IProperty<V> property, Class<V> target, Optional optional) {
		if (optional.isPresent() && target.isInstance(optional.get()))
			return state.withProperty(property, (V) optional.get());
		else
			return state;
	}*/

	public static ImmutableSet<IBlockState> getBlacklistedBlocks() {
		return BLACKLIST_BUILDER.build();
	}

	public static ImmutableList<IBlockState> getHollowHillBlocks() {
		return HILL_BLOCKS_BUILDER.build();
	}

	public static ImmutableList<ItemStack> getLoadingIconStacks() {
		return LOADING_ICONS_BUILDER.build();
	}

	public static ImmutableMultimap<IBlockState, IBlockState> getCrumblingBlocks() {
		return CRUMBLE_BLOCKS_BUILDER.build();
	}
}
