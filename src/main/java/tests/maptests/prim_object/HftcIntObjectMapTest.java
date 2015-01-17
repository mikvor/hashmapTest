package tests.maptests.prim_object;

import net.openhft.koloboke.collect.map.hash.HashIntObjMap;
import net.openhft.koloboke.collect.map.hash.HashIntObjMaps;
import tests.maptests.primitive.AbstractPrimPrimMapTest;

/**
 * Koloboke int-2-object map test
 */
public class HftcIntObjectMapTest extends AbstractPrimPrimMapTest {
    private HashIntObjMap<Integer> m_map;

    @Override
    public void setup(final int[] keys, float fillFactor, int oneFailOutOf) {
        super.setup( keys, fillFactor, oneFailOutOf);
        m_map = HashIntObjMaps.newMutableMap( keys.length );
        for (int key : keys) m_map.put(key % oneFailOutOf == 0 ? key + 1 : key, Integer.valueOf(key));
    }

    @Override
    public int randomGetTest() {
        int res = 0;
        for ( int i = 0; i < m_keys.length; ++i )
            if ( m_map.get( m_keys[ i ] ) != null ) res ^= 1;
        return res;
    }
}

