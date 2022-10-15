package twilightforest.loot.modifiers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import twilightforest.TwilightForestMod;
import twilightforest.block.GiantBlock;
import twilightforest.capabilities.CapabilityList;
import twilightforest.capabilities.giant_pick.GiantPickMineCapability;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFItems;

//FIXME I simply migrated this out of TFEventListener, it somehow needs to be redone in a more sane way.
@Mod.EventBusSubscriber(modid = TwilightForestMod.ID)
public class GiantToolGroupingModifier extends LootModifier {
	public static final Codec<GiantToolGroupingModifier> CODEC = RecordCodecBuilder.create(inst -> LootModifier.codecStart(inst).apply(inst, GiantToolGroupingModifier::new));

	private static boolean isBreakingWithGiantPick = false;
	private static boolean shouldMakeGiantCobble = false;
	private static int amountOfCobbleToReplace = 0;

	public GiantToolGroupingModifier(LootItemCondition[] conditions) {
		super(conditions);
	}

	@Override
	protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
		ObjectArrayList<ItemStack> newLoot = new ObjectArrayList<>();
		boolean flag = false;
		if (shouldMakeGiantCobble && generatedLoot.size() > 0) {
			// turn the next 64 cobblestone drops into one giant cobble
			if (generatedLoot.get(0).getItem() == Item.byBlock(Blocks.COBBLESTONE)) {
				generatedLoot.remove(0);
				if (amountOfCobbleToReplace == 64) {
					newLoot.add(new ItemStack(TFBlocks.GIANT_COBBLESTONE.get()));
					flag = true;
				}
				amountOfCobbleToReplace--;
				if (amountOfCobbleToReplace <= 0) {
					shouldMakeGiantCobble = false;
				}
			}
		}
		return flag ? newLoot : generatedLoot;
	}

	@Override
	public Codec<? extends IGlobalLootModifier> codec() {
		return GiantToolGroupingModifier.CODEC;
	}

	@SubscribeEvent
	public static void breakBlock(BlockEvent.BreakEvent event) {
		Player player = event.getPlayer();
		BlockPos pos = event.getPos();
		BlockState state = event.getState();

		if (!isBreakingWithGiantPick && canHarvestWithGiantPick(player, state)) {

			isBreakingWithGiantPick = true;

			// pre-check for cobble!
			Item cobbleItem = Blocks.COBBLESTONE.asItem();
			boolean allCobble = state.getBlock().asItem() == cobbleItem;

			if (allCobble) {
				for (BlockPos dPos : GiantBlock.getVolume(pos)) {
					if (dPos.equals(pos))
						continue;
					BlockState stateThere = event.getLevel().getBlockState(dPos);
					if (stateThere.getBlock().asItem() != cobbleItem) {
						allCobble = false;
						break;
					}
				}
			}

			if (allCobble && !player.getAbilities().instabuild) {
				shouldMakeGiantCobble = true;
				amountOfCobbleToReplace = 64;
			} else {
				shouldMakeGiantCobble = false;
				amountOfCobbleToReplace = 0;
			}

			// break all nearby blocks
			if (player instanceof ServerPlayer playerMP) {
				for (BlockPos dPos : GiantBlock.getVolume(pos)) {
					if (!dPos.equals(pos) && state.getBlock() == event.getLevel().getBlockState(dPos).getBlock()) {
						// try to break that block too!
						playerMP.gameMode.destroyBlock(dPos);
					}
				}
			}

			isBreakingWithGiantPick = false;
		}
	}

	private static boolean canHarvestWithGiantPick(Player player, BlockState state) {
		return player.getMainHandItem().is(TFItems.GIANT_PICKAXE.get()) && ForgeHooks.isCorrectToolForDrops(state, player)
				&& player.getCapability(CapabilityList.GIANT_PICK_MINE).map(GiantPickMineCapability::getMining).orElse(1L) == player.level.getGameTime();
	}
}
