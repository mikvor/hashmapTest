package tests.maptests.object_prim;

import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import tests.maptests.IMapTest;
import tests.maptests.ITestSet;

/**
 * Trove TObjectIntHashMap
 */
public class TroveObjectIntMapTest implements ITestSet
{
    @Override
    public IMapTest getTest() {
        return new TroveObjectIntGetTest();
    }

    @Override
    public IMapTest putTest() {
        return new TroveObjectIntPutTest();
    }

    private static class TroveObjectIntGetTest extends AbstractObjKeyGetTest {
        private TObjectIntMap<Integer> m_map;
        @Override
        public void setup(int[] keys, float fillFactor, final int oneFailureOutOf ) {
            super.setup(keys, fillFactor, oneFailureOutOf);
            m_map = new TObjectIntHashMap<>( keys.length, fillFactor );
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

    private static class TroveObjectIntPutTest extends AbstractObjKeyPutTest {
        @Override
        public int test() {
            final TObjectIntMap<Integer> m_map = new TObjectIntHashMap<>( m_keys.length, m_fillFactor );
            for ( int i = 0; i < m_keys.length; ++i )
               m_map.put( m_keys[ i ], i );
            for ( int i = 0; i < m_keys2.length; ++i )
               m_map.put( m_keys2[ i ], i );
            return m_map.size();
        }
    }
}

