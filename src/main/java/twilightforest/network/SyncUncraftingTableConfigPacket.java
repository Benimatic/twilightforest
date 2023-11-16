package twilightforest.network;

import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.NetworkEvent;
import twilightforest.TFConfig;

import java.util.List;

public class SyncUncraftingTableConfigPacket {

	private final double uncraftingMultiplier;
	private final double repairingMultiplier;
	private final boolean allowShapeless;
	private final boolean disabledUncrafting;
	private final boolean disabledTable;
	private final List<String> disabledRecipes;
	private final boolean flipRecipeList;
	private final List<String> disabledModids;
	private final boolean flipModidList;

	//I think casting is fine in this case. The forge config requires that entries extend string but they should always be strings
	@SuppressWarnings("unchecked")
	public SyncUncraftingTableConfigPacket(double uncraftingMultiplier, double repairingMultiplier, boolean allowShapeless, boolean disabledUncrafting, boolean disabledTable, List<? extends String> disabledRecipes, boolean flipRecipeList, List<? extends String> disabledModids, boolean flipModidList) {
		this.uncraftingMultiplier = uncraftingMultiplier;
		this.repairingMultiplier = repairingMultiplier;
		this.allowShapeless = allowShapeless;
		this.disabledUncrafting = disabledUncrafting;
		this.disabledTable = disabledTable;
		this.disabledRecipes = (List<String>) disabledRecipes;
		this.flipRecipeList = flipRecipeList;
		this.disabledModids = (List<String>) disabledModids;
		this.flipModidList = flipModidList;
	}

	public SyncUncraftingTableConfigPacket(FriendlyByteBuf buf) {
		this.uncraftingMultiplier = buf.readDouble();
		this.repairingMultiplier = buf.readDouble();
		this.allowShapeless = buf.readBoolean();
		this.disabledUncrafting = buf.readBoolean();
		this.disabledTable = buf.readBoolean();
		this.disabledRecipes = buf.readList(FriendlyByteBuf::readUtf);
		this.flipRecipeList = buf.readBoolean();
		this.disabledModids = buf.readList(FriendlyByteBuf::readUtf);
		this.flipModidList = buf.readBoolean();
	}

	public void encode(FriendlyByteBuf buf) {
		buf.writeDouble(this.uncraftingMultiplier);
		buf.writeDouble(this.repairingMultiplier);
		buf.writeBoolean(this.allowShapeless);
		buf.writeBoolean(this.disabledUncrafting);
		buf.writeBoolean(this.disabledTable);
		buf.writeCollection(this.disabledRecipes, FriendlyByteBuf::writeUtf);
		buf.writeBoolean(this.flipRecipeList);
		buf.writeCollection(this.disabledModids, FriendlyByteBuf::writeUtf);
		buf.writeBoolean(this.flipModidList);
	}

	public static class Handler {

		public static boolean onMessage(SyncUncraftingTableConfigPacket message, NetworkEvent.Context ctx) {
			ctx.enqueueWork(() -> {
				TFConfig.COMMON_CONFIG.UNCRAFTING_STUFFS.uncraftingXpCostMultiplier.set(message.uncraftingMultiplier);
				TFConfig.COMMON_CONFIG.UNCRAFTING_STUFFS.repairingXpCostMultiplier.set(message.repairingMultiplier);
				TFConfig.COMMON_CONFIG.UNCRAFTING_STUFFS.allowShapelessUncrafting.set(message.allowShapeless);
				TFConfig.COMMON_CONFIG.UNCRAFTING_STUFFS.disableUncraftingOnly.set(message.disabledUncrafting);
				TFConfig.COMMON_CONFIG.UNCRAFTING_STUFFS.disableEntireTable.set(message.disabledTable);
				TFConfig.COMMON_CONFIG.UNCRAFTING_STUFFS.disableUncraftingRecipes.set(message.disabledRecipes);
				TFConfig.COMMON_CONFIG.UNCRAFTING_STUFFS.reverseRecipeBlacklist.set(message.flipRecipeList);
				TFConfig.COMMON_CONFIG.UNCRAFTING_STUFFS.blacklistedUncraftingModIds.set(message.disabledModids);
				TFConfig.COMMON_CONFIG.UNCRAFTING_STUFFS.flipUncraftingModIdList.set(message.flipModidList);
			});
			ctx.setPacketHandled(true);
			return true;
		}
	}
}
