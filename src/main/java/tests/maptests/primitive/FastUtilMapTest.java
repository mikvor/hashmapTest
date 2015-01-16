package tests.maptests.primitive;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;

/**
 * FastUtil Int2IntOpenHashMap test
 */
public class FastUtilMapTest extends AbstractPrimPrimMapTest {
    private Int2IntOpenHashMap m_map;

    @Override
    public void setup(final int[] keys, final float fillFactor) {
        super.setup(keys, fillFactor);
        m_map = new Int2IntOpenHashMap( keys.length, fillFactor );
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
