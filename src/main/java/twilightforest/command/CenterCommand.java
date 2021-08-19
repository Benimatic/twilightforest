package twilightforest.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandRuntimeException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import twilightforest.world.registration.TFFeature;

public class CenterCommand {
    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        return Commands.literal("center").requires(cs -> cs.hasPermission(2)).executes(CenterCommand::run);
    }

    // TODO FIX
    private static int run(CommandContext<CommandSourceStack> ctx) {
        if (true)
            throw new CommandRuntimeException(new TextComponent("This command is not supported!"));

        CommandSourceStack source = ctx.getSource();
        int dx = Mth.floor(source.getPosition().x());
        int dz = Mth.floor(source.getPosition().z());

        BlockPos cc = TFFeature.getNearestCenterXYZ(dx >> 4, dz >> 4);

        boolean fc = TFFeature.isInFeatureChunk(dx, dz);
        source.sendSuccess(new TranslatableComponent("commands.tffeature.center", cc), false);
        source.sendSuccess(new TranslatableComponent("commands.tffeature.chunk", fc), false);
        return Command.SINGLE_SUCCESS;
    }
}
