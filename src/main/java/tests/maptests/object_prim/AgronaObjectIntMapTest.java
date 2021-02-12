package tests.maptests.object_prim;

import org.agrona.collections.Object2IntHashMap;
import tests.maptests.IMapTest;
import tests.maptests.ITestSet;


/**
 * Agrona Object2IntHashMap test
 */
public class AgronaObjectIntMapTest implements ITestSet
{
    private static final int MISSING_VALUE = Integer.MIN_VALUE;

    @Override
    public IMapTest getTest() {
        return new AgronaObjectIntGetTest();
    }

    @Override
    public IMapTest putTest() {
        return new AgronaObjectIntPutTest();
    }

    @Override
    public IMapTest removeTest() {
        return new AgronaObjectIntRemoveTest();
    }

    private static class AgronaObjectIntGetTest extends AbstractObjKeyGetTest {
        private Object2IntHashMap<Integer> m_map;

        @Override
        public void setup(int[] keys, float fillFactor, final int oneFailureOutOf ) {
            super.setup(keys, fillFactor, oneFailureOutOf);
            m_map = new Object2IntHashMap<>( keys.length, fillFactor, MISSING_VALUE);
            for ( Integer key : keys ) m_map.put( new Integer( key % oneFailureOutOf == 0 ? key+1 : key), key );
        }

        @Override
        public int test() {
            int res = 0;
            for ( int i = 0; i < m_keys.length; ++i )
                res = res ^ m_map.getValue( m_keys[ i ] );  // need getValue() here because get() returns null for non-existing keys
            return res;
        }
    }

    private static class AgronaObjectIntPutTest extends AbstractObjKeyPutTest {
        @Override
        public int test() {
            final Object2IntHashMap<Integer> m_map = new Object2IntHashMap<>( m_keys.length, m_fillFactor, MISSING_VALUE );
            for ( int i = 0; i < m_keys.length; ++i )
                m_map.put( m_keys[ i ], i );
            for ( int i = 0; i < m_keys2.length; ++i )
                m_map.put( m_keys2[ i ], i );
            return m_map.size();
        }
    }

    private static class AgronaObjectIntRemoveTest extends AbstractObjKeyPutTest {
        @Override
        public int test() {
            final Object2IntHashMap<Integer> m_map = new Object2IntHashMap<>( m_keys.length / 2 + 1, m_fillFactor, MISSING_VALUE );
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
