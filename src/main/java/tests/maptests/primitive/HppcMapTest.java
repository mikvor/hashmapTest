package tests.maptests.primitive;

import com.carrotsearch.hppc.IntIntOpenHashMap;

/**
 * HPPC IntIntOpenHashMap test
 */
public class HppcMapTest extends AbstractPrimPrimMapTest {
    private IntIntOpenHashMap m_map;

    @Override
    public void setup(final int[] keys, final float fillFactor, int oneFailOutOf ) {
        super.setup( keys, fillFactor, oneFailOutOf );
        m_map = new IntIntOpenHashMap( keys.length, fillFactor );
        for (int key : keys) m_map.put( key + (key % oneFailOutOf == 0 ? 1 : 0), key );
    }

    @Override
    public int randomGetTest() {
        int res = 0;
        for ( int i = 0; i < m_keys.length; ++i )
            res = res ^ m_map.get( m_keys[ i ] );
        return res;
    }
}
