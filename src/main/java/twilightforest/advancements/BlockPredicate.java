package twilightforest.advancements;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.NbtPredicate;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.util.GsonHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Optional;

//TODO remove this when we get rid of ItemUseTrigger, not needed
public class BlockPredicate {
    public static final BlockPredicate ANY = new BlockPredicate(ImmutableSet.of(), Blocks.AIR, NbtPredicate.ANY) {
        @Override public boolean test(Level world, BlockPos pos) { return true; }
    };
    private ImmutableSet<PropertyPredicate<?>> propertyPredicates;
    private final Block block;
    private final NbtPredicate nbtPredicate;

    private BlockPredicate(ImmutableSet<PropertyPredicate<?>> propertyPredicates, Block block, NbtPredicate nbt) {
        this.propertyPredicates = propertyPredicates;
        this.block = block;
        this.nbtPredicate = nbt;
    }

    public static BlockPredicate deserialize(@Nullable JsonElement element) {
        if (element == null || element.isJsonNull())
            return ANY;

        JsonObject json = element.getAsJsonObject();

        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(GsonHelper.getAsString(json, "block")));
        StateDefinition<?, ?> container = block.getStateDefinition();
        HashSet<PropertyPredicate<?>> properties = new HashSet<>();

        if (json.has("properties")) {
            for (JsonElement propertyRawGroup : GsonHelper.getAsJsonArray(json, "properties")) {
                JsonObject propertyGroup = propertyRawGroup.getAsJsonObject();

                Property<?> propertyKey = container.getProperty(GsonHelper.getAsString(propertyGroup, "property"));

                if (propertyKey != null)
                    createPropertyPredicateAndAddToSet(
                            properties,
                            propertyKey,
                            GsonHelper.getAsString(propertyGroup, "value"),
                            GsonHelper.getAsString(propertyGroup, "comparator")
                    );
            }
        }

        NbtPredicate nbtPredicate = json.has("nbt") ? NbtPredicate.fromJson(json.get("nbt")) : NbtPredicate.ANY;

        return new BlockPredicate(new ImmutableSet.Builder<PropertyPredicate<?>>().addAll(properties).build(), block, nbtPredicate);
    }

    private static <T extends Comparable<T>> void createPropertyPredicateAndAddToSet(
            HashSet<PropertyPredicate<?>> predicateSet,
            Property<T> key,
            String value,
            String comparisonType) {

        Optional<T> schrodingersVar = key.getValue(value);
        PropertyPredicate.ComparisonType predicateComparator = PropertyPredicate.ComparisonType.get(comparisonType);

        if (predicateComparator == null || !schrodingersVar.isPresent()) return; // Skip

        predicateSet.add(new PropertyPredicate<>(key, schrodingersVar.get(), predicateComparator));
    }

    public boolean test(Level world, BlockPos pos) {
        if (!test(world.getBlockState(pos))) // If BlockState check fails, we're done
            return false;

        if (nbtPredicate == NbtPredicate.ANY) // Do we accept any NBT including lack thereof?
            return true;

        BlockEntity te = world.getBlockEntity(pos);

        return te != null && nbtPredicate.matches(te.serializeNBT());
    }

    private boolean test(BlockState state) {
        if (block != state.getBlock()) return false; // Not same block

        for (PropertyPredicate<?> propertyPredicate : propertyPredicates)
            if (!propertyPredicate.test(state))
                return false;

        return true;
    }

    private static class PropertyPredicate<T extends Comparable<T>> {
        private final Property<T> property;
        private final T value;
        private ComparisonType comparisonType;

        private PropertyPredicate(Property<T> key, T value, ComparisonType comparisonType) {
            this.property = key;
            this.value = value;
            this.comparisonType = comparisonType;
        }

        private boolean test(BlockState state) {
            return state.getProperties().contains(property) && comparisonType.test(value, state.getValue(property));
        }

        private enum ComparisonType {
            EQUAL   { @Override <T extends Comparable<T>> boolean test(T k, T v) { return k.compareTo(v) == 0; } },
            NOT     { @Override <T extends Comparable<T>> boolean test(T k, T v) { return k.compareTo(v) != 0; } },
            LESSER  { @Override <T extends Comparable<T>> boolean test(T k, T v) { return k.compareTo(v) <  0; } },
            GREATER { @Override <T extends Comparable<T>> boolean test(T k, T v) { return k.compareTo(v) >  0; } };

            abstract <T extends Comparable<T>> boolean test(T key, T value);

            @Nullable
            private static ComparisonType get(String type) {
                switch (type) {
                    case "equal":
                    case "same": return EQUAL;
                    case "not":
                    case "different":
                    case "equaln't": return NOT;
                    case "lesser":
                    case "lesser_than": return LESSER;
                    case "greater":
                    case "greater_than": return GREATER;
                }

                return null;
            }
        }
    }
}
