package twilightforest.block.enums;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;
import java.util.Locale;

public enum NagastoneVariant implements IStringSerializable {
    HEAD_NORTH,
    HEAD_EAST,
    HEAD_SOUTH,
    HEAD_WEST,
    NORTH_DOWN,
    EAST_DOWN,
    SOUTH_DOWN,
    WEST_DOWN,
    NORTH_UP,
    EAST_UP,
    SOUTH_UP,
    WEST_UP,
    AXIS_X,
    AXIS_Y,
    AXIS_Z,
    SOLID;

    NagastoneVariant() {
    }

    @Override
    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }

    public static boolean isHead(@Nonnull NagastoneVariant variant) {
        switch (variant) {
            case HEAD_NORTH:
            case HEAD_EAST:
            case HEAD_SOUTH:
            case HEAD_WEST:
                return true;
            default:
                return false;
        }
    }

    @Nonnull
    public static NagastoneVariant getVariantFromAxis(@Nonnull EnumFacing.Axis axis) {
        switch (axis){
            case X:
                return AXIS_X;
            case Y:
                return AXIS_Y;
            case Z:
                return AXIS_Z;
        }
        // If you passed in null to this method... prepare yourselves
        return null;
    }

    @Nonnull
    public static NagastoneVariant getVariantFromDoubleFacing(@Nonnull EnumFacing facing1, @Nonnull EnumFacing facing2) {
        if (facing1.getAxis() == facing2.getAxis()) // Pairs of 6 dirs and axes
            return getVariantFromAxis(facing1.getAxis()); // Both faces are on same axis
        else if (facing1.getAxis() != EnumFacing.Axis.Y && facing2.getAxis() != EnumFacing.Axis.Y)
            return SOLID; // Elbow connection doesn't have Y

        EnumFacing facingYAxis = facing1.getAxis() == EnumFacing.Axis.Y ? facing1 : facing2;
        EnumFacing otherFace = facing1.getAxis() != EnumFacing.Axis.Y ? facing1 : facing2;

        if (facingYAxis == EnumFacing.UP) {
            switch (otherFace) {
                case NORTH:
                    return NORTH_UP;
                case SOUTH:
                    return SOUTH_UP;
                case WEST:
                    return WEST_UP;
                case EAST:
                    return EAST_UP;
            }
        } else {
            switch (otherFace) {
                case NORTH:
                    return NORTH_DOWN;
                case SOUTH:
                    return SOUTH_DOWN;
                case WEST:
                    return WEST_DOWN;
                case EAST:
                    return EAST_DOWN;
            }
        }

        // If you passed in null to this method... prepare yourselves. Should be unreachable
        //noinspection ConstantConditions
        return null;
    }
}
