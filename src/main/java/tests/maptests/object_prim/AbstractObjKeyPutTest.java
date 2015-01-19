package tests.maptests.object_prim;

import tests.maptests.IMapTest;

/**
 * Base class for object-to-primitive put tests
 */
public abstract class AbstractObjKeyPutTest implements IMapTest {
    protected Integer[] m_keys;
    protected Integer[] m_keys2;
	protected float m_fillFactor;

    @Override
    public void setup(final int[] keys, final float fillFactor, final int oneFailOutOf) {
    	m_fillFactor = fillFactor;
        m_keys = new Integer[ keys.length ];
        for ( int i = 0; i < keys.length; ++i )
            m_keys[ i ] = new Integer( keys[ i ] );
        m_keys2 = new Integer[ keys.length ];
        for ( int i = 0; i < keys.length; ++i )
            m_keys2[ i ] = new Integer( keys[ i ] );
    }
}
