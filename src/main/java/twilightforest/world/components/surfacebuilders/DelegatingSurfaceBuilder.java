package twilightforest.world.components.surfacebuilders;

import com.mojang.datafixers.Products.P1;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderConfiguration;

import java.util.function.Supplier;

public abstract class DelegatingSurfaceBuilder<ConfigWrapper extends DelegatingSurfaceBuilder.DelegatingConfig> extends SurfaceBuilder<ConfigWrapper> {
    public DelegatingSurfaceBuilder(Codec<ConfigWrapper> pCodec) {
        super(pCodec);
    }

    public static class DelegatingConfig implements SurfaceBuilderConfiguration {
        protected final Supplier<ConfiguredSurfaceBuilder<?>> surfaceBuilderSupplier;
        protected final ConfiguredSurfaceBuilder<?> surfaceBuilder;

        public DelegatingConfig(Supplier<ConfiguredSurfaceBuilder<?>> surfaceBuilderSupplier) {
            this.surfaceBuilderSupplier = surfaceBuilderSupplier;
            this.surfaceBuilder = this.surfaceBuilderSupplier.get();
        }

        @Override
        public final BlockState getTopMaterial() {
            return this.surfaceBuilder.config().getTopMaterial();
        }

        @Override
        public final BlockState getUnderMaterial() {
            return this.surfaceBuilder.config().getUnderMaterial();
        }

        @Override
        public final BlockState getUnderwaterMaterial() {
            return this.surfaceBuilder.config().getUnderwaterMaterial();
        }

        public static <SchemaGoal extends DelegatingConfig> P1<Mu<SchemaGoal>, Supplier<ConfiguredSurfaceBuilder<?>>> startCodec(Instance<SchemaGoal> instance) {
            return instance.group(ConfiguredSurfaceBuilder.CODEC
                    .fieldOf("wrapped_surface_builder")
                    .flatXmap(ExtraCodecs.nonNullSupplierCheck(), ExtraCodecs.nonNullSupplierCheck())
                    .forGetter(schemaGoal -> schemaGoal.surfaceBuilderSupplier)
            );
        }
    }
}
