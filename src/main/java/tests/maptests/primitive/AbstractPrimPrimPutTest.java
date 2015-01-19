package tests.maptests.primitive;

import tests.maptests.IMapTest;

/**
 * Base class for primitive-primitive put tests
 */
public abstract class AbstractPrimPrimPutTest implements IMapTest {
    protected int[] m_keys;
	protected float m_fillFactor;

    @Override
    public void setup(final int[] keys, final float fillFactor, final int oneFailOutOf ) {
    	m_fillFactor = fillFactor;
        m_keys = keys;
    }
}
