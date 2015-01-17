package tests.maptests.object_prim;

import net.openhft.koloboke.collect.map.hash.HashObjIntMap;
import net.openhft.koloboke.collect.map.hash.HashObjIntMaps;

/**
 * Koloboke object-2-int map
 */
public class HftcObjectIntMapTest extends AbstractObjPrimMapTest {
    private HashObjIntMap<Integer> m_map;

    @Override
    public void setup(final int[] keys, float fillFactor, final int oneFailureOutOf ) {
        super.setup( keys, fillFactor, oneFailureOutOf);
        m_map = HashObjIntMaps.newMutableMap(keys.length);
        for (Integer key : keys) m_map.put(new Integer( key % oneFailureOutOf == 0 ? key+1 : key), key.intValue());
    }

    @Override
    public int randomGetTest() {
        int res = 0;
        for ( int i = 0; i < m_keys.length; ++i )
            res = res ^ m_map.getInt( m_keys[ i ] );
        return res;
    }

}
