package tests.maptests.prim_object;


import org.eclipse.collections.impl.map.mutable.primitive.IntObjectHashMap;
import tests.maptests.IMapTest;
import tests.maptests.ITestSet;

/**
 * GS IntObjectHashMap
 */
public class GsIntObjectMapTest implements ITestSet
{
    @Override
    public IMapTest getTest() {
        return new GsIntObjectGetTest();
    }

    @Override
    public IMapTest putTest() {
        return new GsIntObjectPutTest();
    }

    @Override
    public IMapTest removeTest() {
        return new GsIntObjectRemoveTest();
    }

    private static class GsIntObjectGetTest extends AbstractPrimObjectGetTest {
        private IntObjectHashMap<Integer> m_map;

        @Override
        public void setup(int[] keys, float fillFactor, int oneFailOutOf) {
            super.setup(keys, fillFactor, oneFailOutOf );
            m_map = new IntObjectHashMap<>( keys.length );
            for ( int key : keys ) m_map.put( key % oneFailOutOf == 0 ? key + 1 : key, key );
        }

        @Override
        public int test() {
            int res = 0;
            for ( int i = 0; i < m_keys.length; ++i )
                if ( m_map.get( m_keys[ i ] ) != null ) res ^= 1;
            return res;
        }
    }

    private static class GsIntObjectPutTest extends AbstractPrimObjectPutTest {
        @Override
        public int test() {
            final IntObjectHashMap<Integer> m_map = new IntObjectHashMap<>( m_keys.length );
            for ( int i = 0; i < m_keys.length; ++i )
                m_map.put( m_keys[ i ], null );
            for ( int i = 0; i < m_keys.length; ++i )
                m_map.put( m_keys[ i ], null );
            return m_map.size();
        }
    }

    private static class GsIntObjectRemoveTest extends AbstractPrimObjectPutTest {
        @Override
        public int test() {
            final IntObjectHashMap<Integer> m_map = new IntObjectHashMap<>( m_keys.length / 2 + 1 );
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

