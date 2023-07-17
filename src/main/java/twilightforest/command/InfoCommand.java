package twilightforest.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraftforge.fml.loading.FMLLoader;
import twilightforest.util.LandmarkUtil;
import twilightforest.world.components.chunkgenerators.ChunkGeneratorTwilight;
import twilightforest.world.components.structures.start.TFStructureStart;
import twilightforest.world.components.structures.util.LandmarkStructure;
import twilightforest.world.registration.TFGenerationSettings;

import java.util.List;
import java.util.Optional;

public class InfoCommand {
	public static LiteralArgumentBuilder<CommandSourceStack> register() {
		return Commands.literal("info").requires(cs -> cs.hasPermission(2)).executes(InfoCommand::run);
	}

	private static int run(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
		CommandSourceStack source = ctx.getSource();
		ServerLevel level = source.getLevel();

		if (!TFGenerationSettings.usesTwilightChunkGenerator(level)) {
			throw TFCommand.NOT_IN_TF.create();
		}

		BlockPos pos = BlockPos.containing(source.getPosition());

		Optional<Registry<Structure>> possibleStructureRegistry = level.registryAccess().registry(Registries.STRUCTURE);
		Optional<StructureStart> possibleNearLandmark = LandmarkUtil.locateNearestLandmarkStart(level, SectionPos.blockToSectionCoord(pos.getX()), SectionPos.blockToSectionCoord(pos.getZ()));

		if (possibleStructureRegistry.isEmpty() || possibleNearLandmark.isEmpty() || !(possibleNearLandmark.get().getStructure() instanceof LandmarkStructure landmarkStructure)) return 0;
		StructureStart structureStart = possibleNearLandmark.get();

		ResourceLocation key = possibleStructureRegistry.get().getKey(landmarkStructure);

		if (FMLLoader.isProduction())
			source.sendSuccess(Component.translatable("This command is still WIP, some things may still be broken.").withStyle(ChatFormatting.RED, ChatFormatting.BOLD), false);

		// nearest feature
		String structureName = Component.translatable("structure." + key.getNamespace() + "." + key.getPath()).getString();
		source.sendSuccess(Component.translatable("commands.tffeature.nearest", structureName), false);

		if (structureStart.getBoundingBox().isInside(pos)) {
			source.sendSuccess(Component.translatable("commands.tffeature.structure.inside").withStyle(ChatFormatting.BOLD, ChatFormatting.GREEN), false);

			if (structureStart instanceof TFStructureStart tfStructureStart) {
				source.sendSuccess(Component.translatable("commands.tffeature.structure.conquer.status", tfStructureStart.isConquered()).withStyle(ChatFormatting.BOLD, tfStructureStart.isConquered() ? ChatFormatting.GREEN : ChatFormatting.RED), false);
			}

			// what is the spawn list
			List<MobSpawnSettings.SpawnerData> spawnList = ChunkGeneratorTwilight.gatherPotentialSpawns(level.structureManager(), MobCategory.MONSTER, pos);
			source.sendSuccess(Component.translatable("commands.tffeature.structure.spawn_list").withStyle(ChatFormatting.UNDERLINE), false);
			if (spawnList != null)
				for (MobSpawnSettings.SpawnerData entry : spawnList)
					source.sendSuccess(Component.translatable("commands.tffeature.structure.spawn_info", entry.type.getDescription().getString(), entry.getWeight().asInt()), false);
		} else {
			source.sendSuccess(Component.translatable("commands.tffeature.structure.outside").withStyle(ChatFormatting.BOLD, ChatFormatting.RED), false);
		}

		return Command.SINGLE_SUCCESS;
	}

}
