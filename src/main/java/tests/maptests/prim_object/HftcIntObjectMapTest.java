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
    public void setup(final int[] keys, float fillFactor) {
        super.setup( keys, fillFactor);
        m_map = HashIntObjMaps.newMutableMap( keys.length );
        for (int key : keys) m_map.put(key, Integer.valueOf(key));
    }

    @Override
    public int runRandomTest() {
        int res = 0;
        for ( int i = 0; i < m_keys.length; ++i )
            res = res ^ m_map.get( m_keys[ i ] );
        return res;
    }
}

