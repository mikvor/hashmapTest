package tests.maptests.prim_object;

import com.carrotsearch.hppc.IntObjectOpenHashMap;

/**
 * HPPC IntObjectOpenHashMap test
 */
public class HppcIntObjectMapTest extends AbstractPrimObjectMapTest {
    private IntObjectOpenHashMap<Integer> m_map;

    @Override
    public void setup(int[] keys, float fillFactor) {
        super.setup(keys, fillFactor);
        m_map = new IntObjectOpenHashMap<>( keys.length );
        for ( int key : keys ) m_map.put( key, key );
    }

    @Override
    public int runRandomTest() {
        int res = 0;
        for ( int i = 0; i < m_keys.length; ++i )
            res = res ^ m_map.get( m_keys[ i ] );
        return res;
    }
}

