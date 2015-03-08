package tests.maptests.article_examples;

import map.intint.IntIntMap;
import map.intint.IntIntMap4;

public class IntIntMap4Test extends BaseIntIntMapTest {
    @Override
    public IntIntMap makeMap(int size, float fillFactor) {
        return new IntIntMap4(size, fillFactor);
    }
}
