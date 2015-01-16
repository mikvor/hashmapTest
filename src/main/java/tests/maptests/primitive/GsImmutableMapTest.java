package tests.maptests.primitive;

import com.gs.collections.api.map.primitive.IntIntMap;
import com.gs.collections.impl.map.mutable.primitive.IntIntHashMap;

/**
 * GS ImmutableIntIntHashMap test. Fixed fill factor = 0.5
 */
public class GsImmutableMapTest extends AbstractPrimPrimMapTest {
    private IntIntMap m_map;

    @Override
    public void setup(int[] keys, float fillFactor) {
        super.setup(keys, fillFactor);
        IntIntHashMap map = new IntIntHashMap(keys.length);
        for ( int key: keys ) map.put( key, key );
        m_map = map.toImmutable();
    }

    @Override
    public int runRandomTest() {
        int res = 0;
        for ( int i = 0; i < m_keys.length; ++i )
            res = res ^ m_map.get( m_keys[ i ] );
        return res;
    }
}
