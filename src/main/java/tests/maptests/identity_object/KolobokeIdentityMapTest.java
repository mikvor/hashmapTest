package tests.maptests.identity_object;

import com.koloboke.collect.Equivalence;
import com.koloboke.collect.hash.HashConfig;
import com.koloboke.collect.map.hash.HashObjObjMaps;
import tests.maptests.IMapTest;
import tests.maptests.ITestSet;
import tests.maptests.object_prim.AbstractObjKeyGetTest;

import java.util.Map;

/**
 * Koloboke pure IdentityMap version
 */
public class KolobokeIdentityMapTest implements ITestSet
{
    @Override
    public IMapTest getTest() {
        return new KolobokeIdentityMapGetTest();
    }

    @Override
    public IMapTest putTest() {
        return new KolobokeObjIdentityPutTest();
    }

    @Override
    public IMapTest removeTest() {
        return new KolobokeObjIdentityRemoveTest();
    }

    private static <T, V> Map<T, V> makeMap( final int size, final float fillFactor )
    {
        return HashObjObjMaps.getDefaultFactory().withKeyEquivalence( Equivalence.identity() ).
                            withHashConfig(HashConfig.fromLoads(fillFactor/2, fillFactor, fillFactor)).newMutableMap(size);
    }

    private static class KolobokeIdentityMapGetTest extends AbstractObjKeyGetTest {
        private Map<Integer, Integer> m_map;

        @Override
        public void setup(final int[] keys, final float fillFactor, final int oneFailureOutOf ) {
            super.setup( keys, fillFactor, oneFailureOutOf );
            m_map = makeMap(keys.length, fillFactor);
            for (Integer key : m_keys)
                m_map.put( key % oneFailureOutOf == 0 ? key + 1 : key, key );
        }

        @Override
        public int test() {
            int res = 0;
            for (int i = 0; i < m_keys.length; ++i)
                if ( m_map.get( m_keys[ i ] ) != null ) res ^= 1;
            return res;
        }
    }

    private class KolobokeObjIdentityPutTest extends AbstractObjKeyPutIdentityTest {
        @Override
        public int test() {
            final Map<Integer, Integer> m_map = makeMap(m_keys.length, m_fillFactor);
            for (int i = 0; i < m_keys.length; ++i)
                m_map.put(m_keys[i],m_keys[i]);
            for (int i = 0; i < m_keys.length; ++i) //same set for identity tests
                m_map.put(m_keys[i],m_keys[i]);
            return m_map.size();
        }
    }

    private class KolobokeObjIdentityRemoveTest extends AbstractObjKeyPutIdentityTest {
        @Override
        public int test() {
            final Map<Integer, Integer> m_map = makeMap(m_keys.length / 2 + 1, m_fillFactor);
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

