package tests.maptests.object_prim;

import com.carrotsearch.hppc.ObjectIntHashMap;
import tests.maptests.IMapTest;
import tests.maptests.ITestSet;

/**
 * HPPC ObjectIntHashMap
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
        private ObjectIntHashMap<Integer> m_map;

        @Override
        public void setup(int[] keys, float fillFactor, final int oneFailureOutOf ) {
            super.setup(keys, fillFactor, oneFailureOutOf);
            m_map = new ObjectIntHashMap<>( keys.length, fillFactor );
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
            final ObjectIntHashMap<Integer> m_map = new ObjectIntHashMap<>( m_keys.length, m_fillFactor );
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
            final ObjectIntHashMap<Integer> m_map = new ObjectIntHashMap<>( m_keys.length / 2 + 1, m_fillFactor );
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

