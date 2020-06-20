package twilightforest.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.client.ModelRegisterCallback;
//TODO 1.14: This class appears to only be used for TiCon2, which is currently unavailable
public class BlockTFFluid extends BlockFluidClassic implements ModelRegisterCallback {

    public BlockTFFluid(Fluid fluid, Material material) {
        super(fluid, material);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void registerModel() {
        ModelLoader.setCustomStateMapper(this, new SingleStateMapper(new ModelResourceLocation(getRegistryName(), "fluid")));
    }
}
