package tests.maptests.identity_object;

import tests.maptests.object.AbstractObjObjMapTest;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * JDK identity map test - strictly for identical keys during both populating and querying
 */
public class JDKIdentityMapTest extends AbstractObjObjMapTest {
    private Map<Integer, Integer> m_map;

    @Override
    public void setup(final int[] keys, final float fillFactor) {
        super.setup( keys, fillFactor );
        m_map = new IdentityHashMap<>( keys.length );
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
