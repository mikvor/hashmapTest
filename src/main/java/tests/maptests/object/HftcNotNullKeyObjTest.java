package tests.maptests.object;

import net.openhft.koloboke.collect.hash.HashConfig;
import net.openhft.koloboke.collect.map.hash.HashObjObjMaps;

import java.util.Map;

/**
 * Koloboke Obj map specified it does not want to support null keys
 */
public class HftcNotNullKeyObjTest extends AbstractObjObjMapTest {
    private Map<Integer, Integer> m_map;

    @Override
    public void setup(final int[] keys, final float fillFactor, final int oneFailureOutOf ) {
        super.setup( keys, fillFactor, oneFailureOutOf );
        m_map = HashObjObjMaps.getDefaultFactory().withHashConfig(HashConfig.fromLoads(fillFactor - 0.01, fillFactor, fillFactor + 0.01)).
                withNullKeyAllowed(false).newMutableMap(keys.length);
        for (Integer key : m_keys)
            m_map.put(new Integer( key % oneFailureOutOf == 0 ? key + 1 : key ), key);
    }

    @Override
    public int randomGetTest() {
        int res = 0;
        for (int i = 0; i < m_keys.length; ++i)
            if ( m_map.get( m_keys[ i ] ) != null ) res ^= 1;
        return res;
    }
}
