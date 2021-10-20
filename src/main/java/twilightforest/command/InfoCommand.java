package twilightforest.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
import twilightforest.util.WorldUtil;
import twilightforest.world.components.chunkgenerators.ChunkGeneratorTwilight;
import twilightforest.world.components.structures.start.TFStructureStart;
import twilightforest.world.registration.TFFeature;
import twilightforest.world.registration.TFGenerationSettings;

import java.util.List;

public class InfoCommand {
    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        return Commands.literal("info").requires(cs -> cs.hasPermission(2)).executes(InfoCommand::run);
    }

    private static int run(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        CommandSourceStack source = ctx.getSource();

        if (!TFGenerationSettings.usesTwilightChunkGenerator(source.getLevel())) {
            throw TFCommand.NOT_IN_TF.create();
        }

        BlockPos pos = new BlockPos(source.getPosition());

        // nearest feature
        BlockPos cc = TFFeature.getNearestCenterXYZ(Mth.floor(pos.getX()), Mth.floor(pos.getZ()));
        TFFeature closestFeature = TFFeature.getFeatureAt(cc.getX(), cc.getZ(), source.getLevel());
        source.sendSuccess(new TranslatableComponent("This command is still WIP, some things may still be broken.").withStyle(ChatFormatting.RED, ChatFormatting.BOLD), false);
        String structurename = new TranslatableComponent("structure." + closestFeature.name).getString();
        source.sendSuccess(new TranslatableComponent("commands.tffeature.nearest", structurename), false);

        // are you in a structure?
        ChunkGeneratorTwilight chunkGenerator = WorldUtil.getChunkGenerator(source.getLevel());
        if (chunkGenerator != null && TFFeature.isInFeatureChunk(pos.getX(), pos.getZ())) {
            source.sendSuccess(new TranslatableComponent("commands.tffeature.structure.inside").withStyle(ChatFormatting.BOLD, ChatFormatting.GREEN), false);
//            source.sendFeedback(new TranslationTextComponent("commands.tffeature.structure.conquer.status", chunkGenerator.isStructureConquered(pos)), false); TODO: Sorry...I got rid of the Chunk Generator's things

            // what is the spawn list
            List<MobSpawnSettings.SpawnerData> spawnList = closestFeature.getSpawnableList(MobCategory.MONSTER);
            source.sendSuccess(new TranslatableComponent("commands.tffeature.structure.spawn_list").withStyle(ChatFormatting.UNDERLINE), false);
            for (MobSpawnSettings.SpawnerData entry : spawnList) {
                source.sendSuccess(new TranslatableComponent("commands.tffeature.structure.spawn_info",entry.type.getDescription().getString(), entry.getWeight().asInt()), false);
            }
        } else {
            source.sendSuccess(new TranslatableComponent("commands.tffeature.structure.outside").withStyle(ChatFormatting.BOLD, ChatFormatting.RED), false);
        }

        return Command.SINGLE_SUCCESS;
    }

}
