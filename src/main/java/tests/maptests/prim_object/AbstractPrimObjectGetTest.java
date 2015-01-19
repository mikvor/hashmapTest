package tests.maptests.prim_object;

import tests.maptests.IMapTest;

/**
 * Base class for primitive to object map get tests
 */
public abstract class AbstractPrimObjectGetTest implements IMapTest {
    protected int[] m_keys;

    @Override
    public void setup(final int[] keys, final float fillFactor, final int oneFailOutOf) {
        m_keys = keys;
    }
}
