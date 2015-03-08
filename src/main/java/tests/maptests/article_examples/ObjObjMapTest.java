package tests.maptests.article_examples;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import map.objobj.ObjObjMap;
import tests.maptests.IMapTest;
import tests.maptests.ITestSet;
import tests.maptests.object_prim.AbstractObjKeyGetTest;
import tests.maptests.object_prim.AbstractObjKeyPutTest;

import java.util.Map;

public class ObjObjMapTest implements ITestSet {
    @Override
    public IMapTest getTest() {
        return new ObjObjMapGetTest();
    }

    @Override
    public IMapTest putTest() {
        return new FastUtilObjPutTest();
    }

    @Override
    public IMapTest removeTest() {
        return new FastUtilObjRemoveTest();
    }

    private static class ObjObjMapGetTest extends AbstractObjKeyGetTest {
        private ObjObjMap<Integer, Integer> m_map;

        @Override
        public void setup(final int[] keys, final float fillFactor, final int oneFailureOutOf ) {
            super.setup( keys, fillFactor, oneFailureOutOf );
            m_map = new ObjObjMap<>( m_keys.length, fillFactor );
            for (Integer key : m_keys) m_map.put(new Integer( key % oneFailureOutOf == 0 ? key + 1 : key ), key);
        }

        @Override
        public int test() {
            int res = 0;
            for ( int i = 0; i < m_keys.length; ++i )
                if ( m_map.get( m_keys[ i ] ) != null ) res ^= 1;
            return res;
        }
    }

    private static class FastUtilObjPutTest extends AbstractObjKeyPutTest {
        @Override
        public int test() {
            final Map<Integer, Integer> m_map = new Object2ObjectOpenHashMap<>( m_keys.length, m_fillFactor );
            for ( int i = 0; i < m_keys.length; ++i )
                m_map.put( m_keys[ i ], m_keys[ i ] );
            for ( int i = 0; i < m_keys2.length; ++i )
                m_map.put( m_keys2[ i ], m_keys2[ i ] );
            return m_map.size();
        }
    }

    private static class FastUtilObjRemoveTest extends AbstractObjKeyPutTest {
        @Override
        public int test() {
            final Map<Integer, Integer> m_map = new Object2ObjectOpenHashMap<>( m_keys.length / 2 + 1, m_fillFactor );
            int add = 0, remove = 0;
            while ( add < m_keys.length )
            {
                m_map.put( m_keys[ add ], m_keys[ add ] );
                ++add;
                m_map.put( m_keys[ add ], m_keys[ add ] );
                ++add;
                m_map.remove( m_keys2[ remove++ ] );
            }
            return m_map.size();
        }
    }
}
