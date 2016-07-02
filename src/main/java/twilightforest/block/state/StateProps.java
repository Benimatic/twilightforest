package twilightforest.block.state;

import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import twilightforest.block.state.enums.CastleBrickVariant;
import twilightforest.block.state.enums.SpawnerVariant;

public final class StateProps {

    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    // Aurora Brick
    public static final PropertyInteger AURORA_VARIANT = PropertyInteger.create("variant", 0, 15);

    // Boss Spawner
    public static final PropertyEnum<SpawnerVariant> SPAWNER_VARIANT = PropertyEnum.create("variant", SpawnerVariant.class);

    // Castle Brick
    public static final PropertyEnum<CastleBrickVariant> CASTLEBRICK_VARIANT = PropertyEnum.create("variant", CastleBrickVariant.class);

    private StateProps() {}
}
