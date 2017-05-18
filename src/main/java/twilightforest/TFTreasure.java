package twilightforest;

import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class TFTreasure {
	private final ResourceLocation lootTable;

	public static final TFTreasure hill1 = new TFTreasure("hill_1");
	public static final TFTreasure hill2 = new TFTreasure("hill_2");
	public static final TFTreasure hill3 = new TFTreasure("hill_3");
	public static final TFTreasure hedgemaze = new TFTreasure("hedge_maze");
	public static final TFTreasure labyrinth_room = new TFTreasure("labyrinth_room");
	public static final TFTreasure labyrinth_deadend = new TFTreasure("labyrinth_dead_end");
	public static final TFTreasure tower_room = new TFTreasure("tower_room");
	public static final TFTreasure tower_library = new TFTreasure("tower_library");
	public static final TFTreasure basement = new TFTreasure("basement");
	public static final TFTreasure labyrinth_vault = new TFTreasure("labyrinth_vault");
	public static final TFTreasure darktower_cache = new TFTreasure("darktower_cache");
	public static final TFTreasure darktower_key = new TFTreasure("darktower_key");
	public static final TFTreasure darktower_boss = new TFTreasure("darktower_boss");
	public static final TFTreasure tree_cache = new TFTreasure("tree_cache");
	public static final TFTreasure stronghold_cache = new TFTreasure("stronghold_cache");
	public static final TFTreasure stronghold_room = new TFTreasure("stronghold_room");
	public static final TFTreasure stronghold_boss = new TFTreasure("stronghold_boss");
	public static final TFTreasure aurora_cache = new TFTreasure("aurora_cache");
	public static final TFTreasure aurora_room = new TFTreasure("aurora_room");
	public static final TFTreasure aurora_boss = new TFTreasure("aurora_boss");
	public static final TFTreasure troll_garden = new TFTreasure("troll_garden");
	public static final TFTreasure troll_vault = new TFTreasure("troll_vault");

	private TFTreasure(String path) {
		lootTable = new ResourceLocation(TwilightForestMod.ID, String.format("structures/%s/%s", path, path));

		// only register the primary table, the subtables will be loaded on-demand the first time the primary table fires
		LootTableList.register(lootTable);
	}

	public void generateChest(World world, BlockPos pos) {
		world.setBlockState(pos, Blocks.CHEST.getDefaultState(), 2);
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileEntityChest) {
			((TileEntityChest) te).setLootTable(lootTable, world.getSeed() * pos.getX() + pos.getY() ^ pos.getZ());
		}
	}
}
