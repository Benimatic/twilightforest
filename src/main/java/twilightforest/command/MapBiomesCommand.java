package twilightforest.command;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.fml.loading.FMLEnvironment;
import twilightforest.init.TFBiomes;
import twilightforest.item.MagicMapItem;
import twilightforest.util.ColorUtil;
import twilightforest.world.registration.TFGenerationSettings;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Thank you @SuperCoder79 (from Twitter) for sharing the original code! Code sourced from a LGPL project
 */
public class MapBiomesCommand {
	private static final DecimalFormat numberFormat = new DecimalFormat("#.00");

	private static final HashMap<ResourceLocation, MapColor> BIOME2COLOR = new HashMap<>();

	private static void init() {
		BIOME2COLOR.put(TFBiomes.STREAM.location(), new MapColor(0, 0, 255));
		BIOME2COLOR.put(TFBiomes.LAKE.location(), new MapColor(0, 0, 255));
		BIOME2COLOR.put(TFBiomes.CLEARING.location(), new MapColor(132, 245, 130));
		BIOME2COLOR.put(TFBiomes.OAK_SAVANNAH.location(), new MapColor(239, 245, 130));
		BIOME2COLOR.put(TFBiomes.FOREST.location(), new MapColor(0, 255, 0));
		BIOME2COLOR.put(TFBiomes.DENSE_FOREST.location(), new MapColor(0, 170, 0));
		BIOME2COLOR.put(TFBiomes.FIREFLY_FOREST.location(), new MapColor(88, 252, 102));
		BIOME2COLOR.put(TFBiomes.ENCHANTED_FOREST.location(), new MapColor(0, 255, 255));
		BIOME2COLOR.put(TFBiomes.SPOOKY_FOREST.location(), new MapColor(119, 0, 255));
		BIOME2COLOR.put(TFBiomes.MUSHROOM_FOREST.location(), new MapColor(204, 0, 139));
		BIOME2COLOR.put(TFBiomes.DENSE_MUSHROOM_FOREST.location(), new MapColor(184, 48, 184));

		BIOME2COLOR.put(TFBiomes.SWAMP.location(), new MapColor(0, 204, 187));
		BIOME2COLOR.put(TFBiomes.FIRE_SWAMP.location(), new MapColor(140, 0, 0));

		BIOME2COLOR.put(TFBiomes.DARK_FOREST.location(), new MapColor(25, 61, 13));
		BIOME2COLOR.put(TFBiomes.DARK_FOREST_CENTER.location(), new MapColor(157, 79, 0));

		BIOME2COLOR.put(TFBiomes.SNOWY_FOREST.location(), new MapColor(255, 255, 255));
		BIOME2COLOR.put(TFBiomes.GLACIER.location(), new MapColor(130, 191, 245));

		BIOME2COLOR.put(TFBiomes.HIGHLANDS.location(), new MapColor(100, 65, 0));
		BIOME2COLOR.put(TFBiomes.THORNLANDS.location(), new MapColor(128, 100, 90));
		BIOME2COLOR.put(TFBiomes.FINAL_PLATEAU.location(), new MapColor(128, 128, 128));
	}

	public static LiteralArgumentBuilder<CommandSourceStack> register() {
		return Commands.literal("biomepng").requires(cs -> cs.hasPermission(2)).executes(context -> createMap(context.getSource(), 4096, 4096, true))
				.then(Commands.argument("width", IntegerArgumentType.integer(0))
						.executes(context -> createMap(context.getSource(), IntegerArgumentType.getInteger(context, "width"), IntegerArgumentType.getInteger(context, "width"), true))
						.then(Commands.argument("height", IntegerArgumentType.integer(0))
								.executes(context -> createMap(context.getSource(), IntegerArgumentType.getInteger(context, "width"), IntegerArgumentType.getInteger(context, "height"), true))
								.then(Commands.argument("showBiomePercents", BoolArgumentType.bool())
										.executes(context -> createMap(context.getSource(), IntegerArgumentType.getInteger(context, "width"), IntegerArgumentType.getInteger(context, "height"), BoolArgumentType.getBool(context, "showBiomePercents"))))));

	}

	private static int createMap(CommandSourceStack source, int width, int height, boolean showBiomePercents) throws CommandSyntaxException {
		if (FMLEnvironment.dist.isDedicatedServer())
			return -1;

		if (!TFGenerationSettings.usesTwilightChunkGenerator(source.getLevel())) {
			throw TFCommand.NOT_IN_TF.create();
		}

		if (BIOME2COLOR.isEmpty()) {
			init();
		}

		//setup image
		Map<Biome, Integer> biomeCount = new HashMap<>();
		NativeImage img = new NativeImage(width, height, false);

		int progressUpdate = img.getHeight() / 8;

		for (int x = 0; x < img.getHeight(); x++) {
			for (int z = 0; z < img.getWidth(); z++) {
				ServerLevel level = source.getLevel();
				Biome b = level.getNoiseBiome(x - (img.getWidth() / 2), 0, z - (img.getHeight() / 2)).value();
				ResourceLocation key = level.registryAccess().registryOrThrow(Registries.BIOME).getKey(b);
				MapColor color = BIOME2COLOR.get(key);

				if (color == null) {
					int colorInt = MagicMapItem.getBiomeColor(source.getLevel(), b);

					if (colorInt == 0)
						colorInt = b.getGrassColor(0, 0);

					BIOME2COLOR.put(key, color = new MapColor(colorInt | 0xFF000000));
				}

				if (!biomeCount.containsKey(b)) {
					biomeCount.put(b, 0);
				} else {
					biomeCount.put(b, biomeCount.get(b) + 1);
				}

				//set the color
				img.setPixelRGBA(x, z, ColorUtil.argbToABGR(color.getARGB()));
			}

			//send a progress update to let people know the server isn't dying
			if (x % progressUpdate == 0) {
				source.sendSuccess(Component.translatable(((double) x / img.getHeight()) * 100 + "% Done mapping"), false);
			}
		}

		if (showBiomePercents) {
			source.sendSuccess(Component.literal("Approximate biome-block counts within a " + (width + "x" + height) + " region"), false);
			int totalCount = biomeCount.values().stream().mapToInt(i -> i).sum();
			biomeCount.forEach((biome, integer) -> source.sendSuccess(Component.literal(
							source.getLevel().registryAccess().registryOrThrow(Registries.BIOME).getKey(biome).toString())
					.append(": " + (integer) + ChatFormatting.GRAY + " (" + numberFormat.format(((double) integer / totalCount) * 100) + "%)"), false));
		}

		int startX = Mth.floor(source.getPosition().x()) - (img.getWidth() / 2);
		int startZ = Mth.floor(source.getPosition().z()) - (img.getHeight() / 2);
		//file name is formatted as: biomemap-seed-(startX.startZ)-(endX.endZ)
		//I wanted to put generated maps in the path generated/biomemaps/seed, but it doesnt like when I add more params to the method
		Path p = Paths.get("biome_map-" + source.getLevel().getSeed() + "-(" + startX + "." + startZ + ")-(" + (startX + width) + "." + (startZ + height) + ").png");
		//save the biome map
		try {
			img.writeToFile(p.toAbsolutePath().toFile());
		} catch (IOException e) {
			e.printStackTrace();
			source.sendFailure(Component.literal("Could not save image! Please report this!"));
			return 0;
		}

		source.sendSuccess(Component.literal("Image saved!"), false);

		return Command.SINGLE_SUCCESS;
	}

	public static class MapColor {

		private final int value;

		public MapColor(int r, int g, int b) {
			this(r, g, b, 255);
		}

		public MapColor(int r, int g, int b, int a) {
			this.value = ((a & 0xFF) << 24) |
					((r & 0xFF) << 16) |
					((g & 0xFF) << 8) |
					((b & 0xFF));
		}

		public MapColor(int rgb) {
			this.value = 0xFF000000 | rgb;
		}

		public int getARGB() {
			return this.value;
		}
	}
}