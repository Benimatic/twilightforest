package twilightforest.enums;

import net.minecraft.util.IStringSerializable;

import java.util.Locale;

public enum Diagonals implements IStringSerializable {
    TOP_RIGHT   ((x, rX) -> rX - x, (y, rY) -> y     ),
    BOTTOM_RIGHT((x, rX) -> rX - x, (y, rY) -> rY - y),
    BOTTOM_LEFT ((x, rX) -> x     , (y, rY) -> rY - y),
    TOP_LEFT    ((x, rX) -> x     , (y, rY) -> y     );

    public final Inversion operationX;
    public final Inversion operationY;

    Diagonals(Inversion operationX, Inversion operationY) {
        this.operationX = operationX;
        this.operationY = operationY;
    }

    @Override
    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }

    public interface Inversion {
        int convert(int numberIn, int range);
    }
}