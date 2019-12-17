package twilightforest.block;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.TwilightForestMod;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.item.TFItems;

import java.util.Random;

public class BlockTFExperiment115 extends Block {

    public static final IntegerProperty NOMS = IntegerProperty.create("omnomnom", 0, 7);
    public static final BooleanProperty REGENERATE = BooleanProperty.create("regenerate");

    private static final AxisAlignedBB[] AABB = new AxisAlignedBB[] {
            new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D),
            new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.5D, 0.5D, 0.9375D),
            new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.5D, 0.5D, 0.5D)
    };

    public BlockTFExperiment115() {
        super(Properties.create(Material.CAKE, MaterialColor.IRON).sound(SoundType.CLOTH).tickRandomly());
        this.setDefaultState(this.stateContainer.getBaseState().with(NOMS, 7).with(REGENERATE, false));
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, World world, BlockPos pos, PlayerEntity player) {
        return new ItemStack(TFItems.experiment_115);
    }

    @Override
    public AxisAlignedBB getBoundingBox(BlockState state, IBlockAccess source, BlockPos pos) {
        switch (state.get(NOMS)) {
            default:
                return AABB[0];
            case 4:
            case 5:
                return AABB[1];
            case 6:
            case 7:
                return AABB[2];
        }
    }

    @Override
    public boolean isFullCube(BlockState state)
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube(BlockState state)
    {
        return false;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, BlockState state, BlockPos pos, Direction face) {
        return BlockFaceShape.UNDEFINED;
    }

	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		int bitesTaken = state.get(NOMS);
		ItemStack stack = player.getHeldItem(hand);

		if (!player.isSneaking()) {
			if (bitesTaken > 0 && stack.getItem() == TFItems.experiment_115.get()) {
				worldIn.setBlockState(pos, state.with(NOMS, bitesTaken - 1));
				if (!player.isCreative()) stack.shrink(1);
				if (player instanceof ServerPlayerEntity) CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) player, pos, stack);
				return true;
			} else if (((!state.get(REGENERATE)) && stack.getItem() == Items.REDSTONE) && (player.isCreative() || bitesTaken == 0)) {
				worldIn.setBlockState(pos, state.with(REGENERATE,true));
				if (!player.isCreative()) stack.shrink(1);
				if (player instanceof ServerPlayerEntity) CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) player, pos, stack);
				return true;
			}
		}

		return this.eatCake(worldIn, pos, state, player) || stack.isEmpty();
	}

	private boolean eatCake(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!player.canEat(false)) return false;
        else {
            player.addStat(StatList.CAKE_SLICES_EATEN);
            player.getFoodStats().addStats(4, 0.3F);
            int i = state.get(NOMS);

            if (i < 7) world.setBlockState(pos, state.with(NOMS, i + 1), 3);
            else       world.setBlockToAir(pos);

            if (player instanceof ServerPlayerEntity)
                CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity) player, new ItemStack(TFItems.experiment_115, 8 - i));

            return true;
        }
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, BlockState state, Random rand) {
        if (state.get(REGENERATE) && state.get(NOMS) != 0) {
            worldIn.setBlockState(pos, state.with(NOMS, state.get(NOMS) - 1));
        }
    }

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!this.canBlockStay(worldIn, pos)) {
            worldIn.setBlockToAir(pos);
        }
    }

    private boolean canBlockStay(World world, BlockPos pos) {
        return world.getBlockState(pos.down()).getMaterial().isSolid();
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return super.canPlaceBlockAt(world, pos) && canBlockStay(world, pos);
    }

    @Override
    public int quantityDropped(Random random)
    {
        return 0;
    }

    @Override
    public BlockState getStateFromMeta(int meta) {
        return this.getDefaultState().with(NOMS, meta & 7).with(REGENERATE, (meta & 8) == 8);
    }

    @Override
    public int getMetaFromState(BlockState state) {
        return state.get(NOMS) | (state.get(REGENERATE) ? 8 : 0);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, NOMS, REGENERATE);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public int getComparatorInputOverride(BlockState state, World world, BlockPos pos) {
        return 15-(state.get(NOMS)*2);
    }

    @Override
    public boolean hasComparatorInputOverride(BlockState state)
    {
        return true;
    }

    @Override
    public boolean canProvidePower(BlockState state) {
        return state.get(REGENERATE);
    }

	@Override
	public int getWeakPower(BlockState state, IBlockReader blockAccess, BlockPos pos, Direction side) {
		return state.get(REGENERATE) ? 15-(state.get(NOMS)*2) : 0;
	}

	@Override
    @OnlyIn(Dist.CLIENT)
    public void registerModel() {
        ModelLoader.setCustomModelResourceLocation(TFItems.experiment_115, 0, new ModelResourceLocation(TwilightForestMod.ID + ":experiment_115", "inventory"));
        ModelLoader.setCustomModelResourceLocation(TFItems.experiment_115, 1, new ModelResourceLocation(TwilightForestMod.ID + ":experiment_115", "inventory_full"));
        ModelLoader.setCustomModelResourceLocation(TFItems.experiment_115, 2, new ModelResourceLocation(TwilightForestMod.ID + ":experiment_115", "inventory_think"));

        // Sorry, just gonna tack this here for now. The Shielding Scepter doesn't allow me to tack on another model like I would here. I'd like to not make a placeholder item either >.>
        // TODO 1.14: Well...we may need a placeholder item
        ModelLoader.setCustomModelResourceLocation(TFItems.experiment_115, 3, new ModelResourceLocation(TwilightForestMod.ID + ":shield", "inventory"));
    }
}
