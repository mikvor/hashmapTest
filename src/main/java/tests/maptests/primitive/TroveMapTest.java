package tests.maptests.primitive;

import gnu.trove.map.TIntIntMap;
import gnu.trove.map.hash.TIntIntHashMap;
import tests.maptests.IMapTest;
import tests.maptests.ITestSet;

/**
 * Trove TIntIntHashMap test
 */
public class TroveMapTest implements ITestSet
{
    @Override
    public IMapTest getTest() {
        return new TroveGetTest();
    }

    @Override
    public IMapTest putTest() {
        return new TrovePutTest();
    }

    private static class TroveGetTest extends AbstractPrimPrimGetTest {
        private TIntIntMap m_map;

        @Override
        public void setup(final int[] keys, final float fillFactor, int oneFailOutOf) {
            super.setup( keys, fillFactor, oneFailOutOf );
            m_map = new TIntIntHashMap( keys.length, fillFactor );
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

    private static class TrovePutTest extends AbstractPrimPrimPutTest {
        @Override
        public int test() {
            final TIntIntMap m_map = new TIntIntHashMap( m_keys.length, m_fillFactor );
            for ( int i = 0; i < m_keys.length; ++i )
                m_map.put( m_keys[ i ], m_keys[ i ] );
            for ( int i = 0; i < m_keys.length; ++i )
                m_map.put( m_keys[ i ], m_keys[ i ] );
            return m_map.size();
        }
    }

}

