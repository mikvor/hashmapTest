package tests.maptests.primitive;

import net.openhft.koloboke.collect.map.hash.HashIntIntMap;
import net.openhft.koloboke.collect.map.hash.HashIntIntMaps;

/**
 * Koloboke HashIntIntMaps.newImmutableMap test
 */
public class HftcImmutableMapTest extends AbstractPrimPrimMapTest {
    private HashIntIntMap m_map;

    @Override
    public void setup(final int[] keys, float fillFactor) {
        super.setup( keys, fillFactor);
        final HashIntIntMap map = HashIntIntMaps.newMutableMap(keys.length);
        for (int key : keys) map.put(key, key);
        m_map = HashIntIntMaps.newImmutableMap( map );
    }

    @Override
    public int runRandomTest() {
        int res = 0;
        for ( int i = 0; i < m_keys.length; ++i )
            res = res ^ m_map.get( m_keys[ i ] );
        return res;
    }
}
