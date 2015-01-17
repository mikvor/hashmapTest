package tests.maptests.object_prim;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

/**
 * FastUtil Object2IntOpenHashMap
 */
public class FastUtilObjectIntMapTest extends AbstractObjPrimMapTest {
    private Object2IntOpenHashMap<Integer> m_map;

    @Override
    public void setup(int[] keys, float fillFactor, final int oneFailureOutOf ) {
        super.setup(keys, fillFactor, oneFailureOutOf);
        m_map = new Object2IntOpenHashMap<>( keys.length, fillFactor );
        for ( Integer key : keys ) m_map.put( new Integer( key % oneFailureOutOf == 0 ? key + 1 : key), key );
    }

    @Override
    public int randomGetTest() {
        int res = 0;
        for ( int i = 0; i < m_keys.length; ++i )
            res = res ^ m_map.getInt( m_keys[ i ] );
        return res;
    }
}
