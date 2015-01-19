package tests.maptests.object;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import tests.maptests.IMapTest;
import tests.maptests.ITestSet;
import tests.maptests.object_prim.AbstractObjKeyGetTest;
import tests.maptests.object_prim.AbstractObjKeyPutTest;

import java.util.Map;

/**
 * FastUtil Object2ObjectOpenHashMap test
 */
public class FastUtilObjMapTest implements ITestSet
{
    @Override
    public IMapTest getTest() {
        return new FastUtilObjGetTest();
    }

    @Override
    public IMapTest putTest() {
        return new FastUtilObjPutTest();
    }

    private static class FastUtilObjGetTest extends AbstractObjKeyGetTest {
        private Map<Integer, Integer> m_map;

        @Override
        public void setup(final int[] keys, final float fillFactor, final int oneFailureOutOf ) {
            super.setup( keys, fillFactor, oneFailureOutOf );
            m_map = new Object2ObjectOpenHashMap<>( m_keys.length, fillFactor );
            for (Integer key : m_keys) m_map.put(new Integer( key % oneFailureOutOf == 0 ? key + 1 : key ), key);
        }

        @Override
        public int test() {
            int res = 0;
            for ( int i = 0; i < m_keys.length; ++i )
                if ( m_map.get( m_keys[ i ] ) != null ) res ^= 1;
            return res;
        }
    }

    private static class FastUtilObjPutTest extends AbstractObjKeyPutTest {
        @Override
        public int test() {
            final Map<Integer, Integer> m_map = new Object2ObjectOpenHashMap<>( m_keys.length, m_fillFactor );
            for ( int i = 0; i < m_keys.length; ++i )
                m_map.put( m_keys[ i ], m_keys[ i ] );
            for ( int i = 0; i < m_keys2.length; ++i )
                m_map.put( m_keys2[ i ], m_keys2[ i ] );
            return m_map.size();
        }
    }
}

