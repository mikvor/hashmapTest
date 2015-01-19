package tests.maptests.primitive;

import com.gs.collections.api.map.primitive.IntIntMap;
import com.gs.collections.impl.map.mutable.primitive.IntIntHashMap;
import tests.maptests.IMapTest;
import tests.maptests.ITestSet;

/**
 * GS IntIntHashMap test. Fixed fill factor = 0.5
 */
public class GsMutableMapTest implements ITestSet
{
    @Override
    public IMapTest getTest() {
        return new GsMutableGetTest();
    }

    @Override
    public IMapTest putTest() {
        return new GsMutablePutTest();
    }

    private static class GsMutableGetTest extends AbstractPrimPrimGetTest {
        IntIntMap m_map;

        @Override
        public void setup(int[] keys, float fillFactor, final int oneFailOutOf ) {
            super.setup( keys, fillFactor, oneFailOutOf);
            IntIntHashMap map = new IntIntHashMap(keys.length);
            for (int key : keys) map.put( key + (key % oneFailOutOf == 0 ? 1 : 0), key );
            m_map = map;
        }

        @Override
        public int test() {
            int res = 0;
            for ( int i = 0; i < m_keys.length; ++i )
                res = res ^ m_map.get( m_keys[ i ] );
            return res;
        }
    }

    private static class GsMutablePutTest extends AbstractPrimPrimPutTest {
        @Override
        public int test() {
            final IntIntHashMap m_map = new IntIntHashMap(m_keys.length);
            for ( int i = 0; i < m_keys.length; ++i )
                m_map.put( m_keys[ i ], m_keys[ i ] );
            for ( int i = 0; i < m_keys.length; ++i )
                m_map.put( m_keys[ i ], m_keys[ i ] );
            return m_map.size();
        }
    }

}

