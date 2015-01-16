package tests.maptests.identity_object;

import net.openhft.koloboke.collect.Equivalence;
import net.openhft.koloboke.collect.hash.HashConfig;
import net.openhft.koloboke.collect.map.hash.HashObjObjMaps;
import tests.maptests.object.AbstractObjObjMapTest;

import java.util.Map;

/**
 * HFTC pure IdentityMap version
 */
public class HftcIdentityMapTest extends AbstractObjObjMapTest {
    private Map<Integer, Integer> m_map;

    @Override
    public void setup(final int[] keys, final float fillFactor) {
        super.setup( keys, fillFactor );
        m_map = HashObjObjMaps.getDefaultFactory().withKeyEquivalence( Equivalence.identity() ).
                withHashConfig(HashConfig.fromLoads(fillFactor, fillFactor, fillFactor)).newMutableMap(keys.length);
        for (Integer key : m_keys)
            m_map.put( key, key ); //new Integer is intentional here - this is the whole purpose of this test
    }

    @Override
    public int runRandomTest() {
        int res = 0;
        for (int i = 0; i < m_keys.length; ++i) {
            res = res ^ m_map.get(m_keys[i]);
        }
        return res;
    }
}
