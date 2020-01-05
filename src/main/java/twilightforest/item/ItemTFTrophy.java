package twilightforest.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Rarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.SkullTileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;
import twilightforest.block.BlockTFTrophy;
import twilightforest.block.TFBlocks;
import twilightforest.enums.BossVariant;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Locale;

//TODO 1.14: Rework this class out of metadata
public class ItemTFTrophy extends ItemTF {

	public ItemTFTrophy(Properties props) {
		super(props);
	}

	@Override
	public ITextComponent getDisplayName(ItemStack stack) {
		return new TranslationTextComponent(this.getTranslationKey(stack), I18n.translateToLocal("entity.twilightforest." + BossVariant.values()[stack.getMetadata() % BossVariant.values().length].getName().toLowerCase(Locale.ROOT) + ".name"));
	}

	@Nonnull
	@Override
	public Rarity getRarity(ItemStack stack) {
		return TwilightForestMod.getRarity();
	}

	// [VanillaCopy] ItemSkull, with own block and no player heads
	@Nonnull
	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		Direction facing = context.getFace();
		World worldIn = context.getWorld();
		BlockPos pos = context.getPos();
		PlayerEntity playerIn = context.getPlayer();

		if (facing == Direction.DOWN) {
			return ActionResultType.FAIL;
		} else {
			if (worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos)) {
				facing = Direction.UP;
				pos = pos.down();
			}
			BlockState iblockstate = worldIn.getBlockState(pos);
			Block block = iblockstate.getBlock();
			boolean flag = block.isReplaceable(worldIn, pos);

			if (!flag) {
				if (!worldIn.getBlockState(pos).getMaterial().isSolid() && !worldIn.isSideSolid(pos, facing, true)) {
					return ActionResultType.FAIL;
				}

				pos = pos.offset(facing);
			}

			ItemStack itemstack = playerIn.getHeldItem(context.getHand());

			if (playerIn.canPlayerEdit(pos, facing, itemstack) && TFBlocks.trophy.canPlaceBlockAt(worldIn, pos)) {
				if (worldIn.isRemote) {
					return ActionResultType.SUCCESS;
				} else {
					worldIn.setBlockState(pos, TFBlocks.trophy.getDefaultState().with(BlockTFTrophy.FACING, facing), 11);
					int i = 0;

					if (facing == Direction.UP) {
						i = MathHelper.floor((double) (playerIn.rotationYaw * 16.0F / 360.0F) + 0.5D) & 15;
					}

					TileEntity tileentity = worldIn.getTileEntity(pos);

					if (tileentity instanceof SkullTileEntity) {
						SkullTileEntity tileentityskull = (SkullTileEntity) tileentity;

						tileentityskull.setType(itemstack.getMetadata());

						tileentityskull.setSkullRotation(i);
					}

					if (playerIn instanceof ServerPlayerEntity) {
						CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) playerIn, pos, itemstack);
					}

					itemstack.shrink(1);
					return ActionResultType.SUCCESS;
				}
			} else {
				return ActionResultType.FAIL;
			}
		}
	}

	@Nonnull
	@Override
	public String getTranslationKey(ItemStack stack) {
		//int meta = MathHelper.clamp(stack.getItemDamage(), 0, BossVariant.values().length);
		return "item.twilightforest.tf_trophy.name"; //super.getUnlocalizedName() + "." + BossVariant.values()[meta].getName();
	}

	@Override
	public boolean isValidArmor(ItemStack stack, EquipmentSlotType armorType, Entity entity) {
		return armorType == EquipmentSlotType.HEAD;
	}

	@Override
	@Nullable
	public EquipmentSlotType getEquipmentSlot(ItemStack stack) {
		return EquipmentSlotType.HEAD;
	}

	//TODO 1.14: Somehow get this working...
//	@OnlyIn(Dist.CLIENT)
//	@Override
//	public void registerModel() {
//
//		ModelResourceLocation itemModelLocation = new ModelResourceLocation(TwilightForestMod.ID + ":trophy_tesr", "inventory");
//
//		TileEntityTFTrophyRenderer tesr = new TileEntityTFTrophyRenderer(itemModelLocation);
//		//TileEntityTFTrophyRenderer tesrMinor = new TileEntityTFTrophyRenderer() { @Override protected String getModelRSL() { return TwilightForestMod.ID + ":trophy_minor"; } };
//
//		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTFTrophyRenderer.DummyTile.class, tesr);
//
//		for (BossVariant variant : BossVariant.values()) {
//			if (variant != BossVariant.ALPHA_YETI) {
//				ModelLoader.setCustomModelResourceLocation(this, variant.ordinal(), itemModelLocation);
//				ForgeHooksClient.registerTESRItemStack(this, variant.ordinal(), TileEntityTFTrophyRenderer.DummyTile.class);
//			}
//		}
//
//		ModelBakery.registerItemVariants(this,
//				new ModelResourceLocation(new ResourceLocation(TwilightForestMod.ID, "trophy"), "inventory"),
//				new ModelResourceLocation(new ResourceLocation(TwilightForestMod.ID, "trophy_minor"), "inventory"),
//				new ModelResourceLocation(new ResourceLocation(TwilightForestMod.ID, "trophy_quest"), "inventory")
//		);
//	}
}
