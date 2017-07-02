package tests.maptests.primitive;

import com.koloboke.collect.hash.HashConfig;
import com.koloboke.collect.map.hash.HashIntIntMap;
import com.koloboke.collect.map.hash.HashIntIntMaps;
import tests.maptests.IMapTest;
import tests.maptests.ITestSet;

/**
 * Koloboke HashIntIntMaps.newMutableMap test
 */
public class KolobokeMutableMapTest implements ITestSet
{
    @Override
    public IMapTest getTest() {
        return new KolobokeMutableGetTest();
    }

    @Override
    public IMapTest putTest() {
        return new KolobokeMutablePutTest();
    }

    @Override
    public IMapTest removeTest() {
        return new KolobokeMutableRemoveTest();
    }

    private static HashIntIntMap makeMap(final int size, final float fillFactor )
    {
        return HashIntIntMaps.getDefaultFactory().
                            withHashConfig(HashConfig.fromLoads(fillFactor/2, fillFactor, fillFactor)).newMutableMap(size);
    }

    private static class KolobokeMutableGetTest extends AbstractPrimPrimGetTest {
        private HashIntIntMap m_map;

        @Override
        public void setup(final int[] keys, float fillFactor, final int oneFailOutOf) {
            super.setup( keys, fillFactor, oneFailOutOf);
            m_map = makeMap(keys.length, fillFactor);
            for (int key : keys) m_map.put( key + (key % oneFailOutOf == 0 ? 1 : 0), key );
        }

        @Override
        public int test() {
            int res = 0;
            for ( int i = 0; i < m_keys.length; ++i )
                res = res ^ m_map.get( m_keys[ i ] );
            return res;
        }
    }

    private static class KolobokeMutablePutTest extends AbstractPrimPrimPutTest {
        @Override
        public int test() {
            final HashIntIntMap m_map = makeMap(m_keys.length, m_fillFactor);
            for ( int i = 0; i < m_keys.length; ++i )
                m_map.put( m_keys[ i ],m_keys[ i ] );
            for ( int i = 0; i < m_keys.length; ++i )
                m_map.put( m_keys[ i ],m_keys[ i ] );
            return m_map.size();
        }
    }

    private static class KolobokeMutableRemoveTest extends AbstractPrimPrimPutTest {
        @Override
        public int test() {
            final HashIntIntMap m_map = makeMap(m_keys.length / 2 + 1, m_fillFactor);
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

