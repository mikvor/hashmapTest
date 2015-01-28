package tests.maptests.prim_object;

import net.openhft.koloboke.collect.hash.HashConfig;
import net.openhft.koloboke.collect.map.hash.HashIntObjMap;
import net.openhft.koloboke.collect.map.hash.HashIntObjMaps;
import tests.maptests.IMapTest;
import tests.maptests.ITestSet;
import tests.maptests.primitive.AbstractPrimPrimGetTest;
import tests.maptests.primitive.AbstractPrimPrimPutTest;

/**
 * Koloboke int-2-object map test
 */
public class HftcIntObjectMapTest implements ITestSet
{
    @Override
    public IMapTest getTest() {
        return new HftcIntObjectGetTest();
    }

    @Override
    public IMapTest putTest() {
        return new HftcIntObjectPutTest();
    }

    @Override
    public IMapTest removeTest() {
        return new HftcIntObjectRemoveTest();
    }

    private static <T> HashIntObjMap<T> makeMap( final int size, final float fillFactor )
    {
        return HashIntObjMaps.getDefaultFactory().withHashConfig(HashConfig.fromLoads( fillFactor/2, fillFactor, fillFactor)).newMutableMap( size );
    }

    private static class HftcIntObjectGetTest extends AbstractPrimPrimGetTest {
        private HashIntObjMap<Integer> m_map;

        @Override
        public void setup(final int[] keys, float fillFactor, int oneFailOutOf) {
            super.setup( keys, fillFactor, oneFailOutOf);
            m_map = makeMap( keys.length, fillFactor );
            for (int key : keys) m_map.put(key % oneFailOutOf == 0 ? key + 1 : key, Integer.valueOf(key));
        }

        @Override
        public int test() {
            int res = 0;
            for ( int i = 0; i < m_keys.length; ++i )
                if ( m_map.get( m_keys[ i ] ) != null ) res ^= 1;
            return res;
        }
    }

    private static class HftcIntObjectPutTest extends AbstractPrimPrimPutTest {
        @Override
        public int test() {
            final HashIntObjMap<Integer> m_map = makeMap( m_keys.length, m_fillFactor );
            for ( int i = 0; i < m_keys.length; ++i )
                m_map.put( m_keys[ i ], null );
            for ( int i = 0; i < m_keys.length; ++i )
                m_map.put( m_keys[ i ], null );
            return m_map.size();
        }
    }

    private static class HftcIntObjectRemoveTest extends AbstractPrimPrimPutTest {
        @Override
        public int test() {
            final HashIntObjMap<Integer> m_map = makeMap( m_keys.length, m_fillFactor );
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


