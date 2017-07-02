package tests.maptests.object;


import com.koloboke.collect.hash.HashConfig;
import com.koloboke.collect.map.hash.HashObjObjMaps;

import java.util.Map;

/**
 * Koloboke Obj map specified it does not want to support null keys
 *
 * BEFORE REMOVAL MAKE INNER CLASSES STATIC IN THE PARENT CLASS!!!
 */
public class KolobokeNotNullKeyObjTest extends KolobokeMutableObjTest {
    protected <T, V> Map<T, V> makeMap( final int size, final float fillFactor )
    {
        return HashObjObjMaps.getDefaultFactory().withNullKeyAllowed(false).
                withHashConfig(HashConfig.fromLoads(fillFactor/2, fillFactor, fillFactor)).newMutableMap(size);
    }
}
