package tests.maptests.object_prim;

import com.carrotsearch.hppc.ObjectIntOpenHashMap;

/**
 * HPPC ObjectIntOpenHashMap
 */
public class HppcObjectIntMapTest extends AbstractObjPrimMapTest {
    private ObjectIntOpenHashMap<Integer> m_map;

    @Override
    public void setup(int[] keys, float fillFactor, final int oneFailureOutOf ) {
        super.setup(keys, fillFactor, oneFailureOutOf);
        m_map = new ObjectIntOpenHashMap<>( keys.length );
        for ( Integer key : keys ) m_map.put( new Integer( key % oneFailureOutOf == 0 ? key+1 : key), key );
    }

    @Override
    public int randomGetTest() {
        int res = 0;
        for ( int i = 0; i < m_keys.length; ++i )
            res = res ^ m_map.get( m_keys[ i ] );
        return res;
    }

}
