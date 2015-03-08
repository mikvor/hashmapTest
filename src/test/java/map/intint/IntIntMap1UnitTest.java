package map.intint;

public class IntIntMap1UnitTest extends BaseIntIntMapUnitTest {
    @Override
    protected IntIntMap makeMap(int size, float fillFactor) {
        return new IntIntMap1( size, fillFactor );
    }
}
