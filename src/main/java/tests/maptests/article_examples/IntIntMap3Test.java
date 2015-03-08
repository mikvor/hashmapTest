package tests.maptests.article_examples;

import map.intint.IntIntMap;
import map.intint.IntIntMap3;

public class IntIntMap3Test extends BaseIntIntMapTest {
    @Override
    public IntIntMap makeMap(int size, float fillFactor) {
        return new IntIntMap3(size, fillFactor);
    }
}
