package twilightforest;

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
import twilightforest.world.feature.TFGenCaveStalactite;

import java.util.function.Consumer;

public class IMCHandler {

	private static final ImmutableSet.Builder<IBlockState> BLACKLIST_BUILDER = ImmutableSet.builder();
	private static final ImmutableList.Builder<IBlockState> ORE_BLOCKS_BUILDER = ImmutableList.builder();
	private static final ImmutableList.Builder<ItemStack> LOADING_ICONS_BUILDER = ImmutableList.builder();
	private static final ImmutableMultimap.Builder<IBlockState, IBlockState> CRUMBLE_BLOCKS_BUILDER = ImmutableMultimap.builder();
	private static final ImmutableMultimap.Builder<Integer, TFGenCaveStalactite.StalactiteEntry> STALACTITE_BUILDER = ImmutableMultimap.builder();

	/**
	 IMC NBT Format: You can send all of your requests as one big NBT list rather than needing to shotgun a ton of tiny NBT messages.
	 <pre>
	 root:
		 • "Blacklist"                               - NBTTagList     : List of blockstates to blacklist from blockbreaking (antibuilders, naga, hydra, etc)
			 • List Entry                            - NBTTagCompound : An IBlockState
				 • "Name"                            - String         : Resource location of block. Is not allowed to be Air.
				 • "Properties"                      - NBTTagCompound : Blockstate Properties
					 • [String Property Key]         - String         : Key is nameable to a property key, and the string value attached to it is value to property.

		 • "Ore_Blocks"                              - NBTTagList     : List of blockstates to add to Hollow Hills and Ore Magnets - May change this to a function in the future
			 • List Entry                            - NBTTagCompound : An IBlockState
				 • "Name"                            - String         : Resource location of block. Is not allowed to be Air.
				 • "Properties"                      - NBTTagCompound : Blockstate Properties
					 • [String Property Key]         - String         : Key is nameable to a property key, and the string value attached to it is value to property.
	 			 • "Stalactite_Settings"             - NBTTagCompound : Settings for stalactite generation, exclude if only used for Ore Magnets. May be empty for all default values.
	 			 	 • "Weight"                      - Integer        : Ore weight, decides how often the ore generates. Defaults to 15.
	 				 • "Hill_Size"                   - Integer        : Minimum hill size for the ore stalactite to generate. 1-small, 2-medium, 3-large. Defaults to 3.
	 				 • "Size"                        - Float          : Decides the maximum length of the stalactite relative to the space between hill floor and ceiling. Defaults to 0.7.
	 				 • "Max_Length"                  - Integer        : Maximum length of a stalactite in blocks. Defaults to 8.
	 				 • "Min_Height"                  - Integer        : Minimum space between the hill floor and the stalactite to let it generate. Defaults to 1.

		 • "Crumbling"                               - NBTTagList     : List of blockstates to add to crumble horn crumbling - May change this to a function in the future
			 • List Entry                            - NBTTagCompound : An IBlockState
				 • "Name"                            - String         : Resource location of block. Is not allowed to be Air.
				 • "Properties"                      - NBTTagCompound : Blockstate Properties
				 • "Crumbles"                        - NBTTagList     : List of different blockstates that the blockstate can crumble into
					 • List Entry                    - NBTTagCompound : An IBlockState.
						 • "Name"                    - String         : Resource location of block. Can be Air.
						 • "Properties"              - NBTTagCompound : Blockstate Properties
							 • [String Property Key] - String         : Key is nameable to a property key, and the string value attached to it is value to property.
	 </pre>
	 */

	static void onIMC(FMLInterModComms.IMCEvent event) {
		for (FMLInterModComms.IMCMessage message : event.getMessages()) {
			if (message.isNBTMessage()) {
				NBTTagCompound imcCompound = message.getNBTValue();

				readStatesFromTagList(imcCompound.getTagList("Blacklist", Constants.NBT.TAG_COMPOUND), BLACKLIST_BUILDER::add);
				readFromTagList(imcCompound.getTagList("Ore_Blocks", Constants.NBT.TAG_COMPOUND), IMCHandler::handleOre);
				readFromTagList(imcCompound.getTagList("Crumbling",  Constants.NBT.TAG_COMPOUND), IMCHandler::handleCrumble);
			}

			if (message.isItemStackMessage() && message.key.equals("Loading_Icon")) {
				LOADING_ICONS_BUILDER.add(message.getItemStackValue());
			}
		}
	}

	private static void readFromTagList(NBTTagList list, Consumer<NBTTagCompound> consumer) {
		for (int i = 0; i < list.tagCount(); i++) {
			consumer.accept(list.getCompoundTagAt(i));
		}
	}

	private static void readStatesFromTagList(NBTTagList list, Consumer<IBlockState> consumer) {
		for (int i = 0; i < list.tagCount(); i++) {
			IBlockState state = NBTUtil.readBlockState(list.getCompoundTagAt(i));
			if (state.getBlock() != Blocks.AIR) {
				consumer.accept(state);
			}
		}
	}

	private static void handleCrumble(NBTTagCompound nbt) {
		IBlockState key = NBTUtil.readBlockState(nbt);
		if (key.getBlock() != Blocks.AIR) {
			readStatesFromTagList(nbt.getTagList("Crumbling", Constants.NBT.TAG_COMPOUND), value -> CRUMBLE_BLOCKS_BUILDER.put(key, value));
		}
	}

	private static void handleOre(NBTTagCompound nbt) {
		IBlockState nbtState = NBTUtil.readBlockState(nbt);

		if (nbtState.getBlock() != Blocks.AIR) {
			ORE_BLOCKS_BUILDER.add(nbtState);

			if (nbt.hasKey("Stalactite_Settings", Constants.NBT.TAG_COMPOUND)) {
				NBTTagCompound settings = nbt.getCompoundTag("Stalactite_Settings");
				int weight    = readInt(settings, "Weight", 15);
				int hillSize  = readInt(settings, "Hill_Size", 3);
				float size    = readFloat(settings, "Size", 0.7f);
				int maxLength = readInt(settings, "Max_Length", 8);
				int minHeight = readInt(settings, "Min_Height", 1);
				STALACTITE_BUILDER.put(hillSize, new TFGenCaveStalactite.StalactiteEntry(nbtState, size, maxLength, minHeight, weight));
			}
		}
	}

	private static int readInt(NBTTagCompound tag, String key, int defaultValue) {
		return tag.hasKey(key, Constants.NBT.TAG_ANY_NUMERIC) ? tag.getInteger(key) : defaultValue;
	}

	private static float readFloat(NBTTagCompound tag, String key, float defaultValue) {
		return tag.hasKey(key, Constants.NBT.TAG_ANY_NUMERIC) ? tag.getFloat(key) : defaultValue;
	}

	public static ImmutableSet<IBlockState> getBlacklistedBlocks() {
		return BLACKLIST_BUILDER.build();
	}

	public static ImmutableList<IBlockState> getOreBlocks() {
		return ORE_BLOCKS_BUILDER.build();
	}

	public static ImmutableList<ItemStack> getLoadingIconStacks() {
		return LOADING_ICONS_BUILDER.build();
	}

	public static ImmutableMultimap<IBlockState, IBlockState> getCrumblingBlocks() {
		return CRUMBLE_BLOCKS_BUILDER.build();
	}

	public static ImmutableMultimap<Integer, TFGenCaveStalactite.StalactiteEntry> getStalactites() {
		return STALACTITE_BUILDER.build();
	}
}
