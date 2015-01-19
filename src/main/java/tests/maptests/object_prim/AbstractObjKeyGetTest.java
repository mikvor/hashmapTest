package tests.maptests.object_prim;

import tests.maptests.IMapTest;

/**
 * Base class for object to primitive get tests
 */
public abstract class AbstractObjKeyGetTest implements IMapTest {
    protected Integer[] m_keys;

    @Override
    public void setup(final int[] keys, final float fillFactor, final int oneFailureOutOf ) {
        m_keys = new Integer[ keys.length ];
        for ( int i = 0; i < keys.length; ++i )
            m_keys[ i ] = new Integer( keys[ i ] );
    }
}
