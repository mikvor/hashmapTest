package tests.maptests.identity_object;

import com.carrotsearch.hppc.ObjectObjectMap;
import com.carrotsearch.hppc.ObjectObjectOpenIdentityHashMap;
import tests.maptests.object.AbstractObjObjMapTest;

/**
 * HPPC IdentityMap version
 */
public class HppcIdentityMapTest extends AbstractObjObjMapTest {
    private ObjectObjectMap<Integer, Integer> m_map;

    @Override
    public void setup(final int[] keys, final float fillFactor, final int oneFailureOutOf ) {
        super.setup( keys, fillFactor, oneFailureOutOf );
        m_map = new ObjectObjectOpenIdentityHashMap<>( keys.length, fillFactor );
        for (Integer key : m_keys)
            m_map.put(key % oneFailureOutOf == 0 ? key + 1 : key, key);
    }

    @Override
    public int randomGetTest() {
        int res = 0;
        for ( int i = 0; i < m_keys.length; ++i )
            if ( m_map.get( m_keys[ i ] ) != null ) res ^= 1;
        return res;
    }

}
