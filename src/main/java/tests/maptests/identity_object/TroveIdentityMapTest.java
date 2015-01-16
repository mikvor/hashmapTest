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
    public void setup(final int[] keys, final float fillFactor) {
        super.setup( keys, fillFactor );
        m_map = new TCustomHashMap<>( IdentityHashingStrategy.INSTANCE, keys.length, fillFactor );
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
