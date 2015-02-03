package tests.maptests.object;

import com.gs.collections.impl.map.mutable.UnifiedMap;
import tests.maptests.IMapTest;
import tests.maptests.ITestSet;
import tests.maptests.object_prim.AbstractObjKeyGetTest;
import tests.maptests.object_prim.AbstractObjKeyPutTest;

import java.util.Map;

/**
 * GS UnifiedMap test
 */
public class GsObjMapTest implements ITestSet
{
    @Override
    public IMapTest getTest() {
        return new GsObjGetTest();
    }

    @Override
    public IMapTest putTest() {
        return new GsObjPutTest();
    }

    @Override
    public IMapTest removeTest() {
        return new GsObjRemoveTest();
    }

    private static class GsObjGetTest extends AbstractObjKeyGetTest {
        private Map<Integer, Integer> m_map;

        @Override
        public void setup(final int[] keys, final float fillFactor, final int oneFailureOutOf  ) {
            super.setup( keys, fillFactor, oneFailureOutOf );
            m_map = new UnifiedMap<>( keys.length, fillFactor );
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

    private static class GsObjPutTest extends AbstractObjKeyPutTest {
        @Override
        public int test() {
            final Map<Integer, Integer> m_map = new UnifiedMap<>( m_keys.length, m_fillFactor );
            for ( int i = 0; i < m_keys.length; ++i )
               m_map.put( m_keys[ i ],m_keys[ i ] );
            for ( int i = 0; i < m_keys2.length; ++i )
               m_map.put( m_keys2[ i ],m_keys2[ i ] );
            return m_map.size();
        }
    }

    private static class GsObjRemoveTest extends AbstractObjKeyPutTest {
        @Override
        public int test() {
            final Map<Integer, Integer> m_map = new UnifiedMap<>( m_keys.length / 2 + 1, m_fillFactor );
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

