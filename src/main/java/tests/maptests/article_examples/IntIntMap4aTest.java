package tests.maptests.article_examples;

import map.intint.IntIntMap;
import map.intint.IntIntMap4a;

public class IntIntMap4aTest extends BaseIntIntMapTest {
    @Override
    public IntIntMap makeMap(int size, float fillFactor) {
        return new IntIntMap4a( size, fillFactor);
    }
}
