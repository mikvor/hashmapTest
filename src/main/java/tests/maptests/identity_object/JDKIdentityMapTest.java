package tests.maptests.identity_object;

import tests.maptests.IMapTest;
import tests.maptests.ITestSet;
import tests.maptests.object_prim.AbstractObjKeyGetTest;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * JDK identity map test - strictly for identical keys during both populating and querying
 */
public class JDKIdentityMapTest implements ITestSet
{
    @Override
    public IMapTest getTest() {
        return new JDKIdentityMapGetTest();
    }

    @Override
    public IMapTest putTest() {
        return new JdkIdentityMapPutTest();
    }

    @Override
    public IMapTest removeTest() {
        return new JdkIdentityMapRemoveTest();
    }

    private static class JDKIdentityMapGetTest extends AbstractObjKeyGetTest {
        private Map<Integer, Integer> m_map;

        @Override
        public void setup(final int[] keys, final float fillFactor, final int oneFailureOutOf ) {
            super.setup( keys, fillFactor, oneFailureOutOf );
            m_map = new IdentityHashMap<>( keys.length );
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

    private static class JdkIdentityMapPutTest extends AbstractObjKeyPutIdentityTest {
        @Override
        public int test() {
            final Map<Integer, Integer> map = new IdentityHashMap<>( m_keys.length );
            for ( int i = 0; i < m_keys.length; ++i )
                map.put( m_keys[ i ], m_keys[ i ] );
            for ( int i = 0; i < m_keys.length; ++i ) //same set is used for identity tests
                map.put( m_keys[ i ], m_keys[ i ] );
            return map.size();
        }
    }

    private static class JdkIdentityMapRemoveTest extends AbstractObjKeyPutIdentityTest {
        @Override
        public int test() {
            final Map<Integer, Integer> m_map = new IdentityHashMap<>( m_keys.length / 2 + 1 );
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



