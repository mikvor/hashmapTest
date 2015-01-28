package tests.maptests.object;

import net.openhft.koloboke.collect.hash.HashConfig;
import net.openhft.koloboke.collect.map.hash.HashObjObjMaps;
import tests.maptests.IMapTest;
import tests.maptests.ITestSet;
import tests.maptests.object_prim.AbstractObjKeyGetTest;
import tests.maptests.object_prim.AbstractObjKeyPutTest;

import java.util.Map;

/**
 * Koloboke mutable object map test
 */
public class HftcMutableObjTest implements ITestSet
{
    @Override
    public IMapTest getTest() {
        return new HftcMutableObjGetTest();
    }

    @Override
    public IMapTest putTest() {
        return new HftcMutableObjPutTest();
    }

    @Override
    public IMapTest removeTest() {
        return new HftcMutableObjRemoveTest();
    }

    protected <T, V> Map<T, V> makeMap( final int size, final float fillFactor )
    {
        return HashObjObjMaps.getDefaultFactory().
                withHashConfig(HashConfig.fromLoads(fillFactor/2, fillFactor, fillFactor)).newMutableMap(size);
    }

    //made not static due to NotNullKeys subclass. If that test is removed, we need to rollback to static classes here
    private class HftcMutableObjGetTest extends AbstractObjKeyGetTest {
        private Map<Integer, Integer> m_map;

        @Override
        public void setup(final int[] keys, final float fillFactor, final int oneFailureOutOf ) {
            super.setup( keys, fillFactor, oneFailureOutOf );
            m_map = makeMap( keys.length, fillFactor );
            for (Integer key : m_keys)
                m_map.put(new Integer( key % oneFailureOutOf == 0 ? key + 1 : key ), key);
        }

        @Override
        public int test() {
            int res = 0;
            for (int i = 0; i < m_keys.length; ++i)
                if ( m_map.get( m_keys[ i ] ) != null ) res ^= 1;
            return res;
        }
    }

    private class HftcMutableObjPutTest extends AbstractObjKeyPutTest {
        @Override
        public int test() {
            final Map<Integer, Integer> m_map = makeMap(m_keys.length, m_fillFactor);
            for (int i = 0; i < m_keys.length; ++i)
                m_map.put(m_keys[i],m_keys[i]);
            for (int i = 0; i < m_keys2.length; ++i)
                m_map.put(m_keys2[i],m_keys2[i]);
            return m_map.size();
        }
    }

    private class HftcMutableObjRemoveTest extends AbstractObjKeyPutTest {
        @Override
        public int test() {
            final Map<Integer, Integer> m_map = makeMap(m_keys.length, m_fillFactor);
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

