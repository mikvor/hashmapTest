package tests.maptests.prim_object;

import com.carrotsearch.hppc.IntObjectOpenHashMap;
import tests.maptests.IMapTest;
import tests.maptests.ITestSet;

/**
 * HPPC IntObjectOpenHashMap test
 */
public class HppcIntObjectMapTest implements ITestSet
{
    @Override
    public IMapTest getTest() {
        return new HppcIntObjectGetTest();
    }

    @Override
    public IMapTest putTest() {
        return new HppcIntObjectPutTest();
    }

    private static class HppcIntObjectGetTest extends AbstractPrimObjectGetTest {
        private IntObjectOpenHashMap<Integer> m_map;

        @Override
        public void setup(int[] keys, float fillFactor, int oneFailOutOf) {
            super.setup(keys, fillFactor, oneFailOutOf);
            m_map = new IntObjectOpenHashMap<>( keys.length );
            for ( int key : keys ) m_map.put( key % oneFailOutOf == 0 ? key + 1 : key, key );
        }

        @Override
        public int test() {
            int res = 0;
            for ( int i = 0; i < m_keys.length; ++i )
                if ( m_map.get( m_keys[ i ] ) != null ) res ^= 1;
            return res;
        }
    }

    private static class HppcIntObjectPutTest extends AbstractPrimObjectPutTest {
        @Override
        public int test() {
            final IntObjectOpenHashMap<Integer> m_map = new IntObjectOpenHashMap<>( m_keys.length );
            for ( int i = 0; i < m_keys.length; ++i )
                m_map.put( m_keys[ i ], null );
            for ( int i = 0; i < m_keys.length; ++i )
                m_map.put( m_keys[ i ], null );
            return m_map.size();
        }
    }

}


