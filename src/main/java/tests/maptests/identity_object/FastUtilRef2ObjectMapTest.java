package tests.maptests.identity_object;

import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import tests.maptests.IMapTest;
import tests.maptests.ITestSet;
import tests.maptests.object_prim.AbstractObjKeyGetTest;

import java.util.Map;

/**
 * FastUtil IdentityHashMap version
 */
public class FastUtilRef2ObjectMapTest implements ITestSet
{
    @Override
    public IMapTest getTest() {
        return new FastUtilRef2ObjectGetTest();
    }

    @Override
    public IMapTest putTest() {
        return new FastUtilRef2ObjPutTest();
    }

    private static class FastUtilRef2ObjectGetTest extends AbstractObjKeyGetTest {
        private Map<Integer, Integer> m_map;

        @Override
        public void setup(final int[] keys, final float fillFactor, final int oneFailureOutOf ) {
            super.setup( keys, fillFactor, oneFailureOutOf );
            m_map = new Reference2ObjectOpenHashMap<>( keys.length, fillFactor );
            for (Integer key : m_keys)
                m_map.put(key % oneFailureOutOf == 0 ? key + 1 : key, key);
        }

        @Override
        public int test() {
            int res = 0;
            for ( int i = 0; i < m_keys.length; ++i )
                if ( m_map.get( m_keys[ i ] ) != null ) res ^= 1;
            return res;
        }
    }


    private static class FastUtilRef2ObjPutTest extends AbstractObjKeyPutIdentityTest {
        @Override
        public int test() {
            final Map<Integer, Integer> m_map = new Reference2ObjectOpenHashMap<>( m_keys.length, m_fillFactor );
            for ( int i = 0; i < m_keys.length; ++i )
                m_map.put( m_keys[ i ], m_keys[ i ] );
            for ( int i = 0; i < m_keys.length; ++i ) //same key set is used for identity maps
                m_map.put( m_keys[ i ], m_keys[ i ] );
            return m_map.size();
        }
    }

}


