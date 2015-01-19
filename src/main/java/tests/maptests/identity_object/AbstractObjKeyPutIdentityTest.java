package tests.maptests.identity_object;

import tests.maptests.IMapTest;

/**
 * Base class for identity map put tests
 */
public abstract class AbstractObjKeyPutIdentityTest implements IMapTest {
    protected Integer[] m_keys;
	protected float m_fillFactor;

    @Override
    public void setup(final int[] keys, final float fillFactor, final int oneFailOutOf) {
    	m_fillFactor = fillFactor;
        m_keys = new Integer[ keys.length ];
        for ( int i = 0; i < keys.length; ++i )
            m_keys[ i ] = new Integer( keys[ i ] );
    }

}
