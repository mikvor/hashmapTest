package tests.maptests.object_prim;

import net.openhft.koloboke.collect.hash.HashConfig;
import net.openhft.koloboke.collect.map.hash.HashObjIntMap;
import net.openhft.koloboke.collect.map.hash.HashObjIntMaps;
import tests.maptests.IMapTest;
import tests.maptests.ITestSet;

/**
 * Koloboke object-2-int map
 */
public class HftcObjectIntMapTest implements ITestSet
{
    @Override
    public IMapTest getTest() {
        return new HftcObjectIntGetTest();
    }

    @Override
    public IMapTest putTest() {
        return new HftcObjectIntPutTest();
    }

    @Override
    public IMapTest removeTest() {
        return new HftcObjectIntRemoveTest();
    }

    private static <T> HashObjIntMap<T> makeMap( final int size, final float fillFactor )
    {
        return HashObjIntMaps.getDefaultFactory().withHashConfig(HashConfig.fromLoads( fillFactor/2, fillFactor, fillFactor )).
                newMutableMap(size);
    }

    private static class HftcObjectIntGetTest extends AbstractObjKeyGetTest {
        private HashObjIntMap<Integer> m_map;

        @Override
        public void setup(final int[] keys, float fillFactor, final int oneFailureOutOf ) {
            super.setup( keys, fillFactor, oneFailureOutOf);
            m_map = makeMap(keys.length, fillFactor);
            for (Integer key : keys) m_map.put(new Integer( key % oneFailureOutOf == 0 ? key+1 : key), key.intValue());
        }

        @Override
        public int test() {
            int res = 0;
            for ( int i = 0; i < m_keys.length; ++i )
                res = res ^ m_map.getInt( m_keys[ i ] );
            return res;
        }
    }

    private static class HftcObjectIntPutTest extends AbstractObjKeyPutTest {
        @Override
        public int test() {
            final HashObjIntMap<Integer> m_map = makeMap( m_keys.length, m_fillFactor );
            for ( int i = 0; i < m_keys.length; ++i )
                m_map.put( m_keys[ i ], i );
            for ( int i = 0; i < m_keys2.length; ++i )
                m_map.put( m_keys2[ i ], i );
            return m_map.size();
        }
    }

    private static class HftcObjectIntRemoveTest extends AbstractObjKeyPutTest {
        @Override
        public int test() {
            final HashObjIntMap<Integer> m_map = makeMap( m_keys.length, m_fillFactor );
            int add = 0, remove = 0;
            while ( add < m_keys.length )
            {
                m_map.put( m_keys[ add ], add );
                ++add;
                m_map.put( m_keys[ add ], add );
                ++add;
                m_map.remove( m_keys[ remove++ ] );
            }
            return m_map.size();
        }
    }
}

