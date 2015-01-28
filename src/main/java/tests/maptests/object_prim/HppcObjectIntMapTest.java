package tests.maptests.object_prim;

import com.carrotsearch.hppc.ObjectIntOpenHashMap;
import tests.maptests.IMapTest;
import tests.maptests.ITestSet;

/**
 * HPPC ObjectIntOpenHashMap
 */
public class HppcObjectIntMapTest implements ITestSet
{
    @Override
    public IMapTest getTest() {
        return new HppcObjectIntGetTest();
    }

    @Override
    public IMapTest putTest() {
        return new HppcObjectIntPutTest();
    }

    @Override
    public IMapTest removeTest() {
        return new HppcObjectIntRemoveTest();
    }

    private static class HppcObjectIntGetTest extends AbstractObjKeyGetTest {
        private ObjectIntOpenHashMap<Integer> m_map;

        @Override
        public void setup(int[] keys, float fillFactor, final int oneFailureOutOf ) {
            super.setup(keys, fillFactor, oneFailureOutOf);
            m_map = new ObjectIntOpenHashMap<>( keys.length );
            for ( Integer key : keys ) m_map.put( new Integer( key % oneFailureOutOf == 0 ? key+1 : key), key );
        }

        @Override
        public int test() {
            int res = 0;
            for ( int i = 0; i < m_keys.length; ++i )
                res = res ^ m_map.get( m_keys[ i ] );
            return res;
        }
    }

    private static class HppcObjectIntPutTest extends AbstractObjKeyPutTest {
        @Override
        public int test() {
            final ObjectIntOpenHashMap<Integer> m_map = new ObjectIntOpenHashMap<>( m_keys.length );
            for ( int i = 0; i < m_keys.length; ++i )
                m_map.put( m_keys[ i ], i );
            for ( int i = 0; i < m_keys2.length; ++i )
                m_map.put( m_keys2[ i ], i );
            return m_map.size();
        }
    }

    private static class HppcObjectIntRemoveTest extends AbstractObjKeyPutTest {
        @Override
        public int test() {
            final ObjectIntOpenHashMap<Integer> m_map = new ObjectIntOpenHashMap<>( m_keys.length );
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

