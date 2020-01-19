package twilightforest.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;

public class ConquerCommand {
    private static final SimpleCommandExceptionType NOT_IN_STRUCTURE = new SimpleCommandExceptionType(new TranslationTextComponent("commands.tffeature.structure.required"));

    public static LiteralArgumentBuilder<CommandSource> register() {
        LiteralArgumentBuilder<CommandSource> conquer = Commands.literal("conquer").executes(ctx -> changeStructureActivity(ctx.getSource().asPlayer(), true));
        LiteralArgumentBuilder<CommandSource> reactivate = Commands.literal("reactivate").executes(ctx -> changeStructureActivity(ctx.getSource().asPlayer(), false));
        return conquer.then(reactivate);
    }

    private static int changeStructureActivity(ServerPlayerEntity player, boolean flag) throws CommandSyntaxException {
        if (!TFWorld.isTwilightForest(player.world)) {
            throw TFCommand.NOT_IN_TF.create();
        }

        // are you in a structure?
        ChunkGeneratorTFBase chunkGenerator = TFWorld.getChunkGenerator(player.world);

        BlockPos pos = new BlockPos(player);
        if (chunkGenerator != null && chunkGenerator.isBlockInStructureBB(pos)) {
            player.sendMessage(new TranslationTextComponent("commands.tffeature.structure.conquer.update", chunkGenerator.isStructureConquered(pos), flag));
            chunkGenerator.setStructureConquered(pos, flag);
            return Command.SINGLE_SUCCESS;
        } else {
            throw NOT_IN_STRUCTURE.create();
        }
    }
}
