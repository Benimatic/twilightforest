package twilightforest.item;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import twilightforest.block.BlockTFRoots;
import twilightforest.block.TFBlocks;
import twilightforest.block.enums.RootVariant;

public class ItemTFOreMeter extends ItemTF {

	protected ItemTFOreMeter() {
		this.setCreativeTab(TFItems.creativeTab);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack par1ItemStack, World world, EntityPlayer player, EnumHand hand) {
		
		int useX = MathHelper.floor(player.posX);
		int useZ = MathHelper.floor(player.posZ);
		
		if (!world.isRemote) {
			countOreInArea(player, world, useX, useZ, 3);
		}
		
		return super.onItemRightClick(par1ItemStack, world, player, hand);
	}
	
	private void countOreInArea(EntityPlayer player, World world, int useX, int useZ, int radius) {
		int chunkX = useX >> 4;
		int chunkZ = useZ >> 4;
		

		int countStone = 0;
		int countDirt = 0;
		int countGravel = 0;
		
		int countCoal = 0;
		int countIron = 0;
		int countGold = 0;
		int countDiamond = 0;
		int countLapis = 0;
		int countRedstone = 0;
		int countExposedDiamond = 0;

		int countRoots = 0;
		int countOreRoots = 0;

		int total = 0;


		for (int cx = chunkX - radius; cx <= chunkX + radius; cx++) {
			for (int cz = chunkZ - radius; cz <= chunkZ + radius; cz++) {

				countStone += countBlockInChunk(world, Blocks.STONE, cx, cz);
				countDirt += countBlockInChunk(world, Blocks.DIRT, cx, cz);
				countGravel += countBlockInChunk(world, Blocks.GRAVEL, cx, cz);

				countCoal += countBlockInChunk(world, Blocks.COAL_ORE, cx, cz);
				countIron += countBlockInChunk(world, Blocks.IRON_ORE, cx, cz);
				countGold += countBlockInChunk(world, Blocks.GOLD_ORE, cx, cz);
				countDiamond += countBlockInChunk(world, Blocks.DIAMOND_ORE, cx, cz);
				countLapis += countBlockInChunk(world, Blocks.LAPIS_ORE, cx, cz);
				countRedstone += countBlockInChunk(world, Blocks.REDSTONE_ORE, cx, cz);
				countExposedDiamond += countExposedBlockInChunk(world, Blocks.DIAMOND_ORE, cx, cz);

				
				countRoots += countBlockInChunk(world, TFBlocks.root.getDefaultState().withProperty(BlockTFRoots.VARIANT, RootVariant.ROOT), cx, cz);
				countOreRoots += countBlockInChunk(world, TFBlocks.root.getDefaultState().withProperty(BlockTFRoots.VARIANT, RootVariant.LIVEROOT), cx, cz);
			}
		}

		total = countStone + countDirt + countGravel + countCoal + countIron + countGold + countDiamond + countLapis + countRedstone + countRoots + countOreRoots;


		player.sendMessage(new TextComponentString("Ore Meter!"));
		player.sendMessage(new TextComponentString("Metering chunks in radius " + radius + " around chunk [" + chunkX + ", " + chunkZ + "]"));
		player.sendMessage(new TextComponentString("Coal - " + countCoal + " " + percent(countCoal, total)));
		player.sendMessage(new TextComponentString("Iron - " + countIron + " " + percent(countIron, total)));
		player.sendMessage(new TextComponentString("Gold - " + countGold + " " + percent(countGold, total)));
		player.sendMessage(new TextComponentString("Diamond - " + countDiamond + " " + percent(countDiamond, total) + ", exposed - " + countExposedDiamond));
		player.sendMessage(new TextComponentString("Lapis - " + countLapis + " " + percent(countLapis, total)));
		player.sendMessage(new TextComponentString("Redstone - " + countRedstone + " " + percent(countRedstone, total)));
		player.sendMessage(new TextComponentString("Roots - " + countRoots + " " + percent(countRoots, total)));
		player.sendMessage(new TextComponentString("Ore Roots - " + countOreRoots + " " + percent(countOreRoots, total)));
	}
	
	private float percent(int count, int total) {
		return (float)count / (float)total * 100F;
	}
	
	private int countBlockInChunk(World world, Block stone, int cx, int cz) {

		Chunk chunk = world.getChunkFromChunkCoords(cx, cz);
		
		int count = 0;
		
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				for (int y = 0; y < 256; y++) {
					if (chunk.getBlockState(new BlockPos(x, y, z)).getBlock() == stone) {
						count++;
					}
				}
			}
		}
		
		return count;
	}	
	
	public int countBlockInChunk(World world, IBlockState state, int cx, int cz) {

		Chunk chunk = world.getChunkFromChunkCoords(cx, cz);
		
		int count = 0;
		
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				for (int y = 0; y < 256; y++) {
					if (chunk.getBlockState(new BlockPos(x, y, z)) == state) {
						count++;
					}
				}
			}
		}
		
		return count;
	}

	private int countExposedBlockInChunk(World world, Block blockID, int cx, int cz) {
		
		int count = 0;

		for (int x = cx << 4; x < (cx << 4) + 16; x++) {
			for (int z = cz << 4; z < (cz << 4) + 16; z++) {
				for (int y = 0; y < 256; y++) {
					BlockPos pos = new BlockPos(x, y, z);
					if (world.getBlockState(pos).getBlock() == blockID) {
						// todo 1.9 memory opt?
						// check if exposed
						if (world.isAirBlock(pos.east()) || world.isAirBlock(pos.west())
								||world.isAirBlock(pos.up()) || world.isAirBlock(pos.down())
								||world.isAirBlock(pos.north()) || world.isAirBlock(pos.south()))
						{
							count++;
						}
					}
				}
			}
		}
		
		return count;

	}
}
