package tests.maptests.prim_object;

import tests.maptests.IMapTest;

/**
 * Base class for primitive-to-object put tests
 */
public abstract class AbstractPrimObjectPutTest implements IMapTest {
    protected int[] m_keys;
	protected float m_fillFactor;

    @Override
    public void setup(final int[] keys, final float fillFactor, final int oneFailOutOf ) {
    	m_fillFactor = fillFactor;
        m_keys = keys;
    }

}
