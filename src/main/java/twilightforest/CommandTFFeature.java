package twilightforest;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import twilightforest.world.ChunkProviderTwilightForest;
import twilightforest.world.TFWorldChunkManager;
import twilightforest.world.WorldProviderTwilightForest;

public class CommandTFFeature extends CommandBase {

	@Override
	public String getCommandName() {
		return "tffeature";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "tffeature accepts the following arguments: info";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("info")) {
				// info on current feature

				EntityPlayerMP player = getCommandSenderAsPlayer(sender);
				
				int dx = MathHelper.floor_double(player.posX); 
				int dy = MathHelper.floor_double(player.posY); 
				int dz = MathHelper.floor_double(player.posZ);
				
				if (!(player.worldObj.provider instanceof WorldProviderTwilightForest)) {
					throw new WrongUsageException("commands.tffeature.not_in_twilight_forest", new Object[0]);
				} else {
					// nearest feature
					TFFeature nearbyFeature = ((TFWorldChunkManager)player.worldObj.provider.getBiomeProvider()).getFeatureAt(dx, dz, player.worldObj);
					
					sender.addChatMessage(new TextComponentTranslation("The nearest feature is %s", new Object[] {nearbyFeature.name}));
					
					// are you in a structure?
					ChunkProviderTwilightForest chunkProvider = ((WorldProviderTwilightForest)player.worldObj.provider).getChunkProvider();
					
					if (chunkProvider.isBlockInStructureBB(dx, dy, dz)) {
						sender.addChatMessage(new TextComponentTranslation("You are in the structure for that feature."));

						sender.addChatMessage(new TextComponentTranslation("Structure conquer flag = %s.", new Object[] {chunkProvider.isStructureConquered(dx, dy, dz)}));

						// are you in a room?
						
						// what is the spawn list
//						List<SpawnListEntry> spawnList = chunkProvider.getPossibleCreatures(EnumCreatureType.monster, dx, dy, dz);
//						sender.addChatMessage(new TextComponentTranslation("Spawn list for the area is:"));
//						for (SpawnListEntry entry : spawnList) {
//							sender.addChatMessage(new TextComponentTranslation(entry.toString()));
//						}

						
					} else {
						sender.addChatMessage(new TextComponentTranslation("You are not in the structure for that feature."));
					}
				}
			} else if (args[0].equalsIgnoreCase("reactivate")) {
				changeStructureActivity(sender, false);
			} else if (args[0].equalsIgnoreCase("conquer")) {
				changeStructureActivity(sender, true);
			} else if (args[0].equalsIgnoreCase("center")) {
				EntityPlayerMP player = getCommandSenderAsPlayer(sender);
				
				int dx = MathHelper.floor_double(player.posX); 
				//int dy = MathHelper.floor_double(player.posY); 
				int dz = MathHelper.floor_double(player.posZ);
				
				BlockPos cc = TFFeature.getNearestCenterXYZ(dx >> 4, dz >> 4, player.worldObj);
				
				TFWorldChunkManager wcm = (TFWorldChunkManager)player.worldObj.provider.getBiomeProvider();
				
				boolean fc = wcm.isInFeatureChunk(player.worldObj, dx, dz);
				sender.addChatMessage(new TextComponentTranslation("Center of feature = %s.", new Object[] {cc}));
				sender.addChatMessage(new TextComponentTranslation("Are in feature chunk = %s.", new Object[] {fc}));
			} else {
				throw new WrongUsageException("commands.tffeature.usage", new Object[0]);
			}

		} else {
			throw new WrongUsageException("commands.tffeature.usage", new Object[0]);
		}
	}

	private void changeStructureActivity(ICommandSender sender, boolean flag) throws CommandException {
		// activate current feature
		EntityPlayerMP player = getCommandSenderAsPlayer(sender);
		
		int dx = MathHelper.floor_double(player.posX); 
		int dy = MathHelper.floor_double(player.posY); 
		int dz = MathHelper.floor_double(player.posZ);
		
		if (!(player.worldObj.provider instanceof WorldProviderTwilightForest)) {
			throw new WrongUsageException("commands.tffeature.not_in_twilight_forest", new Object[0]);
		} else {
			// are you in a structure?
			ChunkProviderTwilightForest chunkProvider = ((WorldProviderTwilightForest)player.worldObj.provider).getChunkProvider();
			
			if (chunkProvider.isBlockInStructureBB(dx, dy, dz)) {
				sender.addChatMessage(new TextComponentTranslation("Structure conquer flag was %s.  Changing to %s.", new Object[] {chunkProvider.isStructureConquered(dx, dy, dz), flag}));
				
				chunkProvider.setStructureConquered(dx, dy, dz, flag);
			} else {
				sender.addChatMessage(new TextComponentTranslation("You are not in a structure."));
			}
		}
	}

}
