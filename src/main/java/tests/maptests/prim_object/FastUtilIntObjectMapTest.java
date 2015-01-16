package tests.maptests.prim_object;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

/**
 * FastUtil Int2ObjectOpenHashMap test
 */
public class FastUtilIntObjectMapTest extends AbstractPrimObjectMapTest {
    private Int2ObjectOpenHashMap<Integer> m_map;

    @Override
    public void setup(int[] keys, float fillFactor) {
        super.setup(keys, fillFactor);
        m_map = new Int2ObjectOpenHashMap<>( keys.length, fillFactor );
        for ( int key : keys ) m_map.put( key, Integer.valueOf(key) );
    }

    @Override
    public int runRandomTest() {
        int res = 0;
        for ( int i = 0; i < m_keys.length; ++i )
            res = res ^ m_map.get( m_keys[ i ] );
        return res;
    }
}
