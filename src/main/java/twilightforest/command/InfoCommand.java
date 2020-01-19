package twilightforest.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import twilightforest.TFFeature;

public class InfoCommand {
    public static LiteralArgumentBuilder<CommandSource> register() {
        return Commands.literal("info").executes(InfoCommand::run);
    }

    private static int run(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().asPlayer();

        if (!TFWorld.isTwilightForest(player.world)) {
            throw TFCommand.NOT_IN_TF.create();
        }

        BlockPos pos = new BlockPos(player);

        // nearest feature
        TFFeature nearbyFeature = TFFeature.getFeatureAt(pos.getX(), pos.getZ(), player.world);
        sender.sendMessage(new TranslationTextComponent("commands.tffeature.nearest", nearbyFeature.name));

        // are you in a structure?
        ChunkGeneratorTFBase chunkGenerator = TFWorld.getChunkGenerator(player.world);
        if (chunkGenerator != null && chunkGenerator.isBlockInStructureBB(pos)) {
            player.sendMessage(new TranslationTextComponent("commands.tffeature.structure.inside"));

            player.sendMessage(new TranslationTextComponent("commands.tffeature.structure.conquer.status", chunkGenerator.isStructureConquered(pos)));
            // are you in a room?

            // what is the spawn list
//						List<SpawnListEntry> spawnList = chunkGenerator.getPossibleCreatures(EnumCreatureType.monster, dx, dy, dz);
//						sender.sendMessage(new TextComponentTranslation("Spawn list for the area is:"));
//						for (SpawnListEntry entry : spawnList) {
//							sender.sendMessage(new TextComponentTranslation(entry.toString()));
//						}
        } else {
            player.sendMessage(new TranslationTextComponent("commands.tffeature.structure.outside"));
        }

        return Command.SINGLE_SUCCESS;
    }

}
