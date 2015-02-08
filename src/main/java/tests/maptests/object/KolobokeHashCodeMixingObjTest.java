package tests.maptests.object;

import net.openhft.koloboke.collect.hash.HashConfig;
import net.openhft.koloboke.collect.map.hash.HashObjObjMaps;

import java.util.Map;

/**
 * Koloboke Obj map mixing keys' hash codes
 *
 * BEFORE REMOVAL MAKE INNER CLASSES STATIC IN THE PARENT CLASS!!!
 */
public class KolobokeHashCodeMixingObjTest extends KolobokeMutableObjTest {
    protected <T, V> Map<T, V> makeMap( final int size, final float fillFactor )
    {
        return HashObjObjMaps.getDefaultFactory()
                .withHashConfig(HashConfig.fromLoads(fillFactor / 2, fillFactor, fillFactor))
                .withKeyEquivalence(HashCodeMixingEquivalence.INSTANCE)
                .newMutableMap(size);
    }
}
