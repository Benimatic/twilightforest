package twilightforest.data.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraftforge.common.data.ExistingFileHelper;
import twilightforest.TwilightForestMod;
import twilightforest.init.TFBannerPatterns;

import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

//a place to hold all custom tags, since I imagine we wont have a lot of them
public class CustomTagGenerator {

	public static class EnchantmentTagGenerator extends TagsProvider<Enchantment> {

		public static final TagKey<Enchantment> PHANTOM_ARMOR_BANNED_ENCHANTS = TagKey.create(Registries.ENCHANTMENT, TwilightForestMod.prefix("phantom_armor_banned_enchants"));

		public EnchantmentTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper helper) {
			super(output, Registries.ENCHANTMENT, provider, TwilightForestMod.ID, helper);
		}

		@Override
		protected void addTags(HolderLookup.Provider provider) {
			tag(PHANTOM_ARMOR_BANNED_ENCHANTS).add(Enchantments.VANISHING_CURSE, Enchantments.BINDING_CURSE);
		}

		@Override
		public String getName() {
			return "Twilight Forest Enchantment Tags";
		}
	}

	public static class BannerPatternTagGenerator extends TagsProvider<BannerPattern> {

		public static final TagKey<BannerPattern> NAGA_BANNER_PATTERN = create("pattern_item/naga");
		public static final TagKey<BannerPattern> LICH_BANNER_PATTERN = create("pattern_item/lich");
		public static final TagKey<BannerPattern> MINOSHROOM_BANNER_PATTERN = create("pattern_item/minoshroom");
		public static final TagKey<BannerPattern> HYDRA_BANNER_PATTERN = create("pattern_item/hydra");
		public static final TagKey<BannerPattern> KNIGHT_PHANTOM_BANNER_PATTERN = create("pattern_item/knight_phantom");
		public static final TagKey<BannerPattern> UR_GHAST_BANNER_PATTERN = create("pattern_item/ur_ghast");
		public static final TagKey<BannerPattern> ALPHA_YETI_BANNER_PATTERN = create("pattern_item/alpha_yeti");
		public static final TagKey<BannerPattern> SNOW_QUEEN_BANNER_PATTERN = create("pattern_item/snow_queen");
		public static final TagKey<BannerPattern> QUEST_RAM_BANNER_PATTERN = create("pattern_item/quest_ram");

		public BannerPatternTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
			super(output, Registries.BANNER_PATTERN, provider, TwilightForestMod.ID, existingFileHelper);
		}

		@Override
		protected void addTags(HolderLookup.Provider provider) {
			tag(NAGA_BANNER_PATTERN).add(TFBannerPatterns.NAGA.get());
			tag(LICH_BANNER_PATTERN).add(TFBannerPatterns.LICH.get());
			tag(MINOSHROOM_BANNER_PATTERN).add(TFBannerPatterns.MINOSHROOM.get());
			tag(HYDRA_BANNER_PATTERN).add(TFBannerPatterns.HYDRA.get());
			tag(KNIGHT_PHANTOM_BANNER_PATTERN).add(TFBannerPatterns.KNIGHT_PHANTOM.get());
			tag(UR_GHAST_BANNER_PATTERN).add(TFBannerPatterns.UR_GHAST.get());
			tag(ALPHA_YETI_BANNER_PATTERN).add(TFBannerPatterns.ALPHA_YETI.get());
			tag(SNOW_QUEEN_BANNER_PATTERN).add(TFBannerPatterns.SNOW_QUEEN.get());
			tag(QUEST_RAM_BANNER_PATTERN).add(TFBannerPatterns.QUEST_RAM.get());
		}

		private static TagKey<BannerPattern> create(String name) {
			return TagKey.create(Registries.BANNER_PATTERN, TwilightForestMod.prefix(name));
		}

		@Override
		public String getName() {
			return "Twilight Forest Banner Pattern Tags";
		}
	}
}
