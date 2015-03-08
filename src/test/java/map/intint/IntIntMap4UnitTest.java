package map.intint;

public class IntIntMap4UnitTest extends BaseIntIntMapUnitTest {
    @Override
    protected IntIntMap makeMap(int size, float fillFactor) {
        return new IntIntMap4( size, fillFactor );
    }
}
