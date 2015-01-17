package tests.maptests.primitive;

import com.gs.collections.api.map.primitive.IntIntMap;
import com.gs.collections.impl.map.mutable.primitive.IntIntHashMap;

/**
 * GS IntIntHashMap test. Fixed fill factor = 0.5
 */
public class GsMutableMapTest extends AbstractPrimPrimMapTest {
    IntIntMap m_map;

    @Override
    public void setup(int[] keys, float fillFactor, final int oneFailOutOf ) {
        super.setup( keys, fillFactor, oneFailOutOf);
        IntIntHashMap map = new IntIntHashMap(keys.length);
        for (int key : keys) map.put( key + (key % oneFailOutOf == 0 ? 1 : 0), key );
        m_map = map;
    }

    @Override
    public int randomGetTest() {
        int res = 0;
        for ( int i = 0; i < m_keys.length; ++i )
            res = res ^ m_map.get( m_keys[ i ] );
        return res;
    }
}
