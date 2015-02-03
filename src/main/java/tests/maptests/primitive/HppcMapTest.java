package tests.maptests.primitive;

import com.carrotsearch.hppc.IntIntOpenHashMap;
import tests.maptests.IMapTest;
import tests.maptests.ITestSet;

/**
 * HPPC IntIntOpenHashMap test
 */
public class HppcMapTest implements ITestSet
{
    @Override
    public IMapTest getTest() {
        return new HppcGetTest();
    }

    @Override
    public IMapTest putTest() {
        return new HppcPutTest();
    }

    @Override
    public IMapTest removeTest() {
        return new HppcRemoveTest();
    }

    private static class HppcGetTest extends AbstractPrimPrimGetTest {
        private IntIntOpenHashMap m_map;

        @Override
        public void setup(final int[] keys, final float fillFactor, int oneFailOutOf ) {
            super.setup( keys, fillFactor, oneFailOutOf );
            m_map = new IntIntOpenHashMap( keys.length, fillFactor );
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

    private static class HppcPutTest extends AbstractPrimPrimPutTest {
        @Override
        public int test() {
            final IntIntOpenHashMap m_map = new IntIntOpenHashMap( m_keys.length, m_fillFactor );
            for ( int i = 0; i < m_keys.length; ++i )
               m_map.put( m_keys[ i ],m_keys[ i ] );
            for ( int i = 0; i < m_keys.length; ++i )
               m_map.put( m_keys[ i ],m_keys[ i ] );
            return m_map.size();
        }
    }

    private static class HppcRemoveTest extends AbstractPrimPrimPutTest {
        @Override
        public int test() {
            final IntIntOpenHashMap m_map = new IntIntOpenHashMap( m_keys.length / 2 + 1, m_fillFactor );
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

