package tests.maptests.primitive;

import tests.maptests.IMapTest;
import tests.maptests.ITestSet;
import org.agrona.collections.Int2IntHashMap;


/**
 * Agrona Int2IntHashMap test
 */
public class AgronaMapTest implements ITestSet
{
    private static final int MISSING_VALUE = Integer.MIN_VALUE;

    @Override
    public IMapTest getTest() {
        return new AgronaGetTest();
    }

    @Override
    public IMapTest putTest() {
        return new AgronaPutTest();
    }

    @Override
    public IMapTest removeTest() {
        return new AgronaRemoveTest();
    }

    private static class AgronaGetTest extends AbstractPrimPrimGetTest {
        private Int2IntHashMap m_map;

        @Override
        public void setup(final int[] keys, final float fillFactor, int oneFailOutOf ) {
            super.setup( keys, fillFactor, oneFailOutOf );
            m_map = new Int2IntHashMap( keys.length, fillFactor, MISSING_VALUE);
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

    private static class AgronaPutTest extends AbstractPrimPrimPutTest {
        @Override
        public int test() {
            final Int2IntHashMap m_map = new Int2IntHashMap( m_keys.length, m_fillFactor, MISSING_VALUE );
            for ( int i = 0; i < m_keys.length; ++i )
                m_map.put( m_keys[ i ],m_keys[ i ] );
            for ( int i = 0; i < m_keys.length; ++i )
                m_map.put( m_keys[ i ],m_keys[ i ] );
            return m_map.size();
        }
    }

    private static class AgronaRemoveTest extends AbstractPrimPrimPutTest {
        @Override
        public int test() {
            final Int2IntHashMap m_map = new Int2IntHashMap( m_keys.length / 2 + 1, m_fillFactor, MISSING_VALUE );
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

