package therealeststu.dtbop.cells.cell;

import com.ferreusveritas.dynamictrees.api.cells.ICell;
import net.minecraft.util.Direction;

public class EucalyptusTopBranchCell implements ICell {

    @Override
    public int getValue() {
        return 5;
    }

    static final int[] map = {0, 3, 5, 5, 5, 5};

    @Override
    public int getValueFromSide(Direction side) {
        return map[side.ordinal()];
    }

}