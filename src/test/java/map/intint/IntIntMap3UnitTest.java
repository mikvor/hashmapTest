package map.intint;

public class IntIntMap3UnitTest extends BaseIntIntMapUnitTest {
    @Override
    protected IntIntMap makeMap(int size, float fillFactor) {
        return new IntIntMap3( size, fillFactor );
    }
}
