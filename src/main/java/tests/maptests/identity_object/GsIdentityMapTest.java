package tests.maptests.identity_object;

import org.eclipse.collections.api.block.HashingStrategy;
import org.eclipse.collections.impl.map.strategy.mutable.UnifiedMapWithHashingStrategy;
import tests.maptests.IMapTest;
import tests.maptests.ITestSet;
import tests.maptests.object_prim.AbstractObjKeyGetTest;

import java.util.Map;

/**
 * GS IdentityMap version
 */
public class GsIdentityMapTest implements ITestSet
{
    @Override
    public IMapTest getTest() {
        return new GsIdentityMapGetTest();
    }

    @Override
    public IMapTest putTest() {
        return new GsObjIdentityPutTest();
    }

    @Override
    public IMapTest removeTest() {
        return new GsObjIdentityRemoveTest();
    }

    private static <T, V> Map<T, V> makeMap( final int size, final float fillFactor )
    {
        return new UnifiedMapWithHashingStrategy<>(new HashingStrategy<T>() {
                        @Override
                        public int computeHashCode(T object) {
                            return System.identityHashCode( object );
                        }

                        @Override
                        public boolean equals(T object1, T object2) {
                            return object1 == object2;
                        }
                    }, size, fillFactor );
    }

    private static class GsIdentityMapGetTest extends AbstractObjKeyGetTest {
        private Map<Integer, Integer> m_map;

        @Override
        public void setup(final int[] keys, final float fillFactor, final int oneFailureOutOf ) {
            super.setup( keys, fillFactor, oneFailureOutOf );
            m_map = makeMap( keys.length, fillFactor );
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

    private static class GsObjIdentityPutTest extends AbstractObjKeyPutIdentityTest {
        @Override
        public int test() {
            final Map<Integer, Integer> m_map = makeMap(m_keys.length, m_fillFactor);
            for ( int i = 0; i < m_keys.length; ++i )
               m_map.put( m_keys[ i ],m_keys[ i ] );
            for ( int i = 0; i < m_keys.length; ++i ) //same key set for identity maps
               m_map.put( m_keys[ i ],m_keys[ i ] );
            return m_map.size();
        }
    }

    private static class GsObjIdentityRemoveTest extends AbstractObjKeyPutIdentityTest {
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

