package twilightforest.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.properties.IProperty;
import net.minecraft.item.*;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import twilightforest.TwilightForestMod;
import twilightforest.block.BlockTFLog;
import twilightforest.block.BlockTFMagicLog;
import twilightforest.block.TFBlocks;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.compat.TFCompat;
import twilightforest.enums.*;
import twilightforest.util.IMapColorSupplier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.stream;

//TODO: Currently retained for BlockItem registration reference
@Mod.EventBusSubscriber(modid = TwilightForestMod.ID)
public class RegisterItemEvent {
	@SubscribeEvent
	public static void onRegisterItems(RegistryEvent.Register<Item> event) {
		ItemRegistryHelper items = new ItemRegistryHelper(event.getRegistry());

		String[] thornNames = stream(ThornVariant.values()).map(IStringSerializable::getName).toArray(String[]::new);
		String[] deadrockNames = stream(DeadrockVariant.values()).map(IStringSerializable::getName).toArray(String[]::new);
		// register blocks with their pickup values
		items.registerSubItemBlock(TFBlocks.twilight_log);
		items.registerSubItemBlock(TFBlocks.root);
		items.register(new ItemBlockTFLeaves(TFBlocks.twilight_leaves));
		items.register(new ItemBlockWearable(TFBlocks.firefly));
		items.register(new ItemBlockWearable(TFBlocks.cicada));
		items.registerSubItemBlock(TFBlocks.maze_stone);
		items.registerSubItemBlock(TFBlocks.hedge);
		items.registerSubItemBlock(TFBlocks.boss_spawner);
		items.registerBlock(TFBlocks.firefly_jar);
		items.register(new ItemBlockTFPlant(TFBlocks.twilight_plant));
		items.registerBlock(TFBlocks.uncrafting_table);
		items.registerSubItemBlock(TFBlocks.fire_jet);
		items.registerSubItemBlock(TFBlocks.naga_stone);
		items.register(new ItemBlockTFMeta(TFBlocks.twilight_sapling) {
			@Override
			public int getItemBurnTime(ItemStack itemStack) {
				return 100;
			}

			@Override
			public Rarity getRarity(ItemStack stack) {
				switch (stack.getMetadata()) {
					case 5:
					case 6:
					case 7:
					case 8:
						return Rarity.RARE;
					default:
						return Rarity.COMMON;
				}
			}
		}.setAppend(true));
		items.register(new ItemBlockWearable(TFBlocks.moonworm));
		items.registerSubItemBlock(TFBlocks.magic_log);
		items.register(new ItemBlockTFLeaves(TFBlocks.magic_leaves));
		items.registerSubItemBlock(TFBlocks.magic_log_core);
		items.registerSubItemBlock(TFBlocks.tower_wood);
		items.registerSubItemBlock(TFBlocks.tower_device);
		items.registerSubItemBlock(TFBlocks.tower_translucent);
		items.registerBlock(TFBlocks.stronghold_shield);
		items.registerSubItemBlock(TFBlocks.trophy_pedestal);
		items.registerBlock(TFBlocks.aurora_block);
		items.registerSubItemBlock(TFBlocks.underbrick);
		items.register(new ItemMultiTexture(TFBlocks.thorns, TFBlocks.thorns, thornNames));
		items.registerBlock(TFBlocks.burnt_thorns);
		items.registerBlock(TFBlocks.thorn_rose);
		items.register(new ItemBlockTFLeaves(TFBlocks.twilight_leaves_3));
		items.register(new ItemMultiTexture(TFBlocks.deadrock, TFBlocks.deadrock, deadrockNames));
		items.registerBlock(TFBlocks.dark_leaves);
		items.registerBlock(TFBlocks.aurora_pillar);
		items.register(new ItemSlab(TFBlocks.aurora_slab, TFBlocks.aurora_slab, TFBlocks.double_aurora_slab));
		items.registerBlock(TFBlocks.trollsteinn);
		items.registerBlock(TFBlocks.wispy_cloud);
		items.registerBlock(TFBlocks.fluffy_cloud);
		items.register(new ItemTFGiantBlock(TFBlocks.giant_cobblestone));
		items.register(new ItemTFGiantBlock(TFBlocks.giant_log));
		items.register(new ItemTFGiantBlock(TFBlocks.giant_leaves));
		items.register(new ItemTFGiantBlock(TFBlocks.giant_obsidian));
		items.registerBlock(TFBlocks.uberous_soil);
		items.registerBlock(TFBlocks.huge_stalk);
		items.registerBlock(TFBlocks.huge_mushgloom);
		items.registerBlock(TFBlocks.trollvidr);
		items.registerBlock(TFBlocks.unripe_trollber);
		items.registerBlock(TFBlocks.trollber);
		items.registerBlock(TFBlocks.knightmetal_block);
		items.register(new ItemBlockTFHugeLilyPad(TFBlocks.huge_lilypad));
		items.register(new ItemBlockTFHugeWaterLily(TFBlocks.huge_waterlily));
		items.registerSubItemBlock(TFBlocks.slider);
		items.registerSubItemBlock(TFBlocks.castle_brick);
		items.registerBlock(TFBlocks.castle_stairs_brick);
		items.registerBlock(TFBlocks.castle_stairs_cracked);
		items.registerBlock(TFBlocks.castle_stairs_worn);
		items.registerBlock(TFBlocks.castle_stairs_mossy);
		items.registerSubItemBlock(TFBlocks.castle_pillar);
		items.registerSubItemBlock(TFBlocks.castle_stairs);
		items.registerSubItemBlock(TFBlocks.castle_rune_brick);
		items.registerSubItemBlock(TFBlocks.force_field);
		items.registerBlock(TFBlocks.cinder_furnace);
		items.registerBlock(TFBlocks.cinder_log);
		items.registerSubItemBlock(TFBlocks.castle_door);
		items.register(new ItemTFMiniatureStructure(TFBlocks.miniature_structure));
		items.register(new ItemTFCompressed(TFBlocks.block_storage));
		//items.registerBlock(TFBlocks.lapis_block);
		items.registerBlock(TFBlocks.spiral_bricks);
		items.registerBlock(TFBlocks.etched_nagastone);
		items.registerBlock(TFBlocks.nagastone_pillar);
		items.registerSubItemBlock(TFBlocks.nagastone_stairs);
		items.registerBlock(TFBlocks.etched_nagastone_mossy);
		items.registerBlock(TFBlocks.nagastone_pillar_mossy);
		items.registerSubItemBlock(TFBlocks.nagastone_stairs_mossy);
		items.registerBlock(TFBlocks.etched_nagastone_weathered);
		items.registerBlock(TFBlocks.nagastone_pillar_weathered);
		items.registerSubItemBlock(TFBlocks.nagastone_stairs_weathered);
		items.registerBlock(TFBlocks.auroralized_glass);
		items.registerBlock(TFBlocks.iron_ladder);
		items.registerBlock(TFBlocks.terrorcotta_circle);
		items.registerBlock(TFBlocks.terrorcotta_diagonal);
		items.registerBlock(TFBlocks.stone_twist);
		items.registerBlock(TFBlocks.stone_twist_thin);

		registerWoodVariants(items, BlockTFLog.VARIANT, WoodVariant.values());
		registerWoodVariants(items, BlockTFMagicLog.VARIANT, MagicWoodVariant.values());

		TFCompat.initCompatItems(items);
	}

	private static <T extends IStringSerializable & Comparable<T> & IMapColorSupplier> void registerWoodVariants(ItemRegistryHelper items, IProperty<T> key, T[] types) {
		for (T woodType : types) {
			String woodName = woodType.getName();

			if ("oak".equals(woodName)) // Not really going to rename that enum entry just yet
				woodName = "twilight_oak";

			items.registerBlock(Block.REGISTRY.getObject(TwilightForestMod.prefix(woodName + "_planks")));
			items.registerBlock(Block.REGISTRY.getObject(TwilightForestMod.prefix(woodName + "_stairs")));
			BlockSlab slab = (BlockSlab) Block.REGISTRY.getObject(TwilightForestMod.prefix(woodName + "_slab"));
			items.register(woodName + "_slab", new ItemSlab(slab, slab, (BlockSlab) Block.REGISTRY.getObject(TwilightForestMod.prefix(woodName + "_doubleslab"))));
			items.registerBlock(Block.REGISTRY.getObject(TwilightForestMod.prefix(woodName + "_button")));

			ResourceLocation doorRL = TwilightForestMod.prefix(woodName + "_door");
			Block doorBlock = Block.REGISTRY.getObject(doorRL);
			items.register(doorRL.getPath(), new ItemDoor(doorBlock)).setTranslationKey(doorBlock.getTranslationKey());

			items.registerBlock(Block.REGISTRY.getObject(TwilightForestMod.prefix(woodName + "_trapdoor")));
			items.registerBlock(Block.REGISTRY.getObject(TwilightForestMod.prefix(woodName + "_fence")));
			items.registerBlock(Block.REGISTRY.getObject(TwilightForestMod.prefix(woodName + "_gate")));
			items.registerBlock(Block.REGISTRY.getObject(TwilightForestMod.prefix(woodName + "_plate")));
		}
	}

	public static class ItemRegistryHelper {


		private final IForgeRegistry<Item> registry;

		ItemRegistryHelper(IForgeRegistry<Item> registry) {
			this.registry = registry;
		}

		<T extends Item> void register(String registryName, String translationKey, T item) {
			item.setTranslationKey(TwilightForestMod.ID + "." + translationKey);
			register(registryName, item);
		}

		public <T extends Item> Item register(String registryName, T item) {
			item.setRegistryName(TwilightForestMod.ID, registryName);

			registry.register(item);
			return item;
		}

		void registerBlock(Block block) {
			register(new ItemBlock(block));
		}

		void registerSubItemBlock(Block block) {
			registerSubItemBlock(block, true);
		}

		void registerSubItemBlock(Block block, boolean shouldAppendNumber) {
			register(new ItemBlockTFMeta(block).setAppend(shouldAppendNumber));
		}

		<T extends ItemBlock> void register(T item) {
			item.setRegistryName(item.getBlock().getRegistryName());
			item.setTranslationKey(item.getBlock().getTranslationKey());
			registry.register(item);
		}
	}
}
