package tests.maptests.object;

import com.gs.collections.impl.map.mutable.UnifiedMap;

import java.util.Map;

/**
 * GS UnifiedMap test
 */
public class GsObjMapTest extends AbstractObjObjMapTest {
    private Map<Integer, Integer> m_map;

    @Override
    public void setup(final int[] keys, final float fillFactor, final int oneFailureOutOf  ) {
        super.setup( keys, fillFactor, oneFailureOutOf );
        m_map = new UnifiedMap<>( keys.length, fillFactor );
        for (Integer key : m_keys) m_map.put(new Integer( key % oneFailureOutOf == 0 ? key + 1 : key ), key);
    }

    @Override
    public int randomGetTest() {
        int res = 0;
        for ( int i = 0; i < m_keys.length; ++i )
            if ( m_map.get( m_keys[ i ] ) != null ) res ^= 1;
        return res;
    }
}
