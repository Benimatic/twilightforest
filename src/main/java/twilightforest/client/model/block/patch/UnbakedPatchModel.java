package twilightforest.client.model.block.patch;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.client.model.geometry.IGeometryBakingContext;
import net.minecraftforge.client.model.geometry.IUnbakedGeometry;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.function.Function;

public record UnbakedPatchModel(Material material, boolean shaggify) implements IUnbakedGeometry<UnbakedPatchModel> {
    public UnbakedPatchModel(ResourceLocation texture, boolean shaggify) {
        this(new Material(InventoryMenu.BLOCK_ATLAS, texture), shaggify);
    }

    @Override
    public BakedModel bake(IGeometryBakingContext owner, ModelBakery bakery, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelTransform, ItemOverrides overrides, ResourceLocation modelLocation) {
        return new PatchModel(modelLocation, spriteGetter.apply(this.material()), this.shaggify());
    }

    @Override
    public Collection<Material> getMaterials(IGeometryBakingContext owner, Function<ResourceLocation, UnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors) {
        return Collections.singleton(this.material());
    }
}
