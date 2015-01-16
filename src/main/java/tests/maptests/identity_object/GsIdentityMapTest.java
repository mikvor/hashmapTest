package tests.maptests.identity_object;

import com.gs.collections.api.block.HashingStrategy;
import com.gs.collections.impl.map.strategy.mutable.UnifiedMapWithHashingStrategy;
import tests.maptests.object.AbstractObjObjMapTest;

import java.util.Map;

/**
 * GS IdentityMap version
 */
public class GsIdentityMapTest  extends AbstractObjObjMapTest {
    private Map<Integer, Integer> m_map;

    @Override
    public void setup(final int[] keys, final float fillFactor) {
        super.setup( keys, fillFactor );
        m_map = new UnifiedMapWithHashingStrategy<>(new HashingStrategy<Integer>() {
            @Override
            public int computeHashCode(Integer object) {
                return System.identityHashCode( object );
            }

            @Override
            public boolean equals(Integer object1, Integer object2) {
                return object1 == object2;
            }
        }, keys.length, fillFactor );
        for (Integer key : m_keys)
            m_map.put(key, key);
    }

    @Override
    public int runRandomTest() {
        int res = 0;
        for ( int i = 0; i < m_keys.length; ++i )
            res = res ^ m_map.get( m_keys[ i ] );
        return res;
    }
}
