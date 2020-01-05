package twilightforest.block;

import net.minecraft.block.*;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.BlockState;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.IForgeRegistry;
import twilightforest.TwilightForestMod;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.client.ModelUtils;
import twilightforest.enums.CastleBrickVariant;
import twilightforest.enums.MagicWoodVariant;
import twilightforest.enums.WoodVariant;
import twilightforest.item.TFItems;
import twilightforest.util.IMapColorSupplier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mod.EventBusSubscriber(modid = TwilightForestMod.ID)
public final class RegisterBlockEvent {

	public static void onRegisterBlocks(RegistryEvent.Register<Block> event) {

//		registerFluidBlock(blocks, moltenFiery);
//		registerFluidBlock(blocks, moltenKnightmetal);
//		registerFluidBlock("fiery_essence", blocks, essenceFiery);
	}

	// our internal fluid instances
	// TODO: Not necessary as TiCon2 does not exist on 1.14 yet
//	public static final Fluid moltenFiery;
//	public static final Fluid moltenKnightmetal;
//	public static final Fluid essenceFiery;
//
//	public static final ResourceLocation moltenFieryStill = TwilightForestMod.prefix("blocks/molten_fiery_still");
//	public static final ResourceLocation moltenFieryFlow  = TwilightForestMod.prefix("blocks/molten_fiery_flow");
//
//	public static final ResourceLocation moltenKnightmetalStill = TwilightForestMod.prefix("blocks/molten_knightmetal_still");
//	public static final ResourceLocation moltenKnightmetalFlow  = TwilightForestMod.prefix("blocks/molten_knightmetal_flow");
//
//	public static final ResourceLocation essenceFieryStill = TwilightForestMod.prefix("blocks/fluid_fiery_still");
//	public static final ResourceLocation essenceFieryFlow  = TwilightForestMod.prefix("blocks/fluid_fiery_flow");
//
//	static {
//		moltenFiery       = registerFluid(new Fluid("fierymetal" , moltenFieryStill, moltenFieryFlow).setTemperature(1000).setLuminosity(15));
//		moltenKnightmetal = registerFluid(new Fluid("knightmetal", moltenKnightmetalStill, moltenKnightmetalFlow).setTemperature(1000).setLuminosity(15));
//		essenceFiery      = registerFluid(new Fluid("fiery_essence", essenceFieryStill, essenceFieryFlow).setTemperature(1000));
//	}
//
//	private static Fluid registerFluid(Fluid fluid) {
//		fluid.setUnlocalizedName(fluid.getName());
//		FluidRegistry.registerFluid(fluid);
//		FluidRegistry.addBucketForFluid(fluid);
//		return fluid;
//	}
//
//	private static void registerFluidBlock(BlockRegistryHelper blocks, Fluid fluid) {
//		registerFluidBlock("molten_" + fluid.getName(), blocks, fluid);
//	}
//
//	private static void registerFluidBlock(String registryName, BlockRegistryHelper blocks, Fluid fluid) {
//		Block block = new BlockTFFluid(fluid, Material.LAVA).setTranslationKey(TwilightForestMod.ID + "." + fluid.getName()).setLightLevel(1.0F);
//		blocks.register(registryName, block);
//	}
}
