package tests.maptests.object;

import tests.maptests.IMapTest;
import tests.maptests.ITestSet;
import tests.maptests.object_prim.AbstractObjKeyGetTest;
import tests.maptests.object_prim.AbstractObjKeyPutTest;

import java.util.HashMap;
import java.util.Map;

/**
 * JDK Map<Integer, Integer> tests
 */
public class JdkMapTest implements ITestSet
{
    @Override
    public IMapTest getTest() {
        return new JdkMapGetTest();
    }

    @Override
    public IMapTest putTest() {
        return new JdkMapPutTest();
    }

    protected <T, V> Map<T, V> makeMap( final int size, final float fillFactor )
    {
        return new HashMap<>( size, fillFactor );
    }

    //classes are non static due to a subclass presence
    private class JdkMapGetTest extends AbstractObjKeyGetTest {
        private Map<Integer, Integer> m_map;

        @Override
        public void setup(final int[] keys, final float fillFactor, final int oneFailureOutOf ) {
            super.setup( keys, fillFactor, oneFailureOutOf );
            m_map = makeMap( keys.length, fillFactor );
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

    private class JdkMapPutTest extends AbstractObjKeyPutTest {
        @Override
        public int test() {
            final Map<Integer, Integer> map = makeMap( m_keys.length, m_fillFactor );
            for ( int i = 0; i < m_keys.length; ++i )
                map.put( m_keys[ i ], m_keys[ i ] );
            for ( int i = 0; i < m_keys2.length; ++i )
                map.put( m_keys2[ i ], m_keys2[ i ] );
            return map.size();
        }
    }

}

