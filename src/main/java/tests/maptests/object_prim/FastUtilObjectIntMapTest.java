package tests.maptests.object_prim;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

/**
 * FastUtil Object2IntOpenHashMap
 */
public class FastUtilObjectIntMapTest extends AbstractObjPrimMapTest {
    private Object2IntOpenHashMap<Integer> m_map;

    @Override
    public void setup(int[] keys, float fillFactor) {
        super.setup(keys, fillFactor);
        m_map = new Object2IntOpenHashMap<>( keys.length, fillFactor );
        for ( Integer key : keys ) m_map.put( key, key );
    }

    @Override
    public int runRandomTest() {
        int res = 0;
        for ( int i = 0; i < m_keys.length; ++i )
            res = res ^ m_map.getInt( m_keys[ i ] );
        return res;
    }
}
