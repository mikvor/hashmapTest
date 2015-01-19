package tests.maptests.primitive;

import tests.maptests.IMapTest;

/**
 * A base class for primitive-primitive get tests
 */
public abstract class AbstractPrimPrimGetTest implements IMapTest {
    protected int[] m_keys;

    @Override
    public void setup(final int[] keys, final float fillFactor, final int oneFailOutOf ) {
        m_keys = keys;
    }
}
