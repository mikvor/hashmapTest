package tests.maptests.prim_object;

import com.carrotsearch.hppc.IntObjectOpenHashMap;

/**
 * HPPC IntObjectOpenHashMap test
 */
public class HppcIntObjectMapTest extends AbstractPrimObjectMapTest {
    private IntObjectOpenHashMap<Integer> m_map;

    @Override
    public void setup(int[] keys, float fillFactor, int oneFailOutOf) {
        super.setup(keys, fillFactor, oneFailOutOf);
        m_map = new IntObjectOpenHashMap<>( keys.length );
        for ( int key : keys ) m_map.put( key % oneFailOutOf == 0 ? key + 1 : key, key );
    }

    @Override
    public int randomGetTest() {
        int res = 0;
        for ( int i = 0; i < m_keys.length; ++i )
            if ( m_map.get( m_keys[ i ] ) != null ) res ^= 1;
        return res;
    }
}

