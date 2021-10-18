package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import twilightforest.block.entity.SkullCandleBlockEntity;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.function.ToIntFunction;

//The nastiest mash-up of AbstractSkullBlock and AbstractCandleBlock you will ever see. Oh yeah, some things in here are mine too. I dont copy everything.
public abstract class AbstractSkullCandleBlock extends AbstractLightableBlock {

	private final SkullBlock.Type type;

	public static final IntegerProperty CANDLES = BlockStateProperties.CANDLES;
	public static final ToIntFunction<BlockState> LIGHT_EMISSION = (state) -> state.getValue(LIT) ? 3 * state.getValue(CANDLES) : 0;
	public static final EnumProperty<CandleColors> COLOR = EnumProperty.create("color", CandleColors.class);

	public AbstractSkullCandleBlock(SkullBlock.Type type, Properties properties) {
		super(properties);
		this.type = type;
		registerDefaultState(getStateDefinition().any().setValue(LIT, false).setValue(CANDLES, 1).setValue(COLOR, CandleColors.PLAIN));
	}

	public SkullBlock.Type getType() {
		return type;
	}

	protected abstract Iterable<Vec3> getParticleOffsets(BlockState var1);

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new SkullCandleBlockEntity(blockPos, blockState);
	}

	//input one of the enum names to convert it into a candle block
	public static Block candleColorToCandle(String candleName) {
		if(!candleName.equals(CandleColors.PLAIN.getSerializedName())) {
			return Objects.requireNonNull(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(candleName + "_candle")));
		}
		return Blocks.CANDLE;
	}

	//inverse of above
	public static CandleColors candleToCandleColor(Item candle) {
		if(!(candle == Blocks.CANDLE.asItem())) {
			return CandleColors.valueOf(candle.getRegistryName().getPath().replace("_candle", "").replace("\"", "").toUpperCase(Locale.ROOT));
		}
		return CandleColors.PLAIN;
	}

	public RenderShape getRenderShape(BlockState p_49232_) {
		return RenderShape.INVISIBLE;
	}

	@Override
	public void playerDestroy(Level world, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity entity, ItemStack stack) {
		if (!world.isClientSide && !player.isCreative() && world.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
			//if we have silk touch, assign the candle values to the item and drop it
			if(EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0) {
				ItemStack newStack = new ItemStack(this);
				ItemEntity itementity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), newStack);
				CompoundTag tag = new CompoundTag();
				tag.putString("color", state.getValue(COLOR).getSerializedName());
				tag.putInt("candles", state.getValue(CANDLES));
				newStack.addTagElement("BlockStateTag", tag);
				itementity.setDefaultPickUpDelay();
				world.addFreshEntity(itementity);
				//otherwise lets drop the skull and candles
			} else {
				//skull is handled via loot table
				ItemStack newStack = new ItemStack(candleColorToCandle(state.getValue(COLOR).getSerializedName()));
				for(int i = 0; i < state.getValue(CANDLES); i++) {
					ItemEntity itementity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), newStack);
					world.addFreshEntity(itementity);
				}
			}
		}
		super.playerDestroy(world, player, pos, state, entity, stack);
	}

	@Override
	public ItemStack getPickBlock(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
		ItemStack newStack = new ItemStack(this);
		CompoundTag tag = new CompoundTag();
		tag.putString("color", state.getValue(COLOR).getSerializedName());
		tag.putInt("candles", state.getValue(CANDLES));
		newStack.addTagElement("BlockStateTag", tag);
		return newStack;
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		if (player.getItemInHand(hand).is(ItemTags.CANDLES)
				&& player.getItemInHand(hand).is(candleColorToCandle(state.getValue(COLOR).getSerializedName()).asItem())
				&& state.getValue(CANDLES) < 4 && !player.isShiftKeyDown()) {

			level.setBlockAndUpdate(pos, state.setValue(CANDLES, state.getValue(CANDLES) + 1));
			level.playSound(null, pos, SoundEvents.CANDLE_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
			if (!player.getAbilities().instabuild) player.getItemInHand(hand).shrink(1);
			return InteractionResult.sidedSuccess(level.isClientSide);

		}
		return super.use(state, level, pos, player, hand, result);
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, Random rand) {
		if (state.getValue(LIT)) {
			this.getParticleOffsets(state).forEach((offset) ->
					addParticlesAndSound(level, offset.add(pos.getX(), pos.getY(), pos.getZ()), rand));
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(CANDLES, LIT, COLOR);
	}

	public enum CandleColors implements StringRepresentable {
		WHITE, LIGHT_GRAY, GRAY, BLACK,
		RED, ORANGE, YELLOW, GREEN,
		LIME, BLUE, CYAN, LIGHT_BLUE,
		PURPLE, MAGENTA, PINK, BROWN,
		PLAIN;

		@Override
		public String getSerializedName() {
			return name().toLowerCase(Locale.ROOT);
		}
	}
}
