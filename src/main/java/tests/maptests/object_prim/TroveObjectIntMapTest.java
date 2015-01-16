package tests.maptests.object_prim;

import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;

/**
 * Trove TObjectIntHashMap
 */
public class TroveObjectIntMapTest extends AbstractObjPrimMapTest {
    private TObjectIntMap<Integer> m_map;
    @Override
    public void setup(int[] keys, float fillFactor) {
        super.setup(keys, fillFactor);
        m_map = new TObjectIntHashMap<>( keys.length, fillFactor );
        for ( Integer key : keys ) m_map.put( key, key );
    }

    @Override
    public int runRandomTest() {
        int res = 0;
        for ( int i = 0; i < m_keys.length; ++i )
            res = res ^ m_map.get( m_keys[ i ] );
        return res;
    }

}
