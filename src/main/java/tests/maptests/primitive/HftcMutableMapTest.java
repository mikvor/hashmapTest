package tests.maptests.primitive;

import net.openhft.koloboke.collect.map.hash.HashIntIntMap;
import net.openhft.koloboke.collect.map.hash.HashIntIntMaps;

/**
 * Koloboke HashIntIntMaps.newMutableMap test
 */
public class HftcMutableMapTest extends AbstractPrimPrimMapTest {
    private HashIntIntMap m_map;

    @Override
    public void setup(final int[] keys, float fillFactor, final int oneFailOutOf) {
        super.setup( keys, fillFactor, oneFailOutOf);
        m_map = HashIntIntMaps.newMutableMap(keys.length);
        for (int key : keys) m_map.put( key + (key % oneFailOutOf == 0 ? 1 : 0), key );
    }

    @Override
    public int randomGetTest() {
        int res = 0;
        for ( int i = 0; i < m_keys.length; ++i )
            res = res ^ m_map.get( m_keys[ i ] );
        return res;
    }
}
