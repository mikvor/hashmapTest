package tests.maptests.prim_object;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import tests.maptests.IMapTest;
import tests.maptests.ITestSet;

/**
 * FastUtil Int2ObjectOpenHashMap test
 */
public class FastUtilIntObjectMapTest implements ITestSet
{
    @Override
    public IMapTest getTest() {
        return new FastUtilIntObjectGetTest();
    }

    @Override
    public IMapTest putTest() {
        return new FastUtilIntObjectPutTest();
    }

    @Override
    public IMapTest removeTest() {
        return new FastUtilIntObjectRemoveTest();
    }

    private static class FastUtilIntObjectGetTest extends AbstractPrimObjectGetTest {
        private Int2ObjectOpenHashMap<Integer> m_map;

        @Override
        public void setup(int[] keys, float fillFactor, int oneFailOutOf) {
            super.setup(keys, fillFactor, oneFailOutOf );
            m_map = new Int2ObjectOpenHashMap<>( keys.length, fillFactor );
            for ( int key : keys ) m_map.put( key % oneFailOutOf == 0 ? key + 1 : key, Integer.valueOf(key) );
        }

        @Override
        public int test() {
            int res = 0;
            for ( int i = 0; i < m_keys.length; ++i )
                if ( m_map.get( m_keys[ i ] ) != null ) res ^= 1;
            return res;
        }
    }

    private static class FastUtilIntObjectPutTest extends AbstractPrimObjectPutTest {
        @Override
        public int test() {
            final Integer value = 1;
            final Int2ObjectOpenHashMap<Integer> m_map = new Int2ObjectOpenHashMap<>( m_keys.length, m_fillFactor );
            for ( int i = 0; i < m_keys.length; ++i )
                m_map.put( m_keys[ i ], value );
            for ( int i = 0; i < m_keys.length; ++i )
                m_map.put( m_keys[ i ], value );
            return m_map.size();
        }
    }

    private static class FastUtilIntObjectRemoveTest extends AbstractPrimObjectPutTest {
        @Override
        public int test() {
            final Int2ObjectOpenHashMap<Integer> m_map = new Int2ObjectOpenHashMap<>( m_keys.length / 2 + 1, m_fillFactor );
            final Integer value = 1;
            int add = 0, remove = 0;
            while ( add < m_keys.length )
            {
                m_map.put( m_keys[ add ], value );
                ++add;
                m_map.put( m_keys[ add ], value );
                ++add;
                m_map.remove( m_keys[ remove++ ] );
            }
            return m_map.size();
        }
    }
}

