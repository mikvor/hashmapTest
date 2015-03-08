package map.intint;

public class IntIntMap4aUnitTest extends BaseIntIntMapUnitTest {
    @Override
    protected IntIntMap makeMap(int size, float fillFactor) {
        return new IntIntMap4a(size, fillFactor);
    }
}
