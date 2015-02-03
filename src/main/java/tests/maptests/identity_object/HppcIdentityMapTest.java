package tests.maptests.identity_object;

import com.carrotsearch.hppc.ObjectObjectMap;
import com.carrotsearch.hppc.ObjectObjectOpenIdentityHashMap;
import tests.maptests.IMapTest;
import tests.maptests.ITestSet;
import tests.maptests.object_prim.AbstractObjKeyGetTest;

/**
 * HPPC IdentityMap version
 */
public class HppcIdentityMapTest implements ITestSet
{
    @Override
    public IMapTest getTest() {
        return new HppcIdentityMapGetTest();
    }

    @Override
    public IMapTest putTest() {
        return new HppcObjIdentityMapPutTest();
    }

    @Override
    public IMapTest removeTest() {
        return new HppcObjIdentityMapRemoveTest();
    }

    private static class HppcIdentityMapGetTest extends AbstractObjKeyGetTest {
        private ObjectObjectMap<Integer, Integer> m_map;

        @Override
        public void setup(final int[] keys, final float fillFactor, final int oneFailureOutOf ) {
            super.setup( keys, fillFactor, oneFailureOutOf );
            m_map = new ObjectObjectOpenIdentityHashMap<>( keys.length, fillFactor );
            for (Integer key : m_keys)
                m_map.put(key % oneFailureOutOf == 0 ? key + 1 : key, key);
        }

        @Override
        public int test() {
            int res = 0;
            for ( int i = 0; i < m_keys.length; ++i )
                if ( m_map.get( m_keys[ i ] ) != null ) res ^= 1;
            return res;
        }
    }

    private static class HppcObjIdentityMapPutTest extends AbstractObjKeyPutIdentityTest {
        @Override
        public int test() {
            final ObjectObjectMap<Integer, Integer> m_map = new ObjectObjectOpenIdentityHashMap<>( m_keys.length, m_fillFactor );
            for ( int i = 0; i < m_keys.length; ++i )
                m_map.put( m_keys[ i ], m_keys[ i ] );
            for ( int i = 0; i < m_keys.length; ++i ) //same keys are use for identity test
                m_map.put( m_keys[ i ], m_keys[ i ] );
            return m_map.size();
        }
    }

    private static class HppcObjIdentityMapRemoveTest extends AbstractObjKeyPutIdentityTest {
        @Override
        public int test() {
            final ObjectObjectMap<Integer, Integer> m_map = new ObjectObjectOpenIdentityHashMap<>( m_keys.length / 2 + 1, m_fillFactor );
            int add = 0, remove = 0;
            while ( add < m_keys.length )
            {
                m_map.put( m_keys[ add ], m_keys[ add ] );
                ++add;
                m_map.put( m_keys[ add ], m_keys[ add ] );
                ++add;
                m_map.remove( m_keys[ remove++ ] );
            }
            return m_map.size();
        }
    }

}

