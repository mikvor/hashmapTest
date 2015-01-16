package tests.maptests.object_prim;

import com.gs.collections.impl.map.mutable.primitive.ObjectIntHashMap;

/**
 * GS ObjectIntHashMap
 */
public class GsObjectIntMapTest extends AbstractObjPrimMapTest {
    private ObjectIntHashMap<Integer> m_map;

    @Override
    public void setup(int[] keys, float fillFactor) {
        super.setup(keys, fillFactor);
        m_map = new ObjectIntHashMap<>( keys.length );
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
