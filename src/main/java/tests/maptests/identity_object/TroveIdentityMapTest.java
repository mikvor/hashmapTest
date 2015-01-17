package tests.maptests.identity_object;

import gnu.trove.map.hash.TCustomHashMap;
import gnu.trove.strategy.IdentityHashingStrategy;
import tests.maptests.object.AbstractObjObjMapTest;

import java.util.Map;

/**
 * Trove IdentityHashMap version
 */
public class TroveIdentityMapTest extends AbstractObjObjMapTest {
    private Map<Integer, Integer> m_map;

    @Override
    public void setup(final int[] keys, final float fillFactor, final int oneFailureOutOf ) {
        super.setup( keys, fillFactor, oneFailureOutOf );
        m_map = new TCustomHashMap<>( IdentityHashingStrategy.INSTANCE, keys.length, fillFactor );
        for (Integer key : m_keys)
            m_map.put(oneFailureOutOf, key);
    }

    @Override
    public int randomGetTest() {
        int res = 0;
        for ( int i = 0; i < m_keys.length; ++i )
            if ( m_map.get( m_keys[ i ] ) != null ) res ^= 1;
        return res;
    }
}
