package twilightforest.client;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.multiplayer.ClientAdvancementManager;
import net.minecraft.command.CommandException;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import twilightforest.TFCommonProxy;
import twilightforest.TFSounds;
import twilightforest.client.renderer.tileentity.*;
import twilightforest.tileentity.TileEntityTFTrophy;

public class TFClientProxy extends TFCommonProxy {

	private boolean isDangerOverlayShown;

	public static MusicTicker.MusicType TFMUSICTYPE;

	@Override
	public void init() {

//		MinecraftForge.EVENT_BUS.register(new LoadingScreenListener());

//		TFMUSICTYPE = EnumHelperClient.addMusicType("TFMUSIC", TFSounds.MUSIC, 1200, 12000);

		//ShaderManager.initShaders();

//		ClientCommandHandler.instance.registerCommand(new CommandBase() {
//			@Override
//			public String getName() {
//				return "tfreload";
//			}
//
//			@Override
//			public String getUsage(ICommandSender sender) {
//				return "commands.tffeature.reload";
//			}
//
//			@Override
//			public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
//				if(FMLCommonHandler.instance().getEffectiveSide().isClient()) {
//					Minecraft.getInstance().player.sendMessage(new StringTextComponent("Reloading Twilight Forest Shaders!"));
//					twilightforest.client.shader.ShaderManager.getShaderReloadListener().onResourceManagerReload(net.minecraft.client.Minecraft.getInstance().getResourceManager());
//					if (TFCompat.IMMERSIVEENGINEERING.isActivated())
//						twilightforest.compat.ie.IEShaderRegister.initShaders();
//				}
//			}
//		});
	}

//	public boolean isDangerOverlayShown() {
//		return isDangerOverlayShown;
//	}
//
//	public void setDangerOverlayShown(boolean isDangerOverlayShown) {
//		this.isDangerOverlayShown = isDangerOverlayShown;
//	}

//	@Override
//	public boolean doesPlayerHaveAdvancement(PlayerEntity player, ResourceLocation advId) {
//		if (player instanceof ClientPlayerEntity) {
//			ClientAdvancementManager manager = ((ClientPlayerEntity) player).connection.getAdvancementManager();
//			Advancement adv = manager.getAdvancementList().getAdvancement(advId);
//			if (adv == null) return false;
//			AdvancementProgress progress = manager.advancementToProgress.get(adv);
//			return progress != null && progress.isDone();
//		}
//
//		return super.doesPlayerHaveAdvancement(player, advId);
//	}
}
