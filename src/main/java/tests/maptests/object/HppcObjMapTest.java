package tests.maptests.object;

import com.carrotsearch.hppc.ObjectObjectMap;
import com.carrotsearch.hppc.ObjectObjectHashMap;
import tests.maptests.IMapTest;
import tests.maptests.ITestSet;
import tests.maptests.object_prim.AbstractObjKeyGetTest;
import tests.maptests.object_prim.AbstractObjKeyPutTest;

/**
 * HPPC ObjectObjectHashMap test
 */
public class HppcObjMapTest implements ITestSet
{
    @Override
    public IMapTest getTest() {
        return new HppcObjMapGetTest();
    }

    @Override
    public IMapTest putTest() {
        return new HppcObjMapPutTest();
    }

    @Override
    public IMapTest removeTest() {
        return new HppcObjMapRemoveTest();
    }

    private static class HppcObjMapGetTest extends AbstractObjKeyGetTest {
        private ObjectObjectMap<Integer, Integer> m_map;

        @Override
        public void setup(final int[] keys, final float fillFactor, final int oneFailureOutOf ) {
            super.setup( keys, fillFactor, oneFailureOutOf );
            m_map = new ObjectObjectHashMap<>( keys.length, fillFactor );
            for (Integer key : m_keys)
                m_map.put(new Integer( key % oneFailureOutOf == 0 ? key + 1 : key ), key);
        }

        @Override
        public int test() {
            int res = 0;
            for ( int i = 0; i < m_keys.length; ++i )
                if ( m_map.get( m_keys[ i ] ) != null ) res ^= 1;
            return res;
        }
    }

    private static class HppcObjMapPutTest extends AbstractObjKeyPutTest {
        @Override
        public int test() {
            final ObjectObjectMap<Integer, Integer> m_map = new ObjectObjectHashMap<>( m_keys.length, m_fillFactor );
            for ( int i = 0; i < m_keys.length; ++i )
                m_map.put( m_keys[ i ], m_keys[ i ] );
            for ( int i = 0; i < m_keys2.length; ++i )
                m_map.put( m_keys2[ i ], m_keys2[ i ] );
            return m_map.size();
        }
    }

    private static class HppcObjMapRemoveTest extends AbstractObjKeyPutTest {
        @Override
        public int test() {
            final ObjectObjectMap<Integer, Integer> m_map = new ObjectObjectHashMap<>( m_keys.length / 2 + 1, m_fillFactor );
            int add = 0, remove = 0;
            while ( add < m_keys.length )
            {
                m_map.put( m_keys[ add ], m_keys[ add ] );
                ++add;
                m_map.put( m_keys[ add ], m_keys[ add ] );
                ++add;
                m_map.remove( m_keys2[ remove++ ] );
            }
            return m_map.size();
        }
    }
}

