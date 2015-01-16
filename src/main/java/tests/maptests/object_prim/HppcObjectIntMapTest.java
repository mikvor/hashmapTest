package tests.maptests.object_prim;

import com.carrotsearch.hppc.ObjectIntOpenHashMap;

/**
 * HPPC ObjectIntOpenHashMap
 */
public class HppcObjectIntMapTest extends AbstractObjPrimMapTest {
    private ObjectIntOpenHashMap<Integer> m_map;

    @Override
    public void setup(int[] keys, float fillFactor) {
        super.setup(keys, fillFactor);
        m_map = new ObjectIntOpenHashMap<>( keys.length );
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
