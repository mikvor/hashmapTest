package tests.maptests.object;

import com.carrotsearch.hppc.ObjectObjectMap;
import com.carrotsearch.hppc.ObjectObjectOpenHashMap;

/**
 * HPPC ObjectObjectOpenHashMap test
 */
public class HppcObjMapTest extends AbstractObjObjMapTest {
    private ObjectObjectMap<Integer, Integer> m_map;

    @Override
    public void setup(final int[] keys, final float fillFactor) {
        super.setup( keys, fillFactor );
        m_map = new ObjectObjectOpenHashMap<>( keys.length, fillFactor );
        for (Integer key : m_keys)
            m_map.put(key, key);
    }

    @Override
    public int runRandomTest() {
        int res = 0;
        for ( int i = 0; i < m_keys.length; ++i )
            res = res ^ m_map.get( m_keys[ i ] );
        return res;
    }

}
