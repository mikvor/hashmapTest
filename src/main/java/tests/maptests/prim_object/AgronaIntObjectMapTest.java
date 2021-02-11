package tests.maptests.prim_object;

import org.agrona.collections.Int2ObjectHashMap;
import tests.maptests.IMapTest;
import tests.maptests.ITestSet;

/**
 * Agrona Int2ObjectHashMap test
 */
public class AgronaIntObjectMapTest implements ITestSet
{
    @Override
    public IMapTest getTest() {
        return new AgronaIntObjectGetTest();
    }

    @Override
    public IMapTest putTest() {
        return new AgronaIntObjectPutTest();
    }

    @Override
    public IMapTest removeTest() {
        return new AgronaIntObjectRemoveTest();
    }

    private static class AgronaIntObjectGetTest extends AbstractPrimObjectGetTest {
        private Int2ObjectHashMap<Integer> m_map;

        @Override
        public void setup(int[] keys, float fillFactor, int oneFailOutOf) {
            super.setup(keys, fillFactor, oneFailOutOf);
            m_map = new Int2ObjectHashMap<>( keys.length, 0.5f );
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

    private static class AgronaIntObjectPutTest extends AbstractPrimObjectPutTest {
        @Override
        public int test() {
            final Integer value = 1;
            final Int2ObjectHashMap<Integer> m_map = new Int2ObjectHashMap<>( m_keys.length, 0.5f );
            for ( int i = 0; i < m_keys.length; ++i )
                m_map.put( m_keys[ i ], value );
            for ( int i = 0; i < m_keys.length; ++i )
                m_map.put( m_keys[ i ], value );
            return m_map.size();
        }
    }

    private static class AgronaIntObjectRemoveTest extends AbstractPrimObjectPutTest {
        @Override
        public int test() {
            final Int2ObjectHashMap<Integer> m_map = new Int2ObjectHashMap<>( m_keys.length / 2 + 1, 0.5f );
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


