package tests.maptests.primitive;

import tests.maptests.IMapTest;

/**
 * A base class for all primitive-primitive tests
 */
public abstract class AbstractPrimPrimMapTest implements IMapTest {
    protected int[] m_keys;

    @Override
    public void setup(final int[] keys, final float fillFactor) {
        m_keys = keys;
    }
}
