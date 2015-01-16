package tests.maptests.primitive;

import com.carrotsearch.hppc.IntIntOpenHashMap;

/**
 * HPPC IntIntOpenHashMap test
 */
public class HppcMapTest extends AbstractPrimPrimMapTest {
    private IntIntOpenHashMap m_map;

    @Override
    public void setup(final int[] keys, final float fillFactor) {
        super.setup( keys, fillFactor);
        m_map = new IntIntOpenHashMap( keys.length, fillFactor );
        for (int key : keys) m_map.put(key, key);
    }

    @Override
    public int runRandomTest() {
        int res = 0;
        for ( int i = 0; i < m_keys.length; ++i )
            res = res ^ m_map.get( m_keys[ i ] );
        return res;
    }
}
