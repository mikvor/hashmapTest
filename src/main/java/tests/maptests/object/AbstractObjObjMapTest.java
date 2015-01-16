package tests.maptests.object;

import tests.maptests.IMapTest;

/**
 * A base class for all ObjObj tests
 */
public abstract class AbstractObjObjMapTest implements IMapTest {
    protected Integer[] m_keys;

    @Override
    public void setup(final int[] keys, final float fillFactor ) {
        m_keys = new Integer[ keys.length ];
        for ( int i = 0; i < keys.length; ++i )
            m_keys[ i ] = keys[ i ];
    }
}
