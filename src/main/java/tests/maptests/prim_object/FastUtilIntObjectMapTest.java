package tests.maptests.prim_object;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

/**
 * FastUtil Int2ObjectOpenHashMap test
 */
public class FastUtilIntObjectMapTest extends AbstractPrimObjectMapTest {
    private Int2ObjectOpenHashMap<Integer> m_map;

    @Override
    public void setup(int[] keys, float fillFactor, int oneFailOutOf) {
        super.setup(keys, fillFactor, oneFailOutOf );
        m_map = new Int2ObjectOpenHashMap<>( keys.length, fillFactor );
        for ( int key : keys ) m_map.put( key % oneFailOutOf == 0 ? key + 1 : key, Integer.valueOf(key) );
    }

    @Override
    public int randomGetTest() {
        int res = 0;
        for ( int i = 0; i < m_keys.length; ++i )
            if ( m_map.get( m_keys[ i ] ) != null ) res ^= 1;
        return res;
    }
}
