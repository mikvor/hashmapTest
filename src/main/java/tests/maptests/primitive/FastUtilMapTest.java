package tests.maptests.primitive;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import tests.maptests.IMapTest;
import tests.maptests.ITestSet;

/**
 * FastUtil Int2IntOpenHashMap test
 */
public class FastUtilMapTest implements ITestSet
{

    @Override
    public IMapTest getTest() {
        return new FastUtilGetTest();
    }

    @Override
    public IMapTest putTest() {
        return new FastUtilPutTest();
    }

    private static class FastUtilGetTest extends AbstractPrimPrimGetTest {
        private Int2IntOpenHashMap m_map;

        @Override
        public void setup(final int[] keys, final float fillFactor, final int oneFailOutOf) {
            super.setup(keys, fillFactor, oneFailOutOf);
            m_map = new Int2IntOpenHashMap( keys.length, fillFactor );
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

    private static class FastUtilPutTest extends AbstractPrimPrimPutTest {
        @Override
        public int test() {
            final Int2IntOpenHashMap m_map = new Int2IntOpenHashMap( m_keys.length, m_fillFactor );
            for ( int i = 0; i < m_keys.length; ++i )
                m_map.put( m_keys[ i ],m_keys[ i ] );
            for ( int i = 0; i < m_keys.length; ++i )
                m_map.put( m_keys[ i ],m_keys[ i ] );
            return m_map.size();
        }
    }
}

