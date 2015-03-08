package map.intint;

public class IntIntMap2UnitTest extends BaseIntIntMapUnitTest {
    @Override
    protected IntIntMap makeMap(int size, float fillFactor) {
        return new IntIntMap2(size, fillFactor);
    }
}
