package tests.maptests.object;

import gnu.trove.map.hash.THashMap;

import java.util.Map;

/**
 * Trove THashMap&lt;Integer, Integer&gt; test
 */
public class TroveObjMapTest extends AbstractObjObjMapTest {
    private Map<Integer, Integer> m_map;

    @Override
    public void setup(final int[] keys, final float fillFactor) {
        super.setup( keys, fillFactor );
        m_map = new THashMap<>( keys.length, fillFactor );
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
