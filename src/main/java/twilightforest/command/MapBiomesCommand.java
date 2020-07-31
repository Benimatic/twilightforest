package twilightforest.command;

/**
 * Thank you @SuperCoder79 (from Twitter) for sharing the original code! Code sourced from a LGPL project
 */


import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.biome.Biome;
import twilightforest.item.ItemTFMagicMap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MapBiomesCommand {
    private static Object2IntMap<Supplier<Biome>> BIOME2COLOR = new Object2IntOpenHashMap<>();
    private static final DecimalFormat numberFormat = new DecimalFormat("#.00");

    //tatic {
    //   List<Supplier<Biome>> BIOMES = ImmutableList.of( //TODO: Can we do this more efficiently?
    //           TFBiomes.clearing,
    //           TFBiomes.oakSavanna,
    //           TFBiomes.twilightForest,
    //           TFBiomes.denseTwilightForest,
    //           TFBiomes.fireflyForest,
    //           TFBiomes.mushrooms,
    //           TFBiomes.deepMushrooms,
    //           TFBiomes.enchantedForest,
    //           TFBiomes.tfSwamp,
    //           TFBiomes.fireSwamp,
    //           TFBiomes.darkForest,
    //           TFBiomes.darkForestCenter,
    //           TFBiomes.snowy_forest,
    //           TFBiomes.glacier,
    //           TFBiomes.highlands,
    //           TFBiomes.thornlands,
    //           TFBiomes.highlandsCenter
    //   );

    //   BIOME2COLOR = BIOMES.stream().collect(toMap(
    //           x->x,
    //           x->x.get().hashCode() | 0xFF_00_00_00,
    //           (a, b)->b, Object2IntOpenHashMap::new
    //   ));
    //

    public static LiteralArgumentBuilder<CommandSource> register() {
        // TODO elevate command perm
        return Commands.literal("biomepng").executes(MapBiomesCommand::execute);
    }

    private static int execute(CommandContext<CommandSource> source) {
        //setup image
        Map<Biome, Integer> biomeCount = new HashMap<>();
        BufferedImage img = new BufferedImage(2048, 2048, BufferedImage.TYPE_INT_RGB);

        int progressUpdate = img.getHeight() / 8;

        for (int x = 0; x < img.getHeight(); x++) {
            for (int z = 0; z < img.getWidth(); z++) {
                Biome b = source.getSource().getWorld().getNoiseBiome(x - 1024, 0, z - 1024);
                Integer color = BIOME2COLOR.get(b.delegate);

                if ( color == null || (color & 0xFFFFFF) == 0) {
                    BIOME2COLOR.put(b.delegate, color = (ItemTFMagicMap.getBiomeColor(b) | 0xFF000000));
                }

                if ((color & 0xFFFFFF) == 0) {
                    BIOME2COLOR.put(b.delegate, color = (ItemTFMagicMap.getBiomeColor(b) | 0xFF000000));
                }

                if (!biomeCount.containsKey(b)) {
                    biomeCount.put(b, 0);
                } else {
                    biomeCount.put(b, biomeCount.get(b) + 1);
                }

                //set the color
                img.setRGB(x, z, color);
            }

            //send a progress update to let people know the server isn't dying
            if (x % progressUpdate == 0) {
                source.getSource().sendFeedback(new TranslationTextComponent(((double) x / img.getHeight()) * 100 + "% Done mapping"), true);
            }
        }

        source.getSource().sendFeedback(new StringTextComponent("Approximate biome-block counts within an 2048x2048 region"), true);
        int totalCount = biomeCount.values().stream().mapToInt(i -> i).sum();
        biomeCount.forEach((biome, integer) -> source.getSource().sendFeedback(new StringTextComponent(biome.getTranslationKey()).appendString(": " + (integer) + TextFormatting.GRAY + " (" + numberFormat.format(((double) integer / totalCount) * 100) + "%)"), true));

        //save the biome map
        Path p = Paths.get("biomemap.png");
        try {
            ImageIO.write(img, "png", p.toAbsolutePath().toFile());
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }

        return Command.SINGLE_SUCCESS;
    }
}