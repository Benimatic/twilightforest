package twilightforest;

import net.minecraft.advancements.Advancement;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import twilightforest.inventory.ContainerTFUncrafting;

public class TFCommonProxy {
	public void init() {}

	//TODO: YEET
	public Object getServerGuiElement(int id, PlayerEntity player, World world, int x, int y, int z) {
		if (id == TwilightForestMod.GUI_ID_UNCRAFTING) {
			return new ContainerTFUncrafting(player.inventory, world, x, y, z);
		//} else if (id == TwilightForestMod.GUI_ID_FURNACE) {
			//return new FurnaceContainer(player.inventory, (TileEntityTFCinderFurnace) world.getTileEntity(new BlockPos(x, y, z)));
		} else {
			return null;
		}
	}

	//TODO: YEET
	public Object getClientGuiElement(int id, PlayerEntity player, World world, int x, int y, int z) {
		if (id == TwilightForestMod.GUI_ID_UNCRAFTING) {
			return new twilightforest.client.GuiTFGoblinCrafting(player.inventory, world, x, y, z);
/*		} else if (id == TwilightForestMod.GUI_ID_FURNACE) {
			return new GuiFurnace(player.inventory, (TileEntityTFCinderFurnace) world.getTileEntity(new BlockPos(x, y, z)));*/
		} else {
			return null;
		}
	}

	public boolean doesPlayerHaveAdvancement(PlayerEntity player, ResourceLocation advId) {
		if (player instanceof ServerPlayerEntity) {
			ServerWorld world = ((ServerPlayerEntity) player).getServerWorld();
			Advancement adv = world.getServer().getAdvancementManager().getAdvancement(advId);
			return adv != null && ((ServerPlayerEntity) player).getAdvancements().getProgress(adv).isDone();
		}
		return false;
	}
}
